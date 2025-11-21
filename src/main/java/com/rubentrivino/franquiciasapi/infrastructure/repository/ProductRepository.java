package com.rubentrivino.franquiciasapi.infrastructure.repository;

import com.rubentrivino.franquiciasapi.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
