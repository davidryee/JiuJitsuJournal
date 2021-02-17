package com.David.JiuJitsuJournal;

import com.David.JiuJitsuJournal.api.controllers.OpponentController;
import com.David.JiuJitsuJournal.api.responses.Opponent;
import com.David.JiuJitsuJournal.data.entities.BeltRank;
import com.David.JiuJitsuJournal.data.entities.User;
import com.David.JiuJitsuJournal.data.repository.OpponentRepository;
import com.David.JiuJitsuJournal.data.repository.UserRepository;
import com.David.JiuJitsuJournal.domain.BeltRankEnum;
import com.David.JiuJitsuJournal.domain.managers.OpponentManager;
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

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OpponentControllerTest {
    @InjectMocks
    private OpponentController opponentController;
    private OpponentManager opponentManager;

    @Mock
    private OpponentRepository opponentRepository;
    @Mock
    private UserRepository userRepository;

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
    public void getOpponentByIdShouldReturnOpponentIfItExists() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));

        long opponentId = 1L;

        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(4, "Brown"),
                72,
                176
        );
        opponentEntity.setUser(userEntity);
        opponentEntity.setId(opponentId);
        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.of(opponentEntity));

        opponentManager = new OpponentManager(new com.David.JiuJitsuJournal.data.services.OpponentDataService(opponentRepository, userRepository));
        opponentController = new OpponentController(opponentManager);

        ResponseEntity responseEntity = opponentController.getOpponent(opponentId);
        com.David.JiuJitsuJournal.api.responses.Opponent opponentDto = (com.David.JiuJitsuJournal.api.responses.Opponent) responseEntity.getBody();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertOpponentFields(opponentEntity, opponentDto);
    }

    @Test
    public void getOpponentByIdShouldReturn404IfOpponentDoesNotExist() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        long opponentId = 1L;

        when(opponentRepository.findOne(any(Specification.class))).thenReturn(Optional.empty());

        opponentManager = new OpponentManager(new com.David.JiuJitsuJournal.data.services.OpponentDataService(opponentRepository, userRepository));
        opponentController = new OpponentController(opponentManager);

        ResponseEntity responseEntity = opponentController.getOpponent(opponentId);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());

    }

    @Test
    public void getOpponentsShouldReturnOpponentsIfTheyExist() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));

        long opponent1Id = 1L;
        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity1 = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Royce Gracie",
                new BeltRank(3, "Brown"),
                72,
                176
        );
        opponentEntity1.setId(opponent1Id);
        opponentEntity1.setUser(userEntity);

        long opponent2Id = 2L;
        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity2 = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Bob Grappler",
                new BeltRank(2, "Purple"),
                66,
                200
        );
        opponentEntity2.setId(opponent2Id);
        opponentEntity2.setUser(userEntity);

        long opponent3Id = 3L;
        com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity3 = new com.David.JiuJitsuJournal.data.entities.Opponent(
                "Jane Smith",
                new BeltRank(4, "Black"),
                68,
                150
        );
        opponentEntity3.setId(opponent3Id);
        opponentEntity3.setUser(userEntity);

        when(opponentRepository.findAll(any(Specification.class))).thenReturn(new LinkedList(Arrays.asList(
                opponentEntity1,
                opponentEntity2,
                opponentEntity3
        )));

        opponentManager = new OpponentManager(new com.David.JiuJitsuJournal.data.services.OpponentDataService(opponentRepository, userRepository));
        opponentController = new OpponentController(opponentManager);
        ResponseEntity responseEntity = opponentController.getOpponents(null, null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        LinkedList<Opponent> body = (LinkedList<Opponent>) responseEntity.getBody();
        assertOpponentFields(opponentEntity1, body.get(0));
        assertOpponentFields(opponentEntity2, body.get(1));
        assertOpponentFields(opponentEntity3, body.get(2));
    }

    @Test
    public void getOpponentsShouldReturnEmptyListIfNoneExist() {
        User userEntity = new User();
        when(userRepository.findByUsername(null)).thenReturn(java.util.Optional.of(userEntity));
        when(opponentRepository.findAll(any(Specification.class))).thenReturn(new LinkedList());

        opponentManager = new OpponentManager(new com.David.JiuJitsuJournal.data.services.OpponentDataService(opponentRepository, userRepository));
        opponentController = new OpponentController(opponentManager);
        ResponseEntity responseEntity = opponentController.getOpponents(null, null);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        LinkedList<Opponent> body = (LinkedList<Opponent>) responseEntity.getBody();
        assertEquals(0, body.size());
    }

    private void assertOpponentFields(com.David.JiuJitsuJournal.data.entities.Opponent opponentEntity, com.David.JiuJitsuJournal.api.responses.Opponent opponentDto) {
        assertEquals(Optional.of(opponentEntity.getId()).get(), opponentDto.getId());
        assertEquals(opponentEntity.getName(), opponentDto.getName());
        assertEquals(opponentEntity.getHeightInInches(), opponentDto.getHeightInInches());
        assertEquals(opponentEntity.getWeightInLbs(), opponentDto.getWeightInLbs());
        assertEquals(BeltRankEnum.values()[opponentEntity.getBeltRank().getBeltRankId()], opponentDto.getBeltRank());
    }
}
