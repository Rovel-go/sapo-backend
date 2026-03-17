package sapoCasaPrincesas.registro_login.usuarios.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sapoCasaPrincesas.registro_login.usuarios.model.Usuario;
import sapoCasaPrincesas.registro_login.usuarios.service.UsuarioService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class UsuarioRestController {

    private final UsuarioService usuarioService;

    public UsuarioRestController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Registro de usuario nuevo en BD sapo
    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody Usuario usuario) {

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

        // Crear usuario
        boolean creado = usuarioService.crear(usuario);

        return creado
                ? ResponseEntity.ok("Usuario registrado correctamente")
                : ResponseEntity.badRequest().body("Error al crear usuario");
    }

    // Login de usuario registrado
    @PostMapping("/login")
    public ResponseEntity<?> loginUsuario(@RequestBody Usuario usuario) {

        // Validaciones de campos obligatorios
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

        boolean valido = usuarioService.validarLogin(
                usuario.getEmail(),
                usuario.getContrasena()
        );

        return valido
                ? ResponseEntity.ok("Login exitoso")
                : ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }

    // Recuperar contraseña
    @PostMapping("/recuperar")
    public ResponseEntity<?> recuperarContrasena(@RequestBody Usuario usuario) {

        // Validación de email obligatorio
        if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("El email es obligatorio");
        }

        // Validación de formato de email
        if (!usuario.getEmail().matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            return ResponseEntity.badRequest().body("El formato del email es inválido");
        }

        boolean recuperado = usuarioService.recuperarContrasena(usuario.getEmail());

        return recuperado
                ? ResponseEntity.ok("Se ha enviado una nueva contraseña a su correo")
                : ResponseEntity.badRequest().body("No existe un usuario con ese email");
    }
}
