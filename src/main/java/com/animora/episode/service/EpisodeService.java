package com.animora.episode.service;

import com.animora.episode.entity.Episode;

import java.util.List;

public interface EpisodeService {

    Episode createEpisode(Long seasonId, Episode episode);

    List<Episode> getEpisodesBySeasonId(Long seasonId);
}
