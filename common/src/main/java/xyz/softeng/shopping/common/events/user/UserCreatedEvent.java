package xyz.softeng.shopping.common.events.user;

import lombok.Data;
import xyz.softeng.shopping.common.UserRole;

@Data
public class UserCreatedEvent extends UserEvent {
    private String password;
    private Integer wealth;
    private UserRole role;
}
