package com.stefan.starwars_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CharacterDto (
    String name,
    String height,
    String mass,
    @JsonProperty("hair_color")
    String hairColor,
    @JsonProperty("skin_color")
    String skinColor,
    @JsonProperty("eye_color")
    String eyeColor,
    @JsonProperty("birth_year")
    String birthYear,
    String gender,
    String homeworld,
    List<String> films,
    String created
) {}
