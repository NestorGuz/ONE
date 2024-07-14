package com.example.ForoHub.controller;

import com.example.ForoHub.domain.respuesta.DatosActualizarRespuesta;
import com.example.ForoHub.domain.respuesta.DatosRegistroRespuesta;
import com.example.ForoHub.domain.respuesta.DatosRespuesta;
import com.example.ForoHub.domain.respuesta.RespuestaService;
import com.example.ForoHub.domain.topico.DatosRespuestaTopico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos/{topicoId}/respuestas")
@SecurityRequirement(name = "bearer-key")
public class TopicoRespuestasController {
    private final RespuestaService respuestaService;

    public TopicoRespuestasController(RespuestaService respuestaService) {
        this.respuestaService = respuestaService;
    }

    @GetMapping
    @Operation(
            summary = "Mostrar las respuestas de un tópico.",
            description = "",
            tags = { "Topico/Respuestas"})
    public ResponseEntity<Page<DatosRespuesta>> listarRespuestasDelTopico(
            @Parameter(description = "ID del tópico") @PathVariable Long topicoId,
            @RequestParam(required = false) @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(respuestaService.listarRespuestasDelTopico(topicoId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Mostrar una respuesta específica de un tópico.",
            description = "",
            tags = { "Topico/Respuestas"})
    public ResponseEntity<DatosRespuesta> mostrarRespuestaDelTopico(
            @Parameter(description = "ID del tópico") @PathVariable Long topicoId,
            @Parameter(description = "ID de la respuesta") @PathVariable Long id){
        return ResponseEntity.ok(respuestaService.mostrarRespuestaDelTopico(id));
    }

    @PostMapping
    @Operation(
            summary = "Agregar una nueva respuesta a un tópico.",
            description = "",
            tags = { "Topico/Respuestas"})
    public ResponseEntity<DatosRespuesta> agregarRespuestaAlTopico(
            @Parameter(description = "ID del tópico") @PathVariable Long topicoId,
            @RequestBody @Valid DatosRegistroRespuesta datosRegistroRespuesta,
            UriComponentsBuilder uriComponentsBuilder) {
        DatosRespuesta datosRespuesta = respuestaService.agregarRespuestaAlTopico(topicoId, datosRegistroRespuesta);
        URI url = uriComponentsBuilder.path("/topicos/{topicoId}/respuestas/{id}").buildAndExpand(topicoId, datosRespuesta.id()).toUri();
        return ResponseEntity.created(url).body(datosRespuesta);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar los datos de una respuesta específica de un tópico.",
            description = "",
            tags = { "Topico/Respuestas"})
    public ResponseEntity<DatosRespuesta> actualizarRespuestaDelTopico(
            @Parameter(description = "ID del tópico") @PathVariable Long topicoId,
            @Parameter(description = "ID de la respuesta") @PathVariable Long id,
            @RequestBody @Valid DatosActualizarRespuesta datosActualizarRespuesta){
        return ResponseEntity.ok(respuestaService.actualizarRespuesta(id, datosActualizarRespuesta));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar una respuesta específica de un tópico.",
            description = "",
            tags = { "Topico/Respuestas"})
    public ResponseEntity<DatosRespuestaTopico> eliminarRespuesta(
            @Parameter(description = "ID del tópico") @PathVariable Long topicoId,
            @Parameter(description = "ID de la respuesta") @PathVariable Long id){
        respuestaService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }
}
