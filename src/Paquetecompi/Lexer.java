package Paquetecompi;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lexer {
	private HashMap<String, Integer> reservedWords; //reservadas
	private  SymbolTable tabla;
	 Map<String, TipoSubrango> tablaTipos = new HashMap<>();
	public static int nmrLinea;
	private int currentState = 0;
	private ArrayList<Pair> tokenList;
	private StringBuilder lexema;
	final static int MAX_ID_LENGTH=15;
	private boolean estado; 
	private boolean finished = false;
	private boolean devolvi;
	private HashMap<String,Integer> tablaSimbolitos;
	public Lexer(SymbolTable tabla) {
		reservedWords = new HashMap<>();
		tablaSimbolitos= new HashMap<>();
	    this.tabla=tabla;
	    estado = false;
	    this.devolvi=false;
		initializeReservedSymbols();
	    initializeReservedWords();
	    Lexer.nmrLinea = 1;
	    this.tokenList=new ArrayList<Pair>();
	    this.lexema=new StringBuilder();
	    tablaTipos= new HashMap<>();
	   
	}
	private int[][] transitionMatrix = {
		    { 0,  0,  0,  1, -1,  2,   3,   3, 17, 17, 17, 17, 17, 17, 17, 17, 17, 10, 15, 14, 14, 14, 11, 13, -1,  1, -1, 17, 17, -1},
		    {17, 17, 17,  1,  1,  1,   1,   1, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,  1, -1, 17, 17, 17},
		    {17, 17, 17, 17, 17,  4,   4,   3, 17, 17, 17, 17, 17, 17,  5, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, -1, 17, 17, 17},
		    {17, 17, 17, 17, 17,  3,   3,   3, 17, 17, 17, 17, 17, 17,  5, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, -1, 17, 17, 17},
		    {17, 17, 17, 17, 17,  4,   4,   3, 17, 17, 17, 17, 17, 17,  5, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, -1, 17, 17, 17},
		    {-1, -1, -1, -1, -1,  6,   6,   6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {17, 17, 17, 17, 17,  6,   6,   6, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17,  7, -1, 17, 17, 17},
		    {-1, -1, -1, -1, -1,  9,   9,   9,  8,  8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1,  9,   9,   9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {17, 17, 17, 17, 17,  9,   9,   9, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, -1, 17, 17, 17},
		    {-1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 17, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 12, -1, -1, -1, -1, -1, -1, -1},
		    {12, 12,  0, 12, 12, 12,  12,  12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12},
		    {13, 13, 13, 13, 13, 13,  13,  13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 17, 13, -1, 13, 13, 13},
		    {17, 17, 17, 17, 17, 17,  17,  17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, -1, 17, 17, 17},
		    {17, 17, 17, 17, 17, 17,  17,  17, 17, 17, 17, 17, 17, 17, 17, 17, 17, 17, -1, -1, -1, -1, 17, 17, 17, 17, -1, 17, 17, 17},
		    { 0,  0,  0, 16, 16, 16,  16,  16, 16, 16, 16, 16, 16, 16, 16, 17, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16}
		};


	
	private SemanticAction[][] actionMatrix = {
		    // Estado 0
		    {new ASI(), new ASI(), new ASI(), new AS1(), new ASE("No puede empezar un identificador con _"), new AS1(), new AS1(), new AS1(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new ASE("Falta un ["), new AS1(), new ASE("Caracter no identificado"), new AS6(), new AS6(), new ASE("No puede existir un @ solo")},
		    
		    // Estado 1
		    {new AS3(), new AS3(), new AS3(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS2(),  new ASE("Caracter no identificado"), new AS3(), new AS3(), new AS7()},
		    
		    // Estado 2
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(),  new ASE("Caracter no identificado"), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 3
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(),  new ASE("Caracter no identificado"), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 4
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new ASE("Caracter no identificado"), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 5
		    {new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new AS2(), new AS2(), new AS2(), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"),  new ASE("Caracter no identificado"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito")},
		    
		    // Estado 6
		    {new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS2(), new AS2(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new ASE("Caracter no identificado"), new AS5(), new AS5(), new AS5()},
		    
		    // Estado 7
		    {new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"),  new ASE("Caracter no identificado"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero")},
		    
		    // Estado 8
		    {new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new AS2(), new AS2(), new AS2(), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"),  new ASE("Caracter no identificado"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, -, DEBE ir un numero")},
		    
		    // Estado 9
		    {new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS2(), new AS2(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(),  new ASE("Caracter no identificado"), new AS5(), new AS5(), new AS5()},
		    
		    // Estado 10
		    {new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new AS7(), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="),  new ASE("Caracter no identificado"), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un =")},
		    
		    // Estado 11
		    {new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new AS2(), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"),  new ASE("Caracter no identificado"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #")},
		    
		    // Estado 12
		    {new AS2(), new AS2(), new ASI(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(),  new AS2(), new AS2(), new AS2(), new AS2()},
		    
		    // Estado 13
		    {new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS7(), new AS2(),  new ASE("Caracter no identificado"), new AS2(), new AS2(), new AS2()},
		    
		    // Estado 14
		    {new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(),  new ASE("Caracter no identificado"), new AS8(), new AS8(), new AS8()},
		    
		    // Estado 15
		    {new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new ASE("No puede ir un = despues de un ="), new ASE("No puede ir un < despues de un ="), new ASE("No puede ir un < despues de un ="), new ASE("No puede ir un ! despues de un ="), new AS8(), new AS8(), new AS8(), new AS8(),  new ASE("Caracter no identificado"), new AS8(), new AS8(), new AS8()},
		
		    //Estado 16 (error)
		    {new ASI(), new ASI(), new ASI(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS6(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(),  new AS2(), new AS2(), new AS2(), new AS2()}
	};
	class TipoSubrango {
        String tipoBase;
        double limiteInferior;
        double limiteSuperior;

        public TipoSubrango(String tipoBase, double limiteInferior, double limiteSuperior) {
            this.tipoBase = tipoBase;
            this.limiteInferior = limiteInferior;
            this.limiteSuperior = limiteSuperior;
        }
    }
	/* Función para verificar si el valor está dentro del rango*/
    boolean verificarRango(String tipo, double valor) {
        if (tablaTipos.containsKey(tipo)) {
            TipoSubrango subrango = tablaTipos.get(tipo);
            return valor >= subrango.limiteInferior && valor <= subrango.limiteSuperior;
        }
        return true; /* Si no es un tipo definido por el usuario, no se verifica el rango*/
    }

    
    boolean verificarRangoLongInt(double valor) {
        return valor >= -Math.pow(2, 31) && valor <= Math.pow(2, 31) - 1;
    }

    boolean verificarRangoDouble(double valor) {
        return valor >= -1.7976931348623157e308 && valor <= 1.7976931348623157e308;
    }

    String obtenerTipo(String variable) {
        
        
        if (tabla.hasKey(variable)) return variable;

        return tabla.getType(variable);  
    }
	private void initializeReservedWords() {
		reservedWords.put("IF", 257);
		reservedWords.put("THEN", 258);
		reservedWords.put("ELSE", 259);
		reservedWords.put("BEGIN", 260);
		reservedWords.put("END", 261);
		reservedWords.put("END_IF", 262);
		reservedWords.put("OUTF", 263);
		reservedWords.put("TYPEDEF", 264);
		reservedWords.put("FUN", 265);
		reservedWords.put("RET", 266);
		reservedWords.put("REPEAT", 267);
		reservedWords.put("WHILE", 268);
		reservedWords.put("PAIR", 269);
		reservedWords.put("GOTO", 270);
		reservedWords.put("LONGINT", 271);
		reservedWords.put("DOUBLE", 272);
		reservedWords.put("<=", 273);
		reservedWords.put(">=", 274);
		reservedWords.put("!=", 275);
		reservedWords.put(":=", 276);
		//reservedWords.put("CADENA", 277);
		//reservedWords.put("ID", 278);
		//reservedWords.put("CTE", 279);
		//reservedWords.put("ETIQUETA",280);

    }
	
    
	private void initializeReservedSymbols() {
		this.tablaSimbolitos.put("bl",1); 
		this.tablaSimbolitos.put("tab",2); 
		this.tablaSimbolitos.put("nl",3);	
		this.tablaSimbolitos.put("letra-[d]",4);
		this.tablaSimbolitos.put("_",5);
		this.tablaSimbolitos.put("0",6);
		this.tablaSimbolitos.put("1 al 7",7);
		this.tablaSimbolitos.put("8 al 9", 8);
		this.tablaSimbolitos.put("+", 9);
		this.tablaSimbolitos.put("-", 10);
		this.tablaSimbolitos.put("/", 11);
		this.tablaSimbolitos.put("*", 12);
		this.tablaSimbolitos.put("(", 13);
		this.tablaSimbolitos.put(")", 14);
		this.tablaSimbolitos.put(".", 15);
		this.tablaSimbolitos.put(";", 16);
		this.tablaSimbolitos.put(",", 17);
		this.tablaSimbolitos.put(":", 18);
		this.tablaSimbolitos.put("=", 19);
		this.tablaSimbolitos.put("<", 20);
		this.tablaSimbolitos.put(">", 21);
		this.tablaSimbolitos.put("!", 22);
		this.tablaSimbolitos.put("#", 23);
		this.tablaSimbolitos.put("[", 24);
		this.tablaSimbolitos.put("]", 25);
		this.tablaSimbolitos.put("d", 26);
		this.tablaSimbolitos.put("{", 28);
		this.tablaSimbolitos.put("}", 29);
		this.tablaSimbolitos.put("@", 30);
	}
    public boolean isReservedWord(String word) {
        return reservedWords.containsKey(word);
    }
    public int getReservedWordToken(String word) {
        return reservedWords.getOrDefault(word, -1); // Retorna un token para identificadores si no es reservada
    }
    public void insertSymbolTable(String word, String type, int token) { //
        
        tabla.addValue(word,type, token);
    }
    public void addToken(Pair pair) {
    	this.tokenList.add(pair);
    }
    public void setLexeme(String set) {
    	this.lexema=new StringBuilder(set);
    }
    public int getNroLinea() {
    	return Lexer.nmrLinea;
    }
    public int getSymbol(String name) {
        return tabla.getValue(name);
    }
 
    public boolean containsSymbol(String name) {
        return tabla.hasKey(name);
    }
    public void setDevolvi(boolean est) {
    	this.devolvi=est;
    }
   
    public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	public Pair analyze(BufferedReader reader) {
	    try {
	        int currentChar;
	      
	        while (!devolvi && (currentChar = reader.read()) != -1) { // Leer hasta EOF y mientras que no haya devuelto un token
	            char c = (char) currentChar;
	         
	            if (c == '\n' || c == '\r') {
	                nmrLinea++;
	               
	            }

	            int actionIndex = getTSIndex(c); // Obtener índice en la tabla de símbolos
	            
	            
                actionMatrix[currentState][actionIndex].execute(this, this.lexema, c); // Ejecutar acción
                currentState = transitionMatrix[currentState][actionIndex];
                if(currentState == -1) { //me transiciono hacia el estado de error
                	currentState=16;
                }

                if (currentState == 17) { // Estado final
                    currentState = 0; // Resetear el estado
                    Pair tokenPair = tokenList.remove(0); // Crear un Pair con el lexema y el índice de acción
                    lexema.setLength(0); // Limpiar el lexema para el siguiente token
                    if (estado) {
    	                reader.reset(); // Volver a la última posición marcada
    	                estado = false; // Reiniciar el estado para el siguiente ciclo
    	            }
                    if(devolvi) {
                    	this.devolvi=false;
                    	return tokenPair;
                    }
                     // Devolver el Pair
                }

                reader.mark(1); // Marcar la posición actual antes de leer el siguiente carácter
	          
	        }

	        // Procesar cualquier carácter pendiente al final del archivo
	        if (currentState != 0 && !finished) {
	        	finished = true;
	            actionMatrix[currentState][0].execute(this, this.lexema, '\0'); // Manejar fin de archivo
	            Pair finalPair = tokenList.remove(0); // Devolver el último token o fin de archivo
	            if(devolvi) {
	            	this.devolvi=false;
	            	return finalPair;
	            }
	            
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Manejo de excepciones de E/S
	    }
	    return null; // Devolver null al final del archivo o si ocurre un error
	}

    
    
    private int getTSIndex(char input) {
    	if (Character.toString(input).matches("[a-ce-zA-CE-Z]")) { 
            return this.tablaSimbolitos.get("letra-[d]") - 1;
        } else if (input == 'd' || input =='D') {
            return this.tablaSimbolitos.get("d") - 1; 
        } else if (Character.toString(input).matches("[8-9]")) {
            return this.tablaSimbolitos.get("8 al 9") - 1;
        } else if (Character.toString(input).matches("[1-7]")) {
            return this.tablaSimbolitos.get("1 al 7") - 1;
        } else if (input == ' ') {
            return this.tablaSimbolitos.get("bl") - 1; // Manejo de espacio
        } else if (input == '\n' || input == '\r') {
            return this.tablaSimbolitos.get("nl") - 1; // Manejo de salto de línea
        } else if (input == '\t') {
            return this.tablaSimbolitos.get("tab") - 1; // Manejo de tabulación
        } else if (input == '@'){
        	return this.tablaSimbolitos.get("@") - 1;
        }
        else {
        	if (tablaSimbolitos.containsKey(Character.toString(input))) {
        
        		return this.tablaSimbolitos.get(Character.toString(input)) - 1;
        	}else {
        		return 26;
        	}
        }
    }
    public boolean isLongintRange(double value) {
        return value >=  Math.pow(-2, 31) && value <= (Math.pow(2, 31) - 1);
    }
    public boolean isOctalRange(double token) {
        try {
    
            long minOctal = Long.parseLong("-020000000000", 8);  // Rango mínimo en octal
            long maxOctal = Long.parseLong("017777777777", 8);   // Rango máximo en octal
            return token >= minOctal && token <= maxOctal;

        } catch (NumberFormatException e) {
            // En caso de que el número no sea válido
            System.err.println("Error al parsear el número octal: " + token);
            return false;
        }
    }
    public boolean isDoubleRange(double value) {
        return (value > 2.2250738585072014e-308 && value < 1.7976931348623157e+308) || 
               (value > -1.7976931348623157e+308 && value < -2.2250738585072014e-308) || 
               value == 0.0;
    }
    public void showArray() {
    	System.out.println(tokenList);
    }
   
    
	public void setCurrentState(int i) {
		this.currentState = i;	}
}