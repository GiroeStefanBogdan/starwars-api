package com.stefan.starwars_api.exception;

public class CharacterNotFoundException extends RuntimeException {
    public CharacterNotFoundException(int id) {
        super("Character with id " + id + " not found.");
    }
}
