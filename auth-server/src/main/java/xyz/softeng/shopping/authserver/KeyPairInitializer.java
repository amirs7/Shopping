package xyz.softeng.shopping.authserver;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Component
public class KeyPairInitializer {
    private static final String PRIVATE_KEY_FILE_PATH = "privateKey";
    private static final String PUBLIC_KEY_FILE_PATH = "publicKey";
    public static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.RS256;

    public KeyPair initKeyPair() throws NoSuchAlgorithmException, IOException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(SIGNATURE_ALGORITHM.getFamilyName());
        keyPairGenerator.initialize(SIGNATURE_ALGORITHM.getMinKeyLength());
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        try (FileOutputStream fos = new FileOutputStream(PUBLIC_KEY_FILE_PATH)) {
            fos.write(keyPair.getPublic().getEncoded());
        }
        try (FileOutputStream fos = new FileOutputStream(PRIVATE_KEY_FILE_PATH)) {
            fos.write(keyPair.getPrivate().getEncoded());
        }
        return keyPair;
    }

    public PublicKey readPublicKey() throws IOException, GeneralSecurityException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(PUBLIC_KEY_FILE_PATH));
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(SIGNATURE_ALGORITHM.getFamilyName());
        return kf.generatePublic(spec);
    }

    public PrivateKey readPrivateKey() throws IOException, GeneralSecurityException {
        byte[] keyBytes = Files.readAllBytes(Paths.get(PRIVATE_KEY_FILE_PATH));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(SIGNATURE_ALGORITHM.getFamilyName());
        return kf.generatePrivate(spec);
    }
}
