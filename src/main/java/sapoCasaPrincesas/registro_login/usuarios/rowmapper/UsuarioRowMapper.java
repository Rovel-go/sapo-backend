package sapoCasaPrincesas.registro_login.usuarios.rowmapper;

import sapoCasaPrincesas.registro_login.usuarios.model.Usuario;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

// Convierte cada fila del ResultSet en un objeto Usuario (lo usa JdbcTemplate)
public class UsuarioRowMapper implements RowMapper<Usuario> {

    @Override
    public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {

        // Creo el objeto Usuario y lleno sus campos con los valores de la BD
        Usuario usuario = new Usuario();
        usuario.setId(rs.getLong("id"));
        usuario.setNombre(rs.getString("nombre"));
        usuario.setApellidos(rs.getString("apellidos"));
        usuario.setEmail(rs.getString("email"));

        // Obtengo el hash de la contraseña almacenado en la tabla
        usuario.setPasswordHash(rs.getString("password_hash"));

        return usuario;
    }
}
