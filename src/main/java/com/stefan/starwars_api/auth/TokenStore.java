package com.stefan.starwars_api.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {

    private static final Logger log = LoggerFactory.getLogger(TokenStore.class);

    private final ConcurrentHashMap<String, String> accessToken = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> refreshToken = new ConcurrentHashMap<>();

    public void storeTokens(String accessToken, String refreshToken) {
        this.accessToken.put(accessToken, refreshToken);
        this.refreshToken.put(refreshToken, accessToken);
        log.info("Stored tokens for new session.");
    }

    public boolean isValidAccessToken(String accessToken) {
        return this.accessToken.containsKey(accessToken);
    }

    public boolean isValidRefreshToken(String refreshToken) {
        return this.refreshToken.containsKey(refreshToken);
    }

    public String generateNewAccessToken(String refreshToken) {
        if (!isValidRefreshToken(refreshToken)) {
            return null;
        }
        String oldAccessToken = this.refreshToken.get(refreshToken);
        this.accessToken.remove(oldAccessToken);
        String newAccessToken = UUID.randomUUID().toString();
        this.accessToken.put(newAccessToken, refreshToken);
        this.refreshToken.put(refreshToken, newAccessToken);
        log.info("Access token refreshed.");
        return newAccessToken;
    }

    public void logout(String accessToken) {
        if (isValidAccessToken(accessToken)) {
            String refreshToken = this.accessToken.get(accessToken);
            this.accessToken.remove(accessToken);
            this.refreshToken.remove(refreshToken);
            log.info("Session invalidated.");
        }
    }
}
