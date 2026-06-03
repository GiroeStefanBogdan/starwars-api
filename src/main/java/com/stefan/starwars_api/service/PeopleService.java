package com.stefan.starwars_api.service;

import com.stefan.starwars_api.client.SwapiClient;
import com.stefan.starwars_api.dto.CharacterDto;
import com.stefan.starwars_api.dto.CharacterResponseDto;
import com.stefan.starwars_api.dto.PeopleResponseDto;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PeopleService {

    private final SwapiClient swapiClient;

    public PeopleService(SwapiClient swapiClient) {
        this.swapiClient = swapiClient;
    }

        @Cacheable("people")
        public PeopleResponseDto getAllCharacters(int page) {
            System.out.println("Fetching data from SWAPI for page " + page);
            return swapiClient.getAllCharacters(page);
        }

        public CharacterResponseDto getCharacterById(int id) {
            CharacterDto character = swapiClient.getCharacterById(id);
            return new CharacterResponseDto(
                    character.name(),
                    character.height(),
                    character.mass(),
                    character.birthYear(),
                    character.films().size(),
                    formatDate(character.created())
            );
        }

    private String formatDate(String created) {
        LocalDateTime dateTime = LocalDateTime.parse(created,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"));
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
