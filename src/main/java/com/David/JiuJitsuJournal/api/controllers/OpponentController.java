package com.David.JiuJitsuJournal.api.controllers;

import com.David.JiuJitsuJournal.api.mappers.OpponentMapper;
import com.David.JiuJitsuJournal.api.requests.OpponentRequest;
import com.David.JiuJitsuJournal.domain.managers.OpponentManager;
import com.David.JiuJitsuJournal.domain.models.Opponent;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class OpponentController {
    private OpponentManager opponentManager;

    public OpponentController(OpponentManager opponentManager){
        this.opponentManager = opponentManager;
    }

    @GetMapping("/opponents")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getOpponents(@RequestParam(required = false) String name,
                                                                                   @RequestParam(required = false) Integer beltRank) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        List<com.David.JiuJitsuJournal.api.responses.Opponent> opponentDtos = new LinkedList();
        List<Opponent> opponents = opponentManager.getOpponents(name, beltRank, userDetails.getUsername());
        for(Opponent opponentModel : opponents){
            opponentDtos.add(OpponentMapper.mapToDto(opponentModel));
        }
        return new ResponseEntity(opponentDtos, HttpStatus.OK);
    }

    @GetMapping("/opponents/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity getOpponent(@PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        Opponent domainOpponent = opponentManager.getOpponentById(id, userDetails.getUsername());

        //Even if the opponent exists but belongs to a different user, we want to return a 404.
        //Returning a 403 would alert a malicious user to the existence of the opponent resource.
        if(domainOpponent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Opponent with id %d does not exist", id));
        }

        return new ResponseEntity(OpponentMapper.mapToDto(domainOpponent), HttpStatus.OK);
    }

    @PostMapping("/opponents")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity createOpponent(
            @Valid @RequestBody OpponentRequest opponentRequest){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            Opponent domainOpponent = this.opponentManager.createOpponent(opponentRequest.getName(),
                                                                          opponentRequest.getBeltRank(),
                                                                          opponentRequest.getHeightInInches(),
                                                                          opponentRequest.getWeightInLbs(),
                                                                          userDetails.getUsername());

            return new ResponseEntity(OpponentMapper.mapToDto(domainOpponent), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/opponents/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity updateOpponent(@Valid @RequestBody OpponentRequest opponentRequest,
                                         @PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            Opponent domainOpponent = this.opponentManager.updateOpponent(id,
                                                                          opponentRequest.getName(),
                                                                          opponentRequest.getBeltRank(),
                                                                          opponentRequest.getHeightInInches(),
                                                                          opponentRequest.getWeightInLbs(),
                                                                          userDetails.getUsername());

            return new ResponseEntity(OpponentMapper.mapToDto(domainOpponent), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        catch (Exception e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/opponents/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity deleteOpponent(@PathVariable("id") Long id) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        try {
            this.opponentManager.deleteOpponent(id, userDetails.getUsername());
        }
        catch (EntityNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Opponent with id %d does not exist", id));
        }

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
