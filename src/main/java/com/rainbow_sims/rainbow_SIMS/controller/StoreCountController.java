package com.rainbow_sims.rainbow_SIMS.controller;

import com.rainbow_sims.rainbow_SIMS.service.StoreCountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class StoreCountController {

    @Autowired
    private StoreCountService storeCountService;

    // Get Total Quantity by Category Name
    @GetMapping("/total-quantity/category")
    public ResponseEntity<Map<String, Integer>> getTotalQuantityByCategory() {
        Map<String, Integer> totalQuantityByCategory = storeCountService.getTotalQuantityByCategory();
        return ResponseEntity.ok(totalQuantityByCategory);
    }

    // Get Total Quantity by Product Name
    @GetMapping("/total-quantity/product")
    public ResponseEntity<Map<String, Integer>> getTotalQuantityByProduct() {
        Map<String, Integer> totalQuantityByProduct = storeCountService.getTotalQuantityByProduct();
        return ResponseEntity.ok(totalQuantityByProduct);
    }
}



