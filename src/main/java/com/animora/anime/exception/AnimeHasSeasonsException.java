package com.animora.anime.exception;

public class AnimeHasSeasonsException extends RuntimeException{

    public AnimeHasSeasonsException(Long animeId) {
        super("Anime has seasons and cannot be deleted. Anime id: " + animeId);
    }
}
