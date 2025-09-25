package com.josephadogeridev.factory.product;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
    @Query("SELECT p FROM Product p WHERE p.name=?1")
    Optional<Product> findByProductName(String name);
    @Query("SELECT p FROM Product p WHERE p.id=?1 and p.price=?2")
    Optional<Product> findByProductPrice(Long productId, Double price);
    @Query("SELECT DISTINCT p FROM Product p WHERE p.id!=?1 AND p.name=?2")
    Product findOtherDistinctProductName(Long productId, String name);
}