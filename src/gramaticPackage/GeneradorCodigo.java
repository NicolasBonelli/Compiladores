package gramaticPackage;
import java.io.FileReader;
import java.io.File;
import java.util.*;
import Paquetecompi.Lexer;
import Paquetecompi.Pair;
import Paquetecompi.SymbolTable;
import Paquetecompi.TipoEtiqueta;
import Paquetecompi.SymbolTable.Symbol;

import java.math.BigDecimal;

public class GeneradorCodigo {
    public static StringBuilder codigo = new StringBuilder();

    public static Stack<String> pila_tokens = new Stack<>();
    public static boolean errorSemantico=false;
    public static int posActualPolaca=0;
    private SymbolTable st;
    private TablaTipos tablaTipos;
    private int numeroAuxiliar;
    private int idUnico = 0;
    
    private String lastComparation = "";
       public GeneradorCodigo (SymbolTable st) {
           this.st=st;
           this.numeroAuxiliar = 1;
           this.tablaTipos = new TablaTipos(st);
       }
        
        
        public  void generarCodigo() {
            //funcion principal que genera el codigo del programa, utilizando los tokes de la pocala y simbolos de la respectiva tabla
            for (String token : SymbolTable.polaca) {
                switch (token) {
                    case "*":
                    case "+":
                    case ":=":  
                    case "-":
                    case "/":
                    case ">=":
                    case ">":   
                    case "<=":
                    case "<":
                    case "!=":
                        generarOperador(token);
                        break;
                    case "BI":
                        generarSalto("JMP");
                        break;
                    case "BF":
                        generarSalto(lastComparation);
                    break;
     
                case "!RET":
                    generarCodigoRetorno();
                    break;
                default:
                    if (token.startsWith(Parser.STRING_CHAR)) { //encontramos una cadena ??
                        token = token.substring(1);
                        codigo.append("invoke MessageBox, NULL, addr ").append(token).append(", addr ").append(token).append(", MB_OK \n");
                    } else if (token.startsWith(":")) {   //entramos un label
                        codigo.append(token.substring(1)).append(":\n");
                    } else if (token.startsWith("!")) {   // Encontramos el comienzo de una funcion
                        generarCabeceraFuncion(token);
                    } else {
                        pila_tokens.push(token);
                    }

                    break;
            }

			++posActualPolaca;
            //Impresion por pantalla para debuggear el codigo
            //System.out.println("Se leyo el token: " + token + ", la pila actual es: " + pila_tokens);
			if(errorSemantico)//ocurrio un error semantico
				break;
        }

        codigo.append("invoke ExitProcess, 0\n")
              .append("end START");

        generarCabecera();
    }

    private static void generarCabecera() {
    //funcoin encargada de la generacion de la cabecera del codigo
        StringBuilder cabecera = new StringBuilder();

        cabecera.append(".386\n")
            .append(".model flat, stdcall\n")
            .append("option casemap :none\n")
            .append("include \\masm32\\include\\windows.inc\n")
            .append("include \\masm32\\include\\kernel32.inc\n")
            .append("include \\masm32\\include\\user32.inc\n")
            .append("includelib \\masm32\\lib\\kernel32.lib\n")
            .append("includelib \\masm32\\lib\\user32.lib\n")
            .append(".data\n")
            
            //agregamos las constantes de error
            .append("@ERROR_DIVISION_POR_CERO db \"" + "ERROR DIVISION 0" + "\", 0\n")
            .append("@ERROR_OVERFLOW_PRODUCTO db \"" + "ERROR PRODUCTO" + "\", 0\n");
            

        generarCodigoDatos(cabecera);

        cabecera.append(".code\n");
        cabecera.append(codigo);
        codigo = cabecera;
    }

