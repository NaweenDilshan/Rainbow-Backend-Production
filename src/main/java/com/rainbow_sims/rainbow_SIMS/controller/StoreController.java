package com.rainbow_sims.rainbow_SIMS.controller;

import com.rainbow_sims.rainbow_SIMS.model.Store;
import com.rainbow_sims.rainbow_SIMS.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class StoreController {

    @Autowired
    private StoreService storeService;

    @PostMapping("/stores")
    public Store createStore(@RequestBody Store store) {
        return storeService.createStore(store);
    }

    @GetMapping("/stores/{id}")
    public ResponseEntity<Store> getStoreById(@PathVariable Long id) {
        return ResponseEntity.ok(storeService.getStoreById(id));
    }

    @GetMapping("/stores")
    public List<Store> getAllStores() {
        return storeService.getAllStores();
    }

    @PutMapping("/stores/{id}")
    public ResponseEntity<Store> updateStore(@PathVariable Long id, @RequestBody Store store) {
        return ResponseEntity.ok(storeService.updateStore(id, store));
    }

    @DeleteMapping("/stores/{id}")
    public ResponseEntity<Void> deleteStore(@PathVariable Long id) {
        storeService.deleteStore(id);
        return ResponseEntity.noContent().build();
    }
}
