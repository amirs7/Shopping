package xyz.softeng.shopping.common;

import lombok.Data;

@Data
public class PurchaseEvent implements DomainEvent {
    private Long productId;
    private Long userId;
    private Integer cost;
}
