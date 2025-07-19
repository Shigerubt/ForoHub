package Foro.Hub.AluraForo.controller;

import Foro.Hub.AluraForo.dto.RespuestaRequestDTO;
import Foro.Hub.AluraForo.dto.RespuestaResponseDTO;
import Foro.Hub.AluraForo.model.Respuesta;
import Foro.Hub.AluraForo.model.Topico;
import Foro.Hub.AluraForo.model.Usuario;
import Foro.Hub.AluraForo.repository.RespuestaRepository;
import Foro.Hub.AluraForo.repository.TopicoRepository;
import Foro.Hub.AluraForo.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/respuestas")
public class RespuestaController {
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping
    public List<RespuestaResponseDTO> listar() {
        return respuestaRepository.findAll().stream()
                .map(r -> new RespuestaResponseDTO(
                        r.getId(),
                        r.getMensaje(),
                        r.getFechaCreacion(),
                        r.getTopico().getId(),
                        r.getAutor().getId()
                ))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return respuestaRepository.findById(id)
                .map(r -> ResponseEntity.ok(new RespuestaResponseDTO(
                        r.getId(),
                        r.getMensaje(),
                        r.getFechaCreacion(),
                        r.getTopico().getId(),
                        r.getAutor().getId()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody @Valid RespuestaRequestDTO dto) {
        Topico topico = topicoRepository.findById(dto.getTopicoId()).orElse(null);
        Usuario autor = usuarioRepository.findById(dto.getAutorId()).orElse(null);
        if (topico == null || autor == null) {
            return ResponseEntity.badRequest().body("Topico o autor no encontrado");
        }
        Respuesta respuesta = new Respuesta();
        respuesta.setMensaje(dto.getMensaje());
        respuesta.setFechaCreacion(LocalDateTime.now());
        respuesta.setTopico(topico);
        respuesta.setAutor(autor);
        respuestaRepository.save(respuesta);
        return ResponseEntity.ok(new RespuestaResponseDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getTopico().getId(),
                respuesta.getAutor().getId()
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid RespuestaRequestDTO dto) {
        var respuestaOpt = respuestaRepository.findById(id);
        if (respuestaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = topicoRepository.findById(dto.getTopicoId()).orElse(null);
        Usuario autor = usuarioRepository.findById(dto.getAutorId()).orElse(null);
        if (topico == null || autor == null) {
            return ResponseEntity.badRequest().body("Topico o autor no encontrado");
        }
        Respuesta respuesta = respuestaOpt.get();
        respuesta.setMensaje(dto.getMensaje());
        respuesta.setTopico(topico);
        respuesta.setAutor(autor);
        respuestaRepository.save(respuesta);
        return ResponseEntity.ok(new RespuestaResponseDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getTopico().getId(),
                respuesta.getAutor().getId()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        var respuestaOpt = respuestaRepository.findById(id);
        if (respuestaOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        respuestaRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

