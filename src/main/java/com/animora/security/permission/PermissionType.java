package com.animora.security.permission;

public enum PermissionType {

    ANIME_CREATE,
    ANIME_UPDATE,
    ANIME_DELETE,

    COMMENT_CREATE,
    COMMENT_DELETE_ANY,
    COMMENT_DELETE_OWN,

    FAVORITE_ADD,
    FAVORITE_DELETE_OWN,
    FAVORITE_DELETE_ANY,
}
