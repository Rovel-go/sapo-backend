package sapoCasaPrincesas.registro_login.usuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapoCasaPrincesas.registro_login.usuarios.model.Usuario;
import sapoCasaPrincesas.registro_login.usuarios.service.UsuarioService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // lista todos los usuarios en BD sapo
    @GetMapping
    public ResponseEntity<List<Usuario>> obtenerTodos() {
        return ResponseEntity.ok(usuarioService.obtenerTodos());
    }

    // obtiene usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.obtenerPorId(id);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // registro: nuevos usuarios en BD sapo
    @PostMapping
    public ResponseEntity<String> crear(@RequestBody Usuario usuario) {

        // Validaciones de campos obligatorios
        if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El nombre es obligatorio");
        }

        if (usuario.getApellidos() == null || usuario.getApellidos().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Los apellidos son obligatorios");
        }

        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        // Validación de formato de email
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest().body("El formato del email es inválido");
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La contraseña es obligatoria");
        }

        // Validación de email duplicado
        if (usuarioService.emailExiste(usuario.getEmail())) {
            return ResponseEntity.badRequest().body("El email ya está registrado");
        }

        boolean resultado = usuarioService.crear(usuario);

        if (resultado) {
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Usuario creado correctamente");
        }

        return ResponseEntity.badRequest().body("Error al crear usuario");
    }

    // actualizar usuario por ID (actualización parcial)
    @PutMapping("/{id}")
    public ResponseEntity<String> actualizar(
            @PathVariable Long id,
            @RequestBody Usuario datosNuevos) {

        Usuario usuarioActual = usuarioService.obtenerPorId(id);

        if (usuarioActual == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        // Validación de email si viene en la actualización
        if (datosNuevos.getEmail() != null) {

            if (datosNuevos.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("El email no puede estar vacío");
            }

            if (!datosNuevos.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                return ResponseEntity.badRequest().body("El formato del email es inválido");
            }

            // Evitar duplicados si cambia el email
            if (!datosNuevos.getEmail().equals(usuarioActual.getEmail())
                    && usuarioService.emailExiste(datosNuevos.getEmail())) {
                return ResponseEntity.badRequest().body("El email ya está registrado");
            }

            usuarioActual.setEmail(datosNuevos.getEmail());
        }

        // Actualizar solo los campos enviados
        if (datosNuevos.getNombre() != null) {
            usuarioActual.setNombre(datosNuevos.getNombre());
        }

        if (datosNuevos.getApellidos() != null) {
            usuarioActual.setApellidos(datosNuevos.getApellidos());
        }

        if (datosNuevos.getContrasena() != null) {
            usuarioActual.setContrasena(datosNuevos.getContrasena());
        }

        boolean actualizado = usuarioService.actualizar(id, usuarioActual);

        if (actualizado) {
            return ResponseEntity.ok("Usuario actualizado correctamente");
        }

        return ResponseEntity.badRequest().body("Error al actualizar usuario");
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Usuario> obtenerPorEmail(@PathVariable String email) {
        Usuario usuario = usuarioService.obtenerPorEmail(email);

        if (usuario != null) {
            return ResponseEntity.ok(usuario);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<Usuario>> obtenerPorNombre(@PathVariable String nombre) {
        List<Usuario> usuarios = usuarioService.obtenerTodos()
                .stream()
                .filter(u -> u.getNombre() != null &&
                        u.getNombre().toLowerCase().contains(nombre.toLowerCase()))
                .toList();

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/apellidos/{apellidos}")
    public ResponseEntity<List<Usuario>> obtenerPorApellidos(@PathVariable String apellidos) {

        String filtro = apellidos.trim().toLowerCase();

        List<Usuario> usuarios = usuarioService.obtenerTodos()
                .stream()
                .filter(u -> u.getApellidos() != null &&
                        u.getApellidos().trim().toLowerCase().contains(filtro))
                .toList();

        return ResponseEntity.ok(usuarios);
    }

    // login: Ingreso a sistema de usuarios registrados en BD sapo
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {

        // Validaciones
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest().body("El formato del email es inválido");
        }

        if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La contraseña es obligatoria");
        }

        boolean valido = usuarioService.validarLogin(usuario.getEmail(), usuario.getContrasena());

        if (!valido) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Credenciales incorrectas");
        }

        return ResponseEntity.ok("Login exitoso");
    }

    // cambiar contraseña (usuario olvido contraseña)
    @PostMapping("/cambiar-contrasena")
    public ResponseEntity<String> cambiarContrasena(@RequestBody Map<String, String> body) {

        String email = body.get("email");
        String actual = body.get("actual");
        String nueva = body.get("nueva");

        // Validaciones
        if (email == null || email.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest().body("El formato del email es inválido");
        }

        if (actual == null || actual.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La contraseña actual es obligatoria");
        }

        if (nueva == null || nueva.trim().isEmpty()) {
            return ResponseEntity.badRequest().body("La nueva contraseña es obligatoria");
        }

        Usuario usuario = usuarioService.obtenerPorEmail(email);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("El correo no está registrado");
        }

        boolean actualizado = usuarioService.cambiarContrasena(usuario, actual, nueva);

        if (!actualizado) {
            return ResponseEntity.badRequest().body("La contraseña actual es incorrecta");
        }

        return ResponseEntity.ok("La contraseña ha sido actualizada correctamente");
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminar(@PathVariable Long id) {

        Usuario usuario = usuarioService.obtenerPorId(id);

        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Usuario no encontrado");
        }

        boolean eliminado = usuarioService.eliminar(id);

        if (eliminado) {
            return ResponseEntity.ok("Usuario eliminado correctamente");
        }

        return ResponseEntity.badRequest().body("Error al eliminar usuario");
    }

}
