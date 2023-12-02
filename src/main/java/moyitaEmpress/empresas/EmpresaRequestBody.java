package moyitaEmpress.empresas;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;


public record EmpresaRequestBody (
        @Size(min = 1, max = 20)
        String nombre,
        @PositiveOrZero
        double balance
){

}