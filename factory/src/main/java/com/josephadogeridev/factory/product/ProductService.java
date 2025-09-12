package com.josephadogeridev.factory.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    Map<String, String> responseBody ;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public List<Product> findAllProducts() {

        return productRepository.findAll();
    }

    public Product findProductsByName(String name) {
        return productRepository.findByProductName(name);
    }


    public Product findProductById(Long id) {

        return productRepository.findById(id).orElse(null);
    }

    public ResponseEntity<?> deleteProduct(Long id) {
        System.out.println("product delete id: " + id);
        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
        responseBody = new HashMap<>();
        responseBody.put("message", "Successfully deleted product with id: " + id);
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<?> createProduct(Product product){
        Optional<Product> productOptional = productRepository.findByName(product.getName());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("product name already exists");
        }
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
