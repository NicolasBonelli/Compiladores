package Paquetecompi;



//Implementación de las acciones semánticas como inner classes
abstract class SemanticAction {
    abstract void execute(Lexer lex,StringBuilder lexeme, char currentChar);
}

class ASE extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	
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
    	lexeme.setLength(0);
    	
        lexeme.append(currentChar);
    	
    }//done a priori
}

class AS2 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	lexeme.append(currentChar);
    }//done a priori
}

class AS3 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	
        String token = lexeme.toString();
        if (lex.isReservedWord(token)) {//si está dentro de la tabla de tokens o si es palabra reservada
            Integer valorTabla=lex.getReservedWordToken(token);
            lex.addToken(valorTabla);//se agarra y se mete en la lista del analizador sintactico
        } else {
            if (token.length() > Lexer.MAX_ID_LENGTH) {
                System.out.println("Advertencia: Identificador demasiado largo en la línea " + Integer.toString(lex.getNroLinea()));
                System.out.println("Se recortara el identificador");
                token= lexeme.substring(0,15);
                
                lex.insertReservedWord(token);//cambiar
                
            }
            //VER
            lex.addToken(token);  
        }
    }
}

class AS4 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
        System.out.println("Iniciar la cadena de string y agregar primera letra.");
    }
}

class AS5 extends SemanticAction {
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
        System.out.println("Iniciar la cadena de string y agregar primera letra.");
    }
}

class AS6 extends SemanticAction {//TABLA DE SIMBOLOS
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
        Integer valor=lex.getSymbol(lexeme.toString());
    	lex.addToken(valor);
    	lex.setLexeme(null);
    } //done a priori
}

class AS7 extends SemanticAction {//TABLA PALABRA RESERVADAS
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
    	lexeme.append(currentChar);
    	Integer valor = lex.getReservedWordToken(lexeme.toString());
    	lex.addToken(valor);
    	lex.setLexeme(null);
    } //done a priori
}

class AS8 extends SemanticAction {//TABLA PALABRA RESERVADAS Y TABLA DE SIMBOLOS
    @Override
    void execute(Lexer lex,StringBuilder lexeme, char currentChar) {
        if(Character.toString(currentChar).equals("=")) {//AGARRO =, CONCATENO Y ME FIJO EN TABLA DE RESERVADAS
        	lexeme.append(currentChar);
        	Integer valor = lex.getReservedWordToken(lexeme.toString());
        	lex.addToken(valor);
        }else {//AGARRAR HASTA EL LEXEMA Y ENTREGAR SIMBOLO DE TABLA DE SIMBOLOS
        	Integer valor = lex.getSymbol(lexeme.toString());
        	lex.addToken(valor);
        }
        
        lex.setLexeme(null);
    } //done a priori 
}