    private static void generarCodigoDatos(StringBuilder cabecera) {
        //funcion utilizada para generar el codigo necesario para todos los datos del programa, presentes en la tabla de simbolos
        for (int simbolo : SymbolTable.obtenerConjuntoPunteros()) {
            //tomamos el atributo 'uso' del simbolo actual, desde la tabla de simbolos
            String uso = SymbolTable.obtenerAtributo(simbolo, "uso");

            if (!uso.equals(SymbolTable.NO_ENCONTRADO_S) && uso.equals("funcion")) continue;

            String tipo_actual = SymbolTable.obtenerAtributo(simbolo, "tipo");
            String lexema_actual = SymbolTable.obtenerAtributo(simbolo, "lexema");
            
            if (tipo_actual.equals(SymbolTable.NO_ENCONTRADO_S)) continue;

            switch (tipo_actual) {
                case TablaTipos.STR_TYPE:
                    //tomo el valor de la tabla de simbolos
                    String valor_actual = SymbolTable.obtenerAtributo(simbolo, "valor");
                    cabecera.append(lexema_actual.substring(1)).append(" db \"").append(valor_actual).append("\", 0\n");
                    break;
                
                case "longint":
                case TablaTipos.FUNC_TYPE:
                    if (uso.equals("constante")) {
                        String lexema = lexema_actual;
                        lexema_actual = "@" + lexema_actual;
                        cabecera.append(lexema_actual).append(" dd ").append(lexema).append("\n");
                    } else {
                        if (!lexema_actual.startsWith("@")) {
                            cabecera.append("_");
                        }
                        
                        cabecera.append(lexema_actual).append(" dd ? \n");
                    }
                   
                    break;
                
                case "double":        //en caso que el simbolo de tipo double y sea una constante
                    if (uso.equals("constante")) {
                        String lexema = lexema_actual;

                        if (lexema_actual.charAt(0) == '.')
                            lexema = "0" + lexema;

                        lexema_actual = "@" + lexema_actual.replace('.', '@').replace('-', '@').replace('+', '@');  //cambiamos el punto por una @ 
                        cabecera.append(lexema_actual).append(" REAL4 ").append(lexema).append("\n");   //y agregamos el simbolo a la cabecera con REAL4
                    } else {
                        if (! lexema_actual.startsWith("@")) {
                            cabecera.append("_");
                        }
                        cabecera.append(lexema_actual).append(" dq ?\n");
                    }
                    
                    break;
            }
        }
    }

    private static void generarCabeceraFuncion(String token) {
        codigo.append(token.substring(1)).append(" PROC\n");
        // codigo.append("MOV EAX, ").append(token.substring(1)).append("\n");
        // codigo.append("MOV @FUNCION_ACTUAL, EAX\n"); // en la variable @FUNCION_ACTUAL guardamos el nombre de la funcion actual
    }

    private static void generarCodigoFinalFuncion(String token) {
        String nombre_funcion = pila_tokens.pop();
        codigo.append(nombre_funcion).append(" ").append(token.substring(1)).append("\n");
    }

  



    public  void generarOperador(String operador) {
        String op2 = pila_tokens.pop();   //el primero que saco es el segundo operando, ya que fue el ultimo que lei de la polaca y el ultimo que agregue a la pila
        String op1 = pila_tokens.pop();

        if (operador.equals(":=")) { 
            String aux = op1;
            op1 = op2;
            op2 = aux;
        }

        String tipo = tablaTipos.getTipoAbarcativo(op1, op2, operador);
        switch (tipo) {
            case "longint":
                generarOperacionEnteros(op1, op2, operador);
                break;
            case "double":
                generarOperacionFlotantes(op1, op2, operador);
                break;

            default:
                System.out.println("Algo esta mal");
                
        }
    }
 
    public static void generarOperacionFuncion(String op1, String op2) {
        int punt_op2 = SymbolTable.obtenerSimbolo(op2);
        String uso = SymbolTable.obtenerAtributo(punt_op2, "uso");
        
        op1 = renombre(op1);

        //si el uso es una variable, renombramos el operando
        if (uso.equals("variable"))
            op2 = renombre(op2);

        codigo.append("MOV EAX, ").append(op2).append("\n");
        codigo.append("MOV ").append(op1).append(", EAX\n");
    }

