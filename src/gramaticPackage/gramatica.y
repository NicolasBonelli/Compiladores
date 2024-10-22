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
import Paquetecompi.TipoEtiqueta;    
 
   

class Subrango{
    private double limiteSuperior;
    private double limiteInferior;
    
    public Subrango(double limiteInferior, double limiteSuperior) {
        this.limiteSuperior = limiteSuperior; this.limiteInferior = limiteInferior;
    }
    public double getLimiteInferior() {
        return limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    @Override
    public String toString(){
      return "Limite inferior: "+ limiteInferior + " - Limite Superior: "+ limiteSuperior;
    }
}





%}

%token IF THEN ELSE BEGIN END END_IF OUTF TYPEDEF FUN RET REPEAT WHILE PAIR GOTO
%token LONGINT DOUBLE MENOR_IGUAL MAYOR_IGUAL DISTINTO T_ASIGNACION
%token T_CADENA T_ID T_CTE T_ETIQUETA
%left '+' '-'
%left '*' '/'
%nonassoc error
 
%%


programa: nombre bloque_sentencias {
    System.out.println("Programa compilado correctamente");
    //updatear uso nombre funcion
    st.updateUse(val_peek(1).sval, "Nombre de programa");
    
    
} 
| T_ID { 
    System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
} 
| bloque_sentencias {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}



bloque_sentencias: BEGIN sentencias END 
                | BEGIN END {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
                ;
                
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
         | T_ETIQUETA {
            
            if(st.containsTypeGotos(new TipoEtiqueta(val_peek(0).sval,null,null))){
                int posicion = st.popFirstOccurrenceByNameGotos(val_peek(0).sval);
                if(posicion != -1){
                    SymbolTable.polaca.set(posicion,String.valueOf(SymbolTable.polaca.size()+1));
                }
            }else{
                st.aggPilaEtiquetas(new TipoEtiqueta(val_peek(0).sval,SymbolTable.polaca.size(),SymbolTable.ambitoGlobal.toString()));
            }
            SymbolTable.aggPolaca(val_peek(0).sval);

            if(st.contieneSymbolAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal)){
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
            }else{
                if(st.getAmbitoByKey(val_peek(0).sval).equals(" ")){
                    st.updateAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(val_peek(0).sval,"String","Nombre de variable",SymbolTable.ambitoGlobal.toString(), 280);
                }
            }
         }
         | RET '(' expresion ')' ';'
         | RET '(' expresion ')' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
         | RET '('  ')' ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
         ;


