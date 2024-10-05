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
    0,    0,    0,    1,    1,    2,    2,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    4,
    4,    4,   14,   14,   14,    9,    9,    9,    9,    9,
    9,   15,   16,   16,   16,   17,   17,   13,   13,   13,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,    6,    6,    7,    7,    7,    7,    7,
    7,    8,    8,    8,    8,    8,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   19,   19,   19,   19,   19,   19,
   18,   18,   18,   18,   20,   20,   20,   20,   20,   20,
    5,    5,    5,   22,   22,   21,   21,   21,   21,   21,
   21,   21,   23,   23,   23,   10,   10,   10,   10,   24,
   24,   25,   26,   26,   26,   26,   26,   26,   26,   26,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   27,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    5,    4,    3,    3,
    3,    2,    3,    1,    2,    7,    7,    7,    7,    7,
    8,    2,    3,    3,    0,    1,    1,    1,    1,    1,
    8,   10,    9,    7,    9,    7,    9,    7,    9,    7,
    8,    6,    8,    8,   10,    7,    6,    5,    6,    5,
    7,    5,    5,    4,    4,    5,    6,    7,    7,    6,
    5,    5,    5,    7,    6,    6,    6,    6,    6,    6,
    5,    5,    5,    5,    5,    6,    6,    7,    2,    1,
    3,    3,    2,    2,    1,    1,    1,    1,    1,    1,
    4,    4,    3,    1,    3,    3,    3,    1,    1,    3,
    3,    3,    4,    4,    2,    3,    2,    2,    2,    4,
    4,    1,    3,    3,    3,    3,    1,    1,    1,    1,
    3,    3,    3,    3,    1,    1,    1,    1,    1,    1,
    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    5,    0,    0,    0,    0,
    0,   39,   38,    0,   16,    0,    6,    8,    9,   10,
   11,   12,   13,   14,   15,    0,    0,    0,    1,  140,
   95,   96,   97,    0,  135,    0,    0,   99,  100,   98,
    0,    0,    0,  137,  138,  139,    0,    0,    0,    0,
    0,    0,    0,   36,   37,    0,  119,    0,  117,    0,
  115,    0,    4,    7,    0,    0,   22,    0,    0,    0,
    0,    0,  141,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   40,    0,    0,    0,    0,    0,   19,    0,    0,
    0,    0,  116,    0,  111,    0,    0,    0,    0,    0,
   21,   25,   20,    0,  103,    0,    0,    0,  107,    0,
  110,    0,    0,  127,  129,    0,    0,  130,    0,    0,
    0,    0,    0,  133,  134,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   90,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  114,
  113,    0,    0,    0,   23,  101,  102,    0,  121,  120,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   62,
   66,   63,   72,   71,    0,    0,    0,    0,    0,    0,
   89,   81,   83,    0,   82,   84,    0,    0,   17,   58,
    0,    0,    0,   60,    0,    0,    0,    0,    0,    0,
    0,    0,  125,  126,    0,    0,    0,    0,    0,    0,
    0,    0,   52,    0,   79,    0,   80,    0,   70,    0,
    0,   67,   77,   78,    0,   59,    0,   32,    0,    0,
    0,    0,    0,    0,    0,    0,   46,    0,   50,    0,
    0,    0,    0,    0,   48,    0,   68,   69,   74,    0,
    0,    0,   61,   56,   28,    0,    0,   33,   27,   34,
   30,   29,    0,   51,   54,    0,   41,    0,    0,   53,
   85,    0,    0,    0,   31,   47,    0,    0,   43,   49,
   87,   86,    0,   55,   42,   88,
};
final static short yydgoto[] = {                          3,
   54,   16,   55,   18,   19,   20,   21,   22,   23,   24,
   25,   41,   26,   68,  206,  207,   56,   42,  149,   43,
   27,  117,   44,   45,  126,  127,   46,
};
final static short yysindex[] = {                      -247,
  502, -204,    0,    0,  433,    0,   62,  322,   80,  401,
  -51,    0,    0,  -96,    0,  524,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   79,   31, -174,    0,    0,
    0,    0,    0,  -38,    0, -214,  255,    0,    0,    0,
  552, -154,   15,    0,    0,    0,  305,  347, -227, -210,
 -208,   12,   94,    0,    0, -239,    0,   76,    0, -142,
    0, -232,    0,    0,  -21,  103,    0,   87,  -41, -126,
 -125,  112,    0, -102,  139,   15,   15,   15,   15,   15,
   15,  571,  399,  143,    9,  145,   48, -174,  -99,  -78,
  330,    0, -109, -117, -109,  144,  154,    0,   64,  355,
  161,  457,    0, -112,    0,   82,   97,  171, -227, -227,
    0,    0,    0,  -61,    0,  399,  119, -112,    0, -112,
    0,  226, -112,    0,    0,  328,  578,    0,  571,  420,
  399,  -17,  -17,    0,    0,  399, -220,  337,  352,  362,
  373,  377,  388,  395,  406,  165,    0,  -13,  402,  -47,
  415,  416,  207,  211,  437,  458,  355,  280,  442,    0,
    0, -227,  230,  232,    0,    0,    0,   15,    0,    0,
  140,  140,  140,  140, -182,  379, -149,  571,  461,    0,
    0,    0,    0,    0,   14,   40,  238,  464,  483,  252,
    0,    0,    0,  473,    0,    0,  476,  478,    0,    0,
  497,  480,  503,    0,  -31,   86,  107,  509,  511,  399,
   25,   25,    0,    0,  571,  498,  294,  501,  -68,  571,
  514,  308,    0,  515,    0,  520,    0,  522,    0,   -4,
  543,    0,    0,    0,  533,    0,  534,    0, -204, -204,
 -227, -204, -227, -204, -204,  334,    0,  544,    0,  550,
  571,  551,  360,  364,    0,  558,    0,    0,    0,  499,
  348,    3,    0,    0,    0,  572,  354,    0,    0,    0,
    0,    0,  576,    0,    0, -236,    0,  581,  585,    0,
    0,  523,  527,  368,    0,    0,  594,  595,    0,    0,
    0,    0,  530,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  656,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  302,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   35,    0,    0,
    0,    0,    0,  504,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -36,    0,    0,
    0,    0,    0,    0,    0,  115,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   78,    0,  108,    0,  462,    0,    0,   -7,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  138,    0,   43,    0,   56,
    0,    0,  424,    0,    0,    0,  621,    0,    0,    0,
  109,  481,  545,    0,    0,  128,    0,   -8,    0,   17,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   42,    0,    0,    0,    0,    0,
    0,  149,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   67,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  381,
   53,  121,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   92,    0,  117,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  142,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  167,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  192,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  220,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   27,    0,    0,    0,    0,    0,    0,    0,
    0,   45,  566,    0, -191,    0,  -75,  -16,  -72,  622,
    0,    0,   -1,    0,    0,  341,  -56,
};
final static int YYTABLESIZE=851;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
    4,   72,   29,   36,  118,  148,  137,   59,   28,  239,
   62,  193,    1,  148,   28,  128,  101,  115,  109,  287,
   75,  151,  152,  106,   79,  288,   62,   17,  102,   80,
    2,  190,   65,  137,  137,  137,  109,  137,  178,  137,
  261,  179,   64,   12,   13,   88,  107,  284,   72,  268,
   92,  270,   98,  175,  177,    1,   36,   64,  105,   36,
   12,   13,   96,   97,   73,   94,  173,   92,  119,  121,
  125,  174,  225,   86,   70,  148,  215,  194,  109,  216,
   28,   71,   18,  156,   62,  159,  106,   83,  140,   79,
   77,   87,   78,  123,   80,  123,   99,  123,  227,  112,
  219,   47,  222,   82,  155,   79,   77,   73,   78,  220,
   80,  191,  221,  116,  128,  128,  128,  128,   93,   52,
  131,  132,  133,  134,  135,  136,  240,   28,   28,  241,
  114,   62,   75,  100,  103,  104,   93,   67,  147,  246,
  201,  203,  110,  253,  254,  113,  147,  242,   94,   92,
  243,  118,  120,   12,   13,  129,   36,   76,   24,   60,
   92,  124,  168,  124,   61,  124,   94,   92,   91,  125,
  125,  125,  125,   24,   28,  276,   28,  167,  141,  130,
   61,  104,   57,  138,   36,  139,   91,  250,    5,   35,
  251,    1,   35,  252,    7,    8,  104,    9,   10,  142,
  157,   11,   12,   13,   57,  153,  160,   44,  147,   14,
  162,   15,  210,   28,   30,  154,  165,   28,   28,  118,
  118,  161,  118,  118,  118,  118,  118,  118,   58,  118,
  118,  118,   26,  118,  118,  118,   34,   35,   61,  265,
  266,  118,  269,  118,  271,  272,  238,   65,   65,   28,
   65,   65,   65,   65,   65,   65,  108,   65,   65,   65,
   45,   65,   65,   65,   60,  189,  169,   30,  109,   65,
   30,   65,   64,   64,  260,   64,   64,   64,   64,   64,
   64,  283,   64,   64,   64,   61,   64,   64,   64,   34,
   35,  224,   34,   35,   64,   74,   64,   18,   18,   36,
   18,   18,   18,   18,   18,   18,   69,   18,   18,   18,
  109,   18,   18,   18,   38,   40,   39,  226,  106,   18,
  202,   18,   73,   73,   36,   73,   73,   73,   73,   73,
   73,  112,   73,   73,   73,   93,   73,   73,   73,   38,
   40,   39,  111,   65,   73,  108,   73,   75,   75,   36,
   75,   75,   75,   75,   75,   75,   66,   75,   75,   75,
   40,   75,   75,   75,  112,   94,   92,  122,  170,   75,
   24,   75,   76,   76,  166,   76,   76,   76,   76,   76,
   76,   51,   76,   76,   76,   91,   76,   76,   76,  123,
  124,  146,   24,  104,   76,  180,   76,   57,   57,   36,
   57,   57,   57,   57,   57,   57,   91,   57,   57,   57,
  181,   57,   57,   57,   38,   40,   39,  123,  124,   57,
  182,   57,   44,   44,  105,   44,   44,   44,   44,   44,
   44,  183,   44,   44,   44,  184,   44,   44,   44,  105,
   79,   77,  188,   78,   44,   80,   44,   26,   26,  185,
   26,   26,   26,   26,   26,   26,  186,   26,   26,   26,
  192,   26,   26,   26,  128,  128,  128,  187,  128,   26,
  128,   26,   37,  195,  196,   45,   45,   36,   45,   45,
   45,   45,   45,   45,  197,   45,   45,   45,  198,   45,
   45,   45,   38,   40,   39,  199,  158,   45,  200,   45,
  204,   36,  136,  136,  136,  108,  136,  208,  136,  209,
   30,  211,  212,  213,  214,  228,   38,   40,   39,  223,
   40,  131,  229,  131,  131,  131,  230,   31,   32,   33,
  231,  232,   34,   35,  233,   30,  234,  235,  236,  131,
  131,  131,  131,  237,  136,  136,  136,  136,  136,  244,
  136,  245,   31,   32,   33,  248,  247,   34,   35,  249,
   30,    5,  136,  136,  136,  136,   40,    7,    8,  256,
    9,   10,  255,  257,   11,   12,   13,  108,  258,   40,
  259,   84,   85,   35,   15,  132,  262,  132,  132,  132,
   48,  263,  264,   79,   77,  273,   78,   49,   80,   50,
  143,  144,  274,  132,  132,  132,  132,  145,  275,  277,
   30,   38,   40,   39,   93,   95,  280,   89,   90,  173,
  171,  278,  172,  281,  174,  279,  282,   31,   32,   33,
  285,  238,   34,   35,  286,    5,  105,  217,    1,  289,
  218,    7,    8,  290,    9,   10,  293,  291,   11,   12,
   13,  292,  294,  295,  296,    2,   14,    5,   15,  150,
    1,  122,   81,    7,    8,    0,    9,   10,   53,    0,
   11,   12,   13,    0,  163,  164,    5,  176,   14,    1,
   15,    0,    7,    8,    0,    9,   10,    0,   30,   11,
   12,   13,    0,    0,    0,    0,    0,   14,    0,   15,
    0,    0,    0,    0,    0,   31,   32,   33,    0,    0,
   34,   35,   30,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   40,  205,    0,   31,
   32,   33,    0,    0,   34,   35,  131,  108,  131,   40,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  131,  131,  131,    0,    0,    5,  136,
    0,  136,    6,    0,    7,    8,    0,    9,   10,    0,
    0,   11,   12,   13,    0,    0,  136,  136,  136,   14,
    5,   15,    0,    0,   63,    0,    7,    8,    0,    9,
   10,    0,    0,   11,   12,   13,    0,    0,    0,    0,
  132,   14,  132,   15,    0,    0,  267,   76,  267,    0,
    0,    0,    0,    0,    0,    0,    0,  132,  132,  132,
    0,    0,    0,    0,   31,   32,   33,    5,    0,    0,
    1,    0,    0,    7,    8,    0,    9,   10,    0,    0,
   11,   12,   13,    0,    0,    0,    0,    0,   14,    0,
   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    0,   40,    2,   45,   41,  123,   82,   59,   10,   41,
  123,   59,  260,  123,   16,   72,  256,   59,   40,  256,
   37,   94,   95,  256,   42,  262,  123,    1,  268,   47,
  278,   45,   41,   41,   42,   43,   44,   45,  259,   47,
   45,  262,   16,  271,  272,   47,  279,   45,   40,  241,
  278,  243,   41,  129,  130,  260,   45,   41,   60,   45,
  271,  272,  271,  272,  279,  276,   42,  278,   70,   71,
   72,   47,   59,   47,   44,  123,  259,  150,   44,  262,
   82,  256,   41,  100,  123,  102,   44,   43,   41,   42,
   43,   47,   45,   41,   47,   43,   52,   45,   59,   44,
  176,   40,  178,  258,   41,   42,   43,   41,   45,  259,
   47,  125,  262,   69,  171,  172,  173,  174,   41,   40,
   76,   77,   78,   79,   80,   81,   41,  129,  130,   44,
   44,  123,   41,   40,   59,  278,   59,   59,  256,  215,
  157,  158,   40,  219,  220,   59,  256,   41,   41,   41,
   44,  278,  278,  271,  272,  258,   45,   41,   44,  256,
  278,   41,   44,   43,  277,   45,   59,   59,   41,  171,
  172,  173,  174,   59,  176,  251,  178,   59,  278,   41,
  277,   44,   41,   41,   45,   41,   59,  256,  257,   41,
  259,  260,   44,  262,  263,  264,   59,  266,  267,  278,
   40,  270,  271,  272,  256,   62,  125,   41,  256,  278,
   40,  280,  168,  215,  256,   62,  278,  219,  220,  256,
  257,  125,  259,  260,  261,  262,  263,  264,  280,  266,
  267,  268,   41,  270,  271,  272,  278,  279,  277,  239,
  240,  278,  242,  280,  244,  245,  278,  256,  257,  251,
  259,  260,  261,  262,  263,  264,  278,  266,  267,  268,
   41,  270,  271,  272,  256,  279,   41,  256,  276,  278,
  256,  280,  256,  257,  279,  259,  260,  261,  262,  263,
  264,  279,  266,  267,  268,  277,  270,  271,  272,  278,
  279,  278,  278,  279,  278,   41,  280,  256,  257,   45,
  259,  260,  261,  262,  263,  264,  276,  266,  267,  268,
  276,  270,  271,  272,   60,   61,   62,  278,  276,  278,
   41,  280,  256,  257,   45,  259,  260,  261,  262,  263,
  264,  276,  266,  267,  268,  258,  270,  271,  272,   60,
   61,   62,  256,  265,  278,   44,  280,  256,  257,   45,
  259,  260,  261,  262,  263,  264,  278,  266,  267,  268,
   59,  270,  271,  272,  278,  258,  258,  256,   41,  278,
  256,  280,  256,  257,  256,  259,  260,  261,  262,  263,
  264,   60,  266,  267,  268,  258,  270,  271,  272,  278,
  279,   62,  278,  256,  278,   59,  280,  256,  257,   45,
  259,  260,  261,  262,  263,  264,   60,  266,  267,  268,
   59,  270,  271,  272,   60,   61,   62,  278,  279,  278,
   59,  280,  256,  257,   44,  259,  260,  261,  262,  263,
  264,   59,  266,  267,  268,   59,  270,  271,  272,   59,
   42,   43,  278,   45,  278,   47,  280,  256,  257,   62,
  259,  260,  261,  262,  263,  264,   62,  266,  267,  268,
   59,  270,  271,  272,   41,   42,   43,   62,   45,  278,
   47,  280,   40,   59,   59,  256,  257,   45,  259,  260,
  261,  262,  263,  264,  278,  266,  267,  268,  278,  270,
  271,  272,   60,   61,   62,   59,   40,  278,   41,  280,
   59,   45,   41,   42,   43,   44,   45,  278,   47,  278,
  256,  171,  172,  173,  174,  278,   60,   61,   62,   59,
   59,   41,   59,   43,   44,   45,   44,  273,  274,  275,
  279,   59,  278,  279,   59,  256,   59,   41,   59,   59,
   60,   61,   62,   41,   41,   42,   43,   44,   45,   41,
   47,   41,  273,  274,  275,  262,   59,  278,  279,   59,
  256,  257,   59,   60,   61,   62,  265,  263,  264,  262,
  266,  267,   59,   59,  270,  271,  272,  276,   59,  278,
   59,  277,  278,  279,  280,   41,   44,   43,   44,   45,
  269,   59,   59,   42,   43,  262,   45,  276,   47,  278,
  271,  272,   59,   59,   60,   61,   62,  278,   59,   59,
  256,   60,   61,   62,   49,   50,   59,  271,  272,   42,
   43,  262,   45,  125,   47,  262,  279,  273,  274,  275,
   59,  278,  278,  279,   59,  257,  256,  259,  260,   59,
  262,  263,  264,   59,  266,  267,  279,  125,  270,  271,
  272,  125,   59,   59,  125,    0,  278,  257,  280,   94,
  260,   41,   41,  263,  264,   -1,  266,  267,  268,   -1,
  270,  271,  272,   -1,  109,  110,  257,  258,  278,  260,
  280,   -1,  263,  264,   -1,  266,  267,   -1,  256,  270,
  271,  272,   -1,   -1,   -1,   -1,   -1,  278,   -1,  280,
   -1,   -1,   -1,   -1,   -1,  273,  274,  275,   -1,   -1,
  278,  279,  256,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  265,  162,   -1,  273,
  274,  275,   -1,   -1,  278,  279,  256,  276,  258,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,   -1,  257,  256,
   -1,  258,  261,   -1,  263,  264,   -1,  266,  267,   -1,
   -1,  270,  271,  272,   -1,   -1,  273,  274,  275,  278,
  257,  280,   -1,   -1,  261,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
  256,  278,  258,  280,   -1,   -1,  241,  256,  243,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,
   -1,   -1,   -1,   -1,  273,  274,  275,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,   -1,  266,  267,   -1,   -1,
  270,  271,  272,   -1,   -1,   -1,   -1,   -1,  278,   -1,
  280,
};
}
final static short YYFINAL=3;
final static short YYMAXTOKEN=280;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
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
"sentencia : RET '(' expresion ')'",
"sentencia : RET '(' ')'",
"declaracion : tipo lista_var ';'",
"declaracion : tipo lista_var error",
"declaracion : tipo ';'",
"lista_var : lista_var ',' T_ID",
"lista_var : T_ID",
"lista_var : lista_var T_ID",
"declaracion_funcion : tipo FUN T_ID '(' parametro ')' bloque_sentencias",
"declaracion_funcion : tipo FUN T_ID '(' parametros_error ')' bloque_sentencias",
"declaracion_funcion : tipo FUN T_ID '(' tipo ')' bloque_sentencias",
"declaracion_funcion : tipo T_ID '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN T_ID '(' parametro ')' bloque_sentencias ';'",
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
"if_statement : IF '(' condicion ')' THEN repeat_sentencia repeat_sentencia END_IF ';'",
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
"sentencia_declarativa_tipos : TYPEDEF T_ID tipo subrango ';'",
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
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list error",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list ';'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION ';'",
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : acceso_par",
"IDENTIFIER_LIST : acceso_par error acceso_par",
"IDENTIFIER_LIST : T_ID error acceso_par",
"IDENTIFIER_LIST : acceso_par error T_ID",
"acceso_par : T_ID '{' T_CTE '}'",
"acceso_par : T_ID '{' error '}'",
"acceso_par : T_ID T_CADENA",
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
"unaria : '-' T_CTE",
};

