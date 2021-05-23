package xyz.softeng.shopping.shop;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class Product {
    @Id
    private Long id;

    private Integer price;
}
