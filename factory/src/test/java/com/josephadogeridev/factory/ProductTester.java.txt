package com.josephadogeridev.factory;//package com.josephadogeridev.factory;
//
//import com.josephadogeridev.factory.product.Product;
//import com.josephadogeridev.factory.product.ProductRepository;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Import;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//@SpringBootTest
//@Testcontainers
//@Import(MySQLDBTestConfig.class)   // Import the configuration
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class ProductTester  {
//
//    @Autowired
//    private ProductRepository productRepository;
//    @BeforeEach
//    void setup() {
//
//    }
//
//    @Test
//    void shouldFindActiveDiscountedProducts() {
//        // Arrange: Create products with mixed discount conditions
//        List<Product> productList = productRepository.findAll();  // Clears all previous data before each test
//
//        System.out.println(productList);
//    }
//
//}

public  class ProductTester {
}