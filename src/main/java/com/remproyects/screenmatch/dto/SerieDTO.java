package com.remproyects.screenmatch.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.remproyects.screenmatch.models.defined.Category;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record SerieDTO(
        Long id,

        @JsonProperty("titulo") String title,

        @JsonProperty("genero") Category genre,

        @JsonProperty("sinopsis") String plot,

        @JsonProperty("poster") String poster,

        @JsonProperty("actores") String actors,

        @JsonProperty("totalTemporadas") Integer totalSeasons,

        @JsonProperty("evaluacion") Double rating
) { }
