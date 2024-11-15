package com.rainbow_sims.rainbow_SIMS.service.Impl;

import com.rainbow_sims.rainbow_SIMS.model.Invoice;
import com.rainbow_sims.rainbow_SIMS.model.InvoiceItem;
import com.rainbow_sims.rainbow_SIMS.model.Product;
import com.rainbow_sims.rainbow_SIMS.repository.InvoiceRepository;
import com.rainbow_sims.rainbow_SIMS.repository.ProductRepository;
import com.rainbow_sims.rainbow_SIMS.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    @Transactional
    public Invoice createInvoice(Invoice invoice) {
        for (InvoiceItem item : invoice.getInvoiceItems()) {
            Optional<Product> productOptional = productRepository.findByProductName(item.getProductName());
            if (productOptional.isPresent()) {
                item.setCategoryName(productOptional.get().getCategoryName());
            } else {
                throw new RuntimeException("Product not found for name: " + item.getProductName());
            }
        }
        return invoiceRepository.save(invoice);
    }

    @Override
    public Invoice getInvoiceById(Long id) {
        return invoiceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Invoice not found with id " + id));
    }

    @Override
    public List<Invoice> getAllInvoices() {
        return invoiceRepository.findAll();
    }

    @Override
    public Invoice updateInvoice(Long id, Invoice invoiceDetails) {
        Invoice invoice = getInvoiceById(id);
        invoice.setInvoiceCode(invoiceDetails.getInvoiceCode());
        invoice.setCustomerName(invoiceDetails.getCustomerName());
        invoice.setCustomerNo(invoiceDetails.getCustomerNo());
        invoice.setBillingAddress(invoiceDetails.getBillingAddress());
        invoice.setInvoiceTotal(invoiceDetails.getInvoiceTotal());
        invoice.setIsActive(invoiceDetails.getIsActive());
        invoice.setInvoiceItems(invoiceDetails.getInvoiceItems());
        return invoiceRepository.save(invoice);
    }

    @Override
    public void deleteInvoice(Long id) {
        invoiceRepository.deleteById(id);
    }
    @Override
    @Transactional
    public Invoice updateInvoiceStatus(Long id, Boolean isActive) {
        Invoice invoice = getInvoiceById(id);
        invoice.setIsActive(isActive);
        return invoiceRepository.save(invoice);
    }
}
