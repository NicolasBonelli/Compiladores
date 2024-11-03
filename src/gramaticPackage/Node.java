package gramaticPackage;

import java.util.ArrayList;
import java.util.List;


import java.util.Stack;

class Node {
    boolean hasReturn;
    List<Node> children;

    public Node() {
        this.hasReturn = false;
        this.children = new ArrayList<>();
    }

    // Método para agregar un hijo
    public void addChild(Node child) {
        this.children.add(child);
    }

    // Verificar recursivamente si todas las hojas cumplen con la regla de return
    public boolean validateReturns() {
        // Si tiene un return, no es necesario revisar sus hijos
        if (hasReturn) {
            return true;
        }
        // Revisar todos los hijos
        for (Node child : children) {
            if (!child.validateReturns()) {
                return false;
            }
        }
        return children.isEmpty(); // Retorna false si es una hoja sin return
    }
}

class ReturnChecker {
    private Stack<Node> stack = new Stack<>();
    private boolean insideFunction = false;

    // Método para entrar en una función
    public void enterFunction() {
        insideFunction = true;
        stack.push(new Node());
    }

    // Método para salir de una función
    public void exitFunction() {
        insideFunction = false;
        Node functionNode = stack.pop();
        if (!functionNode.validateReturns()) {
            System.out.println("Error: falta un return en alguna rama de la función.");
        }
    }

    // Método para entrar en un bloque (if/else)
    public void enterBlock() {
        Node parentNode = stack.peek();
        Node newBlock = new Node();
        parentNode.addChild(newBlock);
        stack.push(newBlock);
    }

    // Método para salir de un bloque
    public void exitBlock() {
        stack.pop();
    }

    // Método para registrar un return
    public void registerReturn() {
        if (insideFunction && !stack.isEmpty()) {
            stack.peek().hasReturn = true;
        }
    }
}
