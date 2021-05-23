package xyz.softeng.shopping.shop;

import org.springframework.stereotype.Component;

@Component
public class ShopService {
    public boolean canBuy(User user, Product product) {
        return user.getWealth() >= product.getPrice();
    }
}
