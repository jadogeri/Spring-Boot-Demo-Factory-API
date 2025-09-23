package com.josephadogeridev.factory.product;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(ProductController.class)
public class ProductControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockitoBean
	private ProductService productService;

	@Test
	public void getProduct() throws Exception {
		this.mockMvc.perform(get("/api/v1/products/{productId}", "abc")).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$.<key>").value("<value>"));
	}

	@Test
	public void getProducts() throws Exception {
		this.mockMvc.perform(get("/api/v1/products")).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$[0].id").value("<value>")).
		  andExpect(jsonPath("$[0].name").value("<value>")).
		  andExpect(jsonPath("$[0].description").value("<value>")).
		  andExpect(jsonPath("$[0].price").value("<value>")).
		  andExpect(jsonPath("$[0].createdDate").value("<value>")).
		  andExpect(jsonPath("$[0].lastModifiedDate").value("<value>"));
	}

	@Test
	public void deleteProduct() throws Exception {
		this.mockMvc.perform(delete("/api/v1/products/{productId}", "abc")).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$.<key>").value("<value>"));
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
		this.mockMvc.perform(put("/api/v1/products/{productId}", "abc").content("abc").contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(status().isOk()).
		  andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).
		  andExpect(jsonPath("$.<key>").value("<value>"));
	}

	@Test
	public void addProduct() throws Exception {
		this.mockMvc.perform(post("/api/v1/products").content("abc").contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(status().isOk())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
			.andExpect(jsonPath("$.<key>").value("<value>"));
	}
}
