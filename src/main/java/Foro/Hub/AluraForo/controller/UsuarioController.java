package Foro.Hub.AluraForo.controller;

import Foro.Hub.AluraForo.dto.UsuarioRequestDTO;
import Foro.Hub.AluraForo.dto.UsuarioResponseDTO;
import Foro.Hub.AluraForo.model.Usuario;
import Foro.Hub.AluraForo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public List<UsuarioResponseDTO> listar() {
        return usuarioRepository.findAll().stream()
                .map(u -> new UsuarioResponseDTO(u.getId(), u.getLogin(), u.getNombre()))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return usuarioRepository.findById(id)
                .map(u -> ResponseEntity.ok(new UsuarioResponseDTO(u.getId(), u.getLogin(), u.getNombre())))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        if (usuarioRepository.findByLogin(dto.getLogin()).isPresent()) {
            return ResponseEntity.badRequest().body("Ya existe un usuario con ese login.");
        }
        Usuario usuario = new Usuario();
        usuario.setLogin(dto.getLogin());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setNombre(dto.getNombre());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario.getId(), usuario.getLogin(), usuario.getNombre()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO dto) {
        var usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = usuarioOpt.get();
        usuario.setLogin(dto.getLogin());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setNombre(dto.getNombre());
        usuarioRepository.save(usuario);
        return ResponseEntity.ok(new UsuarioResponseDTO(usuario.getId(), usuario.getLogin(), usuario.getNombre()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        var usuarioOpt = usuarioRepository.findById(id);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

