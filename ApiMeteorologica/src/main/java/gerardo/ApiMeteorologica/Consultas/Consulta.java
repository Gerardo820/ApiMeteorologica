package gerardo.ApiMeteorologica.Consultas;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name = "consultas")
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "solicitud", nullable = false)
    private String solicitud;

    @Column(name = "respuesta", columnDefinition = "TEXT", nullable = false)
    private String respuesta;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "usuario_id", nullable = false)
    private int usuarioId;

}


