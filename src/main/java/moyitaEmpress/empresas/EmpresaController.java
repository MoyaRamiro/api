package moyitaEmpress.empresas;

import org.springframework.cglib.core.Local;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/empresas")

public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<Empresa> getAll(){
        return empresaService.getAll();
    }
    @PostMapping
    public Empresa save(@RequestBody Empresa e){
        return empresaService.save(e);
    }

}

