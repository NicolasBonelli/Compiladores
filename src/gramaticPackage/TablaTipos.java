package gramaticPackage;
import Paquetecompi.Lexer;
import Paquetecompi.SymbolTable;
public class TablaTipos {
	
	private static final String DOUBLE_TYPE= "double";
	private static final String LONGINT_TYPE= "longint";
	private static final String ERROR_TYPE = "error";
	private SymbolTable st;
	/*ESTRUCTURA MATRICES
	 *           longint  double
	 * longint 
	 * double
	 * 
	 */
	private static final String[][] tiposSumaResta = { { LONGINT_TYPE, DOUBLE_TYPE},  
            										{ DOUBLE_TYPE, DOUBLE_TYPE}};
            										
	private static final String[][] tiposMultDiv = { { LONGINT_TYPE, DOUBLE_TYPE },  //PREGUNTAR SI longint a longint da double
													 { DOUBLE_TYPE, DOUBLE_TYPE }}; //PREGUNTAR SI DOUBLE CON DOUBLE HAY QUE CHEQUEAR
													  
	private static final String[][] tiposComparadores = { { LONGINT_TYPE, DOUBLE_TYPE}, 
               											  { DOUBLE_TYPE, DOUBLE_TYPE }};
               											  
	private static final String[][] tiposAsig = { { LONGINT_TYPE, ERROR_TYPE}, 
												  { DOUBLE_TYPE, DOUBLE_TYPE}};
	
	public TablaTipos(SymbolTable st) {
		this.st=st;
	}
	public  String getTipoAbarcativo(String op1, String op2, String operador){
        // mirar en la tabla del operando que tipo queda entre esos 2 tipos
        String tipoOp1 = getTipo(op1);
        String tipoOp2 = getTipo(op2);
        if(tipoOp1.equals("Octal")) {
        	tipoOp1="longint";
        }
        if(tipoOp2.equals("Octal")) {
        	tipoOp2="longint";
        }
        if(!tipoOp1.equals("longint") && !tipoOp1.equals("double")) {
        	tipoOp1= st.getTipoSubrango(tipoOp1+":"+st.getAmbitoByKey(tipoOp1)).getTipoBase();
        }
        if(!tipoOp2.equals("longint") && !tipoOp2.equals("double")){
        	tipoOp2= st.getTipoSubrango(tipoOp2+":"+st.getAmbitoByKey(tipoOp2)).getTipoBase();
        }
        String tipoFinal = tipoResultante(tipoOp1, tipoOp2, operador);
        if (tipoFinal.equals(ERROR_TYPE)) { //si es error
        	System.err.println("No se puede realizar la operacion " + operador + " entre los tipos " + tipoOp1 + " y " + tipoOp2+ " en linea: "+Lexer.nmrLinea);
            GeneradorCodigo.errorSemantico=true;
        }
        return tipoFinal;
    }

    public String getTipo(String op) {
        if (st.getUse(op).equals("Nombre de funcion") ) { //el operador es un llamado a funcion entonces tengo que saber su tipo
            CaracteristicaFuncion funcion = st.getCaracteristicaFuncion(op);
            return funcion.getTipoDevuelto();             
        }
        if (op.contains("{")) {
            // Remueve desde la primera llave hasta el final
            op = op.substring(0, op.indexOf("{"));
        }
        String tipo= st.getType(op);
        return tipo; 
    }

    private  String tipoResultante(String op1, String op2, String operador) {
        int fil = getNumeroTipo(op1);
        int col = getNumeroTipo(op2);

        switch (operador) {
            case ("+"):
            case ("-"):
                return tiposSumaResta[fil][col];
            case ("*"):
            case ("/"):
                return tiposMultDiv[fil][col];
            case (":="):
                return tiposAsig[fil][col];
            case ("<="):
            case ("<"):
            case (">="):
            case (">"):
            case ("!="):
            case ("="):
                return tiposComparadores[fil][col];
            default:
                return ERROR_TYPE;
        }
    }

    private int getNumeroTipo(String tipo) {
        if (tipo.equals(LONGINT_TYPE)) return 0;
        else if (tipo.equals(DOUBLE_TYPE)) return 1;
        else return 2;//error
    }
	
	
}
