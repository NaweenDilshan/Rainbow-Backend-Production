package com.rainbow_sims.rainbow_SIMS.service;

import com.rainbow_sims.rainbow_SIMS.model.Store;
import java.util.List;

public interface StoreService {
    Store createStore(Store store);
    Store getStoreById(Long id);
    List<Store> getAllStores();
    Store updateStore(Long id, Store storeDetails);
    void deleteStore(Long id);
}

