import java.util.ArrayList;
import java.util.List;

public class PackageNode {

    private String nombre;
    private String version;
    private String descripcion;
    private boolean instalado;
    private boolean esCarpeta;
    private List<PackageNode> hijos;

    // Constructor para paquetes reales
    public PackageNode(String nombre, String version, String descripcion) {
        this.nombre = nombre;
        this.version = version;
        this.descripcion = descripcion;
        this.instalado = false;
        this.esCarpeta = false;
        this.hijos = new ArrayList<>();
    }

    // Constructor para carpetas organizadoras
    public PackageNode(String nombre) {
        this.nombre = nombre;
        this.version = "";
        this.descripcion = "";
        this.instalado = false;
        this.esCarpeta = true;
        this.hijos = new ArrayList<>();
    }

    // Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isInstalado() {
        return instalado;
    }

    public void setInstalado(boolean instalado) {
        this.instalado = instalado;
    }

    public boolean isEsCarpeta() {
        return esCarpeta;
    }

    public void setEsCarpeta(boolean esCarpeta) {
        this.esCarpeta = esCarpeta;
    }

    public List<PackageNode> getHijos() {
        return hijos;
    }
}