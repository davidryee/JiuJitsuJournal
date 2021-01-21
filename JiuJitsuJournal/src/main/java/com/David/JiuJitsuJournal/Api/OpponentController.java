package com.David.JiuJitsuJournal.Api;

import com.David.JiuJitsuJournal.Api.Mappers.OpponentMapper.OpponentMapper;
import com.David.JiuJitsuJournal.Domain.Models.Opponent;
import com.David.JiuJitsuJournal.Domain.OpponentManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class OpponentController {

    private OpponentManager opponentManager;
//    public OpponentController(OpponentManager opponentManager){
//        this.opponentManager = opponentManager;
//    }
    public OpponentController(){
        opponentManager = new OpponentManager();
    }
    @GetMapping("/opponents")
    public Iterable<com.David.JiuJitsuJournal.Api.Responses.Opponent> getOpponents() {

        List<com.David.JiuJitsuJournal.Api.Responses.Opponent> opponentDtos = new LinkedList<>();
        List<Opponent> opponents = opponentManager.GetOpponents();
        for(Opponent opponentModel : opponents){
            opponentDtos.add(OpponentMapper.MapToDto(opponentModel));
        }
        return opponentDtos;
    }
}
