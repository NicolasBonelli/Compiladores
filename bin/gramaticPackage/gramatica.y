%{
    package gramaticPackage;
    import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.*;
import javax.swing.JFileChooser;
import Paquetecompi.Lexer;
import Paquetecompi.Pair;
import Paquetecompi.SymbolTable;
    
 
    
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
    System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
} 
| bloque_sentencias {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}



bloque_sentencias: BEGIN sentencias END 
                | BEGIN END {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
                | error {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan Delimitador o Bloque de Sentencia");};
                
sentencias:  sentencia
          | sentencias sentencia
          ;

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
         | RET '(' expresion ')' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
         | RET '('  ')' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
         ;


declaracion: tipo lista_var ';' { 
    List<ParserVal> variables = new ArrayList<ParserVal>();
    variables.add(val_peek(1)); 
    
    for (ParserVal variable : variables) {
        /* Verificar si la variable ya existe en la tabla de simbolos*/
        if (!st.hasKey(variable.toString())) {
            System.out.println("Aclaracion, la tabla de simbolos no contenia la variable: " + variable.toString());
        } else {
            /* Actualiza el tipo de la variable si ya esta en la tabla de simbolos*/
            boolean actualizado = st.updateType(variable.toString(), val_peek(2).toString());
            if (actualizado) {
                System.out.println("Tipo de la variable '" + variable + "' actualizado a: " + val_peek(2));
            } else {
                System.err.println("Error al actualizar el tipo de la variable: " + variable);
            }
        }
    }
} |tipo lista_var error {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
  |tipo ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}; 



lista_var: lista_var ',' T_ID 
  | T_ID 
  |lista_var  T_ID { System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
  ;


declaracion_funcion:
    tipo FUN T_ID '(' parametro ')' bloque_sentencias 
    | tipo FUN T_ID '(' parametros_error ')' bloque_sentencias {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }

    
    | tipo FUN T_ID '(' tipo ')' bloque_sentencias {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }

    | tipo T_ID '(' tipo T_ID ')' bloque_sentencias {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }

    | tipo FUN '(' tipo T_ID ')' bloque_sentencias {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    };

parametro:
    tipo T_ID 

parametros_error:
    parametro ',' parametro {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
    | parametros_error ','  parametro{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
    | /* vacio */ {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion debe tener un parametro.");
    };

repeat_sentencia: bloque_sentencias 
            | sentencia
            ;



tipo: DOUBLE { yyval.sval = "double"; }
    | LONGINT { yyval.sval = "longint"; }
    | T_ID
    {
        
        /* Verificando si el tipo esta en la tabla de tipos definidos*/
        
        if (tablaTipos.containsKey(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0));
        } 
    };
    


if_statement: IF '(' condicion ')' THEN repeat_sentencia END_IF ';'
            | IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF ';'
            | IF '(' condicion ')' THEN repeat_sentencia repeat_sentencia END_IF ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
            | IF '(' condicion ')' THEN repeat_sentencia END_IF {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
            | IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF  {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
            
            | IF '('  ')' THEN repeat_sentencia END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
            | IF '('  ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }

            | IF '('condicion  ')'  repeat_sentencia END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
            | IF '(' condicion ')'  repeat_sentencia ELSE repeat_sentencia END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
            
             | IF '('condicion  ')' THEN END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
            | IF '(' condicion ')' THEN   ELSE  END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
            | IF  condicion  THEN repeat_sentencia END_IF ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
            | IF  condicion  THEN repeat_sentencia ELSE repeat_sentencia END_IF ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
            | IF '(' condicion ')' THEN repeat_sentencia error ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
            | IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia error ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
            ;
            
            


repeat_while_statement: REPEAT repeat_sentencia WHILE '(' condicion ')' ';'
    | REPEAT repeat_sentencia WHILE '(' condicion ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
    | REPEAT WHILE '(' condicion ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
    | REPEAT repeat_sentencia WHILE '('  ')' ';'{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
    | REPEAT repeat_sentencia WHILE  condicion  ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
    | REPEAT repeat_sentencia error '(' condicion ')' ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
    ;




salida: OUTF '(' T_CADENA ')' ';' 
      | OUTF '(' expresion ')' ';' 
      | OUTF '(' expresion ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
      | OUTF '(' T_CADENA ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      } 
      | OUTF '('  ')' ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}  
      | OUTF '(' sentencia ')' ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");};


sentencia_declarativa_tipos: TYPEDEF T_ID T_ASIGNACION tipo subrango ';' {
        
        
        // Obtener el nombre del tipo desde T_ID
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        // Obtener el tipo base (INTEGER o SINGLE)
        String tipoBase = val_peek(2).sval;
        
        /* tipo base (INTEGER o SINGLE)*/
        
        double limiteInferior = val_peek(4).dval; /* Limite inferior */
        
        
        double limiteSuperior =  val_peek(5).dval; /* Limite superior */
        
        // Almacenar en la tabla de tipos
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        
        }
        | TYPEDEF PAIR '<' LONGINT '>' T_ID ';' {
             String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (LONGINT)*/
            String tipoBase = val_peek(3).sval;
            
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -2147483647, 2147483647));
        }
        | TYPEDEF PAIR '<' DOUBLE '>' T_ID ';' {
            String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (DOUBLE)*/
            String tipoBase = val_peek(3).sval;
            
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
        | TYPEDEF PAIR '<'  '>' T_ID ';' {
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
        | TYPEDEF PAIR  DOUBLE  T_ID ';' {
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
        | TYPEDEF PAIR  LONGINT  T_ID ';' {
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
        | TYPEDEF T_ID T_ASIGNACION tipo subrango {
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
        | TYPEDEF PAIR '<' T_ID '>' T_ID ';' {
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
        | TYPEDEF PAIR '<' LONGINT '>' T_ID {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
        | TYPEDEF PAIR '<' DOUBLE '>' T_ID {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");};
        | TYPEDEF  '<' LONGINT '>' T_ID ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
        | TYPEDEF  '<' DOUBLE '>' T_ID ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");};
        | TYPEDEF PAIR '<' LONGINT '>'  ';' { System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
        | TYPEDEF PAIR '<' DOUBLE '>'  ';' { System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
        | TYPEDEF  T_ASIGNACION tipo subrango ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
        | TYPEDEF T_ID T_ASIGNACION  subrango ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
        | TYPEDEF T_ID  T_ASIGNACION tipo  ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}

subrango: '{' T_CTE ',' T_CTE '}'{
        
        //CODIGO PARA PARTE SEMANTICA
        
        String limiteInferiorStr = val_peek(3).sval; // T_CTE (limites inferiores)
        String limiteSuperiorStr = val_peek(1).sval; // T_CTE (limites superiores)

        
        

        try {
           
            double limiteInferior = Double.parseDouble(limiteInferiorStr);
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);

            
            yylval.obj = new Subrango(limiteInferior, limiteSuperior);
            
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
        }
    } 
    |'{' '-' T_CTE ',' T_CTE '}'
    |'{' T_CTE ',' '-' T_CTE '}'
    |'{' '-' T_CTE ',' '-' T_CTE '}'
    |'{' '}'{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
    |error {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    };


condicion: expresion comparador expresion 
         | expresion error expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
         | expresion comparador {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
         | comparador expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
         ;

comparador:MENOR_IGUAL  
        |MAYOR_IGUAL 
        |DISTINTO 
        |'=' 
        |'<' 
        |'>' 
        ;

           
asignacion: IDENTIFIER_LIST T_ASIGNACION expresion_list error{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
        | IDENTIFIER_LIST T_ASIGNACION expresion_list ';'
        | IDENTIFIER_LIST T_ASIGNACION ';'{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
        ;

expresion_list: expresion
        | expresion_list ',' expresion
        ;
             

IDENTIFIER_LIST:IDENTIFIER_LIST ',' T_ID 
            | IDENTIFIER_LIST ',' acceso_par 
            | T_ID 
            | acceso_par  
            | acceso_par error acceso_par  { System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
            | T_ID error acceso_par  { System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
            | acceso_par error T_ID { System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
            | error { System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
            ;


acceso_par: 
    T_ID '{' T_CTE '}' {
            
        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
        }
        
    }
    |T_ID '{' error '}'{System.err.println("Error en linea: " + Lexer.nmrLinea + " Solo se puede acceder a un par con 1 o 2");}
    |T_ID T_CADENA {System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
    ;


goto_statement: GOTO T_ETIQUETA';' | GOTO ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
              | GOTO T_ETIQUETA {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
              | GOTO error {System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");};


invocacion_funcion: T_ID '(' parametro_real ')' 
      | T_ID '(' error ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
      ; 

parametro_real: expresion_aritmetica ; 

expresion_aritmetica: expresion_aritmetica '+' expresion_aritmetica 
         | expresion_aritmetica '-' expresion_aritmetica 
         | expresion_aritmetica '*' expresion_aritmetica 
         | expresion_aritmetica '/' expresion_aritmetica 
         | T_CTE 
         | T_ID 
         | acceso_par
         | unaria 
       ;

expresion:expresion '+' expresion 
        |expresion '-' expresion 
        |expresion '*' expresion 
        |expresion '/' expresion 
        |T_CTE 
        |T_ID 
        |acceso_par
        | invocacion_funcion
        | unaria 
        | error {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
        ;

unaria: '-' T_CTE { // Esta regla maneja especificamente el '-' unario
    double valor = val_peek(0).dval;  
      

    String nombreConstante = val_peek(0).sval;  
    String nombreConMenos = "-" + nombreConstante;
    /* verificacion en la tabla de simbolos.*/
    if (st.hasKey(nombreConstante)) {
        String tipo = st.getType(nombreConstante);  /*  tipo de la constante.*/
        if (tipo != null) {
            /* Verifica si el valor original (sin negativo) esta en el rango adecuado segun el tipo.*/
            if (tipo.equals("longint")) {
                if (!lexer.isLongintRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para longint.");
                } else {
                	
                    
                    st.addValue(nombreConMenos, tipo,SymbolTable.constantValue);
                }
            } else if (tipo.equals("double")) {
                if (!lexer.isDoubleRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para double.");
                } else {
                    
                    st.addValue(nombreConMenos, tipo, SymbolTable.constantValue);
                }
            }
        } else {
            System.err.println("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else {
        System.err.println("Error: La constante " + nombreConstante + " no existe en la tabla de simbolos.");
    }
};

%%

public void yyerror(String s) {
    System.err.println("Error en linea: " + Lexer.nmrLinea + " String: " +s);
  }

int yylex() {
    try {
        Pair token = lexer.analyze(reader);  
        //System.out.println("Pair: "+ token);
        if (token != null) {
            //System.out.println("Token: " + token.getLexema() + " :: " + token.getToken());

            
            if (token.getToken() == 277 || token.getToken() == 278 || token.getToken() == 279 || token.getToken() == 280) { //SI SE TRATA DE UN TOKEN QUE TIENE MUCHAS REFERENCIAS EN TABLA DE SIMBOLOS
                yylval = new ParserVal(token.getLexema());
            }
            if(token.getToken()<31) { //SI SE TRATA DE UN TOKEN DE UN SIMBOLO SINGULAR ESPECIFICO EN LA TABLA DE SIMBOLOS
            	
            	char character = token.getLexema().charAt(0);  
                //System.out.println("Character:" + character);
            	int ascii = (int) character;
                return ascii;
            	
            }

            return token.getToken();  // Devuelve el token al parser
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return 0;  // fin de archivo
}


public static void main(String[] args) {
    // Crear un JFileChooser para seleccionar el archivo
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);  // Muestra el cuadro de diálogo

    if (result == JFileChooser.APPROVE_OPTION) {  // Si el usuario selecciona un archivo
        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();  // Obtener la ruta del archivo seleccionado

        // Instanciar el Parser con la ruta seleccionada
        Parser parser = new Parser(filePath);

        // Ejecutar el compilador
        parser.run();
        parser.imprimirSymbolTable();
    } else {
        System.out.println("No se seleccionó ningún archivo.");
    }
}


 // Funcion para verificar si el valor esta dentro del rango
 boolean verificarRango(String tipo, double valor) {
    if (tablaTipos.containsKey(tipo)) {
        TipoSubrango subrango = tablaTipos.get(tipo);
        return valor >= subrango.limiteInferior && valor <= subrango.limiteSuperior;
    }
    return true; // Si no es un tipo definido por el usuario, no se verifica el rango
}


boolean verificarRangoLongInt(double valor) {
    return valor >= -Math.pow(2, 31) && valor <= Math.pow(2, 31) - 1;
}

boolean verificarRangoDouble(double valor) {
    return valor >= -1.7976931348623157e308 && valor <= 1.7976931348623157e308;
}

String obtenerTipo(String variable) {
    
    if (!st.hasKey(variable)) return variable;

    return st.getType(variable);  
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
    public void imprimirSymbolTable() {
	System.out.println(this.st);
    }

