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
    private String nombrePrograma="CodigoAssembler.asm";
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
                    case "=":	
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
	                     if (token.startsWith("&")||token.endsWith("@") ) {   //entramos un label
	                        codigo.append(token.replace("@", "").replace("&", "")).append(":\n");
	                    } else if (token.endsWith("$")&& posActualPolaca>0) {   // Encontramos el comienzo de una funcion
	                        generarCabeceraFuncion(token);
	                    }else if (token.endsWith("%")&& posActualPolaca<SymbolTable.polaca.size()-1) {   // Encontramos el comienzo de una funcion
	                        generarFinalFuncion(token);
	                    }else if (st.getUse(token).equals("Nombre de funcion")){
	                        generarLlamadoFuncion(token);
	                    } else if(posActualPolaca>0)
	                    	pila_tokens.push(token);
	                    
	                    
	
	                    break;
            }

			++posActualPolaca;
            //Impresion por pantalla para debuggear el codigo
            //System.out.println("Se leyo el token: " + token + ", la pila actual es: " + pila_tokens);
			if(errorSemantico){//ocurrio un error semantico
				FileASSEMCreator.eraseProgram(nombrePrograma);
				break;
			}
			
        }

        codigo.append("invoke ExitProcess, 0\n")
              .append("end START");

        generarCabecera();
        FileASSEMCreator.writeProgram(nombrePrograma, codigo.toString());
    }
    private void generarCodigoImprimirPantalla() {
        // Obtenemos la cadena del tope de la pila
        String cadena = pila_tokens.pop().replace("[","").replace("]","").replace(" ", "_");
        
        codigo.append("push offset " + cadena + "_str \n");
        codigo.append("call printf \n");
        codigo.append("add esp, 4 \n");

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
            .append("include \\masm32\\include\\masm32rt.inc \n")
            .append("includelib \\masm32\\lib\\kernel32.lib \n")
            .append("includelib \\masm32\\lib\\masm32.lib \n")

            .append("include \\masm32\\include\\windows.inc\n")
            .append("include \\masm32\\include\\kernel32.inc\n")
            .append("include \\masm32\\include\\user32.inc\n")
            .append("includelib \\masm32\\lib\\user32.lib\n")
            .append("printf PROTO C : VARARG \n")
            .append(".data\n")

            
            //agregamos las constantes de error
            .append("@ERROR_DIVISION_POR_CERO db \"" + "ERROR DIVISION 0" + "\", 0\n")
            .append("@ERROR_OVERFLOW db \"" + "ERROR OVERFLOW" + "\", 0\n")
            .append("@ERROR_RANGO db \"" + "ERROR RANGO" + "\", 0\n")
            .append("@MAX_DOUBLE REAL8 1.7976931348623157e+308  \n")
        	.append("@aux2bytes dw 0.0 \n")
            .append("format db \"Valor modificado: %f\", 0 \n");
        generarCodigoDatos(cabecera);

        cabecera.append(".code\n").append("START:\n");
        cabecera.append(codigo);
        codigo = cabecera;
    }

    private  void generarCodigoDatos(StringBuilder cabecera) {//TODO
            //funcion utilizada para generar el codigo necesario para todos los datos del programa, presentes en la tabla de simbolos
        for (String simbolo : st.obtenerConjuntoSimbolos()) { 
            // Obtenemos el tipo de uso y tipo de dato desde la tabla de símbolos
        	String simboloRenombrado = renombre(simbolo);
            String uso = st.getUse(simbolo);
            String tipo = st.getType(simbolo);
            if(uso!=null) {
            	// Dependiendo del tipo de uso, se genera el código correspondiente en la cabecera
                if (uso.equals("Nombre de variable") || uso.equals("Nombre de parametro")|| uso.equals("Nombre de variable par") || uso.equals("VarAux")) {
                    if (tipo.equals("longint")){
                        cabecera.append(simboloRenombrado).append(" dd 0\n"); // Asumimos 32 bits para longint

                    } else if (tipo.equals("double")){
                        cabecera.append(simboloRenombrado).append(" dq 0.0\n"); // Para almacenar un double en 64 bits
                    } else if (st.getUse(tipo).equals("Nombre de tipo")){ //DEFINIDO POR EL USER pero no es pair
                        TipoSubrango tS = st.getTipoSubrango(tipo+":"+st.getAmbitoByKey(tipo));
                         if (tS.getTipoBase().equals("double")){
                            cabecera.append(simboloRenombrado).append(" dq ").append(tS.getLimiteInferior()).append("\n"); // Ejemplo de valor inicial en rango
                            cabecera.append(simboloRenombrado+"limiteInferior " + "dq ").append(tS.getLimiteInferior()).append("\n");
                            cabecera.append(simboloRenombrado+"limiteSuperior " + "dq ").append(tS.getLimiteSuperior()).append("\n");

                        } else { // Convertir el límite inferior y superior de double a int
                            cabecera.append(simboloRenombrado).append(" dd ").append((int) Math.round(tS.getLimiteInferior())).append("\n");
                            cabecera.append(simboloRenombrado).append("limiteInferior dd ").append((int) Math.round(tS.getLimiteInferior())).append("\n");
                            cabecera.append(simboloRenombrado).append("limiteSuperior dd ").append((int) Math.round(tS.getLimiteSuperior())).append("\n");
                            
                    }
                    } else if(uso.equals("Nombre de variable par")){ //PAIR
                        TipoSubrango tS = st.getTipoSubrango(tipo+":"+st.getAmbitoByKey(tipo));
                        if (tS.getTipoBase().equals("double")){
                            cabecera.append(simboloRenombrado).append("_1 dq 0.0\n"); // Componente 1 del par (double)
                            cabecera.append(simboloRenombrado).append("_2 dq 0.0\n"); // Componente 2 del par (double)
                        } else {
                            cabecera.append(simboloRenombrado).append("_1 dd 0\n"); // Componente 1 del par
                            cabecera.append(simboloRenombrado).append("_2 dd 0\n"); // Componente 2 del par
                        }
                    }
                } else if (uso.equals("Nombre de funcion")) {
                    // Ejemplo: Definir espacio reservado o etiqueta para función
                    continue;
                } else if (uso.equals("Constante")) {
                    if (tipo.equals("longint") || tipo.equals("Octal"))
                    
                        cabecera.append(simboloRenombrado).append(" dd ").append(simbolo).append("\n"); // Constante con su valor

                    else cabecera.append(simboloRenombrado).append(" REAL8 ").append(simbolo.replace('d', 'e')).append("\n"); // Constante con su valor

                } else if (uso.equals("Cadena multilinea")){
                    String etiquetaUnica = simbolo.replace(" ", "_") + "_str"; // Agregamos un sufijo para evitar duplicados
                
                    cabecera.append(etiquetaUnica) // Usa la etiqueta única en lugar del símbolo original
                            .append(" db \"")
                            .append(simbolo) // Aquí se usa el símbolo como el contenido
                            .append("\", 0\n"); // Terminador nulo para la cadena

                }
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
	            System.out.println("Error de tipos");
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

        // Método para verificar si es un acceso a `pair` (por ejemplo, x{1} o x{2})
    private boolean esAccesoPar(String operando) {
        return operando.matches(".+\\{[12]\\}");
    }

    // Método para obtener la componente específica del `pair`
    private String obtenerComponentePar(String accesoPar) {
        // Divide el acceso en variable y componente (e.g., "x{1}" -> "x", "1")
        String variable = accesoPar.substring(0, accesoPar.indexOf('{'));
        String componente = accesoPar.substring(accesoPar.indexOf('{') + 1, accesoPar.indexOf('}'));

        // Mapea "1" y "2" a las componentes en assembler correspondientes (ajustar según implementación)
        return componente.equals("1") ? variable + "_1" : variable + "_2";
    }


    private int generarIdUnico(){
        idUnico++;
        return idUnico;
    }
    private  void generarOperacionEnteros(String op1, String op2, String operador) {
        String op1Renombrado = renombre(op1);
        String op2Renombrado = renombre(op2); 
        
        
        String aux;
         // Verifica si op1 o op2 son accesos a un par (patrón "variable{n}")
        if (esAccesoPar(op1)) {
            op1 = obtenerComponentePar(op1); // Traduce el acceso par a su representación assembler
        }
        if (esAccesoPar(op2)) {
            op2 = obtenerComponentePar(op2);
        }
        switch (operador) {
            case "+":

                codigo.append("MOV ECX, ").append(op1Renombrado).append("\n"); //muevo siempre al registro ECX ya que al usar auxiliares nunca voy a gastar mas de 1 registro, ademas este registro no es usado por las divisiones
                codigo.append("ADD ECX, ").append(op2Renombrado).append("\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", ECX\n");
                pila_tokens.push(aux);
                break;
            case "-":
                codigo.append("MOV ECX, ").append(op1Renombrado).append("\n"); //muevo siempre al registro ECX ya que al usar auxiliares nunca voy a gastar mas de 1 registro, ademas este registro no es usado por las divisiones
                codigo.append("SUB ECX, ").append(op2Renombrado).append("\n");
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", ECX\n");
                pila_tokens.push(aux);
                break;
            case "*":
            	// Mover op1Renombrado a EAX
                codigo.append("MOV EAX, ").append(op1Renombrado).append("\n");

                // Verificar si op2Renombrado es una constante etiquetada (empieza con '@')
                if (op2Renombrado.matches("@\\d+")) {
                    // Si es una constante definida en .data, usarla directamente en la multiplicación
                    codigo.append("IMUL EAX, ").append(op2Renombrado).append("\n");
                } else {
                    // Si es una variable, realizar la multiplicación directamente
                    codigo.append("MUL ").append(op2Renombrado).append("\n");
                }

                // Almacenar el resultado en una variable auxiliar
                aux = ocuparAuxiliar("longint");
                codigo.append("MOV ").append(aux).append(", EAX\n");
                pila_tokens.push(aux);
                break;
            case ":=":
                String op1tipo = st.getType(op1);

                // Verificar que el tipo es un par definido por el usuario
                if (st.getUse(op1tipo).equals("Nombre de variable par")) {
                    // Asignación de las componentes del par
                    // Mover componente 1 de `op2` a `op1`
                    codigo.append("MOV ECX, ").append(op2Renombrado).append("{1}\n");
                    codigo.append("MOV ").append(op1Renombrado).append("{1}, ECX\n");
            
                    // Mover componente 2 de `op2` a `op1`
                    codigo.append("MOV ECX, ").append(op2Renombrado).append("{2}\n");
                    codigo.append("MOV ").append(op1Renombrado).append("{2}, ECX\n");
            
                } 

            	else if(!op1tipo.equals("longint") && !op1tipo.equals("double")&& !st.getUse(op1tipo).equals("Nombre de tipo de par")){//corroborar que este dentro del rango



                        String etiquetaSinError = "DENTRO_RANGO_" + generarIdUnico();
                        String etiquetaErrorRango = "ERROR_RANGO_" + generarIdUnico();

                        // Comparación con límite inferior
                        codigo.append("MOV EAX, ").append(op2Renombrado).append("\n");  // Cargar `op2` en EAX
                        codigo.append("CMP EAX, ").append(op1Renombrado + "limiteInferior").append("\n");  // Comparar con el límite inferior
                        codigo.append("JL ").append(etiquetaErrorRango).append("\n");    // Salto si `op2` < límite inferior

                        // Comparación con límite superior
                        codigo.append("CMP EAX, ").append(op1Renombrado + "limiteSuperior").append("\n");  // Comparar con el límite superior
                        codigo.append("JG ").append(etiquetaErrorRango).append("\n");    // Salto si `op2` > límite superior
                        codigo.append("JMP ").append(etiquetaSinError).append("\n");
                       

                        // Código en caso de error de rango
                        codigo.append(etiquetaErrorRango).append(":\n");
                        codigo.append("invoke MessageBox, NULL, addr @ERROR_RANGO, addr @ERROR_RANGO, MB_OK\n");
                        codigo.append("invoke ExitProcess, 0\n");

                         // Código en caso de rango válido
                         codigo.append(etiquetaSinError).append(":\n");
                         codigo.append("MOV ").append(op1Renombrado).append(", EAX\n");   // Asignar `op2` a `op1`
            	
            	}else {
            		codigo.append("MOV ECX, ").append(op2Renombrado).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                    codigo.append("MOV ").append(op1Renombrado).append(", ECX\n");
                    break;
            	}
                break;
            case "/":   
            	 // Registro o espacio auxiliar para el resultado
                aux = ocuparAuxiliar("longint"); 

                // Verificar si el divisor es un inmediato (constante) o un registro/memoria
                String divisor = op2Renombrado;

                
                codigo.append("MOV ECX, ").append(divisor).append("\n");
                // Verificación de división por cero directamente con CMP y salto condicional
                codigo.append("CMP ECX").append(", 00h\n"); // Compara el divisor con 0
                String etiquetaSinError = "DIVPOR_CERO" + generarIdUnico(); // Genera una etiqueta única
                codigo.append("JNE ").append(etiquetaSinError).append("\n"); // Si no es cero, salta a etiquetaSinError

                // Código de manejo de error de división por cero
                codigo.append("invoke MessageBox, NULL, addr @ERROR_DIVISION_POR_CERO, addr @ERROR_DIVISION_POR_CERO, MB_OK\n");
                codigo.append("invoke ExitProcess, 0\n"); // Termina el proceso en caso de error

                // Etiqueta para continuar si no hay error
                codigo.append(etiquetaSinError).append(":\n");

                // Preparación para la división
                codigo.append("MOV EAX, ").append(op1Renombrado).append("\n"); // Mueve el dividendo a EAX
                codigo.append("CDQ\n"); // Extiende el signo de EAX a EDX para divisiones con números negativos
                
                // División
                // Si el divisor es una constante, verificar que esté en el formato correcto
                if (divisor.matches("@\\d+")) {
                    // Es una constante en .data, úsala directamente en el código
                    codigo.append("IDIV ECX").append("\n"); // Divide EDX:EAX por el divisor, resultado en EAX
                }else {
                    codigo.append("DIV ECX").append("\n"); // Divide EDX:EAX por el divisor, resultado en EAX
                }
                // Almacenar el resultado en el auxiliar
                codigo.append("MOV ").append(aux).append(", EAX\n"); // Mueve el cociente a aux
                pila_tokens.push(aux); // Guarda aux en la pila de tokens para su uso posterior
                break;
           
        
            case ">=":
            	// Cargar op2 en ECX para la comparación
                codigo.append("MOV ECX, ").append(op2Renombrado).append("\n");  // Mueve el valor de op2 a ECX
                codigo.append("CMP ").append(op1Renombrado).append(", ECX\n");  // Compara op1 con op2 en ECX
                lastComparation = "JL";  // Almacena el tipo de comparación
                break;
            
            case ">":
            	 aux = ocuparAuxiliar("longint"); // Variable auxiliar para almacenar el resultado de la comparación

        	    // Cargar op2 en ECX para la comparación
        	    codigo.append("MOV ECX, ").append(op2Renombrado).append("\n");
        	    codigo.append("CMP ").append(op1Renombrado).append(", ECX\n");   // Comparar op1 con op2
        	    lastComparation = "JLE";
                break;
            
            case "<=":
                codigo.append("MOV ECX, ").append(op2Renombrado).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("CMP ").append(op1Renombrado).append(", ECX\n");
                lastComparation = "JG";
                break;
            
            case "<":
                codigo.append("MOV ECX, ").append(op2Renombrado).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("CMP ").append(op1Renombrado).append(", ECX\n");
                lastComparation = "JGE";
                break;
            case "!=":
                codigo.append("MOV ECX, ").append(op2Renombrado).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("CMP ").append(op1Renombrado).append(", ECX\n");
                lastComparation = "JE";
                break;
            case "=":
                codigo.append("MOV ECX, ").append(op2Renombrado).append("\n"); //muevo al registro EAX ya que esto es lo que dice la filmina, que siempre en las MULT tengo que usar este registro
                codigo.append("CMP ").append(op1Renombrado).append(", ECX\n");
                lastComparation = "JNE";
                break;
            default:
                codigo.append("ERROR, se entro a default en operacion de enteros").append("\n");
                break;
        }
    }
            
    
            
            
    private  void generarOperacionFlotantes(String op1, String op2, String operador) { 
    	String op1Renombrado = renombre(op1);
    	String op2Renombrado = renombre(op2);

        String aux;
         // Verifica si op1 o op2 son accesos a un par (patrón "variable{n}")
         if (esAccesoPar(op1)) {
            op1 = obtenerComponentePar(op1); // Traduce el acceso par a su representación assembler
        }
        if (esAccesoPar(op2)) {
            op2 = obtenerComponentePar(op2);
        }

        System.out.println("op2 antes de if es: "+ op2);
        //Si es LONGINT, la tengo que convertir a DOUBLE
        if (st.getType(op1).equals("longint")|| st.getType(op1).equals("Octal")) {
            if (!operador.equals(":=")){ 
                aux = ocuparAuxiliar("double");
                codigo.append("FILD ").append(op1Renombrado).append("\n");  // Cargar el valor entero en la FPU como double
                codigo.append("FSTP ").append(aux).append("\n"); 
                op1 = aux;
                op1Renombrado = aux;
            }
        }
        if (st.getType(op2).equals("longint")|| st.getType(op2).equals("Octal")) {
            aux = ocuparAuxiliar("double");
            // Convertir el valor de op2Renombrado (entero) a double y almacenarlo en aux
            codigo.append("FILD ").append(op2Renombrado).append("\n");  // Cargar el valor entero en la FPU como double
            codigo.append("FSTP ").append(aux).append("\n");   
            op2 = aux;
            op2Renombrado = aux;

        }
        

        switch (operador) {
            //nunca  va a llegar una operacion AND o OR entre doubles ya que al finalizar cada condicion guardo un ULONG con el resultado de la condicion.
	        case "+":
	        	 // Cargar los operandos en la pila del coprocesador de punto flotante (FPU)
                 codigo.append("FLD ").append(op2Renombrado).append("\n"); // Carga `op2` en la FPU
                 codigo.append("FLD ").append(op1Renombrado).append("\n"); // Carga `op1` en la FPU

                 // Realizar la suma en punto flotante
                 codigo.append("FADD\n");
                 aux = ocuparAuxiliar("double");
                 codigo.append("FSTP "+ aux + "\n");
                 codigo.append("FLD "+ aux + "\n");
                
     
                 // Cargar el valor máximo de un double en la FPU
                 codigo.append("FLD @MAX_DOUBLE \n"); // Carga MAX_DOUBLE en ST(0)
     
                 // Comparar el resultado de la suma (ST(0)) con MAX_DOUBLE
                 codigo.append("FCOM \n"); // Compara ST(0) con ST(1) y actualiza los flags
     
                 // Mover los flags de la FPU a la memoria
                 codigo.append("FSTSW AX \n"); // Mueve los flags a @aux2bytes
                 
                 codigo.append("SAHF \n"); // Carga los flags de AX a los del procesador
     
                 // Verificar si el bit de overflow está activado (bit 11 de los flags)
                 String etiquetaSinOverflow = "LABEL_NO_OVERFLOW_" + generarIdUnico(); // Etiqueta sin overflow
                 String etiquetaOverflow = "LABEL_OVERFLOW_" + generarIdUnico();       // Etiqueta para overflow
     
                 // Si el flag de overflow está activado (bit 11), salta a la etiqueta de overflow
                 codigo.append("JBE ").append(etiquetaOverflow).append("\n"); // Salta si overflow
     
                 // Si no hay overflow, continua con el código normal
                 codigo.append("JMP ").append(etiquetaSinOverflow).append("\n"); 
     
                 // Código de manejo de error de overflow
                 codigo.append(etiquetaOverflow).append(":\n");
                 codigo.append("invoke MessageBox, NULL, addr @ERROR_OVERFLOW, addr @ERROR_OVERFLOW, MB_OK\n");
                 codigo.append("invoke ExitProcess, 0\n"); // Termina el proceso en caso de overflow
     
                 // Etiqueta para continuar si no hay overflow
                 codigo.append(etiquetaSinOverflow).append(":\n");
     
     
                 pila_tokens.push(aux); // Guarda `aux` en la pila de tokens para su uso posterior
                 break;
            case "-":
                codigo.append("FLD ").append(op1Renombrado).append("\n"); //apilo primero el op2 ya que quiero que me quede como el segundo que agarro para las operaciones que no son conmutativas
                codigo.append("FLD ").append(op2Renombrado).append("\n");

                codigo.append("FSUB\n");
                aux = ocuparAuxiliar("double");
                codigo.append("FSTP ").append(aux).append("\n");
                pila_tokens.push(aux);
                break;
            
            case "*":
                codigo.append("FLD ").append(op2Renombrado).append("\n"); //apilo primero el op2 ya que quiero que me quede como el segundo que agarro para las operaciones que no son conmutativas
                codigo.append("FLD ").append(op1Renombrado).append("\n");
                codigo.append("FMUL\n");
                aux = ocuparAuxiliar("double");
                codigo.append("FSTP ").append(aux).append("\n");
                pila_tokens.push(aux);
                break;
            
            case ":=":
                String op2tipo = st.getType(op2);
                String op1tipo = st.getType(op1);
                if(op1tipo.equals("Octal")) {
                	op1tipo="longint";
                }
                if(op2tipo.equals("Octal")) {
                	op2tipo="longint";
                }
                System.out.println("op2: " + op2);
                System.out.println("op1: " + op1);
                System.out.println("op2tipo: " + op2tipo);
                System.out.println("op1tipo: " + op1tipo);
            
                if (st.getUse(op2tipo).equals("Nombre de variable par")) {
                    codigo.append("FLD ").append(op2Renombrado).append("{1}\n");
                    codigo.append("FSTP ").append(op1Renombrado).append("{1}\n");
            
                    codigo.append("FLD ").append(op2Renombrado).append("{2}\n");
                    codigo.append("FSTP ").append(op1Renombrado).append("{2}\n");
                }
                else if (!op1tipo.equals("longint") && !op1tipo.equals("double") && !st.getUse(op1tipo).equals("Nombre de tipo de par")) {
                
                    String etiquetaSinError = "DENTRO_RANGO_" + generarIdUnico();
                    String etiquetaErrorRango = "ERROR_RANGO_" + generarIdUnico();
                    
                    codigo.append("FLD " + op1Renombrado+"limiteInferior").append("\n");

                    
                    // Comparación con límite inferior
                    codigo.append("FLD ").append(op2Renombrado).append("\n");  // Cargar `op2` en ST(0)

                    codigo.append("FCOMPP").append("\n");
                    codigo.append("FSTSW AX\n");
                    codigo.append("SAHF\n");
                    codigo.append("JB ").append(etiquetaErrorRango).append("\n");  // Salto corto a error si ST(0) < ST(1)
                    
                    codigo.append("FLD " + op1Renombrado+"limiteSuperior").append("\n");  

                    // Comparación con límite superior
                    codigo.append("FLD ").append(op2Renombrado).append("\n");  // Cargar `op2` en ST(0)
                    codigo.append("FCOMPP").append("\n");
                    codigo.append("FSTSW AX\n");
                    codigo.append("SAHF\n");
                    codigo.append("JA ").append(etiquetaErrorRango).append("\n");  // Salto corto a error si ST(0) > ST(1)
                    codigo.append("JMP ").append(etiquetaSinError).append("\n");
                    
                   
                
                    // Código en caso de error de rango
                    codigo.append(etiquetaErrorRango).append(":\n");
                    codigo.append("invoke MessageBox, NULL, addr @ERROR_RANGO, addr @ERROR_RANGO, MB_OK\n");
                    codigo.append("invoke ExitProcess, 0\n");

                     // Código en caso de rango válido
                     codigo.append(etiquetaSinError).append(":\n");
                     codigo.append("FSTP ").append(op2Renombrado).append("\n");  // Cargar `op2` en ST(0)
                    
                    }
                 else {
                        codigo.append("FLD ").append(op2Renombrado).append("\n");
                        codigo.append("FSTP ").append(op1Renombrado).append("\n");
                        break;
                    }
                    
                break;
            case "/":
                aux = ocuparAuxiliar("double"); // Registro o espacio auxiliar para el resultado en double

                // Verificación de división por cero utilizando el coprocesador de punto flotante
                codigo.append("FLD ").append(op2Renombrado).append("\n"); // Carga `op2` (divisor) en la pila de FPU

                // Comparación con cero para verificar si el divisor es cero
                String etiquetaSinError = "DIV_POR_CERO_" +  generarIdUnico(); // Genera una etiqueta única
                String _cero = ocuparAuxiliar("double");
                codigo.append("FILD ").append(_cero).append("\n"); // Carga el valor de cero en el coprocesador de punto flotante
                codigo.append("FCOM \n"); // Compara ST(0) (el divisor) con ST(1) (cero)
                codigo.append("FSTSW AX\n"); // Mueve los flags de la FPU a AX
                codigo.append("SAHF\n"); // Carga los flags de AX en los del procesador

                // Si ST(0) es cero, significa que el divisor es cero
                codigo.append("JNZ ").append(etiquetaSinError).append("\n"); // Salta a etiquetaSinError si ST(0) == 0 (divisor es cero)

                // Código de manejo de error de división por cero
                codigo.append("invoke MessageBox, NULL, addr @ERROR_DIVISION_POR_CERO, addr @ERROR_DIVISION_POR_CERO, MB_OK\n");
                codigo.append("invoke ExitProcess, 0\n"); // Termina el proceso en caso de error

                // Etiqueta para continuar si no hay error
                codigo.append(etiquetaSinError).append(":\n");

                // Preparación para la división en punto flotante
                codigo.append("FLD ").append(op1Renombrado).append("\n"); // Apila `op2` como divisor
                codigo.append("FLD ").append(op2Renombrado).append("\n"); // Apila `op1` como dividendo
                codigo.append("FDIV\n"); // Realiza la división ST(1) = ST(1) / ST y almacena en ST(1)

                // Almacenar el resultado de la división en `aux`
                codigo.append("FSTP ").append(aux).append("\n"); // Mueve el resultado a `aux`
                pila_tokens.push(aux); // Guarda el resultado en la pila de tokens para su uso posterior
                break; // Guarda `aux` en la pila de tokens para su uso posterior
            
            
           
            
            case ">=":
                codigo.append("FLD ").append(op2Renombrado).append("\n"); 
                codigo.append("FLD ").append(op1Renombrado).append("\n"); 

                codigo.append("FCOM ").append("\n");
                codigo.append("FSTSW AX").append("\n");// cargo la palabra de estado en la memoria
                codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                lastComparation = "JB";                    
                break;
            
            case ">":
                    codigo.append("FLD ").append(op2Renombrado).append("\n"); 
                    codigo.append("FLD ").append(op1Renombrado).append("\n"); 

                    codigo.append("FCOM ").append("\n");
                    codigo.append("FSTSW AX").append("\n");// cargo la palabra de estado en la memoria
                    codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                    lastComparation = "JBE";  
            break;
            
            case "<=":
                    codigo.append("FLD ").append(op2Renombrado).append("\n"); 
                    codigo.append("FLD ").append(op1Renombrado).append("\n"); 

                    codigo.append("FCOM ").append("\n");
                    codigo.append("FSTSW AX").append("\n");// cargo la palabra de estado en la memoria
                    codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                    lastComparation = "JA";  
            break;
            
            case "<":
                codigo.append("FLD ").append(op2Renombrado).append("\n"); 
                codigo.append("FLD ").append(op1Renombrado).append("\n"); 

                codigo.append("FCOM ").append("\n");
                codigo.append("FSTSW AX").append("\n");// cargo la palabra de estado en la memoria
                codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                lastComparation = "JAE";  
            break;
            case "!=":
                codigo.append("FLD ").append(op2Renombrado).append("\n"); 
                codigo.append("FLD ").append(op1Renombrado).append("\n"); 

                codigo.append("FCOM ").append("\n");
                codigo.append("FSTSW AX").append("\n");// cargo la palabra de estado en la memoria
                codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                lastComparation = "JE";  
            break;

            case "=":
                codigo.append("FLD ").append(op2Renombrado).append("\n"); 
                codigo.append("FLD ").append(op1Renombrado).append("\n"); 

                codigo.append("FCOM ").append("\n");
                codigo.append("FSTSW AX").append("\n");// cargo la palabra de estado en la memoria
                codigo.append("SAHF").append("\n"); //Almacena en los 8 bits menos significativos del regisro de indicadores el valor del registro AH

                lastComparation = "JNE";  
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
        } else if (!lastComparation.equals("JAE") && !lastComparation.equals("JB") && !lastComparation.equals("JBE")&& !lastComparation.equals("JA")&& !lastComparation.equals("JE")&& !lastComparation.equals("JNE")){
        	int direccionInt = Integer.parseInt(direccion);
            codigo.append(salto).append(" ").append(SymbolTable.polaca.get(direccionInt).replace("@","").replace("&","")).append("\n");
        } else {
    
            // Generar el salto si cumple la condición de >=
            codigo.append(salto + " L").append(direccion).append("\n"); // Salta a Ldireccion si >= es verdadero
        }

        lastComparation = "";
    }

    private void generarLlamadoFuncion(String nombreFuncion) {
        String parametroReal = pila_tokens.pop();
        String funcion = renombre(nombreFuncion);
        
        CaracteristicaFuncion cF = st.getCaracteristicaFuncion(nombreFuncion);
        // Asumimos que el tipo de parametro y retorno están disponibles
        String tipoParametro = cF.getTipoParametro();
        String tipoRetorno = cF.getTipoDevuelto();
    
        // Cargar el parámetro en el registro adecuado según el tipo
        if (tipoParametro.equals("double") || st.getTipoSubrango(tipoParametro+":"+st.getAmbitoByKey(tipoParametro)).getTipoBase().equals("double")) {
            codigo.append("MOVSD xmm0, ").append(parametroReal).append("\n");  // Carga double
        } else if (tipoParametro.equals("longint") || st.getTipoSubrango(tipoParametro+":"+st.getAmbitoByKey(tipoParametro)).getTipoBase().equals("longint")) {
            codigo.append("MOV EAX, ").append(parametroReal).append("\n");      // Carga longint
        }
    
        // Llamada a la función
        codigo.append("CALL ").append(funcion).append("\n");
    
        // Guardar el retorno según el tipo de retorno
        if (tipoRetorno.equals("double")) {
            codigo.append("MOVSD @ret@, xmm0\n");      // Retorno double en xmm0
        } else if (tipoRetorno.equals("longint")) {
            codigo.append("MOV @ret@, EAX\n");         // Retorno longint en EAX
        }
    
        pila_tokens.push("@ret@"); // Pusheo el valor de retorno
    }
    

    private String renombre(String token) {

        // Si es una constante, le cambio de nombre al cual fue declarada
        if(st.getUse(token)!=null) {
        	if (st.getUse(token).equals("Constante")) {
                return "@" + token.replace('.', '@').replace('-', '$').replace('+', '@').replace('d', 'e');
            } else if (st.getUse(token).equals("Nombre de variable") || st.getUse(token).equals("Nombre de funcion") || st.getUse(token).equals("Nombre de variable par")|| st.getUse(token).equals("Nombre de parametro")) {
                return "_" + token;
            } else {
                return token;
            }
        }
        return "";
    	
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
        String renombrado= renombre(topePila);
        // Verificar si el tipo es "double" o no
        if (st.getType(topePila).equals("double")) {
            // Cargar el valor al registro de coma flotante
            codigo.append("FLD ").append(renombrado).append("\n");  // FLD carga el valor en el tope de la pila de FPU
        } else {
            // Cargar el valor al registro EAX (para enteros)
            codigo.append("MOV EAX, ").append(renombrado).append("\n"); // Cargar el valor en EAX para el retorno de enteros
        }

        // Instrucción de retorno
        codigo.append("RET\n");
    }
}
