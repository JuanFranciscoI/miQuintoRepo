package Resol.IngaramoJ;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO {

    // === CRUD PEDIDO POR LA CONSIGNA ===

    // a) obtenerProductos()
    public List<Producto> obtenerProductos() throws SQLException {
        String sql = """
            SELECT p.id_producto,
                   p.nombre_producto AS nombre,
                   COALESCE(c.nombre, 'Sin categoría') AS categoria
            FROM producto p
            LEFT JOIN categoria c ON p.id_categoria = c.id_categoria
            ORDER BY p.id_producto ASC
        """;
        List<Producto> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria")
                ));
            }
        }
        return lista;
    }

    // b) obtenerProducto(int id)
    public Producto obtenerProducto(int id) throws SQLException {
        String sql = """
            SELECT p.id_producto,
                   p.nombre_producto AS nombre,
                   COALESCE(c.nombre, 'Sin categoría') AS categoria
            FROM producto p
            LEFT JOIN categoria c ON p.id_categoria = c.id_categoria
            WHERE p.id_producto = ?
        """;
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Producto(
                            rs.getInt("id_producto"),
                            rs.getString("nombre"),
                            rs.getString("categoria")
                    );
                }
            }
        }
        return null;
    }

    // c) obtenerUltimoId()
    public int obtenerUltimoId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(id_producto), 0) AS max_id FROM producto";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            return rs.next() ? rs.getInt("max_id") : 0;
        }
    }

    // d) agregarProducto(String nombre, Integer idCategoria)
    public void agregarProducto(String nombre, Integer idCategoria) throws SQLException {
        String sql = "INSERT INTO producto (nombre_producto, id_categoria) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nombre);
            if (idCategoria == null) ps.setNull(2, Types.INTEGER); else ps.setInt(2, idCategoria);
            ps.executeUpdate();
        }
    }

    // e) actualizarProducto(int id)  (overload pedido)
    public void actualizarProducto(int id) throws SQLException {
        String sql = "UPDATE producto SET nombre_producto = nombre_producto || ' (upd)' WHERE id_producto = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // e-bis) actualizarProducto(int id, String nuevoNombre)  (opcional)
    public void actualizarProducto(int id, String nuevoNombre) throws SQLException {
        String sql = "UPDATE producto SET nombre_producto = ? WHERE id_producto = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, nuevoNombre);
            ps.setInt(2, id);
            ps.executeUpdate();
        }
    }

    // f) eliminarProducto(int id)
    public void eliminarProducto(int id) throws SQLException {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // === REGLAS (TP nuevo) ===

    // Disponibles: suspendido = false
    public List<Producto> listarDisponibles() throws SQLException {
        String sql = """
            SELECT p.id_producto,
                   p.nombre_producto AS nombre,
                   COALESCE(c.nombre,'Sin categoría') AS categoria
            FROM producto p
            LEFT JOIN categoria c ON p.id_categoria = c.id_categoria
            WHERE COALESCE(p.suspendido,false) = false
            ORDER BY p.id_producto
        """;
        List<Producto> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria")
                ));
            }
        }
        return lista;
    }

    // Necesitan reposición: unidades_en_existencia < nivel_nuevo_pedido
    public List<Producto> listarNecesitanReposicion() throws SQLException {
        String sql = """
            SELECT p.id_producto,
                   p.nombre_producto AS nombre,
                   COALESCE(c.nombre,'Sin categoría') AS categoria
            FROM producto p
            LEFT JOIN categoria c ON p.id_categoria = c.id_categoria
            WHERE p.unidades_en_existencia < p.nivel_nuevo_pedido
            ORDER BY p.id_producto
        """;
        List<Producto> lista = new ArrayList<>();
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getInt("id_producto"),
                        rs.getString("nombre"),
                        rs.getString("categoria")
                ));
            }
        }
        return lista;
    }
}
