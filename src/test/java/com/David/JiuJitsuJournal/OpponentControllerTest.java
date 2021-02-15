package com.David.JiuJitsuJournal;

import com.David.JiuJitsuJournal.api.controllers.OpponentController;
import com.David.JiuJitsuJournal.domain.BeltRankEnum;
import com.David.JiuJitsuJournal.domain.managers.OpponentManager;
import com.David.JiuJitsuJournal.domain.models.Opponent;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OpponentControllerTest {
    @InjectMocks
    private OpponentController opponentController;

    @Mock
    private OpponentManager opponentManager;

    @Before
    public void init() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    public void getOpponentByIdShouldReturnOpponentIfItExists() {
        UserDetails userDetails = mock(UserDetails.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal()).thenReturn(userDetails);

        Opponent domainOpponent = new Opponent("Royce Gracie",
                                                     BeltRankEnum.BROWN,
                                                    73,
                                                     176,
                                                  1L);
        when(opponentManager.getOpponentById(1L, userDetails.getUsername())).thenReturn(domainOpponent);
        ResponseEntity responseEntity = opponentController.getOpponent(1L);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

    }
}
