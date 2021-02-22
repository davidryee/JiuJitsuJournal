package com.David.JiuJitsuJournal;

import com.David.JiuJitsuJournal.api.controllers.MatchController;
import com.David.JiuJitsuJournal.data.entities.BeltRank;
import com.David.JiuJitsuJournal.data.entities.Match;
import com.David.JiuJitsuJournal.data.entities.Opponent;
import com.David.JiuJitsuJournal.data.entities.User;
import com.David.JiuJitsuJournal.data.repository.MatchRepository;
import com.David.JiuJitsuJournal.data.repository.OpponentRepository;
import com.David.JiuJitsuJournal.data.repository.UserRepository;
import com.David.JiuJitsuJournal.data.services.MatchDataService;
import com.David.JiuJitsuJournal.domain.managers.MatchManager;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class MatchControllerTests {
    @InjectMocks
    private MatchController matchController;
    private MatchManager matchManager;

    @Mock
    private MatchRepository matchRepository;
    @Mock
    private OpponentRepository opponentRepository;
    @Mock
    UserRepository userRepository;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    private void setupAuth() {
        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);
    }

    @Test
    public void getMatchByIdShouldReturnMatchIfItExists() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));

        long opponentId = 1L;
        long matchId = 2L;

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);
        Match matchEntity = new Match(LocalDate.of(2021, 1,1 ),
                                        userEntity, opponentEntity, "good Fight");
        matchEntity.setId(matchId);

        when(matchRepository.findOne(any(Specification.class))).thenReturn(Optional.of(matchEntity));

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository));
        matchController = new MatchController(matchManager);

        ResponseEntity responseEntity = matchController.getMatch(matchId);
        com.David.JiuJitsuJournal.api.responses.Match matchDto = (com.David.JiuJitsuJournal.api.responses.Match) responseEntity.getBody();
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertMatchValues(matchEntity, matchDto);
    }

    @Test
    public void getMatchByIdShouldReturn404IfMatchDoesNotExist(){
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));

        long matchId = 2L;

        when(matchRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());
        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository));
        matchController = new MatchController(matchManager);

        ResponseEntity responseEntity = matchController.getMatch(matchId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void getMatchesShouldReturnAndMapMatchesFromDb() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));

        long opponentId = 1L;
        long match1Id = 1L;
        long match2Id = 2L;

        com.David.JiuJitsuJournal.data.entities.Opponent opponent1Entity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponent1Entity.setUser(userEntity);
        opponent1Entity.setId(opponentId);
        Match match1Entity = new Match(LocalDate.of(2021, 1,1 ),
                userEntity, opponent1Entity, "good Fight");
        match1Entity.setId(match1Id);

        com.David.JiuJitsuJournal.data.entities.Opponent opponent2Entity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Scotty Karate",
                new BeltRank(2, "Blue"),
                66,
                142
        );
        opponent2Entity.setUser(userEntity);
        opponent2Entity.setId(opponentId);
        Match match2Entity = new Match(LocalDate.of(2021, 2,1 ),
                userEntity, opponent1Entity, "Arm bar finish");
        match2Entity.setId(match2Id);

        List<Match> matchEntitiesFound = new LinkedList<>(Arrays.asList(match1Entity, match2Entity));
        when(matchRepository.findAll(any(Specification.class))).thenReturn(matchEntitiesFound);

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.getMatches(null, null, null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LinkedList<com.David.JiuJitsuJournal.api.responses.Match> body = (LinkedList<com.David.JiuJitsuJournal.api.responses.Match>) responseEntity.getBody();
        assertMatchValues(match1Entity, body.get(0));
        assertMatchValues(match2Entity, body.get(1));
    }

    @Test
    public void getMatchesShouldReturnAndMapMatchesFromDbWhenSearchCriteriaSpecified() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));

        long opponentId = 1L;
        long match1Id = 1L;
        long match2Id = 2L;

        com.David.JiuJitsuJournal.data.entities.Opponent opponent1Entity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponent1Entity.setUser(userEntity);
        opponent1Entity.setId(opponentId);
        Match match1Entity = new Match(LocalDate.of(2021, 1,1 ),
                userEntity, opponent1Entity, "good Fight");
        match1Entity.setId(match1Id);

        com.David.JiuJitsuJournal.data.entities.Opponent opponent2Entity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Scotty Karate",
                new BeltRank(2, "Blue"),
                66,
                142
        );
        opponent2Entity.setUser(userEntity);
        opponent2Entity.setId(opponentId);
        Match match2Entity = new Match(LocalDate.of(2021, 2,1 ),
                userEntity, opponent1Entity, "Arm bar finish");
        match2Entity.setId(match2Id);

        List<Opponent> opponentEntitiesFound = new LinkedList<>(Arrays.asList(opponent1Entity));
        when(opponentRepository.findAll(any(Specification.class))).thenReturn(opponentEntitiesFound);

        List<Match> matchEntitiesFound = new LinkedList<>(Arrays.asList(match1Entity));
        when(matchRepository.findAll(any(Specification.class))).thenReturn(matchEntitiesFound);

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.getMatches(opponent1Entity.getName(), null, null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LinkedList<com.David.JiuJitsuJournal.api.responses.Match> body = (LinkedList<com.David.JiuJitsuJournal.api.responses.Match>) responseEntity.getBody();
        assertMatchValues(match1Entity, body.get(0));
    }

    @Test
    public void GetMatchesShouldReturnEmptyListIfThereAreNoMatches() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findAll(any(Specification.class))).thenReturn(new LinkedList());
        when(matchRepository.findAll(any(Specification.class))).thenReturn(new LinkedList());

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.getMatches(null, null, null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LinkedList<com.David.JiuJitsuJournal.api.responses.Match> body = (LinkedList<com.David.JiuJitsuJournal.api.responses.Match>) responseEntity.getBody();

        assertEquals(0, body.size());
    }

    private void assertMatchValues(Match matchEntity, com.David.JiuJitsuJournal.api.responses.Match matchDto) {
        assertEquals(Optional.of(matchEntity.getId()).get(), matchDto.getId());
        assertEquals(matchEntity.getMatchDate(), matchDto.getMatchDate());
        assertEquals(matchEntity.getDescription(), matchDto.getDescription());
        assertEquals(Optional.of(matchEntity.getOpponent().getId()).get(), matchDto.getOpponentId());
    }
}
