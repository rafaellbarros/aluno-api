package br.com.rafaellbarros.service;

import br.com.rafaellbarros.entity.AlunoEntity;
import br.com.rafaellbarros.model.Aluno;
import br.com.rafaellbarros.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public List<AlunoEntity> findAll() {
        return alunoRepository.findAll();
    }

    public Optional<AlunoEntity> findById(Long id) {
        return alunoRepository.findById(id);
    }

    public AlunoEntity findByMatricula(String matricula) {
        return alunoRepository.findByMatricula(matricula).orElseThrow(
                () -> new RuntimeException("Aluno não encontrado com matrícula: " + matricula));
    }

    public AlunoEntity save(Aluno aluno) {
        AlunoEntity entity = mapToEntity(aluno);
        return alunoRepository.save(entity);
    }

    public AlunoEntity update(Long id, Aluno aluno) {
        AlunoEntity existing = alunoRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Aluno não encontrado com ID: " + id));

        existing.setNome(aluno.getNome());
        existing.setIdade(aluno.getIdade());
        existing.setSexo(aluno.getSexo());
        existing.setMatricula(aluno.getMatricula());

        return alunoRepository.save(existing);
    }

    public void delete(Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado com ID: " + id);
        }
        alunoRepository.deleteById(id);
    }

    private AlunoEntity mapToEntity(Aluno aluno) {
        AlunoEntity entity = new AlunoEntity();
        entity.setNome(aluno.getNome());
        entity.setIdade(aluno.getIdade());
        entity.setSexo(aluno.getSexo());
        entity.setMatricula(aluno.getMatricula());
        return entity;
    }

}
