package com.josephadogeridev.factory.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    public Product saveEntity(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllEntities() {
        return productRepository.findAll();
    }

    public Product findEntityById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ResponseEntity<?> createProduct(@RequestBody Product product){

        productRepository.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
