package moyitaEmpress.empresas;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;


public record Empresa (
     int id,
    String nombre,
     double balance,
    LocalDate fechaDeCreacion

){

}
