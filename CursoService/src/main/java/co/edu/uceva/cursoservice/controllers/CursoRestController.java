package co.edu.uceva.cursoservice.controllers;

import co.edu.uceva.cursoservice.model.entities.Curso;
import co.edu.uceva.cursoservice.model.services.ICursoService;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/curso-service")
public class CursoRestController {

    // Declaramos como final el servicio para mejorar la inmutabilidad
    private final ICursoService cursoService;

    private static final String ERROR = "error";
    private static final String MENSAJE = "mensaje";
    private static final String CURSO = "curso";
    private static final String CURSOS = "cursos";

    // Inyección de dependencia del servicio que proporciona servicios de CRUD
    public CursoRestController(ICursoService cursoService) {
        this.cursoService = cursoService;
    }

    /**
     * Listar todos los cursos.
     */
    @GetMapping("/cursos")
    public ResponseEntity<Map<String, Object>> getCursos() {
        Map<String, Object> response = new HashMap<>();

        try {
            List<Curso> cursos = cursoService.findAll();

            if (cursos.isEmpty()) {
                response.put(MENSAJE, "No hay cursos en la base de datos.");
                response.put(CURSOS, cursos); // para que sea siempre el mismo campo
                return ResponseEntity.status(HttpStatus.OK).body(response); // 200 pero lista vacía
            }

            response.put(CURSOS, cursos);
            return ResponseEntity.ok(response);

        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al consultar la base de datos.");
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Listar cursos con paginación.
     */
    @GetMapping("/curso/page/{page}")
    public ResponseEntity<Object> index(@PathVariable Integer page) {
        Map<String, Object> response = new HashMap<>();
        Pageable pageable = PageRequest.of(page, 4);

        try {
            Page<Curso> cursos = cursoService.findAll(pageable);

            if (cursos.isEmpty()) {
                response.put(MENSAJE, "No hay cursos en la página solicitada.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            return ResponseEntity.ok(cursos);

        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al consultar la base de datos.");
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        } catch (IllegalArgumentException e) {
            response.put(MENSAJE, "Número de página inválido.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    /**
     * Crear un nuevo curso pasando el objeto en el cuerpo de la petición.
     */
    @PostMapping("/cursos")
    public ResponseEntity<Map<String, Object>> save(@RequestBody Curso curso) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Guardar el curso en la base de datos
            Curso nuevoCurso = cursoService.save(curso);

            response.put(MENSAJE, "El curso ha sido creado con éxito!");
            response.put(CURSO, nuevoCurso);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al insertar el curso en la base de datos.");
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    /**
     * Eliminar un curso pasando el objeto en el cuerpo de la petición.
     */
    @DeleteMapping("/cursos")
    public ResponseEntity<Map<String, Object>> delete(@RequestBody Curso curso) {
        Map<String, Object> response = new HashMap<>();
        try {
            Curso cursoExistente = cursoService.findById(curso.getId());
            if (cursoExistente == null) {
                response.put(MENSAJE, "El curso ID: " + curso.getId() + " no existe en la base de datos.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            cursoService.delete(curso);
            response.put(MENSAJE, "El curso ha sido eliminado con éxito!");
            return ResponseEntity.ok(response);
        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al eliminar el curso de la base de datos.");
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Actualizar un curso pasando el objeto en el cuerpo de la petición.
     * @param curso: Objeto Curso que se va a actualizar
     */
    @PutMapping("/cursos")
    public ResponseEntity<Map<String, Object>> update(@RequestBody Curso curso) {
        Map<String, Object> response = new HashMap<>();

        try {
            // Verificar si el curso existe antes de actualizar
            if (cursoService.findById(curso.getId()) == null) {
                response.put(MENSAJE, "Error: No se pudo editar, el curso ID: " + curso.getId() + " no existe en la base de datos.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            // Guardar directamente el curso actualizado en la base de datos
            Curso cursoActualizado = cursoService.save(curso);

            response.put(MENSAJE, "El curso ha sido actualizado con éxito!");
            response.put(CURSO, cursoActualizado);
            return ResponseEntity.ok(response);

        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al actualizar el curso en la base de datos.");
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * Obtener un curso por su ID.
     */
    @GetMapping("/cursos/{id}")
    public ResponseEntity<Map<String, Object>> findById(@PathVariable Long id) {
        Map<String, Object> response = new HashMap<>();

        try {
            Curso curso = cursoService.findById(id);

            if (curso == null) {
                response.put(MENSAJE, "El curso ID: " + id + " no existe en la base de datos.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }

            response.put(MENSAJE, "El curso ha sido actualizado con éxito!");
            response.put(CURSO, curso);
            return ResponseEntity.ok(response);

        } catch (DataAccessException e) {
            response.put(MENSAJE, "Error al consultar la base de datos.");
            response.put(ERROR, e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
