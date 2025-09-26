package Resol.IngaramoJ;

public class ProductoDTO {
    private int idProducto;
    private String nombre;
    private int stock;
    private boolean disponible;
    private boolean necesitaReposicion;

    public ProductoDTO() {}

    public ProductoDTO(int idProducto, String nombre, int stock, boolean disponible, boolean necesitaReposicion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.stock = stock;
        this.disponible = disponible;
        this.necesitaReposicion = necesitaReposicion;
    }

    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
    public boolean isDisponible() { return disponible; }
    public void setDisponible(boolean disponible) { this.disponible = disponible; }
    public boolean isNecesitaReposicion() { return necesitaReposicion; }
    public void setNecesitaReposicion(boolean necesitaReposicion) { this.necesitaReposicion = necesitaReposicion; }
}
