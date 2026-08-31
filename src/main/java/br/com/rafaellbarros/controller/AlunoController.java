package br.com.rafaellbarros.controller;

import br.com.rafaellbarros.entity.AlunoEntity;
import br.com.rafaellbarros.model.Aluno;
import br.com.rafaellbarros.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    public ResponseEntity<List<AlunoEntity>> findAll() {
        return ResponseEntity.ok(alunoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlunoEntity> findById(@PathVariable final Long id) {
        return alunoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/matricula/{matricula}")
    public ResponseEntity<AlunoEntity> findByMatricula(@PathVariable final String matricula) throws RuntimeException {
        return ResponseEntity.ok(alunoService.findByMatricula(matricula));
    }

    @PostMapping
    public ResponseEntity<AlunoEntity> create(@RequestBody final Aluno aluno) {
        final AlunoEntity saved = alunoService.save(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlunoEntity> update(@PathVariable final Long id, @RequestBody final Aluno aluno) throws RuntimeException {
        return ResponseEntity.ok(alunoService.update(id, aluno));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable final Long id) throws RuntimeException {
        alunoService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
