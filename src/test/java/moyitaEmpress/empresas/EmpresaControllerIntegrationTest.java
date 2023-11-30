package moyitaEmpress.empresas;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EmpresaControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EmpresaRepository empresaRepository;

    @AfterEach
    public void setUp() {
        empresaRepository.deleteAll();
    }

    @Nested
    @DisplayName("Cuando no hay empresas creadas")
    class WithNoCreatedEmpresas {

        @Test
        @DisplayName("find all debe retornar un array vacio")
        public void findAllShouldReturnEmpty() throws Exception {
            mockMvc.perform(get("/empresas"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(0));
        }

        @Test
        @DisplayName("save debe crear una empresa")
        public void saveShouldCreateACompany() throws Exception {
            String nombre = "Mi empresita";
            double balance = 100000.0;
            mockMvc.perform(post("/empresas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(aEmpresaJson(nombre, balance)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.nombre", equalTo(nombre)))
                    .andExpect(jsonPath("$.balance", equalTo(balance)));
        }

        @Test
        @DisplayName("save debe crear una empresa con id autoincremental")
        public void saveShouldCreateAEmpresaWithAutoincrementalId() throws Exception {
            mockMvc.perform(post("/empresas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aEmpresaJson("Mi empresita", 100000.0)));
            mockMvc.perform(post("/empresas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aEmpresaJson("Mi otra empresita", 100000.0)));
            mockMvc.perform(post("/empresas")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(aEmpresaJson("Mi ultima empresita", 100000.0)));
            assertThat(empresaRepository.getAll()).hasSize(3);
            assertThat(empresaRepository.getAll()).extracting(Empresa::id).doesNotHaveDuplicates();
        }


        @Test
        @DisplayName("save debe retornar bad request cuando se intenta crear una empresa con un nombre muy largo")
        public void saveShouldFailWhenNameIsTooLong() throws Exception {
            mockMvc.perform(post("/empresas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(aEmpresaJson("Mi empresa tiene un nombre demasiado largo y debería fallar", 0)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("save debe retornar bad request cuando se intenta crear una empresa con un nombre vacio")
        public void saveShouldFailWhenNameIsEmpty() throws Exception {
            mockMvc.perform(post("/empresas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(aEmpresaJson("", 0)))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @DisplayName("save debe crear una empresa con fecha de creacion con el dia de hoy")
        public void saveShouldCreateAEmpresaWithTodayAsFechaDeCreacion() throws Exception {
            mockMvc.perform(post("/empresas")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(aEmpresaJson("Mi empresita", 99.0)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.fechaDeCreacion", equalTo(LocalDate.now().format(DateTimeFormatter.ISO_DATE))));
        }

        @Test
        @DisplayName("find by id debe retornar not found cuando se busca una empresa que no existe")
        public void findByIdShouldNotFound() throws Exception {
            mockMvc.perform(get("/empresas/{id}", 1))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    @DisplayName("Cuando hay empresas creadas")
    class WithCreatedEmpresas {
        private Empresa empresa1;
        private Empresa empresa2;
        private Empresa empresa3;

        @BeforeEach
        public void setUp() {
            empresa1 = empresaRepository.save(createEmpresa(0, "una empresa"));
            empresa2 = empresaRepository.save(createEmpresa(1, "la otra empresa"));
            empresa3 = empresaRepository.save(createEmpresa(2, "la última empresa"));
        }

        @Test
        @DisplayName("find all debe retornar un array con empresas")
        public void findAllShouldReturnAnArrayWithEmpresas() throws Exception {
            mockMvc.perform(get("/empresas"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.length()").value(3));
        }

        @Test
        @DisplayName("find all debe retornar las empresas que comiencen con el string indicado")
        public void findAllShouldEmpresasThatStartWithQueryParam() throws Exception {
            mockMvc.perform(get("/empresas")
                            .queryParam("nombreEmpiezaCon", "la"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].nombre", containsInAnyOrder(empresa2.nombre(), empresa3.nombre())));
        }

        @Test
        @DisplayName("find all debe retornar las empresas que comiencen con el string indicado ordenadas por nombre")
        public void findAllShouldEmpresasThatStartWithQueryParamSortedByName() throws Exception {
            mockMvc.perform(get("/empresas")
                            .queryParam("nombreEmpiezaCon", "la"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].nombre", contains(empresa2.nombre(), empresa3.nombre())));
        }

        @Test
        @DisplayName("find all debe retornar un los detalles de las empresas")
        public void findAllShouldReturnAnArrayWithEmpresaDetails() throws Exception {
            mockMvc.perform(get("/empresas")
                            .queryParam("nombreEmpiezaCon", "la"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].nombre", containsInAnyOrder(empresa2.nombre(), empresa3.nombre())));
        }

        @Test
        @DisplayName("find all debe retornar las empresas ordenadas por nombre")
        public void findAllShouldReturnAnArrayWithEmpresaSortedByName() throws Exception {
            mockMvc.perform(get("/empresas"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[*].nombre", contains(empresa2.nombre(), empresa3.nombre(), empresa1.nombre())));
        }

        @Test
        @DisplayName("delete by id debe retornar las empresa eliminada")
        public void deleteByIdShouldReturnDeletedEmpresa() throws Exception {
            mockMvc.perform(delete("/empresas/{id}", empresa1.id()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(empresa1.id())))
                    .andExpect(jsonPath("$.nombre", equalTo(empresa1.nombre())))
                    .andExpect(jsonPath("$.balance", equalTo(empresa1.balance())))
                    .andExpect(jsonPath("$.fechaDeCreacion", equalTo(empresa1.fechaDeCreacion().format(DateTimeFormatter.ISO_DATE))));
        }

        @Test
        @DisplayName("delete by id debe eliminar las empresa del repository")
        public void deleteByIdShouldRemoveTheEmpresaFromRepository() throws Exception {
            mockMvc.perform(delete("/empresas/{id}", empresa3.id()));
            assertThat(empresaRepository.getAll()).noneMatch(empresa -> empresa.equals(empresa3));
            assertThat(empresaRepository.getAll()).hasSize(2);

        }

        @Test
        @DisplayName("delete by id debe retornar not found si la empresa a eliminar no existe")
        public void deleteByIdShouldReturnNotFoundWhenEmpresaDoesNotExists() throws Exception {
            mockMvc.perform(delete("/empresas/{id}", 99999999))
                    .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("find by id debe retornar los detalles de la empresa")
        public void findByIdShouldReturnEmpresaDetails() throws Exception {
            mockMvc.perform(get("/empresas/{id}", empresa2.id()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id", equalTo(empresa2.id())))
                    .andExpect(jsonPath("$.nombre", equalTo(empresa2.nombre())))
                    .andExpect(jsonPath("$.balance", equalTo(empresa2.balance())))
                    .andExpect(jsonPath("$.fechaDeCreacion", equalTo(empresa2.fechaDeCreacion().format(DateTimeFormatter.ISO_DATE))));
        }
    }

    private static String aEmpresaJson(String nombre, double balance) {
        return String.format("""
                {
                    "nombre": "%s",
                    "balance": %f
                }
                """, nombre, balance).trim();
    }

    private Empresa createEmpresa(int id, String nombre) {
        return new Empresa(id, nombre, 0.0, LocalDate.now());
    }
}