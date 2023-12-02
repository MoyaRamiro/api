package moyitaEmpress.empresas;

import java.time.LocalDate;


public record Empresa(
        Integer id,
        String nombre,
        double balance,
        LocalDate fechaDeCreacion

) {
    public Empresa(int id, String nombre, double balance) {
        this(id, nombre, balance, LocalDate.now());
    }
}
