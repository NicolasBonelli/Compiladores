%{
    package gramaticPackage;
    import java.util.*;
    import gramaticPackage.*;
    
    // Definir la tabla de símbolos para los tipos definidos por el usuario.
    Map<String, TipoSubrango> tablaTipos = new HashMap<>();
    SymbolTable st = new SymbolTable();
    Lexer lexer = new Lexer(st);
    lexer.analyze("C:\\Users\\hecto\\OneDrive\\Escritorio\\prueba.txt");
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
        if (!tablaDeSimbolos.containsKey(variable)) return variable;

        return tablaDeSimbolos.get(variable).tipo;  // Ejemplo
    }

    public Parser(String ruta)
{
	tablaTipos = new HashMap<>();
	st = new SymbolTable();
	lexer = new Lexer(st);
	System.out.println("SALI DEL LEXER");
}
    
%}

%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET REPEAT WHILE PAIR GOTO
%token LONGINT DOUBLE MENOR_IGUAL MAYOR_IGUAL DISTINTO T_ASIGNACION
%token T_CADENA T_ID T_CTE T_ETIQUETA
%left MENOR_IGUAL MAYOR_IGUAL DISTINTO '='
%left '+' '-'
%left '*' '/'

%%

programa: T_ID bloque_sentencias {  
    System.out.println("Programa compilado correctamente");
};

bloque_sentencias: BEGIN sentencias END {System.out.println("Llegue a BEGIN sentencia END")};

sentencias: sentencias sentencia 
          | sentencia {System.out.println("Llegue a sentencias")};

sentencia: declaracion 
         | asignacion
         | if_statement
         | condicion
         | repeat_while_statement
         | salida
         | invocacion_funcion
         | declaracion_funcion
         | goto_statement
         | sentencia_declarativa_tipos
         {System.out.println("Llegue a sentencia")};

declaracion: tipo lista_var ';' { 
    System.out.println("Llegue a declaracion");
    List<String> variables = (List<String>)$2; // Asume que lista_var devuelve una lista de variables
    
    for (String variable : variables) {
        // Verificar si la variable ya existe en la tabla de símbolos
        if (!st.hasKey(variable)) {
            System.out.println("ERROR, la tabla de símbolos no contenía la variable: " + variable);
        } else {
            // Actualiza el tipo de la variable si ya está en la tabla de símbolos
            boolean actualizado = st.updateType(variable, $1);
            if (actualizado) {
                System.out.println("Tipo de la variable '" + variable + "' actualizado a: " + $1);
            } else {
                System.out.println("Error al actualizar el tipo de la variable: " + variable);
            }
        }
    }
};

declaracion_funcion:
      tipo FUN T_ID '(' tipo T_ID ')' BEGIN cuerpo_funcion END {System.out.println("declaracion_funcion")};

cuerpo_funcion:
    cuerpo_funcion sentencias_funcion
    | sentencias_funcion {System.out.println("Llegue a cuerpo_funcion")}
    ;


sentencias_funcion:
    sentencia | RET '(' expresion ')' ';'  {System.out.println("Llegue a sentencia_funcion")}
    ;



lista_var: lista_var ',' T_ID { 
    System.out.println("Llegue a lista_var 1");
    // Si ya tenemos una lista de variables, añadimos la nueva variable
    List<String> variables = (List<String>)$1;
    variables.add($3);
    $$ = variables;  // Devolvemos la lista actualizada
}
| T_ID { 
    System.out.println("Llegue a lista_var 2");
    // Creamos una nueva lista con la primera variable
    List<String> variables = new ArrayList<>();
    variables.add($1);
    $$ = variables;  // Devolvemos la lista
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
            yyerror("Tipo no definido: " + $1);
        }
    }
    ;
    
    

if_statement: IF '(' condicion ')' THEN bloque_sentencias END_IF ';'
            | IF '(' condicion ')' THEN sentencia END_IF ';'
            | IF '(' condicion ')' THEN bloque_sentencias ELSE sentencia END_IF ';'
            | IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';'
            | IF '(' condicion ')' THEN sentencia ELSE bloque_sentencias END_IF ';'
            | IF '(' condicion ')' THEN sentencia ELSE sentencia END_IF ';'
            {        System.out.println("Llegue a if_statement");        }
            ;

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

        $$ = new Subrango(Double.parseDouble($2), Double.parseDouble($4));
    }
    ;


