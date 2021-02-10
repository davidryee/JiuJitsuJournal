package com.David.JiuJitsuJournal.api.controllers;

import com.David.JiuJitsuJournal.api.mappers.MatchMapper;
import com.David.JiuJitsuJournal.api.requests.MatchRequest;
import com.David.JiuJitsuJournal.domain.managers.MatchManager;
import com.David.JiuJitsuJournal.domain.models.Match;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
public class MatchController {
    private MatchManager matchManager;

    public MatchController(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    @PostMapping("/matches")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity createMatch(@Valid @RequestBody MatchRequest matchRequest) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            Match domainMatch = this.matchManager.createMatch(matchRequest.getMatchDate(),
                                          matchRequest.getOpponentId(),
                                          matchRequest.getDescription(),
                                          userDetails.getUsername());
            return new ResponseEntity(MatchMapper.mapToDto(domainMatch), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
