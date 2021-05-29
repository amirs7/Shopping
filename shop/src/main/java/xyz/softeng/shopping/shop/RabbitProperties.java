package xyz.softeng.shopping.shop;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("shopping.rabbit")
public class RabbitProperties {
    private final Exchanges exchanges = new Exchanges();

    @Data
    public static class Exchanges {
        private String purchases;
    }
}
