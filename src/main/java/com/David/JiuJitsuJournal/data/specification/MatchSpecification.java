package com.David.JiuJitsuJournal.data.specification;

import com.David.JiuJitsuJournal.data.entities.Match;
import com.David.JiuJitsuJournal.data.entities.Opponent;
import com.David.JiuJitsuJournal.data.entities.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class MatchSpecification {
    public Specification<Match> withUser(User user) {
        return (root, query, cb) -> user == null ? null : cb.equal(root.get("user"), user);
    }

    public Specification<Match> withDate(LocalDate date) {
        return ((root, query, cb) -> date == null ? null : cb.equal(root.get("matchDate"), date));
    }

    public Specification<Match> withOpponents(List<Opponent> opponents) {
        return ((root, query, cb) -> opponents == null ? null : cb.isTrue(root.get("opponent").in(opponents)));
    }

    public Specification<Match> withId(Long id) {
        return ((root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id));
    }
}
