package com.animora.season.exception;

public class SeasonAlreadyExistsException extends RuntimeException {

    public SeasonAlreadyExistsException(int seasonNumber) {
        super("Season " + seasonNumber + " already exists for this anime");
    }
}
