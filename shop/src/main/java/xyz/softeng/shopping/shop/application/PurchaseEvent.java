package xyz.softeng.shopping.shop.application;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PurchaseEvent {
    private Long userId;
    private Long productId;
}
