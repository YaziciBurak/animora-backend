package com.animora.comment.service;

import com.animora.comment.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment addComment(Long animeId, Long userId, String content);

    List<Comment> getCommentsByAnimeId(Long animeId);
}
