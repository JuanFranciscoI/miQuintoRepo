package Resol.IngaramoJ;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        EmpleadoDAO empDAO = new EmpleadoDAO();
        ProductoDAO prodDAO = new ProductoDAO();

        // === TP6: DAOs y Controllers nuevos (en memoria) ===
        IClienteDAO clienteDAO = new ClienteInMemoryDAO();
        IPedidoDAO  pedidoDAO  = new PedidoInMemoryDAO();
        ClienteController clienteController = new ClienteController(clienteDAO, new ClienteView());
        PedidoController  pedidoController  = new PedidoController(pedidoDAO, new PedidoView());

        Scanner sc = new Scanner(System.in);
        boolean seguir = true;
        while (seguir) {
            switch (mostrarMenuPrincipal(sc)) {
                case "1" -> menuEmpleados(sc, empDAO);
                case "2" -> menuProductos(sc, prodDAO);
                case "3" -> clienteController.ejecutar();  // <-- TP6
                case "4" -> pedidoController.ejecutar();   // <-- TP6
                case "0" -> seguir = false;
                default -> System.out.println("Opción inválida.");
            }
        }
        System.out.println("Fin.");
        sc.close();
    }

    // ====== VISTA PRINCIPAL ======
    private static String mostrarMenuPrincipal(Scanner sc) {
        System.out.println("\n=== MENÚ PRINCIPAL ===");
        System.out.println("1. Gestión de Empleados");
        System.out.println("2. Gestión de Productos");
        System.out.println("3. Gestión de Clientes (TP6)"); // <-- TP6
        System.out.println("4. Gestión de Pedidos  (TP6)"); // <-- TP6
        System.out.println("0. Salir");
        System.out.print("Seleccione una opción: ");
        return sc.nextLine().trim();
    }

    // ====== EMPLEADOS (TP5) ======
    private static void menuEmpleados(Scanner sc, EmpleadoDAO empDAO) {
        System.out.println("\n=== GESTIÓN DE EMPLEADOS ===");
        System.out.println("1. Listar empleados");
        System.out.println("2. Buscar empleado por ID");
        System.out.println("3. Agregar empleado");
        System.out.println("4. Actualizar empleado");
        System.out.println("5. Eliminar empleado");
        System.out.println("6. Reporte de antigüedad y bonificaciones");
        System.out.println("7. Empleados que cumplen años este mes");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        String op = sc.nextLine().trim();

        try {
            switch (op) {
                case "1" -> empDAO.obtenerEmpleados().forEach(System.out::println);
                case "2" -> {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    var emp = empDAO.obtenerEmpleado(id);
                    System.out.println(emp != null ? emp : "No encontrado.");
                }
                case "3" -> {
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("id_localidad: ");
                    String sLoc = sc.nextLine().trim();
                    Integer idLoc = sLoc.isEmpty() ? null : Integer.parseInt(sLoc);
                    System.out.print("id_jefe (vacío = null): ");
                    String sJ = sc.nextLine().trim();
                    Integer idJefe = sJ.isEmpty() ? null : Integer.parseInt(sJ);
                    empDAO.agregarEmpleado(nombre, idLoc, idJefe);
                    System.out.println("Agregado. Último ID: " + empDAO.obtenerUltimoId());
                }
                case "4" -> {
                    System.out.print("ID a actualizar: ");
                    int id = Integer.parseInt(sc.nextLine());
                    empDAO.actualizarEmpleado(id); // overload (solo id)
                    System.out.println("Actualizado.");
                }
                case "5" -> {
                    System.out.print("ID a eliminar: ");
                    int id = Integer.parseInt(sc.nextLine());
                    empDAO.eliminarEmpleado(id);
                    System.out.println("Eliminado.");
                }
                case "6" -> {
                    try {
                        empDAO.listarConAntiguedadYBono().forEach(System.out::println);
                    } catch (Throwable t) {
                        System.out.println("No disponible (faltan columnas de fecha en la BD o método en DAO).");
                    }
                }
                case "7" -> {
                    try {
                        empDAO.empleadosCumpleMes().forEach(System.out::println);
                    } catch (Throwable t) {
                        System.out.println("No disponible (faltan columnas de fecha en la BD o método en DAO).");
                    }
                }
                case "0" -> { /* volver */ }
                default -> System.out.println("Opción inválida.");
            }
        } catch (SQLException e) {
            System.err.println("SQL: " + e.getMessage());
        } catch (NumberFormatException nfe) {
            System.err.println("Número inválido.");
        }
    }

    // ====== PRODUCTOS (TP5) ======
    private static void menuProductos(Scanner sc, ProductoDAO prodDAO) {
        System.out.println("\n=== GESTIÓN DE PRODUCTOS ===");
        System.out.println("1. Listar productos");
        System.out.println("2. Buscar producto por ID");
        System.out.println("3. Agregar producto");
        System.out.println("4. Actualizar producto");
        System.out.println("5. Eliminar producto");
        System.out.println("6. Productos que necesitan reposición");
        System.out.println("7. Productos disponibles para la venta");
        System.out.println("0. Volver al menú principal");
        System.out.print("Seleccione una opción: ");
        String op = sc.nextLine().trim();

        try {
            switch (op) {
                case "1" -> prodDAO.obtenerProductos().forEach(System.out::println);
                case "2" -> {
                    System.out.print("ID: ");
                    int id = Integer.parseInt(sc.nextLine());
                    var p = prodDAO.obtenerProducto(id);
                    System.out.println(p != null ? p : "No encontrado.");
                }
                case "3" -> {
                    System.out.print("Nombre: ");
                    String nombre = sc.nextLine();
                    System.out.print("id_categoria (vacío = null): ");
                    String sCat = sc.nextLine().trim();
                    Integer idCat = sCat.isEmpty() ? null : Integer.parseInt(sCat);
                    prodDAO.agregarProducto(nombre, idCat);
                    System.out.println("Agregado. Último ID: " + prodDAO.obtenerUltimoId());
                }
                case "4" -> {
                    System.out.print("ID a actualizar: ");
                    int id = Integer.parseInt(sc.nextLine());
                    prodDAO.actualizarProducto(id); // overload (solo id)
                    System.out.println("Actualizado.");
                }
                case "5" -> {
                    System.out.print("ID a eliminar: ");
                    int id = Integer.parseInt(sc.nextLine());
                    prodDAO.eliminarProducto(id);
                    System.out.println("Eliminado.");
                }
                case "6" -> prodDAO.listarNecesitanReposicion().forEach(System.out::println);
                case "7" -> prodDAO.listarDisponibles().forEach(System.out::println);
                case "0" -> { /* volver */ }
                default -> System.out.println("Opción inválida.");
            }
        } catch (SQLException e) {
            System.err.println("SQL: " + e.getMessage());
        } catch (NumberFormatException nfe) {
            System.err.println("Número inválido.");
        }
    }
}
