package com.example.ForoHub.domain.respuesta;

import com.example.ForoHub.domain.topico.DatosRespuestaAutor;

import java.time.LocalDateTime;

public record DatosRespuesta(Long id, String mensaje, LocalDateTime fechaDeCreacion, Boolean solucion, DatosRespuestaAutor autor) {
    public DatosRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getFechaDeCreacion(), respuesta.getSolucion(), new DatosRespuestaAutor(respuesta.getAutor()));
    }
}
