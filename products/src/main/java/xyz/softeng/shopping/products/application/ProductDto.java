package xyz.softeng.shopping.products.application;

import lombok.Data;

@Data
public class ProductDto {
    private String name;
    private int price;
    private String vendorUsername;
}
