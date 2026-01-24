package com.animora.anime.exception;

public class AnimeAlreadyExistsException extends RuntimeException {

    public AnimeAlreadyExistsException(String title) {
        super("Anime already exists with title: " + title);
    }
}
