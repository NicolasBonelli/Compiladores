package Paquetecompi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import gramaticPackage.*;
public class SymbolTable {
	
	private HashMap<Symbol,Integer> symbolMap;
	public static final int stringValue = 277;
	public static final int identifierValue = 278;
	public static final int constantValue = 279;
	public static final int tagValue=280;
	private Map<String, TipoSubrango> tablaTipos;
	private Map<String, CaracteristicaFuncion> tablaFuncion;
	public static  List<Integer> posicionesPolaca = new ArrayList<>();
    public static  List<String> polaca = new ArrayList<>();
    public static  Stack<Integer> pila = new Stack<>();
    public static  StringBuilder ambitoGlobal = new StringBuilder();
    private static int posActualPolaca;
    
    
    public static void aggPolaca(String valor){
        polaca.add(valor);
        posicionesPolaca.add(posActualPolaca);
        posActualPolaca+=1;
    }
	public boolean updateUse(String variable, String newUse) {
	    for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(variable)) {
	            symbol.setUso(newUse); // Actualiza el tipo del símbolo
	            return true; 
	        }
	    }
	    return false;  // No se encontro el símbolo
	}
	public String getUse(String variable) {
	    for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(variable)) {
	           
	            return symbol.getUso(); 
	        }
	    }
	    return " ";  // No se encontro el símbolo
	}
	public SymbolTable(){
		this.symbolMap=new HashMap<Symbol, Integer>();
		this.tablaTipos= new HashMap<String,TipoSubrango>();
		this.tablaFuncion = new HashMap<String, CaracteristicaFuncion>();
		ambitoGlobal.setLength(0);
	}
	
	public void addValue(String clave,String tipo,String uso,String ambito, Integer valor) {
		Symbol sym= new Symbol(clave,tipo,uso,ambito);
		if(!symbolMap.containsKey(sym)) {
			this.symbolMap.put(sym,valor);
		}
	}
	public boolean updateType(String variable,String ambito, String nuevoTipo) {
	    for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(variable) && symbol.getAmbito().equals(ambito)) {
	            symbol.setTipo(nuevoTipo); // Actualiza el tipo del símbolo
	            return true; 
	        }
	    }
	    return false;  // No se encontro el símbolo
	}

		// Método auxiliar para verificar si un ámbito es compatible con el ámbito actual
	private boolean esAmbitoCompatible(String ambitoVar) {
		// Si el ámbito de la variable es el mismo o está contenido en el ámbito actual, es compatible
		return ambitoGlobal.toString().startsWith(ambitoVar);
	}

	public boolean esUsoValidoAmbito(String var1) {
		// Verifica si var1  declarada en el ámbito actual o en ámbitos superiores
		String ambitoVar1 = getAmbitoByKey(var1);
	
		if (ambitoVar1 == null) {
			// Si alguna variable no está declarada, devolvemos false
			System.out.println("Error: "+var1 +" no está declarada en ningún ámbito");
			return false;
		}
	
		// Verificamos si var1 puede usar var2, o viceversa, de acuerdo a sus ámbitos
		if (esAmbitoCompatible(ambitoVar1)) {
			return true; // Ambas variables están en ámbitos compatibles
		} else {
			System.err.println("Error: "+ var1 +" nunca ha sido declarada");
			return false;
		}
	}


	public void imprimirTablaTipos() {

        System.out.println(this.tablaTipos);
        System.out.println(polaca); System.out.println(posicionesPolaca);

      }
	public boolean updateAmbito(String variable, StringBuilder nuevoAmbito) {
	    for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(variable)) { 
	    		symbol.setAmbito(nuevoAmbito.toString()); // Actualiza el ambito
	            return true; 
	        }
	    }
	    return false;  // No se encontro el símbolo
	}
	public String getAmbitoByKey(String nombre) {
		for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(nombre)) {
	        	return symbol.getAmbito();
	        }
	    }
	    return null;
	}


	public boolean contieneSymbolAmbito(String nombre, StringBuilder nuevoAmbito) {
		Symbol simb= new Symbol(nombre,null,null,nuevoAmbito.toString());
		return this.symbolMap.containsKey(simb);
	}


	public Integer getValue(String clave) {
		Symbol sym= new Symbol(clave,null,null,null);
		if(symbolMap.containsKey(sym)) {
			return symbolMap.get(sym);
		}else {
			return -1;
		}
	}
	
	public String getType(String key) {
	    // Recorremos el HashMap buscando el símbolo con el nombre coincidente
	    for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(key)) {
	            return symbol.getTipo(); // Si el nombre coincide, devolvemos el tipo
	        }
	    }
	    return null; 
	}
	public boolean hasKey(String key) {
		for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(key)) {
	            return true; // Si el nombre coincide, devolvemos el tipo
	        }
	    }
	    return false; 
    }
	@Override
	public String toString() {
	    StringBuilder sb = new StringBuilder();
	    sb.append("SymbolTable:\n");
	    for (Symbol key : symbolMap.keySet()) {
	        sb.append(key.toString())
	          .append(" -> ")
	          .append(symbolMap.get(key))
	          .append("\n");
	    }
	    return sb.toString();
	}

	
	  public boolean containsKeyTT(String tipo){
		return this.tablaTipos.containsKey(tipo);
	  }
	
	  public void insertTT(String nombreTipo, TipoSubrango tipoSubrango){
		this.tablaTipos.put(nombreTipo, tipoSubrango);
	  }
	
	  public TipoSubrango getTipoSubrango(String nombreTipo){
		return this.tablaTipos.get(nombreTipo);
	  }
	public boolean isTypePair(String tipo) {
		// Recorremos la tabla de símbolos buscando un símbolo con el nombre coincidente y uso "Nombre de tipo de par"
		for (Symbol symbol : symbolMap.keySet()) {
			if (symbol.getNombre().equals(tipo) && "Nombre de tipo de par".equals(symbol.getUso())) {
				return true; // Si encontramos un símbolo con el nombre y el uso correcto, devolvemos true
			}
		}
		return false; // Si no encontramos ningún símbolo que cumpla, devolvemos false
	}

	 // Método para imprimir la tabla de funciones
	 public void imprimirTablaFunciones() {
        System.out.println(this.tablaFuncion);
    }

    // Método para verificar si existe una clave en la tabla de funciones
    public boolean containsKeyTF(String nombreFuncion) {
        return this.tablaFuncion.containsKey(nombreFuncion);
    }

    // Método para insertar un nuevo elemento en la tabla de funciones
    public void insertTF(String nombreFuncion, CaracteristicaFuncion caracteristicaFuncion) {
        this.tablaFuncion.put(nombreFuncion, caracteristicaFuncion);
    }

    // Método para obtener la característica de una función por su nombre
    public CaracteristicaFuncion getCaracteristicaFuncion(String nombreFuncion) {
        return this.tablaFuncion.get(nombreFuncion);
    }


	/*------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------
	------------------------------------------------------------------------------------
	*/
	public class Symbol implements Comparable{
	    private String nombre;
	    private String tipo;
	    private String uso;
	    private String ambito;

	    public Symbol(String nombre, String tipo, String uso,String ambito) {
	        this.nombre = nombre;
	        this.tipo = tipo;
	        this.uso=uso;
	        this.ambito=ambito;

	    }

	    public String getUso() {
			return uso;
		}

		public void setUso(String uso) {
			this.uso = uso;
		}

		public String getAmbito() {
			return ambito;
		}

		public void setAmbito(String ambito) {
			this.ambito = ambito;
		}

		public String getNombre() {
	        return nombre;
	    }

	    public void setNombre(String nombre) {
	        this.nombre = nombre;
	    }

	    public String getTipo() {
	        return tipo;
	    }

	    public void setTipo(String tipo) {
	        this.tipo = tipo;
	    }

		

	    @Override
	    public boolean equals(Object o) {
	        if (this == o) return true;
	        if (o == null || getClass() != o.getClass()) return false;

	        Symbol symbol = (Symbol) o;
	        return nombre.equals(symbol.nombre) && ambito.equals(symbol.getAmbito());  // Comparamos solo por nombre
	    }

	    @Override
	    public int hashCode() {
	        return nombre.hashCode();  // El hashCode se basa solo en el nombre
	    }

	    @Override
	    public String toString() {
	        return "Symbol{" + "nombre=" + nombre +   ",tipo=" + tipo +",uso="+uso+",ambito="+ambito +'}';
	    }
	    
	    @Override
        public int compareTo(Object o) {
            Symbol symbol = (Symbol)o;
            return this.nombre.compareTo(symbol.nombre);
        }
	}

}

