package moyitaEmpress.empresas;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class EmpresaRepository {

    private static AtomicInteger lastGeneratedID = new AtomicInteger(0);
    private final Map<Integer,Empresa> empresas = new HashMap<>();

    public Empresa save(Empresa empresaACrear){
        Empresa empresaCreada = new Empresa(lastGeneratedID.getAndIncrement(), empresaACrear.nombre(), empresaACrear.balance(), empresaACrear.fechaDeCreacion());

        empresas.put(empresaCreada.id(), empresaCreada);

        return empresaCreada;
    }

    public List<Empresa> getAll(){
        return empresas.values().stream().sorted(Comparator.comparing(Empresa::nombre)).toList();
    }

    public Empresa delete(int id){
        return empresas.remove(id);
    }

    public void deleteAll() {
        empresas.clear();
    }
}
