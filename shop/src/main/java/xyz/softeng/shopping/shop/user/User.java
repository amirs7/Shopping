package xyz.softeng.shopping.shop.user;

import lombok.Data;
import xyz.softeng.shopping.shop.product.Product;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "shop_user")
public class User {
    @Id
    private Long id;

    private Integer wealth;

    public boolean canPurchase(Product product) {
        return wealth >= product.getPrice();
    }
}
