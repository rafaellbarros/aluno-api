package br.com.rafaellbarros.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import br.com.rafaellbarros.entity.AlunoEntity;
import br.com.rafaellbarros.model.Aluno;
import br.com.rafaellbarros.repository.AlunoRepository;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public List<AlunoEntity> findAll() {
        return alunoRepository.findAll();
    }

    public Optional<AlunoEntity> findById(final Long id) {
        return alunoRepository.findById(id);
    }

    public AlunoEntity findByMatricula(final String matricula) {
        return alunoRepository.findByMatricula(matricula).orElseThrow(() -> new RuntimeException("Aluno não encontrado com matrícula: " + matricula));
    }

    public AlunoEntity save(final Aluno aluno) {
        final AlunoEntity entity = mapToEntity(aluno);
        return alunoRepository.save(entity);
    }

    public AlunoEntity update(final Long id, final Aluno aluno) {
        final AlunoEntity existing = alunoRepository.findById(id).orElseThrow(() -> new RuntimeException("Aluno não encontrado com ID: " + id));

        existing.setNome(aluno.getNome());
        existing.setIdade(aluno.getIdade());
        existing.setSexo(aluno.getSexo());
        existing.setMatricula(aluno.getMatricula());

        return alunoRepository.save(existing);
    }

    public void delete(final Long id) {
        if (!alunoRepository.existsById(id)) {
            throw new RuntimeException("Aluno não encontrado com ID: " + id);
        }
        alunoRepository.deleteById(id);
    }

    private AlunoEntity mapToEntity(final Aluno aluno) {
        final AlunoEntity entity = new AlunoEntity();
        entity.setNome(aluno.getNome());
        entity.setIdade(aluno.getIdade());
        entity.setSexo(aluno.getSexo());
        entity.setMatricula(aluno.getMatricula());
        return entity;
    }

}
