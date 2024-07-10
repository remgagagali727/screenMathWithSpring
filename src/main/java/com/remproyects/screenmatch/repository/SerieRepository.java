package com.remproyects.screenmatch.repository;

import com.remproyects.screenmatch.models.defined.Category;
import com.remproyects.screenmatch.models.files.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainsIgnoreCase(String title);
    List<Serie> findTop5ByOrderByRatingDesc();
    List<Serie> findByGenre(Category category);
    List<Serie> findByTotalSeasonsLessThanEqualAndRatingGreaterThanEqual(Integer seasons, Double rating);
}
