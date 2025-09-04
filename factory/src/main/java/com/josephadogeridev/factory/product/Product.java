package com.josephadogeridev.factory.product;



import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;

import javax.json.Json;
import javax.json.JsonObject;

public class Product implements ProductInterface {
    private String name;
    private String description;
    private Double price;

    public Product(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }
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
    @Override
    public String toString() {

       JsonObject productJson = Json.createObjectBuilder()
                .add("name", this.name)
                .add("description", this.description)
                .add("price", this.price)
                .build();

        return productJson.toString();
    }

}
