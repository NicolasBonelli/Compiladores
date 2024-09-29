//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 2 "gramatica.y"
    package gramaticPackage;
    import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

import Paquetecompi.Lexer;
import Paquetecompi.Pair;
import Paquetecompi.SymbolTable;
    
 
    /* Clase para almacenar la información de los subrangos.*/
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

/*#line 66 "Parser.java"*/



//#line 62 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short IF=257;
public final static short THEN=258;
public final static short ELSE=259;
public final static short BEGIN=260;
public final static short END=261;
public final static short END_IF=262;
public final static short OUTF=263;
public final static short TYPEDEF=264;
public final static short FUN=265;
public final static short RET=266;
public final static short REPEAT=267;
public final static short WHILE=268;
public final static short PAIR=269;
public final static short GOTO=270;
public final static short LONGINT=271;
public final static short DOUBLE=272;
public final static short MENOR_IGUAL=273;
public final static short MAYOR_IGUAL=274;
public final static short DISTINTO=275;
public final static short T_ASIGNACION=276;
public final static short T_CADENA=277;
public final static short T_ID=278;
public final static short T_CTE=279;
public final static short T_ETIQUETA=280;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    0,    1,    1,    1,    2,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,   13,    3,
    4,    4,   15,   15,   15,    9,    9,    9,    9,    9,
   16,   17,   17,   17,   18,   18,   14,   14,   14,    6,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,    7,    7,    7,    7,    7,    7,    8,
    8,    8,    8,    8,    8,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   20,   20,   20,   20,   20,   20,   19,   19,
   19,   19,   21,   21,   21,   21,   21,   21,    5,    5,
   22,   22,   22,   22,   23,   23,   24,   24,   24,   10,
   10,   10,   10,   25,   25,   26,   27,   27,   27,   27,
   27,   27,   27,   27,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   28,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    5,    0,    5,
    3,    2,    3,    1,    1,    7,    7,    7,    7,    7,
    2,    3,    3,    0,    1,    1,    1,    1,    1,    8,
   10,    7,    9,    7,    9,    7,    9,    7,    8,    6,
    8,    8,   10,    7,    6,    5,    6,    5,    7,    5,
    5,    4,    4,    4,    5,    6,    7,    7,    6,    5,
    5,    5,    7,    6,    6,    6,    6,    6,    6,    5,
    5,    5,    5,    6,    6,    7,    2,    1,    3,    3,
    2,    2,    1,    1,    1,    1,    1,    1,    4,    3,
    3,    3,    1,    1,    1,    3,    4,    4,    4,    3,
    2,    2,    2,    4,    4,    1,    3,    3,    3,    3,
    1,    1,    1,    1,    3,    3,    3,    3,    1,    1,
    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    3,    0,    5,    0,    0,    0,
    0,    0,   38,   37,    0,   17,    0,    7,    9,   10,
   11,   12,   13,   14,   15,   16,    0,    0,  104,    1,
  134,   93,   94,   95,    0,  129,    0,    0,   97,   98,
   96,    0,    0,    0,  131,  132,  133,    0,    0,    0,
    0,    0,    0,    0,   35,   36,    0,  113,    0,  111,
    0,    4,    8,   25,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   39,    0,    0,    0,    0,    0,    0,    0,    0,  110,
    0,    0,    0,    0,    0,    0,   21,    0,    0,    0,
    0,  102,    0,    0,  121,  123,    0,    0,  124,    0,
    0,    0,    0,    0,  127,  128,    0,    0,    0,   64,
    0,    0,    0,    0,    0,    0,    0,    0,   88,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  109,  107,  108,    0,    0,    0,   23,   99,    0,  115,
  114,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   60,   65,   61,   71,   70,    0,    0,    0,    0,    0,
    0,   87,   80,   82,    0,   81,    0,    0,   18,   20,
   56,    0,    0,    0,   58,    0,    0,    0,    0,    0,
    0,    0,    0,  119,  120,    0,    0,    0,    0,    0,
    0,    0,    0,   50,    0,   78,    0,   79,    0,   69,
    0,    0,   66,   76,   77,    0,   57,    0,   31,    0,
    0,    0,    0,    0,    0,    0,    0,   44,    0,   48,
    0,    0,    0,    0,   46,    0,   67,   68,   73,    0,
    0,    0,   59,   54,   28,   26,    0,   32,   27,   33,
   30,   29,    0,   49,   52,    0,   40,    0,   51,   83,
    0,    0,    0,   45,    0,    0,   47,   85,   84,    0,
   53,   41,   86,
};
final static short yydgoto[] = {                          4,
   55,   17,   56,   19,   20,   21,   22,   23,   24,   25,
   26,   42,  190,   27,   67,  197,  198,   57,   43,  141,
   44,   28,  110,   45,   46,  117,  118,   47,
};
final static short yysindex[] = {                      -148,
    0,  668, -180,    0,    0,  431,    0,  -14,  425,   -4,
  574,  -43,    0,    0,  -62,    0,  690,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -150,  -33,    0,    0,
    0,    0,    0,    0,  -27,    0,   58,  479,    0,    0,
    0,  670, -188,   58,    0,    0,    0,  397,  131, -244,
 -171, -254,   58,   69,    0,    0, -167,    0,   66,    0,
   36,    0,    0,    0,  -30,   94,   -7,   58, -143,  104,
   84, -118,  106,   58,   58,   58,   58,   58,   58,  640,
  101,  110,  -27,   93,  117,  138,    0, -106, -102,  448,
    0, -111,  -78,  136,  144,  154,  611,  174,  550,    0,
   91,  140,  153,  258, -244, -244,    0,   25,  101,    7,
  -62,    0,  274,  -62,    0,    0,  276,  160,    0,  640,
  593,  101,   84,   84,    0,    0,  101, -146,  262,    0,
  264,  289,  305,  308,  307,  312,  322,  116,    0,  -15,
  345,  -35,  355,  145,  146,  363,  393,  611,  504,  384,
    0,    0,    0, -244,  166,  176,    0,    0,   58,    0,
    0,   78,   78,   78,   78,  -85,  529,  -70,  640,  399,
    0,    0,    0,    0,    0,  -52,   12,  185,  405,  422,
  191,    0,    0,    0,  409,    0,  415,  430,    0,    0,
    0,  440,  436,  458,    0,  -40,  167,  171,  460,  465,
  101,   90,   90,    0,    0,  640,  459,  269,  467, -142,
  640,  476,  280,    0,  484,    0,  492,    0,  497,    0,
  -26,  516,    0,    0,    0,  517,    0,  521,    0, -180,
 -180, -244, -180, -244, -180, -180,  323,    0,  525,    0,
  527,  640,  528,  326,    0,  533,    0,    0,    0,  472,
  327,   10,    0,    0,    0,    0,  324,    0,    0,    0,
    0,    0,  563,    0,    0, -197,    0,  567,    0,    0,
  505,  506,  353,    0,  583,  587,    0,    0,    0,  526,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  652,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  446,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -39,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  129,    0,
    0,    0,    0,    0,    0,   63,  149,    0,    0,    0,
  -12,    0,    0,    0,    0,    0,    0,    0,  -16,    0,
   70,    0,  637,    0,    0,    0,   -3,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   83,  169,
   -9,    0,    0,  702,    0,    0,    0,  617,    0,    0,
    0,   80,   13,   38,    0,    0,  100,    0,  189,    0,
    0,  216,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  241,    0,    0,    0,    0,
    0,    0,    0,  318,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  266,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  109,  128,  141,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  291,    0,  311,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  337,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  357,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  377,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
  462,    0,   85,    0,    0,    0,    0,    0,    0,    0,
    0,   16,    0,  636,    0,  -68,    0,  570,   39,  -79,
  620,    0,    0,   -2,    0,    0,  352,   -8,
};
final static int YYTABLESIZE=970;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
  230,  130,  130,  130,  130,  130,  216,  130,   29,  105,
   69,  140,   70,  143,   29,   60,   94,   95,  251,  130,
  130,  130,  130,  184,   91,   48,   13,   14,  135,  181,
  135,  135,  135,   91,  101,   53,  108,  131,  131,  131,
  104,  131,   91,  131,  140,   87,  135,  135,  135,  135,
  159,  107,   71,  125,  273,  125,  125,  125,  275,   81,
   61,  119,  185,   86,  276,  158,  112,  116,   96,   80,
  218,  125,  125,  125,  125,    1,   73,   29,  126,    2,
  126,  126,  126,  109,  102,  103,   18,  140,   98,  122,
  123,  124,  125,  126,  127,   61,  126,  126,  126,  126,
   99,   63,   37,   24,   93,   64,   24,    1,   97,  182,
   92,    2,  169,  241,   65,  170,  242,   29,   29,  243,
   90,   24,   37,  105,  100,   77,  105,   66,   92,    3,
   78,  164,   85,  106,  111,  147,  165,  150,   90,  120,
   89,  105,   77,   75,  139,   76,  121,   78,   37,  106,
  129,  130,  106,  119,  119,  119,  119,  131,   89,  116,
  116,  116,  116,  258,   29,  260,   29,  106,  117,  112,
  117,  133,  117,  206,  201,  134,  207,  139,  132,   77,
   75,  118,   76,  118,   78,  118,  192,  194,  211,   22,
   90,  212,   13,   14,  146,   77,   75,  144,   76,   91,
   78,  164,  162,   29,  163,  145,  165,  231,   29,  100,
  232,  233,   58,  148,  234,  151,  130,  130,  130,  130,
  139,  130,  130,  130,  130,  215,  130,  130,  130,   63,
  130,  130,  130,  130,  130,  130,   59,  229,  130,   29,
  130,   91,   68,  135,  135,  135,  135,  104,  135,  135,
  135,  135,  250,  135,  135,  135,   62,  135,  135,  135,
  135,  135,  135,  180,  152,  135,  101,  135,  125,  125,
  125,  125,  104,  125,  125,  125,  125,  153,  125,  125,
  125,   19,  125,  125,  125,  125,  125,  125,  272,  217,
  125,  101,  125,  126,  126,  126,  126,  154,  126,  126,
  126,  126,  157,  126,  126,  126,   72,  126,  126,  126,
  126,  126,  126,   31,  160,  126,  161,  126,   24,   24,
  171,   24,  172,   24,   24,   24,   24,   92,   24,   24,
   24,   74,   24,   24,   24,   35,   36,   90,  105,  105,
   24,  105,   24,  105,  105,  105,  105,  173,  105,  105,
  105,   75,  105,  105,  105,  114,  115,   89,   34,  113,
  105,   34,  105,  174,  106,  106,  175,  106,  176,  106,
  106,  106,  106,  177,  106,  106,  106,   55,  106,  106,
  106,  114,  115,  178,  112,  112,  106,  112,  106,  112,
  112,  112,  112,  179,  112,  112,  112,   42,  112,  112,
  112,   88,   89,  183,   22,   22,  112,   22,  112,   22,
   22,   22,   22,  186,   22,   22,   22,   43,   22,   22,
   22,  189,  187,  188,  100,  100,   22,  100,   22,  100,
  100,  100,  100,  191,  100,  100,  100,   84,  100,  100,
  100,   37,  195,  199,   63,   63,  100,   63,  100,   63,
   63,   63,   63,  200,   63,   63,   63,  214,   63,   63,
   63,    5,  219,  220,   30,  221,   63,  223,   63,  222,
   38,   62,   62,  224,   62,   37,   62,   62,   62,   62,
  226,   62,   62,   62,   52,   62,   62,   62,  225,  103,
   39,   41,   40,   62,  227,   62,   19,   19,  228,   19,
  235,   19,   19,   19,   19,  236,   19,   19,   19,  138,
   19,   19,   19,  202,  203,  204,  205,  238,   19,   72,
   19,   72,   72,   37,   72,  240,   72,   72,   72,   72,
  239,   72,   72,   72,  245,   72,   72,   72,   39,   41,
   40,  246,  247,   72,  193,   72,   74,   74,   37,   74,
  248,   74,   74,   74,   74,  249,   74,   74,   74,  252,
   74,   74,   74,   39,   41,   40,   75,   75,   74,   75,
   74,   75,   75,   75,   75,  253,   75,   75,   75,  254,
   75,   75,   75,  264,  263,  265,  267,  268,   75,  149,
   75,  269,   55,   55,   37,   55,  270,   55,   55,   55,
   55,  229,   55,   55,   55,  271,   55,   55,   55,   39,
   41,   40,   42,   42,   55,   42,   55,   42,   42,   42,
   42,  274,   42,   42,   42,  277,   42,   42,   42,  278,
  279,  280,   43,   43,   42,   43,   42,   43,   43,   43,
   43,  281,   43,   43,   43,  282,   43,   43,   43,  128,
  283,    2,   31,    6,   43,   37,   43,  116,    0,    8,
    9,   79,   10,   11,    0,    0,   12,   13,   14,    0,
   39,   41,   40,   82,   83,   36,   16,  130,  130,  130,
  103,  130,    0,  130,    0,   92,   31,    0,    0,  166,
  168,  255,  256,   49,  259,    0,  261,  262,    0,    0,
   50,   39,   51,   32,   33,   34,    0,    0,   35,   36,
   39,   77,   75,    0,   76,    0,   78,    0,  135,  136,
    0,  103,    0,   39,    0,  137,    0,    0,  142,   39,
   41,   40,    0,    0,   31,    0,  210,    0,  213,    0,
  155,  156,  122,  122,  122,    0,  122,    0,  122,    0,
    0,   32,   33,   34,    0,    0,   35,   36,    0,   31,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  237,   32,   33,   34,    0,
  244,   35,   36,    0,    1,    6,    0,  208,    2,  196,
  209,    8,    9,    0,   10,   11,    0,    0,   12,   13,
   14,    0,    0,    0,    0,   31,   15,    0,   16,    0,
    0,  266,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   32,   33,   34,    0,    0,   35,   36,    1,
    6,    0,    0,    2,    0,    0,    8,    9,    0,   10,
   11,   54,    0,   12,   13,   14,    0,    0,    1,    6,
  167,   15,    2,   16,    0,    8,    9,    0,   10,   11,
    0,    0,   12,   13,   14,    0,   31,  257,    0,  257,
   15,    0,   16,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   32,   33,   34,    0,    0,   35,   36,
    0,    0,   39,    0,    0,    1,    6,    0,    0,    2,
    0,   39,    8,    9,    0,   10,   11,    0,    0,   12,
   13,   14,  103,    0,   39,    0,    0,   15,    0,   16,
    0,    0,    0,    0,    6,   74,    0,    0,    7,    0,
    8,    9,    0,   10,   11,    0,    0,   12,   13,   14,
    0,    0,   32,   33,   34,   15,    6,   16,    0,    0,
   62,    0,    8,    9,    0,   10,   11,    0,    0,   12,
   13,   14,    0,    0,    0,    0,    0,   15,    0,   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   41,   41,   42,   43,   44,   45,   59,   47,   11,   40,
   44,  123,   40,   93,   17,   59,  271,  272,   45,   59,
   60,   61,   62,   59,   41,   40,  271,  272,   41,   45,
   43,   44,   45,  278,   44,   40,   44,   41,   42,   43,
   44,   45,   59,   47,  123,   48,   59,   60,   61,   62,
   44,   59,   37,   41,   45,   43,   44,   45,  256,   44,
  123,   70,  142,   48,  262,   59,   69,   70,   53,  258,
   59,   59,   60,   61,   62,  256,   38,   80,   41,  260,
   43,   44,   45,   68,   49,   50,    2,  123,  256,   74,
   75,   76,   77,   78,   79,  123,   59,   60,   61,   62,
  268,   17,   45,   41,  276,  256,   44,  256,   40,  125,
   41,  260,  259,  256,  265,  262,  259,  120,  121,  262,
   41,   59,   45,   41,   59,   42,   44,  278,   59,  278,
   47,   42,   48,   40,  278,   97,   47,   99,   59,  258,
   41,   59,   42,   43,  256,   45,   41,   47,   45,   41,
   41,   59,   44,  162,  163,  164,  165,   41,   59,  162,
  163,  164,  165,  232,  167,  234,  169,   59,   41,   41,
   43,  278,   45,  259,  159,  278,  262,  256,   41,   42,
   43,   41,   45,   43,   47,   45,  148,  149,  259,   41,
   60,  262,  271,  272,   41,   42,   43,   62,   45,  278,
   47,   42,   43,  206,   45,   62,   47,   41,  211,   41,
   44,   41,  256,   40,   44,  125,  256,  257,  258,  259,
  256,  261,  262,  263,  264,  278,  266,  267,  268,   41,
  270,  271,  272,  273,  274,  275,  280,  278,  278,  242,
  280,  258,  276,  256,  257,  258,  259,  278,  261,  262,
  263,  264,  279,  266,  267,  268,   41,  270,  271,  272,
  273,  274,  275,  279,  125,  278,  276,  280,  256,  257,
  258,  259,  276,  261,  262,  263,  264,  125,  266,  267,
  268,   41,  270,  271,  272,  273,  274,  275,  279,  278,
  278,  256,  280,  256,  257,  258,  259,   40,  261,  262,
  263,  264,  278,  266,  267,  268,   41,  270,  271,  272,
  273,  274,  275,  256,   41,  278,   41,  280,  256,  257,
   59,  259,   59,  261,  262,  263,  264,  258,  266,  267,
  268,   41,  270,  271,  272,  278,  279,  258,  256,  257,
  278,  259,  280,  261,  262,  263,  264,   59,  266,  267,
  268,   41,  270,  271,  272,  278,  279,  258,   41,  256,
  278,   44,  280,   59,  256,  257,   59,  259,   62,  261,
  262,  263,  264,   62,  266,  267,  268,   41,  270,  271,
  272,  278,  279,   62,  256,  257,  278,  259,  280,  261,
  262,  263,  264,  278,  266,  267,  268,   41,  270,  271,
  272,  271,  272,   59,  256,  257,  278,  259,  280,  261,
  262,  263,  264,   59,  266,  267,  268,   41,  270,  271,
  272,   59,  278,  278,  256,  257,  278,  259,  280,  261,
  262,  263,  264,   41,  266,  267,  268,   41,  270,  271,
  272,   45,   59,  278,  256,  257,  278,  259,  280,  261,
  262,  263,  264,  278,  266,  267,  268,   59,  270,  271,
  272,    0,  278,   59,    3,   44,  278,   59,  280,  279,
   40,  256,  257,   59,  259,   45,  261,  262,  263,  264,
   41,  266,  267,  268,   60,  270,  271,  272,   59,   44,
   60,   61,   62,  278,   59,  280,  256,  257,   41,  259,
   41,  261,  262,  263,  264,   41,  266,  267,  268,   62,
  270,  271,  272,  162,  163,  164,  165,   59,  278,   41,
  280,  256,  257,   45,  259,   59,  261,  262,  263,  264,
  262,  266,  267,  268,   59,  270,  271,  272,   60,   61,
   62,  262,   59,  278,   41,  280,  256,  257,   45,  259,
   59,  261,  262,  263,  264,   59,  266,  267,  268,   44,
  270,  271,  272,   60,   61,   62,  256,  257,  278,  259,
  280,  261,  262,  263,  264,   59,  266,  267,  268,   59,
  270,  271,  272,   59,  262,   59,   59,  262,  278,   40,
  280,   59,  256,  257,   45,  259,  125,  261,  262,  263,
  264,  278,  266,  267,  268,  279,  270,  271,  272,   60,
   61,   62,  256,  257,  278,  259,  280,  261,  262,  263,
  264,   59,  266,  267,  268,   59,  270,  271,  272,  125,
  125,  279,  256,  257,  278,  259,  280,  261,  262,  263,
  264,   59,  266,  267,  268,   59,  270,  271,  272,   80,
  125,    0,  256,  257,  278,   45,  280,   41,   -1,  263,
  264,   42,  266,  267,   -1,   -1,  270,  271,  272,   -1,
   60,   61,   62,  277,  278,  279,  280,   41,   42,   43,
   44,   45,   -1,   47,   -1,   50,  256,   -1,   -1,  120,
  121,  230,  231,  269,  233,   -1,  235,  236,   -1,   -1,
  276,  256,  278,  273,  274,  275,   -1,   -1,  278,  279,
  265,   42,   43,   -1,   45,   -1,   47,   -1,  271,  272,
   -1,  276,   -1,  278,   -1,  278,   -1,   -1,   93,   60,
   61,   62,   -1,   -1,  256,   -1,  167,   -1,  169,   -1,
  105,  106,   41,   42,   43,   -1,   45,   -1,   47,   -1,
   -1,  273,  274,  275,   -1,   -1,  278,  279,   -1,  256,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  206,  273,  274,  275,   -1,
  211,  278,  279,   -1,  256,  257,   -1,  259,  260,  154,
  262,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,   -1,  256,  278,   -1,  280,   -1,
   -1,  242,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,  256,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,  256,  257,
  258,  278,  260,  280,   -1,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,  256,  232,   -1,  234,
  278,   -1,  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,
   -1,   -1,  256,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,  265,  263,  264,   -1,  266,  267,   -1,   -1,  270,
  271,  272,  276,   -1,  278,   -1,   -1,  278,   -1,  280,
   -1,   -1,   -1,   -1,  257,  256,   -1,   -1,  261,   -1,
  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,  273,  274,  275,  278,  257,  280,   -1,   -1,
  261,   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,
  271,  272,   -1,   -1,   -1,   -1,   -1,  278,   -1,  280,
};
}
final static short YYFINAL=4;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,"'1'","'2'",null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,"IF","THEN","ELSE","BEGIN","END","END_IF",
"OUTF","TYPEDEF","FUN","RET","REPEAT","WHILE","PAIR","GOTO","LONGINT","DOUBLE",
"MENOR_IGUAL","MAYOR_IGUAL","DISTINTO","T_ASIGNACION","T_CADENA","T_ID","T_CTE",
"T_ETIQUETA",
};
final static String yyrule[] = {
"$accept : programa",
"programa : T_ID bloque_sentencias",
"programa : T_ID",
"programa : bloque_sentencias",
"bloque_sentencias : BEGIN sentencias END",
"bloque_sentencias : BEGIN END",
"bloque_sentencias : error",
"sentencias : sentencia",
"sentencias : sentencias sentencia",
"sentencia : declaracion",
"sentencia : asignacion",
"sentencia : if_statement",
"sentencia : repeat_while_statement",
"sentencia : salida",
"sentencia : declaracion_funcion",
"sentencia : goto_statement",
"sentencia : sentencia_declarativa_tipos",
"sentencia : T_ETIQUETA",
"sentencia : RET '(' expresion ')' ';'",
"$$1 :",
"sentencia : RET '(' expresion ')' $$1",
"declaracion : tipo lista_var ';'",
"declaracion : tipo lista_var",
"lista_var : lista_var ',' T_ID",
"lista_var : T_ID",
"lista_var : error",
"declaracion_funcion : tipo FUN T_ID '(' parametro ')' bloque_sentencias",
"declaracion_funcion : tipo FUN T_ID '(' parametros_error ')' bloque_sentencias",
"declaracion_funcion : tipo FUN T_ID '(' tipo ')' bloque_sentencias",
"declaracion_funcion : tipo T_ID '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN '(' tipo T_ID ')' bloque_sentencias",
"parametro : tipo T_ID",
"parametros_error : parametro ',' parametro",
"parametros_error : parametros_error ',' parametro",
"parametros_error :",
"repeat_sentencia : bloque_sentencias",
"repeat_sentencia : sentencia",
"tipo : DOUBLE",
"tipo : LONGINT",
"tipo : T_ID",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia END_IF",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF",
"if_statement : IF '(' ')' THEN repeat_sentencia END_IF ';'",
"if_statement : IF '(' ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' repeat_sentencia ELSE repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' THEN END_IF ';'",
"if_statement : IF '(' condicion ')' THEN ELSE END_IF ';'",
"if_statement : IF condicion THEN repeat_sentencia END_IF ';'",
"if_statement : IF condicion THEN repeat_sentencia ELSE repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia error ';'",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia error ';'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')' ';'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')'",
"repeat_while_statement : REPEAT WHILE '(' condicion ')'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' ')' ';'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE condicion ';'",
"repeat_while_statement : REPEAT repeat_sentencia error '(' condicion ')' ';'",
"salida : OUTF '(' T_CADENA ')' ';'",
"salida : OUTF '(' expresion ')' ';'",
"salida : OUTF '(' expresion ')'",
"salida : OUTF '(' T_CADENA ')'",
"salida : OUTF '(' ')' ';'",
"salida : OUTF '(' sentencia ')' ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR DOUBLE T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR LONGINT T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' T_ID '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID",
"sentencia_declarativa_tipos : TYPEDEF '<' LONGINT '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF '<' DOUBLE '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ASIGNACION tipo subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo ';'",
"subrango : '{' T_CTE ',' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' T_CTE '}'",
"subrango : '{' T_CTE ',' '-' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' '-' T_CTE '}'",
"subrango : '{' '}'",
"subrango : error",
"condicion : expresion comparador expresion",
"condicion : expresion error expresion",
"condicion : expresion comparador",
"condicion : comparador expresion",
"comparador : MENOR_IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : DISTINTO",
"comparador : '='",
"comparador : '<'",
"comparador : '>'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list ';'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : acceso_par",
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"acceso_par : T_ID '{' '1' '}'",
"acceso_par : T_ID '{' '2' '}'",
"acceso_par : T_ID '{' error '}'",
"goto_statement : GOTO T_ETIQUETA ';'",
"goto_statement : GOTO ';'",
"goto_statement : GOTO T_ETIQUETA",
"goto_statement : GOTO error",
"invocacion_funcion : T_ID '(' parametro_real ')'",
"invocacion_funcion : T_ID '(' error ')'",
"parametro_real : expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '+' expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '-' expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '*' expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '/' expresion_aritmetica",
"expresion_aritmetica : T_CTE",
"expresion_aritmetica : T_ID",
"expresion_aritmetica : acceso_par",
"expresion_aritmetica : unaria",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '/' expresion",
"expresion : T_CTE",
"expresion : T_ID",
"expresion : acceso_par",
"expresion : invocacion_funcion",
"expresion : unaria",
"expresion : error",
"unaria : '-' expresion",
};

