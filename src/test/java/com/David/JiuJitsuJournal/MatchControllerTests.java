package com.David.JiuJitsuJournal;

import com.David.JiuJitsuJournal.api.controllers.MatchController;
import com.David.JiuJitsuJournal.api.requests.MatchRequest;
import com.David.JiuJitsuJournal.data.entities.BeltRank;
import com.David.JiuJitsuJournal.data.entities.Match;
import com.David.JiuJitsuJournal.data.entities.Opponent;
import com.David.JiuJitsuJournal.data.entities.User;
import com.David.JiuJitsuJournal.data.repository.MatchRepository;
import com.David.JiuJitsuJournal.data.repository.OpponentRepository;
import com.David.JiuJitsuJournal.data.repository.UserRepository;
import com.David.JiuJitsuJournal.data.services.MatchDataService;
import com.David.JiuJitsuJournal.data.specification.MatchSpecification;
import com.David.JiuJitsuJournal.data.specification.OpponentSpecification;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
    @Mock
    OpponentSpecification opponentSpecification;
    @Mock
    MatchSpecification matchSpecification;

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

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);

        ResponseEntity responseEntity = matchController.getMatch(matchId);
        verify(matchSpecification).withId(matchId);
        verify(matchSpecification).withUser(userEntity);
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
        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);

        ResponseEntity responseEntity = matchController.getMatch(matchId);
        verify(matchSpecification).withId(matchId);
        verify(matchSpecification).withUser(userEntity);
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

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.getMatches(null, null, null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(matchSpecification).withDate(null);
        verify(matchSpecification).withUser(userEntity);
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

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.getMatches(opponent1Entity.getName(),
                opponent1Entity.getBeltRank().getBeltRankId(), match1Entity.getMatchDate());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(opponentSpecification).withName(opponent1Entity.getName());
        verify(opponentSpecification).withBeltRank(opponent1Entity.getBeltRank().getBeltRankId());
        verify(opponentSpecification).withUser(userEntity);
        verify(matchSpecification).withDate(match1Entity.getMatchDate());
        verify(matchSpecification).withUser(userEntity);
        verify(matchSpecification).withOpponents(Arrays.asList(opponent1Entity));
        LinkedList<com.David.JiuJitsuJournal.api.responses.Match> body = (LinkedList<com.David.JiuJitsuJournal.api.responses.Match>) responseEntity.getBody();
        assertMatchValues(match1Entity, body.get(0));
    }

    @Test
    public void GetMatchesShouldReturnEmptyListIfThereAreNoMatches() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findAll(any(Specification.class))).thenReturn(new LinkedList());
        when(matchRepository.findAll(any(Specification.class))).thenReturn(new LinkedList());

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        LocalDate matchDate = LocalDate.of(2021, 2, 2);
        ResponseEntity responseEntity = matchController.getMatches("Bob", 2,
                matchDate);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(matchSpecification).withUser(userEntity);
        verify(matchSpecification).withDate(matchDate);
        LinkedList<com.David.JiuJitsuJournal.api.responses.Match> body = (LinkedList<com.David.JiuJitsuJournal.api.responses.Match>) responseEntity.getBody();

        assertEquals(0, body.size());
    }

    @Test
    public void createMatchShouldReturnMatchWithProperMapping() {
        long opponentId = 1L;
        LocalDate matchDate = LocalDate.of(2021, 2, 20);
        MatchRequest matchRequest = new MatchRequest(matchDate, opponentId,
                "Lots of reversals");

        User userEntity = new User();

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);

        Match matchEntity = new Match(matchRequest.getMatchDate(), userEntity, opponentEntity,
                matchRequest.getDescription());

        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.of(opponentEntity));
        when(matchRepository.save(any(Match.class))).thenReturn(matchEntity);

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.createMatch(matchRequest);
        verify(opponentSpecification).withId(opponentId);
        verify(opponentSpecification).withUser(userEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertMatchValues(matchEntity, (com.David.JiuJitsuJournal.api.responses.Match) responseEntity.getBody());
    }

    @Test
    public void createMatchDoesNotFindOpponentReturns404() {
        long opponentId = 1L;
        LocalDate matchDate = LocalDate.of(2021, 2, 20);
        MatchRequest matchRequest = new MatchRequest(matchDate, opponentId,
                "Lots of reversals");

        User userEntity = new User();

        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());
        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.createMatch(matchRequest);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(String.format("Opponent with id %d not found.", opponentId), responseEntity.getBody());
    }

    @Test
    public void createMatchShouldReturn500IfNotSavedToDatabase() {
        long opponentId = 1L;
        LocalDate matchDate = LocalDate.of(2021, 2, 20);
        MatchRequest matchRequest = new MatchRequest(matchDate, opponentId,
                "Lots of reversals");

        User userEntity = new User();

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);

        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.of(opponentEntity));
        when(matchRepository.save(any(Match.class))).thenReturn(null);

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.createMatch(matchRequest);
        verify(opponentSpecification).withId(opponentId);
        verify(opponentSpecification).withUser(userEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void updateMatchShouldMapFields() {
        long opponentId = 1L;
        long matchId = 1L;
        LocalDate matchDate = LocalDate.of(2021, 2, 20);
        MatchRequest matchRequest = new MatchRequest(matchDate, opponentId,
                "Lots of reversals");

        User userEntity = new User();

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);

        Match originalMatchEntity = new Match(LocalDate.of(2021, 2, 2), userEntity, opponentEntity,
                "Good fight");

        Match matchEntity = new Match(matchRequest.getMatchDate(), userEntity, opponentEntity,
                matchRequest.getDescription());

        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.of(opponentEntity));
        when(matchRepository.findOne(any(Specification.class))).thenReturn(Optional.of(originalMatchEntity));
        when(matchRepository.save(any(Match.class))).thenReturn(matchEntity);

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.updateMatch(matchRequest, matchId);
        verify(opponentSpecification).withId(opponentId);
        verify(opponentSpecification).withUser(userEntity);
        verify(matchSpecification).withId(matchId);
        verify(matchSpecification).withUser(userEntity);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertMatchValues(matchEntity, (com.David.JiuJitsuJournal.api.responses.Match) responseEntity.getBody());
    }

    @Test
    public void updateMatchCannotFindOpponentShouldReturn404() {
        long matchId = 1L;
        long opponentId = 1L;
        LocalDate matchDate = LocalDate.of(2021, 2, 20);
        MatchRequest matchRequest = new MatchRequest(matchDate, opponentId,
                "Lots of reversals");

        User userEntity = new User();

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.updateMatch(matchRequest, matchId);
        verify(opponentSpecification).withUser(userEntity);
        verify(opponentSpecification).withId(opponentId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(String.format("Opponent with id %d not found.", opponentId), responseEntity.getBody());
    }

    @Test
    public void updateMatchCannotFindMatchShouldReturn404() {
        long opponentId = 1L;
        long matchId = 1L;
        LocalDate matchDate = LocalDate.of(2021, 2, 20);
        MatchRequest matchRequest = new MatchRequest(matchDate, opponentId,
                "Lots of reversals");

        User userEntity = new User();

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);

        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.of(opponentEntity));
        when(matchRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.updateMatch(matchRequest, matchId);
        verify(opponentSpecification).withUser(userEntity);
        verify(opponentSpecification).withId(opponentId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals(String.format("Match with id %d does not exist.", matchId), responseEntity.getBody());
    }

    @Test
    public void updateMatchFailsToSaveToDatabaseShouldReturn500() {
        long opponentId = 1L;
        long matchId = 1L;
        LocalDate matchDate = LocalDate.of(2021, 2, 20);
        MatchRequest matchRequest = new MatchRequest(matchDate, opponentId,
                "Lots of reversals");

        User userEntity = new User();

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);

        Match originalMatchEntity = new Match(LocalDate.of(2021, 2, 2), userEntity, opponentEntity,
                "Good fight");

        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.of(opponentEntity));
        when(matchRepository.findOne(any(Specification.class))).thenReturn(Optional.of(originalMatchEntity));
        when(matchRepository.save(any(Match.class))).thenReturn(null);

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.updateMatch(matchRequest, matchId);
        verify(opponentSpecification).withId(opponentId);
        verify(opponentSpecification).withUser(userEntity);
        verify(matchSpecification).withId(matchId);
        verify(matchSpecification).withUser(userEntity);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }

    @Test
    public void deleteMatchShouldReturn204NoContent() {
        long matchId = 1L;
        long opponentId = 1L;
        User userEntity = new User();

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);
        Match originalMatchEntity = new Match(LocalDate.of(2021, 2, 2), userEntity, opponentEntity,
                "Good fight");
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(matchRepository.findOne(any(Specification.class))).thenReturn(Optional.of(originalMatchEntity));

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.deleteMatch(matchId);
        verify(matchSpecification).withId(matchId);
        verify(matchSpecification).withUser(userEntity);
        verify(matchRepository).delete(originalMatchEntity);
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    }

    @Test
    public void deleteMatchCantFindMatchShouldReturn404() {
        long matchId = 1L;
        long opponentId = 1L;
        User userEntity = new User();

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);
        Match originalMatchEntity = new Match(LocalDate.of(2021, 2, 2), userEntity, opponentEntity,
                "Good fight");
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(matchRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        matchManager = new MatchManager(new MatchDataService(userRepository, opponentRepository, matchRepository,
                opponentSpecification, matchSpecification));
        matchController = new MatchController(matchManager);
        ResponseEntity responseEntity = matchController.deleteMatch(matchId);
        verify(matchSpecification).withId(matchId);
        verify(matchSpecification).withUser(userEntity);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private void assertMatchValues(Match matchEntity, com.David.JiuJitsuJournal.api.responses.Match matchDto) {
        assertEquals(Optional.of(matchEntity.getId()).get(), matchDto.getId());
        assertEquals(matchEntity.getMatchDate(), matchDto.getMatchDate());
        assertEquals(matchEntity.getDescription(), matchDto.getDescription());
        assertEquals(Optional.of(matchEntity.getOpponent().getId()).get(), matchDto.getOpponentId());
    }
}
