package Resol.IngaramoJ;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class PedidoInMemoryDAO implements IPedidoDAO {
    private final Map<Integer, PedidoDTO> store = new LinkedHashMap<>();
    private final AtomicInteger sec = new AtomicInteger(1);

    private void inferirEstado(PedidoDTO p) {
        if (p.getFechaEntrega() != null) p.setEstado("ENTREGADO");
        else if (p.getFechaEnvio() != null) p.setEstado("ENVIADO");
        else p.setEstado("PENDIENTE");
    }

    private void validar(PedidoDTO p) throws ReglaNegocioException {
        if (p.getFechaPedido() == null) throw new ReglaNegocioException("fecha_pedido es obligatoria");
        if (p.getFechaEntrega() != null && p.getFechaPedido().isAfter(p.getFechaEntrega()))
            throw new ReglaNegocioException("fecha_pedido debe ser <= fecha_entrega");
        if (p.getMontoTotal() <= 0) throw new ReglaNegocioException("monto_total debe ser > 0");
        inferirEstado(p);
    }

    @Override
    public void insertar(PedidoDTO p) throws ReglaNegocioException {
        validar(p);
        p.setIdPedido(sec.getAndIncrement());
        store.put(p.getIdPedido(), p);
    }

    @Override
    public void actualizar(PedidoDTO p) throws ReglaNegocioException {
        if (p.getIdPedido() <= 0 || !store.containsKey(p.getIdPedido()))
            throw new ReglaNegocioException("Pedido inexistente");
        validar(p);
        store.put(p.getIdPedido(), p);
    }

    @Override
    public void eliminar(int id) throws ReglaNegocioException {
        if (!store.containsKey(id))
            throw new ReglaNegocioException("Pedido inexistente");
        store.remove(id);
    }

    @Override
    public PedidoDTO obtenerPorId(int id) { return store.get(id); }

    @Override
    public List<PedidoDTO> listarTodos() { return new ArrayList<>(store.values()); }

    @Override
    public List<PedidoDTO> listarPorCliente(int idCliente) {
        List<PedidoDTO> out = new ArrayList<>();
        for (PedidoDTO p : store.values()) if (p.getIdCliente() == idCliente) out.add(p);
        return out;
    }

    @Override
    public void cambiarEstado(int idPedido, String nuevoEstado) throws ReglaNegocioException {
        PedidoDTO p = store.get(idPedido);
        if (p == null) throw new ReglaNegocioException("Pedido inexistente");
        String target = (nuevoEstado == null ? "" : nuevoEstado.trim().toUpperCase());
        switch (target) {
            case "PENDIENTE" -> { p.setFechaEnvio(null); p.setFechaEntrega(null); }
            case "ENVIADO"   -> { if (p.getFechaEnvio() == null) p.setFechaEnvio(LocalDate.now()); p.setFechaEntrega(null); }
            case "ENTREGADO" -> { if (p.getFechaEnvio() == null) p.setFechaEnvio(LocalDate.now()); if (p.getFechaEntrega() == null) p.setFechaEntrega(LocalDate.now()); }
            case "PAGADO"    -> { /* opcional si querés modelar PENDIENTE->PAGADO->ENTREGADO */ }
            default -> throw new ReglaNegocioException("Estado inválido");
        }
        inferirEstado(p);
        store.put(p.getIdPedido(), p);
    }
}
