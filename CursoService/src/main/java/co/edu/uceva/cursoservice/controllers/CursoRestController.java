package co.edu.uceva.cursoservice.controllers;

import co.edu.uceva.cursoservice.model.entities.Curso;
import co.edu.uceva.cursoservice.model.entities.services.ICursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/CursoService")

public class CursoRestController {
private ICursoService cursoService;
@Autowired
    public CursoRestController(ICursoService cursoService) {this.cursoService = cursoService;}
@GetMapping ("/curso")
public List<Curso> getCurso(){
    return cursoService.findAll();
}
    @PostMapping("/curso")
    public Curso save(@RequestBody Curso curso) {
        return cursoService.save(curso);
    }

    @DeleteMapping("/curso")
    public void delete(@RequestBody Curso curso){
        cursoService.delete(curso);
    }

    @PutMapping("/curso")
    public Curso update(@RequestBody Curso curso){
        return cursoService.update(curso);
    }

    @GetMapping("/curso/{id}")
    public Curso findById(@PathVariable("id") Long id){
        return cursoService.findById(id);
    }
}
