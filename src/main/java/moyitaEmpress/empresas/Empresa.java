package moyitaEmpress.empresas;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;


public record Empresa (
     int id,
    String nombre,
     double balance,
    LocalDate fechaDeCreacion

){
    public Empresa(int id, String nombre, double balance) {
        this(id, nombre, balance, LocalDate.now());
    }
}
