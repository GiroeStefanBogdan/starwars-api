package com.stefan.starwars_api.client;

import com.stefan.starwars_api.dto.CharacterDto;
import com.stefan.starwars_api.dto.PeopleResponseDto;
import com.stefan.starwars_api.exception.CharacterNotFoundException;
import com.stefan.starwars_api.exception.SwapiUnavailableException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
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
        try {
            return restClient.get()
                    .uri("/people/?page=" + page)
                    .retrieve()
                    .onStatus(status -> status.value() == 404,
                            (req, res) -> {
                                throw new IllegalArgumentException("Page " + page + " does not exist.");
                            })
                    .onStatus(HttpStatusCode::is5xxServerError,
                            (req, res) -> {
                                throw new SwapiUnavailableException();
                            })
                    .body(PeopleResponseDto.class);
        } catch (ResourceAccessException ex) {
            throw new SwapiUnavailableException();
        }
    }

    public CharacterDto getCharacterById(int id) {
        try {
            return restClient.get()
                    .uri("/people/" + id + "/")
                    .retrieve()
                    .onStatus(status -> status.value() == 404,
                            (req, res) -> {
                                throw new CharacterNotFoundException(id);
                            })
                    .onStatus(HttpStatusCode::is5xxServerError,
                            (req, res) -> {
                                throw new SwapiUnavailableException();
                            })
                    .body(CharacterDto.class);
        } catch (ResourceAccessException ex) {
            throw new SwapiUnavailableException();
        }
    }
}
