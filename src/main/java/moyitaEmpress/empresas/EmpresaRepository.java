package moyitaEmpress.empresas;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import java.util.*;


@Repository
public class EmpresaRepository {

    private static int lastGeneratedID=0;

    private final Map<Integer,Empresa> empresas = new HashMap<>();

    public Empresa save(Empresa empresa){
        int idAutoIncremental = lastGeneratedID++;

        empresa = new Empresa(idAutoIncremental, empresa.nombre(), empresa.balance(), LocalDate.now());
        empresas.put(idAutoIncremental, empresa);

        return empresa;
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
