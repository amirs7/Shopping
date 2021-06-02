package xyz.softeng.shopping.common;

import lombok.Data;

@Data
public class UserCreatedEvent extends UserEvent {
    private String password;
    private Integer wealth;
    private UserRole role;
}
