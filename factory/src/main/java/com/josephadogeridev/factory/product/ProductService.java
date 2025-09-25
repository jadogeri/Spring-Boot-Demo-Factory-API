package com.josephadogeridev.factory.product;

import com.github.dockerjava.api.exception.NotFoundException;
import com.josephadogeridev.factory.exceptions.ResourceConflictException;
import com.josephadogeridev.factory.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    HashMap<String, String> responseBody ;


    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAllProducts() {

        System.out.println(productRepository.findAll());
        return productRepository.findAll();
    }


    public ResponseEntity<Product> findProductById(Long id) {

        Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            responseBody = new HashMap<>();

            throw new NotFoundException("No product found with id: " + id);

        }
        return ResponseEntity.ok(product);
    }

    public ResponseEntity<HashMap<String,String>> deleteProduct(Long id) {
         Product product = productRepository.findById(id).orElse(null);

        if (product == null) {
            responseBody = new HashMap<>();
            throw new NotFoundException("No product found with id: " + id);
        }
        productRepository.deleteById(id);
        responseBody = new HashMap<>();
        responseBody.put("message", "Successfully deleted product with id: " + id);
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    public ResponseEntity<HashMap<String,String>> deleteAllProducts() {
        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) {
            responseBody = new HashMap<>();
            throw new ResourceNotFoundException("No products found");
        }
        productRepository.deleteAll(products);
        responseBody = new HashMap<>();
        responseBody.put("message", "Successfully deleted all products");
        return new ResponseEntity<>(responseBody, HttpStatus.OK);
    }

    public ResponseEntity<Product> createProduct(Product product){
        Optional<Product> productOptional = productRepository.findByProductName(product.getName());
        if (productOptional.isPresent()) {
            throw new ResourceConflictException("product name already exists : " + product.getName());
        }
        product.setCreatedDate(LocalDateTime.now());
        product.setLastModifiedDate(LocalDateTime.now());
        productRepository.save(product);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        //return new ResponseEntity<>(product, headers, HttpStatus.CREATED);
        return new ResponseEntity<>(product, HttpStatus.CREATED);
    }

    public ResponseEntity<Product> updateProduct(Long productId, Product product) {
        Product oldProduct = productRepository.findById(productId).orElse(null);

        if (oldProduct == null) {
            throw new ResourceNotFoundException("No product found with id: " + productId);

        }

        if(product.getName() != null) {
            Product distinctProduct = productRepository.findOtherDistinctProductName((productId), product.getName());
            if (distinctProduct != null) {
                throw new ResourceConflictException( "name already taken by another Product: " + product.getName());
            }

            Product repoProduct = productRepository.findByProductName(product.getName()).orElse(null);
            if (repoProduct != null) {
                throw new ResourceConflictException("Cannot update product name with same name! name: " + product.getName());
            }
            oldProduct.setName(product.getName());

        }
        if(product.getPrice() != null) {
            Product repoProduct = productRepository.findByProductPrice(productId, product.getPrice()).orElse(null);
            if (repoProduct != null) {
                throw new ResourceConflictException("Cannot update product price with same price! price: " + product.getPrice());
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

        return ResponseEntity.accepted().body(modifiedProduct);

    }
}
