package com.remproyects.screenmatch.repository;

import com.remproyects.screenmatch.models.files.Episode;
import com.remproyects.screenmatch.models.files.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {
    List<Episode> findByTitleContainingIgnoreCase(String episodeTitle);
    List<Episode> findTop5BySerieOrderByRatingDesc(Serie serie);
}
