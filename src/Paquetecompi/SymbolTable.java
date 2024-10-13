package Paquetecompi;

import java.util.HashMap;
import java.util.Map;

import gramaticPackage.*;
public class SymbolTable {
	
	private HashMap<Symbol,Integer> symbolMap;
	public static final int stringValue = 277;
	public static final int identifierValue = 278;
	public static final int constantValue = 279;
	public static final int tagValue=280;
	private Map<String, TipoSubrango> tablaTipos;

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
	        return nombre.equals(symbol.nombre);  // Comparamos solo por nombre
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

	}
	public void addValue(String clave,String tipo,String uso,String ambito, Integer valor) {
		Symbol sym= new Symbol(clave,tipo,uso,ambito);
		if(!symbolMap.containsKey(sym)) {
			this.symbolMap.put(sym,valor);
		}
	}
	public boolean updateType(String variable, String nuevoTipo) {
	    for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(variable)) {
	            symbol.setTipo(nuevoTipo); // Actualiza el tipo del símbolo
	            return true; 
	        }
	    }
	    return false;  // No se encontro el símbolo
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
		Symbol sym= new Symbol(key,null,null,null);
        return symbolMap.containsKey(sym);
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

	public void imprimirTablaTipos() {

		System.out.println(this.tablaTipos);  
	  
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
}

