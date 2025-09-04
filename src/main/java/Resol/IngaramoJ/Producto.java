package Resol.IngaramoJ;

public class Producto {
    private int idProducto;
    private String nombre;
    private String categoria; // nombre de la categoría, no el id

    public Producto() {}

    public Producto(int idProducto, String nombre, String categoria) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return idProducto + " - " + nombre + " | Categoria: " + categoria;
    }
}
