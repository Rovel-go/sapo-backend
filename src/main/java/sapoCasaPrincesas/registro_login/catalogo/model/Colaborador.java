package sapoCasaPrincesas.registro_login.catalogo.model;

// Clase que representa a un colaborador dentro del catálogo del sistema
public class Colaborador {

    // Identificador único del colaborador (lo usa el CRUD)
    private int id;

    // Nombre real del colaborador
    private String nombre;

    // Rol que desempeña dentro del proyecto (Peluqeuro, Colorista, Peinador, etc.)
    private String rol;

    // Especialidad principal del colaborador
    private String especialidad;

    // Años de experiencia que tiene el coloaborador
    private String experiencia;

    // URL o ruta de la foto del colaborador
    private String foto;

    // Constructor vacío requerido por Spring y por el mapeo JSON
    public Colaborador() {}

    // Constructor completo para crear objetos manualmente si se necesita
    public Colaborador(int id, String nombre, String rol, String especialidad, String experiencia, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.rol = rol;
        this.especialidad = especialidad;
        this.experiencia = experiencia;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    // Setter necesario para actualizar el ID cuando se autogenera en el CRUD
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    // Permite modificar el nombre del colaborador
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRol() {
        return rol;
    }

    // Actualiza el rol asignado al colaborador
    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    // Define la especialidad principal del colaborador
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getExperiencia() {
        return experiencia;
    }

    // Guarda la experiencia o trayectoria del colaborador
    public void setExperiencia(String experiencia) {
        this.experiencia = experiencia;
    }

    public String getFoto() {
        return foto;
    }

    // Asigna la foto o imagen asociada al colaborador
    public void setFoto(String foto) {
        this.foto = foto;
    }
}
