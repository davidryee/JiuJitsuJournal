package com.David.JiuJitsuJournal.api.controllers;

import com.David.JiuJitsuJournal.api.mappers.OpponentMapper;
import com.David.JiuJitsuJournal.api.requests.OpponentRequest;
import com.David.JiuJitsuJournal.domain.models.Opponent;
import com.David.JiuJitsuJournal.domain.OpponentManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.LinkedList;
import java.util.List;

@RestController
public class OpponentController {
    private OpponentManager opponentManager;

    public OpponentController(OpponentManager opponentManager){
        this.opponentManager = opponentManager;
    }

    @GetMapping("/opponents")
    public Iterable<com.David.JiuJitsuJournal.api.responses.Opponent> getOpponents(@RequestParam(required = false) String name,
                                                                                   @RequestParam(required = false) Integer beltRank) {
        List<com.David.JiuJitsuJournal.api.responses.Opponent> opponentDtos = new LinkedList<>();
        List<Opponent> opponents = opponentManager.getOpponents(name, beltRank);
        for(Opponent opponentModel : opponents){
            opponentDtos.add(OpponentMapper.mapToDto(opponentModel));
        }
        return opponentDtos;
    }

    @GetMapping("/opponents/{id}")
    public ResponseEntity getOpponent(@PathVariable("id") Long id) {
        Opponent domainOpponent = opponentManager.getOpponentById(id);
        if(domainOpponent == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Opponent with id %d does not exist", id));
        }

        return new ResponseEntity<>(OpponentMapper.mapToDto(domainOpponent), HttpStatus.OK);
    }

    @PostMapping("/opponents")
    public ResponseEntity createOpponent(
            @Valid @RequestBody OpponentRequest opponentRequest){
        try {
            Opponent domainOpponent = this.opponentManager.createOpponent(opponentRequest.getName(),
                                                                          opponentRequest.getBeltRank(),
                                                                          opponentRequest.getHeightInInches(),
                                                                          opponentRequest.getWeightInLbs());

            return new ResponseEntity(OpponentMapper.mapToDto(domainOpponent), HttpStatus.OK);
        }
        catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/opponents/{id}")
    public ResponseEntity updateOpponent(@Valid @RequestBody OpponentRequest opponentRequest,
                                         @PathVariable("id") Long id) {
        try {
            Opponent domainOpponent = this.opponentManager.updateOpponent(id,
                                                                          opponentRequest.getName(),
                                                                          opponentRequest.getBeltRank(),
                                                                          opponentRequest.getHeightInInches(),
                                                                          opponentRequest.getWeightInLbs());

            if(domainOpponent == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(String.format("Opponent with id %d does not exist", id));
            }
            return new ResponseEntity(OpponentMapper.mapToDto(domainOpponent), HttpStatus.OK);
        }
        catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
