package com.example.LiterAlura.repository;

import com.example.LiterAlura.model.Autor;
import com.example.LiterAlura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.time.Year;
import java.util.List;
import java.util.Optional;

@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {
    Optional<Libro> findByTitulo(String titulo);

    @Query("SELECT a FROM Autor a")
    List<Autor> obtenerAutores();

    @Query("SELECT a FROM Autor a WHERE a.anioDeNacimiento <= :anio AND a.anioDeFallecimiento > :anio")
    List<Autor> obtenerAutoresVivosPorAnio(Integer anio);

    @Query("SELECT l.idioma FROM Libro l GROUP BY l.idioma")
    List<String> obtenerIdiomasDisponibles();

    List<Libro> findByIdioma(String idioma);
}
