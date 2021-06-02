package xyz.softeng.shopping.shop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;


@Data
@Validated
@ConfigurationProperties("shopping.shop")
public class ShopConfigurationProperties {
    private String productsQueue;
    private String usersQueue;
}
