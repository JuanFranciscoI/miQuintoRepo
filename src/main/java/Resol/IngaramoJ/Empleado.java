package Resol.IngaramoJ;
    public class Empleado {
        private int idEmpleado;
        private String nombre;
        private String localidad; // nombre de localidad
        private String jefe;      // nombre del jefe

        public Empleado() {}

        public Empleado(int idEmpleado, String nombre, String localidad, String jefe) {
            this.idEmpleado = idEmpleado;
            this.nombre = nombre;
            this.localidad = localidad;
            this.jefe = jefe;
        }

        // Getters y setters
        public int getIdEmpleado() { return idEmpleado; }
        public void setIdEmpleado(int idEmpleado) { this.idEmpleado = idEmpleado; }

        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }

        public String getLocalidad() { return localidad; }
        public void setLocalidad(String localidad) { this.localidad = localidad; }

        public String getJefe() { return jefe; }
        public void setJefe(String jefe) { this.jefe = jefe; }

        @Override
        public String toString() {
            return idEmpleado + " - " + nombre + " | Localidad: " + localidad + " | Jefe: " + jefe;
        }
    }
