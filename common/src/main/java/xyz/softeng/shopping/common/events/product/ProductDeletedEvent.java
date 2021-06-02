package xyz.softeng.shopping.common.events.product;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ProductDeletedEvent extends ProductEvent {
}

