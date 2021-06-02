package xyz.softeng.shopping.rating;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties("rating-service")
public class RatingServiceProperties {
    @NotEmpty
    private String purchaseQueue;

    @NotEmpty
    private String purchaseExchange;
}
