package com.rainbow_sims.rainbow_SIMS.repository;

import com.rainbow_sims.rainbow_SIMS.model.StoreCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@EnableJpaRepositories
@Repository
public interface StoreCountRepository extends JpaRepository<StoreCount, Long> {
    // Add custom queries if needed
    @Query("SELECT COALESCE(SUM(sc.quantity), 0) FROM StoreCount sc WHERE sc.categoryName = :categoryName AND sc.productName = :productName AND sc.storeType = 'IN'")
    Integer getTotalInQuantity(@Param("categoryName") String categoryName, @Param("productName") String productName);

    @Query("SELECT COALESCE(SUM(sc.quantity), 0) FROM StoreCount sc WHERE sc.categoryName = :categoryName AND sc.productName = :productName AND sc.storeType = 'OUT'")
    Integer getTotalOutQuantity(@Param("categoryName") String categoryName, @Param("productName") String productName);

}