//#line 503 "gramatica.y"

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

//#line 751 "Parser.java"
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
//#line 57 "gramatica.y"
{
    System.out.println("Programa compilado correctamente");
}
break;
case 2:
//#line 60 "gramatica.y"
{ 
    System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
}
break;
case 3:
//#line 63 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}
break;
case 5:
//#line 68 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 18:
//#line 85 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 19:
//#line 86 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
break;
case 20:
//#line 90 "gramatica.y"
{ 
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
}
break;
case 21:
//#line 108 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 22:
//#line 109 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}
break;
case 25:
//#line 115 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 27:
//#line 121 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }
break;
case 28:
//#line 126 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }
break;
case 29:
//#line 130 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 30:
//#line 134 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    }
break;
case 31:
//#line 137 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se puede poner ; al final de la declaracion de una fucnion");
    }
break;
case 33:
//#line 145 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 34:
//#line 148 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 35:
//#line 151 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion debe tener un parametro.");
    }
break;
case 38:
//#line 161 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 39:
//#line 162 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 40:
//#line 164 "gramatica.y"
{
        
        /* Verificando si el tipo esta en la tabla de tipos definidos*/
        
        if (tablaTipos.containsKey(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0));
        } 
    }
break;
case 43:
//#line 179 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 44:
//#line 180 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 45:
//#line 183 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 46:
//#line 187 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 47:
//#line 190 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 48:
//#line 194 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 49:
//#line 197 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 50:
//#line 201 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 51:
//#line 204 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 52:
//#line 207 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 53:
//#line 208 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 54:
//#line 209 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 55:
//#line 210 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 57:
//#line 218 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 58:
//#line 221 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
break;
case 59:
//#line 224 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 60:
//#line 227 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 61:
//#line 228 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 64:
//#line 236 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 65:
//#line 239 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 66:
//#line 242 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
break;
case 67:
//#line 245 "gramatica.y"
{
        
        
        /* Obtener el nombre del tipo desde T_ID*/
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        /* Obtener el tipo base (INTEGER o SINGLE)*/
        String tipoBase = val_peek(2).sval;
        
        /* tipo base (INTEGER o SINGLE)*/
        
        double limiteInferior = val_peek(4).dval; /* Limite inferior */
        
        
        double limiteSuperior =  val_peek(5).dval; /* Limite superior */
        
        /* Almacenar en la tabla de tipos*/
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        
        }
