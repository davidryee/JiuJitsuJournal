package com.David.JiuJitsuJournal.api.controllers;

import com.David.JiuJitsuJournal.api.mappers.MatchMapper;
import com.David.JiuJitsuJournal.api.requests.MatchRequest;
import com.David.JiuJitsuJournal.domain.managers.MatchManager;
import com.David.JiuJitsuJournal.domain.models.Match;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

@RestController
public class MatchController {
    private MatchManager matchManager;

    public MatchController(MatchManager matchManager) {
        this.matchManager = matchManager;
    }

    @GetMapping("/matches/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getMatch(@PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Match domainMatch = matchManager.getMatchById(id, userDetails.getUsername());

        if(domainMatch == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Match with id %d does not exist", id));
        }

        return new ResponseEntity(MatchMapper.mapToDto(domainMatch), HttpStatus.OK);
    }

    @GetMapping("/matches")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Iterable<com.David.JiuJitsuJournal.api.responses.Match> getMatches(@RequestParam(required = false) String opponentName,
                                                                              @RequestParam(required = false) Integer opponentBeltRank,
                                                                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate matchDate) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<Match> matches = matchManager.getMatches(opponentName, opponentBeltRank, matchDate, userDetails.getUsername());
        List<com.David.JiuJitsuJournal.api.responses.Match> matchDtos = new LinkedList();
        for (Match matchModel : matches) {
            matchDtos.add(MatchMapper.mapToDto(matchModel));
        }

        return matchDtos;
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
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/matches/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity updateMatch(@Valid @RequestBody MatchRequest matchRequest,
                                      @PathVariable("id") Long id) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            Match domainMatch = this.matchManager.updateMatch(id,
                                                              matchRequest.getMatchDate(),
                                                              matchRequest.getOpponentId(),
                                                              matchRequest.getDescription(),
                                                              userDetails.getUsername());
            return new ResponseEntity(MatchMapper.mapToDto(domainMatch), HttpStatus.OK);
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @DeleteMapping("/matches/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity deleteMatch(@PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            this.matchManager.deleteMatch(id, userDetails.getUsername());
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
