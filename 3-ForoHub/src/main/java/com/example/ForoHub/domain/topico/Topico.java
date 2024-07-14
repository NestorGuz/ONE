package com.example.ForoHub.domain.topico;

import com.example.ForoHub.domain.respuesta.Respuesta;
import com.example.ForoHub.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "topicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;
    private String titulo;
    private String mensaje;
    @Setter(AccessLevel.NONE)
    private LocalDateTime fechaDeCreacion;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Setter(AccessLevel.NONE)
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="autor_id", nullable=false)
    Usuario usuario;
}
