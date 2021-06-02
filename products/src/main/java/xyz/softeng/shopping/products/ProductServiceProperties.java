package xyz.softeng.shopping.products;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties("product-service")
public class ProductServiceProperties {
    @NotEmpty
    private String productExchange;
}
