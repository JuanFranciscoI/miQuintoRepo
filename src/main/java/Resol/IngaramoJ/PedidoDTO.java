package Resol.IngaramoJ;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PedidoDTO {
    private int idPedido;
    private LocalDate fechaPedido;
    private LocalDate fechaEntrega;
    private LocalDate fechaEnvio;
    private String estado;        // Pendiente / Enviado / Entregado / Pagado (si lo usás)
    private double montoTotal;

    // Identificador para listar por cliente
    private int idCliente;

    // Cliente asociado
    private String clienteNombreCompleto;
    private String clienteEmpresa;
    private String clienteTipoEmpresa;
    private String clienteLocalidad;

    // Empleado asociado
    private String empleadoNombreCompleto;
    private String empleadoCargo;

    private List<PedidoDetalleDTO> detalles = new ArrayList<>();

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public LocalDate getFechaPedido() { return fechaPedido; }
    public void setFechaPedido(LocalDate fechaPedido) { this.fechaPedido = fechaPedido; }
    public LocalDate getFechaEntrega() { return fechaEntrega; }
    public void setFechaEntrega(LocalDate fechaEntrega) { this.fechaEntrega = fechaEntrega; }
    public LocalDate getFechaEnvio() { return fechaEnvio; }
    public void setFechaEnvio(LocalDate fechaEnvio) { this.fechaEnvio = fechaEnvio; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public double getMontoTotal() { return montoTotal; }
    public void setMontoTotal(double montoTotal) { this.montoTotal = montoTotal; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
    public String getClienteNombreCompleto() { return clienteNombreCompleto; }
    public void setClienteNombreCompleto(String clienteNombreCompleto) { this.clienteNombreCompleto = clienteNombreCompleto; }
    public String getClienteEmpresa() { return clienteEmpresa; }
    public void setClienteEmpresa(String clienteEmpresa) { this.clienteEmpresa = clienteEmpresa; }
    public String getClienteTipoEmpresa() { return clienteTipoEmpresa; }
    public void setClienteTipoEmpresa(String clienteTipoEmpresa) { this.clienteTipoEmpresa = clienteTipoEmpresa; }
    public String getClienteLocalidad() { return clienteLocalidad; }
    public void setClienteLocalidad(String clienteLocalidad) { this.clienteLocalidad = clienteLocalidad; }
    public String getEmpleadoNombreCompleto() { return empleadoNombreCompleto; }
    public void setEmpleadoNombreCompleto(String empleadoNombreCompleto) { this.empleadoNombreCompleto = empleadoNombreCompleto; }
    public String getEmpleadoCargo() { return empleadoCargo; }
    public void setEmpleadoCargo(String empleadoCargo) { this.empleadoCargo = empleadoCargo; }
    public List<PedidoDetalleDTO> getDetalles() { return detalles; }
    public void setDetalles(List<PedidoDetalleDTO> detalles) { this.detalles = detalles; }
}
