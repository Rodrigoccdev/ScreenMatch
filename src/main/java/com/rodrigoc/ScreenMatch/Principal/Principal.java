package com.rodrigoc.ScreenMatch.Principal;

import com.rodrigoc.ScreenMatch.Model.*;
import com.rodrigoc.ScreenMatch.Repository.SerieRepository;
import com.rodrigoc.ScreenMatch.Service.ConsumoAPI;
import com.rodrigoc.ScreenMatch.Service.ConvierteDatos;

import javax.xml.transform.Source;
import java.io.DataOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private Scanner in = new Scanner(System.in);
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String APIKey = "&apikey=4fc7c187";
    private ConvierteDatos conversor = new ConvierteDatos();
    private List<DatosSerie> datosSeries = new ArrayList<>();
    private SerieRepository repositorio;

    private List<Serie> series;

    public Principal(SerieRepository repository) {
        this.repositorio = repository;
    }

    public void muestraMenu(){
        var opcion = -1;
        while (opcion!=0){
            var menu = """
                    1.-Buscar Series
                    2.-Buscar Episodios
                    3.-Mostar Series Buscadas
                    4.-Buscar Series por Titulo
                    5.-Top 5 Mejores Series
                    6.-Buscar por Categoria
                    7.-Buscar por Temporadas y Evaluacion
                    0.-Salir
                    """;
            System.out.println(menu);
            opcion = in.nextInt();
            in.nextLine();

            switch (opcion){
                case 1:
                    buscarSerieWeb(); break;
                case 2:
                    buscarEpisodio(); break;
                case 3:
                    mostrarSeriesBuscadas(); break;
                case 4:
                    buscarSerieTitulo(); break;
                case 5:
                    top5Series(); break;
                case 6:
                    buscarPorCategoria(); break;
                case 7:
                    buscarPorTemporadaYEvaluacion(); break;
                case 0:
                    System.out.println("Cerrando programa....."); break;
                default:
                    System.out.println("Opcion invalida");
            }
        }

        /*
        System.out.println("Ingresa el nombre de la serie que deseas buscar: ");
        var nombreSerie = in.nextLine();
        var json = consumoAPI.obtenerDatos(URL + nombreSerie.replace(" ","+") + APIKey);
        var datos = conversor.obtenerDatos(json, DatosSerie.class);
        System.out.println(datos);

        List<DatosTemporada> temporadas = new ArrayList<>();
        for(int i = 1; i<= datos.totalTemporadas(); i++){
            json = consumoAPI.obtenerDatos(URL + nombreSerie.replace(" ","+") + "&Season="+i + APIKey);
            var datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
            temporadas.add(datosTemporada);
        }
        temporadas.forEach(System.out::println);

        temporadas.forEach(t->t.episodios().forEach(e-> System.out.println(e.titulo())));*/
    }

    private DatosSerie getDatosSerie(){
        System.out.println("Ingrese el nombre de la serie deseada");
        var nombreSerie = in.nextLine();
        var json = consumoAPI.obtenerDatos(URL + nombreSerie.replace(" ", "+") +APIKey);
        DatosSerie datos = conversor.obtenerDatos(json, DatosSerie.class);
        return datos;
    }

    private void buscarEpisodio(){
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie deseada: ");
        var nombreSerie = in.nextLine();

        Optional<Serie> serie = series.stream()
                .filter(s->s.getTitulo().toLowerCase()
                        .contains(nombreSerie.toLowerCase())).
                findFirst();

        if (serie.isPresent()){
            var serieEncontrada = serie.get();
            List<DatosTemporada> temporadas = new ArrayList<>();
            for(int i = 1; i<= serieEncontrada.getTotalTemporadas(); i++){
                var json = consumoAPI.obtenerDatos(URL + serieEncontrada.getTitulo().replace(" ","+") + "&Season="+i + APIKey);
                var datosTemporada = conversor.obtenerDatos(json, DatosTemporada.class);
                temporadas.add(datosTemporada);
            }
            temporadas.forEach(System.out::println);
            List<Episodio> episodios = temporadas.stream()
                    .flatMap(d->d.episodios().stream()
                            .map(e -> new Episodio(d.numero(), e)))
                    .collect(Collectors.toList());
            serieEncontrada.setEpisodios(episodios);
            repositorio.save(serieEncontrada);
        }


    }

    private void buscarSerieWeb(){
        DatosSerie datos = getDatosSerie();
        //datosSeries.add(datos);
        Serie serie = new Serie(datos);
        repositorio.save(serie);
        System.out.println(datos);
    }

    private void mostrarSeriesBuscadas() {
        series = repositorio.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    private void buscarSerieTitulo(){
        mostrarSeriesBuscadas();
        System.out.println("Escribe el nombre de la serie deseada: ");
        var nombreSerie = in.nextLine();

        Optional<Serie> serieBuscada = repositorio.findByTituloContainsIgnoreCase(nombreSerie);
        if (serieBuscada.isPresent()){
            System.out.println("La serie buscada es: "+serieBuscada.get());
        }else {
            System.out.println("Serie "+nombreSerie+" no encontrada");
        }
    }

    private void top5Series(){
        List<Serie> topSeries = repositorio.findTop5ByOrderByEvaluacionDesc();
        topSeries.forEach(s-> System.out.println("Serie: "+ s.getTitulo() + ", Evaluacion: "+s.getEvaluacion()));
    }

    private void buscarPorCategoria(){
        System.out.println("Escriba la categoria deseada: ");
        var genero = in.nextLine();

        var categoria = Categoria.fromEspanol(genero);
        List<Serie> seriePorCategoria = repositorio.findByGenero(categoria);
        System.out.println("Las series de la categoria "+categoria+ " son: ");
        seriePorCategoria.forEach(System.out::println);
    }

    private void buscarPorTemporadaYEvaluacion(){
        System.out.println("Ingrese cuantas temporadas desea: ");
        var temporadas = in.nextInt();
        in.nextLine();
        System.out.println("Ingrese la evaluacion deseada: ");
        var evaluacion = in.nextDouble();
        in.nextLine();
        List<Serie> filtrosSerie = repositorio.seriesPorTemporadaYEvaluacion(evaluacion,temporadas);
        System.out.println("-Series Filtradas-");
        filtrosSerie.forEach(s-> System.out.println("Serie: "+s.getTitulo()+", Temporadas: "+s.getTotalTemporadas()+", Evaluacion: "+s.getEvaluacion()));
        System.out.println("");
    }
}