    private static void generarErrorDivCero(String aux){
        // genera el codigo necesario ante un error de division por cero
        codigo.append("JNE ").append(aux.substring(1)).append("\n");
        codigo.append("invoke MessageBox, NULL, addr @ERROR_DIVISION_POR_CERO, addr @ERROR_DIVISION_POR_CERO, MB_OK\n");
        codigo.append("invoke ExitProcess, 0\n");
        codigo.append(aux.substring(1)).append(":\n"); //declaro una label        
    }


    private static void generarErrorInvocacion(String funcion, String funcion_actual) {
        //genera el codigo necesario ante un error de invocacion de una funcion:
        //El codigo Assembler debera controlar que una funcion no pueda invocarse a si misma. 
     
        int punt_funcion = SymbolTable.obtenerSimbolo(funcion);
        String uso = SymbolTable.obtenerAtributo(punt_funcion, "uso"); 

        if (uso.equals("variable")) {
            funcion = renombre(funcion);  //renombramos la variable de funcion
            codigo.append("MOV EAX, [").append(funcion).append("]\n");
        } else 
            codigo.append("MOV EAX, ").append(funcion).append("\n");

        String label = "@aux" + numeroAuxiliar;
        ++numeroAuxiliar;

        codigo.append("MOV EBX, ").append(funcion_actual).append("\n");
        codigo.append("CMP EBX, EAX\n");
        codigo.append("JNE ").append(label).append("\n");
        codigo.append("invoke MessageBox, NULL, addr @ERROR_INVOCACION, addr @ERROR_INVOCACION, MB_OK\n");
        codigo.append("invoke ExitProcess, 0\n");
        codigo.append(label).append(":\n"); //declaro una label        
    }


