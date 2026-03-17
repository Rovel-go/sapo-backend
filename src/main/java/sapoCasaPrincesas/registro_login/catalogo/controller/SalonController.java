package sapoCasaPrincesas.registro_login.catalogo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapoCasaPrincesas.registro_login.catalogo.model.Salon;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/salones")
@CrossOrigin(origins = "http://localhost:5173")
public class SalonController {

    private final List<Salon> salones = new ArrayList<>(List.of(
            new Salon(1, "Salón Princesa Rosa", "Decoración rosa pastel con temática de coronas", "Piso 1", "https://ruta/salon1.png"),
            new Salon(2, "Salón Encantado", "Ambiente mágico con luces y estrellas", "Piso 2", "https://ruta/salon2.png"),
            new Salon(3, "Salón Real", "Estilo elegante con tonos dorados", "Piso 1", "https://ruta/salon3.png")
    ));

    // ============================
    // LISTAR TODOS
    // ============================
    @GetMapping
    public ResponseEntity<List<Salon>> obtenerTodos() {
        return ResponseEntity.ok(salones);
    }

    // ============================
    // OBTENER POR ID
    // ============================
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable int id) {
        return salones.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Salón inexistente"));
    }


    // BUSCAR SALON POR NOMBRE

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<Salon>> buscarPorNombre(@PathVariable String nombre) {
        List<Salon> encontrados = salones.stream()
                .filter(s -> s.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();

        return ResponseEntity.ok(encontrados);
    }


    // CREAR NUEVO SALÓN

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Salon nuevo) {

        // Validaciones
        if (nuevo.getNombre() == null || nuevo.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        if (nuevo.getDescripcion() == null || nuevo.getDescripcion().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La descripción es obligatoria");
        }

        if (nuevo.getUbicacion() == null || nuevo.getUbicacion().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La ubicación es obligatoria");
        }

        if (nuevo.getFoto() == null || nuevo.getFoto().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La URL de la foto es obligatoria");
        }

        // Generar ID automático
        int nuevoId = salones.stream()
                .mapToInt(Salon::getId)
                .max()
                .orElse(0) + 1;

        nuevo.setId(nuevoId);
        salones.add(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Salón creado correctamente");
    }


    // ACTUALIZAR SALÓN (ACTUALIZAR INFORMACION PARCIAL)

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable int id, @RequestBody Salon datos) {

        Salon existente = salones.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Salón inexistente");
        }

        // Validaciones y actualizaciones parciales
        if (datos.getNombre() != null) {
            if (datos.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre no puede estar vacío");
            }
            existente.setNombre(datos.getNombre());
        }

        if (datos.getDescripcion() != null) {
            if (datos.getDescripcion().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La descripción no puede estar vacía");
            }
            existente.setDescripcion(datos.getDescripcion());
        }

        if (datos.getUbicacion() != null) {
            if (datos.getUbicacion().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La ubicación no puede estar vacía");
            }
            existente.setUbicacion(datos.getUbicacion());
        }

        if (datos.getFoto() != null) {
            if (datos.getFoto().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("La URL de la foto no puede estar vacía");
            }
            existente.setFoto(datos.getFoto());
        }

        return ResponseEntity.ok("Salón actualizado correctamente");
    }


    // ELIMINAR SALÓN

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable int id) {

        Salon existente = salones.stream()
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);

        if (existente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Salón inexistente");
        }

        salones.remove(existente);

        return ResponseEntity.ok("Salón eliminado correctamente");
    }
}
