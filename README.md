
# 📦 Package Dependency Manager

-----

## 🧩 ¿Qué es esto?

Un sistema que simula la gestión interna de un repositorio de paquetes de software, al estilo de **npm** o **Maven**. El administrador puede registrar paquetes, definir sus dependencias y el sistema resuelve automáticamente en qué orden instalarlos.

-----

## 🏗️ Estructuras de datos

|Estructura      |Clase            |Rol                                                             |
|----------------|-----------------|----------------------------------------------------------------|
|🌳 Árbol General |`PackageTree`    |Catálogo visual del repositorio, organiza paquetes por categoría|
|🕸️ Grafo Dirigido|`DependencyGraph`|Modela dependencias entre paquetes con lista de adyacencia      |

Las dos estructuras se coordinan a través de `PackageManager`. El árbol responde *¿qué paquetes hay?*, el grafo responde *¿en qué orden se instalan?*

-----

## ⚙️ Algoritmos

- 🔁 **DFS recursivo** — base para topological sort y detección de ciclos
- 📋 **Topological Sort** — resuelve el orden correcto de instalación
- 🚨 **Detección de ciclos** — identifica y muestra dependencias circulares

-----

## 📁 Archivos

```
├── PackageNode.java       → nodo del árbol y el grafo
├── PackageTree.java       → árbol general del repositorio
├── DependencyGraph.java   → grafo de dependencias
├── PackageManager.java    → coordinador entre ambas estructuras
└── Main.java              → menú por consola
```

-----

## 🚀 Cómo ejecutar

```bash
# Compilar
javac *.java

# Ejecutar
java Main
```

> Requiere Java 11 o superior

-----

## 🖥️ Menú

```
📦 GESTOR DE PAQUETES
─────────────────────
 1. Agregar carpeta
 2. Agregar paquete
 3. Buscar paquete
 4. Ver árbol de paquetes
 5. Agregar dependencia
 6. Eliminar dependencia
 7. Ver dependencias de un paquete
 8. Instalar paquete
 9. Detectar ciclos
10. Eliminar paquete/carpeta
11. Recorrer arbol
 0. Salir
```

-----

## 📌 Ejemplo

```
# Se define que DRF depende de Django y de lodash
DRF ──→ Django
DRF ──→ lodash

# Al instalar DRF, el sistema resuelve el orden solo:
  1. Instalando: Django v4.2
  2. Instalando: lodash v4.17
  3. Instalando: DRF v3.14
✅ Instalación completada exitosamente.
```

-----

## 👤 Autor

**Eduardo Alejandro Salgueiro López**
