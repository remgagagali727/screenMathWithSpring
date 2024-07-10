package com.remproyects.screenmatch.models.files;

import com.remproyects.screenmatch.models.data_files.SerieData;
import com.remproyects.screenmatch.models.defined.Category;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;

import java.util.List;

@Entity
@Table(name = "series")
public class Serie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String title;

    @Enumerated(EnumType.STRING)
    private Category genre;

    private String plot;

    private String poster;

    private String actors;

    private Integer totalSeasons;

    private Double rating;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL
        ,fetch = FetchType.EAGER)
    private List<Episode> episodes;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Category getGenre() {
        return genre;
    }

    public String getPlot() {
        return plot;
    }

    public String getPoster() {
        return poster;
    }

    public String getActors() {
        return actors;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setEpisodes(List<Episode> episodes) {
        episodes.stream()
                .forEach(E -> E.setSerie(this));
        this.episodes = episodes;
    }

    public Serie() { }

    public Serie(SerieData serieData) {
        this.title = serieData.title();
        this.plot = serieData.plot();
        this.poster = serieData.plot();
        this.actors = serieData.actors();
        this.totalSeasons = serieData.totalSeasons();
        this.rating = serieData.rating();
        this.genre = Category.fromString(serieData.genre().split(", ")[0]);
    }

    @Override
    public String toString() {
        return "title='" + title + '\'' +
                ", genre=" + genre +
                ", plot='" + plot + '\'' +
                ", poster='" + poster + '\'' +
                ", actors='" + actors + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
                ", episodes=" + episodes ;
    }
}
