package com.David.JiuJitsuJournal.Data.Repository;

import com.David.JiuJitsuJournal.Data.Entities.BeltRank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BeltRankRepository extends JpaRepository<BeltRank, Long> {
}
