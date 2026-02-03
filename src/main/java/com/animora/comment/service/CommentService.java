package com.animora.comment.service;

import com.animora.comment.dto.CommentRequest;
import com.animora.comment.dto.CommentResponse;

import java.util.List;

public interface CommentService {

    CommentResponse createComment(Long animeId, CommentRequest request);

    List<CommentResponse> getCommentsByAnime(Long animeId);

    void deleteComment(Long commentId);
}
