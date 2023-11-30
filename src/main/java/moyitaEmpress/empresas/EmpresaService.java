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

    public List<Empresa> getAll(){
        return empresaRepository.getAll();
    }

    public Empresa save(Empresa e){
        e = new Empresa(e.id(), e.nombre(), e.balance(), LocalDate.now());
        return empresaRepository.save(e);
    }



}
