package com.David.JiuJitsuJournal.Api;

import com.David.JiuJitsuJournal.Api.Mappers.OpponentMapper;
import com.David.JiuJitsuJournal.Api.Requests.OpponentRequest;
import com.David.JiuJitsuJournal.Domain.Models.Opponent;
import com.David.JiuJitsuJournal.Domain.OpponentManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class OpponentController {
    private OpponentManager opponentManager;

    public OpponentController(OpponentManager opponentManager){
        this.opponentManager = opponentManager;
    }

    @GetMapping("/opponents")
    public Iterable<com.David.JiuJitsuJournal.Api.Responses.Opponent> getOpponents() {

        List<com.David.JiuJitsuJournal.Api.Responses.Opponent> opponentDtos = new LinkedList<>();
        List<Opponent> opponents = opponentManager.GetOpponents();
        for(Opponent opponentModel : opponents){
            opponentDtos.add(OpponentMapper.mapToDto(opponentModel));
        }
        return opponentDtos;
    }

    @PostMapping("/opponents")
    public ResponseEntity<com.David.JiuJitsuJournal.Api.Responses.Opponent> createOpponent(@RequestBody OpponentRequest opponentRequest){
        try {
            Opponent domainOpponent = this.opponentManager.createOpponent(opponentRequest.getName(),
                                                                          opponentRequest.getBeltRank(),
                                                                          opponentRequest.getHeightInInches(),
                                                                          opponentRequest.getWeightInLbs());
            return new ResponseEntity<>(OpponentMapper.mapToDto(domainOpponent), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
