import java.util.*;

public class DependencyGraph {

    private Map<PackageNode, List<PackageNode>> lista_adyacencia;

    public DependencyGraph() {
        lista_adyacencia = new HashMap<>();
    }

    // Registra un paquete en el grafo sin dependencias
    public void agregarNodo(PackageNode paquete) {
        lista_adyacencia.putIfAbsent(paquete, new LinkedList<>());
    }

    // Agrega una dependencia dirigida: origen depende de destino
    public void agregarDependencia(PackageNode origen, PackageNode destino) {
        lista_adyacencia.putIfAbsent(origen, new LinkedList<>());
        lista_adyacencia.putIfAbsent(destino, new LinkedList<>());
        lista_adyacencia.get(origen).add(destino);
    }

    // Elimina la dependencia entre origen y destino
    public void eliminarDependencia(PackageNode origen, PackageNode destino) {
        if (lista_adyacencia.containsKey(origen)) {
            lista_adyacencia.get(origen).remove(destino);
        }
    }

    // Devuelve la lista de dependencias directas de un paquete
    public List<PackageNode> getDependencias(PackageNode paquete) {
        return lista_adyacencia.getOrDefault(paquete, new LinkedList<>());
    }

    // Verifica si algún otro paquete depende del paquete dado
    public boolean tieneDependientes(PackageNode paquete) {
        for (PackageNode nodo : lista_adyacencia.keySet()) {
            if (lista_adyacencia.get(nodo).contains(paquete)) {
                return true;
            }
        }
        return false;
    }

    // Elimina un nodo del grafo y lo quita de las listas de dependencias de los demás
    public void eliminarNodo(PackageNode paquete) {
        lista_adyacencia.remove(paquete);
        for (PackageNode nodo : lista_adyacencia.keySet()) {
            lista_adyacencia.get(nodo).remove(paquete);
        }
    }

    // Recorrido BFS desde un nodo inicial, muestra los nodos nivel por nivel
    public void BFS(PackageNode inicio) {
        Set<PackageNode> visitados = new HashSet<>();
        Queue<PackageNode> cola = new LinkedList<>();

        cola.add(inicio);
        visitados.add(inicio);

        System.out.println("BFS desde " + inicio.getNombre() + ":");
        while (!cola.isEmpty()) {
            PackageNode nodo = cola.poll();
            System.out.println("  " + nodo.getNombre());

            for (PackageNode vecino : lista_adyacencia.getOrDefault(nodo, new LinkedList<>())) {
                if (!visitados.contains(vecino)) {
                    visitados.add(vecino);
                    cola.add(vecino);
                }
            }
        }
    }

    // Recorrido DFS iterativo desde un nodo inicial
    public void DFS(PackageNode inicio) {
        Set<PackageNode> visitados = new HashSet<>();
        Stack<PackageNode> pila = new Stack<>();

        pila.push(inicio);

        System.out.println("DFS desde " + inicio.getNombre() + ":");
        while (!pila.isEmpty()) {
            PackageNode nodo = pila.pop();
            if (!visitados.contains(nodo)) {
                visitados.add(nodo);
                System.out.println("  " + nodo.getNombre());

                for (PackageNode vecino : lista_adyacencia.getOrDefault(nodo, new LinkedList<>())) {
                    pila.push(vecino);
                }
            }
        }
    }

    // DFS recursivo auxiliar usado por topologicalSort
    private void dfsRecursivo(PackageNode nodo, Set<PackageNode> visitados, Stack<PackageNode> pila) {
        visitados.add(nodo);
        for (PackageNode dependencia : lista_adyacencia.getOrDefault(nodo, new LinkedList<>())) {
            if (!visitados.contains(dependencia)) {
                dfsRecursivo(dependencia, visitados, pila);
            }
        }
        pila.push(nodo);
    }

    // Detecta ciclos en el grafo usando DFS, devuelve la lista de nodos del ciclo o null si no hay
    public List<PackageNode> detectarCiclos() {
        Set<PackageNode> visitados = new HashSet<>();
        Set<PackageNode> enPila = new HashSet<>();
        List<PackageNode> camino = new ArrayList<>();

        for (PackageNode nodo : lista_adyacencia.keySet()) {
            if (!visitados.contains(nodo)) {
                List<PackageNode> ciclo = detectarCiclosRecursivo(nodo, visitados, enPila, camino);
                if (ciclo != null) {
                    return ciclo;
                }
            }
        }
        return null;
    }

    private List<PackageNode> detectarCiclosRecursivo(PackageNode nodo, Set<PackageNode> visitados, Set<PackageNode> enPila, List<PackageNode> camino) {

        visitados.add(nodo);
        enPila.add(nodo);
        camino.add(nodo);

        for (PackageNode dependencia : lista_adyacencia.getOrDefault(nodo, new LinkedList<>())) {
            if (!visitados.contains(dependencia)) {
                List<PackageNode> ciclo = detectarCiclosRecursivo(dependencia, visitados, enPila, camino);
                if (ciclo != null) return ciclo;
            } else if (enPila.contains(dependencia)) {
                // Se encontró un ciclo, extraemos solo la parte del ciclo
                List<PackageNode> ciclo = new ArrayList<>();
                int inicio = camino.indexOf(dependencia);
                ciclo.addAll(camino.subList(inicio, camino.size()));
                return ciclo;
            }
        }

        enPila.remove(nodo);
        camino.remove(nodo);
        return null;
    }

    // Devuelve el orden correcto de instalación de un paquete y sus dependencias
    public List<PackageNode> topologicalSort(PackageNode inicio) {
        Set<PackageNode> visitados = new HashSet<>();
        Stack<PackageNode> pila = new Stack<>();

        dfsRecursivo(inicio, visitados, pila);

        List<PackageNode> orden = new ArrayList<>();
        while (!pila.isEmpty()) {
            orden.add(pila.pop());
        }
        return orden;
    }
}
