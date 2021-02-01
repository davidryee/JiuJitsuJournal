package com.David.JiuJitsuJournal.Api;

import com.David.JiuJitsuJournal.Api.Mappers.OpponentMapper;
import com.David.JiuJitsuJournal.Api.Requests.OpponentRequest;
import com.David.JiuJitsuJournal.Domain.Models.Opponent;
import com.David.JiuJitsuJournal.Domain.OpponentManager;
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
    public Iterable<com.David.JiuJitsuJournal.Api.Responses.Opponent> getOpponents(@RequestParam(required = false) String name,
                                                                                   @RequestParam(required = false) Integer beltRank) {
        List<com.David.JiuJitsuJournal.Api.Responses.Opponent> opponentDtos = new LinkedList<>();
        List<Opponent> opponents = opponentManager.GetOpponents(name, beltRank);
        for(Opponent opponentModel : opponents){
            opponentDtos.add(OpponentMapper.mapToDto(opponentModel));
        }
        return opponentDtos;
    }

    @PostMapping("/opponents")
    public ResponseEntity createOpponent(
            @Valid @RequestBody OpponentRequest opponentRequest){
        try {
            Opponent domainOpponent = this.opponentManager.createOpponent(opponentRequest.getName(),
                                                                          opponentRequest.getBeltRank(),
                                                                          opponentRequest.getHeightInInches(),
                                                                          opponentRequest.getWeightInLbs());
            return new ResponseEntity<>(OpponentMapper.mapToDto(domainOpponent), HttpStatus.OK);
        }
        catch(ConstraintViolationException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
        catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
