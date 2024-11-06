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
    private String nombreAux2bytes="@aux2bytes";
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
                    case "OUTF":
                        generarCodigoImprimirPantalla();
                        break;    
     
	                case "!RET":
	                    generarCodigoRetorno();
	                    break;
	                default:
	                     if (token.endsWith("@")) {   //entramos un label
	                        codigo.append(token.replace("@", "")).append(":\n");
	                    } else if (token.endsWith("$")&& posActualPolaca>0) {   // Encontramos el comienzo de una funcion
	                        generarCabeceraFuncion(token);
	                    }else if (token.endsWith("%")&& posActualPolaca<SymbolTable.polaca.size()-1) {   // Encontramos el comienzo de una funcion
	                        generarFinalFuncion(token);
	                    }else {
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
    private void generarCodigoImprimirPantalla() {
        // Obtenemos la cadena del tope de la pila
        String cadena = pila_tokens.pop();
        
        // Suponiendo que el contenido de `cadena` es el nombre de la etiqueta en la sección de datos:
        codigo.append("mov eax, 4\n");                // Código del sistema para escribir
        codigo.append("mov ebx, 1\n");                // File descriptor 1 (salida estándar)
        codigo.append("mov ecx, ").append(cadena).append("\n");  // Dirección de la cadena en `ecx`
        codigo.append("mov edx, [").append(cadena).append("_len]\n"); // Tamaño de la cadena en `edx`
        codigo.append("int 0x80\n");                  // Llamada a interrupción para ejecutar la salida
    }
    

	private void generarCabeceraFuncion(String token) {
        codigo.append(token.replace("$","")).append(" PROC\n");
    }    
    private void generarFinalFuncion(String token) {
    	
    	codigo.append(token.replace("%","")).append(" ENDP\n");
	}


	private  void generarCabecera() {
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

    private  void generarCodigoDatos(StringBuilder cabecera) {
            //funcion utilizada para generar el codigo necesario para todos los datos del programa, presentes en la tabla de simbolos
        for (String simbolo : st.obtenerConjuntoSimbolos()) { 
            // Obtenemos el tipo de uso y tipo de dato desde la tabla de símbolos
            String uso = st.getUse(simbolo);
            String tipo = st.getType(simbolo);

            // Dependiendo del tipo de uso, se genera el código correspondiente en la cabecera
            if (uso.equals("Nombre de variable") || uso.equals("Nombre de parametro")) {
                // Ejemplo: Definir variable con el tipo y símbolo
                cabecera.append(simbolo).append(" dd 0\n"); // Suponiendo que es una variable de tipo entero en assembler
            } else if (uso.equals("Nombre de funcion")) {
                // Ejemplo: Definir espacio reservado o etiqueta para función
                continue;
            } else if (uso.equals("Constante")) {
                // Ejemplo: Definir constante en assembler
                cabecera.append(simbolo).append(" equ ").append(simbolo).append("\n"); // Constante con su valor
            } else if (uso.equals("Cadena multilinea")){
                String etiquetaUnica = simbolo + "_str"; // Agregamos un sufijo para evitar duplicados
            
                cabecera.append(etiquetaUnica) // Usa la etiqueta única en lugar del símbolo original
                        .append(" db \"")
                        .append(simbolo) // Aquí se usa el símbolo como el contenido
                        .append("\", 0\n"); // Terminador nulo para la cadena

            }
            else continue;
            // Añadir otros tipos de uso según sea necesario
        }
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
 /*
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

*/

    private void generarErrorInvocacion(String funcion, String funcion_actual) {
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
            	String op2tipo=st.getType(op2);
            	if(!op2tipo.equals("longint") && !op2tipo.equals("double")&& !st.getUse(op2tipo).equals("Nombre de tipo de par")){//corroborar que este dentro del rango
            		// Obtener los límites de rango del tipo definido por el usuario
            		Double limiteInferior = st.getTipoSubrango(op2tipo).getLimiteInferior();
            		Double limiteSuperior = st.getTipoSubrango(op2tipo).getLimiteSuperior();

            		// Crear etiquetas para control de flujo
            		String etiquetaSinError = "DENTRO_RANGO_" + generarIdUnico();
            		String etiquetaErrorRango = "ERROR_RANGO_" + generarIdUnico();

            		// Cargar el valor de `op2` en la FPU para verificar los rangos
            		codigo.append("FLD ").append(op2).append("\n"); // Cargar `op2` en ST(0)

            		// Comprobar límite inferior
            		codigo.append("FLD ").append(limiteInferior).append("\n"); // Cargar límite inferior en ST(1)
            		codigo.append("FCOMI ST(0), ST(1)\n"); // Comparar ST(0) con ST(1)
            		codigo.append("FSTSW AX\n"); // Almacenar el estado en AX
            		codigo.append("SAHF\n"); // Cargar el estado en los indicadores
            		codigo.append("JB ").append(etiquetaErrorRango).append("\n"); // Si `op2` es menor que el límite inferior, ir a `etiquetaErrorRango`

            		// Limpiar ST(1) después de la comparación
            		codigo.append("FSTP ST(0)\n"); // Sacar el límite inferior de la pila de la FPU

            		// Comprobar límite superior
            		codigo.append("FLD ").append(limiteSuperior).append("\n"); // Cargar límite superior en ST(1)
            		codigo.append("FCOMI ST(0), ST(1)\n"); // Comparar ST(0) con ST(1)
            		codigo.append("FSTSW AX\n"); // Almacenar el estado en AX
            		codigo.append("SAHF\n"); // Cargar el estado en los indicadores
            		codigo.append("JA ").append(etiquetaErrorRango).append("\n"); // Si `op2` es mayor que el límite superior, ir a `etiquetaErrorRango`

            		// Limpiar ST(1) después de la comparación
            		codigo.append("FSTP ST(0)\n"); // Sacar el límite superior de la pila de la FPU

            		// Si está dentro del rango, continuar con la asignación
            		codigo.append(etiquetaSinError).append(":\n");
            		codigo.append("MOV ECX, ").append(op2).append("\n"); // Mover `op2` a ECX (asignación)
            		codigo.append("MOV ").append(op1).append(", ECX\n");

            		// Manejo de error si está fuera del rango
            		codigo.append(etiquetaErrorRango).append(":\n");
            		codigo.append("invoke MessageBox, NULL, addr @ERROR_RANGO, addr @ERROR_RANGO, MB_OK\n");
            		codigo.append("invoke ExitProcess, 0\n");
            	
            	}else {
            		codigo.append("MOV ECX, ").append(op2).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                    codigo.append("MOV ").append(op1).append(", ECX\n");
                    break;
            	}
                
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

        //Si es LONGINT, la tengo que convertir a DOUBLE
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
                 // Variable auxiliar para la palabra de estado

                // Almacenar la palabra de estado y verificar si hubo overflow
                codigo.append("FSTSW ").append(nombreAux2bytes ).append("\n"); // Almacena la palabra de estado en `nombreAux2bytes`
                codigo.append("MOV AX, ").append(nombreAux2bytes ).append("\n"); // Carga la palabra de estado en AX
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
            	String op2tipo=st.getType(op2);
            	if(!op2tipo.equals("longint") && !op2tipo.equals("double")&& !st.getUse(op2tipo).equals("Nombre de tipo de par")){//corroborar que este dentro del rango
            		// Obtener los límites de rango del tipo definido por el usuario
            		Double limiteInferior = st.getTipoSubrango(op2tipo).getLimiteInferior();
            		Double limiteSuperior = st.getTipoSubrango(op2tipo).getLimiteSuperior();

            		// Crear etiquetas para control de flujo
            		String etiquetaSinError = "DENTRO_RANGO_" + generarIdUnico();
            		String etiquetaErrorRango = "ERROR_RANGO_" + generarIdUnico();

            		// Cargar el valor de `op2` en la FPU para verificar los rangos
            		codigo.append("FLD ").append(op2).append("\n"); // Cargar `op2` en ST(0)

            		// Comprobar límite inferior
            		codigo.append("FLD ").append(limiteInferior).append("\n"); // Cargar límite inferior en ST(1)
            		codigo.append("FCOMI ST(0), ST(1)\n"); // Comparar ST(0) con ST(1)
            		codigo.append("FSTSW AX\n"); // Almacenar el estado en AX
            		codigo.append("SAHF\n"); // Cargar el estado en los indicadores
            		codigo.append("JB ").append(etiquetaErrorRango).append("\n"); // Si `op2` es menor que el límite inferior, ir a `etiquetaErrorRango`

            		// Limpiar ST(1) después de la comparación
            		codigo.append("FSTP ST(0)\n"); // Sacar el límite inferior de la pila de la FPU

            		// Comprobar límite superior
            		codigo.append("FLD ").append(limiteSuperior).append("\n"); // Cargar límite superior en ST(1)
            		codigo.append("FCOMI ST(0), ST(1)\n"); // Comparar ST(0) con ST(1)
            		codigo.append("FSTSW AX\n"); // Almacenar el estado en AX
            		codigo.append("SAHF\n"); // Cargar el estado en los indicadores
            		codigo.append("JA ").append(etiquetaErrorRango).append("\n"); // Si `op2` es mayor que el límite superior, ir a `etiquetaErrorRango`

            		// Limpiar ST(1) después de la comparación
            		codigo.append("FSTP ST(0)\n"); // Sacar el límite superior de la pila de la FPU

            		// Si está dentro del rango, continuar con la asignación
            		codigo.append(etiquetaSinError).append(":\n");
            		codigo.append("MOV ECX, ").append(op2).append("\n"); // Mover `op2` a ECX (asignación)
            		codigo.append("MOV ").append(op1).append(", ECX\n");

            		// Manejo de error si está fuera del rango
            		codigo.append(etiquetaErrorRango).append(":\n");
            		codigo.append("invoke MessageBox, NULL, addr @ERROR_RANGO, addr @ERROR_RANGO, MB_OK\n");
            		codigo.append("invoke ExitProcess, 0\n");
            	
            	}else {
            		codigo.append("FLD ").append(op2).append("\n");
                    codigo.append("FSTP ").append(op1).append("\n");
                    break;
            	}
                
            
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

        if (!salto.equals("JMP") && lastComparation.equals("")) {//Es un salto con condicion
            String valor = pila_tokens.pop();
            
            codigo.append("MOV ECX, ").append(valor).append("\n");
            codigo.append("OR ECX, 0\n");
            codigo.append("JE L").append(direccion).append("\n");
        } else {
        	int direccionInt = Integer.parseInt(direccion);
            codigo.append(salto).append(" ").append(SymbolTable.polaca.get(direccionInt).replace("@","")).append("\n");
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

    private String renombre(String token) {

        // Si es una constante, le cambio de nombre al cual fue declarada
        if (st.getUse(token).equals("Constante")) {
            return "@" + token.replace('.', '@').replace('-', '@').replace('+', '@');
        } else if (st.getUse(token).equals("Nombre de variable") || st.getUse(token).equals("Nombre de funcion") || st.getUse(token).equals("Nombre de variable par")) {
            return "_" + token;
        } else {
            return token;
        }
    }

   

    private  String ocuparAuxiliar(String tipo) {
        String retorno = "@aux" + numeroAuxiliar;
        ++numeroAuxiliar;
        //agrego a la tabla de simbolos la auxiliar.
        st.addValue(retorno, tipo, "VarAux", null, SymbolTable.identifierValue);
        return retorno;
    }

    private void generarCodigoRetorno() {
        String topePila = pila_tokens.pop();

        // Verificar si el tipo es "double" o no
        if (st.getType(topePila).equals("double")) {
            // Cargar el valor al registro de coma flotante
            codigo.append("FLD ").append(topePila).append("\n");  // FLD carga el valor en el tope de la pila de FPU
        } else {
            // Cargar el valor al registro EAX (para enteros)
            codigo.append("MOV EAX, ").append(topePila).append("\n"); // Cargar el valor en EAX para el retorno de enteros
        }

        // Instrucción de retorno
        codigo.append("RET\n");
    }
}
