package xyz.softeng.shopping.common;

import lombok.Data;

@Data
public class NewProductEvent implements DomainEvent {
    private Long id;
    private String name;
    private Integer price;
}

