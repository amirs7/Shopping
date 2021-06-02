package xyz.softeng.shopping.history;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import xyz.softeng.shopping.common.configuration.RabbitBaseConfiguration;

@Configuration
@Import(RabbitBaseConfiguration.class)
public class RabbitConfiguration {
}
