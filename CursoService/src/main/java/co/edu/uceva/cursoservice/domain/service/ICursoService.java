package co.edu.uceva.cursoservice.domain.service;

import co.edu.uceva.cursoservice.domain.model.Curso;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ICursoService {
    Curso save(Curso curso);

    void delete(Curso curso);

    Curso findById(Long id);

    Curso update(Curso curso);

    List<Curso> findAll();

    Page<Curso> findAll(Pageable pageable);

}
