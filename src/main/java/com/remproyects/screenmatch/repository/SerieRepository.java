package com.remproyects.screenmatch.repository;

import com.remproyects.screenmatch.models.defined.Category;
import com.remproyects.screenmatch.models.files.Episode;
import com.remproyects.screenmatch.models.files.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainsIgnoreCase(String title);
    List<Serie> findTop5ByOrderByRatingDesc();
    List<Serie> findByGenre(Category category);
    List<Serie> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(Integer seasons, Double rating);
    @Query("SELECT s FROM Serie  s WHERE s.totalSeasons <= :seasons AND s.rating >= :rating")
    List<Serie> findBySeasonsAndRating(Integer seasons, Double rating);
    List<Serie> findByEpisodesTitleContainingIgnoreCase(String episodeTitle);
}
