package com.example.LiterAlura.model;

import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String titulo;
    private String idioma;
    private Integer totalDescargas;

    @OneToOne(mappedBy = "libro", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Autor autor;

    public Libro() {
    }

    public Libro(DatosLibro datos) {
        this.titulo = datos.titulo();
        this.totalDescargas = datos.totalDescargas();
        Optional<DatosAutor> datosAutor = datos.autores().stream().findFirst();
        //this.autor = datosAutor.map(Autor::new).orElse(null);
        this.autor = null;
        if(datosAutor.isPresent()){
            Autor autor1 = new Autor(datosAutor.get());
            autor1.setLibro(this);
            this.autor = autor1;
        }
        Optional<String> idioma = datos.idiomas().stream().findFirst();
        this.idioma = idioma.orElse(null);
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                ", titulo='" + titulo + '\''+
                ", idioma='" + idioma + '\''+
                ", totalDescargas='" + totalDescargas +
                ", autor='" + autor + '\''
                ;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        autor.setLibro(this);
        this.autor = autor;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public Integer getTotalDescargas() {
        return totalDescargas;
    }
}
