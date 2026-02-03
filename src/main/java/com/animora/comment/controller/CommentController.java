package com.animora.comment.controller;

import com.animora.comment.dto.CommentRequest;
import com.animora.comment.dto.CommentResponse;
import com.animora.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/animes")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{animeId}/comments")
    @PreAuthorize("hasAuthority('COMMENT_CREATE')")
    public ResponseEntity<CommentResponse> createComment(
            @PathVariable Long animeId,
            @Valid @RequestBody CommentRequest request
            ) {
        CommentResponse response = commentService.createComment(animeId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{animeId}/comments")
    @PreAuthorize("hasAuthority('COMMENT_READ')")
    public ResponseEntity<List<CommentResponse>> getCommentsByAnime(
            @PathVariable Long animeId
    ) {
        List<CommentResponse> responses = commentService.getCommentsByAnime(animeId);

        return ResponseEntity.ok(responses);
    }


    @DeleteMapping("/comments/{commentId}")
    @PreAuthorize("hasAuthority('COMMENT_DELETE_OWN') or hasAuthority('COMMENT_DELETE_ANY')")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId
    ) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}
