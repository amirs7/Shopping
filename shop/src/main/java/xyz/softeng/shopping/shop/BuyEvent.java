package xyz.softeng.shopping.shop;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BuyEvent {
    private Long userId;
    private Long productId;
}
