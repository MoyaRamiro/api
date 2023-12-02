package moyitaEmpress.empresas;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/empresas")
public class EmpresaController {

    private final EmpresaService empresaService;

    public EmpresaController(EmpresaService empresaService) {
        this.empresaService = empresaService;
    }

    @GetMapping
    public List<Empresa> getAll() {
        return empresaService.getAll();
    }

    @PostMapping
    public Empresa save(@RequestBody Empresa e) {
        return empresaService.save(e);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> delete(@PathVariable int id){
        return ResponseEntity.ofNullable(empresaService.delete(id));
    }

}

