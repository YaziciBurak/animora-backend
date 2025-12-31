package com.animora.season.repository;

import com.animora.anime.entity.Anime;
import com.animora.season.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepository extends JpaRepository<Season, Long> {

    List<Season> findByAnime(Anime anime);
}
