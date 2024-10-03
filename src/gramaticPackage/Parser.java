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
    7,    8,    8,    8,    8,    8,    8,   11,   11,   11,
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
    7,    5,    5,    4,    4,    4,    5,    6,    7,    7,
    6,    5,    5,    5,    7,    6,    6,    6,    6,    6,
    6,    5,    5,    5,    5,    6,    6,    7,    2,    1,
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
    0,    0,   40,    0,    0,    0,    0,   19,    0,    0,
    0,    0,  116,    0,  111,    0,    0,    0,    0,    0,
   21,   25,   20,    0,  103,    0,    0,    0,  107,    0,
  110,    0,    0,  127,  129,    0,    0,  130,    0,    0,
    0,    0,    0,  133,  134,    0,    0,    0,   66,    0,
    0,    0,    0,    0,    0,    0,    0,   90,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  114,
  113,    0,    0,    0,   23,  101,  102,    0,  121,  120,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   62,
   67,   63,   73,   72,    0,    0,    0,    0,    0,    0,
   89,   82,   84,    0,   83,    0,    0,   17,   58,    0,
    0,    0,   60,    0,    0,    0,    0,    0,    0,    0,
    0,  125,  126,    0,    0,    0,    0,    0,    0,    0,
    0,   52,    0,   80,    0,   81,    0,   71,    0,    0,
   68,   78,   79,    0,   59,    0,   32,    0,    0,    0,
    0,    0,    0,    0,    0,   46,    0,   50,    0,    0,
    0,    0,    0,   48,    0,   69,   70,   75,    0,    0,
    0,   61,   56,   28,    0,    0,   33,   27,   34,   30,
   29,    0,   51,   54,    0,   41,    0,    0,   53,   85,
    0,    0,    0,   31,   47,    0,    0,   43,   49,   87,
   86,    0,   55,   42,   88,
};
final static short yydgoto[] = {                          3,
   54,   16,   55,   18,   19,   20,   21,   22,   23,   24,
   25,   41,   26,   68,  205,  206,   56,   42,  150,   43,
   27,  117,   44,   45,  126,  127,   46,
};
final static short yysindex[] = {                      -237,
  389, -238,    0,    0,  432,    0,   24,  371,   38,  571,
  -52,    0,    0, -113,    0,  608,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  103,  -39, -214,    0,    0,
    0,    0,    0,  -21,    0, -200,  279,    0,    0,    0,
  551, -172,  114,    0,    0,    0,  244,  364, -220, -227,
 -209,   39,   51,    0,    0, -230,    0,   36,    0, -182,
    0, -235,    0,    0,  -32,   61,    0,  -42,  139, -167,
 -165,  164,    0, -136,  111,  114,  114,  114,  114,  114,
  114,  627,   88,  115,   14,   99,  127,   47, -214,  -91,
  -90,  348,    0, -109, -111,  107,  132,    0,   63,  354,
  155,  458,    0, -112,    0,   72,   74,  160, -220, -220,
    0,    0,    0,  -77,    0,   88,    9, -112,    0, -112,
    0,  161, -112,    0,    0,  162,  106,    0,  627,  590,
   88,    3,    3,    0,    0,   88, -186,  146,    0,  147,
  149,  153,  156,  154,  159,  204,  -67,    0,   -5,  251,
  -46,  297,   82,   89,  326,  349,  354,  304,  376,    0,
    0, -220,  163,  167,    0,    0,    0,  114,    0,    0,
   64,   64,   64,   64, -159,  549, -141,  627,  380,    0,
    0,    0,    0,    0,  -11,   28,  171,  381,  412,  181,
    0,    0,    0,  405,    0,  406,  407,    0,    0,  426,
  409,  429,    0,  -14,   95,  122,  433,  443,   88,   13,
   13,    0,    0,  627,  414,  226,  436,  423,  627,  437,
  240,    0,  445,    0,  446,    0,  447,    0,    2,  465,
    0,    0,    0,  453,    0,  454,    0, -238, -238, -220,
 -238, -220, -238, -238,  255,    0,  471,    0,  473,  627,
  474,  272,  274,    0,  478,    0,    0,    0,  413,  260,
   11,    0,    0,    0,  481,  263,    0,    0,    0,    0,
    0,  483,    0,    0, -232,    0,  490,  492,    0,    0,
  431,  434,  282,    0,    0,  508,  513,    0,    0,    0,
    0,  451,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  580,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  330,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -38,    0,    0,
    0,    0,    0,  503,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -37,    0,    0,
    0,    0,    0,    0,    0,  347,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   34,    0,   56,    0,  484,    0,    0,    0,   -8,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   75,    0,   17,    0,   23,
    0,    0,  138,    0,    0,    0,  540,    0,    0,    0,
   57,  525,  530,    0,    0,   58,    0,   -9,    0,    0,
   16,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
    0,  152,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   66,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   79,  101,
  133,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   91,    0,  116,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  141,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  166,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  191,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  219,    0,    0,    0,
    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   27,    0,    0,    0,    0,    0,    0,    0,
    0,  596,  506,    0, -211,    0,  -64,  -17,  -79,  547,
    0,    0,   -1,    0,    0,   18,  -47,
};
final static int YYTABLESIZE=907;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
    4,  114,   29,  118,   70,  109,   59,  109,   28,   62,
   62,  149,  193,  149,   28,  152,  113,  137,   72,   75,
  106,    1,    1,  286,  128,  101,  238,   17,  267,  287,
  269,   65,  137,  137,  137,  109,  137,  102,  137,  190,
    2,   71,   64,  107,   79,   89,  260,  224,   95,   80,
   12,   13,  168,   72,  173,  283,   64,   93,  105,  174,
  106,   96,   97,   47,  175,  177,  112,  167,  119,  121,
  125,  194,  178,   87,   93,  179,  149,   52,   73,   98,
   28,   18,  156,   36,  159,   82,  226,  141,   79,   77,
  100,   78,   93,   80,  103,  104,   94,   92,   91,  214,
  110,   62,  215,  155,   79,   77,   74,   78,   36,   80,
  118,  218,  120,  221,   94,   92,   91,  219,  104,  191,
  220,  129,  105,  128,  128,  128,  128,   28,   28,   79,
   77,   76,   78,  104,   80,  239,   62,  105,  240,  200,
  202,  123,   60,  123,  148,  123,  148,  173,  171,  245,
  172,  130,  174,  252,  253,  138,   77,  139,   36,   12,
   13,   67,  241,   61,   61,  242,   93,  140,  153,  125,
  125,  125,  125,  124,   28,  124,   28,  124,  128,  128,
  128,   57,  128,   36,  128,  275,  142,  143,  210,  211,
  212,  213,   35,  154,  157,   35,  160,  115,  161,  162,
  165,  169,  170,   57,  180,  181,   44,  182,   36,  148,
  188,  183,   28,  111,  184,  185,   28,   28,  118,  118,
  186,  118,  118,  118,  118,  118,  118,   58,  118,  118,
  118,   26,  118,  118,  118,  112,   69,  109,  264,  265,
  118,  268,  118,  270,  271,  108,   65,   65,   28,   65,
   65,   65,   65,   65,   65,   61,   65,   65,   65,   45,
   65,   65,   65,  237,  166,  187,  223,  109,   65,   60,
   65,   64,   64,  189,   64,   64,   64,   64,   64,   64,
  259,   64,   64,   64,   86,   64,   64,   64,   36,  282,
   61,   93,  106,   64,   30,   64,   18,   18,  112,   18,
   18,   18,   18,   18,   18,  225,   18,   18,   18,  192,
   18,   18,   18,   94,   92,   91,   34,   35,   18,   74,
   18,   74,   74,   36,   74,   74,   74,   74,   74,   74,
  104,   74,   74,   74,  105,   74,   74,   74,   38,   40,
   39,  123,  124,   74,  201,   74,   76,   76,   36,   76,
   76,   76,   76,   76,   76,  195,   76,   76,   76,  196,
   76,   76,   76,   38,   40,   39,  197,   65,   76,   30,
   76,   77,   77,  108,   77,   77,   77,   77,   77,   77,
   66,   77,   77,   77,  198,   77,   77,   77,   40,  199,
   24,   34,   35,   77,   30,   77,   57,   57,   36,   57,
   57,   57,   57,   57,   57,   24,   57,   57,   57,  147,
   57,   57,   57,   38,   40,   39,   34,   35,   57,  122,
   57,   44,   44,   92,   44,   44,   44,   44,   44,   44,
   51,   44,   44,   44,  203,   44,   44,   44,  222,  228,
  207,  123,  124,   44,  208,   44,   26,   26,  227,   26,
   26,   26,   26,   26,   26,  229,   26,   26,   26,  230,
   26,   26,   26,  231,  232,  233,  234,  235,   26,  236,
   26,   37,  246,  243,   45,   45,   36,   45,   45,   45,
   45,   45,   45,  244,   45,   45,   45,  247,   45,   45,
   45,   38,   40,   39,  248,  254,   45,  158,   45,   30,
    5,  255,   36,  256,  257,  258,    7,    8,  261,    9,
   10,  262,  263,   11,   12,   13,  272,   38,   40,   39,
   84,   85,   35,   15,  136,  136,  136,  108,  136,  273,
  136,  274,  276,  277,   30,  278,  279,  280,  281,  284,
  237,  285,   40,  136,  136,  136,  136,  136,  288,  136,
  289,   31,   32,   33,   94,  290,   34,   35,  291,   30,
  292,  136,  136,  136,  136,  131,  293,  131,  131,  131,
  132,  294,  132,  132,  132,  295,   31,   32,   33,    2,
  122,   34,   35,  131,  131,  131,  131,   81,  132,  132,
  132,  132,   79,   77,   40,   78,    0,   80,    0,    0,
  151,    0,   24,    0,    0,  108,    0,   40,    0,   30,
   38,   40,   39,    0,  163,  164,    0,    0,  144,  145,
    0,    0,    0,    0,   24,  146,   31,   32,   33,    0,
    0,   34,   35,    0,   90,   91,    0,    0,   83,   48,
    0,    0,   88,    0,    0,    5,   49,   99,   50,    6,
    0,    7,    8,    0,    9,   10,    0,    0,   11,   12,
   13,    0,    0,    0,  116,    0,   14,  204,   15,    0,
    0,  131,  132,  133,  134,  135,  136,    0,  249,    5,
    0,  250,    1,    0,  251,    7,    8,   30,    9,   10,
    0,    0,   11,   12,   13,    0,    0,    0,    0,    0,
   14,    0,   15,    0,   31,   32,   33,    0,    0,   34,
   35,    0,    0,   30,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   31,   32,   33,    0,    0,   34,   35,    0,    0,    0,
    0,    0,    0,    0,    0,  266,    0,  266,   40,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  136,  108,
  136,   40,    0,  209,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  136,  136,  136,    0,    0,
  131,    0,  131,    0,    0,  132,    0,  132,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  131,  131,  131,
    0,    0,  132,  132,  132,    5,   76,  216,    1,    0,
  217,    7,    8,    0,    9,   10,    0,    0,   11,   12,
   13,    0,    0,   31,   32,   33,   14,    5,   15,    0,
    1,    0,    0,    7,    8,    0,    9,   10,   53,    0,
   11,   12,   13,    0,    0,    0,    5,  176,   14,    1,
   15,    0,    7,    8,    0,    9,   10,    0,    0,   11,
   12,   13,    0,    0,    5,    0,    0,   14,   63,   15,
    7,    8,    0,    9,   10,    0,    0,   11,   12,   13,
    0,    0,    0,    5,    0,   14,    1,   15,    0,    7,
    8,    0,    9,   10,    0,    0,   11,   12,   13,    0,
    0,    0,    0,    0,   14,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    0,   44,    2,   41,   44,   44,   59,   40,   10,  123,
  123,  123,   59,  123,   16,   95,   59,   82,   40,   37,
  256,  260,  260,  256,   72,  256,   41,    1,  240,  262,
  242,   41,   41,   42,   43,   44,   45,  268,   47,   45,
  278,  256,   16,  279,   42,   47,   45,   59,  276,   47,
  271,  272,   44,   40,   42,   45,   41,  278,   60,   47,
   44,  271,  272,   40,  129,  130,   44,   59,   70,   71,
   72,  151,  259,   47,   41,  262,  123,   40,  279,   41,
   82,   41,  100,   45,  102,  258,   59,   41,   42,   43,
   40,   45,   59,   47,   59,  278,   41,   41,   41,  259,
   40,  123,  262,   41,   42,   43,   41,   45,   45,   47,
  278,  176,  278,  178,   59,   59,   59,  259,   44,  125,
  262,  258,   44,  171,  172,  173,  174,  129,  130,   42,
   43,   41,   45,   59,   47,   41,  123,   59,   44,  157,
  158,   41,  256,   43,  256,   45,  256,   42,   43,  214,
   45,   41,   47,  218,  219,   41,   41,   59,   45,  271,
  272,   59,   41,  277,  277,   44,  278,   41,   62,  171,
  172,  173,  174,   41,  176,   43,  178,   45,   41,   42,
   43,   41,   45,   45,   47,  250,  278,  278,  171,  172,
  173,  174,   41,   62,   40,   44,  125,   59,  125,   40,
  278,   41,   41,  256,   59,   59,   41,   59,   45,  256,
  278,   59,  214,  256,   59,   62,  218,  219,  256,  257,
   62,  259,  260,  261,  262,  263,  264,  280,  266,  267,
  268,   41,  270,  271,  272,  278,  276,  276,  238,  239,
  278,  241,  280,  243,  244,  278,  256,  257,  250,  259,
  260,  261,  262,  263,  264,  277,  266,  267,  268,   41,
  270,  271,  272,  278,  256,   62,  278,  276,  278,  256,
  280,  256,  257,  279,  259,  260,  261,  262,  263,  264,
  279,  266,  267,  268,   41,  270,  271,  272,   45,  279,
  277,  258,  276,  278,  256,  280,  256,  257,  276,  259,
  260,  261,  262,  263,  264,  278,  266,  267,  268,   59,
  270,  271,  272,  258,  258,  258,  278,  279,  278,   41,
  280,  256,  257,   45,  259,  260,  261,  262,  263,  264,
  256,  266,  267,  268,  256,  270,  271,  272,   60,   61,
   62,  278,  279,  278,   41,  280,  256,  257,   45,  259,
  260,  261,  262,  263,  264,   59,  266,  267,  268,  278,
  270,  271,  272,   60,   61,   62,  278,  265,  278,  256,
  280,  256,  257,   44,  259,  260,  261,  262,  263,  264,
  278,  266,  267,  268,   59,  270,  271,  272,   59,   41,
   44,  278,  279,  278,  256,  280,  256,  257,   45,  259,
  260,  261,  262,  263,  264,   59,  266,  267,  268,   62,
  270,  271,  272,   60,   61,   62,  278,  279,  278,  256,
  280,  256,  257,   60,  259,  260,  261,  262,  263,  264,
   60,  266,  267,  268,   59,  270,  271,  272,   59,   59,
  278,  278,  279,  278,  278,  280,  256,  257,  278,  259,
  260,  261,  262,  263,  264,   44,  266,  267,  268,  279,
  270,  271,  272,   59,   59,   59,   41,   59,  278,   41,
  280,   40,   59,   41,  256,  257,   45,  259,  260,  261,
  262,  263,  264,   41,  266,  267,  268,  262,  270,  271,
  272,   60,   61,   62,   59,   59,  278,   40,  280,  256,
  257,  262,   45,   59,   59,   59,  263,  264,   44,  266,
  267,   59,   59,  270,  271,  272,  262,   60,   61,   62,
  277,  278,  279,  280,   41,   42,   43,   44,   45,   59,
   47,   59,   59,  262,  256,  262,   59,  125,  279,   59,
  278,   59,   59,   41,   42,   43,   44,   45,   59,   47,
   59,  273,  274,  275,   49,  125,  278,  279,  125,  256,
  279,   59,   60,   61,   62,   41,   59,   43,   44,   45,
   41,   59,   43,   44,   45,  125,  273,  274,  275,    0,
   41,  278,  279,   59,   60,   61,   62,   41,   59,   60,
   61,   62,   42,   43,  265,   45,   -1,   47,   -1,   -1,
   95,   -1,  256,   -1,   -1,  276,   -1,  278,   -1,  256,
   60,   61,   62,   -1,  109,  110,   -1,   -1,  271,  272,
   -1,   -1,   -1,   -1,  278,  278,  273,  274,  275,   -1,
   -1,  278,  279,   -1,  271,  272,   -1,   -1,   43,  269,
   -1,   -1,   47,   -1,   -1,  257,  276,   52,  278,  261,
   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,   69,   -1,  278,  162,  280,   -1,
   -1,   76,   77,   78,   79,   80,   81,   -1,  256,  257,
   -1,  259,  260,   -1,  262,  263,  264,  256,  266,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,
  278,   -1,  280,   -1,  273,  274,  275,   -1,   -1,  278,
  279,   -1,   -1,  256,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,   -1,   -1,  278,  279,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  240,   -1,  242,  265,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,  276,
  258,  278,   -1,  168,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  273,  274,  275,   -1,   -1,
  256,   -1,  258,   -1,   -1,  256,   -1,  258,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,
   -1,   -1,  273,  274,  275,  257,  256,  259,  260,   -1,
  262,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,  273,  274,  275,  278,  257,  280,   -1,
  260,   -1,   -1,  263,  264,   -1,  266,  267,  268,   -1,
  270,  271,  272,   -1,   -1,   -1,  257,  258,  278,  260,
  280,   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,
  271,  272,   -1,   -1,  257,   -1,   -1,  278,  261,  280,
  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,   -1,  257,   -1,  278,  260,  280,   -1,  263,
  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,
   -1,   -1,   -1,   -1,  278,   -1,  280,
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

