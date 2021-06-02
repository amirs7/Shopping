package xyz.softeng.shopping.common;

import lombok.Data;

@Data
public class UserEvent implements DomainEvent {
    private String username;
}

