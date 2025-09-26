package Resol.IngaramoJ;

public class PedidoDetalleDTO {
    private String nombreProducto;
    private double subtotal;

    public PedidoDetalleDTO() {}
    public PedidoDetalleDTO(String nombreProducto, double subtotal) {
        this.nombreProducto = nombreProducto;
        this.subtotal = subtotal;
    }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }
    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
}
