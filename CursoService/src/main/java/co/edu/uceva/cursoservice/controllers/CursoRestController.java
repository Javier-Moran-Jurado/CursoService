package co.edu.uceva.cursoservice.controllers;

import co.edu.uceva.cursoservice.model.entities.Curso;
import co.edu.uceva.cursoservice.model.services.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/curso-service")
public class CursoRestController {

    // Inyección de dependencia del servicio que proporciona servicios de CRUD
    private ICursoService cursoService;

    // Inyección de dependencia del servicio que proporciona servicios de CRUD
    @Autowired
    public CursoRestController(ICursoService cursoService) {
        this.cursoService = cursoService;
    }

    // Metodo que retorna todos los cursos
    @GetMapping("/cursos")
    public List<Curso> getCursos(){
        return cursoService.findAll();
    }

    // Metodo que retorna todos los cursos paginados
    @GetMapping("/curso/page/{page}")
    public Page<Curso> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        return cursoService.findAll(pageable);
    }

    // Metodo que guarda un curso pasandolo por el cuerpo de la petición
    @PostMapping("/cursos")
    public Curso save(@RequestBody Curso curso) {
        return cursoService.save(curso);
    }

    // Metodo que elimina un curso pasandolo por el cuerpo de la petición
    @DeleteMapping("/cursos")
    public void delete(@RequestBody Curso curso){
        cursoService.delete(curso);
    }

    // Metodo que actualiza un curso pasandolo por el cuerpo de la petición
    @PutMapping("/cursos")
    public Curso update(@RequestBody Curso curso){
        return cursoService.update(curso);
    }

    // Metodo que retorna un curso por su id pasado por la URL
    @GetMapping("/cursos/{id}")
    public Curso findById(@PathVariable("id") Long id){
        return cursoService.findById(id);
    }


}
