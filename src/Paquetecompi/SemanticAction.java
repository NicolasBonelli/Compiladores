package Paquetecompi;



//Implementación de las acciones semánticas como inner classes
abstract class SemanticAction {
    abstract void execute(Lexer lex,StringBuilder lexeme, char currentChar);
}

class ASE extends SemanticAction {
private String errorType;
public ASE(String errorType) {
this.errorType = errorType;
}
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.err.println(errorType + " at linea: "+ lex.getNroLinea());
   
    }
}

class ASI extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
        //Solo inicia cadena o espacio en blanco
    }
}

class AS1 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	lexeme.setLength(0);
    	lex.setEstado(false);
        lexeme.append(currentChar);
    }
}

class AS2 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	lex.setEstado(false);
    	lexeme.append(currentChar);
    }
}

class AS3 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
        lex.setEstado(true);
        String token = lexeme.toString().toUpperCase();
        
        
        if (lex.isReservedWord(token)) {
            Integer valorTabla = lex.getReservedWordToken(token);
            lex.addToken(new Pair(lexeme.toString(), valorTabla));
            lex.setDevolvi(true);
        } else {
        	
            if (token.length() > Lexer.MAX_ID_LENGTH) {
                token = lexeme.substring(0, 15).toUpperCase();
                System.err.println("Linea: " + Lexer.nmrLinea + ": El identificador " + lexeme.toString() +"  fue truncado a: " + token);
            }
            if (!lex.containsSymbol(token)) {
                lex.insertSymbolTable(token, "String", SymbolTable.identifierValue);
            }
            lex.setDevolvi(true);
            lex.addToken(new Pair(token, SymbolTable.identifierValue));
        }
    }
}


class AS4 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	lex.setEstado(true);
        String token = lexeme.toString();
        
        
        if (token.startsWith("0") && !token.matches(".*[89].*")) { //si el numero es octal (empieza con 0 y no contiene ni 8 ni 9
            try {
                
                long numeroOctal = Long.parseLong(token, 8);// Convertimos el token en número octal (base 8)
                String representacionOctal = "0" + Long.toOctalString(numeroOctal);
                // Verificar si el número octal está en el rango permitido
                 
                long maxOctal = Long.parseLong("017777777777", 8);   // Rango máximo en octal
                
                
                if (numeroOctal > maxOctal) {
                    numeroOctal = maxOctal; // Si es mayor, ajustamos al máximo
                    representacionOctal = "0" + Long.toOctalString(numeroOctal);
                    System.err.println("El numero octal en la linea " + lex.getNroLinea() + " superaba el rango permitido. Se ajusto");
                }
                
                // Insertar el valor ajustado en la tabla de símbolos
                lex.insertSymbolTable(representacionOctal, "Octal", SymbolTable.constantValue);
                lex.setDevolvi(true);
                lex.addToken(new Pair(lexeme.toString(), SymbolTable.constantValue));
                
            } catch (NumberFormatException e) {
                System.err.println("El numero en la linea " + lex.getNroLinea() + " no es un octal valido.");
            }
        } else {
            // Manejo de números no octales (decimal)
            try {
                double numero = Double.parseDouble(token);
                if (numero > Math.pow(2, 31)-1) {
                	System.err.println("El numero usado en la linea " + lex.getNroLinea() + " supera el rango permitido para enteros. Se redujo.");
                	token= "2147483647";//se redujo al maximo
                } 
                lex.insertSymbolTable(token, "longint", SymbolTable.constantValue);
                lex.setDevolvi(true);
                lex.addToken(new Pair(lexeme.toString(), SymbolTable.constantValue));
            } catch (NumberFormatException e) {
                System.err.println("El numero en la linea " + lex.getNroLinea() + " no es valido.");
            }
        }
    }
}

class AS5 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	lex.setEstado(true);
        String token = lexeme.toString();
    	try {
            // Eliminar 'd' si está presente y convertir el string en double
            double number = Double.parseDouble(token.replace("d", "e"));

            // Rango de números de doble precisión
            double minPositive = 2.2250738585072014e-308;
            double maxPositive = 1.7976931348623157e+308;
            double maxNegative = -2.2250738585072014e-308;
            double minNegative = -1.7976931348623157e+308;
            //ACA TOQUETEAR PAQUI LO DE LOS RANGOS
            // Verificar si el número está dentro de los rangos permitidos
            if ((number >= minPositive && number <= maxPositive) ||
                (number <= maxNegative && number >= minNegative) || 
                number == 0.0) {
            	lex.insertSymbolTable(lexeme.toString(),"double" , SymbolTable.constantValue);
            	lex.setDevolvi(true);
                lex.addToken(new Pair(lexeme.toString(), SymbolTable.constantValue)); 
                
            } else {
            	//ACA TAMBIEN PAQUI
                System.out.println("El número está fuera del rango permitido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("El formato del número es incorrecto.");
      
        };
    }
}

class AS6 extends SemanticAction {//TABLA DE SIMBOLOS
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	lex.setEstado(false);
    	lexeme.append(currentChar);
        Integer valor = lex.getSymbol(lexeme.toString());
        lex.setDevolvi(true);
        lex.addToken(new Pair(lexeme.toString(), valor));
    } 
}

class AS7 extends SemanticAction {//TABLA PALABRA RESERVADAS
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	lex.setEstado(false);
		lexeme.append(currentChar);
		Integer valor = 0;
    	if (currentChar == ']') {
        	lex.insertSymbolTable(lexeme.toString().replace("[", "").replace("]", "").replace("\n", " "),"String", SymbolTable.stringValue);
        	valor = SymbolTable.stringValue;
    	}else if(currentChar == '@') {
    		lex.insertSymbolTable(lexeme.toString(),"String",SymbolTable.tagValue);
    		valor = SymbolTable.tagValue;
    	}
    	else {
    		valor = lex.getReservedWordToken(lexeme.toString());
    	}
    	lex.setDevolvi(true);
    	lex.addToken(new Pair(lexeme.toString(), valor));
    } 
}

class AS8 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
        if(Character.toString(currentChar).equals("=")) {//AGARRO =, CONCATENO Y ME FIJO EN TABLA DE RESERVADAS
        	lex.setEstado(false);
        	lexeme.append(currentChar);
        	Integer valor = lex.getReservedWordToken(lexeme.toString());
        	lex.setDevolvi(true);
        	lex.addToken(new Pair(lexeme.toString(), valor));
        }else {//AGARRAR HASTA EL LEXEMA Y ENTREGAR SIMBOLO DE TABLA DE SIMBOLOS
        	Integer valor = lex.getSymbol(lexeme.toString());
        	lex.setEstado(true);
        	lex.setDevolvi(true);
        	lex.addToken(new Pair(lexeme.toString(), valor));
        }
        
    } 
}
