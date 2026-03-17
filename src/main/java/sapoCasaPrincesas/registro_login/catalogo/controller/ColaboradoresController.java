package sapoCasaPrincesas.registro_login.catalogo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapoCasaPrincesas.registro_login.catalogo.model.Colaborador;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/colaboradores")
@CrossOrigin(origins = "http://localhost:5173")
public class ColaboradoresController {

    private final List<Colaborador> colaboradores = new ArrayList<>(List.of(
            new Colaborador(1, "Mike", "Estilista", "Color y peinados", "5 años de experiencia", "https://ruta/valentina.png"),
            new Colaborador(2, "Salome", "Estilista", "Cortes y trenzas", "3 años de experiencia", "https://ruta/camila.png"),
            new Colaborador(3, "Rey", "Estilista", "Peinados creativos", "4 años de experiencia", "https://ruta/isabela.png")
    ));


    // LISTAR TODOS LOS COLABORADORES

    @GetMapping
    public ResponseEntity<List<Colaborador>> obtenerTodos() {
        return ResponseEntity.ok(colaboradores);
    }


    // OBTENER UN COLABORADOR POR ID

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        return colaboradores.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Colaborador inexistente"));
    }


    // BUSCAR UN COLABORADOR POR NOMBRE

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Colaborador>> buscarPorNombre(@PathVariable String nombre) {
        List<Colaborador> encontrados = colaboradores.stream()
                .filter(c -> c.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();

        return ResponseEntity.ok(encontrados);
    }


    // CREAR  UN COLABORADOR

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Colaborador nuevo) {

        // Validaciones
        if (nuevo.getNombre() == null || nuevo.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        if (nuevo.getRol() == null || nuevo.getRol().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El rol es obligatorio");
        }

        if (nuevo.getEspecialidad() == null || nuevo.getEspecialidad().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La especialidad es obligatoria");
        }

        if (nuevo.getExperiencia() == null || nuevo.getExperiencia().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La experiencia es obligatoria");
        }

        if (nuevo.getFoto() == null || nuevo.getFoto().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La URL de la foto es obligatoria");
        }

        // Generar ID automático
        int nuevoId = colaboradores.stream()
                .mapToInt(Colaborador::getId)
                .max()
                .orElse(0) + 1;

        nuevo.setId(nuevoId);
        colaboradores.add(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Colaborador creado correctamente");
    }


    // ACTUALIZAR COLABORADOR (ACTUALIZAR INFORMACION PARCIAL)

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Colaborador datos) {

        Colaborador existente = colaboradores.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Colaborador inexistente");
        }

        // Validaciones y actualizaciones parciales
        if (datos.getNombre() != null) {
            if (datos.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre no puede estar vacío");
            }
            existente.setNombre(datos.getNombre());
        }

        if (datos.getRol() != null) {
            if (datos.getRol().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El rol no puede estar vacío");
            }
            existente.setRol(datos.getRol());
        }

        if (datos.getEspecialidad() != null) {
            if (datos.getEspecialidad().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La especialidad no puede estar vacía");
            }
            existente.setEspecialidad(datos.getEspecialidad());
        }

        if (datos.getExperiencia() != null) {
            if (datos.getExperiencia().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La experiencia no puede estar vacía");
            }
            existente.setExperiencia(datos.getExperiencia());
        }

        if (datos.getFoto() != null) {
            if (datos.getFoto().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La URL de la foto no puede estar vacía");
            }
            existente.setFoto(datos.getFoto());
        }

        return ResponseEntity.ok("Colaborador actualizado correctamente");
    }


    // ELIMINAR UN COLABORADOR

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {

        Colaborador existente = colaboradores.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Colaborador inexistente");
        }

        colaboradores.remove(existente);

        return ResponseEntity.ok("Colaborador eliminado correctamente");
    }
}
