package com.rainbow_sims.rainbow_SIMS.service;

import java.util.Map;

public interface StoreCountService {
    Map<String, Integer> getTotalQuantityByCategory();
    Map<String, Integer> getTotalQuantityByProduct();
}

