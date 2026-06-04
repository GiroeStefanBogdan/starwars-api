package com.stefan.starwars_api.service;

import com.stefan.starwars_api.client.SwapiClient;
import com.stefan.starwars_api.dto.CharacterDto;
import com.stefan.starwars_api.dto.CharacterResponseDto;
import com.stefan.starwars_api.dto.PeopleResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class PeopleService {

    private static final Logger log = LoggerFactory.getLogger(PeopleService.class);

    private final SwapiClient swapiClient;

    public PeopleService(SwapiClient swapiClient) {
        this.swapiClient = swapiClient;
    }

    @Cacheable("people")
    public PeopleResponseDto getAllCharacters(int page) {
        if (page < 1) {
            throw new IllegalArgumentException("Page number must be greater than 0.");
        }
        log.info("Fetching data from SWAPI for page {}", page);
        return swapiClient.getAllCharacters(page);
    }

    public CharacterResponseDto getCharacterById(int id) {
        if (id < 1) {
            throw new IllegalArgumentException("ID must be greater than 0.");
        }
        CharacterDto character = swapiClient.getCharacterById(id);
        return new CharacterResponseDto(
                character.name(),
                convertHeight(character.height()),
                character.mass(),
                character.birthYear(),
                character.films().size(),
                formatDate(character.created())
        );
    }

    private String convertHeight(String height) {
        try {
            return String.valueOf(Integer.parseInt(height) / 100.0);
        } catch (NumberFormatException ex) {
            return height;
        }
    }

    private String formatDate(String created) {
        LocalDateTime dateTime = LocalDateTime.parse(created,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'"));
        return dateTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
