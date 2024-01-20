package com.github.xielong.aihub.adapter.zhipu;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.time.Instant;
import java.util.Date;
import java.util.Map;

public class TokenGenerator {

    public static String generateToken(String apiKey, int expSeconds) {
        try {
            String[] parts = apiKey.split("\\.");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Invalid apikey format");
            }
            String id = parts[0];
            String secret = parts[1];

            Instant now = Instant.now();
            Instant expirationTime = now.plusSeconds(expSeconds);

            return JWT.create()
                    .withClaim("api_key", id)
                    .withClaim("exp", Date.from(expirationTime).getTime())
                    .withClaim("timestamp", Date.from(now).getTime())
                    .withHeader(Map.of("alg", "HS256", "sign_type", "SIGN"))
                    .sign(Algorithm.HMAC256(secret));
        } catch (Exception e) {
            throw new RuntimeException("Error generating token: " + e.getMessage(), e);
        }
    }

}
