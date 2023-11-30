package moyitaEmpress.empresas;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmpresaRepository {

    private final List<Empresa> empresas=new ArrayList<>();

    public Empresa save(Empresa empresa){
        empresas.add(empresa);
        return empresa;
    }

    public List<Empresa> getAll(){
        return empresas;
    }

    public List<Empresa> buscarEmpresa(String buscado) {
        List<Empresa> listaFiltrada =null;

        for (Empresa empresa: empresas) {
            if(empresa.nombre().startsWith(buscado)){
                listaFiltrada.add(empresa);
            }
        }

        return listaFiltrada;
    }
}
