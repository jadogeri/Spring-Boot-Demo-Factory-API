package com.josephadogeridev.factory;

import com.josephadogeridev.factory.product.Product;
import com.josephadogeridev.factory.product.ProductRepository;
import com.josephadogeridev.factory.product.ProductService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Activates application-test.properties
@DisplayName("Product Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

class ProductServiceTest {

    @Autowired
    private ProductRepository productRepository; // Assuming you have a Spring Data JPA repository
    @Autowired
    private ProductService productService;

    @Test
    @Order(1)
    void findAllProductsTest() {
        //Arrange
        List<Product> allProducts = null;

        //Act
        allProducts = productService.findAllProducts();

        //Assert
        assertNotNull(allProducts);
       // assertThat(allProducts.size()).isGreaterThan(0);
    }

    @Test
    @Order(2)
    void deleteSingleProductTest() {
        //Arrange
        ResponseEntity<?> deletedProductResponse   = null;

        //Act
        deletedProductResponse = productService.deleteProduct(8L);
        System.out.println("product found : " + deletedProductResponse);
        // Get the status code
        @SuppressWarnings("unchecked")
        HttpStatusCode status = deletedProductResponse.getStatusCode();
        System.out.println("Status: " + status); // Output: Status: 202 ACCEPTED

        // Get the body
        // In a real application, you would cast to the expected type
        @SuppressWarnings("unchecked")
        Map<String, String> body = (Map<String, String>) deletedProductResponse.getBody();
        String message = null;
        if (body != null) {
            message = body.get("message");
            System.out.println("Message: " + message); // Output: Message: Successfully deleted product with id: 7
        }

        //Assert
        assertEquals(status.toString().trim(), "202 ACCEPTED".trim());
        assertEquals(message, "Successfully deleted product with id: 8");
    }

    @Test
    @Order(2)
    void findSingleProductTest() {
        //Arrange
        ResponseEntity<Product> singleProductResponse   = null;
        Product singleProduct = null;
        //Act
        singleProductResponse = ( ResponseEntity <Product>) productService.findProductById(7L);
        System.out.println("singleProductResponse: " + singleProductResponse);

        HttpStatusCode status = singleProductResponse.getStatusCode();
        System.out.println("Status: " + status);

        // Get the body
        // In a real application, you would cast to the expected type
        @SuppressWarnings("unchecked")
        Product product =  singleProductResponse.getBody();
        System.out.println("product: " + product);

        //Assert
        assertNotNull(singleProductResponse);
        assertEquals(status.toString().trim(), "200 OK");
        assertThat(product instanceof Product && product != null);
        assertEquals(product.getName().trim().toLowerCase(), "steel");
        assertThat(product.getPrice()).isGreaterThan(0);
        assertThat(product.getDescription().length()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void deleteAllProductsTest() {

        //Arrange
        List<Product> allProducts;

        // Act
        productRepository.deleteAll();
        allProducts = productRepository.findAll();
        System.out.println("saved products : " + allProducts);


        //Assert
        assertThat(allProducts).isEmpty();
        assertEquals(allProducts.size(), 0);

    }





//    @Test
////    @Order(5)
//    void ShouldDeleteSingleProduct() {
//        //Arrange
//        List<Product> oldProductsList = productRepository.findAll();
//        List<Product> newProductsList;
//        Product oldProduct = null;
//        Long productId = 7L;
//
//        // Act
//        productRepository.deleteById(productId);
//        newProductsList = productRepository.findAll();
//
//        //Assert
//        for (Product p : newProductsList) {
//            if (p.getId().equals(productId)) {
//                oldProduct = p;
//            }
//
//        }
//        assertNull(oldProduct);
//        assertNotEquals(oldProductsList.size(), newProductsList.size());
//        assertEquals(oldProductsList.size(), newProductsList.size() + 1);
//        assertThat(oldProductsList.size()).isGreaterThan(newProductsList.size());
//
//
//    }

}