condicion: expresion MENOR_IGUAL expresion { System.out.println("Llegue a MENOR_IGUAL");
}
           | expresion MAYOR_IGUAL expresion
           | expresion DISTINTO expresion
           | expresion '=' expresion
           | expresion '<' expresion
           | expresion '>' expresion
           | invocacion_funcion MENOR_IGUAL expresion
           | invocacion_funcion MAYOR_IGUAL expresion
           | invocacion_funcion DISTINTO expresion
           | invocacion_funcion '=' expresion
           | invocacion_funcion '<' expresion
           | invocacion_funcion '>' expresion
           | expresion MENOR_IGUAL invocacion_funcion
           | expresion MAYOR_IGUAL invocacion_funcion
           | expresion DISTINTO invocacion_funcion
           | expresion '=' invocacion_funcion
           | expresion '<' invocacion_funcion
           | expresion '>' invocacion_funcion
           | invocacion_funcion MENOR_IGUAL invocacion_funcion
           | invocacion_funcion MAYOR_IGUAL invocacion_funcion
           | invocacion_funcion DISTINTO invocacion_funcion
           | invocacion_funcion '=' invocacion_funcion
           | invocacion_funcion '<' invocacion_funcion
           | invocacion_funcion '>' invocacion_funcion
           ;


repeat_while_statement: REPEAT bloque_sentencias WHILE '(' condicion ')' ';' {System.out.println("Llegue a repeat_while");}
           | REPEAT sentencia WHILE '(' condicion ')' ';'
           ;

asignacion: IDENTIFIER_LIST T_ASIGNACION expresion_list ';' {
    if ($1.size() != $3.size()) {
        yyerror("Error: El número de variables no coincide con el número de expresiones.");
    } else {
        // Verificar si el valor asignado está dentro del rango
        for (int i = 0; i < $1.size(); i++) {
            String variable = (String) $1.get(i);  // IDENTIFIER_LIST
            Object valorAsignado = $3.get(i);      // expresion_list (valor)

            // Obtener el tipo de la variable
            String tipoVariable = obtenerTipo(variable); // Implementa obtenerTipo() para encontrar el tipo de la variable

            // Verificar si es un tipo definido por el usuario
            if (tablaTipos.containsKey(tipoVariable)) {
                if (!verificarRango(tipoVariable, valorAsignado)) {
                    yyerror("Valor fuera del rango para el tipo: " + tipoVariable);
                } else {
                    // Asignar valor a la variable en la tabla de símbolos
                    st.update(variable, valorAsignado);
                }
            } else {
                // Verificar los rangos de los tipos estándar
                if (tipoVariable.equals("longint") && !verificarRangoLongInt((Long) valorAsignado)) {
                    yyerror("Valor fuera del rango para el tipo longint");
                } else if (tipoVariable.equals("double") && !verificarRangoDouble((Double) valorAsignado)) {
                    yyerror("Valor fuera del rango para el tipo double");
                } else if (tipoVariable.equals("int") && !verificarRangoInt((Integer) valorAsignado)) {
                    yyerror("Valor fuera del rango para el tipo int");
                } else {
                    yyerror("Tipo no declarado antes para la variable: " + variable);
                }
            }
        }
    }
       
};
        
IDENTIFIER_LIST: T_ID { $$ = new ArrayList<>();
                        $$.add($1);
                }
               | IDENTIFIER_LIST ',' T_ID {
                $1.add($3);
                $$ = $1;
                }
               | IDENTIFIER_LIST ',' acceso_par {
                $1.add($3);
                $$ = $1;
                }
               | acceso_par  {
                $$ = new ArrayList<>();
                $$.add($1);
                };

expresion_list: expresion {
                $$ = new ArrayList<>();
                $$.add($1);
              }
              | expresion_list ',' expresion {
                $1.add($3);
                $$ = $1;
              }
              | expresion_list ',' invocacion_funcion {
                $1.add($3);
                $$ = $1;
              }
              | invocacion_funcion {
                $$ = new ArrayList<>();
                $$.add($1);
              };

acceso_par: T_ID '{' '1' '}' { $$ = $1 + "{1}"; } 
          | T_ID '{' '2' '}' { $$ = $1 + "{2}"; };


goto_statement: GOTO T_ETIQUETA';';

invocacion_funcion: T_ID '(' parametro_real ')' ';';

parametro_real: expresion ; 

expresion: expresion '+' expresion {
            $$ = $1 + $3;  // Asegúrate de manejar los tipos correctamente
        }
         | expresion '-' expresion {
            $$ =  $1 -  $3;
        }
         | expresion '*' expresion {
            $$ =  $1 *  $3;
        }
         | expresion '/' expresion {
            $$ =  $1 /  $3;
        }
         | T_CTE {
            $$ = $1;  // Suponiendo que T_CTE es un número constante
        }
         | T_ID {
            $$ = st.hasKey($1);  // Buscar el valor del identificador en la tabla de símbolos
        }
         | acceso_par{
            $$ = st.hasKey($1);  // Manejar accesos como T_ID{1}
        };

%%


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


