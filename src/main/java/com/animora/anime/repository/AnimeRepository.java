package com.animora.anime.repository;

import com.animora.anime.entity.Anime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AnimeRepository extends JpaRepository<Anime, Long> {
    boolean existsByTitle(String title);

    @Query("""
    SELECT DISTINCT a
    FROM Anime a
    LEFT JOIN FETCH a.genres
    LEFT JOIN FETCH a.seasons s
    LEFT JOIN FETCH s.episodes
    WHERE a.id = :id
""")
Optional<Anime> findByIdWithDetails(@Param("id") Long id);
}