    private int generarIdUnico(){
        idUnico++;
        return idUnico;
    }
    private  void generarOperacionEnteros(String op1, String op2, String operador) {
        op1 = renombre(op1);
        op2 = renombre(op2); 

        String aux;

        switch (operador) {
            case "+":
                codigo.append("MOV ECX, ").append(op1).append("\n"); //muevo siempre al registro ECX ya que al usar auxiliares nunca voy a gastar mas de 1 registro, ademas este registro no es usado por las divisiones
                codigo.append("ADD ECX, ").append(op2).append("\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", ECX\n");
                pila_tokens.push(aux);
                break;
            case "-":
                codigo.append("MOV ECX, ").append(op1).append("\n"); //muevo siempre al registro ECX ya que al usar auxiliares nunca voy a gastar mas de 1 registro, ademas este registro no es usado por las divisiones
                codigo.append("SUB ECX, ").append(op2).append("\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", ECX\n");
                pila_tokens.push(aux);
                break;
            case "*":
                codigo.append("MOV EAX, ").append(op1).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("MUL ").append(op2).append("\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", EAX\n");
                pila_tokens.push(aux);
                break;
            case ":=":
                codigo.append("MOV ECX, ").append(op2).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("MOV ").append(op1).append(", ECX\n");
                break;
            case "/":   
                aux = ocuparAuxiliar("longint"); // Registro o espacio auxiliar para el resultado

                // Verificar si el divisor es un inmediato (constante) o un registro/memoria
                String divisor = op2;
                if (esInmediato(op2)) {  // Asumimos que tienes un método `esInmediato`
                    divisor = ocuparAuxiliar("longint"); // Reserva un registro temporal para el divisor
                    codigo.append("MOV ").append(divisor).append(", ").append(op2).append("\n"); 
                }
            
                // Verificación de división por cero directamente con CMP y salto condicional
                codigo.append("CMP ").append(divisor).append(", 00h\n"); // Compara el divisor con 0
                String etiquetaSinError = "DIV_POR_CERO_" +  + generarIdUnico(); // Genera una etiqueta única
                codigo.append("JNE ").append(etiquetaSinError).append("\n"); // Si no es cero, salta a etiquetaSinError

                // Código de manejo de error de división por cero
                codigo.append("invoke MessageBox, NULL, addr @ERROR_DIVISION_POR_CERO, addr @ERROR_DIVISION_POR_CERO, MB_OK\n");
                codigo.append("invoke ExitProcess, 0\n"); // Termina el proceso en caso de error

                // Etiqueta para continuar si no hay error
                codigo.append(etiquetaSinError).append(":\n");
            
                // Preparación para la división
                codigo.append("MOV EAX, ").append(op1).append("\n"); // Mueve el dividendo a EAX
                codigo.append("CDQ\n"); // Extiende el signo de EAX a EDX para divisiones con números negativos
            
                // División
                codigo.append("DIV ").append(divisor).append("\n"); // Divide EDX:EAX por el divisor, resultado en EAX
            
                // Almacenar el resultado en el auxiliar
                codigo.append("MOV ").append(aux).append(", EAX\n"); // Mueve el cociente a aux
                pila_tokens.push(aux); // Guarda aux en la pila de tokens para su uso posterior
            break;
        
            case ">=":
                codigo.append("MOV ECX, ").append(op2).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); //REVISAR pongo el aux en todos 1
                codigo.append("JAE ").append(aux.substring(1)).append("\n"); // si llega a ser verdadero salto y sigo con la ejecucion. En caso contrario tengo que poner el valor de aux en 0
                codigo.append("MOV ").append(aux).append(", 00h\n"); //REVISAR pongo el aux en todos 0
                codigo.append(aux.substring(1)).append(":\n"); //creo una label para que salte y se saltee la instruccion de poner aux en cero en caso de que sea verdadera
                pila_tokens.push(aux);
                lastComparation = "JB";
                break;
            
            case ">":
                codigo.append("MOV ECX, ").append(op2).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); //REVISAR pongo el aux en todos 1
                codigo.append("JA ").append(aux.substring(1)).append("\n"); // si llega a ser verdadero salto y sigo con la ejecucion. En caso contrario tengo que poner el valor de aux en 0
                codigo.append("MOV ").append(aux).append(", 00h\n"); //REVISAR pongo el aux en todos 0
                codigo.append(aux.substring(1)).append(":\n"); //creo una label para que salte y se saltee la instruccion de poner aux en cero en caso de que sea verdadera
                pila_tokens.push(aux);
                lastComparation = "JBE";
                break;
            
            case "<=":
                codigo.append("MOV ECX, ").append(op2).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", 0FFh\n"); //REVISAR pongo el aux en todos 1
                codigo.append("JBE ").append(aux.substring(1)).append("\n"); // si llega a ser verdadero salto y sigo con la ejecucion. En caso contrario tengo que poner el valor de aux en 0
                codigo.append("MOV ").append(aux).append(", 00h\n"); //REVISAR pongo el aux en todos 0
                codigo.append(aux.substring(1)).append(":\n"); //creo una label para que salte y se saltee la instruccion de poner aux en cero en caso de que sea verdadera
                pila_tokens.push(aux);
                lastComparation = "JA";
                break;
            
            case "<":
                codigo.append("MOV ECX, ").append(op2).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("CMP ").append(op1).append(", ECX\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV " + aux + ", 0FFh\n"); //REVISAR pongo el aux en todos 1
                codigo.append("JB " + aux.substring(1) + "\n"); // si llega a ser verdadero salto y sigo con la ejecucion. En caso contrario tengo que poner el valor de aux en 0
                codigo.append("MOV " + aux + ", 00h\n"); //REVISAR pongo el aux en todos 0
                codigo.append(aux.substring(1) + ":\n"); //creo una label para que salte y se saltee la instruccion de poner aux en cero en caso de que sea verdadera
                pila_tokens.push(aux);
                lastComparation = "JAE";
                break;
            
           
            default:
                codigo.append("ERROR, se entro a default en operacion de enteros").append("\n");
                break;
        }
    }
            
