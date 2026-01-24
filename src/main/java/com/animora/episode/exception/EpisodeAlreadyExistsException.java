package com.animora.episode.exception;

public class EpisodeAlreadyExistsException extends RuntimeException{

    public EpisodeAlreadyExistsException(int episodeNumber) {
        super("Episode " + episodeNumber + " already exists in this season");
    }
}
