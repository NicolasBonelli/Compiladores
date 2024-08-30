package Paquetecompi;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
	private HashMap<String, Integer> reservedWords; //reservadas
	private SymbolTable tabla;
	static int nmrLinea;
	
	public Lexer(SymbolTable tabla) {
		reservedWords = new HashMap<>();
		initializeReservedSymbols();
	    initializeReservedWords();
	    this.nmrLinea = 0;
	    this.tabla=tabla;
	}
	private int[][] transitionMatrix = {
		    {0, 0, 0, 1, -1, 2, 3, 3, 16, -1, 17, 17, 17, 17, 17, 17, 17, 17, 10, 18, 16, 16, 16, 11, 13, -1, 1, -1},
		    {-1, -1, -1, 1, 1, 1, 1, 1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1},
		    {-1, -1, -1, -1, -1, 4, 4, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, 3, 3, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, 4, 4, 3, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, 6, 6, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 6, -1},
		    {-1, -1, -1, -1, -1, 6, 6, 6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 7, -1},
		    {-1, -1, -1, -1, -1, 9, 9, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, 9, 9, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, 9, 9, 9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1},
		    {12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
		    {14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14},
		    {14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14, 14},
		    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1}
		};

	private SemanticAction[][] actionMatrix = {
		    // Estado 0
		    {new ASI(), new ASI(), new ASI(), new AS1(), new ASE(), new AS1(), new AS1(), new AS1(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new ASE(), new AS1(), new ASE()},
		    
		    // Estado 1
		    {new AS3(), new AS3(), new AS3(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS2(), new AS3()},
		    
		    // Estado 2
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 3
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 4
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 5
		    {new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new AS2(), new AS2(), new AS2(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE()},
		    
		    // Estado 6
		    {new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS2(), new AS2(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS5()},
		    
		    // Estado 7
		    {new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE()},
		    
		    // Estado 8
		    {new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new AS2(), new AS2(), new AS2(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE()},
		    
		    // Estado 9
		    {new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS2(), new AS2(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS5()},
		    
		    // Estado 10
		    {new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new AS7(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE()},
		    
		    // Estado 11
		    {new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new AS2(), new ASE(), new ASE(), new ASE(), new ASE()},
		    
		    // Estado 12
		    {new AS2(), new AS2(), new ASI(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2()},
		    
		    // Estado 13
		    {new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS7(), new AS2(), new AS2()},
		    
		    // Estado 14
		    {new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new ASE(), new AS7(), new ASE(), new ASE()},
		    
		    // Estado 15
		    {new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8()},
		    
		    // Estado 16
		    {new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new ASE(), new ASE(), new ASE(), new ASE(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8()}
		};
	private int currentState = 0;

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
	
    
	private void initializeReservedSymbols() {
		this.tabla.addValue("bl",1);
		this.tabla.addValue("tab",2);
		this.tabla.addValue("nl",3);
		this.tabla.addValue("letra-[d]",4);
		this.tabla.addValue("\" \"",5);
		this.tabla.addValue("0",6);
		this.tabla.addValue("1 al 7",7);
		this.tabla.addValue("8 al 9", 8);
		this.tabla.addValue("+", 9);
		this.tabla.addValue("-", 10);
		this.tabla.addValue("/", 11);
		this.tabla.addValue("*", 12);
		this.tabla.addValue("(", 13);
		this.tabla.addValue(")", 14);
		this.tabla.addValue("=", 15);
		this.tabla.addValue(">", 16);
		this.tabla.addValue(",", 17);
		this.tabla.addValue(";", 18);
		this.tabla.addValue("==", 19);
		this.tabla.addValue("<", 20);
		this.tabla.addValue(">", 21);
		this.tabla.addValue("#", 22);
		this.tabla.addValue("[", 23);
		this.tabla.addValue("]", 24);
		this.tabla.addValue("{", 25);
		this.tabla.addValue("d", 26);
	}
    public boolean isReservedWord(String word) {
        return reservedWords.containsKey(word);
    }
    public int getReservedWordToken(String word) {
        return reservedWords.getOrDefault(word, -1); // Retorna un token para identificadores si no es reservada
    }
    
    
    
    public int getSymbol(String name) {
        return symbolMap.get(name);
    }
 
    public boolean containsSymbol(String name) {
        return symbolMap.containsKey(name);
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
        lexer.analyze("abc");
    }
}