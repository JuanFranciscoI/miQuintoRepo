package Resol.IngaramoJ;

import java.util.List;

public interface IClienteDAO {
    void insertar(ClienteDTO c) throws ReglaNegocioException;
    void actualizar(ClienteDTO c) throws ReglaNegocioException;
    void eliminar(int id) throws ReglaNegocioException;
    ClienteDTO obtenerPorId(int id);
    List<ClienteDTO> listarTodos();
}
