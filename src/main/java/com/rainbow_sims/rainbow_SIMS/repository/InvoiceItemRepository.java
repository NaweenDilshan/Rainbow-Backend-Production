package com.rainbow_sims.rainbow_SIMS.repository;

import com.rainbow_sims.rainbow_SIMS.model.InvoiceItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceItemRepository extends JpaRepository<InvoiceItem, Long> {
}