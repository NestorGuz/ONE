package com.example.ForoHub.domain.respuesta;

import com.example.ForoHub.domain.topico.DatosRespuestaTopico;
import com.example.ForoHub.domain.topico.Status;
import com.example.ForoHub.domain.topico.Topico;
import com.example.ForoHub.domain.topico.TopicoRepository;
import com.example.ForoHub.domain.usuario.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class RespuestaService {
    private final RespuestaRepository respuestaRepository;
    private final TopicoRepository topicoRepository;

    public RespuestaService(RespuestaRepository respuestaRepository, TopicoRepository topicoRepository) {
        this.respuestaRepository = respuestaRepository;
        this.topicoRepository = topicoRepository;
    }

    public Page<DatosRespuesta> listarRespuestasDelTopico(Long topicoId, Pageable pageable){
        return respuestaRepository.findByTopicoId(topicoId, pageable).map(DatosRespuesta::new);
    }

    public DatosRespuesta agregarRespuestaAlTopico(Long topicoId, DatosRegistroRespuesta datosRegistroRespuesta) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario autor = (Usuario) authentication.getPrincipal();
        Topico topico = topicoRepository.getReferenceById(topicoId);
        System.out.println(topico);
        Respuesta respuesta = respuestaRepository.save(
                new Respuesta(
                        null,
                        datosRegistroRespuesta.mensaje(),
                        LocalDateTime.now(),
                        false,
                        topico,
                        autor
                )
        );

        return new DatosRespuesta(respuesta);
    }

    public DatosRespuesta mostrarRespuestaDelTopico(Long id) {
        return new DatosRespuesta(respuestaRepository.getReferenceById(id));
    }

    @Transactional
    public DatosRespuesta actualizarRespuesta(Long id, DatosActualizarRespuesta datosActualizarRespuesta) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        if(datosActualizarRespuesta.mensaje() != null && !datosActualizarRespuesta.mensaje().isBlank()){
            respuesta.setMensaje(datosActualizarRespuesta.mensaje());
        }
        if(datosActualizarRespuesta.solucion() != null){
            respuesta.setSolucion(datosActualizarRespuesta.solucion());
        }
        return new DatosRespuesta(respuesta);
    }

    public DatosRespuesta eliminarRespuesta(Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuestaRepository.delete(respuesta);
        return new DatosRespuesta(respuesta);
    }
}
