package com.rodrigoc.ScreenMatch.Service;

import com.rodrigoc.ScreenMatch.Dto.EpisodioDto;
import com.rodrigoc.ScreenMatch.Dto.SerieDto;
import com.rodrigoc.ScreenMatch.Model.Categoria;
import com.rodrigoc.ScreenMatch.Model.Serie;
import com.rodrigoc.ScreenMatch.Repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class SerieService {
    @Autowired
    private SerieRepository repository;
    public List<SerieDto> obtenerSeries() {
        return convierteDatos(repository.findAll());
    }

    public List<SerieDto> obtenerTop5() {
        return convierteDatos(repository.findTop5ByOrderByEvaluacionDesc());
    }

    public List<SerieDto> obtenerLanzamientosRecientes(){
        return convierteDatos(repository.lanzamientosMasRecientes());
    }

    public List<SerieDto> convierteDatos(List<Serie> serie){
        return serie.stream()
                .map(s-> new SerieDto(s.getId(),s.getTitulo(), s.getTotalTemporadas(),
                        s.getEvaluacion(),s.getPoster(),s.getGenero(),s.getActores(),s.getSinopsis()))
                .collect(Collectors.toList());
    }

    public SerieDto obtenerPorId(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if(serie.isPresent()){
            Serie s = serie.get();
            return new SerieDto(s.getId(),s.getTitulo(), s.getTotalTemporadas(),
                    s.getEvaluacion(),s.getPoster(),s.getGenero(),s.getActores(),s.getSinopsis());
        }
        return null;
    }

    public List<EpisodioDto> obtenerTodasTempradas(Long id) {
        Optional<Serie> serie = repository.findById(id);
        if (serie.isPresent()){
            Serie s =serie.get();
            return s.getEpisodios().stream().map(e->new EpisodioDto(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDto> obtenerTemporadaPorNumero(Long id, Long numeroTemporada) {
        return repository.obtenerTemporadaPorNumero(id, numeroTemporada).stream()
                .map(e->new EpisodioDto(e.getTemporada(), e.getTitulo(), e.getNumeroEpisodio()))
                .collect(Collectors.toList());
    }

    public List<SerieDto> obtenerSerieCategoria(String genero) {
        Categoria categoria = Categoria.fromEspanol(genero);
        return convierteDatos(repository.findByGenero(categoria));
    }
}
