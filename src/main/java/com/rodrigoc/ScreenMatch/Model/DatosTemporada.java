package com.rodrigoc.ScreenMatch.Model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)

public record DatosTemporada(
        @JsonAlias("Season") String temporada,
        @JsonAlias("Episodes") List<DatosEpisodio> episodios,
        @JsonAlias("Season") int numero
) {
}
