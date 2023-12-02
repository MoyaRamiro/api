package moyitaEmpress.empresas;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> getAll(String nombreEmpiezaCon) {
        if(nombreEmpiezaCon == null){
            return empresaRepository.getAll();
        }

        return empresaRepository.buscarEmpresa(nombreEmpiezaCon);
    }



    public Empresa delete(int id){
        return empresaRepository.delete(id);
    }


    public Empresa create(String nombre, double balance) {
        return empresaRepository.save(new Empresa(null, nombre, balance, LocalDate.now()));
    }

}
