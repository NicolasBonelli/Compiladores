package Paquetecompi;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
	private HashMap<String, Integer> reservedWords; //reservadas
	private SymbolTable tabla;
	static int nmrLinea;
	private int currentState = 0;
	private ArrayList<Integer> tokenList;
	private StringBuilder lexema;
	private final static int MAX_ID_LENGTH=15;
	public Lexer(SymbolTable tabla) {
		reservedWords = new HashMap<>();
		initializeReservedSymbols();
	    initializeReservedWords();
	    this.nmrLinea = 0;
	    this.tabla=tabla;
	    this.tokenList=new ArrayList<Integer>();
	    this.lexema=new StringBuilder();
	}
	private int[][] transitionMatrix = {
		    { 0,  0,  0,  1, -1,  2,   3,   3, 16, 16, 16, 16, 16, 16, 16, 16, 16, 10, 16, 15, 15, 15, 11, 13, -1,  1, -1},
		    {16, 16, 16,  1,  1,  1,   1,   1, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,  1, -1},
		    {16, 16, 16, 16, 16,  4,   4,   3, 16, 16, 16, 16, 16, 16,  5, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1},
		    {16, 16, 16, 16, 16,  3,   3,   3, 16, 16, 16, 16, 16, 16,  5, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1},
		    {16, 16, 16, 16, 16,  4,   4,   3, 16, 16, 16, 16, 16, 16,  5, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1},
		    {-1, -1, -1, -1, -1,  6,   6,   6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {16, 16, 16, 16, 16,  6,   6,   6, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,  7, -1},
		    {-1, -1, -1, -1, -1,  9,   9,   9,  8,  8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1,  9,   9,   9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {16, 16, 16, 16, 16,  9,   9,   9, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1},
		    {-1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 13, -1, -1, -1, -1},
		    {12, 12, 0 , 12, 12, 12,  12,  12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, -1},
		    {13, 13, 13, 13, 13, 13,  13,  13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 16, 13, -1},
		    {16, 16, 16, 16, 16, 16,  16,  16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1},
		    {16, 16, 16, 16, 16, 16,  16,  16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, -1, -1, 16, 16, 16, 16, -1}
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
		    {new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8()},
		    
		    // Estado 15
		    {new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new ASE(), new ASE(), new ASE(), new ASE(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8()}
		};

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
		this.tabla.addValue("bl",1); //espacio alt 32
		this.tabla.addValue("tab",2); //tab facil alt 09
		this.tabla.addValue("nl",3);	// /n ??
		this.tabla.addValue("letra-[d]",4);
		this.tabla.addValue("_",5);
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
		this.tabla.addValue("=", 19);
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
    public void insertReservedWord(String word) {
        int maxKey = -1;
        for (Integer key : reservedWords.values()) {
            if (key > maxKey) {
                maxKey = key;
            }
        }
        int newKey = maxKey + 1;
        reservedWords.put(word, newKey);
    }
    public void addToken(Integer token) {
    	this.tokenList.add(token);
    }
    public void setLexeme(String set) {
    	this.lexema=new StringBuilder(set);
    }
    public int getNroLinea() {
    	return this.nmrLinea;
    }
    public int getSymbol(String name) {
        return tabla.getValue(name);
    }
 
    public boolean containsSymbol(String name) {
        return tabla.hasKey(name);
    }
    
    

    // Otras acciones semánticas implementadas de manera similar...
    
    // Función principal de análisis léxico
    public void analyze(String input) {
        char[] chars = input.toCharArray();
        for (char c : chars) {
            int actionIndex = getTSIndex(c);
            if (actionIndex != -1) {
                actionMatrix[currentState][actionIndex].execute(this,this.lexema,c);
                currentState = getNextState(currentState, c);
            } else {
                new ASE().execute(this,this.lexema,c); //hacer algo para diferenciar los errores 
                break;
            }
            
        }
    }
    
    private int getNextState(int currentState, char input) {
        return transitionMatrix[currentState][this.getTSIndex(input)]; 
    }

    private int getTSIndex(char input) {
        if (Character.toString(input).matches("[a-ce-zA-Z]")) { 
            return this.tabla.getValue("letra-[d]") - 1;
        } else if (input == 'd') {
            return this.tabla.getValue("d") - 1; 
        } else if (Character.toString(input).matches("[8-9]")) {
            return this.tabla.getValue("8 al 9") - 1;
        } else if (Character.toString(input).matches("[1-7]")) {
            return this.tabla.getValue("1 al 7") - 1;
        } else if (input == ' ') {
            return this.tabla.getValue("bl") - 1; // Manejo de espacio
        } else if (input == '\n') {
            return this.tabla.getValue("nl") - 1; // Manejo de salto de línea
        } else if (input == '\t') {
            return this.tabla.getValue("tab") - 1; // Manejo de tabulación
        } else {
        	if (tabla.hasKey(Character.toString(input))) {
        		return this.tabla.getValue(Character.toString(input)) - 1;
        	}else {
        		return -1;
        	}
        }
    }


    public static void main(String[] args) {
    	SymbolTable st = new SymbolTable();
        Lexer lexer = new Lexer(st);
        lexer.analyze("abc");
    }
}