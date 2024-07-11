package com.example.LiterAlura.repository;

import com.example.LiterAlura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface AutorRepository extends JpaRepository<Autor,Long> {
    List<Autor> findByAnioDeNacimientoLessThanEqualAndAnioDeFallecimientoGreaterThan(Integer anioDeNacimiento, Integer anioDeFallecimiento);
}
