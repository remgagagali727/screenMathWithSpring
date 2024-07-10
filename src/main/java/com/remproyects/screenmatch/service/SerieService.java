package com.remproyects.screenmatch.service;

import com.remproyects.screenmatch.dto.EpisodeDTO;
import com.remproyects.screenmatch.dto.SerieDTO;
import com.remproyects.screenmatch.models.defined.Category;
import com.remproyects.screenmatch.models.files.Episode;
import com.remproyects.screenmatch.models.files.Serie;
import com.remproyects.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    private SerieDTO serieToSerieDTO(Serie S) {
        return new SerieDTO(S.getId(),S.getTitle(), S.getGenre(), S.getPlot(), S.getPoster()
                                ,S.getActors(),S.getTotalSeasons(), S.getRating());
    }

    private List<SerieDTO> serieToSerieDTO(List<Serie> series) {
        return series.stream()
                .map(this::serieToSerieDTO)
                .toList();
    }

    public EpisodeDTO episodeToEpisodeDTO(Episode episode) {
        return new EpisodeDTO(episode.getID(),episode.getTitle(),episode.getSeason(),episode.getEpisode());
    }

    public List<EpisodeDTO> episodeToEpisodeDTO(List<Episode> episodes) {
        return episodes.stream()
                .map(this::episodeToEpisodeDTO)
                .toList();
    }

    public List<SerieDTO> getAllSerieDTO() {
        return serieToSerieDTO(serieRepository.findAll());
    }

    public List<SerieDTO> getTop5() {
        return serieToSerieDTO(serieRepository.findTop5ByOrderByRatingDesc());
    }

    public List<SerieDTO> getNewSeries() {
        return serieToSerieDTO(serieRepository.findTop5ByOrderByEpisodesReleaseDesc());
    }

    public SerieDTO getById(Long id) {
        Optional<Serie> serieOptional = serieRepository.findById(id);
        return serieOptional.map(this::serieToSerieDTO).orElse(null);
    }

    public List<EpisodeDTO> getEpisodesOf(Long id, Long season) {
        return episodeToEpisodeDTO(serieRepository.getEpisodesBySeasonNumber(id, season));
    }

    public List<EpisodeDTO> getEpisodesAllOf(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);
        if(serie.isPresent()) {
            return episodeToEpisodeDTO(serie.get().getEpisodes());
        }
        return null;
    }

    public List<SerieDTO> getSeriesFromCategory(String category) {
        return serieToSerieDTO(serieRepository.findByGenre(Category.fromString(category)));

    }
}
