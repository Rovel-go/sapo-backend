package sapoCasaPrincesas.registro_login.catalogo.model;

// Representa un subservicio individual dentro de una categoría (ej: "Peinado básico")
public class SubServicio {

    // Identificador único del subservicio
    private int id;

    // Nombre del subservicio
    private String nombre;

    // Precio asociado al subservicio
    private double precio;

    // Constructor principal para crear el objeto con sus datos
    public SubServicio(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    // Permiten modificar los datos cuando se hace un CRUD
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