declaracion: tipo lista_var ';' { 
    List<String> variables = (List<String>) val_peek(1).obj;  // Obtener la lista de variables de lista_var
    System.out.println("vars:"+variables);
	for (String variable : variables) {
	    /* Verificar si la variable ya existe en la tabla de símbolos */
	    if (st.hasKey(variable)) {
	        System.out.println("Aclaracion, se declaro la variable: " + variable);
            
            //updatear uso de variable a variable
            if(st.isTypePair(val_peek(2).sval)){//si el tipo
                st.updateUse(variable, "Nombre de variable par");
            }else{
	            st.updateUse(variable, "Nombre de variable");
            }


            if(st.contieneSymbolAmbito(variable,SymbolTable.ambitoGlobal)){
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
            }else{
                if(st.getAmbitoByKey(variable).equals(" ")){
                    st.updateAmbito(variable,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(variable,val_peek(2).sval,"Nombre de variable",SymbolTable.ambitoGlobal.toString(), 278);
                }
            }
            //updatear tipo de variable
            st.updateType(variable,SymbolTable.ambitoGlobal.toString(), val_peek(2).sval);
            
	    } else {
	        System.err.println("Error, la variable no está en la tabla de símbolos: " + variable);
	    }
	}
} |tipo lista_var error {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
  |tipo ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}; 



lista_var: lista_var ',' T_ID {
    
    @SuppressWarnings("unchecked")
    List<String> variables = (List<String>) val_peek(2).obj;
    variables.add(val_peek(0).sval);  /* Agregar nueva variable*/
    yyval.obj = variables;  /* Pasar la lista actualizada hacia arriba */
} 
  | T_ID {
    List<String> variables = new ArrayList<String>();
    variables.add(val_peek(0).sval);  /* Agregar la primera variable*/
    yyval.obj = variables; 
} 
  |lista_var T_ID { System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
  ;


nombre: T_ID { yyval.sval = val_peek(0).sval;
    System.out.println("Entre a Funcion antes (o despues?) de la derecha");

    if (SymbolTable.ambitoGlobal.length() == 0) {
        SymbolTable.ambitoGlobal = new StringBuilder(val_peek(0).sval);
    } else SymbolTable.ambitoGlobal.append(":" + val_peek(0).sval);
        };

declaracion_funcion:
    tipo FUN nombre  '(' parametro ')' bloque_sentencias {
        
        System.out.println("Entre a la 2da llave");
        //updatear uso nombre funcion
        st.updateUse(val_peek(4).sval, "Nombre de funcion");
        

        // Separar el tipo y el nombre del parámetro
        String[] tipoYNombre = val_peek(2).sval.split(":");
        String tipoParametro = tipoYNombre[0];
        String nombreParametro = tipoYNombre[1];

        if(st.contieneSymbolAmbito(val_peek(4).sval,SymbolTable.ambitoGlobal)){
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar funciones en el mismo ambito. Error con el nombre de la funcion:"+val_peek(4).sval);
        }else{
            if(st.getAmbitoByKey(val_peek(4).sval).equals(" ")){
                StringBuilder ambitoOrig= new StringBuilder(borrarUltimoAmbito());
                st.updateAmbito(val_peek(4).sval,ambitoOrig);
            }else{
                st.addValue(val_peek(4).sval,"String","Nombre de funcion",SymbolTable.ambitoGlobal.toString(), 278);
            }
            // Insertar en la tabla de funciones
            st.insertTF(val_peek(4).sval+":"+this.borrarUltimoAmbito(), new CaracteristicaFuncion(val_peek(6).sval, tipoParametro, nombreParametro)); 
        }
        
        // Encuentra el índice donde empieza "Gato"
        int inicio = st.ambitoGlobal.indexOf(":" + val_peek(4).sval);

        // Si la palabra a borrar existe en el StringBuilder, elimínala
        if (inicio != -1) {
            st.ambitoGlobal.delete(inicio, inicio + val_peek(4).sval.length()+1);
        }


    }
    | tipo FUN nombre '(' parametros_error ')' bloque_sentencias {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }
    
    
    | tipo FUN nombre '(' tipo ')' bloque_sentencias {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }

    | tipo nombre '(' tipo T_ID ')' bloque_sentencias {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }

    | tipo FUN '(' tipo T_ID ')' bloque_sentencias {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    }
    | tipo FUN nombre '(' parametro ')' bloque_sentencias ';'{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se puede poner ; al final de la declaracion de una fucnion");
    };

parametro:
    tipo T_ID {
        
        //updatear uso de variable a variable
        if(st.isTypePair(val_peek(1).sval)){//si el tipo
            st.updateUse(val_peek(0).sval, "Nombre de variable par");
        }else{
            st.updateUse(val_peek(0).sval, "Nombre de parametro");
        }
        if(st.contieneSymbolAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal)){
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
        }else{
            if(st.getAmbitoByKey(val_peek(0).sval).equals(" ")){
                st.updateAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal);
            }else{
                st.addValue(val_peek(0).sval,val_peek(1).sval,"Nombre de parametro",SymbolTable.ambitoGlobal.toString(), 278);
            }
        }
        yyval.sval = val_peek(1).sval + ":" + val_peek(0).sval;
        
    }

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
        
        if (st.containsKeyTT(val_peek(0).sval+":"+SymbolTable.ambitoGlobal.toString())) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0).sval);
        } 
    };
    

bloque_THEN: THEN repeat_sentencia {
    
        int posicion = SymbolTable.pila.pop();
        SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));
        SymbolTable.pila.push(SymbolTable.polaca.size());


};


bloque_THEN_CON_ELSE: THEN repeat_sentencia {
    
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()+2));
    SymbolTable.pila.push(SymbolTable.polaca.size());
    SymbolTable.aggPolaca(""); SymbolTable.aggPolaca("BI");

};
bloque_ELSE: ELSE repeat_sentencia {
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));

};