//#line 459 "gramatica.y"

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

//#line 761 "Parser.java"
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
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 67:
//#line 243 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
break;
case 68:
//#line 246 "gramatica.y"
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
case 69:
//#line 266 "gramatica.y"
{
             String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (LONGINT)*/
            String tipoBase = val_peek(3).sval;
            
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -2147483647, 2147483647));
        }
break;
case 70:
//#line 274 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (DOUBLE)*/
            String tipoBase = val_peek(3).sval;
            
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
break;
case 71:
//#line 282 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
break;
case 72:
//#line 285 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 73:
//#line 288 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 74:
//#line 291 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
break;
case 75:
//#line 294 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 76:
//#line 297 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 77:
//#line 298 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 78:
//#line 299 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 79:
//#line 300 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 80:
//#line 301 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 81:
//#line 302 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 82:
//#line 303 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 83:
//#line 304 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 84:
//#line 305 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
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
            }else if (tipo.equals("octal")) {
                if (!lexer.isOctalRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para octales.");
                    
                } else {
                    
                    st.addValue(nombreConMenos, tipo, SymbolTable.constantValue);
                }
            }
        } else {
            System.err.println("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else { //se trata de numero negativo menor al menor negativo.
    	//ACA VER QUE TIPO DE NUMERO ES CON IFS
    	
        if (nombreConstante.startsWith("0") && !nombreConstante.matches(".*[89].*")) {
        	System.err.println("El valor octal " + "-"+nombreConstante+ " se ajusto al valor minimo.");
            st.addValue("-020000000000", "Octal", SymbolTable.constantValue);
        } else if (nombreConstante.contains(".")) {
        	System.err.println("El valor double -" + nombreConstante + " se ajusta al valor mínimo.");
        	//ACA TOQUETEAR PAQUI
            st.addValue("-1.7976931348623157d+308", "double", SymbolTable.constantValue);
        } else{ //ya se sabe que es entero
            // Lógica para longint
        	System.err.println("El valor longint -" + nombreConstante + " se ajusta al valor mínimo.");
            nombreConMenos = "-2147483648"; // Asignar valor mínimo si está fuera de rango
            st.addValue(nombreConMenos, "longint", SymbolTable.constantValue);
        }
        
    }
}
break;
//#line 1412 "Parser.java"
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
