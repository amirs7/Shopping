package xyz.softeng.shopping.common.events.user;

import lombok.Data;
import xyz.softeng.shopping.common.events.DomainEvent;

@Data
public class UserEvent implements DomainEvent {
    private String username;
}

