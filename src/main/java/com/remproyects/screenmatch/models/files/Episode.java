package com.remproyects.screenmatch.models.files;

import com.remproyects.screenmatch.models.data_files.EpisodeData;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@Entity
@Table(name = "episodes")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private Integer season;

    private Integer episode;

    private String title;

    private Double rating;

    private LocalDate release;

    @ManyToOne
    private Serie serie;

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

    public Long getID() {
        return ID;
    }

    public Serie getSerie() {
        return serie;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }

    public Episode() { }

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

    @Override
    public String toString() {
        return "season=" + season +
                ", episode=" + episode +
                ", title='" + title + '\'' +
                ", rating=" + rating +
                ", release=" + release;
    }
}
