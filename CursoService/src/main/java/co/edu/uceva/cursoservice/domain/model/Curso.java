package co.edu.uceva.cursoservice.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Curso {
    //No se me ocurrieron regex
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty(message = "El curso debe tener nombre")
    @Column(nullable = false)
    private String nombre;
    @NotEmpty(message = "El curso debe tener descripcion")
    @Column(nullable = false)
    private String descripcion;
    @NotNull(message = "El curso debe estar vinculado a un docente")
    @Column(nullable = false)
    private long idDocente;
    @NotNull(message = "El curso debe estar vinculado a un semestre")
    @Column(nullable = false)
    private long idSemestre;
    @NotEmpty(message = "El curso debe tener una modalidad")
    @Column(nullable = false)
    private String modalidad;
    @NotNull(message = "Debe especificar el numero de creditos")
    @Column(nullable = false)
    private Byte numeroCreditos;
    @NotNull(message = "Debe especificar la duracion del curso")
    @Column(nullable = false)
    private Integer duracion;
    @NotNull(message = "Debe especificar el numero de cupos disponibles")
    @Column(nullable = false)
    private Byte cuposDisponibles;
    @NotNull(message = "Debe ingresar la fecha de creacion del curso")
    @Temporal(TemporalType.DATE)
    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="dd-MM-yyyy")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate fechaCreacion;
    @NotNull(message = "Debe especificar el horacio del curso")
    @Column(nullable = false)
    private String horario;
    @Column(nullable = false)
    private boolean activo;
}
