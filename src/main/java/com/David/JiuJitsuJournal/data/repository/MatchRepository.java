package com.David.JiuJitsuJournal.data.repository;

import com.David.JiuJitsuJournal.data.entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MatchRepository extends JpaRepository<Match, Long>, JpaSpecificationExecutor {
}
