package com.stefan.starwars_api.controller;

import com.stefan.starwars_api.auth.TokenStore;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FavoritesController {

    private final TokenStore tokenStore;

    public FavoritesController(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }
    @GetMapping("/favourites")
    public ResponseEntity<?> getFavourites(@RequestHeader("Authorization") String authHeader) {
        String accessToken = authHeader.replace("Bearer ", "");
        if (!tokenStore.isValidAccessToken(accessToken)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(List.of(
                "Luke Skywalker",
                "Darth Vader",
                "Yoda",
                "Obi-Wan Kenobi",
                "Leia Organa"
        ));
    }
}
