package Resol.IngaramoJ;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    // a) obtenerEmpleados() — con JOINs para mostrar nombres (no IDs)
    public List<Empleado> obtenerEmpleados() throws SQLException {
        String sql = """
            SELECT e.id_empleado,
                   e.nombre,
                   l.nombre AS localidad,
                   j.nombre AS jefe
            FROM empleado e
            LEFT JOIN localidad l ON e.id_localidad = l.id_localidad
            LEFT JOIN empleado  j ON e.id_jefe      = j.id_empleado
            ORDER BY e.id_empleado ASC
        """;
        List<Empleado> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Empleado(
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getString("localidad"),
                        rs.getString("jefe")
                ));
            }
        }
        return lista;
    }

    // b) obtenerEmpleado(int id)
    public Empleado obtenerEmpleado(int id) throws SQLException {
        String sql = """
            SELECT e.id_empleado,
                   e.nombre,
                   l.nombre AS localidad,
                   j.nombre AS jefe
            FROM empleado e
            LEFT JOIN localidad l ON e.id_localidad = l.id_localidad
            LEFT JOIN empleado  j ON e.id_jefe      = j.id_empleado
            WHERE e.id_empleado = ?
        """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Empleado(
                            rs.getInt("id_empleado"),
                            rs.getString("nombre"),
                            rs.getString("localidad"),
                            rs.getString("jefe")
                    );
                }
            }
        }
        return null;
    }

    // c) obtenerUltimoId()
    public int obtenerUltimoId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(id_empleado),0) AS max_id FROM empleado";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt("max_id") : 0;
        }
    }

    // d) agregarEmpleado(...)
    public void agregarEmpleado(String nombre, Integer idLocalidad, Integer idJefe) throws SQLException {
        String sql = "INSERT INTO empleado (nombre, id_localidad, id_jefe) VALUES (?, ?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            if (idLocalidad == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, idLocalidad);
            if (idJefe == null)      ps.setNull(3, Types.INTEGER); else ps.setInt(3, idJefe);
            ps.executeUpdate();
        }
    }

    // e1) actualizarEmpleado(int id) — overload para cumplir la firma exacta
    public void actualizarEmpleado(int id) throws SQLException {
        String sql = "UPDATE empleado SET nombre = nombre || ' (actualizado)' WHERE id_empleado = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // e2) actualizarEmpleado completo (opcional, lo podés usar también)
    public void actualizarEmpleado(int id, String nuevoNombre, Integer idLocalidad, Integer idJefe) throws SQLException {
        String sql = "UPDATE empleado SET nombre = ?, id_localidad = ?, id_jefe = ? WHERE id_empleado = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            if (idLocalidad == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, idLocalidad);
            if (idJefe == null)      ps.setNull(3, Types.INTEGER); else ps.setInt(3, idJefe);
            ps.setInt(4, id);
            ps.executeUpdate();
        }
    }

    // f) eliminarEmpleado(int id)
    public void eliminarEmpleado(int id) throws SQLException {
        String sql = "DELETE FROM empleado WHERE id_empleado = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // ====== REGLAS / REPORTES ======

    // 1) Listar empleados con antigüedad (años) y % de bono
    public List<String> listarConAntiguedadYBono() throws SQLException {
        String sql = """
            SELECT e.id_empleado,
                   e.nombre,
                   COALESCE(EXTRACT(YEAR FROM age(current_date, e.fecha_contratacion))::int, 0) AS antiguedad,
                   CASE
                     WHEN COALESCE(EXTRACT(YEAR FROM age(current_date, e.fecha_contratacion))::int, 0) >= 10 THEN 15
                     WHEN COALESCE(EXTRACT(YEAR FROM age(current_date, e.fecha_contratacion))::int, 0) >= 5  THEN 10
                     WHEN COALESCE(EXTRACT(YEAR FROM age(current_date, e.fecha_contratacion))::int, 0) >= 2  THEN 5
                     ELSE 0
                   END AS bono
            FROM empleado e
            ORDER BY e.id_empleado
        """;
        List<String> out = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(String.format(
                        "%d - %s | Antigüedad: %d años | Bono: %d%%",
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        rs.getInt("antiguedad"),
                        rs.getInt("bono")
                ));
            }
        }
        return out;
    }

    // 2) Empleados que cumplen años este mes
    public List<String> empleadosCumpleMes() throws SQLException {
        String sql = """
            SELECT id_empleado, nombre, fecha_nacimiento
            FROM empleado
            WHERE fecha_nacimiento IS NOT NULL
              AND EXTRACT(MONTH FROM fecha_nacimiento) = EXTRACT(MONTH FROM current_date)
            ORDER BY EXTRACT(DAY FROM fecha_nacimiento), id_empleado
        """;
        List<String> out = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Date fn = rs.getDate("fecha_nacimiento");
                out.add(String.format(
                        "%d - %s | Cumple: %s",
                        rs.getInt("id_empleado"),
                        rs.getString("nombre"),
                        fn != null ? fn.toString() : "N/D"
                ));
            }
        }
        return out;
    }
}
