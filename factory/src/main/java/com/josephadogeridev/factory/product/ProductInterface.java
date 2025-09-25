package com.josephadogeridev.factory.product;

/**
 * @author Joseph Adogeri
 * @since 25-SEP-2025
 * @version 1.0.0
 */

public interface ProductInterface {
    
    String getName();
    void setName(String name);
    String getDescription();
    void setDescription(String description);
    Double getPrice();
    void setPrice(Double price);

}
