package com.josephadogeridev.factory.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDateTime;
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

    public List<Product> findAllProducts() {

        System.out.println(productRepository.findAll());
        return productRepository.findAll();
    }

    public ResponseEntity<?> findProductById(Long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            responseBody = new HashMap<>();
            responseBody.put("message", "No product found with id: " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<?> deleteProduct(Long id) {
         Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            responseBody = new HashMap<>();
            responseBody.put("message", "No product with id: " + id);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }
        productRepository.deleteById(id);
        responseBody = new HashMap<>();
        responseBody.put("message", "Successfully deleted product with id: " + id);
        return new ResponseEntity<>(responseBody, HttpStatus.ACCEPTED);
    }

    public ResponseEntity<?> deleteAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            responseBody = new HashMap<>();
            responseBody.put("message", "No products found");
            return ResponseEntity.ok(responseBody);
        }

        productRepository.deleteAll(products);
        responseBody = new HashMap<>();
        responseBody.put("message", "Successfully deleted all products");
        return ResponseEntity.ok(responseBody);
    }

    public ResponseEntity<?> createProduct(Product product){
        Optional<Product> productOptional = productRepository.findByProductName(product.getName());
        if (productOptional.isPresent()) {
            throw new IllegalStateException("product name already exists");
        }
        product.setCreatedDate(LocalDateTime.now());
        product.setLastModifiedDate(LocalDateTime.now());
        productRepository.save(product);

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    public ResponseEntity<?> updateProduct(Long productId, Product product) {
        Product oldProduct = productRepository.findById(productId).orElse(null);

        if (oldProduct == null) {
            responseBody = new HashMap<>();
            responseBody.put("message", "No product found with id: " + productId);
            return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
        }

        if(product.getName() != null) {
            Product distinctProduct = productRepository.findOtherDistinctProductName((productId), product.getName());
            if (distinctProduct != null) {
                responseBody = new HashMap<>();
                responseBody.put("message", "name already taken by another Product: " + product.getName());
                return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
            }

            Product repoProduct = productRepository.findByProductName(product.getName()).orElse(null);
            if (repoProduct != null) {
                responseBody = new HashMap<>();
                responseBody.put("message", "Cannot update product name with same name! name: " + product.getName());
                return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
            }
            oldProduct.setName(product.getName());

        }
        if(product.getPrice() != null) {
            Product repoProduct = productRepository.findByProductPrice(productId, product.getPrice()).orElse(null);
            if (repoProduct != null) {
                responseBody = new HashMap<>();
                responseBody.put("message", "Cannot update product price with same price! price: " + product.getPrice());
                return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
            }
            oldProduct.setPrice(product.getPrice());

        }
        if(product.getDescription() != null ) {


            oldProduct.setDescription(product.getDescription());

        }
        oldProduct.setId(productId);
        oldProduct.setLastModifiedDate(LocalDateTime.now());
        System.out.println("product to modify with new fields: " + oldProduct.toString());

        Product modifiedProduct = productRepository.save(oldProduct);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(modifiedProduct);

    }
}
