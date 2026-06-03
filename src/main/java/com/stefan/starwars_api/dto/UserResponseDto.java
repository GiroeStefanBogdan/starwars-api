package com.stefan.starwars_api.dto;

public record UserResponseDto (
        String accessToken,
        String refreshToken,
        UserDto user
){

}
