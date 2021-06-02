package xyz.softeng.shopping.users;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@ConfigurationProperties("user-service")
public class UserServiceConfigurationProperties {
    private String adminUser;
    private String adminPass;
    private String usersExchange;
    private String purchasesQueue;
    private String purchasesExchange;
}
