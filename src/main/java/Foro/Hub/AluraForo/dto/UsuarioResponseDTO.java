package Foro.Hub.AluraForo.dto;

public class UsuarioResponseDTO {
    private Long id;
    private String login;
    private String nombre;

    public UsuarioResponseDTO(Long id, String login, String nombre) {
        this.id = id;
        this.login = login;
        this.nombre = nombre;
    }

    public Long getId() { return id; }
    public String getLogin() { return login; }
    public String getNombre() { return nombre; }
}

