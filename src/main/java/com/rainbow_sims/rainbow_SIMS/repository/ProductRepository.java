package com.rainbow_sims.rainbow_SIMS.repository;

import com.rainbow_sims.rainbow_SIMS.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductName(String productName);
    @Query("SELECT MAX(p.productId) FROM Product p")
    Optional<String> findMaxProductId();
}

