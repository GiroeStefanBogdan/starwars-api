package com.stefan.starwars_api.auth;

import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class TokenStore {
    private final ConcurrentHashMap<String, String> accessToken = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, String> refreshToken = new ConcurrentHashMap<>();

    public void storeTokens(String accessToken, String refreshToken) {
        this.accessToken.put(accessToken, refreshToken);
        this.refreshToken.put(refreshToken, accessToken);

        System.out.println("........................");
        System.out.println("Inside storeTokens(login) method:");
        this.accessToken.forEach((key, value) -> System.out.println("AccessTokenMap: Access Token: " + key + " -> Refresh Token: " + value));
        this.refreshToken.forEach((key, value) -> System.out.println("RefreshTokenMap: Refresh Token: " + key + " -> Access Token: " + value));
    }


    public boolean isValidAccessToken(String accessToken) {
        return this.accessToken.containsKey(accessToken);
    }

    public boolean isValidRefreshToken(String refreshToken) {
        return this.refreshToken.containsKey(refreshToken);
    }

    public String generateNewAccessToken(String refreshToken) {
        if(!isValidRefreshToken(refreshToken)) {
            return null;
        }

        String oldAccessToken = this.refreshToken.get(refreshToken);
        this.accessToken.remove(oldAccessToken);
        String newAccessToken = UUID.randomUUID().toString();
        this.accessToken.put(newAccessToken, refreshToken);
        this.refreshToken.put(refreshToken, newAccessToken);

        System.out.println("........................");
        System.out.println("Inside generateNewAccessToken(refresh) method:");
        this.accessToken.forEach((key, value) -> System.out.println("AccessTokenMap: Access Token: " + key + " -> Refresh Token: " + value));
        this.refreshToken.forEach((key, value) -> System.out.println("RefreshTokenMap: Refresh Token: " + key + " -> Access Token: " + value));
        return newAccessToken;
    }

    /**
     * Removes the access token and its associated refresh token from the maps.
     * @param accessToken
     */
    public void logout(String accessToken) {
        if(isValidAccessToken(accessToken)) {
            String refreshToken = this.accessToken.get(accessToken);
            this.accessToken.remove(accessToken);
            this.refreshToken.remove(refreshToken);

            System.out.println("........................");
            System.out.println("Inside logout method:");
            this.accessToken.forEach((key, value) -> System.out.println("AccessTokenMap: Access Token: " + key + " -> Refresh Token: " + value));
            this.refreshToken.forEach((key, value) -> System.out.println("RefreshTokenMap: Refresh Token: " + key + " -> Access Token: " + value));
        }
    }
}
