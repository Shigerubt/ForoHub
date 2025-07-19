package Foro.Hub.AluraForo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RespuestaRequestDTO {
    @NotBlank
    private String mensaje;
    @NotNull
    private Long topicoId;
    @NotNull
    private Long autorId;

    public String getMensaje() { return mensaje; }
    public void setMensaje(String mensaje) { this.mensaje = mensaje; }
    public Long getTopicoId() { return topicoId; }
    public void setTopicoId(Long topicoId) { this.topicoId = topicoId; }
    public Long getAutorId() { return autorId; }
    public void setAutorId(Long autorId) { this.autorId = autorId; }
}

