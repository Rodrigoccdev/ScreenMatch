package com.rodrigoc.ScreenMatch.Dto;

import com.rodrigoc.ScreenMatch.Model.Categoria;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record SerieDto(Long id,
        String titulo,
        int totalTemporadas,
        Double evaluacion,
        String poster,
        Categoria genero,
        String actores,
        String sinopsis) {
}
