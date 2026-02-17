package com.animora.common.exception.enums;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    ANIME_ALREADY_EXISTS("Anime already exists with title: %s"),
    ANIME_NOT_FOUND("Anime not found with id: %d"),
    ANIME_HAS_SEASONS("Anime has seasons and cannot be deleted. Anime id: %d"),

    EPISODE_NOT_IN_SEASON("Episode with id: %d does not belong to season with id: %d"),
    EPISODE_ALREADY_EXISTS("Episode already exists with this episode number: %d"),
    EPISODE_NOT_FOUND("Episode not found with id: %d"),

    SEASON_NOT_FOUND("Season not found: %d"),
    SEASON_ALREADY_EXISTS("Season already exists with this season number: %d"),
    SEASON_HAS_EPISODES("Season has episodes and cannot be deleted. Season id: %d"),
    SEASON_DOES_NOT_BELONG_TO_ANIME("Season with id: %d does not belong to anime with id: %d"),

    COMMENT_NOT_FOUND("Comment not found with id: %d"),
    FORBIDDEN_COMMENT_DELETE("You are not allowed to delete this comment"),

    FAVORITE_NOT_FOUND("Favorite not found for user id: %d and anime id: %d"),
    FAVORITE_ALREADY_EXISTS("Anime already in favorites. userId: %d, animeId: %d"),
    FAVORITE_FORBIDDEN("You are not allowed to perform this action on favorite"),

    USER_NOT_FOUND("User not found with id: %d"),

    ROLE_NOT_FOUND("Role not found: %s"),
    USER_NOT_AUTHENTICATED("User not authenticated"),
    INVALID_PRINCIPAL_TYPE("Invalid principal type"),

    GENRE_ALREADY_EXISTS("Genre already exists with the name: %s"),
    GENRE_NOT_FOUND("Genre not found with id: %d");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }

}


