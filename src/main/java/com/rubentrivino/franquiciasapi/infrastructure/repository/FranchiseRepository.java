package com.rubentrivino.franquiciasapi.infrastructure.repository;

import com.rubentrivino.franquiciasapi.domain.Franchise;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FranchiseRepository extends JpaRepository<Franchise, Long> {
}
