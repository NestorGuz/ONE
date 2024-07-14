package com.example.ForoHub.domain.topico;

import java.time.LocalDateTime;

public record DatosRespuestaTopico(Long id, String titulo, String mensaje, LocalDateTime fechaDeCreacion, Status status, DatosRespuestaAutor autor) {
    public DatosRespuestaTopico(Topico topico) {
        this(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaDeCreacion(), topico.getStatus(), new DatosRespuestaAutor(topico.getUsuario()));
    }
}
