package co.edu.uceva.cursoservice.model.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nombre;
    private String descripcion;
    private long idDocente;
    private long idSemestre;
    private String modalidad;
    private Byte numeroCreditos;
    private Integer duracion;
    private Byte cuposDisponibles;
    private LocalDate fechaCreacion;
    private String horario;
    private boolean activo;



}
