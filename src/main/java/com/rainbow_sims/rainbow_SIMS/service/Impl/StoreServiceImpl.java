package com.rainbow_sims.rainbow_SIMS.service.Impl;

import com.rainbow_sims.rainbow_SIMS.model.Product;
import com.rainbow_sims.rainbow_SIMS.model.Store;
import com.rainbow_sims.rainbow_SIMS.model.StoreCount;
import com.rainbow_sims.rainbow_SIMS.repository.CategoryRepository;
import com.rainbow_sims.rainbow_SIMS.repository.ProductRepository;
import com.rainbow_sims.rainbow_SIMS.repository.StoreCountRepository;
import com.rainbow_sims.rainbow_SIMS.repository.StoreRepository;
import com.rainbow_sims.rainbow_SIMS.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StoreServiceImpl implements StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreCountRepository storeCountRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Store createStore(Store store) {
        // Retrieve the product by name
        Optional<Product> productOptional = productRepository.findByProductName(store.getProductName());
        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found: " + store.getProductName());
        }

        // Get the product and set productId and categoryId in store
        Product product = productOptional.get();
        store.setProductId(product.getProductId());
        store.setCategoryId(product.getCategoryId());

        // Save the store entry
        Store savedStore = storeRepository.save(store);

        // Fetch categoryName using categoryId
        String categoryName = product.getCategoryName();

        // Save to StoreCount
        StoreCount storeCount = new StoreCount();
        storeCount.setProductName(store.getProductName());
        storeCount.setCategoryName(categoryName);
        storeCount.setQuantity(store.getQuantity());
        storeCount.setStoreType(store.getStoreType());
        storeCountRepository.save(storeCount);

        return savedStore;
    }

    @Override
    public Store getStoreById(Long id) {
        return storeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Store entry not found with id " + id));
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }

    @Override
    public Store updateStore(Long id, Store storeDetails) {
        Store store = getStoreById(id);
        store.setQuantity(storeDetails.getQuantity());
        store.setUnitPrice(storeDetails.getUnitPrice());
        store.setStoreType(storeDetails.getStoreType());

        // Update StoreCount entry as well
        StoreCount storeCount = new StoreCount();
        storeCount.setProductName(store.getProductName());
        storeCount.setCategoryName(categoryRepository.findByCategoryId(store.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found")).getCategoryName());
        storeCount.setQuantity(storeDetails.getQuantity());
        storeCount.setStoreType(storeDetails.getStoreType());
        storeCountRepository.save(storeCount);

        return storeRepository.save(store);
    }

    @Override
    public void deleteStore(Long id) {
        storeRepository.deleteById(id);
    }
}
