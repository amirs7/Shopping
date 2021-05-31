package xyz.softeng.shopping.authserver;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final KeyPair keyPair;
    private final JWKSet jwkSet;

    public String encode(String username) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder(JWSAlgorithm.RS256)
                .type(JOSEObjectType.JWT)
                .keyID("my-key-id")
                .build();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .issuer("http://localhost:8080")
                .subject(username)
                .expirationTime(Date.from(Instant.now().plusSeconds(12000)))
                .build();
        SignedJWT jwt = new SignedJWT(header, claimsSet);
        jwt.sign(new RSASSASigner(keyPair.getPrivate()));
        return jwt.serialize();
    }

    @PostConstruct
    public void init() throws JOSEException {
        System.out.println(new String(keyPair.getPublic().getEncoded()));
        System.out.println(encode("amir"));
    }
}
