package com.David.JiuJitsuJournal.data.specification;

import com.David.JiuJitsuJournal.data.entities.Match;
import com.David.JiuJitsuJournal.data.entities.Opponent;
import com.David.JiuJitsuJournal.data.entities.User;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.List;

public class MatchSpecification {
    public static Specification<Match> withUser(User user) {
        return (root, query, cb) -> user == null ? null : cb.equal(root.get("user"), user);
    }

    public static Specification<Match> withDate(LocalDate date) {
        return ((root, query, cb) -> date == null ? null : cb.equal(root.get("matchDate"), date));
    }

    public static Specification<Match> withOpponents(List<Opponent> opponents) {
        return ((root, query, cb) -> opponents == null ? null : cb.isTrue(root.get("opponent").in(opponents)));
    }
}
