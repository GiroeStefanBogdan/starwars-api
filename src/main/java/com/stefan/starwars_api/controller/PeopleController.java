package com.stefan.starwars_api.controller;

import com.stefan.starwars_api.dto.CharacterResponseDto;
import com.stefan.starwars_api.dto.PeopleResponseDto;
import com.stefan.starwars_api.service.PeopleService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class PeopleController {

    private final PeopleService peopleService;

    public PeopleController(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @GetMapping("/people")
    public PeopleResponseDto getAllCharacters(@RequestParam(defaultValue = "1") int page) {
        return peopleService.getAllCharacters(page);
    }

    @GetMapping("/people/{id}")
    public CharacterResponseDto getCharacterById(@PathVariable int id) {
        return peopleService.getCharacterById(id);
    }
}
