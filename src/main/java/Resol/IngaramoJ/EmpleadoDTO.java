package Resol.IngaramoJ;

public class EmpleadoDTO {
    private int idEmpleado;
    private String nombreCompleto;
    private int antiguedad;       // años
    private double bonificacion;  // %
    private String localidad;
    private String cargo;

    public EmpleadoDTO() {}

    public EmpleadoDTO(int idEmpleado, String nombreCompleto, int antiguedad, double bonificacion, String localidad, String cargo) {
        this.idEmpleado = idEmpleado;
        this.nombreCompleto = nombreCompleto;
        this.antiguedad = antiguedad;
        this.bonificacion = bonificacion;
        this.localidad = localidad;
        this.cargo = cargo;
    }

    public int getIdEmpleado() { return idEmpleado; }
    public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public int getAntiguedad() { return antiguedad; }
    public void setAntiguedad(int antiguedad) { this.antiguedad = antiguedad; }
    public double getBonificacion() { return bonificacion; }
    public void setBonificacion(double bonificacion) { this.bonificacion = bonificacion; }
    public String getLocalidad() { return localidad; }
    public void setLocalidad(String localidad) { this.localidad = localidad; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
}
