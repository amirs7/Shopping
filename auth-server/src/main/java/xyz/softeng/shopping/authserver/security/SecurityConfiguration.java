package xyz.softeng.shopping.authserver.security;

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
}
