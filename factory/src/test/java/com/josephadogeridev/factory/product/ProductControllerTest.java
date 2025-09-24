package com.josephadogeridev.factory.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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

	@MockBean
	private ProductService productService;

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
        ResponseEntity<Product> mockResponse = new ResponseEntity<>(product, HttpStatus.OK);

        when(productService.findProductById(productId)).thenReturn((mockResponse));

//        HashMap<String, String > responseBody = new HashMap<String, String>();
//        responseBody.put("message", "Successfully deleted product with id: " + productId);
//        ResponseEntity<HashMap<String, String>> mockResponse = new ResponseEntity<HashMap<String, String>>(responseBody, HttpStatus.OK);
//
//        when(productService.findProductById(Long.parseLong(productId))).thenReturn(product);

        //when(productService.deleteProduct(Long.parseLong(productId)).thenReturn(responseBody));
        HashMap<String, String > responseBody = new HashMap<String, String>();
        responseBody.put("message", "Successfully deleted product with id: " + productId);

        Gson gson = new Gson();
        String jsonString = gson.toJson(responseBody);

        System.out.println(jsonString);


        ResponseEntity<HashMap<String, String>> mockResponseEntity = ResponseEntity.ok(responseBody);

        when(productService.deleteProduct(productId)).thenReturn(mockResponseEntity);

        System.out.println("mock response" + mockResponseEntity);
        System.out.println("mock response string" + mockResponseEntity.toString());


        // Act & Assert
        MvcResult mvcResult = mockMvc.perform(delete("/api/v1/products", productId)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();

        // Get the response content as a String
        String responseData = mvcResult.getResponse().getContentAsString();


//        doNothing().when(productService).deleteProduct(productId);
//		this.mockMvc.perform(delete("/api/v1/products/{productId}", productId)).
//		  andExpect(status().isOk()).
//		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
//		  andExpect(jsonPath("$.<key>").value("<value>"));
	}

	@Test
	public void deleteProducts() throws Exception {
		this.mockMvc.perform(delete("/api/v1/products")).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$.<key>").value("<value>"));
	}

	@Test
	public void updateProduct() throws Exception {
		this.mockMvc.perform(put("/api/v1/products/{productId}", "abc").content("abc").contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.id").value("<value>"))
			.andExpect(jsonPath("$.name").value("<value>"))
			.andExpect(jsonPath("$.description").value("<value>"))
			.andExpect(jsonPath("$.price").value("<value>"))
			.andExpect(jsonPath("$.createdDate").value("<value>"))
			.andExpect(jsonPath("$.lastModifiedDate").value("<value>"));
	}
}
