package Resol.IngaramoJ;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ClienteView {
    private final Scanner sc = new Scanner(System.in);

    public int menu() {
        System.out.println("\n=== Gestión Clientes ===");
        System.out.println("1) Alta");
        System.out.println("2) Modificación");
        System.out.println("3) Baja");
        System.out.println("4) Listar");
        System.out.println("5) Ver por ID");
        System.out.println("0) Volver");
        System.out.print("Opción: ");
        return leerInt();
    }

    public ClienteDTO pedirAlta() {
        ClienteDTO c = new ClienteDTO();
        System.out.print("Nombre completo: "); c.setNombreCompleto(sc.nextLine());
        System.out.print("Empresa: "); c.setNombreEmpresa(sc.nextLine());
        System.out.print("Tipo empresa: "); c.setTipoEmpresa(sc.nextLine());
        System.out.print("Localidad: "); c.setLocalidad(sc.nextLine());
        System.out.print("Fecha alta (YYYY-MM-DD): "); c.setFechaAlta(LocalDate.parse(sc.nextLine().trim()));
        return c;
    }

    public ClienteDTO pedirModif(ClienteDTO o) {
        ClienteDTO c = new ClienteDTO(); c.setIdCliente(o.getIdCliente());
        System.out.print("Nombre ("+o.getNombreCompleto()+"): "); String s=sc.nextLine(); c.setNombreCompleto(s.isBlank()?o.getNombreCompleto():s);
        System.out.print("Empresa ("+o.getNombreEmpresa()+"): "); s=sc.nextLine(); c.setNombreEmpresa(s.isBlank()?o.getNombreEmpresa():s);
        System.out.print("Tipo ("+o.getTipoEmpresa()+"): "); s=sc.nextLine(); c.setTipoEmpresa(s.isBlank()?o.getTipoEmpresa():s);
        System.out.print("Localidad ("+o.getLocalidad()+"): "); s=sc.nextLine(); c.setLocalidad(s.isBlank()?o.getLocalidad():s);
        System.out.print("Fecha alta ("+o.getFechaAlta()+"): "); s=sc.nextLine(); c.setFechaAlta(s.isBlank()?o.getFechaAlta():LocalDate.parse(s.trim()));
        return c;
    }

    public int pedirId() { System.out.print("ID: "); return leerInt(); }

    public void listar(List<ClienteDTO> xs) {
        if (xs.isEmpty()) { System.out.println("(sin clientes)"); return; }
        xs.forEach(c -> System.out.println(c.getIdCliente()+" | "+c.getNombreCompleto()+" | "+c.getNombreEmpresa()+" | "+c.getLocalidad()+" | alta="+c.getFechaAlta()));
    }

    public void mostrar(ClienteDTO c) {
        if (c==null) { System.out.println("No existe."); return; }
        System.out.println("ID:"+c.getIdCliente()+" Nombre:"+c.getNombreCompleto()+" Empresa:"+c.getNombreEmpresa()+" Tipo:"+c.getTipoEmpresa()+" Localidad:"+c.getLocalidad()+" Alta:"+c.getFechaAlta());
    }

    public void ok(String m){ System.out.println("✔ "+m); }
    public void error(String m){ System.out.println("✘ "+m); }

    private int leerInt() {
        while (true) {
            String s=sc.nextLine();
            try { return Integer.parseInt(s.trim()); } catch(Exception e){ System.out.print("Número inválido. Reintente: "); }
        }
    }
}
