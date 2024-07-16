package com.rodrigoc.ScreenMatch.Controler;

import com.rodrigoc.ScreenMatch.Dto.EpisodioDto;
import com.rodrigoc.ScreenMatch.Dto.SerieDto;
import com.rodrigoc.ScreenMatch.Model.Episodio;
import com.rodrigoc.ScreenMatch.Model.Serie;
import com.rodrigoc.ScreenMatch.Repository.SerieRepository;
import com.rodrigoc.ScreenMatch.Service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/series")
public class SerieControler {
    @Autowired
    private SerieService service;

    @GetMapping()
    public List<SerieDto> mostrarMensaje() {
        return service.obtenerSeries();
    }

    @GetMapping("/top5")
    public List<SerieDto> obtenerTop5(){
        return service.obtenerTop5();
    }

    @GetMapping("/lanzamientos")
    public List<SerieDto> obtenerLanzamientosRecientes(){
        return service.obtenerLanzamientosRecientes();
    }

    @GetMapping("/{id}")
    public SerieDto obtenerPorId(@PathVariable Long id){
        return service.obtenerPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDto> obtenerTodasTemporadas(@PathVariable Long id){
        return service.obtenerTodasTempradas(id);
    }

    @GetMapping("/{id}/temporadas/{numeroTemporada}")
    public List<EpisodioDto> obtenerTemporadaPorNumero(@PathVariable Long id, @PathVariable Long numeroTemporada){
        return service.obtenerTemporadaPorNumero(id, numeroTemporada);
    }

    @GetMapping("categoria/{categoriaSerie}")
    public List<SerieDto> obtenerSerieCategoria(@PathVariable String categoriaSerie){
        return service.obtenerSerieCategoria(categoriaSerie);
    }
}
