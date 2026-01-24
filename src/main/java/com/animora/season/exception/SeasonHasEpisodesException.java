package com.animora.season.exception;

public class SeasonHasEpisodesException extends RuntimeException{

    public SeasonHasEpisodesException(Long seasonId) {
        super(("Season with id " + seasonId + " cannot be deleted because it has episodes"));
    }
}
