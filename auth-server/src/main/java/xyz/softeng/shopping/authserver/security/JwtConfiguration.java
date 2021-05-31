package xyz.softeng.shopping.authserver.security;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import xyz.softeng.shopping.authserver.AuthServerProperties;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;

@Configuration
public class JwtConfiguration {
    @Autowired
    private AuthServerProperties properties;

    @Bean
    public JWSAlgorithm algorithm() {
        return JWSAlgorithm.RS256;
    }

    @Bean
    public JWK publicKey(KeyPair keyPair, JWSAlgorithm algorithm) {
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(algorithm)
                .keyID(properties.getKeyId())
                .build();
    }

    @Bean
    public JWKSet jwkSet(JWK jwk) {
        return new JWKSet(jwk);
    }
}
