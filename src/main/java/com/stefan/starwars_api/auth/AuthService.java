package com.stefan.starwars_api.auth;

import com.stefan.starwars_api.dto.UserDto;
import com.stefan.starwars_api.dto.UserResponseDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {

    private final TokenStore tokenStore;

    public AuthService(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    public UserResponseDto login(UserDto userDto) {
        String accessToken = UUID.randomUUID().toString();
        String refreshToken = UUID.randomUUID().toString();

        tokenStore.storeTokens(accessToken, refreshToken);

        return new UserResponseDto(accessToken, refreshToken, userDto);
    }

    public String refreshToken(String refreshToken) {
        return tokenStore.generateNewAccessToken(refreshToken);
    }

    public void logout(String accessToken) {
        tokenStore.logout(accessToken);
    }
}
