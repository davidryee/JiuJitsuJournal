package com.David.JiuJitsuJournal.Data.Repository;

import com.David.JiuJitsuJournal.Data.Entities.Opponent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OpponentRepository extends JpaRepository<Opponent, Long> {

}
