package Resol.IngaramoJ;

import java.util.List;

public interface IPedidoDAO {
    void insertar(PedidoDTO p) throws ReglaNegocioException;   // inserta pedido + detalle
    void actualizar(PedidoDTO p) throws ReglaNegocioException; // CRUD completo
    void eliminar(int id) throws ReglaNegocioException;
    PedidoDTO obtenerPorId(int id);
    List<PedidoDTO> listarTodos();
    List<PedidoDTO> listarPorCliente(int idCliente);
    void cambiarEstado(int idPedido, String nuevoEstado) throws ReglaNegocioException;
}

