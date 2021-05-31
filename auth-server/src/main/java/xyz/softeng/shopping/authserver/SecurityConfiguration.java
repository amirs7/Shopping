package xyz.softeng.shopping.authserver;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public KeyPair keyPair(KeyPairInitializer keyPairInitializer) throws GeneralSecurityException, IOException {
        try {
            PublicKey publicKey = keyPairInitializer.readPublicKey();
            PrivateKey privateKey = keyPairInitializer.readPrivateKey();
            return new KeyPair(publicKey, privateKey);
        } catch (Exception e) {
            return keyPairInitializer.initKeyPair();
        }
    }

    @Bean
    public JWK publicKey(KeyPair keyPair) throws JOSEException {
//        return new ECKeyGenerator(Curve.P_256)
//                .keyID("my-key")
//                .generate();
//        return new RSAKeyGenerator(2048)
//                .algorithm(JWSAlgorithm.RS256)
//                .keyUse(KeyUse.SIGNATURE)
//                .keyID("my-key-id")
//                .generate();
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("my-key-id")
                .build();
    }

    @Bean
    public JWKSet jwkSet(JWK jwk) throws JOSEException {
        return new JWKSet(jwk);
//        JWK jwk = new RSAKeyGenerator(2048)
//                .keyUse(KeyUse.SIGNATURE)
//                .algorithm(JWSAlgorithm.RS256)
//                .keyID("my-key-id")
//                .generate();
//        return new JWKSet(jwk);
    }
}
