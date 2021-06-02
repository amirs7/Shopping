package xyz.softeng.shopping.common.events.user;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.softeng.shopping.common.UserRole;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserCreatedEvent extends UserEvent {
    private String password;
    private Integer wealth;
    private UserRole role;
}
