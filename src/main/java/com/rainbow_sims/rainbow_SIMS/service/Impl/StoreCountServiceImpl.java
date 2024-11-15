package com.rainbow_sims.rainbow_SIMS.service.Impl;

import com.rainbow_sims.rainbow_SIMS.model.InvoiceItem;
import com.rainbow_sims.rainbow_SIMS.model.StoreCount;
import com.rainbow_sims.rainbow_SIMS.repository.InvoiceItemRepository;
import com.rainbow_sims.rainbow_SIMS.repository.StoreCountRepository;
import com.rainbow_sims.rainbow_SIMS.service.StoreCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class StoreCountServiceImpl implements StoreCountService {

    @Autowired
    private StoreCountRepository storeCountRepository;

    @Autowired
    private InvoiceItemRepository invoiceItemRepository;

    @Override
    public Map<String, Integer> getTotalQuantityByCategory() {
        List<StoreCount> storeCounts = storeCountRepository.findAll();
        Map<String, Integer> totalQuantityByCategory = new HashMap<>();

        // Step 1: Get StoreCount quantities (IN - OUT)
        for (StoreCount storeCount : storeCounts) {
            int quantity = storeCount.getStoreType().equalsIgnoreCase("IN") ?
                    storeCount.getQuantity() : -storeCount.getQuantity();

            totalQuantityByCategory.merge(storeCount.getCategoryName(), quantity, Integer::sum);
        }

        // Step 2: Subtract InvoiceItem quantities for each category
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();
        for (InvoiceItem item : invoiceItems) {
            // Assuming the invoice items have a categoryName field (you can fetch category from Product if needed)
            totalQuantityByCategory.merge(item.getCategoryName(), -item.getQuantity(), Integer::sum);
        }

        return totalQuantityByCategory;
    }

    @Override
    public Map<String, Integer> getTotalQuantityByProduct() {
        List<StoreCount> storeCounts = storeCountRepository.findAll();
        Map<String, Integer> totalQuantityByProduct = new HashMap<>();

        // Step 1: Get StoreCount quantities (IN - OUT)
        for (StoreCount storeCount : storeCounts) {
            int quantity = storeCount.getStoreType().equalsIgnoreCase("IN") ?
                    storeCount.getQuantity() : -storeCount.getQuantity();

            totalQuantityByProduct.merge(storeCount.getProductName(), quantity, Integer::sum);
        }

        // Step 2: Subtract InvoiceItem quantities for each product
        List<InvoiceItem> invoiceItems = invoiceItemRepository.findAll();
        for (InvoiceItem item : invoiceItems) {
            totalQuantityByProduct.merge(item.getProductName(), -item.getQuantity(), Integer::sum);
        }

        return totalQuantityByProduct;
    }
}

