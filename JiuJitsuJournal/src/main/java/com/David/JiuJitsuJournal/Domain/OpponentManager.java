package com.David.JiuJitsuJournal.Domain;

import com.David.JiuJitsuJournal.Domain.Models.Opponent;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

@Component
public class OpponentManager {
    public List<Opponent> GetOpponents(){
        List<Opponent> opponents = new LinkedList<>();
        opponents.add(new Opponent("Royce Gracie", BeltRankEnum.BLACK));
        opponents.add(new Opponent("Chuck Norris", BeltRankEnum.BROWN));

        return opponents;
    }
}
