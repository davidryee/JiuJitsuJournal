package com.David.JiuJitsuJournal.data.repository;

import com.David.JiuJitsuJournal.data.entities.BeltRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeltRankRepository extends JpaRepository<BeltRank, Long> {
}
