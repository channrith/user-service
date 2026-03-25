package com.futureflowhome.userservice.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.RSAPublicKeySpec;

@Configuration
@EnableConfigurationProperties(JwtProperties.class)
public class JwtRsaSigningConfig {

    @Bean
    public RsaSigningKeys rsaSigningKeys(JwtProperties jwtProperties, ResourceLoader resourceLoader) throws Exception {
        Resource resource = resourceLoader.getResource(jwtProperties.getPrivateKeyLocation());
        String pem;
        try (InputStream in = resource.getInputStream()) {
            pem = new String(in.readAllBytes(), StandardCharsets.UTF_8);
        }
        PrivateKey privateKey = PemPkcs8RsaPrivateKeyParser.parse(pem);
        RSAPublicKey publicKey = publicKeyFrom(privateKey);
        return new RsaSigningKeys(privateKey, publicKey);
    }

    private static RSAPublicKey publicKeyFrom(PrivateKey privateKey) throws Exception {
        if (privateKey instanceof RSAPrivateCrtKey crt) {
            RSAPublicKeySpec spec = new RSAPublicKeySpec(crt.getModulus(), crt.getPublicExponent());
            return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
        }
        throw new IllegalStateException(
                "RSA private key must be in PKCS#8 form with CRT parameters (typical OpenSSL PKCS#8 output)");
    }

    public record RsaSigningKeys(PrivateKey privateKey, RSAPublicKey publicKey) {
    }
}
