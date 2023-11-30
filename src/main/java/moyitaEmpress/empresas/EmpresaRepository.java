package moyitaEmpress.empresas;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpresaRepository {

    private static int lastGeneratedID=0;
    private final List<Empresa> empresas=new ArrayList<>();

    public Empresa save(Empresa empresa){
        empresa = new Empresa(++lastGeneratedID, empresa.nombre(), empresa.balance(), LocalDate.now());
        empresas.add(empresa);
        return empresa;
    }

    public List<Empresa> getAll(){
        return empresas;
    }
}