//#line 446 "gramatica.y"

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
//#line 744 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 56 "gramatica.y"
{
    System.out.println("Programa compilado correctamente");
}
break;
case 2:
//#line 59 "gramatica.y"
{ 
    System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
}
break;
case 3:
//#line 62 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}
break;
case 4:
//#line 66 "gramatica.y"
{System.out.println("Llegue a BEGIN sentencia END");}
break;
case 5:
//#line 67 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 6:
//#line 68 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan Delimitador o Bloque de Sentencia");}
break;
case 8:
//#line 71 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 19:
//#line 83 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 20:
//#line 84 "gramatica.y"
{System.out.println("Llegue a sentencia");}
break;
case 21:
//#line 87 "gramatica.y"
{ 
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
}
break;
case 22:
//#line 107 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 23:
//#line 111 "gramatica.y"
{ 
   
}
break;
case 24:
//#line 114 "gramatica.y"
{ 
    
}
break;
case 25:
//#line 116 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables");}
break;
case 26:
//#line 122 "gramatica.y"
{
        System.out.println("Declaración de función correcta");
    }
break;
case 27:
//#line 126 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }
break;
case 28:
//#line 131 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }
break;
case 29:
//#line 135 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 30:
//#line 139 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
    }
break;
case 31:
//#line 144 "gramatica.y"
{
        /* Caso correcto con un solo parámetro*/
    }
