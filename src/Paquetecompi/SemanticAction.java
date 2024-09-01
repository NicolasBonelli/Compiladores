package Paquetecompi;



//Implementación de las acciones semánticas como inner classes
abstract class SemanticAction {
    abstract void execute(Lexer lex,StringBuilder lexeme, char currentChar);
}

class ASE extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("Error");
    	/* Lo que vamos a hacer es en la matriz de AS, pasarle por el constructor el tipo de error, de manera que aquí
    	 * lo determinamos, y tiramos el error, warning, lo q sea*/
    	
    }
}

class ASI extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
        System.out.println("Leer siguiente token o eliminar espacio en blanco.");//se deja asi
    }// done a priori
}

class AS1 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("AS1");

    	lexeme.setLength(0);
    	
        lexeme.append(currentChar);
    	
    }//done a priori
}

class AS2 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("AS2");

    	lexeme.append(currentChar);
    }//done a priori
}

class AS3 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("AS3");

        String token = lexeme.toString().toUpperCase();
        if (lex.isReservedWord(token)) {//si está dentro de la tabla de tokens o si es palabra reservada
            Integer valorTabla = lex.getReservedWordToken(token);
        	System.out.println("Valor: "+  valorTabla);

            lex.addToken(valorTabla);//se agarra y se mete en la lista del analizador sintactico
        } else {
            if (token.length() > Lexer.MAX_ID_LENGTH) {
                System.out.println("Advertencia: Identificador demasiado largo en la linea " + Integer.toString(lex.getNroLinea()));
                System.out.println("Se recortara el identificador");
                token = lexeme.substring(0,15);
                if (!lex.containsSymbol(token))
                	lex.insertSymbolTable(token, SymbolTable.identifierValue);
            }
            //VER
            lex.addToken(SymbolTable.identifierValue);  
        }
        
    	lex.setLexeme("");
    }//done a priori
}

class AS4 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("AS4");

        String token = lexeme.toString();
        int numero = Integer.parseInt(token);
        if ((numero > Math.pow(-2, -31)) && (numero < Math.pow(2, 31))) {
        	lex.insertSymbolTable(token, SymbolTable.constantValue);
            lex.addToken(SymbolTable.constantValue);  

        } else {
            System.out.println("El número usado en la linea " + lex.getNroLinea() + " supera el rango permitido por los enteros.");

        }
       
    	lex.setLexeme("");
    }//done a priori
}

class AS5 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("AS5");

        String token = lexeme.toString();

    	try {
            // Eliminar 'd' si está presente y convertir el string en double
            double number = Double.parseDouble(token.replace("d", "e"));

            // Rango de números de doble precisión
            double minPositive = 2.2250738585072014e-308;
            double maxPositive = 1.7976931348623157e+308;
            double maxNegative = -2.2250738585072014e-308;
            double minNegative = -1.7976931348623157e+308;

            // Verificar si el número está dentro de los rangos permitidos
            if ((number > minPositive && number < maxPositive) ||
                (number < maxNegative && number > minNegative) || 
                number == 0.0) {
            	lex.insertSymbolTable(lexeme.toString(), SymbolTable.constantValue);
            	
                lex.addToken(SymbolTable.constantValue); 
            } else {
                System.out.println("El número está fuera del rango permitido.");
            }
        } catch (NumberFormatException e) {
            System.out.println("El formato del número es incorrecto.");
        }finally{
        	lex.setLexeme("");

        };
    	

    }//done a priori
    
}

class AS6 extends SemanticAction {//TABLA DE SIMBOLOS
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("AS6");
    	lexeme.append(currentChar);
        Integer valor=lex.getSymbol(lexeme.toString());
        lex.addToken(valor);
    	System.out.println("Valor: "+  valor + "para: " + lexeme.toString());

    	
    	lex.setLexeme("");
    } //done a priori
}

class AS7 extends SemanticAction {//TABLA PALABRA RESERVADAS
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("AS7");

    	lexeme.append(currentChar);
    	Integer valor = lex.getReservedWordToken(lexeme.toString());
    	System.out.println("Valor: "+  valor);

    	lex.addToken(valor);
    	
    	lex.setLexeme("");
    } //done a priori
}

class AS8 extends SemanticAction {//TABLA PALABRA RESERVADAS Y TABLA DE SIMBOLOS
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	System.out.println("AS8");

        if(Character.toString(currentChar).equals("=")) {//AGARRO =, CONCATENO Y ME FIJO EN TABLA DE RESERVADAS
        	lexeme.append(currentChar);
        	Integer valor = lex.getReservedWordToken(lexeme.toString());
        	lex.addToken(valor);
        }else {//AGARRAR HASTA EL LEXEMA Y ENTREGAR SIMBOLO DE TABLA DE SIMBOLOS
        	Integer valor = lex.getSymbol(lexeme.toString());
        	lex.addToken(valor);
        }
        
    	lex.setLexeme("");
    } //done a priori 
}
