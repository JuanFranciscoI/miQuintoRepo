package Resol.IngaramoJ;

import java.util.Scanner;

public class PedidoView {
    private final Scanner sc = new Scanner(System.in);

    public int menu() {
        System.out.println("\n=== Gestión Pedidos ===");
        System.out.println("1) Alta (pedido + detalle)");
        System.out.println("2) Modificación");
        System.out.println("3) Baja");
        System.out.println("4) Listar todos");
        System.out.println("5) Listar por cliente");
        System.out.println("6) Cambiar estado");
        System.out.println("0) Volver");
        System.out.print("Opción: ");
        return leerInt();
    }

    public int pedirIdPedido(){ System.out.print("ID Pedido: "); return leerInt(); }
    public int pedirIdCliente(){ System.out.print("ID Cliente: "); return leerInt(); }
    public String pedirEstado(){ System.out.print("Nuevo estado (PENDIENTE/ENVIADO/ENTREGADO/PAGADO): "); return sc.nextLine().trim(); }

    public void ok(String m){ System.out.println("✔ "+m); }
    public void error(String m){ System.out.println("✘ "+m); }

    private int leerInt() {
        while (true) {
            String s=sc.nextLine();
            try { return Integer.parseInt(s.trim()); } catch(Exception e){ System.out.print("Número inválido. Reintente: "); }
        }
    }
}
