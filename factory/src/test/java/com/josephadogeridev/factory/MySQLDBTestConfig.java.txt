package com.josephadogeridev.factory;//package com.josephadogeridev.factory;
//
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.MySQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//
//@TestConfiguration
//@Testcontainers
//public class MySQLDBTestConfig {
//
//    @Container
//    private static final MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:8.0.33")
//            .withDatabaseName("test_db")
//            .withUsername("test")
//            .withPassword("test");
//
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        //mySQLContainer.start();  // Start the container before setting properties
//        registry.add("spring.datasource.url", mySQLContainer::getJdbcUrl);
//        registry.add("spring.datasource.username", mySQLContainer::getUsername);
//        registry.add("spring.datasource.password", mySQLContainer::getPassword);
//        registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
//    }
//
//    // This automatically binds the container properties to Spring Boot, and stopping is still handled automatically.
//    // A modern alternative to using System.setProperty and HikariDataSource is @DynamicPropertySource
//
//}

public class MySQLDBTestConfig
{
}