    private boolean esInmediato(String operando) {
        // Verifica si es un número decimal o hexadecimal
        try {
            if (operando.startsWith("0x") || operando.startsWith("0X")) {
                // Intenta parsear como hexadecimal
                Integer.parseInt(operando.substring(2), 16);
            } else {
                // Intenta parsear como número decimal
                Integer.parseInt(operando);
            }
            return true; // Si parsea sin errores, es un inmediato
        } catch (NumberFormatException e) {
            return false; // Si hay una excepción, no es un inmediato
        }
    }
            
            
    private  void generarOperacionFlotantes(String op1, String op2, String operador) { 
        op1 = renombre(op1);
        op2 = renombre(op2);

        String aux;

        //Si es ULONG, la tengo que convertir a DOUBLE
        if (st.getType(op1).equals("longint")) {
            aux = ocuparAuxiliar("double");
            codigo.append("FLD ").append(op1).append("\n");
            codigo.append("FSTP ").append(aux).append("\n");
            op1 = aux;
        }
        if (st.getType(op2).equals("longint")) {
            aux = ocuparAuxiliar("double");
            codigo.append("FLD ").append(op2).append("\n");
            codigo.append("FSTP ").append(aux).append("\n");
            op1 = aux;
        }
        

        switch (operador) {
            //nunca  va a llegar una operacion AND o OR entre doubles ya que al finalizar cada condicion guardo un ULONG con el resultado de la condicion.
            case "+":
                // Cargar los operandos en la pila del coprocesador de punto flotante (FPU)
                codigo.append("FLD ").append(op2).append("\n"); // Carga `op2` en la FPU
                codigo.append("FLD ").append(op1).append("\n"); // Carga `op1` en la FPU

                // Realizar la suma en punto flotante
                codigo.append("FADD\n");

                // Verificación de overflow en la suma
                String etiquetaSinOverflow = "LABEL_NO_OVERFLOW_" + generarIdUnico(); // Etiqueta única para el control de flujo sin overflow
                String nombreAux2bytes = ocuparAuxiliar("2bytes"); // Variable auxiliar para la palabra de estado

                // Almacenar la palabra de estado y verificar si hubo overflow
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n"); // Almacena la palabra de estado en `nombreAux2bytes`
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); // Carga la palabra de estado en AX
                codigo.append("SAHF\n"); // Almacena el valor de AH en los bits de indicador

                // Verificar si se establece la bandera de overflow
                codigo.append("JNO ").append(etiquetaSinOverflow).append("\n"); // Salta a `etiquetaSinOverflow` si no hay overflow

                // Código de manejo de error de overflow
                codigo.append("invoke MessageBox, NULL, addr @ERROR_OVERFLOW, addr @ERROR_OVERFLOW, MB_OK\n");
                codigo.append("invoke ExitProcess, 0\n"); // Termina el proceso en caso de overflow

                // Etiqueta para continuar si no hay overflow
                codigo.append(etiquetaSinOverflow).append(":\n");

                // Almacenar el resultado de la suma en el auxiliar `aux`
                aux = ocuparAuxiliar("double");
                codigo.append("FSTP ").append(aux).append("\n"); // Mueve el resultado a `aux`
                pila_tokens.push(aux); // Guarda `aux` en la pila de tokens para su uso posterior
                break;

            case "-":
                codigo.append("FLD ").append(op2).append("\n"); //apilo primero el op2 ya que quiero que me quede como el segundo que agarro para las operaciones que no son conmutativas
                codigo.append("FLD ").append(op1).append("\n");

                codigo.append("FSUB\n");
                aux = ocuparAuxiliar("double");
                codigo.append("FSTP ").append(aux).append("\n");
                pila_tokens.push(aux);
                break;
            
            case "*":
                codigo.append("FLD ").append(op2).append("\n"); //apilo primero el op2 ya que quiero que me quede como el segundo que agarro para las operaciones que no son conmutativas
                codigo.append("FLD ").append(op1).append("\n");
                
                codigo.append("FMUL\n");
                aux = ocuparAuxiliar("double");
                codigo.append("FSTP ").append(aux).append("\n");
                pila_tokens.push(aux);
                break;
            
            case ":=":
                codigo.append("FLD ").append(op2).append("\n");
                codigo.append("FSTP ").append(op1).append("\n");
                break;
            
            case "/":
                aux = ocuparAuxiliar("double"); // Registro o espacio auxiliar para el resultado en double

                // Verificación de división por cero utilizando el coprocesador de punto flotante
                codigo.append("FLD ").append(op2).append("\n"); // Carga `op2` (divisor) en la pila de FPU
            
                // Comparación con cero para verificar si el divisor es cero
                String etiquetaSinError = "DIV_POR_CERO_" +  generarIdUnico(); // Genera una etiqueta única
                String _cero = ocuparAuxiliar("double");
                codigo.append("FILD ").append(_cero).append("\n"); // Carga el valor de cero en el coprocesador de punto flotante
                codigo.append("FCOMIP ST, ST(1)\n"); // Compara ST con ST(1) y elimina ST del stack
                codigo.append("JNE ").append(etiquetaSinError).append("\n"); // Si `op2` no es cero, salta a la etiquetaSinError
            
                // Código de manejo de error de división por cero
                codigo.append("invoke MessageBox, NULL, addr @ERROR_DIVISION_POR_CERO, addr @ERROR_DIVISION_POR_CERO, MB_OK\n");
                codigo.append("invoke ExitProcess, 0\n"); // Termina el proceso en caso de error
            
                // Etiqueta para continuar si no hay error
                codigo.append(etiquetaSinError).append(":\n");
            
                // Preparación para la división en punto flotante
                codigo.append("FLD ").append(op2).append("\n"); // Apila `op2` como divisor
                codigo.append("FLD ").append(op1).append("\n"); // Apila `op1` como dividendo
                codigo.append("FDIV\n"); // Realiza la división ST(1) = ST(1) / ST y almacena en ST(1)
            
                // Almacenar el resultado de la división en `aux`
                codigo.append("FSTP ").append(aux).append("\n"); // Mueve el resultado a `aux`
                pila_tokens.push(aux); // Guarda `aux` en la pila de tokens para su uso posterior
            break;
            
           
            
            case ">=":
                codigo.append("FLD ").append(op1).append("\n"); 
                codigo.append("FCOM ").append(op2).append("\n");
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");// cargo la palabra de estado en la memoria
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); //copia el contenido en el registro AX
                codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                aux = ocuparAuxiliar("longint");
                codigo.append("MOV " + aux + ", 0FFh\n");
                codigo.append("JAE " + aux.substring(1) + "\n"); // si llega a ser verdadero salto y sigo con la ejecucion. En caso contrario tengo que poner el valor de aux en 0
                codigo.append("MOV " + aux + ", 00h\n"); 
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                break;
            
