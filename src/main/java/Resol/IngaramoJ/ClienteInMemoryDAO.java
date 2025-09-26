package Resol.IngaramoJ;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ClienteInMemoryDAO implements IClienteDAO {
    private final Map<Integer, ClienteDTO> store = new LinkedHashMap<>();
    private final AtomicInteger sec = new AtomicInteger(1);

    private void validar(ClienteDTO c) throws ReglaNegocioException {
        if (c.getNombreCompleto() == null || c.getNombreCompleto().isBlank())
            throw new ReglaNegocioException("El nombre completo es obligatorio");
        if (c.getFechaAlta() == null)
            throw new ReglaNegocioException("La fecha de alta es obligatoria");
        if (c.getFechaAlta().isAfter(LocalDate.now()))
            throw new ReglaNegocioException("La fecha de alta no puede ser futura");
    }

    @Override
    public void insertar(ClienteDTO c) throws ReglaNegocioException {
        validar(c);
        c.setIdCliente(sec.getAndIncrement());
        store.put(c.getIdCliente(), c);
    }

    @Override
    public void actualizar(ClienteDTO c) throws ReglaNegocioException {
        if (c.getIdCliente() <= 0 || !store.containsKey(c.getIdCliente()))
            throw new ReglaNegocioException("Cliente inexistente");
        validar(c);
        store.put(c.getIdCliente(), c);
    }

    @Override
    public void eliminar(int id) throws ReglaNegocioException {
        if (!store.containsKey(id))
            throw new ReglaNegocioException("Cliente inexistente");
        store.remove(id);
    }

    @Override
    public ClienteDTO obtenerPorId(int id) { return store.get(id); }

    @Override
    public List<ClienteDTO> listarTodos() { return new ArrayList<>(store.values()); }
}
