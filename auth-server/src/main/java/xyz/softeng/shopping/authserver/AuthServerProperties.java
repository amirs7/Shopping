package xyz.softeng.shopping.authserver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("auth-server")
public class AuthServerProperties {
    private String keyId = "auth-server-key";
    private String issuer;
    private Integer expire = 120_000;
    private String adminUser;
    private String adminPass;
    private String usersExchange;
}
