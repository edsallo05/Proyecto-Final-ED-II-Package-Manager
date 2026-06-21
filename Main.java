import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        PackageManager pm = new PackageManager();
        int opcion;

        do {
            System.out.println("\n=============================");
            System.out.println("   GESTOR DE PAQUETES");
            System.out.println("=============================");
            System.out.println(" 1. Agregar carpeta");
            System.out.println(" 2. Agregar paquete");
            System.out.println(" 3. Buscar paquete");
            System.out.println(" 4. Ver árbol de paquetes");
            System.out.println(" 5. Agregar dependencia");
            System.out.println(" 6. Eliminar dependencia");
            System.out.println(" 7. Ver dependencias de un paquete");
            System.out.println(" 8. Instalar paquete");
            System.out.println(" 9. Detectar ciclos");
            System.out.println("10. Eliminar paquete/carpeta");
            System.out.println("11. Recorrer árbol");
            System.out.println(" 0. Salir");
            System.out.println("=============================");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    System.out.print("Nombre de la carpeta: ");
                    String nombreCarpeta = scanner.nextLine();
                    System.out.print("Carpeta Padre: ");
                    String padreCarpeta = scanner.nextLine();
                    pm.agregarCarpeta(nombreCarpeta, padreCarpeta);
                    break;

                case 2:
                    System.out.print("Nombre del paquete: ");
                    String nombre = scanner.nextLine();
                    System.out.print("Version: ");
                    String version = scanner.nextLine();
                    System.out.print("Descripcion: ");
                    String descripcion = scanner.nextLine();
                    System.out.print("Carpeta a guardar: ");
                    String padre = scanner.nextLine();
                    pm.agregarPaquete(nombre, version, descripcion, padre);
                    break;

                case 3:
                    System.out.print("Nombre del paquete: ");
                    String buscar = scanner.nextLine();
                    pm.buscarPaquete(buscar);
                    break;

                case 4:
                    pm.mostrarArbol();
                    break;

                case 5:
                    System.out.print("Paquete origen: ");
                    String origen = scanner.nextLine();
                    System.out.print("Paquete destino (depende de): ");
                    String destino = scanner.nextLine();
                    pm.agregarDependencia(origen, destino);
                    break;

                case 6:
                    System.out.print("Paquete origen: ");
                    String origenElim = scanner.nextLine();
                    System.out.print("Paquete destino: ");
                    String destinoElim = scanner.nextLine();
                    pm.eliminarDependencia(origenElim, destinoElim);
                    break;

                case 7:
                    System.out.print("Nombre del paquete: ");
                    String dep = scanner.nextLine();
                    pm.verDependencias(dep);
                    break;

                case 8:
                    System.out.print("Nombre del paquete a instalar: ");
                    String instalar = scanner.nextLine();
                    pm.instalarPaquete(instalar);
                    break;

                case 9:
                    pm.detectarCiclos();
                    break;

                case 10:
                    System.out.print("Nombre del paquete o carpeta a eliminar: ");
                    String eliminar = scanner.nextLine();
                    pm.eliminarPaquete(eliminar);
                    break;
                case 11:
                    pm.mostrarPreorden();
                    break;
                case 0:
                    System.out.println("Saliendo del gestor...");
                    break;

                default:
                    System.out.println("Opción no válida. Intente de nuevo.");
            }

        } while (opcion != 0);

        scanner.close();
    }
}