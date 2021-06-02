package xyz.softeng.shopping.users;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties("user-service")
public class UserServiceProperties {
    @NotEmpty
    private String adminUser;

    @NotEmpty
    private String adminPass;

    @NotEmpty
    private String userExchange;

    @NotEmpty
    private String purchaseQueue;

    @NotEmpty
    private String purchaseExchange;
}
