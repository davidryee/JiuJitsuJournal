package com.David.JiuJitsuJournal.data.specification;

import com.David.JiuJitsuJournal.data.entities.Opponent;
import com.David.JiuJitsuJournal.data.entities.User;
import org.springframework.data.jpa.domain.Specification;

public class OpponentSpecification {
    public static Specification<Opponent> withName(String name) {
        return (root, query, cb) -> name == null ? null : cb.like(root.get("name"), '%' + name + '%');
    }

    public static Specification<Opponent> withBeltRank(Integer beltRank) {
        return (root, query, cb) -> beltRank == null ? null : cb.equal(root.get("beltRank"), beltRank);
    }

    public static Specification<Opponent> withUser(User user) {
        return (root, query, cb) -> user == null ? null : cb.equal(root.get("user"), user);
    }

    public static Specification<Opponent> withId(Long id) {
        return (root, query, cb) -> id == null ? null : cb.equal(root.get("id"), id);
    }
}
