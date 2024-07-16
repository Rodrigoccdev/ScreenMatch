package com.rodrigoc.ScreenMatch.Repository;

import com.rodrigoc.ScreenMatch.Dto.EpisodioDto;
import com.rodrigoc.ScreenMatch.Model.Categoria;
import com.rodrigoc.ScreenMatch.Model.Episodio;
import com.rodrigoc.ScreenMatch.Model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloContainsIgnoreCase(String nombreSerie);

    List<Serie> findTop5ByOrderByEvaluacionDesc();

    List<Serie> findByGenero(Categoria categoria);

    @Query(value = "SELECT * FROM series WHERE series.evaluacion >= :evaluacion AND series.total_temporadas <= :totalTemporadas", nativeQuery = true)
    List<Serie> seriesPorTemporadaYEvaluacion(Double evaluacion, int totalTemporadas);

    @Query(value = "SELECT s FROM Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.fechaLanzamiento) DESC LIMIT 5")
    List<Serie> lanzamientosMasRecientes();

    @Query(value = "SELECT e FROM Serie s JOIN s.episodios e WHERE s.id= :id AND e.temporada = :numero")
    List<Episodio> obtenerTemporadaPorNumero(Long id, Long numero);


}
