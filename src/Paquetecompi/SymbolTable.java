package Paquetecompi;

import java.util.HashMap;

public class SymbolTable {
	
	private HashMap<Symbol,Integer> symbolMap;
	static final int stringValue = 277;
	static final int identifierValue = 278;
	static final int constantValue = 279;
	static final int tagValue=280;
	
	public class Symbol {
	    private String nombre;
	    private String tipo;

	    public Symbol(String nombre, String tipo) {
	        this.nombre = nombre;
	        this.tipo = tipo;
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
	        return nombre.equals(symbol.nombre);  // Comparar solo por nombre
	    }

	    @Override
	    public int hashCode() {
	        return nombre.hashCode();  // El hashCode se basa solo en el nombre
	    }

	    @Override
	    public String toString() {
	        return "Symbol{" + "nombre='" + nombre + '\'' + ", tipo='" + tipo + '\'' + '}';
	    }
	}
	
	
	public SymbolTable(){
		this.symbolMap=new HashMap<Symbol, Integer>();
	}
	public void addValue(String clave,String tipo, Integer valor) {
		Symbol sym= new Symbol(clave,tipo);
		if(!symbolMap.containsKey(sym)) {
			this.symbolMap.put(sym,valor);
		}
	}
	public boolean updateType(String variable, String nuevoTipo) {
	    for (Symbol symbol : symbolMap.keySet()) {
	        if (symbol.getNombre().equals(variable)) {
	            symbol.setTipo(nuevoTipo); // Actualiza el tipo del símbolo
	            return true;  // Tipo actualizado exitosamente
	        }
	    }
	    return false;  // No se encontró el símbolo
	}
	public Integer getValue(String clave) {
		Symbol sym= new Symbol(clave,null);
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
	    return null; // Si no se encuentra el símbolo, retornamos null o podrías lanzar una excepción si es necesario
	}
	public boolean hasKey(String key) {
		Symbol sym= new Symbol(key,null);
        return symbolMap.containsKey(sym);
    }
	@Override	
	public String toString() {
		return symbolMap.toString();
	}
	
}

