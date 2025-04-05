package co.edu.uceva.cursoservice.domain.repositories;

import co.edu.uceva.cursoservice.domain.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICursoRepository extends JpaRepository<Curso, Long> {


}
