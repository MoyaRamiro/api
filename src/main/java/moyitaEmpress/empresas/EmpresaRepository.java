package moyitaEmpress.empresas;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class EmpresaRepository {

    private static int lastGeneratedID=0;
    private final Map<Integer,Empresa> empresas=new HashMap<>();

    public Empresa save(Empresa empresa){
        empresas.put(lastGeneratedID++, empresa);
        return empresa;
    }

    public List<Empresa> getAll(){
        return empresas.values().stream().toList();
    }

    public Empresa delete(int id){
        return empresas.remove(id);
    }
}
