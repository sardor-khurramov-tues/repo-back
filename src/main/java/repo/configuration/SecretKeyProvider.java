package repo.configuration;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Getter;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

@Getter
@Component
public class SecretKeyProvider {

    private static final String JWT_SECRET_PROPERTY_NAME = "jwt.secret";
    private final Key secretKey;

    public SecretKeyProvider(Environment environment) {
        String jwtSecret = environment.getProperty(JWT_SECRET_PROPERTY_NAME);

        secretKey = new SecretKeySpec(
                jwtSecret.getBytes(StandardCharsets.UTF_8),
                SignatureAlgorithm.HS256.getJcaName()
        );
    }

}
