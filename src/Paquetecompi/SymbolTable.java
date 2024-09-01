package Paquetecompi;

import java.util.HashMap;

public class SymbolTable {
	private HashMap<String,Integer> symbolMap;
	static final int identifierValue = 278;
	static final int constantValue = 279;
	
	public SymbolTable(){
		this.symbolMap=new HashMap<String, Integer>();
	}
	public void addValue(String clave, Integer valor) {
		if(!symbolMap.containsKey(clave)) {
			this.symbolMap.put(clave,valor);
		}
	}
	public Integer getValue(String clave) {
		if(symbolMap.containsKey(clave)) {
			return symbolMap.get(clave);
		}else {
			return -1;
		}
	}
	
	
	public boolean hasKey(String key) {
        return symbolMap.containsKey(key);
    }
	
	
}
	

