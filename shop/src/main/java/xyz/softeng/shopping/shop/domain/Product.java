package xyz.softeng.shopping.shop.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "shop_product")
public class Product {
    @Id
    private Long id;

    private String name;

    private Integer price;
}
