package com.josephadogeridev.factory.product;


//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
    // You can add custom query methods here if needed
    @Query("SELECT p FROM Product p WHERE p.name=?1")
    Optional<Product> findByProductName(String name);
    @Query("SELECT p FROM Product p WHERE p.id=?1 and p.price=?2")
    Optional<Product> findByProductPrice(Long productId, Double price);
    @Query("SELECT DISTINCT p FROM Product p WHERE p.id!=?1 AND p.name=?2")
    Product findOtherDistinctProductName(Long productId, String name);
}