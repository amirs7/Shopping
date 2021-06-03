package xyz.softeng.shopping.history;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties("rating-service")
public class HistoryServiceProperties {
    @NotEmpty
    private String purchaseQueue;

    @NotEmpty
    private String purchaseExchange;
}
