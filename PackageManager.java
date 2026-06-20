import java.util.List;

public class PackageManager {

    private PackageTree arbol;
    private DependencyGraph grafo;

    public PackageManager() {
        arbol = new PackageTree();
        grafo = new DependencyGraph();
    }

    // Agrega una carpeta organizadora al árbol bajo el padre indicado
    public void agregarCarpeta(String nombre, String nombrePadre) {
        if (arbol.buscar(nombre) != null) {
            System.out.println("Ya existe un nodo con ese nombre: " + nombre);
            return;
        }
        PackageNode carpeta = new PackageNode(nombre);
        if (arbol.insertar(nombrePadre, carpeta)) {
            System.out.println("Carpeta '" + nombre + "' agregada bajo '" + nombrePadre + "'");
        }
    }

    // Agrega un paquete real al árbol y lo registra en el grafo
    public void agregarPaquete(String nombre, String version, String descripcion, String nombrePadre) {
        if (arbol.buscar(nombre) != null) {
            System.out.println("Ya existe un nodo con ese nombre: " + nombre);
            return;
        }
        PackageNode nuevo = new PackageNode(nombre, version, descripcion);
        if (arbol.insertar(nombrePadre, nuevo)) {
            grafo.agregarNodo(nuevo);
            System.out.println("Paquete '" + nombre + "' agregado bajo '" + nombrePadre + "'");
        }
    }

    // Busca un paquete por nombre y muestra su información
    public void buscarPaquete(String nombre) {
        PackageNode nodo = arbol.buscar(nombre);
        if (nodo == null) {
            System.out.println("Paquete no encontrado: " + nombre);
            return;
        }
        System.out.println("=== Paquete encontrado ===");
        System.out.println("Nombre:      " + nodo.getNombre());
        if (!nodo.isEsCarpeta()) {
            System.out.println("Version:     " + nodo.getVersion());
            System.out.println("Descripcion: " + nodo.getDescripcion());
            System.out.println("Instalado:   " + (nodo.isInstalado() ? "Sí" : "No"));
        } else {
            System.out.println("Tipo: Carpeta organizadora");
        }
    }

    // Muestra el árbol completo de paquetes en consola
    public void mostrarArbol() {
        arbol.mostrarArbol();
    }

    // Muestra el recorrido preorden del árbol
    public void mostrarPreorden() {
        arbol.recorridoPreorden();
    }

    // Agrega una dependencia dirigida entre dos paquetes en el grafo
    public void agregarDependencia(String nombreOrigen, String nombreDestino) {
        PackageNode origen = arbol.buscar(nombreOrigen);
        PackageNode destino = arbol.buscar(nombreDestino);

        if (origen == null || destino == null) {
            System.out.println("Uno o ambos paquetes no encontrados");
            return;
        }
        if (origen.isEsCarpeta() || destino.isEsCarpeta()) {
            System.out.println("No se pueden agregar dependencias a carpetas");
            return;
        }

        grafo.agregarDependencia(origen, destino);
        System.out.println("Dependencia agregada: " + nombreOrigen + " → " + nombreDestino);
    }

    // Elimina una dependencia entre dos paquetes
    public void eliminarDependencia(String nombreOrigen, String nombreDestino) {
        PackageNode origen = arbol.buscar(nombreOrigen);
        PackageNode destino = arbol.buscar(nombreDestino);

        if (origen == null || destino == null) {
            System.out.println("Uno o ambos paquetes no encontrados");
            return;
        }

        grafo.eliminarDependencia(origen, destino);
        System.out.println("Dependencia eliminada: " + nombreOrigen + " → " + nombreDestino);
    }

    // Muestra las dependencias directas de un paquete
    public void verDependencias(String nombre) {
        PackageNode nodo = arbol.buscar(nombre);
        if (nodo == null) {
            System.out.println("Paquete no encontrado: " + nombre);
            return;
        }

        List<PackageNode> dependencias = grafo.getDependencias(nodo);
        if (dependencias.isEmpty()) {
            System.out.println("El paquete '" + nombre + "' no tiene dependencias");
            return;
        }

        System.out.println("Dependencias de " + nombre + ":");
        for (PackageNode dep : dependencias) {
            System.out.println("  → " + dep.getNombre() + " v" + dep.getVersion());
        }
    }

    // Instala un paquete resolviendo su orden de dependencias con Topological Sort
    public void instalarPaquete(String nombre) {
        PackageNode nodo = arbol.buscar(nombre);
        if (nodo == null) {
            System.out.println("Paquete no encontrado: " + nombre);
            return;
        }
        if (nodo.isEsCarpeta()) {
            System.out.println("No se puede instalar una carpeta");
            return;
        }

        // Verificar que no haya ciclos antes de instalar
        List<PackageNode> ciclo = grafo.detectarCiclos();
        if (ciclo != null) {
            System.out.println("Error: hay ciclos en las dependencias, no se puede instalar.");
            System.out.println("Use la opción de detectar ciclos para ver cuál es y elimínelo.");
            return;
        }

        List<PackageNode> orden = grafo.topologicalSort(nodo);
        System.out.println("Resolviendo dependencias...");
        System.out.println("Orden de instalación:");
        for (int i = 0; i < orden.size(); i++) {
            PackageNode p = orden.get(i);
            p.setInstalado(true);
            System.out.println("  " + (i + 1) + ". Instalando: " + p.getNombre()
                    + (p.getVersion().isEmpty() ? "" : " v" + p.getVersion()));
        }
        System.out.println("Instalación completada exitosamente.");
    }

    // Detecta ciclos en el grafo y muestra cuál es si existe
    public void detectarCiclos() {
        List<PackageNode> ciclo = grafo.detectarCiclos();
        if (ciclo == null) {
            System.out.println("No se detectaron dependencias circulares.");
            return;
        }

        System.out.print("⚠️ Ciclo detectado: ");
        for (int i = 0; i < ciclo.size(); i++) {
            System.out.print(ciclo.get(i).getNombre());
            if (i < ciclo.size() - 1) System.out.print(" → ");
        }
        System.out.println(" → " + ciclo.get(0).getNombre());
    }

    // Elimina un paquete del árbol y del grafo si no tiene dependientes
    public void eliminarPaquete(String nombre) {
        PackageNode nodo = arbol.buscar(nombre);
        if (nodo == null) {
            System.out.println("Paquete no encontrado: " + nombre);
            return;
        }

        if (!nodo.isEsCarpeta() && grafo.tieneDependientes(nodo)) {
            System.out.println("No se puede eliminar: otros paquetes dependen de '" + nombre + "'");
            return;
        }

        arbol.eliminar(nombre);
        if (!nodo.isEsCarpeta()) {
            grafo.eliminarNodo(nodo);
        }
        System.out.println("'" + nombre + "' eliminado correctamente.");
    }
}