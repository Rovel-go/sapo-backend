package sapoCasaPrincesas.registro_login.catalogo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import sapoCasaPrincesas.registro_login.catalogo.model.CategoriaServicio;
import sapoCasaPrincesas.registro_login.catalogo.model.SubServicio;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/servicios")
@CrossOrigin(origins = "http://localhost:5173")
public class ServiciosController {

    private final List<CategoriaServicio> categorias = new ArrayList<>(List.of(
            new CategoriaServicio("Color", new ArrayList<>(List.of(
                    new SubServicio(1, "Tinte completo", 45000),
                    new SubServicio(2, "High-Lights", 60000),
                    new SubServicio(3, "Iluminación", 55000)
            ))),
            new CategoriaServicio("Corte", new ArrayList<>(List.of(
                    new SubServicio(4, "Corte básico", 20000),
                    new SubServicio(5, "Corte en capas", 30000),
                    new SubServicio(6, "Flequillo", 15000)
            ))),
            new CategoriaServicio("Peinado", new ArrayList<>(List.of(
                    new SubServicio(7, "Trenzas", 25000),
                    new SubServicio(8, "Ondas", 30000),
                    new SubServicio(9, "Moño princesa", 35000)
            )))
    ));


    // LISTAR CATEGORÍAS

    @GetMapping
    public ResponseEntity<List<CategoriaServicio>> obtenerCategorias() {
        return ResponseEntity.ok(categorias);
    }


    // OBTENER CATEGORÍA POR NOMBRE

    @GetMapping("/{categoria}")
    public ResponseEntity<?> obtenerCategoria(@PathVariable String categoria) {
        return categorias.stream()
                .filter(c -> c.getCategoria().equalsIgnoreCase(categoria))
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Categoría inexistente"));
    }


    // OBTENER SUBSERVICIO POR ID

    @GetMapping("/subservicio/{id}")
    public ResponseEntity<?> obtenerSubServicioPorId(@PathVariable int id) {
        return categorias.stream()
                .flatMap(c -> c.getSubservicios().stream())
                .filter(s -> s.getId() == id)
                .findFirst()
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Subservicio inexistente"));
    }


    // BUSCAR SUBSERVICIO POR NOMBRE

    @GetMapping("/buscar/{nombre}")
    public ResponseEntity<List<SubServicio>> buscarPorNombre(@PathVariable String nombre) {
        List<SubServicio> encontrados = categorias.stream()
                .flatMap(c -> c.getSubservicios().stream())
                .filter(s -> s.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();

        return ResponseEntity.ok(encontrados);
    }


    // CREAR SUBSERVICIO

    @PostMapping("/subservicio")
    public ResponseEntity<?> crearSubServicio(@RequestBody SubServicioRequest request) {

        // Validaciones
        if (request.getCategoria() == null || request.getCategoria().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La categoría es obligatoria");
        }

        if (request.getNombre() == null || request.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        if (request.getPrecio() == null) {
            return ResponseEntity.badRequest().body("El precio es obligatorio");
        }

        if (request.getPrecio() <= 0) {
            return ResponseEntity.badRequest().body("El precio debe ser mayor a 0");
        }

        // Buscar categoría
        CategoriaServicio cat = categorias.stream()
                .filter(c -> c.getCategoria().equalsIgnoreCase(request.getCategoria()))
                .findFirst()
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Categoría no encontrada")
                );

        // Generar nuevo ID
        int nuevoId = categorias.stream()
                .flatMap(c -> c.getSubservicios().stream())
                .mapToInt(SubServicio::getId)
                .max()
                .orElse(0) + 1;

        // Crear subservicio
        SubServicio nuevo = new SubServicio(nuevoId, request.getNombre(), request.getPrecio());
        cat.getSubservicios().add(nuevo);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Subservicio creado correctamente");
    }




    // ACTUALIZAR SUBSERVICIO

    @PutMapping("/subservicio/{id}")
    public ResponseEntity<?> actualizarSubServicio(
            @PathVariable int id,
            @RequestBody SubServicioRequest request) {

        // Buscar subservicio
        SubServicio sub = categorias.stream()
                .flatMap(c -> c.getSubservicios().stream())
                .filter(s -> s.getId() == id)
                .findFirst()
                .orElse(null);

        if (sub == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Subservicio inexistente");
        }

        // Validación de nombre
        if (request.getNombre() != null) {
            if (request.getNombre().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El nombre no puede estar vacío");
            }
            sub.setNombre(request.getNombre());
        }

        // Validación de precio
        if (request.getPrecio() != null) {
            if (request.getPrecio() <= 0) {
                return ResponseEntity.badRequest().body("El precio debe ser mayor a 0");
            }
            sub.setPrecio(request.getPrecio());
        }

        return ResponseEntity.ok("Subservicio actualizado correctamente");
    }




    // ELIMINAR SUBSERVICIO

    @DeleteMapping("/subservicio/{id}")
    public ResponseEntity<?> eliminarSubServicio(@PathVariable int id) {

        boolean existe = categorias.stream()
                .flatMap(c -> c.getSubservicios().stream())
                .anyMatch(s -> s.getId() == id);

        if (!existe) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Subservicio inexistente");
        }

        categorias.forEach(c ->
                c.getSubservicios().removeIf(s -> s.getId() == id)
        );

        return ResponseEntity.ok("Subservicio eliminado correctamente");
    }


    // REQUEST DTO

    public static class SubServicioRequest {

        private String categoria;
        private String nombre;
        private Double precio; // ← ahora permite detectar null

        public String getCategoria() {
            return categoria;
        }

        public String getNombre() {
            return nombre;
        }

        public Double getPrecio() {
            return precio;
        }

        public void setCategoria(String categoria) {
            this.categoria = categoria;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public void setPrecio(Double precio) {
            this.precio = precio;
        }
    }


}
