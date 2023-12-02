package moyitaEmpress.empresas;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class EmpresaService {

    private final EmpresaRepository empresaRepository;

    public EmpresaService(EmpresaRepository empresaRepository) {
        this.empresaRepository = empresaRepository;
    }

    public List<Empresa> getAll() {
        return empresaRepository.getAll();
    }



    public Empresa delete(int id){
        return empresaRepository.delete(id);
    }


    public Empresa create(String nombre, double balance) {
        return empresaRepository.save(new Empresa(null, nombre, balance, LocalDate.now()));
    }
}
