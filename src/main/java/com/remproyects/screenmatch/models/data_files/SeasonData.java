package com.remproyects.screenmatch.models.data_files;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(

        @JsonProperty("Season") Integer season,

        @JsonProperty("Episodes") List<EpisodeData> episodes
) { }