break;
case 68:
//#line 265 "gramatica.y"
{
             String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (LONGINT)*/
            String tipoBase = val_peek(3).sval;
            
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -2147483647, 2147483647));
        }
break;
case 69:
//#line 273 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (DOUBLE)*/
            String tipoBase = val_peek(3).sval;
            
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
break;
case 70:
//#line 281 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
break;
case 71:
//#line 284 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 72:
//#line 287 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 73:
//#line 290 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
break;
case 74:
//#line 293 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 75:
//#line 296 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 76:
//#line 297 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 77:
//#line 298 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 78:
//#line 299 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 79:
//#line 300 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 80:
//#line 301 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 81:
//#line 302 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 82:
//#line 303 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 83:
//#line 304 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 84:
//#line 305 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la asignacion en la definicion de nuevos tipos");}
break;
case 85:
//#line 307 "gramatica.y"
{
        
        /*CODIGO PARA PARTE SEMANTICA*/
        
        String limiteInferiorStr = val_peek(3).sval; /* T_CTE (limites inferiores)*/
        String limiteSuperiorStr = val_peek(1).sval; /* T_CTE (limites superiores)*/

        
        

        try {
           
            double limiteInferior = Double.parseDouble(limiteInferiorStr);
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);

            
            yylval.obj = new Subrango(limiteInferior, limiteSuperior);
            
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
        }
    }
