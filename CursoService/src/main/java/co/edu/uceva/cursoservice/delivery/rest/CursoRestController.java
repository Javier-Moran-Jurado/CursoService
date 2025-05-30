package co.edu.uceva.cursoservice.delivery.rest;

import co.edu.uceva.cursoservice.domain.exception.*;
import co.edu.uceva.cursoservice.domain.model.Curso;
import co.edu.uceva.cursoservice.domain.model.UsuarioDTO;
import co.edu.uceva.cursoservice.domain.service.ICursoService;
import co.edu.uceva.cursoservice.domain.service.ISemestreClient;
import co.edu.uceva.cursoservice.domain.service.IUsuarioClient;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/v1/curso-service")
public class CursoRestController {

    // Declaramos como final el servicio para mejorar la inmutabilidad
    private final ICursoService cursoService;
    private final IUsuarioClient usuarioService;
    private final ISemestreClient semestreService;

    // Constantes para los mensajes de respuesta
    private static final String MENSAJE = "mensaje";
    private static final String CURSO = "curso";
    private static final String CURSOS = "cursos";
    private static final String USUARIOS = "usuarios";

    // Inyección de dependencia del servicio que proporciona servicios de CRUD
    public CursoRestController(ICursoService cursoService, IUsuarioClient usuarioService, ISemestreClient semestreService) {
        this.cursoService = cursoService;
        this.usuarioService = usuarioService;
        this.semestreService = semestreService;
    }

    /**
     * Listar todos los cursos.
     */
    @GetMapping("/cursos")
    public ResponseEntity<Map<String, Object>> getCursos() {
        List<Curso> cursos = cursoService.findAll();
        if (cursos.isEmpty()) {
            throw new NoHayCursosException();
        }
        Map<String, Object> response = new HashMap<>();
        response.put(CURSOS, cursos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/docentes")
    public ResponseEntity<Map<String, Object>> getDocentes() {
        ObjectMapper mapper = new ObjectMapper();
        //https://stackoverflow.com/questions/28821715/java-lang-classcastexception-java-util-linkedhashmap-cannot-be-cast-to-com-test
        List<UsuarioDTO> usuarios = mapper.convertValue(usuarioService.getUsuarios().getBody().get(USUARIOS), new TypeReference<List<UsuarioDTO>>(){});
        List<UsuarioDTO> docentes = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        for(UsuarioDTO usuario : usuarios) {
            if(usuario.getRol().equals("Coordinador") || usuario.getRol().equals("Docente") || usuario.getRol().equals("Decano")) {
                docentes.add(usuario);
            }
        }
        if (docentes.isEmpty()) {
            throw new NoHayDocentesException();
        }
        response.put(USUARIOS, docentes);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/semestres")
    public ResponseEntity<Map<String, Object>> getSemestre() {
        return semestreService.getSemestre();
    }

    /**
     * Listar cursos con paginación.
     */
    @GetMapping("/curso/page/{page}")
    public ResponseEntity<Object> index(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Curso> cursos = cursoService.findAll(pageable);
        if (cursos.isEmpty()) {
            throw new PaginaSinCursosException(page);
        }
        return ResponseEntity.ok(cursos);
    }

    /**
     * Crear un nuevo curso pasando el objeto en el cuerpo de la petición, usando validaciones
     */
    @PostMapping("/cursos")
    public ResponseEntity<Map<String, Object>> save(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        Map<String, Object> response = new HashMap<>();
        Curso nuevoCurso = cursoService.save(curso);
        response.put(MENSAJE, "El curso ha sido creado con éxito!");
        response.put(CURSO, nuevoCurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    /**
     * Eliminar un curso pasando el objeto en el cuerpo de la petición.
     */
    @DeleteMapping("/cursos")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Curso curso) {
        cursoService.findById(curso.getId())
                .orElseThrow(() -> new CursoNoEncontradoException(curso.getId()));
        cursoService.delete(curso);
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El curso ha sido eliminado con éxito!");
        response.put(CURSO, null);
        return ResponseEntity.ok(response);
    }

    /**
     * Actualizar un curso pasando el objeto en el cuerpo de la petición.
     * @param curso: Objeto Curso que se va a actualizar
     */
    @PutMapping("/cursos")
    public ResponseEntity<Map<String, Object>> update(@Valid @RequestBody Curso curso, BindingResult result) {
        if (result.hasErrors()) {
            throw new ValidationException(result);
        }
        cursoService.findById(curso.getId())
                .orElseThrow(() -> new CursoNoEncontradoException(curso.getId()));
        Map<String, Object> response = new HashMap<>();
        Curso cursoActualizado = cursoService.update(curso);
        response.put(MENSAJE, "El curso ha sido actualizado con éxito!");
        response.put(CURSO, cursoActualizado);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener un curso por su ID.
     */
    @GetMapping("/cursos/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Curso curso = cursoService.findById(id)
                .orElseThrow(() -> new CursoNoEncontradoException(id));
        Map<String, Object> response = new HashMap<>();
        response.put(MENSAJE, "El curso ha sido encontrado con éxito!");
        response.put(CURSO, curso);
        return ResponseEntity.ok(response);
    }
}
