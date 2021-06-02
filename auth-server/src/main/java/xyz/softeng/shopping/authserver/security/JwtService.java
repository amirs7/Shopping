package xyz.softeng.shopping.authserver.security;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.softeng.shopping.authserver.AuthServerProperties;
import xyz.softeng.shopping.authserver.user.User;

import java.security.KeyPair;
import java.time.Instant;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtService {
    private final KeyPair keyPair;
    private final JWSAlgorithm algorithm;
    private final AuthServerProperties properties;

    public String encode(User user) throws JOSEException {
        JWSHeader header = new JWSHeader.Builder(algorithm)
                .type(JOSEObjectType.JWT)
                .keyID(properties.getKeyId())
                .build();
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .issuer(properties.getIssuer())
                .claim("scope", user.getRole())
                .expirationTime(Date.from(Instant.now().plusSeconds(properties.getExpire())))
                .build();
        SignedJWT jwt = new SignedJWT(header, claimsSet);
        jwt.sign(new RSASSASigner(keyPair.getPrivate()));
        return jwt.serialize();
    }
}
