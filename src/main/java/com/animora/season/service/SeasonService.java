package com.animora.season.service;

import com.animora.anime.entity.Anime;
import com.animora.season.entity.Season;

import java.util.List;

public interface SeasonService {

    Season createSeason(Anime anime, int seasonNumber);

    List<Season> getSeasonsByAnimeId(Long animeId);
}
