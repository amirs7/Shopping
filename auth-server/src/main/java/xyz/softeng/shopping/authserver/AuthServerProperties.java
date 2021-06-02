package xyz.softeng.shopping.authserver;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Data
@Validated
@ConfigurationProperties("auth-server")
public class AuthServerProperties {
    private String keyId = "auth-server-key";

    @NotEmpty
    private String issuer;

    private Integer expire = 120_000;

    @NotEmpty
    private String userQueue;

    @NotEmpty
    private String userExchange;
}
