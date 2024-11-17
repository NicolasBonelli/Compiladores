package gramaticPackage;

public class Arbol {
    String value;                // Valor del nodo
    Arbol left;       // Hijo izquierdo
    Arbol right;      // Hijo derecho

    Arbol(String value, Arbol left, Arbol right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    Arbol(String value) {
        this.value = value;
        this.left = null;
        this.right = null;
    }

    public String getValue() {
        return value;
    }

    public void setLeft(Arbol left) {
        this.left = left;
    }

    public void setRight(Arbol right) {
        this.right = right;
    }


    @Override
    public String toString() {
        return toStringArbol(this, 0);
    }

    private String toStringArbol(Arbol node, int depth) {
        if (node == null) {
            return "";
        }

        StringBuilder sb = new StringBuilder();

        // Añadir sangría según la profundidad actual
        sb.append("  ".repeat(depth));  // Cada nivel de profundidad añade dos espacios
        sb.append(node.value).append("\n");

        // Llamada recursiva para el hijo izquierdo y derecho, incrementando la profundidad
        if (node.left != null || node.right != null) {
            sb.append(toStringArbol(node.left, depth + 1));
            sb.append(toStringArbol(node.right, depth + 1));
        }

        return sb.toString();
    }
}
