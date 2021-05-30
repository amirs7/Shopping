package xyz.softeng.shopping.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseEvent implements DomainEvent {
    private Long userId;
    private Integer cost;
}
