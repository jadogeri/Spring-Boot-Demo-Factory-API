package com.josephadogeridev.factory.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/factories")
//@XmlRootElement(name = "employee")

public class ProductController {

    private final ProductService productService;

    //dummy list for testing routes will be deleted
    List<Product> productList;

    @Autowired
    ProductController(ProductService productService){
        this.productService = productService;
        this.productList = new ArrayList<Product>();
        this.productList.add(new Product("oil","oil refinery",20.5));
        this.productList.add(new Product("rack","rack list refinery",20.5));
        this.productList.add(new Product("jetfuel","jet fuel refinery",20.5));
        this.productList.add(new Product("petrol","car oil refinery",20.5));
    }

//    @PostMapping
//    public ResponseEntity<?> creatProduct(@RequestBody Product product) {
//        // Logic to save a new user
//        return productService.createProduct(product);
//    }

    @GetMapping
    public List<Product> getEmployees() {

        return this.productList;

    }
    @GetMapping(path = "{productId}")
    public List<Product> getEmployee() {

        return this.productList;

    }
    @PostMapping
    public List<Product> registerNewEmployee(@RequestBody Product product) {
        return this.productList;

    }

    @DeleteMapping(path = "{productId}")
    public List<Product> deleteEmployee(@PathVariable("productId") Long productId) {
        return this.productList;

    }

    @PutMapping(path = "{productId}")
    public List<Product> updateEmployee(
            @PathVariable("productId") Long employeeId,
            @RequestBody(required = false) Product product) {
        return this.productList;

    }
}
