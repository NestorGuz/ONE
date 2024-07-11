package com.example.LiterAlura.principal;

import com.example.LiterAlura.model.Autor;
import com.example.LiterAlura.model.Datos;
import com.example.LiterAlura.model.DatosLibro;
import com.example.LiterAlura.model.Libro;
import com.example.LiterAlura.repository.AutorRepository;
import com.example.LiterAlura.repository.LibroRepository;
import com.example.LiterAlura.service.ConsumoAPI;
import com.example.LiterAlura.service.ConvierteDatos;

import java.util.*;

public class Principal {
    private Scanner teclado = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/books/?search=";
    private ConvierteDatos conversor = new ConvierteDatos();
    private LibroRepository repository;
    private AutorRepository autorRepository;

    public Principal() {
    }

    public Principal(LibroRepository repository, AutorRepository autorRepository) {
        this.repository = repository;
        this.autorRepository = autorRepository;
    }

    public void muestraElMenu() {
        String opcion = "-1";
        while (!opcion.equals("0")) {
            var menu = """
                    1 - Buscar libro
                    2 - Mostrar libros registrados
                    3 - Mostrar autores registrados
                    4 - Mostrar autores vivos en un determinado año
                    5 - Mostrar libros por idioma
                    6 - Mostrar estadísticas de libros por idioma              
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = teclado.nextLine();

            switch (opcion) {
                case "1":
                    buscarLibroWeb();
                    break;
                case "2":
                    mostrarLibros();
                    break;
                case "3":
                    mostrarAutores();
                    break;
                case "4":
                    mostrarAutoresVivosPorAnio();
                    break;
                case "5":
                    mostrarLibrosPorIdioma();
                    break;
                case "6":
                    mostrarEstadisticasDeLibrosPorIdioma();
                    break;
                case "0":
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }

    }

    private void mostrarLibrosPorIdioma() {
        List<String> idiomasDisponibles = repository.obtenerIdiomasDisponibles();

        if (idiomasDisponibles.isEmpty()){
            System.out.println("Por el momento no se cuenta con idiomas disponibles");
            return;
        }
        System.out.println("Idiomas disponibles:");
        idiomasDisponibles.forEach(System.out::println);
        System.out.println("Escribe el idioma a buscar");
        var idioma = teclado.nextLine();

        if(!idiomasDisponibles.contains(idioma)){
            System.out.println("Idioma ingresado no disponible");
            return;
        }

        List<Libro> libros = repository.findByIdioma(idioma);
        if (libros.isEmpty()){
            System.out.println("No hay libros en el idioma especificado");
            return;
        }

        libros.forEach(System.out::println);
    }

    public void mostrarEstadisticasDeLibrosPorIdioma(){
        List<String> idiomasDisponibles = repository.obtenerIdiomasDisponibles();

        if (idiomasDisponibles.isEmpty()){
            System.out.println("Por el momento no se cuenta con idiomas disponibles");
            return;
        }

        System.out.println("Idiomas disponibles:");
        idiomasDisponibles.forEach(System.out::println);

        System.out.println("Escribe el idioma del cual desea saber las estádiscas");

        var idioma = teclado.nextLine();

        if(!idiomasDisponibles.contains(idioma)){
            System.out.println("Idioma ingresado no disponible");
            return;
        }

        List<Libro> libros = repository.findAll();

        IntSummaryStatistics est = libros.stream().filter(libro -> libro.getIdioma().equals(idioma)).mapToInt(libro -> 1).summaryStatistics();

        System.out.println("Idioma: " + idioma);
        System.out.println("Total de libros en este idioma: " + est.getCount());
    }

    private Optional<DatosLibro> getDatosLibro() {
        System.out.println("Escribe el nombre del libro que deseas buscar");
        var nombreLibro = teclado.nextLine();
        var json = consumoApi.obtenerDatos(URL_BASE + nombreLibro.replace(" ", "+"));

        Datos datosBusqueda = conversor.obtenerDatos(json, Datos.class);

        return datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(nombreLibro.toUpperCase()))
                .findFirst();
    }

    private void mostrarLibros() {
        List<Libro> libros  = repository.findAll();
        if (libros.isEmpty()){
            System.out.println("Sin libros para mostrar");
            return;
        }
        libros.forEach(System.out::println);
    }

    private void buscarLibroWeb() {
        Optional<DatosLibro> datos = getDatosLibro();
        if(datos.isPresent()){
            Libro libro = new Libro(datos.get());

            Optional<Libro> libroEnBD = repository.findByTitulo(datos.get().titulo());

            if(libroEnBD.isEmpty()){
                repository.save(libro);
            }

            System.out.println("Libro Encontrado ");
            System.out.println(datos.get());

        }else {
            System.out.println("Libro no encontrado");
        }
    }

    private void mostrarAutores() {
        List<Autor> autores = repository.obtenerAutores();
        if (autores.isEmpty()){
            System.out.println("Aún no se ha registrado ningun autor");
            return;
        }
        autores.forEach(System.out::println);
    }

    private void mostrarAutoresVivosPorAnio(){
        System.out.println("Escribe el año en el que deseas consultar que autores estaban vivos");
        var anioBuscado = teclado.nextInt();
        teclado.nextLine();

        //List<Autor> autores = repository.obtenerAutoresVivosPorAnio(anioBuscado);
        List<Autor> autores = autorRepository.findByAnioDeNacimientoLessThanEqualAndAnioDeFallecimientoGreaterThan(anioBuscado, anioBuscado);
        if (autores.isEmpty()){
            System.out.println("Sin autores vivos en el año especificado");
            return;
        }
        autores.forEach(System.out::println);
    }
}

