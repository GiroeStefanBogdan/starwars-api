package com.stefan.starwars_api.client;

import com.stefan.starwars_api.dto.CharacterDto;
import com.stefan.starwars_api.dto.CharacterResponseDto;
import com.stefan.starwars_api.dto.PeopleResponseDto;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SwapiClient {
    private final RestClient restClient;

    public SwapiClient(RestClient.Builder restClientBuilder) {
        this.restClient = restClientBuilder
                .baseUrl("https://swapi.py4e.com/api")
                .build();
    }

    public PeopleResponseDto getAllCharacters(int page) {
        return restClient.get()
                .uri("/people/?page=" + page)
                .retrieve()
                .body(PeopleResponseDto.class);
    }

    public CharacterDto getCharacterById(int id) {
        return restClient.get()
                .uri("/people/" + id + "/")
                .retrieve()
                .body(CharacterDto.class);
    }
}
