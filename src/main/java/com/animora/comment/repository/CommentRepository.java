package com.animora.comment.repository;

import com.animora.anime.entity.Anime;
import com.animora.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByAnime(Anime anime);

    List<Comment> findByAnimeId(Long animeId);
}
