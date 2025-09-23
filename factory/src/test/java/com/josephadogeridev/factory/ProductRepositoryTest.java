package com.josephadogeridev.factory;

import com.josephadogeridev.factory.product.Product;
import com.josephadogeridev.factory.product.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test") // Activates application-test.properties
@DisplayName("Product Repository Integration Test : " + "😱")
class ProductRepositoryTest {


    @Autowired
    private ProductRepository productRepository; // Assuming you have a Spring Data JPA repository

    @Test
//    @Order(2)
    void shouldSaveAndFindProduct() {
        Product product = new Product();
//        product.setId(7L);
        product.setName("Test Product");
        product.setDescription("Test Product");
        product.setPrice(200.0);

        Product savedProduct = productRepository.save(product);
        List<Product> allProducts = productRepository.findAll();

        System.out.println("saved products : " + allProducts);

        assertThat(savedProduct.getId()).isNotNull();
        assertThat(productRepository.findById(product.getId())).isPresent();
    }

//    @Test
////    @Order(10)
//    void ShouldDeleteAllProducts() {
//        //Arrange
//        List<Product> allProducts;
//
//        // Act
//        productRepository.deleteAll();
//        allProducts = productRepository.findAll();
//        System.out.println("saved products : " + allProducts);
//
//
//        //Assert
//        assertThat(allProducts).isEmpty();
//        assertEquals(allProducts.size(), 0);
//
//    }


    @Test
//    @Order(4)
    void ShouldFindProductById() {
        //Arrange
        Optional<Product> product;

        //Act
        product = productRepository.findById(7L);
        System.out.println("product found : " + product);

        //Assert
        assertThat(product).isPresent();
        assertThat(product.get().getName()).isNotNull();
        assertThat(product.get().getDescription()).isNotNull();
        assertThat(product.get().getPrice()).isNotNull();
    }

    @Test
    void ShouldFindAndUpdateProduct() {
        //Arrange
        Product oldProduct = new Product("old name", "old description", 20.0, LocalDateTime.now(), LocalDateTime.now());
        Product updatedProduct = null;
        Product savedProduct = null;

        //Act
        productRepository.save(oldProduct);
        Optional<Product> optionalProduct = productRepository.findByProductName("old name");

        if (optionalProduct.isPresent()) {
            oldProduct = optionalProduct.get();
        }
        System.out.println("old product found : " + oldProduct);

        updatedProduct = oldProduct;
        updatedProduct.setPrice(200.0);
        updatedProduct.setName("new product");
        updatedProduct.setDescription("new description");
        updatedProduct.setLastModifiedDate(LocalDateTime.now());

        savedProduct = productRepository.save(updatedProduct);
        System.out.println("updated product found : " + savedProduct);

        //Assert
        assertNotNull(savedProduct);
        assertThat(savedProduct.getName()).isNotNull();
        assertEquals(savedProduct.getName(), updatedProduct.getName());
        assertEquals(savedProduct.getDescription(), updatedProduct.getDescription());
        assertEquals(savedProduct.getLastModifiedDate(), updatedProduct.getLastModifiedDate());
    }

    @Test
//    @Order(1)
    void ShouldGetAllProducts() {
        //Arrange
        List<Product> productsList;

        //Act
        productsList = productRepository.findAll();
        System.out.println("product found : " + productsList);

        //Assert
        assertThat(productsList).isNotNull();
        assertThat(productsList.size()).isGreaterThan(0);
    }

    @Test
//    @Order(5)
    void ShouldDeleteSingleProduct() {
        //Arrange
        List<Product> oldProductsList = productRepository.findAll();
        List<Product> newProductsList;
        Product oldProduct = null;
        Long productId = 7L;

        // Act
        productRepository.deleteById(productId);
        newProductsList = productRepository.findAll();

        //Assert
        for (Product p : newProductsList) {
            if (p.getId().equals(productId)) {
                oldProduct = p;
            }

        }
        assertNull(oldProduct);
        assertNotEquals(oldProductsList.size(), newProductsList.size());
        assertEquals(oldProductsList.size(), newProductsList.size() + 1);
        assertThat(oldProductsList.size()).isGreaterThan(newProductsList.size());


    }

}