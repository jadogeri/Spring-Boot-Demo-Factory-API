package com.josephadogeridev.factory.product;

import static jakarta.persistence.GenerationType.SEQUENCE;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.json.Json;
import javax.json.JsonObject;
import java.time.LocalDateTime;

@Entity(name = "Product")
@Table(
        uniqueConstraints = {
                @UniqueConstraint(name = "product_name_unique" , columnNames = "name")
        }
)
public class Product implements ProductInterface {

    @Id
    @SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )

    @GeneratedValue(
            strategy = SEQUENCE,
            generator = "product_sequence"

    )
    private Long id;
    @Column(nullable = false, columnDefinition = "TEXT", unique = true)
    @NotBlank(message = "The name is required.")
    private String name;
    @Column(updatable = true, nullable = false, columnDefinition = "TEXT")
    private String description;
    @Column(updatable = true, nullable = false, columnDefinition = "DOUBLE")
    private Double price;
    @Column(updatable = false)
    @CreatedDate
    protected LocalDateTime createdDate;
    @Column(updatable = true)
    @LastModifiedDate
    protected LocalDateTime lastModifiedDate;

    public Product() {  }

    public Product(String name, String description, Double price, LocalDateTime createdDate, LocalDateTime lastModifiedDate) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
    }
    public Product(Long id,String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.createdDate = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public LocalDateTime getCreatedDate() { return createdDate;}
    public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
    public LocalDateTime getLastModifiedDate() { return lastModifiedDate;}
    public void setLastModifiedDate(LocalDateTime lastModifiedDate) { this.lastModifiedDate = lastModifiedDate; }

    @Override
    public String toString() {

       JsonObject productJson = Json.createObjectBuilder()
               .add("name", this.name)
               .add("description", this.description)
               .add("price", this.price)
               .add("createdDate", String.valueOf(this.getCreatedDate()))
               .add("lastModifiedDate", String.valueOf(this.getLastModifiedDate()))
               .build();

//        // Build a Gson instance with pretty printing enabled
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//
//
//        // Convert the JsonObject to a pretty-printed JSON string
//        String prettyJson = gson.toJson(productJson);
//
//        // Print the pretty-printed JSON
        System.out.println(productJson.toString());
//
//        System.out.println(prettyJson);

        return productJson.toString();


    }
}