break;
case 89:
//#line 332 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 90:
//#line 333 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 92:
//#line 339 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 93:
//#line 340 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 94:
//#line 341 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 101:
//#line 353 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 103:
//#line 355 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
break;
case 110:
//#line 367 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 111:
//#line 368 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 112:
//#line 369 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 113:
//#line 374 "gramatica.y"
{
            
        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
        }
        
    }
break;
case 114:
//#line 383 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Solo se puede acceder a un par con 1 o 2");}
break;
case 115:
//#line 384 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 117:
//#line 388 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 118:
//#line 389 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 119:
//#line 390 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 121:
//#line 394 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
break;
case 140:
//#line 420 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 141:
//#line 423 "gramatica.y"
{ /* Esta regla maneja especificamente el '-' unario*/
    double valor = val_peek(0).dval;  
    System.out.println("dval: "+valor);

    String nombreConstante = val_peek(0).sval;  
    String nombreConMenos = "-" + nombreConstante;
    System.out.println("sval: "+nombreConstante);
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
            }else if (tipo.equals("Octal")) {
                if (!lexer.isOctalRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para octales.");
                    
                } else {
                    System.out.println("Entre a el else del octal");
                    st.addValue(nombreConMenos, tipo, SymbolTable.constantValue);
                }
            }
        } else {
            System.err.println("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else { /*se trata de numero negativo menor al menor negativo.*/
    	/*ACA VER QUE TIPO DE NUMERO ES CON IFS*/
    	System.out.println("Entre");
        if (nombreConstante.startsWith("0") && !nombreConstante.matches(".*[89].*")) {
        	System.err.println("El valor octal " + "-"+nombreConstante+ " se ajusto al valor minimo.");
            st.addValue("-020000000000", "Octal", SymbolTable.constantValue);
        } else if (nombreConstante.contains(".")) {
        	System.err.println("El valor double -" + nombreConstante + " se ajusta al valor mínimo.");

            
            /* Parseamos el valor como double para comparaciones*/
            double valorDouble = Double.parseDouble("-" + nombreConstante.replace("d", "e"));
            System.out.println("valorDouble: "+valorDouble);
            /* Rango mínimo y máximo de los números double*/
            double maxNegativeDouble = -1.7976931348623157e+308;
            double minNegativeDouble = -2.2250738585072014e-308;

            /* Si está por debajo del máximo permitido, lo mantenemos*/
            if (valorDouble < maxNegativeDouble) {
                st.addValue("-1.7976931348623156d+308", "double", SymbolTable.constantValue);
            } 
            /* Si está por debajo del mínimo permitido pero mayor al mínimo ajustado*/
            else if (valorDouble > minNegativeDouble) {
                st.addValue("-2.2250738585072015d-308", "double", SymbolTable.constantValue);
            } 
            /* Si está en el rango permitido*/
            else {
                st.addValue("-" + nombreConstante, "double", SymbolTable.constantValue);
            }
            
        } else{ /*ya se sabe que es entero*/
            /* Lógica para longint*/
        	System.err.println("El valor longint -" + nombreConstante + " se ajusta al valor mínimo.");
            nombreConMenos = "-2147483648"; /* Asignar valor mínimo si está fuera de rango*/
            st.addValue(nombreConMenos, "longint", SymbolTable.constantValue);
        }
        
    }
}
break;
//#line 1446 "Parser.java"
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
