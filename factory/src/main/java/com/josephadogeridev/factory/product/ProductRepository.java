package com.josephadogeridev.factory.product;


//import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;


@Repository
public interface ProductRepository extends ListCrudRepository<Product, Long> {
    // You can add custom query methods here if needed
    @Query("SELECT p FROM Product p WHERE p.name=?1")
    Product findByProductName(String name);
    @Query("SELECT p FROM Product p WHERE p.name=?1")
    Optional<Product> findByName(String name);


}