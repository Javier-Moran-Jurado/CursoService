package co.edu.uceva.cursoservice.domain.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "semestreservice", url = "http://api.mewings.joptionpane.software")
public interface ISemestreClient {
    @GetMapping("api/v1/semestre-service/semestres")
    ResponseEntity<Map<String, Object>> getSemestre();
}