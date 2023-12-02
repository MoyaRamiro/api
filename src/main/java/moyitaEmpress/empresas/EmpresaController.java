package moyitaEmpress.empresas;

import jakarta.validation.Valid;
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
    public List<Empresa> getAll(@RequestParam(required = false) String nombreEmpiezaCon) {
        return empresaService.getAll(nombreEmpiezaCon);
    }


    @PostMapping
    public ResponseEntity<Empresa> save(@RequestBody @Valid EmpresaRequestBody e) {

        return ResponseEntity.ok(empresaService.create(e.nombre(), e.balance()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Empresa> delete(@PathVariable int id){
        return ResponseEntity.ofNullable(empresaService.delete(id));
    }



}

