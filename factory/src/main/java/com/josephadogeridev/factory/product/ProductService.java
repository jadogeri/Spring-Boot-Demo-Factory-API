package com.josephadogeridev.factory.product;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ProductService {

    public ProductService(){

    }

    public ResponseEntity<?> createProduct(@RequestBody Product product){
        if(product == null||product.getName()==null||product.getDescription()==null||product.getPrice() == null){
            //return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Product name, description and price are required");
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }
}
