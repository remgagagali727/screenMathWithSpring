package com.remproyects.screenmatch.models.data_files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SerieData(

        @JsonProperty("Title") String title,

        @JsonProperty("Genre") String genre,

        @JsonProperty("Plot") String plot,

        @JsonProperty("Poster") String poster,

        @JsonProperty("Actors") String actors,

        Integer totalSeasons,

        @JsonProperty("imdbRating") Double rating

) {  }
