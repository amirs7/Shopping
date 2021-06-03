package xyz.softeng.shopping.common.events.user;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDeletedEvent extends UserEvent {
}
