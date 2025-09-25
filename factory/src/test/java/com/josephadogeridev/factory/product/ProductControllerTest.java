package com.josephadogeridev.factory.product;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import com.josephadogeridev.factory.utils.JSONStringParser;
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
import org.springframework.test.web.servlet.MvcResult;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Stream;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ProductService productService;

    @MockitoBean
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    static Stream<Arguments> productIds() {
        return Stream.of(
                Arguments.of(1L),
                Arguments.of(2L),
                Arguments.of(3L),
                Arguments.of(4L)
        );
    }

    static Stream<Arguments> productData() {
        return Stream.of(
                Arguments.of(1L,"lead","lead description", 100.0),
                Arguments.of(2L,"steel","steel description", 200.0),
                Arguments.of(3L,"copper","copper description", 50.0)
        );
    }


    @Test
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
    public void getProduct(long productId, String name, String description, double price) throws Exception {
        Product product = new Product(productId, name, description, price);
        ResponseEntity<Product> mockResponse = new ResponseEntity<>(product, HttpStatus.OK);

        when(productService.findProductById(productId)).thenReturn((mockResponse));


        this.mockMvc.perform(get("/api/v1/products/{productId}", productId)).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$.id").value(productId)).
		  andExpect(jsonPath("$.name").value(product.getName())).
		  andExpect(jsonPath("$.description").value(product.getDescription())).
		  andExpect(jsonPath("$.price").value(product.getPrice()));
	}

    @ParameterizedTest
    @MethodSource("productData")
	public void addProduct(long productId, String name, String description, double price) throws Exception {
        LocalDateTime createdDate = LocalDateTime.now();
        LocalDateTime lastModifiedDate = LocalDateTime.now();
          Product savedProduct = new Product(name,description,price, createdDate,lastModifiedDate);
        savedProduct.setId(productId);

        System.out.println("product instance" + savedProduct);

        ResponseEntity<Product> mockResponse = new ResponseEntity<>(savedProduct, HttpStatus.CREATED);

        when(productService.createProduct(any(Product.class))).thenReturn(mockResponse);

        this.mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(savedProduct))).
		  andExpect(status().isCreated()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON)).
          andExpect(jsonPath("$.id").value(productId)).
          andExpect(jsonPath("$.name").value(savedProduct.getName())).
          andExpect(jsonPath("$.description").value(savedProduct.getDescription())).
          andExpect(jsonPath("$.price").value(savedProduct.getPrice()));

        verify(productService, times(1)).createProduct(any(Product.class));

    }

    @ParameterizedTest
    @MethodSource("productIds")
	public void deleteProduct(long productId) throws Exception {

        // Arrange
        Product product =  new Product("lead","lead description",100.0, LocalDateTime.now(),LocalDateTime.now());
        product.setId(productId);

        HashMap<String, String > responseBody = new HashMap<>();
        responseBody.put("message", "Successfully deleted product with id: " + productId);

        ResponseEntity<HashMap<String, String>> mockResponseEntity = ResponseEntity.ok(responseBody);

        when(productService.deleteProduct(productId)).thenReturn(mockResponseEntity);

        // Act & Assert
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/products/{productId}", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        // Get the response content as a String
        String responseData = mvcResult.getResponse().getContentAsString();

        JSONStringParser jsonStringParser = new JSONStringParser();
        jsonStringParser.extract(responseData);

        String key = jsonStringParser.getKey();
        String value = jsonStringParser.getValue();

        Assertions.assertTrue(responseBody.containsKey(key));
        Assertions.assertTrue(responseBody.containsValue(value));
        Assertions.assertEquals(responseBody.get(key), value);

        verify(productService, times(1)).deleteProduct(productId);


    }

	@Test
	public void deleteProducts() throws Exception {
        List<Product> productList = Arrays.asList(
                new Product("lead","lead description",100.0, LocalDateTime.now(),LocalDateTime.now()),
                new Product("steel","steel description",150.0, LocalDateTime.now(),LocalDateTime.now()),
                new Product("copper","copper description",50.0, LocalDateTime.now(), LocalDateTime.now()));

        productRepository.saveAll(productList);

        HashMap<String, String > responseBody = new HashMap<>();
        responseBody.put("message", "Successfully deleted all products");

        ResponseEntity<HashMap<String, String>> mockResponseEntity = ResponseEntity.ok(responseBody);

        when(productService.deleteAllProducts()).thenReturn(mockResponseEntity);

        MvcResult mvcResult = this.mockMvc.perform(delete("/api/v1/products")).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();

        // Get the response content as a String
        String responseData = mvcResult.getResponse().getContentAsString();

        JSONStringParser jsonStringParser = new JSONStringParser();
        jsonStringParser.extract(responseData);

        String key = jsonStringParser.getKey();
        String value = jsonStringParser.getValue();

        Assertions.assertTrue(responseBody.containsKey(key));
        Assertions.assertTrue(responseBody.containsValue(value));
        Assertions.assertEquals(responseBody.get(key), value);

        verify(productService, times(1)).deleteAllProducts();
	}

    @ParameterizedTest
    @MethodSource("productData")
	public void updateProduct(long productId, String name, String description, double price) throws Exception {
        final String newDescription = "New description";
        final double newPrice = 1000.0;

        LocalDateTime createdDate = LocalDateTime.now();
        Product existingProduct = new Product(name, description, price, createdDate, LocalDateTime.now());
        existingProduct.setId(productId);
        Product updatedProduct = new Product(name, newDescription, newPrice, createdDate, LocalDateTime.now());
        updatedProduct.setId(productId);
        ResponseEntity<Product> mockSaveResponse = new ResponseEntity<>(updatedProduct, HttpStatus.ACCEPTED);

        productRepository.save(existingProduct);
        when(productService.updateProduct(eq(productId), any(Product.class)))
                .thenReturn(mockSaveResponse);

		this.mockMvc.perform(put("/api/v1/products/{productId}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(updatedProduct)))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("$.id").value(updatedProduct.getId()))
			.andExpect(jsonPath("$.name").value(updatedProduct.getName()))
			.andExpect(jsonPath("$.description").value(updatedProduct.getDescription()))
			.andExpect(jsonPath("$.price").value(updatedProduct.getPrice()));
	}
}
