package com.example.ForoHub.domain.topico;

import com.example.ForoHub.domain.usuario.Usuario;

public record DatosRespuestaAutor(String nombre) {
    public DatosRespuestaAutor(Usuario autor) {
        this(autor.getCuenta());
    }
}
