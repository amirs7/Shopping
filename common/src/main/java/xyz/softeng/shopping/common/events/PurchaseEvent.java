package xyz.softeng.shopping.common.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseEvent implements DomainEvent {
    private Long userId;
    private Long productId;
    private Integer cost;
}
