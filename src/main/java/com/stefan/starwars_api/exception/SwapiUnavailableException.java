package com.stefan.starwars_api.exception;

public class SwapiUnavailableException extends RuntimeException {
    public SwapiUnavailableException() {
        super("SWAPI is currently unavailable. Please try again later.");
    }
}
