package com.example.demo.Controller;

import com.example.demo.repository.GuildaRepository;
import com.example.demo.Model.Aventureiro;
import com.example.demo.Model.Classes;
import com.example.demo.Model.Companheiro;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/aventureiros")
@CrossOrigin(origins = "http://localhost:5173") // Porta padrão do Vite
@RestController
@RequestMapping("/aventureiros")
public class AventureiroController {

    private final GuildaRepository repository;

    // Injeção de Dependência via Construtor
    public AventureiroController(GuildaRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<List<Aventureiro>> listar(
            @RequestParam(required = false) Classes classe,
            @RequestParam(required = false) Boolean ativo,
            @RequestParam(required = false) Integer nivelMinimo,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        if (page < 0 || size < 1 || size > 50) {
            return ResponseEntity.badRequest().build();
        }

        List<Aventureiro> filtrados = repository.findByFilters(classe, ativo, nivelMinimo);

        int totalRegistros = filtrados.size();
        int totalPaginas = (int) Math.ceil((double) totalRegistros / size);

        int inicio = page * size;
        List<Aventureiro> paginada;

        if (inicio >= totalRegistros) {
            paginada = List.of();
        } else {
            int fim = Math.min(inicio + size, totalRegistros);
            paginada = filtrados.subList(inicio, fim);
        }

        // 4. Configuração dos Headers Obrigatórios
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Total-Count", String.valueOf(totalRegistros));
        headers.add("X-Page", String.valueOf(page));
        headers.add("X-Size", String.valueOf(size));
        headers.add("X-Total-Pages", String.valueOf(totalPaginas));

        return new ResponseEntity<>(paginada, headers, HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> encerrarVinculo(@PathVariable Long id) {
        return repository.findById(id).map(a -> {
            a.setAtivo(false);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // 6. Recrutar novamente (Reativar)
    @PatchMapping("/{id}/reativar")
    public ResponseEntity<Void> reativar(@PathVariable Long id) {
        return repository.findById(id).map(a -> {
            a.setAtivo(true);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // 7. Definir ou substituir companheiro
    @PutMapping("/{id}/companheiro")
    public ResponseEntity<Void> definirCompanheiro(@PathVariable Long id, @Valid @RequestBody Companheiro novo) {
        return repository.findById(id).map(a -> {
            a.setCompanheiro(novo);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }

    // 8. Remover companheiro
    @DeleteMapping("/{id}/companheiro")
    public ResponseEntity<Void> removerCompanheiro(@PathVariable Long id) {
        return repository.findById(id).map(a -> {
            a.setCompanheiro(null);
            return ResponseEntity.noContent().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
    @PostMapping
    public ResponseEntity<Aventureiro> registrar(@RequestBody Aventureiro novo) {
        // Validação básica (o ideal é usar @Valid, mas vamos garantir o essencial aqui)
        if (novo.getNome() == null || novo.getNome().isBlank() || novo.getClasse() == null || novo.getNivel() < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        Aventureiro salvo = repository.save(novo);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aventureiro> consultarPorId(@PathVariable Long id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}