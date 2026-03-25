package com.futureflowhome.userservice.service;

import com.futureflowhome.userservice.config.JwtProperties;
import com.futureflowhome.userservice.config.JwtRsaSigningConfig.RsaSigningKeys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;

@Service
public class JwtService {

    private final JwtProperties jwtProperties;
    private final PrivateKey privateKey;
    private final RSAPublicKey publicKey;

    public JwtService(JwtProperties jwtProperties, RsaSigningKeys rsaSigningKeys) {
        this.jwtProperties = jwtProperties;
        this.privateKey = rsaSigningKeys.privateKey();
        this.publicKey = rsaSigningKeys.publicKey();
    }

    public String createToken(String username, String userUuid, String roleName) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + jwtProperties.getExpirationSeconds() * 1000);
        return Jwts.builder()
                .header()
                .keyId(jwtProperties.getKeyId())
                .and()
                .issuer(jwtProperties.getIssuer())
                .subject(username)
                .claim("userId", userUuid)
                .claim("role", roleName)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    public Claims parseToken(String token) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .requireIssuer(jwtProperties.getIssuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getExpirationSeconds() {
        return jwtProperties.getExpirationSeconds();
    }
}
