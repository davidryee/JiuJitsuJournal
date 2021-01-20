package com.David.JiuJitsuJournal.Api;

import com.David.JiuJitsuJournal.Domain.BeltRankEnum;
import com.David.JiuJitsuJournal.Domain.Opponent;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@RestController
public class OpponentController {
    @GetMapping("/opponents")
    public Iterable<Opponent> getOpponents() {
        List<Opponent> opponents = new LinkedList<>();

        Opponent opponent1 = new Opponent();
        opponent1.setName("Royce Gracie");
        opponent1.setBeltRank(BeltRankEnum.BLACK);

        opponents.add(opponent1);

        return opponents;
    }
}
