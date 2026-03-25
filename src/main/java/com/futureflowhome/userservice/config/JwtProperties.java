package com.futureflowhome.userservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    /**
     * Issuer claim (iss); resource servers must trust this same value as issuer-uri.
     */
    private String issuer = "http://localhost:8081";

    /**
     * Key id (kid) for JWKS and JWT header; use a new value when rotating keys.
     */
    private String keyId = "futureflow-1";

    /**
     * PKCS#8 PEM RSA private key (RS256). Override with JWT_PRIVATE_KEY_LOCATION.
     * Default assumes {@code mvn spring-boot:run} from the user-service module (parent contains jwt-keys).
     */
    private String privateKeyLocation = "file:../jwt-keys/private_pkcs8.pem";

    private long expirationSeconds = 86400;

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getPrivateKeyLocation() {
        return privateKeyLocation;
    }

    public void setPrivateKeyLocation(String privateKeyLocation) {
        this.privateKeyLocation = privateKeyLocation;
    }

    public long getExpirationSeconds() {
        return expirationSeconds;
    }

    public void setExpirationSeconds(long expirationSeconds) {
        this.expirationSeconds = expirationSeconds;
    }
}
