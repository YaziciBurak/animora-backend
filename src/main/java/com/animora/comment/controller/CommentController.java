package com.animora.comment.controller;

import com.animora.comment.dto.CommentRequest;
import com.animora.comment.dto.CommentResponse;
import com.animora.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/animes/{animeId}/comments")
    public ResponseEntity<CommentResponse>createComment(
            @PathVariable Long animeId,
            @RequestParam Long userId,
            @Valid @RequestBody CommentRequest request
            ) {
        CommentResponse response = commentService.createComment(animeId, userId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/animes/{animeId}/comments")
    public ResponseEntity<List<CommentResponse>> getCommentsByAnime(
            @PathVariable Long animeId
    ) {
        List<CommentResponse> responses = commentService.getCommentsByAnime(animeId);

        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
