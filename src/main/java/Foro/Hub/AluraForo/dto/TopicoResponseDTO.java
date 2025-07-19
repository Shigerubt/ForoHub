package Foro.Hub.AluraForo.dto;

import java.time.LocalDateTime;

public class TopicoResponseDTO {
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion;
    private String status;
    private String autor;
    private String curso;

    public TopicoResponseDTO(Long id, String titulo, String mensaje, LocalDateTime fechaCreacion, String status, String autor, String curso) {
        this.id = id;
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.fechaCreacion = fechaCreacion;
        this.status = status;
        this.autor = autor;
        this.curso = curso;
    }

    // Getters
    public Long getId() { return id; }
    public String getTitulo() { return titulo; }
    public String getMensaje() { return mensaje; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }
    public String getStatus() { return status; }
    public String getAutor() { return autor; }
    public String getCurso() { return curso; }
}

