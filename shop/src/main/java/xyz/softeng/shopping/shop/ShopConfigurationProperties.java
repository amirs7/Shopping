package xyz.softeng.shopping.shop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties("shopping.shop")
public class ShopConfigurationProperties {
    @NotEmpty
    private String productsQueue;
    @NotEmpty
    private String usersQueue;
}
