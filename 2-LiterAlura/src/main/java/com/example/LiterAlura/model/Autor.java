package com.example.LiterAlura.model;


import jakarta.persistence.*;

import java.time.DateTimeException;
import java.time.Year;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer anioDeNacimiento;
    private Integer anioDeFallecimiento;
    @OneToOne
    private Libro libro;

    public Autor(){}

    public Autor(DatosAutor datos){
        this.nombre = datos.nombre();
        this.anioDeNacimiento = datos.anioDeNacimiento();
        this.anioDeFallecimiento = datos.anioDeFallecimiento();
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", anioDeNacimiento=" + anioDeNacimiento +
                ", anioDeFallecimiento=" + anioDeFallecimiento;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getAnioDeNacimiento() {
        return anioDeNacimiento;
    }

    public void setAnioDeNacimiento(Integer anioDeNacimiento) {
        this.anioDeNacimiento = anioDeNacimiento;
    }

    public Integer getAnioDeFallecimiento() {
        return anioDeFallecimiento;
    }

    public void setAnioDeFallecimiento(Integer anioDeFallecimiento) {
        this.anioDeFallecimiento = anioDeFallecimiento;
    }

    public Libro getLibro() {
        return libro;
    }

    public void setLibro(Libro libro) {
        this.libro = libro;
    }
}
