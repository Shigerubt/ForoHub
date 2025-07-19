package Foro.Hub.AluraForo.controller;

import Foro.Hub.AluraForo.dto.TopicoRequestDTO;
import Foro.Hub.AluraForo.dto.TopicoResponseDTO;
import Foro.Hub.AluraForo.model.Topico;
import Foro.Hub.AluraForo.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/topicos")
public class TopicoController {
    @Autowired
    private TopicoRepository topicoRepository;

    @GetMapping
    public List<TopicoResponseDTO> listar() {
        return topicoRepository.findAll().stream()
                .map(t -> new TopicoResponseDTO(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensaje(),
                        t.getFechaCreacion(),
                        t.getStatus(),
                        t.getAutor(),
                        t.getCurso()
                ))
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<?> registrar(@RequestBody @Valid TopicoRequestDTO dto) {
        boolean existe = topicoRepository.existsByTituloAndMensaje(dto.getTitulo(), dto.getMensaje());
        if (existe) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje.");
        }
        Topico topico = new Topico();
        topico.setTitulo(dto.getTitulo());
        topico.setMensaje(dto.getMensaje());
        topico.setAutor(dto.getAutor());
        topico.setCurso(dto.getCurso());
        topico.setFechaCreacion(LocalDateTime.now());
        topico.setStatus("ABIERTO");
        topicoRepository.save(topico);
        return ResponseEntity.ok(new TopicoResponseDTO(
            topico.getId(),
            topico.getTitulo(),
            topico.getMensaje(),
            topico.getFechaCreacion(),
            topico.getStatus(),
            topico.getAutor(),
            topico.getCurso()
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        return topicoRepository.findById(id)
                .map(t -> ResponseEntity.ok(new TopicoResponseDTO(
                        t.getId(),
                        t.getTitulo(),
                        t.getMensaje(),
                        t.getFechaCreacion(),
                        t.getStatus(),
                        t.getAutor(),
                        t.getCurso()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody @Valid TopicoRequestDTO dto) {
        var topicoOpt = topicoRepository.findById(id);
        if (topicoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Validar que no exista otro tópico con el mismo título y mensaje
        boolean existe = topicoRepository.existsByTituloAndMensaje(dto.getTitulo(), dto.getMensaje());
        Topico topico = topicoOpt.get();
        if (existe && (!topico.getTitulo().equals(dto.getTitulo()) || !topico.getMensaje().equals(dto.getMensaje()))) {
            return ResponseEntity.badRequest().body("Ya existe un tópico con el mismo título y mensaje.");
        }
        topico.setTitulo(dto.getTitulo());
        topico.setMensaje(dto.getMensaje());
        topico.setAutor(dto.getAutor());
        topico.setCurso(dto.getCurso());
        topicoRepository.save(topico);
        return ResponseEntity.ok(new TopicoResponseDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getStatus(),
                topico.getAutor(),
                topico.getCurso()
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        var topicoOpt = topicoRepository.findById(id);
        if (topicoOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        topicoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
