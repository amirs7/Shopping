package xyz.softeng.shopping.errorhandler;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {
    @Bean
    public Exchange exchange() {
        return ExchangeBuilder.fanoutExchange("products")
                .durable(true)
                .build();
    }
}
