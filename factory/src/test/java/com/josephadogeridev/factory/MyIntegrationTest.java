package com.josephadogeridev.factory;

import com.josephadogeridev.factory.product.Product;
import com.josephadogeridev.factory.product.ProductRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.MountableFile;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@Testcontainers
@Transactional
class MyIntegrationTest {


    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine")
            .withCopyFileToContainer(MountableFile.forClasspathResource("import.sql"),
                    "/docker-entrypoint-initdb.d/");

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver"); // Explicitly set driver
    }

    @BeforeAll
    static void startContainer() {
        System.out.println("Starting container************************");


        System.setProperty("postgres.database.name", postgres.getDatabaseName());
        postgres.start();
    }
    @BeforeEach
    void init() {
    }

    @Autowired
    private ProductRepository productRepository;

    @AfterAll
    static void stopContainer() {
        System.out.println("Stopping container");
        postgres.stop();
    }

    @Test
    void contextLoads() {
        List<Product> products = productRepository.findAll();
        System.out.println(products);
        // Your test logic here
    }

    @Test
    void contextLoads2() {
        List<Product> products = productRepository.findAll();
        System.out.println(products);
        // Your test logic here
    }

    @Test
    void shouldSaveAndFindEntity() throws Exception{
        Product product =  new Product();
        product.setName("test test");
        product.setDescription("This is a test");
        product.setPrice(200.25);
        product.setLastModifiedDate(LocalDateTime.now());
        product.setCreatedDate(LocalDateTime.now());
        product.setId(8L);
        productRepository.save(product);
//        String sql = "INSERT INTO my_table (id, name) VALUES (1, 'TestName')";
//        jdbcTemplate.update(sql);

        List<Product> products = productRepository.findAll();
        System.out.println("products list size: " + products);
        //Assertions.assertNotNull(foundProduct);
        //assertThat(foundProduct).isEqualTo("Test Name");
    }
}



//package com.josephadogeridev.factory;
//
//import com.josephadogeridev.factory.product.Product;
//import com.josephadogeridev.factory.product.ProductRepository;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import java.util.List;
//
//@SpringBootTest
//@Testcontainers
//class MyIntegrationTest {
//
//    @Container
//    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.0.28")
//            .withDatabaseName("testdb")
//            .withUsername("testuser")
//            .withPassword("testpass")
//            .withInitScript("import.sql"); // Initialize with your schema.sql;
//
//    @DynamicPropertySource
//    static void registerDynamicProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mysqlContainer::getUsername);
//        registry.add("spring.datasource.password", mysqlContainer::getPassword);
//        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
//        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop"); // Or update
//        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.MySQL8Dialect");
//    }
//
//    @Autowired
//    private ProductRepository productRepository;
//
//    @Test
//    void contextLoads() {
//        // Your test logic here
//        List<Product> products = productRepository.findAll();
//        System.out.println(products);
//
//
//    }
//}

//.withCopyFileToContainer(MountableFile.forClasspathResource("schema.sql"),
//                              "/docker-entrypoint-initdb.d/");
