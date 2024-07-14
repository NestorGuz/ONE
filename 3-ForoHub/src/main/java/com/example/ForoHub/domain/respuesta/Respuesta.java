package com.example.ForoHub.domain.respuesta;

import com.example.ForoHub.domain.topico.Topico;
import com.example.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String mensaje;
    private LocalDateTime fechaDeCreacion;
    private Boolean solucion;
    @ManyToOne
    @JoinColumn(name="topico_id", nullable=false)
    @Setter(AccessLevel.NONE)
    private Topico topico;
    @ManyToOne
    @JoinColumn(name="autor_id", nullable=false)
    @Setter(AccessLevel.NONE)
    private Usuario autor;
}
