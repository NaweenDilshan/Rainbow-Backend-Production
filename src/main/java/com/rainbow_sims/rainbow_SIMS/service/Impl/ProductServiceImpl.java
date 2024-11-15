package com.rainbow_sims.rainbow_SIMS.service.Impl;

import com.rainbow_sims.rainbow_SIMS.model.Category;
import com.rainbow_sims.rainbow_SIMS.model.Product;
import com.rainbow_sims.rainbow_SIMS.repository.CategoryRepository;
import com.rainbow_sims.rainbow_SIMS.repository.ProductRepository;
import com.rainbow_sims.rainbow_SIMS.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Product createProduct(Product product) {
        if (product.getCategoryName() == null || product.getCategoryName().isEmpty()) {
            throw new RuntimeException("Category name is required");
        }
        product.setProductId(generateProductId());
        Category category = categoryRepository.findByCategoryName(product.getCategoryName())
                .orElseThrow(() -> new RuntimeException("Category not found: " + product.getCategoryName()));
        product.setCategoryId(category.getCategoryId());
        return productRepository.save(product);
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Long id, Product productDetails) {
        Product product = getProductById(id);
        product.setProductName(productDetails.getProductName());
        product.setBrand(productDetails.getBrand());
        product.setIsActive(productDetails.getIsActive());
        if (!product.getCategoryName().equals(productDetails.getCategoryName())) {
            Category category = categoryRepository.findByCategoryName(productDetails.getCategoryName())
                    .orElseThrow(() -> new RuntimeException("Category not found: " + productDetails.getCategoryName()));
            product.setCategoryName(category.getCategoryName());
            product.setCategoryId(category.getCategoryId());
        }
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    private String generateProductId() {
        Optional<String> maxProductId = productRepository.findMaxProductId();
        int nextId = maxProductId.map(id -> Integer.parseInt(id.substring(2)) + 1).orElse(1);
        return String.format("pt%05d", nextId);
    }
}

