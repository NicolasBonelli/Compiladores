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
	public Lexer(SymbolTable tabla) {
		reservedWords = new HashMap<>();
	    this.tabla=tabla;
	    estado = false;
		initializeReservedSymbols();
	    initializeReservedWords();
	    Lexer.nmrLinea = 1;
	    this.tokenList=new ArrayList<Pair>();
	    this.lexema=new StringBuilder();
	    tablaTipos= new HashMap<>();
	    
	}
	private int[][] transitionMatrix = {
		    { 0,  0,  0,  1, -1,  2,   3,   3, 16, 16, 16, 16, 16, 16, 16, 16, 16, 10, 15, 14, 14, 14, 11, 13, -1,  1, -1, 16, 16, -1},
		    {16, 16, 16,  1,  1,  1,   1,   1, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,  1, -1, 16, 16, 16},
		    {16, 16, 16, 16, 16,  4,   4,   3, 16, 16, 16, 16, 16, 16,  5, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, 16, 16, 16},
		    {16, 16, 16, 16, 16,  3,   3,   3, 16, 16, 16, 16, 16, 16,  5, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, 16, 16, 16},
		    {16, 16, 16, 16, 16,  4,   4,   3, 16, 16, 16, 16, 16, 16,  5, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, 16, 16, 16},
		    {-1, -1, -1, -1, -1,  6,   6,   6, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {16, 16, 16, 16, 16,  6,   6,   6, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16,  7, -1, 16, 16, 16},
		    {-1, -1, -1, -1, -1,  9,   9,   9,  8,  8, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1,  9,   9,   9, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {16, 16, 16, 16, 16,  9,   9,   9, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, 16, 16, 16},
		    {-1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 16, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1},
		    {-1, -1, -1, -1, -1, -1,  -1,  -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 12, -1, -1, -1, -1, -1, -1, -1},
		    {12, 12,  0, 12, 12, 12,  12,  12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, 12, -1, 12, 12, 12},
		    {13, 13, 13, 13, 13, 13,  13,  13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 13, 16, 13, -1, 13, 13, 13},
		    {16, 16, 16, 16, 16, 16,  16,  16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, 16, 16, 16},
		    {16, 16, 16, 16, 16, 16,  16,  16, 16, 16, 16, 16, 16, 16, 16, 16, 16, 16, -1, -1, -1, -1, 16, 16, 16, 16, -1, 16, 16, 16}
		};



	private SemanticAction[][] actionMatrix = {
		    // Estado 0
		    {new ASI(), new ASI(), new ASI(), new AS1(), new ASE("No puede empezar un identificador con _"), new AS1(), new AS1(), new AS1(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS6(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new AS1(), new ASE("Falta un ["), new AS1(), new ASE("Caracter unknown"), new AS6(), new AS6(), new ASE("No puede existir un @ solo")},
		    
		    // Estado 1
		    {new AS3(), new AS3(), new AS3(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS3(), new AS2(), new AS3(), new AS3(), new AS3(), new AS7()},
		    
		    // Estado 2
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 3
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 4
		    {new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS2(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS2(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4(), new AS4()},
		    
		    // Estado 5
		    {new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new AS2(), new AS2(), new AS2(), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito"), new ASE("Despues del . DEBE ir un digito")},
		    
		    // Estado 6
		    {new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS2(), new AS2(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS5(), new AS5(), new AS5(), new AS5()},
		    
		    // Estado 7
		    {new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero"), new ASE("Despues de la d debe ir un +, -, o numero")},
		    
		    // Estado 8
		    {new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new AS2(), new AS2(), new AS2(), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, - DEBE ir un numero"), new ASE("Despues del +, -, DEBE ir un numero")},
		    
		    // Estado 9
		    {new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS2(), new AS2(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS5(), new AS2(), new AS5(), new AS5(), new AS5(), new AS5()},
		    
		    // Estado 10
		    {new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new AS7(), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un ="), new ASE("Despues del : DEBE ir un =")},
		    
		    // Estado 11
		    {new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new AS2(), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #"), new ASE("Despues del # DEBE ir un #")},
		    
		    // Estado 12
		    {new AS2(), new AS2(), new ASI(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2()},
		    
		    // Estado 13
		    {new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2(), new AS7(), new AS2(), new AS2(), new AS2(), new AS2(), new AS2()},
		    
		    // Estado 14
		    {new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8()},
		    
		    // Estado 15
		    {new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new ASE("No puede ir un = despues de un ="), new ASE("No puede ir un < despues de un ="), new ASE("No puede ir un < despues de un ="), new ASE("No puede ir un ! despues de un ="), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8(), new AS8()}
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

    /* Definir rangos para tipos estándar*/
    boolean verificarRangoLongInt(double valor) {
        return valor >= -Math.pow(2, 31) && valor <= Math.pow(2, 31) - 1;
    }

    boolean verificarRangoDouble(double valor) {
        return valor >= -1.7976931348623157e308 && valor <= 1.7976931348623157e308;
    }

    String obtenerTipo(String variable) {
        /* Implementa la lógica para obtener el tipo de la variable a partir de una tabla de símbolos.*/
        /* Debe devolver el tipo como "longint", "double" o un tipo definido por el usuario.*/
        if (tabla.hasKey(variable)) return variable;

        return tabla.getType(variable);  /* Ejemplo*/
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
		this.tabla.addValue("bl",null,1); //espacio alt 32
		this.tabla.addValue("tab",null,2); //tab facil alt 09
		this.tabla.addValue("nl",null,3);	// /n ??
		this.tabla.addValue("letra-[d]",null,4);
		this.tabla.addValue("_",null,5);
		this.tabla.addValue("0",null,6);
		this.tabla.addValue("1 al 7",null,7);
		this.tabla.addValue("8 al 9",null, 8);
		this.tabla.addValue("+",null, 9);
		this.tabla.addValue("-",null, 10);
		this.tabla.addValue("/",null, 11);
		this.tabla.addValue("*",null, 12);
		this.tabla.addValue("(",null, 13);
		this.tabla.addValue(")",null, 14);
		this.tabla.addValue(".",null, 15);
		this.tabla.addValue(";",null, 16);
		this.tabla.addValue(",",null, 17);
		this.tabla.addValue(":",null, 18);
		this.tabla.addValue("=",null, 19);
		this.tabla.addValue("<",null, 20);
		this.tabla.addValue(">",null, 21);
		this.tabla.addValue("!",null, 22);
		this.tabla.addValue("#",null, 23);
		this.tabla.addValue("[",null, 24);
		this.tabla.addValue("]",null, 25);
		this.tabla.addValue("d",null, 26);
		this.tabla.addValue("{",null, 28);
		this.tabla.addValue("}",null, 29);
		this.tabla.addValue("@",null, 30);
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
    
    

    // Otras acciones semánticas implementadas de manera similar...
    
    public boolean isEstado() {
		return estado;
	}


	public void setEstado(boolean estado) {
		this.estado = estado;
	}



	public Pair analyze(BufferedReader reader) {
	    try {
	        int currentChar;
	        //reader.mark(1); // Marcar la posición actual en el archivo
	        
	        while ((currentChar = reader.read()) != -1) { // Leer hasta EOF
	            char c = (char) currentChar;
	           // System.out.println("CHAR: " + c);
	            if (c == '\n' || c == '\r') {
	                nmrLinea++;
	               // System.out.println("ENTRE");
	            }

	            int actionIndex = getTSIndex(c); // Obtener índice en la tabla de símbolos
	            
	            if (actionIndex != -1) {
	                actionMatrix[currentState][actionIndex].execute(this, this.lexema, c); // Ejecutar acción
	                currentState = transitionMatrix[currentState][actionIndex];
	                //System.out.println(currentState + " arriba");

	                if (currentState == 16) { // Estado final
	                    currentState = 0; // Resetear el estado
	                    Pair tokenPair = tokenList.remove(0); // Crear un Pair con el lexema y el índice de acción
	                    lexema.setLength(0); // Limpiar el lexema para el siguiente token
	                    if (estado) {
	    	                reader.reset(); // Volver a la última posición marcada
	    	                estado = false; // Reiniciar el estado para el siguiente ciclo
	    	            }
	                    return tokenPair; // Devolver el Pair
	                }

	                reader.mark(1); // Marcar la posición actual antes de leer el siguiente carácter
	            } else {
	                new ASE("Caracter no identificado").execute(this, this.lexema, c); // Manejo de errores
	                return null; // Devolver null si ocurre un error
	            }

	            
	        }

	        // Procesar cualquier carácter pendiente al final del archivo
	        if (currentState != 0 && !finished) {
	        	finished = true;
	            actionMatrix[currentState][0].execute(this, this.lexema, '\0'); // Manejar fin de archivo
	            Pair finalPair = tokenList.remove(0); // Devolver el último token o fin de archivo
	            return finalPair;
	        }
	    } catch (Exception e) {
	        e.printStackTrace(); // Manejo de excepciones de E/S
	    }
	    return null; // Devolver null al final del archivo o si ocurre un error
	}

    
    

    private int getTSIndex(char input) {
    	//System.out.println("Valor input:" + input);
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
        } else if (input == '\n' || input == '\r') {
            return this.tabla.getValue("nl") - 1; // Manejo de salto de línea
        } else if (input == '\t') {
            return this.tabla.getValue("tab") - 1; // Manejo de tabulación
        } else if (input == '@'){
        	return this.tabla.getValue("@") - 1;
        }
        
        else {
        	if (tabla.hasKey(Character.toString(input))) {
        		
        		//System.out.println("Valor devuelto columna:" + String.valueOf(this.tabla.getValue(Character.toString(input)) - 1));
        		return this.tabla.getValue(Character.toString(input)) - 1;
        	}else {
        		return -1;
        	}
        }
    }
    
    public void showArray() {
    	System.out.println(tokenList);
    }
    /*
    public Pair getLex() {
    	return tokenList.remove(0);
    } */

    public static void main(String[] args) {
     	SymbolTable st = new SymbolTable();
    	try (BufferedReader reader = new BufferedReader(new FileReader("C:\\Users\\hecto\\OneDrive\\Escritorio\\prueba.txt"))) {
    	    Lexer lexer = new Lexer(st); // Asumiendo que tienes una clase Lexer
    	    Pair token;
    	    while ((token = lexer.analyze(reader)) != null) {
    	        System.out.println("Token: " + token);
    	    }
    	} catch (IOException e) {
    	    e.printStackTrace();
    	}

    	System.out.println(st.toString());
    }

	public void setCurrentState(int i) {
		this.currentState = i;	}
}