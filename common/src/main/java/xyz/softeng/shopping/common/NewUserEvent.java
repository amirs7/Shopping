package xyz.softeng.shopping.common;

import lombok.Data;

@Data
public class NewUserEvent implements DomainEvent {
    private Long id;
    private String username;
    private Integer wealth;
}
