package xyz.softeng.shopping.products;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
@EntityListeners(ProductUpdateListener.class)
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private Integer price;

    private Integer buyCount = 0;

    public void increaseBuyCount() {
        buyCount++;
    }
}
