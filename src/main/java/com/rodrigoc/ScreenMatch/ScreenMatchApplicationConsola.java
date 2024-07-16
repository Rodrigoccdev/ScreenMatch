package com.rodrigoc.ScreenMatch;

import com.rodrigoc.ScreenMatch.Principal.Principal;
import com.rodrigoc.ScreenMatch.Repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenMatchApplicationConsola implements CommandLineRunner {

	@Autowired
	private SerieRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(ScreenMatchApplicationConsola.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*System.out.println("Hola mundo");
		var consumoapi = new ConsumoAPI();
		var json = consumoapi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&apikey=4fc7c187");
		var json = consumoapi.obtenerDatos("https://coffee.alexflipnote.dev/random.json");
		System.out.println(json);
		ConvierteDatos conversor = new ConvierteDatos();
		var datos = conversor.obtenerDatos(json, DatosSerie.class);
		System.out.println(datos);

		json = consumoapi.obtenerDatos("https://www.omdbapi.com/?t=game+of+thrones&Season=1&episode=1&apikey=4fc7c187");
		DatosEpisodio episodio = conversor.obtenerDatos(json, DatosEpisodio.class);
		System.out.println(episodio);*/

		Principal principal = new Principal(repository);
		principal.muestraMenu();

		/*Streams stream = new Streams();
		stream.muestraEjemplo();*/

	}
}
