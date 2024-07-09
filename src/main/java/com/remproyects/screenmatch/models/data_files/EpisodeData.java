package com.remproyects.screenmatch.models.data_files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record EpisodeData(

        @JsonProperty("Title")   String title,

        @JsonProperty("Episode")   Integer episode,

        @JsonProperty("imdbRating")   String rating,

        @JsonProperty("Released")   String released

) {  }
