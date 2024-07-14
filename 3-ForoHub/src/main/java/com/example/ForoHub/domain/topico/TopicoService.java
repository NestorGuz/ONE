package com.example.ForoHub.domain.topico;

import com.example.ForoHub.domain.respuesta.DatosRespuesta;
import com.example.ForoHub.domain.respuesta.Respuesta;
import com.example.ForoHub.domain.respuesta.RespuestaRepository;
import com.example.ForoHub.domain.usuario.Usuario;
import com.example.ForoHub.domain.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public Page<DatosRespuestaTopico> listarTopicos(Pageable paginacion) {
        return topicoRepository.findAll(paginacion).map(DatosRespuestaTopico::new);
    }

    public DatosRespuestaTopico registrarTopico(DatosRegistroTopico datosRegistroTopico){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Usuario autor = (Usuario) authentication.getPrincipal();

        Topico topico = topicoRepository.save(
                new Topico(
                    null,
                    datosRegistroTopico.titulo(),
                    datosRegistroTopico.mensaje(),
                    LocalDateTime.now(),
                    Status.PUBLICADO,
                    autor
                )
        );

        return new DatosRespuestaTopico(topico);
    }

    @Transactional
    public DatosRespuestaTopico actualizarTopico(Long id, DatosActualizarTopico datosActualizarTopico) {
        Topico topico = topicoRepository.getReferenceById(id);

        if (datosActualizarTopico.titulo() != null && !datosActualizarTopico.titulo().isBlank()){
            topico.setTitulo(datosActualizarTopico.titulo());
        }
        if (datosActualizarTopico.mensaje() != null && !datosActualizarTopico.mensaje().isBlank()){
            topico.setMensaje(datosActualizarTopico.mensaje());
        }
        if (datosActualizarTopico.status() != null){
            topico.setMensaje(datosActualizarTopico.mensaje());
        }
        return new DatosRespuestaTopico(topico);
    }

    public DatosRespuestaTopico eliminarTopico(Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topicoRepository.delete(topico);
        return new DatosRespuestaTopico(topico);
    }

    public DatosRespuestaTopico mostrarTopico(Long id) {
        return new DatosRespuestaTopico(topicoRepository.getReferenceById(id));
    }
}
