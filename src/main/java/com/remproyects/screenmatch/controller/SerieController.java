package com.remproyects.screenmatch.controller;

import com.remproyects.screenmatch.dto.EpisodeDTO;
import com.remproyects.screenmatch.dto.SerieDTO;
import com.remproyects.screenmatch.models.files.Serie;
import com.remproyects.screenmatch.repository.SerieRepository;
import com.remproyects.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/series")
public class SerieController {

    @Autowired
    private SerieService serieService;

    @GetMapping("")
    public List<SerieDTO> getAllSeries() {
            return serieService.getAllSerieDTO();
    }

    @GetMapping("/top5")
    public List<SerieDTO> getTop5Series() {
        return serieService.getTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDTO> getNewSeries() {
        return serieService.getNewSeries();
    }

    @GetMapping("/{id}")
    public SerieDTO getById(@PathVariable Long id) {
        return serieService.getById(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodeDTO> getAllEpisodes(@PathVariable Long id) {
        return serieService.getEpisodesAllOf(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodeDTO> getAllEpisodes(@PathVariable Long id, @PathVariable Long numeroTemporada) {
        return serieService.getEpisodesOf(id,numeroTemporada);
    }

    @GetMapping("/categoria/{category}")
    public List<SerieDTO> getSeriesFromCategory(@PathVariable String category) {
        return serieService.getSeriesFromCategory(category);
    }

}
