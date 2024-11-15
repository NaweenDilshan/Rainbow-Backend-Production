package com.rainbow_sims.rainbow_SIMS.controller;

import com.rainbow_sims.rainbow_SIMS.model.Invoice;
import com.rainbow_sims.rainbow_SIMS.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class InvoiceController {

    @Autowired
    private InvoiceService invoiceService;

    @PostMapping("/invoices")
    public Invoice createInvoice(@RequestBody Invoice invoice) {
        return invoiceService.createInvoice(invoice);
    }

    @GetMapping("/invoices/{id}")
    public ResponseEntity<Invoice> getInvoiceById(@PathVariable Long id) {
        return ResponseEntity.ok(invoiceService.getInvoiceById(id));
    }

    @GetMapping("/invoices")
    public List<Invoice> getAllInvoices() {
        return invoiceService.getAllInvoices();
    }

    @PutMapping("/invoices/{id}")
    public ResponseEntity<Invoice> updateInvoice(@PathVariable Long id, @RequestBody Invoice invoice) {
        return ResponseEntity.ok(invoiceService.updateInvoice(id, invoice));
    }

    @DeleteMapping("/invoices/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.deleteInvoice(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/invoices/{id}/status")
    public ResponseEntity<Invoice> updateInvoiceStatus(@PathVariable Long id, @RequestBody Boolean isActive) {
        // Call a service method to update only the status
        Invoice updatedInvoice = invoiceService.updateInvoiceStatus(id, isActive);
        return ResponseEntity.ok(updatedInvoice);
    }
}
