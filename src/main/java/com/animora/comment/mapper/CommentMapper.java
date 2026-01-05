package com.animora.comment.mapper;

import com.animora.anime.entity.Anime;
import com.animora.comment.dto.CommentRequest;
import com.animora.comment.dto.CommentResponse;
import com.animora.comment.entity.Comment;
import com.animora.user.entity.User;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    public Comment toEntity(CommentRequest request, Anime anime, User user) {
        return Comment.builder()
                .anime(anime)
                .user(user)
                .content(request.getContent())
                .build();
    }

    public CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .userId(comment.getUser().getId())
                .userName(comment.getUser().getUserName())
                .build();
    }
}
