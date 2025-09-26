package Resol.IngaramoJ;

// quitá: import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PedidoController {
    private final IPedidoDAO dao;
    private final PedidoView view;
    private final Scanner sc = new Scanner(System.in);

    public PedidoController(IPedidoDAO dao, PedidoView view) {
        this.dao = dao; this.view = view;
    }

    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            int op = view.menu();
            try {
                switch (op) {
                    case 1 -> alta();
                    case 2 -> modificar();
                    case 3 -> { int id = view.pedirIdPedido(); dao.eliminar(id); view.ok("Eliminado"); }
                    case 4 -> dao.listarTodos().forEach(p -> System.out.println(render(p)));
                    case 5 -> { int idCli = view.pedirIdCliente(); dao.listarPorCliente(idCli).forEach(p -> System.out.println(render(p))); }
                    case 6 -> { int id = view.pedirIdPedido(); String est = view.pedirEstado(); dao.cambiarEstado(id, est); view.ok("Estado cambiado"); }
                    case 0 -> salir = true;
                    default -> view.error("Opción inválida");
                }
            } catch (ReglaNegocioException e) {
                view.error("Regla: " + e.getMessage());
            } catch (Exception e) {
                view.error("Error: " + e.getMessage());
            }
        }
    }

    private void alta() throws ReglaNegocioException {
        PedidoDTO p = new PedidoDTO();
        p.setFechaPedido(LocalDate.now());
        System.out.print("Fecha entrega (YYYY-MM-DD o vacío): ");
        String f = sc.nextLine().trim();
        if (!f.isBlank()) p.setFechaEntrega(LocalDate.parse(f));
        System.out.print("ID Cliente: "); p.setIdCliente(Integer.parseInt(sc.nextLine().trim()));
        System.out.print("Cliente nombre completo: "); p.setClienteNombreCompleto(sc.nextLine());
        System.out.print("Cliente empresa: "); p.setClienteEmpresa(sc.nextLine());
        System.out.print("Cliente tipo: "); p.setClienteTipoEmpresa(sc.nextLine());
        System.out.print("Cliente localidad: "); p.setClienteLocalidad(sc.nextLine());
        System.out.print("Empleado nombre completo: "); p.setEmpleadoNombreCompleto(sc.nextLine());
        System.out.print("Empleado cargo: "); p.setEmpleadoCargo(sc.nextLine());

        List<PedidoDetalleDTO> dets = new ArrayList<>();
        while (true) {
            System.out.print("Producto (vacío para terminar): ");
            String prod = sc.nextLine().trim();
            if (prod.isBlank()) break;
            System.out.print("Subtotal: ");
            double sub = Double.parseDouble(sc.nextLine().trim());
            dets.add(new PedidoDetalleDTO(prod, sub));
        }
        p.setDetalles(dets);
        p.setMontoTotal(dets.stream().mapToDouble(PedidoDetalleDTO::getSubtotal).sum());
        dao.insertar(p);
        view.ok("Alta OK. ID=" + p.getIdPedido() + " total=" + p.getMontoTotal());
    }

    private void modificar() throws ReglaNegocioException {
        System.out.print("ID pedido: ");
        int id = Integer.parseInt(sc.nextLine().trim());
        PedidoDTO p = dao.obtenerPorId(id);
        if (p == null) { view.error("No existe"); return; }
        System.out.print("Fecha entrega (" + p.getFechaEntrega() + "): ");
        String f = sc.nextLine().trim();
        if (!f.isBlank()) p.setFechaEntrega(LocalDate.parse(f));
        System.out.print("Monto total (" + p.getMontoTotal() + "): ");
        String mt = sc.nextLine().trim();
        if (!mt.isBlank()) p.setMontoTotal(Double.parseDouble(mt));
        dao.actualizar(p);
        view.ok("Actualizado");
    }

    private String render(PedidoDTO p) {
        int cant = p.getDetalles() == null ? 0 : p.getDetalles().size();
        return String.format("#%d %s total=%.2f | ClienteID=%d %s/%s | Emp=%s/%s | det=%d",
                p.getIdPedido(), p.getEstado(), p.getMontoTotal(), p.getIdCliente(),
                p.getClienteNombreCompleto(), p.getClienteEmpresa(),
                p.getEmpleadoNombreCompleto(), p.getEmpleadoCargo(), cant);
    }
}