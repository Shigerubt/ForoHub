package Foro.Hub.AluraForo.dto;

import java.time.LocalDateTime;

public class RespuestaResponseDTO {
    private Long id;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private Long topicoId;
    private Long autorId;

    public RespuestaResponseDTO(Long id, String mensaje, LocalDateTime fechaCreacion, Long topicoId, Long autorId) {
        this.id = id;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.topicoId = topicoId;
        this.autorId = autorId;
    }

    public Long getId() { return id; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public Long getTopicoId() { return topicoId; }
    public Long getAutorId() { return autorId; }
}

