package Paquetecompi;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
	private HashMap<String, Integer> reservedWords; //reservadas
	private HashMap<String,Integer> symbolTable;
	private int[][] transitionMatrix = {
	        // Define la matriz de estados según tu informe
	        // Ejemplo:
	        // {estado_actual, entrada, nuevo_estado}
	        {0, 'a', 1}, // Desde estado 0, si recibe 'a', pasa al estado 1
	        {1, 'b', 2}, // Desde estado 1, si recibe 'b', pasa al estado 2
	        // ...
	    };
	private SemanticAction[][] actionMatrix = {
	        // Define las acciones semánticas para cada transición
	        // Ejemplo:
	        {new ASE(), new ASI(), new AS1()},
	        // ...
	    };
	private int currentState = 0;
	public Lexer() {
		reservedWords = new HashMap<>();
		symbolTable = new HashMap<>();
	    initializeReservedWords();
	}
	private void initializeReservedWords() {
        reservedWords.put("IF",1);
        reservedWords.put("THEN", 2);
        reservedWords.put("ELSE", 3);
        reservedWords.put("BEGIN", 4);
        reservedWords.put("END", 5);
        reservedWords.put("END_IF", 6);
        reservedWords.put("OUTF", 7);
        reservedWords.put("TYPEDEF", 8);
        reservedWords.put("FUN", 9);
        reservedWords.put("RET", 10);
        reservedWords.put("REPEAT", 11);
        reservedWords.put("WHILE", 12);
        // Agrega más palabras reservadas según sea necesario
    }
    // Matriz de transición de estados
    
    public boolean isReservedWord(String word) {
        return reservedWords.containsKey(word);
    }
    public int getReservedWordToken(String word) {
        return reservedWords.getOrDefault(word, -1); // Retorna un token para identificadores si no es reservada
    }
    
    
    public void addSymbol(String name, int value) {
        if (!symbolTable.containsKey(name)) {
            symbolTable.put(name, value);
        } else {
            // Si ya existe, puedes decidir si actualizar el símbolo o lanzar un error
            System.out.println("Warning: Symbol already exists in the table.");
        }
    }
    public int getSymbol(String name) {
        return symbolTable.get(name);
    }
 
    public boolean containsSymbol(String name) {
        return symbolTable.containsKey(name);
    }

    // Implementación de las acciones semánticas como inner classes
    abstract class SemanticAction {
        abstract void execute();
    }

    class ASE extends SemanticAction {
        @Override
        void execute() {
            System.out.println("Error léxico.");
        }
    }

    class ASI extends SemanticAction {
        @Override
        void execute() {
            System.out.println("Leer siguiente token o eliminar espacio en blanco.");
        }
    }

    class AS1 extends SemanticAction {
        @Override
        void execute() {
            System.out.println("Iniciar la cadena de string y agregar primera letra.");
        }
    }

    // Otras acciones semánticas implementadas de manera similar...

    // Función principal de análisis léxico
    public void analyze(String input) {
        char[] chars = input.toCharArray();
        for (char c : chars) {
            int actionIndex = getActionIndex(currentState, c);
            if (actionIndex != -1) {
                actionMatrix[currentState][actionIndex].execute();
                currentState = getNextState(currentState, c);
            } else {
                new ASE().execute();
                break;
            }
        }
    }

    private int getNextState(int currentState, char input) {
        // Busca en la matriz de transición el próximo estado
        for (int[] transition : transitionMatrix) {
            if (transition[0] == currentState && transition[1] == input) {
                return transition[2];
            }
        }
        return -1; // Estado de error
    }

    private int getActionIndex(int currentState, char input) {
        // Determina qué acción ejecutar según el estado y la entrada
        // Puedes modificar esta lógica según tu implementación
        return 0; // Ejemplo: Siempre retorna la primera acción
    }

    public static void main(String[] args) {
        Lexer lexer = new Lexer();
        lexer.analyze("ab");
    }
}