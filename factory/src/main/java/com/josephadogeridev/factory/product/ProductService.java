package com.josephadogeridev.factory.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

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

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ResponseEntity<?> createProduct(Product product){
        System.out.println("inside create product");
        Optional<Product> productOptional = productRepository.findByName(product.getName());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("Email already exists");
        }
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
