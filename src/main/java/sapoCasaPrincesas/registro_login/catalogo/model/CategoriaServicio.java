package sapoCasaPrincesas.registro_login.catalogo.model;

import java.util.List;

// Clase que agrupa una categoría junto con todos sus subservicios
public class CategoriaServicio {

    // Nombre de la categoría (ej: "Maquillaje", "Peinados", etc.)
    private String categoria;

    // Lista de subservicios que pertenecen a esta categoría
    private List<SubServicio> subservicios;

    // Constructor principal para inicializar la categoría con sus subservicios
    public CategoriaServicio(String categoria, List<SubServicio> subservicios) {
        this.categoria = categoria;
        this.subservicios = subservicios;
    }

    // Devuelve el nombre de la categoría
    public String getCategoria() {
        return categoria;
    }

    // Retorna todos los subservicios asociados a esta categoría
    public List<SubServicio> getSubservicios() {
        return subservicios;
    }
}
