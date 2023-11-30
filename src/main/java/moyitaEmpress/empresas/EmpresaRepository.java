package moyitaEmpress.empresas;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Repository
public class EmpresaRepository {

    private final List<Empresa> empresas=new ArrayList<>();

    public Empresa save(Empresa empresa){
        empresas.add(empresa);
        return empresa;
    }

    public List<Empresa> getAll(){
        empresas.sort(Comparator.comparing(Empresa::nombre));
        return empresas;
    }
}
