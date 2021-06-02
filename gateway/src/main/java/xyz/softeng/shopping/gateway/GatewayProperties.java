package xyz.softeng.shopping.gateway;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties("shopping.gateway")
public class GatewayProperties {
    private Map<String, String> services = new HashMap<>();
}
