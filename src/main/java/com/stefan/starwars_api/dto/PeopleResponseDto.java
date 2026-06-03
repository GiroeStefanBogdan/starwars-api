package com.stefan.starwars_api.dto;

import java.util.List;

public record PeopleResponseDto (
        int count,
        String next,
        String previous,
        List<CharacterDto> results
) {
}
