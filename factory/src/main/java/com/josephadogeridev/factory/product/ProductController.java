package com.josephadogeridev.factory.product;



import com.josephadogeridev.factory.utils.NumberChecker;
import com.josephadogeridev.factory.exceptions.ErrorResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
//@XmlRootElement(name = "employee")

public class ProductController {

    private final ProductService productService;

    //dummy list for testing routes will be deleted
    List<Product> productList;

    @Autowired
    ProductController(ProductService productService){
        this.productService = productService;
   }

    @GetMapping
    public List<Product> getProducts() {

        return this.productService.findAllProducts();

    }
    @GetMapping(path = "{productId}" )
    public ResponseEntity<Product> getProduct(@PathVariable("productId") String productId     ) {
        Long id = Long.valueOf(productId);
        return this.productService.findProductById(id);
    }

    @PostMapping( consumes = "application/json", produces = "application/json")
    public ResponseEntity<Product> addProduct(@RequestBody Product product ) {
        System.out.println("variables in product" + product);
        return this.productService.createProduct(product);

    }

    @DeleteMapping(path = "{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable("productId") String productId) {
        System.out.println("variables in product" + productId);
        if (!NumberChecker.isNumeric(productId)){
            throw new IllegalArgumentException("productId " + productId + "is not valid");

        }
        return this.productService.deleteProduct(Long.valueOf(productId));

    }

    @DeleteMapping
    public ResponseEntity<?> deleteProducts() {
        return this.productService.deleteAllProducts();
    }

    @PutMapping(path = "{productId}")
    public  ResponseEntity<Product> updateProduct(
            @PathVariable("productId") String productId,
            @RequestBody Product product) throws Exception {
        if (!NumberChecker.isNumeric(productId)){

            throw new BadRequestException("productId " + productId + "is not valid" );
        }
        return this.productService.updateProduct(Long.valueOf(productId), product);

    }
}
