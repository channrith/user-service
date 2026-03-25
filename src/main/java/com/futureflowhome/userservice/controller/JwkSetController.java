package com.futureflowhome.userservice.controller;

import com.futureflowhome.userservice.config.JwtProperties;
import com.futureflowhome.userservice.config.JwtRsaSigningConfig.RsaSigningKeys;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
public class JwkSetController {

    private final RSAPublicKey publicKey;
    private final JwtProperties jwtProperties;

    public JwkSetController(RsaSigningKeys rsaSigningKeys, JwtProperties jwtProperties) {
        this.publicKey = rsaSigningKeys.publicKey();
        this.jwtProperties = jwtProperties;
    }

    @GetMapping(value = "/.well-known/jwks.json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> jwks() {
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .keyID(jwtProperties.getKeyId())
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .build();
        return new JWKSet(rsaKey).toPublicJWKSet().toJSONObject();
    }
}