break;
case 32:
//#line 149 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 33:
//#line 152 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 34:
//#line 155 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
    }
break;
case 35:
//#line 159 "gramatica.y"
{}
break;
case 36:
//#line 160 "gramatica.y"
{}
break;
case 37:
//#line 164 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 38:
//#line 165 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 39:
//#line 167 "gramatica.y"
{
        System.out.println("Llegue a tipo");
        /* Verificar si el tipo está en la tabla de tipos definidos*/
        System.out.println(val_peek(0).sval);
        if (tablaTipos.containsKey(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo está definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0));
        } 
    }
break;
case 42:
//#line 182 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 43:
//#line 185 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 44:
//#line 189 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 45:
//#line 192 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 46:
//#line 196 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 47:
//#line 199 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 48:
//#line 203 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 49:
//#line 206 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 50:
//#line 209 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 51:
//#line 210 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 52:
//#line 211 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 53:
//#line 212 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 55:
//#line 219 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 56:
//#line 222 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 57:
//#line 225 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 58:
//#line 228 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 59:
//#line 229 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 61:
//#line 236 "gramatica.y"
{     
        System.out.println("Llegue a salida");   
        }
break;
case 62:
//#line 239 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 63:
//#line 242 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 64:
//#line 245 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 65:
//#line 246 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");}
break;
case 66:
//#line 249 "gramatica.y"
{
        System.out.println("Llegue a sentencia_declarativa_tipos");
        
        /* Obtener el nombre del tipo desde T_ID*/
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        /* Obtener el tipo base (INTEGER o SINGLE)*/
        String tipoBase = val_peek(2).sval;
        System.out.println("tipobase"+ " "+tipoBase );
        /* tipo base (INTEGER o SINGLE)*/
        /* Limite inferior del subrango (asegúrate de que sea numérico)*/
        double limiteInferior = val_peek(4).dval; /* Limite inferior */
        System.out.println("liminf"+ " "+limiteInferior );
        /* Limite superior del subrango (asegúrate de que sea numérico)*/
        double limiteSuperior =  val_peek(5).dval; /* Limite superior */
        System.out.println("limsup"+ " "+limiteSuperior );
        /* Almacenar en la tabla de tipos*/
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        System.out.println("ENTRE A DEFINIR NUEVO TIPO");
        }
