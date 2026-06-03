package com.stefan.starwars_api.auth;

import com.stefan.starwars_api.dto.RefreshRequestDto;
import com.stefan.starwars_api.dto.UserDto;
import com.stefan.starwars_api.dto.UserResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/auth/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserDto userDto) {

        UserResponseDto userResponseDto = authService.login(userDto);
        return ResponseEntity.ok(userResponseDto);
    }

    @PostMapping("/auth/refresh")
    public ResponseEntity<String> refreshToken(@RequestBody RefreshRequestDto refreshRequestDto) {

        String newAccessToken = authService.refreshToken(refreshRequestDto.refreshToken());
        if (newAccessToken == null) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(newAccessToken);
    }

    @PostMapping("/auth/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authHeader) {
        String accessToken = authHeader.replace("Bearer ", "");
        authService.logout(accessToken);
        return ResponseEntity.noContent().build();
    }
}
