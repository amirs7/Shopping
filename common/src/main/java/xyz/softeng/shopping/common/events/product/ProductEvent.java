package xyz.softeng.shopping.common.events.product;

import lombok.Data;
import xyz.softeng.shopping.common.events.DomainEvent;

@Data
public class ProductEvent implements DomainEvent {
    private Long id;
}
