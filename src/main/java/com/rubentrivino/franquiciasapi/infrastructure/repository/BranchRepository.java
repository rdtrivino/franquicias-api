package com.rubentrivino.franquiciasapi.infrastructure.repository;

import com.rubentrivino.franquiciasapi.domain.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BranchRepository extends JpaRepository<Branch, Long> {
}
