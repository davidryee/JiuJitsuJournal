package com.David.JiuJitsuJournal.data.repository;

import java.util.Optional;

import com.David.JiuJitsuJournal.data.entities.Role;
import com.David.JiuJitsuJournal.data.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleEnum name);
}