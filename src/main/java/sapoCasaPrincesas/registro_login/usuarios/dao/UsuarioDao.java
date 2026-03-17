package sapoCasaPrincesas.registro_login.usuarios.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import sapoCasaPrincesas.registro_login.usuarios.model.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UsuarioDao {

    private final JdbcTemplate jdbcTemplate;

    public UsuarioDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // Mapea un registro de la BD a un objeto Usuario
    private final RowMapper<Usuario> usuarioMapper = new RowMapper<Usuario>() {
        @Override
        public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
            Usuario u = new Usuario();
            u.setId(rs.getLong("id"));
            u.setNombre(rs.getString("nombre"));
            u.setApellidos(rs.getString("apellidos"));
            u.setEmail(rs.getString("email"));
            u.setPasswordHash(rs.getString("password_hash"));
            return u;
        }
    };

    // Lista todos los usuarios de BD sapo
    public List<Usuario> obtenerTodos() {
        String sql = "SELECT id, nombre, apellidos, email, password_hash FROM usuarios";
        return jdbcTemplate.query(sql, usuarioMapper);
    }

    // Obtiene un usuario de la BD sapo por ID
    public Usuario obtenerPorId(Long id) {
        String sql = "SELECT id, nombre, apellidos, email, password_hash FROM usuarios WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, usuarioMapper, id);
    }

    // Obtiene un usuario de la BD sapo por email
    public Usuario obtenerPorEmail(String email) {
        try {
            String sql = "SELECT id, nombre, apellidos, email, password_hash FROM usuarios WHERE email = ?";
            return jdbcTemplate.queryForObject(sql, usuarioMapper, email);
        } catch (Exception e) {
            return null;
        }
    }

    // Inserta datos de nuevo usuario
    public int crear(Usuario usuario) {
        try {
            String sql = "INSERT INTO usuarios (nombre, apellidos, email, password_hash) VALUES (?, ?, ?, ?)";
            return jdbcTemplate.update(sql,
                    usuario.getNombre(),
                    usuario.getApellidos(),
                    usuario.getEmail(),
                    usuario.getPasswordHash());
        } catch (Exception e) {
            System.out.println("ERROR en UsuarioDao.crear: " + e.getMessage());
            return 0;
        }
    }

    // Actualiza todos los cambios del usuario en Bd sapo
    public int actualizar(Usuario usuario) {
        String sql = "UPDATE usuarios SET nombre = ?, apellidos = ?, email = ?, password_hash = ? WHERE id = ?";
        return jdbcTemplate.update(sql,
                usuario.getNombre(),
                usuario.getApellidos(),
                usuario.getEmail(),
                usuario.getPasswordHash(),
                usuario.getId());
    }

    // Actualiza solo la contraseña del usuario
    public int actualizarPassword(Long id, String passwordHash) {
        String sql = "UPDATE usuarios SET password_hash = ? WHERE id = ?";
        return jdbcTemplate.update(sql, passwordHash, id);
    }

    // Elimina un usuario de la BD sapo por ID
    public int eliminar(Long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    // Verifica si un email ya está registrado en BD sapo
    public boolean emailExiste(String email) {
        String sql = "SELECT COUNT(*) FROM usuarios WHERE email = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, email);
        return count != null && count > 0;
    }
}
