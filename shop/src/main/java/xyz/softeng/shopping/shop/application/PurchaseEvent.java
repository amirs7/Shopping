package xyz.softeng.shopping.shop.application;

import lombok.AllArgsConstructor;
import lombok.Data;
import xyz.softeng.shopping.common.DomainEvent;

@Data
@AllArgsConstructor
public class PurchaseEvent implements DomainEvent {
    private Long userId;
    private Long productId;
    private Integer cost;
}
