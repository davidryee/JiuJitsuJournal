package com.David.JiuJitsuJournal;

import com.David.JiuJitsuJournal.api.controllers.MatchController;
import com.David.JiuJitsuJournal.data.entities.BeltRank;
import com.David.JiuJitsuJournal.data.entities.Match;
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

    private void assertMatchValues(Match matchEntity, com.David.JiuJitsuJournal.api.responses.Match matchDto) {
        assertEquals(Optional.of(matchEntity.getId()).get(), matchDto.getId());
        assertEquals(matchEntity.getMatchDate(), matchDto.getMatchDate());
        assertEquals(matchEntity.getDescription(), matchDto.getDescription());
        assertEquals(Optional.of(matchEntity.getOpponent().getId()).get(), matchDto.getOpponentId());
    }
}