break;
case 67:
//#line 269 "gramatica.y"
{
            /* Obtener el nombre del tipo desde T_ID*/
            String nombreTipo = val_peek(4).sval; /* T_ID*/

            /* Obtener el tipo base (INTEGER o SINGLE)*/
            String tipoBase = val_peek(2).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        }
break;
case 68:
//#line 278 "gramatica.y"
{

        }
break;
case 69:
//#line 281 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaración de tipo.");
        }
break;
case 70:
//#line 284 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 71:
//#line 287 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 72:
//#line 290 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
break;
case 73:
//#line 293 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 74:
//#line 296 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 75:
//#line 297 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 76:
//#line 298 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 77:
//#line 299 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 78:
//#line 300 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 79:
//#line 301 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 80:
//#line 302 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 81:
//#line 303 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 82:
//#line 304 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 83:
//#line 306 "gramatica.y"
{
        System.out.println("Llegue a subrango");

        /* Verificar que los valores en la pila son correctos antes de convertirlos*/
        String limiteInferiorStr = val_peek(3).sval; /* T_CTE (límites inferiores)*/
        String limiteSuperiorStr = val_peek(1).sval; /* T_CTE (límites superiores)*/

        System.out.println("VAL3 (Limite Inferior): " + limiteInferiorStr);
        System.out.println("VAL1 (Limite Superior): " + limiteSuperiorStr);

        try {
            /* Convertir los valores de límites de cadena a double*/
            double limiteInferior = Double.parseDouble(limiteInferiorStr);
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);

            /* Crear el objeto Subrango y asignarlo*/
            yylval.obj = new Subrango(limiteInferior, limiteSuperior);
            System.out.println("Subrango creado correctamente con límites: " + limiteInferior + " - " + limiteSuperior);
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los límites del subrango a double: " + e.getMessage());
        }
    }
