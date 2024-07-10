package com.remproyects.screenmatch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EpisodeDTO(
        Long id,
        @JsonProperty("titulo") String title,
        @JsonProperty("temporada") Integer season,
        @JsonProperty("numeroEpisodio") Integer episode

) {  }
