package Resol.IngaramoJ;

// quitá: import java.sql.SQLException;

public class ClienteController {
    private final IClienteDAO dao;
    private final ClienteView view;

    public ClienteController(IClienteDAO dao, ClienteView view) {
        this.dao = dao;
        this.view = view;
    }

    public void ejecutar() {
        boolean salir = false;
        while (!salir) {
            int op = view.menu();
            try {
                switch (op) {
                    case 1 -> { var c = view.pedirAlta(); dao.insertar(c); view.ok("Alta OK. ID=" + c.getIdCliente()); }
                    case 2 -> { int id = view.pedirId(); var o = dao.obtenerPorId(id);
                        if (o == null) { view.error("No existe"); break; }
                        var m = view.pedirModif(o); dao.actualizar(m); view.ok("Actualizado"); }
                    case 3 -> { int id = view.pedirId(); dao.eliminar(id); view.ok("Eliminado"); }
                    case 4 -> view.listar(dao.listarTodos());
                    case 5 -> { int id = view.pedirId(); view.mostrar(dao.obtenerPorId(id)); }
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
}

