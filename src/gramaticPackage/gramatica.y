%{
    package gramaticPackage;
    import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import Paquetecompi.Lexer;
import Paquetecompi.Pair;
import Paquetecompi.SymbolTable;
import gramaticPackage.*;
    
 
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
};

bloque_sentencias: BEGIN sentencias END {System.out.println("Llegue a BEGIN sentencia END");};

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
         {System.out.println("Llegue a sentencia");};


declaracion: tipo lista_var ';' { 
    System.out.println("Llegue a declaracion");
    List<String> variables = (List<String>)$2; // Asume que lista_var devuelve una lista de variables
    
    for (String variable : variables) {
        // Verificar si la variable ya existe en la tabla de símbolos
        if (!st.hasKey(variable)) {
            System.out.println("Error en linea: " + Lexer.nmrLinea + " ERROR, la tabla de símbolos no contenía la variable: " + variable);
        } else {
            // Actualiza el tipo de la variable si ya está en la tabla de símbolos
            boolean actualizado = st.updateType(variable, $1);
            if (actualizado) {
                System.out.println("Tipo de la variable '" + variable + "' actualizado a: " + $1);
            } else {
                System.out.println("Error en linea: " + Lexer.nmrLinea + " Error al actualizar el tipo de la variable: " + variable);
            }
        }
    }
};

declaracion_funcion:
      tipo FUN T_ID '(' tipo T_ID ')' bloque_sentencias {System.out.println("declaracion_funcion");};

    

repeat_sentencia: bloque_sentencias {}
                | sentencia;{}


lista_var: lista_var ',' T_ID { 
   
}
| T_ID { 
    
};

tipo: DOUBLE { $$ = "double"; }
    | LONGINT { $$ = "longint"; }
    | T_ID
    {
        System.out.println("Llegue a tipo");
        // Verificar si el tipo está en la tabla de tipos definidos
        if (tablaTipos.containsKey($1)) {
            $$ = $1; // Si el tipo está definido, se usa el nombre del tipo
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + $1);
        }
    }
    ;


if_statement: IF '(' condicion ')' THEN repeat_sentencia END_IF ';'
            | IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF ';'
            ;


repeat_while_statement: REPEAT repeat_sentencia WHILE '(' condicion ')' ';';



salida: OUTF '(' T_CADENA ')' ';' 
      | OUTF '(' expresion ')' ';' {     System.out.println("Llegue a salida");   };


sentencia_declarativa_tipos: TYPEDEF T_ID T_ASIGNACION tipo subrango ';'
        | TYPEDEF PAIR '<' LONGINT '>' T_ID ';'
        | TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'
        {
            System.out.println("Llegue a sentencia_declarativa_tipos");
        // Guardar el nuevo tipo en la tabla de símbolos
        String nombreTipo = $2; // T_ID
        String tipoBase = $4; // tipo base (INTEGER o SINGLE)
        double limiteInferior = Double.parseDouble($5); // Limite inferior del subrango
        double limiteSuperior = Double.parseDouble($6); // Limite superior del subrango

        // Almacenar en la tabla de símbolos
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
    };

subrango: '{' T_CTE ',' T_CTE '}'{
        System.out.println("Llegue a subrango");

        //$$ = new Subrango(Double.parseDouble($2), Double.parseDouble($4));
    } 
    |'{' '-' T_CTE ',' T_CTE '}'
    |'{' T_CTE ',' '-' T_CTE '}'
    |'{' '-' T_CTE ',' '-' T_CTE '}'
    ;


condicion: expresion comparador expresion | expresion comparador {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado derecho de la comparacion");}
         | comparador expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado izquierdo de la comparacion");};

comparador:    MENOR_IGUAL  
            |  MAYOR_IGUAL 
            |  DISTINTO 
            |  '=' 
            |  '<' 
            |  '>' 
           ;

           
asignacion: IDENTIFIER_LIST T_ASIGNACION expresion_list ';' {
    
       
};
        
IDENTIFIER_LIST: T_ID { 
                }
               | IDENTIFIER_LIST ',' T_ID {
               
                }
               | IDENTIFIER_LIST ',' acceso_par {
                }
               | acceso_par  {
                };

expresion_list: expresion {
                }
              | expresion_list ',' expresion {
                };

acceso_par: T_ID '{' T_CTE '}' { 
    // Verificar si el T_CTE es '1' o '2'
    /* */
    if (!($3.equals("1") || $3.equals("2"))) {
        yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Solo se permite 1 o 2 dentro de las llaves.");
    } else {
        $$ = $1 + "{" + $3 + "}";
    }
}; 


goto_statement: GOTO T_ETIQUETA';' | GOTO ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); };

invocacion_funcion: 
      T_ID '(' parametro_real ')' {
      }
    | T_ID '(' parametro_real { System.err.println("Error en línea: " + Lexer.nmrLinea + " Falta cierre de paréntesis en la invocación de la función."); }
    | T_ID parametro_real ')' { System.err.println("Error en línea: " + Lexer.nmrLinea + " Falta apertura de paréntesis en la invocación de la función."); }
    | T_ID '(' ')' { System.err.println("Error en línea: " + Lexer.nmrLinea + " La invocación de la función no puede tener parámetros vacíos."); }
    | T_ID '(' operador ')' { System.err.println("Error en línea: " + Lexer.nmrLinea + " Parámetro no válido en la invocación de la función."); }
    | T_ID operador '(' parametro_real ')' { System.err.println("Error en línea: " + Lexer.nmrLinea + " Nombre de función no válido, se esperaba un identificador."); }
    ;

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
           $$ = -$2;
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
