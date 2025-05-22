package co.edu.uceva.cursoservice.domain.exception;

public class NoHayDocentesException extends RuntimeException {
    public NoHayDocentesException() {
        super("No hay docentes en la base de datos.");
    }
}