import java.util.ArrayList;
import java.util.List;

public class PackageTree {

    private PackageNode raiz;

    // Inicializa el árbol con la raíz y las carpetas base
    public PackageTree() {
        raiz = new PackageNode("Repositorio");
        raiz.getHijos().add(new PackageNode("Frontend"));
        raiz.getHijos().add(new PackageNode("Backend"));
        raiz.getHijos().add(new PackageNode("AI"));
        raiz.getHijos().add(new PackageNode("Utilidades"));
    }

    public PackageNode getRaiz() {
        return raiz;
    }

    // Inserta un nuevo nodo como hijo del nodo padre indicado
    public boolean insertar(String nombrePadre, PackageNode nuevo) {
        PackageNode padre = buscar(nombrePadre);
        if (padre == null) {
            System.out.println("Padre no encontrado: " + nombrePadre);
            return false;
        }
        padre.getHijos().add(nuevo);
        return true;
    }

    // Busca un nodo por nombre, devuelve null si no existe
    public PackageNode buscar(String nombre) {
        return buscarRecursivo(raiz, nombre);
    }

    private PackageNode buscarRecursivo(PackageNode actual, String nombre) {
        if (actual.getNombre().equalsIgnoreCase(nombre)) {
            return actual;
        }
        for (PackageNode hijo : actual.getHijos()) {
            PackageNode resultado = buscarRecursivo(hijo, nombre);
            if (resultado != null) {
                return resultado;
            }
        }
        return null;
    }

    // Elimina un nodo del árbol buscando quién lo tiene como hijo
    public boolean eliminar(String nombre) {
        return eliminarRecursivo(raiz, nombre);
    }

    private boolean eliminarRecursivo(PackageNode actual, String nombre) {
        for (int i = 0; i < actual.getHijos().size(); i++) {
            PackageNode hijo = actual.getHijos().get(i);
            if (hijo.getNombre().equalsIgnoreCase(nombre)) {
                actual.getHijos().remove(i);
                return true;
            }
            if (eliminarRecursivo(hijo, nombre)) {
                return true;
            }
        }
        return false;
    }

    // Muestra el árbol visualmente en consola con formato de árbol
    public void mostrarArbol() {
        mostrarNodo(raiz, "", true);
    }

    private void mostrarNodo(PackageNode nodo, String prefijo, boolean esUltimo) {
        String conector = esUltimo ? "└── " : "├── ";
        String icono = nodo.isEsCarpeta() ? "[DIR] " : "[PKG] ";
        String info = nodo.isEsCarpeta()
                ? nodo.getNombre()
                : nodo.getNombre() + " v" + nodo.getVersion()
                + (nodo.isInstalado() ? " [instalado]" : "");

        System.out.println(prefijo + conector + icono + info);

        String nuevoPrefijo = prefijo + (esUltimo ? "    " : "│   ");
        for (int i = 0; i < nodo.getHijos().size(); i++) {
            boolean ultimo = (i == nodo.getHijos().size() - 1);
            mostrarNodo(nodo.getHijos().get(i), nuevoPrefijo, ultimo);
        }
    }

    // Recorrido preorden del árbol (raíz, luego hijos de izquierda a derecha)
    public void recorridoPreorden() {
        System.out.println("Recorrido Preorden:");
        preordenRecursivo(raiz);
    }

    private void preordenRecursivo(PackageNode nodo) {
        System.out.println("  " + nodo.getNombre());
        for (PackageNode hijo : nodo.getHijos()) {
            preordenRecursivo(hijo);
        }
    }
}

