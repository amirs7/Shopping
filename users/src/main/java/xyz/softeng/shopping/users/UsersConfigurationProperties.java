package xyz.softeng.shopping.users;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties("shopping.users")
public class UsersConfigurationProperties {
    @NotEmpty
    private String purchasesQueue;
}
