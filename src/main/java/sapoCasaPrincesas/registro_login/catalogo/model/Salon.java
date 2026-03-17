package sapoCasaPrincesas.registro_login.catalogo.model;

public class Salon {

    private int id;
    private String nombre;
    private String descripcion;
    private String ubicacion;
    private String foto;

    public Salon(int id, String nombre, String descripcion, String ubicacion, String foto) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.ubicacion = ubicacion;
        this.foto = foto;
    }

    public int getId() {
        return id;
    }

    // Setter necesario para el CRUD (ID autogenerado)
    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}