if_statement: IF '(' condicion ')' bloque_THEN END_IF ';' 
            | IF '(' condicion ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF ';'
            | IF '(' condicion ')' bloque_THEN_CON_ELSE repeat_sentencia END_IF ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
            | IF '(' condicion ')' bloque_THEN END_IF {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
            | IF '(' condicion ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF  {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
            
            | IF '('  ')' bloque_THEN END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
            | IF '('  ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }

            | IF '('condicion ')'  repeat_sentencia END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
            | IF '(' condicion ')'  repeat_sentencia bloque_ELSE END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
            
             | IF '('condicion  ')' THEN END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
            | IF '(' condicion ')' THEN   ELSE  END_IF ';' {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
            | IF  condicion  bloque_THEN END_IF ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
            | IF  condicion  bloque_THEN_CON_ELSE bloque_ELSE END_IF ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
            | IF '(' condicion ')' bloque_THEN error ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
            | IF '(' condicion ')' bloque_THEN_CON_ELSE bloque_ELSE error ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
            
            ;
            
            
inicio_while: REPEAT {SymbolTable.pila.push(SymbolTable.polaca.size());};

repeat_while_statement: inicio_while repeat_sentencia WHILE '(' condicion ')' ';' {
    SymbolTable.aggPolaca(""); SymbolTable.aggPolaca("BI");
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));
    SymbolTable.polaca.set(SymbolTable.polaca.size()-2, String.valueOf(SymbolTable.pila.pop()));
    }
    | inicio_while repeat_sentencia WHILE '(' condicion ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
    | inicio_while WHILE '(' condicion ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
    | inicio_while repeat_sentencia WHILE '('  ')' ';'{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
    | inicio_while repeat_sentencia WHILE  condicion  ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
    | inicio_while repeat_sentencia error '(' condicion ')' ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
    ;




salida: OUTF '(' T_CADENA ')' ';' 
      | OUTF '(' expresion ')' ';' 
      | OUTF '(' expresion ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
      | OUTF '(' T_CADENA ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      } 
      | OUTF '(' sentencia ')' ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
      | OUTF '(' ')' ';'  {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta contenido en el OUTF");}
      ;



sentencia_declarativa_tipos: TYPEDEF T_ID T_ASIGNACION tipo subrango ';' { 

        System.out.println("2do");
        // Obtener el nombre del tipo desde T_ID
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        String tipoBase = val_peek(2).sval;
        
        Subrango subrango = (Subrango) val_peek(1).obj;
    
        double limiteInferior = subrango.getLimiteInferior(); /* Limite inferior */
        double limiteSuperior = subrango.getLimiteSuperior(); /* Limite superior */
        // Almacenar en la tabla de tipos

        if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(4).sval);
        }else{

                //FALTA CHEQUEAR MISMO TIPO
            if (tipoBase.toLowerCase().equals("longint")){
                long limiteInferiorLong = (long) limiteInferior; // Convertir a longint
                long limiteSuperiorLong = (long) limiteSuperior; // Convertir a longint
                this.st.insertTT(nombreTipo+":"+SymbolTable.ambitoGlobal.toString(), new TipoSubrango(tipoBase, limiteInferiorLong, limiteSuperiorLong));

            } else this.st.insertTT(nombreTipo+":"+SymbolTable.ambitoGlobal.toString(), new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
            //updatear uso
            st.updateUse(nombreTipo, "Nombre de tipo");

            if(st.getAmbitoByKey(nombreTipo).equals(" ")){
                st.updateAmbito(nombreTipo,SymbolTable.ambitoGlobal);
            }else{
                st.addValue(nombreTipo,"String","Nombre de tipo",SymbolTable.ambitoGlobal.toString(), 278);
            }
        }

        }
        | TYPEDEF PAIR '<' LONGINT '>' T_ID ';' {
            String nombreTipo = val_peek(1).sval; /* T_ID*/



            if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(5).sval);
            }else{
    
                //FALTA CHEQUEAR MISMO TIPO
                st.insertTT(nombreTipo+":"+SymbolTable.ambitoGlobal.toString(), new TipoSubrango("longint", -2147483647, 2147483647));

            
                //updatear uso
                st.updateUse(nombreTipo, "Nombre de tipo de par");
    
                if(st.getAmbitoByKey(nombreTipo).equals(" ")){
                    st.updateAmbito(nombreTipo,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(nombreTipo,"String","Nombre de tipo de par",SymbolTable.ambitoGlobal.toString(), 278);
                }
            }

        }
        | TYPEDEF PAIR '<' DOUBLE '>' T_ID ';' {
            String nombreTipo = val_peek(1).sval; /* T_ID*/
            
            

            if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(5).sval);
            }else{
    
                //FALTA CHEQUEAR MISMO TIPO
                st.insertTT(nombreTipo+":"+SymbolTable.ambitoGlobal.toString(), new TipoSubrango("double", -1.7976931348623157E+308, 1.7976931348623157E+308));	
                
            
                //updatear uso
                st.updateUse(nombreTipo, "Nombre de tipo de par");
    
                if(st.getAmbitoByKey(nombreTipo).equals(" ")){
                    st.updateAmbito(nombreTipo,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(nombreTipo,"String","Nombre de tipo de par",SymbolTable.ambitoGlobal.toString(), 278);
                }
            }
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
        | TYPEDEF T_ID tipo subrango ';'{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la asignacion en la definicion de nuevos tipos");}
        ;
subrango: '{' T_CTE ',' T_CTE '}'{
        
        //CODIGO PARA PARTE SEMANTICA

       String limiteInferiorStr = val_peek(3).sval; // T_CTE (limites inferiores)
       String limiteSuperiorStr = val_peek(1).sval; // T_CTE (limites superiores)
        try {
           
            double limiteInferior = Double.parseDouble(limiteInferiorStr);
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);
            System.out.println("limiteInferior: " + limiteInferior);
            System.out.println("limiteSuperior: " + limiteSuperior);

            if (limiteInferior <= limiteSuperior)
                yyval.obj = new Subrango(limiteInferior, limiteSuperior);
            else {
                System.out.println("Aclaración: el limite inferior era mas grande que el superior, fueron invertidos");
                yyval.obj = new Subrango(limiteSuperior, limiteInferior);

            }
            
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
        }
    } 
    |'{' '-' T_CTE ',' T_CTE '}' {
       //CODIGO PARA PARTE SEMANTICA
       String limiteInferiorStr = val_peek(3).sval; // T_CTE (limites inferiores)
       String limiteSuperiorStr = val_peek(1).sval; // T_CTE (limites superiores)
        try {
           
            double limiteInferior = Double.parseDouble(limiteInferiorStr)*-1;
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);
            System.out.println("limiteInferior: " + limiteInferior);
            System.out.println("limiteSuperior: " + limiteSuperior);

            if (limiteInferior <= limiteSuperior)
                yyval.obj = new Subrango(limiteInferior, limiteSuperior);
            else {
                System.out.println("Aclaración: el limite inferior era mas grande que el superior, fueron invertidos");
                yyval.obj = new Subrango(limiteSuperior, limiteInferior);

            }
            
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
        }

    }
    |'{' T_CTE ',' '-' T_CTE '}' {//CODIGO PARA PARTE SEMANTICA
        yyerror("Error: el subrango esta mal declarado, fueron invertidos los rangos");
        String limiteInferiorStr = val_peek(1).sval; // T_CTE (limites inferiores)
        String limiteSuperiorStr = val_peek(4).sval; // T_CTE (limites superiores)
         try {
            
             double limiteInferior = Double.parseDouble(limiteInferiorStr)*-1;
             double limiteSuperior = Double.parseDouble(limiteSuperiorStr);
             System.out.println("limiteInferior: " + limiteInferior);
             System.out.println("limiteSuperior: " + limiteSuperior);
 
             
             yyval.obj = new Subrango(limiteInferior, limiteSuperior);
             
             
         } catch (NumberFormatException e) {
             System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
         }}
    |'{' '-' T_CTE ',' '-' T_CTE '}' {//CODIGO PARA PARTE SEMANTICA
        String limiteInferiorStr = val_peek(4).sval; // T_CTE (limites inferiores)
        String limiteSuperiorStr = val_peek(1).sval; // T_CTE (limites superiores)
         try {
            
             double limiteInferior = Double.parseDouble(limiteInferiorStr)*-1;
             double limiteSuperior = Double.parseDouble(limiteSuperiorStr)*-1;
             System.out.println("limiteInferior: " + limiteInferior);
             System.out.println("limiteSuperior: " + limiteSuperior);
 
             
             if (limiteInferior <= limiteSuperior)
                yyval.obj = new Subrango(limiteInferior, limiteSuperior);
            else {
                System.out.println("Aclaración: el limite inferior era mas grande que el superior, fueron invertidos");
                yyval.obj = new Subrango(limiteSuperior, limiteInferior);

            }
             
             
         } catch (NumberFormatException e) {
             System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
         }}
    |'{' '}'{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
    |error {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    };


condicion: expresion comparador expresion {
    SymbolTable.aggPolaca(val_peek(1).sval);
    SymbolTable.pila.push(SymbolTable.polaca.size()); SymbolTable.aggPolaca("");  SymbolTable.aggPolaca("BF"); 
}
         | expresion error expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
         | expresion comparador {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
         | comparador expresion {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
         ;

comparador:MENOR_IGUAL  {yyval.sval = "<=" ;}
        |MAYOR_IGUAL {yyval.sval = ">=";}
        |DISTINTO {yyval.sval = "!=";}
        |'=' {yyval.sval = "=";}
        |'<' {yyval.sval = "<";}
        |'>' {yyval.sval = ">";}
        ;

           
asignacion: IDENTIFIER_LIST T_ASIGNACION expresion_list error{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
        | IDENTIFIER_LIST T_ASIGNACION expresion_list ';' {
            
            // Obtener las listas de variables y expresiones
            List<String> listaVariables = (List<String>) val_peek(3).obj;
            List<String> listaExpresiones = (List<String>) val_peek(1).obj;
            st.imprimirTablaTipos();
            if (listaVariables != null){ 
                // Verificar si hay más variables que expresiones
                if (listaVariables.size() > listaExpresiones.size()) {
                    System.out.println("Warning: Hay más variables que expresiones. Se asignará 0 a las variables sobrantes.");
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable = listaVariables.get(i).toString();
                        if (i < listaExpresiones.size()) {
                            String expresion= listaExpresiones.get(i).toString();
                            
                            chequeoPares(variable,expresion);                       
                        } else {
                            SymbolTable.aggPolaca("0");
                            
                            System.out.println(listaVariables.get(i).toString() + " := 0;");
                        }

                        for (int j = 0; j < SymbolTable.polaca.size(); j++) {
                            if (SymbolTable.polaca.get(j).equals(" ")) {
                                SymbolTable.polaca.set(j, variable);
                                SymbolTable.polaca.set(j+1, ":=");

                                break;  // Salir del loop una vez que reemplace el primer espacio
                            }
                        }
                    }
                } else if (listaVariables.size() < listaExpresiones.size()) {
                    System.out.println("Warning: Hay más expresiones que variables. Se descartarán las expresiones sobrantes.");
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable= listaVariables.get(i).toString();
                        String expresion= listaExpresiones.get(i).toString();
                        // Buscar el primer espacio vacío y reemplazarlo con la variable
                        for (int j = 0; j < SymbolTable.polaca.size(); j++) {
                            if (SymbolTable.polaca.get(j).equals(" ")) {
                                SymbolTable.polaca.set(j, variable);
                                SymbolTable.polaca.set(j+1, ":=");

                                break;  // Salir del loop una vez que reemplace el primer espacio
                            }
                        }

                        chequeoPares(variable,expresion);
                        
                    }
                } else {
                    // Generar el código para cada asignación correspondiente
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable= listaVariables.get(i).toString();
                        String expresion= listaExpresiones.get(i).toString();
                        System.out.println("expresion: " + expresion);
                        
                        // Buscar el primer espacio vacío y reemplazarlo con la variable
                        for (int j = 0; j < SymbolTable.polaca.size(); j++) {
                            if (SymbolTable.polaca.get(j).equals(" ")) {
                                SymbolTable.polaca.set(j, variable);
                                SymbolTable.polaca.set(j+1, ":=");
                                break;  // Salir del loop una vez que reemplace el primer espacio
                            }
                        }

                        chequeoPares(variable,expresion);
                    }
                }
        }
    }
        | IDENTIFIER_LIST T_ASIGNACION ';'{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
        ;

expresion_list:
        expresion {
            SymbolTable.aggPolaca(" "); SymbolTable.aggPolaca(" ");

           // Crear una nueva lista con una sola expresión
           List<String> lista = new ArrayList<>();
           lista.add(val_peek(0).sval);  // Almacenar la expresión como cadena de texto
           yyval.obj = lista;
        }
    |   expresion_list ',' expresion {
            SymbolTable.aggPolaca(" "); SymbolTable.aggPolaca(" ");

            // Agregar la expresión a la lista existente
            List<String> lista = (List<String>) val_peek(2).obj;
            lista.add(val_peek(0).sval);  // Almacenar la nueva expresión
            yyval.obj = lista;
        }
;
             

IDENTIFIER_LIST:IDENTIFIER_LIST ',' T_ID {
                
                // Agregar el identificador a la lista
                st.esUsoValidoAmbito(val_peek(0).sval);
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yylval.obj = lista;
            }
            | IDENTIFIER_LIST ',' acceso_par{
                 // Agregar acceso_par (acceso a atributos o elementos) a la lista
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            } 
            | T_ID {
                
                st.esUsoValidoAmbito(val_peek(0).sval);
                // Crear lista con el primer identificador
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
            | acceso_par {
                // Crear una nueva lista con acceso_par
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            } 
            | acceso_par error acceso_par  { System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");} //anda
            | T_ID error acceso_par  { System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");} //no anda
            | acceso_par error T_ID { System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");} //anda
            | T_CTE {System.err.println("No puede haber constantes a la izquierda en la asignacion");}
            | IDENTIFIER_LIST ',' T_CTE {System.err.println("No puede haber constantes a la izquierda en la asignacion");}
            ;


acceso_par: 
    T_ID '{' T_CTE '}' {

        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            st.esUsoValidoAmbito(val_peek(3).sval);
            yyval.sval = val_peek(3).sval + "{" + val_peek(1).sval + "}";
            SymbolTable.aggPolaca(val_peek(3).sval + "{" + val_peek(1).sval + "}"); 

        }
        
    }
    |T_ID '{' '}'{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se debe utilizar el indice 1 o 2 para acceder a los pares");}
    |T_ID T_CADENA {System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
    ;


goto_statement: GOTO T_ETIQUETA';' {
            if(st.containsTypeEtiquetas(new TipoEtiqueta(val_peek(1).sval,null,null))){//YA HUBO UNA ETIQUETA DECLARADA ANTES
                int posicion = st.popFirstOccurrenceByNameEtiquetas(val_peek(1).sval);
                if(posicion!=-1){

                    SymbolTable.aggPolaca(String.valueOf(posicion+1));
                }else{
                    SymbolTable.aggPolaca("");
                }
            }else{
                st.aggPilaGotos(new TipoEtiqueta(val_peek(1).sval,SymbolTable.polaca.size(),SymbolTable.ambitoGlobal.toString()));
                SymbolTable.aggPolaca("");
            }
            SymbolTable.aggPolaca("BI");

            st.esUsoValidoAmbito(val_peek(1).sval);
            }
            | GOTO ';' {System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
            | GOTO T_ETIQUETA {System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
            | GOTO error {System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");};


invocacion_funcion: T_ID '(' parametro_real ')' {
        // Verifica que el parámetro no sea nulo antes de intentar convertirlo a cadena
        if (val_peek(1).sval != null) {
            if (st.getUse(val_peek(3).sval) == null) {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Llamado funcion:"+val_peek(3).sval+"  no declarada");
            }
            st.esUsoValidoAmbito(val_peek(3).sval);
            yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
            SymbolTable.aggPolaca(val_peek(3).sval); 

        } else {
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro de función nulo");
            yyval.sval = val_peek(3).sval + "()";  // Asume que no hay parámetros si es nulo
        }
    }
      | T_ID '(' error ')' {
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
      ; 

parametro_real: expresion_aritmetica {
    // Asegúrate de que el valor de la expresión aritmética se pase correctamente hacia arriba
    yyval.sval = val_peek(0).sval;
}; 

expresion_aritmetica:
    expresion_aritmetica '+' expresion_aritmetica {
                if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                    System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
                }
                SymbolTable.aggPolaca("+");
                // Devuelve la expresión como una cadena que representa la suma
                yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
            }
        |   expresion_aritmetica '-' expresion_aritmetica {
                if( (isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                    System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
                }
                SymbolTable.aggPolaca("-");
                // Devuelve la expresión como una cadena que representa la resta
                yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
            }
        |   expresion_aritmetica '*' expresion_aritmetica {
                if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                    System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
                }
                SymbolTable.aggPolaca("*");
                // Devuelve la expresión como una cadena que representa la multiplicación
                yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
            }
        |   expresion_aritmetica '/' expresion_aritmetica {
                if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                    System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
                }
                SymbolTable.aggPolaca("/");
                // Devuelve la expresión como una cadena que representa la división
                yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
            }
        |   T_CTE {
                SymbolTable.aggPolaca(val_peek(0).sval);
                // Devuelve el valor de la constante como cadena
                yyval.sval = val_peek(0).sval;
            }
        |   T_ID {
                SymbolTable.aggPolaca(val_peek(0).sval);
                // Devuelve el identificador como cadena
                st.esUsoValidoAmbito(val_peek(0).sval);
                yyval.sval = val_peek(0).sval;
            }
        |   acceso_par {
                // Devuelve el resultado del acceso a un parámetro
                yyval.sval = val_peek(0).sval;
            }
        
        |   unaria {
                // Devuelve la expresión unaria
                yyval.sval = val_peek(0).sval;
            }
    ;

expresion:
        expresion '+' expresion {
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("+");
            // Devuelve la expresión como una cadena que representa la suma
            yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
        }
    |   expresion '-' expresion {
            if( (isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("-");
            // Devuelve la expresión como una cadena que representa la resta
            yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
        }
    |   expresion '*' expresion {
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("*");
            // Devuelve la expresión como una cadena que representa la multiplicación
            yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
        }
    |   expresion '/' expresion {
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("/");
            // Devuelve la expresión como una cadena que representa la división
            yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
        }
    |   T_CTE {
            SymbolTable.aggPolaca(val_peek(0).sval);
            // Devuelve el valor de la constante como cadena
            yyval.sval = val_peek(0).sval;
        }
    |   T_ID {
            SymbolTable.aggPolaca(val_peek(0).sval);
            // Devuelve el identificador como cadena
            st.esUsoValidoAmbito(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
    |   acceso_par {
            // Devuelve el resultado del acceso a un parámetro
            yyval.sval = val_peek(0).sval;
        }
    |   invocacion_funcion {
            // Devuelve el resultado de la invocación de una función
            yyval.sval = val_peek(0).sval;
        }
    |   unaria {
            // Devuelve la expresión unaria
            yyval.sval = val_peek(0).sval;
        }
    |  error {System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
    ;

unaria: '-' T_CTE { 
    SymbolTable.aggPolaca(val_peek(0).sval);
    SymbolTable.aggPolaca("-");
    double valor = val_peek(0).dval;  
    // Devuelve el valor unario con el signo negativo
    yyval.sval = "-" + val_peek(0).sval;
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
                    st.addValue(nombreConMenos, tipo,"Constante"," ",SymbolTable.constantValue);
                }
            } else if (tipo.equals("double")) {
                if (!lexer.isDoubleRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para double.");
                } else {
                    
                    st.addValue(nombreConMenos, tipo,"Constante"," ", SymbolTable.constantValue);
                }
            }else if (tipo.equals("Octal")) {
                if (!lexer.isOctalRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para octales.");
                    
                } else {
                    st.addValue(nombreConMenos, tipo,"Constante"," ", SymbolTable.constantValue);
                }
            }
        } else {
            System.err.println("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else { /*se trata de numero negativo menor al menor negativo.*/
    	
        if (nombreConstante.startsWith("0") && !nombreConstante.matches(".*[89].*")) {
        	System.err.println("El valor octal " + "-"+nombreConstante+ " se ajusto al valor minimo.");
            st.addValue("-020000000000", "Octal","Constante"," ", SymbolTable.constantValue);
        } else if (nombreConstante.contains(".")) {
        	System.err.println("El valor double -" + nombreConstante + " se ajusta al valor mínimo.");

            /* Parseamos el valor como double para comparaciones*/
            double valorDouble = Double.parseDouble("-" + nombreConstante.replace("d", "e"));
            /* Rango mínimo y máximo de los números double*/
            double maxNegativeDouble = -1.7976931348623157e+308;
            double minNegativeDouble = -2.2250738585072014e-308;

            /* Si está por debajo del máximo permitido, lo mantenemos*/
            if (valorDouble < maxNegativeDouble) {
                st.addValue("-1.7976931348623156d+308", "double","Constante"," ", SymbolTable.constantValue);
            } 
            /* Si está por debajo del mínimo permitido pero mayor al mínimo ajustado*/
            else if (valorDouble > minNegativeDouble) {
                st.addValue("-2.2250738585072015d-308", "double","Constante"," ", SymbolTable.constantValue);
            } 
            /* Si está en el rango permitido*/
            else {
                st.addValue("-" + nombreConstante, "double","Constante"," ", SymbolTable.constantValue);
            }
            
        } else{ /*ya se sabe que es entero*/
            /* Lógica para longint*/
        	System.err.println("El valor longint -" + nombreConstante + " se ajusta al valor mínimo.");
            nombreConMenos = "-2147483648"; /* Asignar valor mínimo si está fuera de rango*/
            st.addValue(nombreConMenos, "longint","Constante"," ", SymbolTable.constantValue);
        }
        
    }
};

%%

public void yyerror(String s) {
    System.err.println("Error en linea: " + Lexer.nmrLinea + " String: " +s);
  }

int yylex() {
    try {
        Pair token = lexer.analyze(reader);  
        if (token != null) {
            if (token.getToken() == 277 || token.getToken() == 278 || token.getToken() == 279 || token.getToken() == 280) { //SI SE TRATA DE UN TOKEN QUE TIENE MUCHAS REFERENCIAS EN TABLA DE SIMBOLOS
                yylval = new ParserVal(token.getLexema());
            }
            if(token.getToken()<31) { //SI SE TRATA DE UN TOKEN DE UN SIMBOLO SINGULAR ESPECIFICO EN LA TABLA DE SIMBOLOS
            	
            	char character = token.getLexema().charAt(0);  
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
   
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);  

    if (result == JFileChooser.APPROVE_OPTION) {  
        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();  

        
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
    if (st.containsKeyTT(tipo)) {
        TipoSubrango subrango = st.getTipoSubrango(tipo);
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
public void chequeoPares(String variable, String expresion){
    if (st.getUse(variable) == null && st.getUse(expresion) == null) {
        System.out.println("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("Las variables: "+ variable + " " + expresion + " nunca fueron declaradas");

    }
    else
    if (st.getUse(variable) == null) {
        System.out.println("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("La variable: "+ variable + " nunca fue declarada");

    } else if (st.getUse(expresion) == null) {
        System.out.println("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("La variable: "+ expresion + " nunca fue declarada");

    }

    else 
    if((st.getUse(variable).equals("Nombre de variable par") &&!st.getUse(expresion).equals("Nombre de variable par"))
    ||(!st.getUse(variable).equals("Nombre de variable par") &&st.getUse(expresion).equals("Nombre de variable par"))){
        System.out.println("Warning: No se pueden utilizar los tipos pares en operaciones que conlleven tipos distintos ");        
        System.out.println("Error en asignacion: "+variable + " := " + expresion + ";");
    }else{
        System.out.println(variable + " := " + expresion + ";");
    }
}
public boolean isPair(String variable){
    if (st.getUse(variable)!=null)
        if(st.getUse(variable).equals("Nombre de variable par")){
            return true;
        }
        return false;
}
public String borrarUltimoAmbito(){
    String originalString = SymbolTable.ambitoGlobal.toString();

    // Separar por ":"
    String[] partes = originalString.split(":");

    // Crear un nuevo StringBuilder con todas las partes excepto la última
    StringBuilder nuevoStringBuilder = new StringBuilder();
    for (int i = 0; i < partes.length - 1; i++) {
        nuevoStringBuilder.append(partes[i]);
        if (i < partes.length - 2) {
            nuevoStringBuilder.append(":"); // Volver a agregar los separadores ":"
        }
    }
    return nuevoStringBuilder.toString();
}




String obtenerTipo(String variable) {
    
    if (!st.hasKey(variable)) return variable;

    return st.getType(variable);  
}


	private SymbolTable st;
	private Lexer lexer;
	private BufferedReader reader;

	public Parser(String filePath) {
	    this.st = new SymbolTable();
	    try {
	        this.reader = new BufferedReader(new FileReader(filePath));
	        this.lexer = new Lexer(st);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
    public void imprimirSymbolTable() {
	System.out.println(this.st);
    st.imprimirTablaTipos();
    st.imprimirTablaFunciones();
    }

