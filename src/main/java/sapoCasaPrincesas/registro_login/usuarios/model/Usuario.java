package sapoCasaPrincesas.registro_login.usuarios.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {

    private Long id;

    @JsonProperty("nombre")
    private String nombre;

    @JsonProperty("apellidos")
    private String apellidos;

    @JsonProperty("email")
    private String email;

    // Hash almacenado en BD (no se envía al frontend)
    private String passwordHash;

    // Contraseña en texto plano recibida desde el frontend
    @JsonProperty("contrasena")
    private String contrasena;

    // Constructor vacío requerido por Spring
    public Usuario() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }

    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}