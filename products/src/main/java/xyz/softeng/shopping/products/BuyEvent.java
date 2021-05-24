package xyz.softeng.shopping.products;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyEvent {
    private Long productId;
}
