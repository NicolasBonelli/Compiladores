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

bloque_sentencias: BEGIN sentencias END;

sentencias: sentencias sentencia
          | sentencia;

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
         ;

declaracion: tipo lista_var ';' { 
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
      tipo FUN T_ID '(' tipo T_ID ')' BEGIN cuerpo_funcion END;

cuerpo_funcion:
    cuerpo_funcion sentencias_funcion
    | sentencias_funcion
    ;


sentencias_funcion:
    sentencia | RET '(' expresion ')' ';' 
    ;



lista_var: lista_var ',' T_ID { 
    // Si ya tenemos una lista de variables, añadimos la nueva variable
    List<String> variables = (List<String>)$1;
    variables.add($3);
    $$ = variables;  // Devolvemos la lista actualizada
}
| T_ID { 
    // Creamos una nueva lista con la primera variable
    List<String> variables = new ArrayList<>();
    variables.add($1);
    $$ = variables;  // Devolvemos la lista
};

tipo: DOUBLE { $$ = "double"; }
    | LONGINT { $$ = "longint"; }
    | T_ID
    {
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
            ;

salida: OUTF '(' T_CADENA ')' ';'
      | OUTF '(' expresion ')' ';';


sentencia_declarativa_tipos: TYPEDEF T_ID T_ASIGNACION tipo subrango ';'
        | TYPEDEF PAIR '<' LONGINT '>' T_ID ';'
        | TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'
        {
        // Guardar el nuevo tipo en la tabla de símbolos
        String nombreTipo = $2; // T_ID
        String tipoBase = $4; // tipo base (INTEGER o SINGLE)
        double limiteInferior = Double.parseDouble($5); // Limite inferior del subrango
        double limiteSuperior = Double.parseDouble($6); // Limite superior del subrango

        // Almacenar en la tabla de símbolos
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
    };

subrango: '{' T_CTE ',' T_CTE '}'{
        $$ = new Subrango(Double.parseDouble($2), Double.parseDouble($4));
    }
    ;


condicion: expresion MENOR_IGUAL expresion
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


repeat_while_statement: REPEAT bloque_sentencias WHILE '(' condicion ')' ';'
           | REPEAT sentencia WHILE '(' condicion ')' ';'
           ;

asignacion: IDENTIFIER_LIST T_ASIGNACION expresion_list ';' {

    if ($1.size() != $3.size()){
        yyerror("Error: El número de variables no coincide con el número de expresiones.");

    } else {

        // Verificar si el valor asignado está dentro del rango
        for (int i = 0; i < $1.size(); i++) {
            String variable = $1.get(i);  // IDENTIFIER_LIST
            double valorAsignado = $3.get(i);  // expresion_list (valor)

            // Obtener el tipo de la variable
            String tipoVariable = obtenerTipo(variable); // Implementa obtenerTipo() para encontrar el tipo de la variable

            // Verificar si es un tipo definido por el usuario
            if (tablaTipos.containsKey(tipoVariable)) {
                if (!verificarRango(tipoVariable, valorAsignado)) {
                    yyerror("Valor fuera del rango para el tipo: " + tipoVariable);
                }
            } else {
                // Verificar los rangos de los tipos estándar
                if (tipoVariable.equals("longint") && !verificarRangoLongInt(valorAsignado)) {
                    yyerror("Valor fuera del rango para el tipo longint");
                } else if (tipoVariable.equals("double") && !verificarRangoDouble(valorAsignado)) {
                    yyerror("Valor fuera del rango para el tipo double");
                } else yyerror("Tipo no declarado antes");

            }
        }
    }
        
};
        
IDENTIFIER_LIST: T_ID
               | IDENTIFIER_LIST ',' T_ID;
               | IDENTIFIER_LIST ',' acceso_par
               | acceso_par;

expresion_list: expresion
              | expresion_list ',' expresion
              | expresion_list ',' invocacion_funcion
              | invocacion_funcion;

acceso_par: T_ID '{' '1' '}' { $$ = $1 + "{1}"; }
          | T_ID '{' '2' '}' { $$ = $1 + "{2}"; };


goto_statement: GOTO T_ETIQUETA';';

invocacion_funcion: T_ID '(' parametro_real ')' ';';

parametro_real: expresion ; 

expresion: expresion '+' expresion
         | expresion '-' expresion
         | expresion '*' expresion
         | expresion '/' expresion
         | T_CTE
         | T_ID
         | acceso_par;

%%

int yylex() {
   	lexer.getToken();
}

public static void main(String[] args) {
	Parser parser= new Parser();
    parser.run();
        
}