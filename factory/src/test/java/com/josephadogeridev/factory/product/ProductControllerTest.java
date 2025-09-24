package com.josephadogeridev.factory.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ProductController productController;

    @MockitoBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper; // For converting objects to JSON
    /*
    *    List<Book> books = Arrays.asList(
            new Book(1L, "Book 1", "Author 1"),
            new Book(2L, "Book 2", "Author 2")
        );
        when(bookService.findAll()).thenReturn(books);

        // Act & Assert
        mockMvc.perform(get("/api/books")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].title").value("Book 1"))
                .andExpect(jsonPath("$[1].id").value(2L));

        verify(bookService, times(1)).findAll();
        *
        *
        *
        *
        *
            static Stream<Arguments> providePersonData() {
        return Stream.of(
            Arguments.of("Alice", 30, true),
            Arguments.of("Bob", 25, false),
            Arguments.of("Charlie", 40, true)
        );
    }

    @ParameterizedTest
    @MethodSource("providePersonData")
    void testPersonDetails(String name, int age, boolean isActive) {
        assertTrue(age > 0);
        // Further assertions based on name and isActive
    }

        */

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        //MockitoAnnotations.openMocks(this); // Initialize mocks
    }

    static Stream<Arguments> productData() {
        return Stream.of(
                Arguments.of("lead","lead description", 100.0),
                Arguments.of("steel","steel description", 200.0),
                Arguments.of("copper","copper description", 50.0)
        );
    }

    @Test
    //@MethodSource("productList")
    public void getProducts() throws Exception {

        List<Product> productList = Arrays.asList(
                new Product("lead","lead description",100.0, LocalDateTime.now(),LocalDateTime.now()),
                new Product("steel","steel description",150.0, LocalDateTime.now(),LocalDateTime.now()),
                new Product("copper","copper description",50.0, LocalDateTime.now(), LocalDateTime.now()));

        when(productService.findAllProducts()).thenReturn(productList);
        this.mockMvc.perform(get("/api/v1/products")).
                andExpect(status().isOk()).
                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
	            andExpect(jsonPath("$[0].name").value("lead")).
                andExpect(jsonPath("$[0].description").value("lead description")).
                andExpect(jsonPath("$[0].price").value(100.0)).
                andExpect(jsonPath("$[1].name").value("steel")).
                andExpect(jsonPath("$[1].description").value("steel description")).
                andExpect(jsonPath("$[1].price").value(150.0)).
                andExpect(jsonPath("$[2].name").value("copper")).
                andExpect(jsonPath("$[2].description").value("copper description")).
                andExpect(jsonPath("$[2].price").value(50.0));

        verify(productService, times(1)).findAllProducts();
    }

	@ParameterizedTest
    @MethodSource("productData")
	public void getProduct(String name, String description, double price) throws Exception {
        long id = 1L;
        String stringID = Long.toString(id);
        Product product = new Product(id, name,description,price);



        //ResponseEntity<?> mockResponse = ResponseEntity.ok().body(product);
        ResponseEntity<Product> mockResponse = new ResponseEntity<>(product, HttpStatus.OK);

        // 3. Define the behavior of the mock object.
                // `when()` specifies the method call to intercept.
                // `thenReturn()` specifies the value to return when the intercepted method is called.
                // `anyLong()` is a Mockito argument matcher used to match any `long` value.
//                when(productController.getProduct(stringID)).thenReturn(mockResponse);

        //ResponseEntity<?> mockResponse = ResponseEntity.ok().body(product);
//
// Pass the correct argument type (long) to the method call
        when(productService.findProductById(id)).thenReturn(mockResponse);

        // 4. Call the method being tested (the `productController` method that uses `productService`).
                //ResponseEntity<?> response = productController.getProduct(stringID);

                // 5. Verify the results.
//                assertEquals(HttpStatus.OK, response.getStatusCode());
//                assertEquals(mockResponse, response.getBody());



        this.mockMvc.perform(get("/api/v1/products/{productId}", stringID)).andExpect(status().isOk()) // Assert HTTP status code is 200 OK
               // .andExpect(content().contentType(MediaType.APPLICATION_JSON)). //
                .andExpect(status().isOk()).
        //        andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
//                andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(jsonPath("$.name").value(name))
        .andExpect(jsonPath("$.description").value(description))
                .andExpect(jsonPath("$.price").value(price));
	}

	@Test
	public void deleteProduct() throws Exception {
		this.mockMvc.perform(delete("/api/v1/products/{productId}", "abc")).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$.name").value("<value>")
          );
	}

	@Test
	public void deleteProducts() throws Exception {
		this.mockMvc.perform(delete("/api/v1/products")).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$.name").value("<value>"));
	}

	@Test
	public void updateProduct() throws Exception {
		this.mockMvc.perform(put("/api/v1/products/{productId}", "abc").content("abc").contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$.name").value(""));
	}

    @ParameterizedTest
    @MethodSource("productData")
	public void addProducts(String name, String description, double price) throws Exception {

        Product product = new Product(name,description,price, LocalDateTime.now(),LocalDateTime.now());
        ResponseEntity<Product> responseEntity = new ResponseEntity<>(product, HttpStatus.CREATED);

        when(productService.createProduct(product)).thenReturn(responseEntity);

        ResponseEntity<Product> response = (ResponseEntity<Product>)productController.addProduct(product);

        // Call the controller method
        //String result = productC("someId");
//        ResponseEntity<Product> responseEntity = new ResponseEntity<>(product, HttpStatus.CREATED);


//        Mockito.when(productService.createProduct(product)).thenReturn(responseEntity);

//        ResponseEntity<Product> actualResponseEntity = (ResponseEntity<Product>) productController.addProduct(product);


        this.mockMvc.perform(post("/api/v1/products")
        .content(objectMapper.writeValueAsString(product))
//                        .contentType(MediaType.APPLICATION_JSON))

                      .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isCreated())
               // .andDo(print())
			.andExpect(jsonPath("$.name").value(product.getName()))
            .andExpect(jsonPath("$.description").value(product.getDescription()))
            .andExpect(jsonPath("$.price").value(product.getPrice()))
            .andExpect(jsonPath("$.createdDate").value(product.getCreatedDate()))
            .andExpect(jsonPath("$.lastModifiedDate").value(product.getLastModifiedDate()));







    }
}

/*
*
*         ResponseEntity<Product> responseEntityWithNullBody = new ResponseEntity<>(HttpStatus.OK);

        // Mock the service method to return this ResponseEntity
        when(productService.getProductById(anyLong())).thenReturn(responseEntityWithNullBody);

        // Call the method being tested, which in turn calls the mocked service
        // Assuming your controller or another component calls productService.getProductById()
        ResponseEntity<Product> actualResponseEntity = someController.getProduct(1L);

        // Assert that the body of the returned ResponseEntity is null
        assertNull(actualResponseEntity.getBody());
        assertEquals(HttpStatus.OK, actualResponseEntity.getStatusCode());*/

/*
* /*
*         Product product = new Product(1L, "Test Product");
        ResponseEntity<Product> responseEntity = new ResponseEntity<>(product, HttpStatus.OK);

        when(productService.getProduct(1L)).thenReturn(responseEntity); // Assuming productService returns ResponseEntity

        mockMvc.perform(get("/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(product)));
    }*/

/*
*                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newProduct)))
                .andExpect(status().isCreated()) // Verify HTTP status 201 Created
                .andExpect(content().json(objectMapper.writeValueAsString(expectedProduct))); // Verify response body*/