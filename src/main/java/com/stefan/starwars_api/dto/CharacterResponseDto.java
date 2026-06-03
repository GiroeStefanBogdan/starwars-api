package com.stefan.starwars_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterResponseDto(
        String name,
        String height,
        String mass,

        @JsonProperty("birth_year")
        String birthYear,

        @JsonProperty("number_of_films")
        int numberOfFilms,

        @JsonProperty("date_added")
        String dateAdded
) {}