package com.stefan.starwars_api;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.stefan.starwars_api.auth.TokenStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TokenStoreTest {

    private TokenStore tokenStore;

    @BeforeEach
    void setUp() {
        tokenStore = new TokenStore();
    }

    @Test
    void storeTokens_accessTokenIsValid() {
        tokenStore.storeTokens("access123", "refresh123");

        assertTrue(tokenStore.isValidAccessToken("access123"));
    }

    @Test
    void storeTokens_refreshTokenIsValid() {
        tokenStore.storeTokens("access123", "refresh123");

        assertTrue(tokenStore.isValidRefreshToken("refresh123"));
    }

    @Test
    void generateNewAccessToken_invalidRefreshToken_returnsNull() {
        assertNull(tokenStore.generateNewAccessToken("invalid"));
    }

    @Test
    void generateNewAccessToken_validRefreshToken_returnsNewTokenAndInvalidatesOld() {
        tokenStore.storeTokens("access123", "refresh123");

        String newAccessToken = tokenStore.generateNewAccessToken("refresh123");

        assertNotNull(newAccessToken);
        assertNotEquals("access123", newAccessToken);
        assertTrue(tokenStore.isValidAccessToken(newAccessToken));
        assertFalse(tokenStore.isValidAccessToken("access123"));
    }

    @Test
    void logout_validAccessToken_invalidatesSession() {
        tokenStore.storeTokens("access123", "refresh123");

        tokenStore.logout("access123");

        assertFalse(tokenStore.isValidAccessToken("access123"));
        assertFalse(tokenStore.isValidRefreshToken("refresh123"));
    }
}
