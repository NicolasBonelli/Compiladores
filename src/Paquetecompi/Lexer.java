package Paquetecompi;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
	private HashMap<String, Integer> reservedWords; //reservadas
	private HashMap<String,Integer> symbolMap;
	private int nmrLinea;
	
	public Lexer() {
		reservedWords = new HashMap<>();
		initializeReservedSymbols();
	    initializeReservedWords();
	    this.nmrLinea = 0;	}
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
		symbolMap = new HashMap<>();
        symbolMap.put("bl", 1);
        symbolMap.put("tab", 2);
        symbolMap.put("nl", 3);
        symbolMap.put("letra-[d]", 4);
        symbolMap.put("\" \"", 5);
        symbolMap.put("0", 6);
        symbolMap.put("1 al 7", 7);
        symbolMap.put("8 al 9", 8);
        symbolMap.put("+", 9);
        symbolMap.put("-", 10);
        symbolMap.put("/", 11);
        symbolMap.put("*", 12);
        symbolMap.put("(", 13);
        symbolMap.put(")", 14);
        symbolMap.put("=", 15);
        symbolMap.put(">", 16);
        symbolMap.put(",", 17);
        symbolMap.put(";", 18);
        symbolMap.put("==", 19);
        symbolMap.put("<", 20);
        symbolMap.put(">", 21);
        symbolMap.put("#", 22);
        symbolMap.put("[", 23);
        symbolMap.put("]", 24);
        symbolMap.put("{", 25);
        symbolMap.put("d", 26);
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