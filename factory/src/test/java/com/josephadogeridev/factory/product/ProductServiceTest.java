package com.josephadogeridev.factory.product;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Activates application-test.properties
@DisplayName("Product Service Test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceTest {

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
        assertThat(allProducts.size()).isGreaterThan(0);
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
        assertEquals(status.toString().trim(), "200 OK".trim());
        assertEquals(message, "Successfully deleted product with id: 8");
    }

    @Test
    @Order(2)
    void findSingleProductTest() {
        //Arrange
        ResponseEntity<Product> singleProductResponse   = null;
        Product singleProduct = null;
        //Act
        singleProductResponse = ( ResponseEntity <Product>) productService.findProductById(9L);
        System.out.println("singleProductResponse: " + singleProductResponse);

        HttpStatusCode status = singleProductResponse.getStatusCode();
        System.out.println("Status: " + status);

        // Get the body
        // In a real application, you would cast to the expected type
        @SuppressWarnings("unchecked")
//        Map<Product> product =  singleProductResponse.getBody();
//        System.out.println("product: " + product);
        Product product = null;

        if (singleProductResponse.getStatusCode().is2xxSuccessful()) {
            product = singleProductResponse.getBody(); // This is how you get the 'product' object
            System.out.println("Product Name: " + product.getName());
            System.out.println("Product Price: " + product.getPrice());
        }
        //Assert
        assertNotNull(singleProductResponse);
        assertEquals(status.toString().trim(), "200 OK");
        assertThat(product instanceof Product && product != null);
        assertEquals(product.getName().trim().toLowerCase(), "lead");
        assertThat(product.getPrice()).isGreaterThan(0);
        assertThat(product.getDescription().length()).isGreaterThan(0);
    }

    @Test
    @Order(3)
    void deleteAllProductsTest() {

        //Arrange
        ResponseEntity<?> deleteResponse = null;
        List<Product> allProducts;
        HashMap map = new HashMap();

        // Act
        deleteResponse = productService.deleteAllProducts();
        allProducts = productService.findAllProducts();
        System.out.println("deleteAllProducts: " + deleteResponse);
        HttpStatusCode status = deleteResponse.getStatusCode();
        System.out.println("Status: " + status);
        if (deleteResponse.getStatusCode().is2xxSuccessful()) {
            map = (HashMap<String, String>) deleteResponse.getBody(); // This is how you get the 'product' object
        }

        //Assert
        assertThat(allProducts).isEmpty();
        assertEquals(allProducts.size(), 0);
        assertEquals(status.toString().trim(), "200 OK");
        assertEquals(allProducts.size(), 0);
        assertEquals(map.get("message"), "Successfully deleted all products");

    }
    @Test
    @Order(3)
    void createProductTest() {

        //Arrange
        Product product = new Product();
        product.setName("Test Create Product");
        product.setDescription("Test Product in ProductServiceTest");
        product.setPrice(1000.0);

        // Act
        ResponseEntity<Product> createResponse = (ResponseEntity<Product>)productService.createProduct(product);
        HttpStatusCode status = createResponse.getStatusCode();
        Product createdProduct = null;

        if (createResponse.getStatusCode().is2xxSuccessful()) {
            createdProduct = createResponse.getBody(); // This is how you get the 'product' object
            System.out.println("Product Name: " + createdProduct.getName());
            System.out.println("Product Price: " + createdProduct.getPrice());
        }


        //Assert
        assertThat(createdProduct).isNotNull();
        assertEquals(status.toString().trim(), "201 CREATED");
        assertThat(product instanceof Product && product != null);
        assertEquals(product.getName().trim().toLowerCase(), "test create product");
        assertThat(product.getPrice()).isGreaterThan(0);
        assertEquals(product.getDescription(), createdProduct.getDescription());
        assertEquals(product.getPrice(), createdProduct.getPrice());
        assertEquals(product.getDescription().length(), createdProduct.getDescription().length());
        assertEquals(product.getName(), createdProduct.getName());

    }

}