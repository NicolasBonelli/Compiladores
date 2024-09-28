%{
    package gramaticPackage;
    import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import Paquetecompi.Lexer;
import Paquetecompi.Pair;
import Paquetecompi.SymbolTable;
    
 
    // Clase para almacenar la información de los subrangos.
   /* Clase para almacenar la información de los subrangos.*/
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

class Subrango{
    private double limiteSuperior;
    private double limiteInferior;
    
    public Subrango(double limiteSuperior, double limiteInferior) {
        this.limiteSuperior = limiteSuperior; this.limiteInferior = limiteInferior;
    }
    public double getLimiteInferior() {
        return limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }
}

//#line 66 "Parser.java"



%}

%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET REPEAT WHILE PAIR GOTO
%token LONGINT DOUBLE MENOR_IGUAL MAYOR_IGUAL DISTINTO T_ASIGNACION
%token T_CADENA T_ID T_CTE T_ETIQUETA
%left '+' '-'
%left '*' '/'

%%

programa: T_ID bloque_sentencias {
    System.out.println("Programa compilado correctamente");
} 
| T_ID { 
    System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
} 
| bloque_sentencias {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}



bloque_sentencias: BEGIN sentencias END {System.out.println("Llegue a BEGIN sentencia END");}
                | BEGIN END {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
                | error {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan Delimitador o Bloque de Sentencia");};
                
sentencias: sentencias sentencia 
          | sentencia {System.out.println("Llegue a sentencias");};

sentencia: declaracion 
         | asignacion
         | if_statement
         | repeat_while_statement
         | salida
         | declaracion_funcion
         | goto_statement
         | sentencia_declarativa_tipos
         | T_ETIQUETA
         | RET '(' expresion ')' ';'
         | RET '(' expresion ')' {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
         {System.out.println("Llegue a sentencia");};


declaracion: tipo lista_var ';' { 
    System.out.println("Llegue a declaracion");
    List<ParserVal> variables = new ArrayList<ParserVal>();
    variables.add(val_peek(1)); /* Asume que lista_var devuelve una lista de variables*/
    
    for (ParserVal variable : variables) {
        /* Verificar si la variable ya existe en la tabla de símbolos*/
        if (!st.hasKey(variable.toString())) {
            System.out.println("Aclaracion, la tabla de símbolos no contenía la variable: " + variable.toString());
        } else {
            /* Actualiza el tipo de la variable si ya está en la tabla de símbolos*/
            boolean actualizado = st.updateType(variable.toString(), val_peek(2).toString());
            if (actualizado) {
                System.out.println("Tipo de la variable '" + variable + "' actualizado a: " + val_peek(2));
            } else {
                System.out.println("Error al actualizar el tipo de la variable: " + variable);
            }
        }
    }
} | 
    tipo lista_var {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");};



lista_var: lista_var ',' T_ID { 
   
}
| T_ID { 
    
} | error { System.err.println("Error en línea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables");};


declaracion_funcion:
    tipo FUN T_ID '(' parametro ')' bloque_sentencias {
        System.out.println("Declaración de función correcta");
    }
    
    | tipo FUN T_ID '(' parametros_error ')' bloque_sentencias {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }

    // Errores adicionales que ya tenías
    | tipo FUN T_ID '(' tipo ')' bloque_sentencias {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }

    | tipo T_ID '(' tipo T_ID ')' bloque_sentencias {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }

    | tipo FUN '(' tipo T_ID ')' bloque_sentencias {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
    };

parametro:
    tipo T_ID {
        // Caso correcto con un solo parámetro
    };

parametros_error:
    parametro ',' parametro {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
    | parametros_error ','  parametro{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
    | /* vacío */ {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
    };

repeat_sentencia: bloque_sentencias {}
                | sentencia;{}



tipo: DOUBLE { yyval.sval = "double"; }
    | LONGINT { yyval.sval = "longint"; }
    | T_ID
    {
        System.out.println("Llegue a tipo");
        /* Verificar si el tipo está en la tabla de tipos definidos*/
        if (tablaTipos.containsKey(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo está definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0));
        } 
    };
    


if_statement: IF '(' condicion ')' THEN repeat_sentencia END_IF ';'
            | IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF ';'
            | IF '(' condicion ')' THEN repeat_sentencia END_IF {
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
            | IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF  {
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
            
            | IF '('  ')' THEN repeat_sentencia END_IF ';' {
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
            | IF '('  ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF ';' {
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }

            | IF '('condicion  ')'  repeat_sentencia END_IF ';' {
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
            | IF '(' condicion ')'  repeat_sentencia ELSE repeat_sentencia END_IF ';' {
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
            
             | IF '('condicion  ')' THEN END_IF ';' {
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
            | IF '(' condicion ')' THEN   ELSE  END_IF ';' {
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
            | IF  condicion  THEN repeat_sentencia END_IF ';' {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
            | IF  condicion  THEN repeat_sentencia ELSE repeat_sentencia END_IF ';' {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");};
            
            


repeat_while_statement: REPEAT repeat_sentencia WHILE '(' condicion ')' ';'
    | REPEAT repeat_sentencia WHILE '(' condicion ')' {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
    | REPEAT WHILE '(' condicion ')' {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
    | REPEAT repeat_sentencia WHILE '('  ')' ';'{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
    | REPEAT repeat_sentencia WHILE  condicion  ';' {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");};




salida: OUTF '(' T_CADENA ')' ';' 
      | OUTF '(' expresion ')' ';' {     
        System.out.println("Llegue a salida");   
        }
      | OUTF '(' expresion ')' {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
      | OUTF '(' T_CADENA ')' {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      } 
      | OUTF '('  ')' ';' {System.err.println("Error en línea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}  
      | OUTF '(' sentencia ')' ';' {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");};


sentencia_declarativa_tipos: TYPEDEF T_ID T_ASIGNACION tipo subrango ';' {
        System.out.println("Llegue a sentencia_declarativa_tipos");
        
        // Obtener el nombre del tipo desde T_ID
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        // Obtener el tipo base (INTEGER o SINGLE)
        String tipoBase = val_peek(2).sval;
        System.out.println("tipobase"+ " "+tipoBase );
        /* tipo base (INTEGER o SINGLE)*/
        // Limite inferior del subrango (asegúrate de que sea numérico)
        double limiteInferior = val_peek(4).dval; /* Limite inferior */
        System.out.println("liminf"+ " "+limiteInferior );
        // Limite superior del subrango (asegúrate de que sea numérico)
        double limiteSuperior =  val_peek(5).dval; /* Limite superior */
        System.out.println("limsup"+ " "+limiteSuperior );
        // Almacenar en la tabla de tipos
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        System.out.println("ENTRE A DEFINIR NUEVO TIPO");
        }
        | TYPEDEF PAIR '<' LONGINT '>' T_ID ';' {
            // Obtener el nombre del tipo desde T_ID
            String nombreTipo = val_peek(4).sval; /* T_ID*/

            // Obtener el tipo base (INTEGER o SINGLE)
            String tipoBase = val_peek(2).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        }
        | TYPEDEF PAIR '<' DOUBLE '>' T_ID ';' {

        }
        | TYPEDEF T_ID T_ASIGNACION tipo subrango {
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
        | TYPEDEF PAIR '<' T_ID '>' T_ID ';' {
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
        | TYPEDEF PAIR '<' LONGINT '>' T_ID {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
        | TYPEDEF PAIR '<' DOUBLE '>' T_ID {System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");};

subrango: '{' T_CTE ',' T_CTE '}'{
        System.out.println("Llegue a subrango");

        // Verificar que los valores en la pila son correctos antes de convertirlos
        String limiteInferiorStr = val_peek(3).sval; // T_CTE (límites inferiores)
        String limiteSuperiorStr = val_peek(1).sval; // T_CTE (límites superiores)

        System.out.println("VAL3 (Limite Inferior): " + limiteInferiorStr);
        System.out.println("VAL1 (Limite Superior): " + limiteSuperiorStr);

        try {
            // Convertir los valores de límites de cadena a double
            double limiteInferior = Double.parseDouble(limiteInferiorStr);
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);

            // Crear el objeto Subrango y asignarlo
            yylval.obj = new Subrango(limiteInferior, limiteSuperior);
            System.out.println("Subrango creado correctamente con límites: " + limiteInferior + " - " + limiteSuperior);
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los límites del subrango a double: " + e.getMessage());
        }
    } 
    |'{' '-' T_CTE ',' T_CTE '}'{ System.out.println("Llegue a subrango con - en el primero");}
    |'{' T_CTE ',' '-' T_CTE '}'{System.out.println("Llegue a subrango con - en el segundo");}
    |'{' '-' T_CTE ',' '-' T_CTE '}'{System.out.println("Llegue a subrango con - en los dos");}
    | error {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    };//PROBAR TODO


condicion: expresion comparador expresion | expresion comparador {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado derecho de la comparacion");}
         | comparador expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado izquierdo de la comparacion");};

comparador:    MENOR_IGUAL  
            |  MAYOR_IGUAL 
            |  DISTINTO 
            |  '=' 
            |  '<' 
            |  '>' 
           ;

           
asignacion: IDENTIFIER_LIST T_ASIGNACION expresion_list ';' {} 
          | IDENTIFIER_LIST T_ASIGNACION expresion_list {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion");};
        
IDENTIFIER_LIST: T_ID { 
                }
               | IDENTIFIER_LIST ',' T_ID {
               
                }
               | IDENTIFIER_LIST ',' acceso_par {
                }
               | acceso_par  {
                };

expresion_list: 
      expresion {
      }
    | expresion_list ',' expresion {
      }
    
    ;

acceso_par: 
      T_ID '{' T_CTE '}' {
                    /* Verificar si el T_CTE es '1' o '2'*/
          if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
              yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Solo se permite 1 o 2 dentro de las llaves.");
          } else {
              yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
          
      }
    }
    | T_ID '{' '}' { 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta constante dentro de las llaves."); 
      }
    | T_ID '{' T_CTE { 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta cierre de llave en acceso a parámetros."); 
      }
    | T_ID T_CTE '}' { 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta apertura de llave en acceso a parámetros."); 
    }
    ;


goto_statement: GOTO T_ETIQUETA';' | GOTO ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
              | GOTO T_ETIQUETA {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");};

invocacion_funcion: 
      T_ID '(' parametro_real ')' {
      }
      | error {
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida");
        }
      ; //PROBAR SI SE ROMPE LO DE OUTF CON CADENA

parametro_real: expresion_aritmetica ; 

expresion_aritmetica: expresion_aritmetica '+' expresion_aritmetica 
         | expresion_aritmetica '-' expresion_aritmetica 
         | expresion_aritmetica '*' expresion_aritmetica 
         | expresion_aritmetica '/' expresion_aritmetica 
         | T_CTE 
         | T_ID 
         | acceso_par
         | unaria;
         | expresion_aritmetica '+' operador expresion_aritmetica {System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
         | expresion_aritmetica '*' operador expresion_aritmetica {System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
         | expresion_aritmetica '/' operador expresion_aritmetica {System.err.println("Error en linea: " + Lexer.nmrLinea + "Error: Dos o mas operadores juntos");};

operador: '+' | '*' | '/' | operador '+' | operador '/' | operador '*';

expresion: expresion '+' expresion {
        }
         | expresion '-' expresion {
        }
         | expresion '*' expresion {
        }
         | expresion '/' expresion {
        }
         | T_CTE {
        }
         | T_ID {
        }
         | acceso_par{
        }
        | invocacion_funcion{
        }
        | unaria { // Se añade la regla para operadores unarios
        }
        | expresion '+' operador expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
        | expresion '*' operador expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
        | expresion '/' operador expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");};
        
        ;

unaria: '-' expresion { // Esta regla maneja específicamente el '-' unario
    yyval.dval = -val_peek(0).dval;
}
      ;

%%

public void yyerror(String s) {
    System.err.println("Error en linea: " + Lexer.nmrLinea + " String: " +s);
  }

int yylex() {
    try {
        Pair token = lexer.analyze(reader);  // Sigue desde donde se quedó
        System.out.println("Pair: "+ token);
        if (token != null) {
            System.out.println("Token: " + token.getLexema() + " :: " + token.getToken());

            // Dependiendo del token, rellena el valor en yylval
            if (token.getToken() == 277 || token.getToken() == 278 || token.getToken() == 279 || token.getToken() == 280) {
                yylval = new ParserVal(token.getLexema());
            }
            if(token.getToken()<31) {
            	
            	char character = token.getLexema().charAt(0);  // Obtiene el carácter en la posición 'i'
                System.out.println("Character:" + character);
            	int ascii = (int) character;
                return ascii;
            	
            }

            return token.getToken();  // Devuelve el token al parser
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;  // Indicar fin de archivo o error
}


public static void main(String[] args) {
    Parser parser = new Parser("C:\\Users\\hecto\\OneDrive\\Escritorio\\prueba.txt");
    parser.run();
}


 // Función para verificar si el valor está dentro del rango
 boolean verificarRango(String tipo, double valor) {
    if (tablaTipos.containsKey(tipo)) {
        TipoSubrango subrango = tablaTipos.get(tipo);
        return valor >= subrango.limiteInferior && valor <= subrango.limiteSuperior;
    }
    return true; // Si no es un tipo definido por el usuario, no se verifica el rango
}

// Definir rangos para tipos estándar
boolean verificarRangoLongInt(double valor) {
    return valor >= -Math.pow(2, 31) && valor <= Math.pow(2, 31) - 1;
}

boolean verificarRangoDouble(double valor) {
    return valor >= -1.7976931348623157e308 && valor <= 1.7976931348623157e308;
}

String obtenerTipo(String variable) {
    // Implementa la lógica para obtener el tipo de la variable a partir de una tabla de símbolos.
    // Debe devolver el tipo como "longint", "double" o un tipo definido por el usuario.
    if (!st.hasKey(variable)) return variable;

    return st.getType(variable);  // Ejemplo
}


private Map<String, TipoSubrango> tablaTipos;
	 private SymbolTable st;
	 private Lexer lexer;
	    private BufferedReader reader;

	    public Parser(String filePath) {
	        this.st = new SymbolTable();
	        this.tablaTipos= new HashMap<String,TipoSubrango>();
	        try {
	            this.reader = new BufferedReader(new FileReader(filePath));
	            this.lexer = new Lexer(st);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
