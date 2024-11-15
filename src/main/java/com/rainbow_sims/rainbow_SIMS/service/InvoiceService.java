package com.rainbow_sims.rainbow_SIMS.service;

import com.rainbow_sims.rainbow_SIMS.model.Invoice;
import java.util.List;

public interface InvoiceService {
    Invoice createInvoice(Invoice invoice);
    Invoice getInvoiceById(Long id);
    List<Invoice> getAllInvoices();
    Invoice updateInvoice(Long id, Invoice invoice);
    void deleteInvoice(Long id);
    Invoice updateInvoiceStatus(Long id, Boolean isActive);
}
