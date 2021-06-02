package xyz.softeng.shopping.shop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;


@Data
@Validated
@ConfigurationProperties("shop-service")
public class ShopServiceProperties {
    @NotEmpty
    private String purchaseExchange;

    @NotEmpty
    private String productExchange;

    @NotEmpty
    private String productQueue;

    @NotEmpty
    private String userExchange;

    @NotEmpty
    private String userQueue;
}
