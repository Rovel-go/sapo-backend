package sapoCasaPrincesas.registro_login.usuarios.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import sapoCasaPrincesas.registro_login.usuarios.dao.UsuarioDao;
import sapoCasaPrincesas.registro_login.usuarios.model.Usuario;

import java.util.List;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioDao usuarioDao;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioDao usuarioDao, PasswordEncoder passwordEncoder) {
        this.usuarioDao = usuarioDao;
        this.passwordEncoder = passwordEncoder;
    }

    // Cuando email ya esta registrado en BD sapo
    public boolean emailExiste(String email) {
        return usuarioDao.emailExiste(email);
    }

    // lista todos los usuarios de la BD sapo
    public List<Usuario> obtenerTodos() {
        return usuarioDao.obtenerTodos();
    }

    // obtiene un usuario de la BD sapo por ID
    public Usuario obtenerPorId(Long id) {
        return usuarioDao.obtenerPorId(id);
    }

    // obtiene un usuario de la BD sapo por email
    public Usuario obtenerPorEmail(String email) {
        return usuarioDao.obtenerPorEmail(email);
    }

    // crea usuario en la BD sapo con hash
    public boolean crear(Usuario usuario) {
        try {
            if (usuario.getNombre() == null || usuario.getNombre().trim().isEmpty()) return false;
            if (usuario.getApellidos() == null || usuario.getApellidos().trim().isEmpty()) return false;
            if (usuario.getEmail() == null || usuario.getEmail().trim().isEmpty()) return false;
            if (usuario.getContrasena() == null || usuario.getContrasena().trim().isEmpty()) return false;

            String hash = passwordEncoder.encode(usuario.getContrasena());
            usuario.setPasswordHash(hash);

            return usuarioDao.crear(usuario) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // actualiza datos de usuario en BD sapo
    public boolean actualizar(Long id, Usuario usuario) {
        try {
            if (id == null) return false;

            if (usuario.getContrasena() != null && !usuario.getContrasena().trim().isEmpty()) {
                String hash = passwordEncoder.encode(usuario.getContrasena());
                usuario.setPasswordHash(hash);
            }

            usuario.setId(id);
            return usuarioDao.actualizar(usuario) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // elimina usuario de la BD sapo
    public boolean eliminar(Long id) {
        try {
            return usuarioDao.eliminar(id) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // login de usuario registrado
    public boolean validarLogin(String email, String contrasena) {
        try {
            System.out.println(">>> LOGIN EMAIL RECIBIDO: " + email);
            System.out.println(">>> LOGIN CONTRASENA RECIBIDA: " + contrasena);

            Usuario usuario = usuarioDao.obtenerPorEmail(email);

            if (usuario == null) {
                System.out.println(">>> USUARIO NO ENCONTRADO");
                return false;
            }

            System.out.println(">>> HASH EN BD: " + usuario.getPasswordHash());

            boolean coincide = passwordEncoder.matches(contrasena, usuario.getPasswordHash());
            System.out.println(">>> RESULTADO MATCH: " + coincide);

            return coincide;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Cambiar contraseña ( si el usuario la olvido)

    public boolean recuperarContrasena(String email) {

        // Validar que email esta registrado
        Usuario usuario = usuarioDao.obtenerPorEmail(email);

        if (usuario == null) {
            return false; // no existe
        }

        // Generar contraseña temporal
        String temporal = generarContrasenaTemporal();

        // Hacer hash
        String hash = passwordEncoder.encode(temporal);

        // Actualizar unicamente la contraseña temporal en BD sapo
        usuarioDao.actualizarPassword(usuario.getId(), hash);

        //  Mostrar en consola
        System.out.println("Nueva contraseña temporal para " + email + ": " + temporal);

        return true;
    }

    // Genera contraseña temporal de 8 caracteres
    public String generarContrasenaTemporal() {
        return UUID.randomUUID().toString().substring(0, 8);
    }


    //  Cambiar contraseña (si el usuario recuerda la actual) Requiere implementación en frontend

    public boolean cambiarContrasena(Usuario usuario, String actual, String nueva) {

        if (!passwordEncoder.matches(actual, usuario.getPasswordHash())) {
            return false;
        }

        try {
            String hash = passwordEncoder.encode(nueva);
            usuario.setPasswordHash(hash);
            return usuarioDao.actualizar(usuario) > 0;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