break;
case 84:
//#line 328 "gramatica.y"
{ System.out.println("Llegue a subrango con - en el primero");}
break;
case 85:
//#line 329 "gramatica.y"
{System.out.println("Llegue a subrango con - en el segundo");}
break;
case 86:
//#line 330 "gramatica.y"
{System.out.println("Llegue a subrango con - en los dos");}
break;
case 87:
//#line 331 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 88:
//#line 333 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 90:
//#line 339 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 91:
//#line 340 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 92:
//#line 341 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1da expresion en la condicion");}
break;
case 99:
//#line 353 "gramatica.y"
{}
break;
case 100:
//#line 354 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion");}
break;
case 101:
//#line 356 "gramatica.y"
{
               
                }
break;
case 102:
//#line 359 "gramatica.y"
{
                }
break;
case 103:
//#line 362 "gramatica.y"
{ 
                }
break;
case 104:
//#line 366 "gramatica.y"
{
                }
break;
case 105:
//#line 371 "gramatica.y"
{
      }
break;
case 106:
//#line 373 "gramatica.y"
{
      }
break;
case 107:
//#line 381 "gramatica.y"
{
          
    }
break;
case 108:
//#line 384 "gramatica.y"
{}
break;
case 109:
//#line 385 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Solo se puede acceder a un par con 1 o 2");}
break;
case 111:
//#line 389 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 112:
//#line 390 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 113:
//#line 391 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 114:
//#line 395 "gramatica.y"
{
      }
break;
case 115:
//#line 397 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida"); /*CAMBIAR*/
        }
break;
case 125:
//#line 415 "gramatica.y"
{
        }
break;
case 126:
//#line 417 "gramatica.y"
{
        }
break;
case 127:
//#line 419 "gramatica.y"
{
        }
break;
case 128:
//#line 421 "gramatica.y"
{
        }
break;
case 129:
//#line 423 "gramatica.y"
{
                    
        }
break;
case 130:
//#line 426 "gramatica.y"
{
           
        }
break;
case 131:
//#line 429 "gramatica.y"
{
              
        }
break;
case 132:
//#line 432 "gramatica.y"
{
                    
        }
break;
case 133:
//#line 435 "gramatica.y"
{ /* Se añade la regla para operadores unarios*/
        }
break;
case 134:
//#line 437 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 135:
//#line 440 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
    yyval.dval = -val_peek(0).dval;
}
break;
//#line 1476 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
