package com.example.TechMarket.Product;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductRepository extends CrudRepository<Product, Long> {
    @Query(nativeQuery = true, value = "SELECT product_id, product_condition, description, is_available, price, title, category_category_id FROM products WHERE is_available = TRUE")
    public List<Product> findAvailableProducts();
}