            case ">":
                codigo.append("FLD ").append(op1).append("\n"); 
                codigo.append("FCOM ").append(op2).append("\n");
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");// cargo la palabra de estado en la memoria
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); //copia el contenido en el registro AX
                codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                aux = ocuparAuxiliar("longint");
                codigo.append("MOV " + aux + ", 0FFh\n"); 
                codigo.append("JA " + aux.substring(1) + "\n"); // si llega a ser verdadero salto y sigo con la ejecucion. En caso contrario tengo que poner el valor de aux en 0
                codigo.append("MOV " + aux + ", 00h\n"); 
                codigo.append(aux.substring(1) + ":\n"); //creo una label para que salte y se saltee la instruccion de poner aux en cero en caso de que sea verdadera
                pila_tokens.push(aux);
                break;
            
            case "<=":
                codigo.append("FLD ").append(op1).append("\n"); 
                codigo.append("FCOM ").append(op2).append("\n");
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");// cargo la palabra de estado en la memoria
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); //copia el contenido en el registro AX
                codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                aux = ocuparAuxiliar("longint");
                codigo.append("MOV " + aux + ", 0FFh\n");
                codigo.append("JBE " + aux.substring(1) + "\n"); // si llega a ser verdadero salto y sigo con la ejecucion. En caso contrario tengo que poner el valor de aux en 0
                codigo.append("MOV " + aux + ", 00h\n"); 
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                break;
            
            case "<":
                codigo.append("FLD ").append(op1).append("\n"); 
                codigo.append("FCOM ").append(op2).append("\n");
                codigo.append("FSTSW ").append(nombreAux2bytes).append("\n");// cargo la palabra de estado en la memoria
                codigo.append("MOV AX, ").append(nombreAux2bytes).append("\n"); //copia el contenido en el registro AX
                codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                aux = ocuparAuxiliar("longint");
                codigo.append("MOV " + aux + ", 0FFh\n");
                codigo.append("JB " + aux.substring(1) + "\n"); // si llega a ser verdadero salto y sigo con la ejecucion. En caso contrario tengo que poner el valor de aux en 0
                codigo.append("MOV " + aux + ", 00h\n"); 
                codigo.append(aux.substring(1) + ":\n"); 
                pila_tokens.push(aux);
                break;
            
            default:
                codigo.append("ERROR se entro a default al generar codigo para una operacion de flotantes\n");
                break;
        }
    }

    private  void generarSalto(String salto) {
        String direccion = pila_tokens.pop();    

        if (!salto.equals("JMP") && lastComparation.equals("")) {
            String valor = pila_tokens.pop();
            int punt_valor = SymbolTable.obtenerSimbolo(valor);
            String uso = SymbolTable.obtenerAtributo(punt_valor, "uso");
            
            if (uso.equals("variable"))
                valor = renombre(valor);

            codigo.append("MOV ECX, ").append(valor).append("\n");
            codigo.append("OR ECX, 0\n");
            codigo.append("JE L").append(direccion).append("\n");
        } else {
            codigo.append(salto).append(" L").append(direccion).append("\n");
        }

        lastComparation = "";
    }

    private static void generarLlamadoFuncion() {
        String parametro = pila_tokens.pop();
        String funcion = pila_tokens.pop();
        String funcion_actual = pila_tokens.pop();  //guardamos la funcion en ejeucucon actual.

        int punt_funcion = SymbolTable.obtenerSimbolo(funcion);
        int punt_parametro = SymbolTable.obtenerParametro(funcion);
        String tipo_retorno = SymbolTable.obtenerAtributo(punt_funcion, "retorno");
        String uso_funcion = SymbolTable.obtenerAtributo(punt_funcion, "uso");
        String lexema_parametro = SymbolTable.obtenerAtributo(punt_parametro, "lexema");

        // Si la funcion actual tiene un @, quiere decir que estamos fuera del MAIN
        if (funcion_actual.contains("@"))
            generarErrorInvocacion(funcion, funcion_actual);

        pila_tokens.push(parametro);
        pila_tokens.push(lexema_parametro);
        generarOperador(":=");

        parametro = renombre(parametro);
        
        if (uso_funcion.equals("variable")) {
            codigo.append("CALL [_").append(funcion).append("]\n");   //es un puntero a funcion
            String nombreFuncion = SymbolTable.obtenerAtributo(punt_funcion, "funcion_asignada");
            pila_tokens.push("@ret@" + nombreFuncion);
        } else {
            codigo.append("CALL ").append(funcion).append("\n");    //es una funcion normal
            pila_tokens.push("@ret@" + funcion); //pusheo el retorno de la funcion
        }
    }

    private static String renombre(String token) {
        char caracter = token.charAt(0);
        int puntToken = SymbolTable.obtenerSimbolo(token);

        // Si es una constante, le cambio de nombre al cual fue declarada
        if (SymbolTable.obtenerAtributo(puntToken, "uso").equals("constante")) {
            return "@" + token.replace('.', '@').replace('-', '@').replace('+', '@');
        } else if (Character.isLowerCase(caracter) || Character.isUpperCase(caracter) || caracter == '_') {
            return "_" + token;
        } else {
            return token;
        }
    }

    private static String negacion(String comparacion) {
        switch (comparacion) {
            case "JE": return "JNE";
            case "JNE": return "JE";
            case "JG": return "JLE";
            case "JLE": return "JG";
            case "JL": return "JGE";
            case "JGE": return "JL";
            default: return comparacion;
        }
    }

    private  String ocuparAuxiliar(String tipo) {
        String retorno = "@aux" + numeroAuxiliar;
        ++numeroAuxiliar;
        //agrego a la tabla de simbolos la auxiliar.
        st.addValue(retorno, tipo, "VarAux", null, SymbolTable.identifierValue);
        return retorno;
    }

    private  void generarCodigoRetorno() {
        generarOperador(":=");
        codigo.append("RET\n");
    }
}
