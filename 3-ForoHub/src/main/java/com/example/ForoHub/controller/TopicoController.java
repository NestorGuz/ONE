package com.example.ForoHub.controller;

import com.example.ForoHub.domain.topico.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class TopicoController {

    @Autowired
    private TopicoService topicoService;

    @GetMapping
    @Operation(
            summary = "Mostrar todos los tópicos registrados.",
            description = "",
            tags = { "Topico"})
    public ResponseEntity<Page<DatosRespuestaTopico>> listar(@PageableDefault(size = 10) Pageable paginacion) {
        return ResponseEntity.ok(topicoService.listarTopicos(paginacion));
    }


    @GetMapping("/{id}")
    @Operation(
            summary = "Mostrar un tópico en específico.",
            description = "",
            tags = { "Topico"})
    public ResponseEntity<DatosRespuestaTopico> mostrar(@PathVariable Long id){
        return ResponseEntity.ok(topicoService.mostrarTopico(id));
    }

    @PostMapping
    @Operation(
            summary = "Registrar un nuevo tópico en la base de datos.",
            description = "",
            tags = { "Topico"})
    //@Schema(example = "") // Disable example value
    @ApiResponses(value = {
            @ApiResponse(responseCode = "401", description = "El token de autorización no se ha proporcionado o ha caducado.", content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "200", description = "Se ha registrado el tópico."),
            @ApiResponse(responseCode = "400", description = "No se ha podido registrar el tópico debido a que faltan algunos datos.", content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<DatosRespuestaTopico> registrar(@RequestBody @Valid DatosRegistroTopico datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        DatosRespuestaTopico datosTopico = topicoService.registrarTopico(datosRegistroTopico);
        URI url = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(datosTopico.id()).toUri();
        return ResponseEntity.created(url).body(datosTopico);
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar los datos de un tópico.",
            description = "",
            tags = { "Topico" })
    public ResponseEntity<DatosRespuestaTopico> actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico){
        return ResponseEntity.ok(topicoService.actualizarTopico(id, datosActualizarTopico));
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un tópico específico de la base de datos.",
            description = "",
            tags = { "Topico"})
    public ResponseEntity<DatosRespuestaTopico> eliminar(@PathVariable Long id){
        topicoService.eliminarTopico(id);
        return ResponseEntity.noContent().build();
    }
}
