package com.rainbow_sims.rainbow_SIMS.service;

import com.rainbow_sims.rainbow_SIMS.model.Product;
import java.util.List;

public interface ProductService {
    Product createProduct(Product product);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    Product updateProduct(Long id, Product product);
    void deleteProduct(Long id);
}
