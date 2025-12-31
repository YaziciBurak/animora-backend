package com.animora.comment.service.impl;

import com.animora.anime.entity.Anime;
import com.animora.anime.repository.AnimeRepository;
import com.animora.comment.entity.Comment;
import com.animora.comment.repository.CommentRepository;
import com.animora.comment.service.CommentService;
import com.animora.user.entity.User;
import com.animora.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final AnimeRepository animeRepository;
    private final UserRepository userRepository;

    public CommentServiceImpl(CommentRepository commentRepository,
                              AnimeRepository animeRepository,
                              UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.animeRepository = animeRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Comment addComment(Long animeId, Long userId, String content) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Comment comment = Comment.builder()
                .anime(anime)
                .user(user)
                .content(content)
                .createdAt(LocalDate.now())
                .build();

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsByAnimeId(Long animeId) {
        Anime anime = animeRepository.findById(animeId)
                .orElseThrow(() -> new EntityNotFoundException("Anime not found"));

        return commentRepository.findByAnime(anime);
    }
}
