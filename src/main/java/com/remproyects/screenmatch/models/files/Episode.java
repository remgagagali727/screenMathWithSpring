package com.remproyects.screenmatch.models.files;

import com.remproyects.screenmatch.models.data_files.EpisodeData;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class Episode {

    private Integer season;
    private Integer episode;
    private String title;
    private Double rating;
    private LocalDate release;

    public Episode(Integer season, Integer episode, String title, Double rating, LocalDate release) {
        this.season = season;
        this.episode = episode;
        this.title = title;
        this.rating = rating;
        this.release = release;
    }

    public Episode(Integer season, EpisodeData episodeData) {
        Double rating;
        LocalDate localDate;
        try {
            rating = Double.valueOf(episodeData.rating());
        } catch (NumberFormatException e) {
            rating = 0d;
        }
        try {
            localDate = LocalDate.parse(episodeData.released());
        } catch (DateTimeParseException e) {
            localDate = null;
        }
        this.season = season;
        this.episode = episodeData.episode();
        this.title = episodeData.title();
        this.rating = rating;
        this.release = localDate;
    }

    public Integer getSeason() {
        return season;
    }

    public Integer getEpisode() {
        return episode;
    }

    public String getTitle() {
        return title;
    }

    public Double getRating() {
        return rating;
    }

    public LocalDate getRelease() {
        return release;
    }

    @Override
    public String toString() {
        return "season=" + season +
                "\n episode=" + episode +
                "\n title='" + title + '\'' +
                "\n rating=" + rating +
                "\n release=" + release;
    }
}
