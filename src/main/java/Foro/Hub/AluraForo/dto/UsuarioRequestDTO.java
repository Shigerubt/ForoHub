package Foro.Hub.AluraForo.dto;

import jakarta.validation.constraints.NotBlank;

public class UsuarioRequestDTO {
    @NotBlank
    private String login;
    @NotBlank
    private String password;
    @NotBlank
    private String nombre;

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
}

