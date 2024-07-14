package com.example.ForoHub.domain.respuesta;

import jakarta.validation.constraints.NotBlank;

public record DatosRegistroRespuesta(@NotBlank String mensaje) {
}
