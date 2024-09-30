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
    3,    3,    3,    3,    3,    3,    3,    3,    3,    4,
    4,   14,   14,   14,    9,    9,    9,    9,    9,   15,
   16,   16,   16,   17,   17,   13,   13,   13,    6,    6,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,    7,    7,    7,    7,    7,    7,    8,
    8,    8,    8,    8,    8,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   19,   19,   19,   19,   19,   19,   18,   18,
   18,   18,   20,   20,   20,   20,   20,   20,    5,    5,
   22,   22,   21,   21,   21,   21,   21,   23,   23,   10,
   10,   10,   10,   24,   24,   25,   26,   26,   26,   26,
   26,   26,   26,   26,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   27,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    5,    4,    3,
    3,    3,    1,    2,    7,    7,    7,    7,    7,    2,
    3,    3,    0,    1,    1,    1,    1,    1,    8,   10,
    9,    7,    9,    7,    9,    7,    9,    7,    8,    6,
    8,    8,   10,    7,    6,    5,    6,    5,    7,    5,
    5,    4,    4,    4,    5,    6,    7,    7,    6,    5,
    5,    5,    7,    6,    6,    6,    6,    6,    6,    5,
    5,    5,    5,    6,    6,    7,    2,    1,    3,    3,
    2,    2,    1,    1,    1,    1,    1,    1,    4,    4,
    1,    3,    3,    3,    1,    1,    1,    4,    4,    3,
    2,    2,    2,    4,    4,    1,    3,    3,    3,    3,
    1,    1,    1,    1,    3,    3,    3,    3,    1,    1,
    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    3,  105,    0,    5,    0,    0,
    0,    0,    0,   37,   36,    0,   17,    0,    7,    9,
   10,   11,   12,   13,   14,   15,   16,    0,    0,  107,
    1,  134,   93,   94,   95,    0,  129,    0,    0,   97,
   98,   96,    0,    0,    0,  131,  132,  133,    0,    0,
    0,    0,    0,    0,    0,    0,   34,   35,    0,  113,
    0,  111,    0,    4,    8,    0,    0,    0,    0,    0,
    0,  135,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   38,    0,    0,    0,    0,    0,    0,    0,
    0,  110,    0,    0,    0,    0,    0,   21,   24,   20,
    0,    0,    0,    0,  104,    0,    0,  121,  123,    0,
    0,  124,    0,    0,    0,    0,    0,  127,  128,    0,
    0,    0,   64,    0,    0,    0,    0,    0,    0,    0,
    0,   88,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  109,  108,    0,    0,    0,   22,   99,
  100,    0,  115,  114,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   60,   65,   61,   71,   70,    0,    0,
    0,    0,    0,    0,   87,   80,   82,    0,   81,    0,
    0,   18,   56,    0,    0,    0,   58,    0,    0,    0,
    0,    0,    0,    0,    0,  119,  120,    0,    0,    0,
    0,    0,    0,    0,    0,   50,    0,   78,    0,   79,
    0,   69,    0,    0,   66,   76,   77,    0,   57,    0,
   30,    0,    0,    0,    0,    0,    0,    0,    0,   44,
    0,   48,    0,    0,    0,    0,    0,   46,    0,   67,
   68,   73,    0,    0,    0,   59,   54,   27,   25,    0,
   31,   26,   32,   29,   28,    0,   49,   52,    0,   39,
    0,    0,   51,   83,    0,    0,    0,   45,    0,    0,
   41,   47,   85,   84,    0,   53,   40,   86,
};
final static short yydgoto[] = {                          4,
   57,   18,   58,   20,   21,   22,   23,   24,   25,   26,
   27,   43,   28,   68,  199,  200,   59,   44,  144,   45,
   29,  113,   46,   47,  120,  121,   48,
};
final static short yysindex[] = {                      -219,
    0,  580, -148,    0,    0,    0,  401,    0,    3,  296,
   69, -120,  -55,    0,    0, -110,    0,  598,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -217,    5,    0,
    0,    0,    0,    0,    0,  -31,    0, -202,  250,    0,
    0,    0,  473, -147,   10,    0,    0,    0,  215,  -36,
 -205, -161, -137,   10,    0,  109,    0,    0, -212,    0,
   82,    0, -222,    0,    0,  -37,  117,  135,   10, -116,
  160,    0,  -91,  132,   10,   10,   10,   10,   10,   10,
  617,   84,    0,  134,  -31,  118,  141,   41,    0,  -94,
  -92,  344,    0, -118, -117,  126,  145,   52,  350,  150,
  429,    0,   67,   75,  168, -205, -205,    0,    0,    0,
  -66,   84,  -19, -110,    0,  172, -110,    0,    0,  173,
  138,    0,  617,  562,   84,    4,    4,    0,    0,   84,
 -220,  156,    0,  159,  227,  268,  280,  147,  290,  308,
   99,    0,  -34,  322,  -52,  326,  111,  136,  361,  386,
  350,  275,  372,    0,    0, -205,  157,  165,    0,    0,
    0,   10,    0,    0,   35,   35,   35,   35, -139,  453,
 -129,  617,  377,    0,    0,    0,    0,    0,  -45,  -44,
  175,  378,  413,  185,    0,    0,    0,  406,    0,  408,
  411,    0,    0,  432,  416,  435,    0,  -39,  101,  115,
  436,  439,   84,   63,   63,    0,    0,  617,  424,  222,
  437,  521,  617,  440,  226,    0,  443,    0,  451,    0,
  455,    0,  -18,  478,    0,    0,    0,  467,    0,  468,
    0, -148, -148, -205, -148, -205, -148, -148,  270,    0,
  471,    0,  477,  617,  488,  297,  302,    0,  493,    0,
    0,    0,  442,  289,   -9,    0,    0,    0,    0,  291,
    0,    0,    0,    0,    0,  519,    0,    0, -175,    0,
  522,  523,    0,    0,  463,  464,  311,    0,  532,  533,
    0,    0,    0,    0,  475,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  596,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  301,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  496,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  241,    0,    0,    0,    0,    0,
  -40,    0,    0,    0,    0,    0,  343,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   48,
    0,   73,  -24,    0,  319,    0,    0,    0,  -12,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    6,    0,   26,    0,    0,   59,    0,    0,    0,
  539,    0,    0,    0,  128,  501,  542,    0,    0,  130,
    0,  -13,    0,    0,   12,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   37,    0,
    0,    0,    0,    0,    0,  158,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   62,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   31,   17,  131,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   87,    0,  112,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  137,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0,  162,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  188,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  338,    0,   36,    0,    0,    0,    0,    0,    0,    0,
    0,  587,  -43,    0, -138,    0,  470,  -27,  -69,  555,
    0,    0,   -2,    0,    0,   30,  -49,
};
final static int YYTABLESIZE=897;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
  112,  232,  106,   62,  143,  143,  187,   94,   71,   30,
  184,   74,   63,  218,  220,   30,  134,  134,  134,  105,
  134,  122,  134,   92,  162,  146,  254,   63,  131,  131,
  131,  107,  131,  103,  131,  277,    1,   19,  172,  161,
    2,  173,   49,  100,  105,   78,   89,   66,   70,  101,
   79,  145,   62,   65,   38,  101,  104,  117,    3,  117,
   67,  117,  157,  158,  101,   14,   15,  115,  119,  103,
  143,  150,   93,  153,  102,  188,   72,   19,   30,   38,
  279,  135,   78,   76,   87,   77,  280,   79,   91,  102,
  185,   63,  149,   78,   76,  261,   77,  263,   79,  122,
  122,  122,   72,  122,  167,  122,   91,    1,   54,  168,
   81,    2,  198,   92,   95,  122,  122,  122,  122,  208,
   30,   30,  209,  194,  196,   78,   76,   74,   77,  213,
   79,   92,  214,   96,   97,   55,    7,  142,  142,    2,
  102,  233,    9,   10,  234,   11,   12,   56,   99,   13,
   14,   15,   75,   14,   15,  235,  107,   16,  236,   17,
   93,  114,  119,  119,  119,  119,  123,   30,   90,   30,
   89,  118,  124,  118,  132,  118,  133,   55,  111,  167,
  165,  134,  166,  136,  168,  137,   90,  147,   89,  151,
  260,  154,  260,  110,  204,  205,  206,  207,   33,  155,
   60,   33,   42,  142,   38,   30,  148,  156,  179,   30,
   30,  159,  163,  164,  174,  112,  112,  175,  112,  112,
  112,  112,  112,  112,   61,  112,  112,  112,   43,  112,
  112,  112,  217,  219,   90,   91,  160,  112,  231,  112,
  105,   30,   63,   63,  183,   63,   63,   63,   63,   63,
   63,  105,   63,   63,   63,   86,   63,   63,   63,   38,
  253,  101,    6,  107,   63,   32,   63,   62,   62,  276,
   62,   62,   62,   62,   62,   62,  105,   62,   62,   62,
   69,   62,   62,   62,  105,  176,  102,   36,   37,   62,
   73,   62,   19,   19,   38,   19,   19,   19,   19,   19,
   19,  103,   19,   19,   19,   91,   19,   19,   19,   40,
   42,   41,  117,  118,   19,  195,   19,   72,   72,   38,
   72,   72,   72,   72,   72,   72,  177,   72,   72,   72,
   92,   72,   72,   72,   40,   42,   41,    5,  178,   72,
   31,   72,   74,   74,  106,   74,   74,   74,   74,   74,
   74,  180,   74,   74,   74,   53,   74,   74,   74,  130,
  130,  130,  106,  130,   74,  130,   74,   75,   75,  181,
   75,   75,   75,   75,   75,   75,  182,   75,   75,   75,
  186,   75,   75,   75,  189,   90,   23,   89,  190,   75,
  108,   75,   55,   55,   38,   55,   55,   55,   55,   55,
   55,   23,   55,   55,   55,  141,   55,   55,   55,   40,
   42,   41,  109,  191,   55,  116,   55,   42,   42,  192,
   42,   42,   42,   42,   42,   42,  193,   42,   42,   42,
  197,   42,   42,   42,  201,  216,  222,  117,  118,   42,
   39,   42,  202,   43,   43,   38,   43,   43,   43,   43,
   43,   43,  221,   43,   43,   43,  223,   43,   43,   43,
   40,   42,   41,  224,  225,   43,  226,   43,  152,  227,
   83,    7,  228,   38,  229,  230,  237,    9,   10,  238,
   11,   12,  240,  241,   13,   14,   15,  249,   40,   42,
   41,   84,   85,   37,   17,  242,    6,    6,  248,    6,
    6,  250,    6,    6,    6,   32,    6,    6,    6,  251,
    6,    6,    6,  252,   78,   76,  105,   77,    6,   79,
    6,  255,   33,   34,   35,  256,  257,   36,   37,  267,
   32,  266,   40,   42,   41,  268,  130,  130,  130,  130,
  130,  125,  130,  125,  125,  125,  270,   33,   34,   35,
  131,  273,   36,   37,  130,  130,  130,  130,  271,  125,
  125,  125,  125,  272,   50,   38,  274,  275,  231,  258,
  259,   51,  262,   52,  264,  265,  106,  278,   38,  116,
  281,  282,  126,   38,  126,  126,  126,  283,  284,  285,
  286,  287,  169,  171,  106,    2,   38,   80,   23,  288,
  126,  126,  126,  126,    0,   32,    0,    0,    0,    0,
    0,    0,    0,    0,  138,  139,    0,    0,    0,    0,
   23,  140,   33,   34,   35,    0,    0,   36,   37,    0,
    0,   82,    0,    0,    0,   88,    0,    0,    0,  212,
   98,  215,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  112,   32,    0,    0,    0,
    0,  125,  126,  127,  128,  129,  130,    0,    0,    0,
    0,    0,    0,   33,   34,   35,    0,  239,   36,   37,
    0,  246,  247,    0,   32,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   33,   34,   35,    0,    0,   36,   37,   55,    7,
    0,  210,    2,  269,  211,    9,   10,    0,   11,   12,
    0,    0,   13,   14,   15,    0,    0,    0,   75,    0,
   16,    0,   17,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   33,   34,   35,  203,    0,
    0,  130,    0,  130,    0,    0,  125,    0,  125,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  130,  130,
  130,    0,    0,  125,  125,  125,  243,    7,    0,  244,
    2,    0,  245,    9,   10,    0,   11,   12,    0,    0,
   13,   14,   15,    0,    0,    0,    0,  126,   16,  126,
   17,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  126,  126,  126,   55,    7,  170,
    0,    2,    0,    0,    9,   10,    0,   11,   12,    0,
    0,   13,   14,   15,    0,    6,    7,    0,    0,   16,
    8,   17,    9,   10,    0,   11,   12,    0,    0,   13,
   14,   15,    0,    6,    7,    0,    0,   16,   64,   17,
    9,   10,    0,   11,   12,    0,    0,   13,   14,   15,
    0,    0,   55,    7,    0,   16,    2,   17,    0,    9,
   10,    0,   11,   12,    0,    0,   13,   14,   15,    0,
    0,    0,    0,    0,   16,    0,   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
   41,   41,   40,   59,  123,  123,   59,   51,   40,   12,
   45,   39,  123,   59,   59,   18,   41,   42,   43,   44,
   45,   71,   47,   60,   44,   95,   45,   41,   41,   42,
   43,   44,   45,  256,   47,   45,  256,    2,  259,   59,
  260,  262,   40,  256,   44,   42,   49,  265,   44,   44,
   47,   95,   41,   18,   45,  268,  279,   41,  278,   43,
  278,   45,  106,  107,   59,  271,  272,   70,   71,   44,
  123,   99,  278,  101,   44,  145,  279,   41,   81,   45,
  256,   41,   42,   43,   49,   45,  262,   47,   41,   59,
  125,  123,   41,   42,   43,  234,   45,  236,   47,   41,
   42,   43,   41,   45,   42,   47,   59,  256,   40,   47,
  258,  260,  156,   41,  276,  165,  166,  167,  168,  259,
  123,  124,  262,  151,  152,   42,   43,   41,   45,  259,
   47,   59,  262,  271,  272,  256,  257,  256,  256,  260,
   59,   41,  263,  264,   44,  266,  267,  268,   40,  270,
  271,  272,   41,  271,  272,   41,   40,  278,   44,  280,
  278,  278,  165,  166,  167,  168,  258,  170,   41,  172,
   41,   41,   41,   43,   41,   45,   59,   41,   44,   42,
   43,   41,   45,  278,   47,  278,   59,   62,   59,   40,
  234,  125,  236,   59,  165,  166,  167,  168,   41,  125,
  256,   44,   41,  256,   45,  208,   62,   40,   62,  212,
  213,  278,   41,   41,   59,  256,  257,   59,  259,  260,
  261,  262,  263,  264,  280,  266,  267,  268,   41,  270,
  271,  272,  278,  278,  271,  272,  256,  278,  278,  280,
  278,  244,  256,  257,  279,  259,  260,  261,  262,  263,
  264,  276,  266,  267,  268,   41,  270,  271,  272,   45,
  279,  256,  262,  276,  278,  256,  280,  256,  257,  279,
  259,  260,  261,  262,  263,  264,  276,  266,  267,  268,
  276,  270,  271,  272,   44,   59,  256,  278,  279,  278,
   41,  280,  256,  257,   45,  259,  260,  261,  262,  263,
  264,  276,  266,  267,  268,  258,  270,  271,  272,   60,
   61,   62,  278,  279,  278,   41,  280,  256,  257,   45,
  259,  260,  261,  262,  263,  264,   59,  266,  267,  268,
  258,  270,  271,  272,   60,   61,   62,    0,   59,  278,
    3,  280,  256,  257,   44,  259,  260,  261,  262,  263,
  264,   62,  266,  267,  268,   60,  270,  271,  272,   41,
   42,   43,   44,   45,  278,   47,  280,  256,  257,   62,
  259,  260,  261,  262,  263,  264,  278,  266,  267,  268,
   59,  270,  271,  272,   59,  258,   44,  258,  278,  278,
  256,  280,  256,  257,   45,  259,  260,  261,  262,  263,
  264,   59,  266,  267,  268,   62,  270,  271,  272,   60,
   61,   62,  278,  278,  278,  256,  280,  256,  257,   59,
  259,  260,  261,  262,  263,  264,   41,  266,  267,  268,
   59,  270,  271,  272,  278,   59,   59,  278,  279,  278,
   40,  280,  278,  256,  257,   45,  259,  260,  261,  262,
  263,  264,  278,  266,  267,  268,   44,  270,  271,  272,
   60,   61,   62,  279,   59,  278,   59,  280,   40,   59,
  256,  257,   41,   45,   59,   41,   41,  263,  264,   41,
  266,  267,   59,  262,  270,  271,  272,  262,   60,   61,
   62,  277,  278,  279,  280,   59,  256,  257,   59,  259,
  260,   59,  262,  263,  264,  256,  266,  267,  268,   59,
  270,  271,  272,   59,   42,   43,  276,   45,  278,   47,
  280,   44,  273,  274,  275,   59,   59,  278,  279,   59,
  256,  262,   60,   61,   62,   59,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   59,  273,  274,  275,
   81,   59,  278,  279,   59,   60,   61,   62,  262,   59,
   60,   61,   62,  262,  269,  265,  125,  279,  278,  232,
  233,  276,  235,  278,  237,  238,  276,   59,  278,   41,
   59,   59,   41,  265,   43,   44,   45,  125,  125,  279,
   59,   59,  123,  124,  276,    0,  278,   43,  256,  125,
   59,   60,   61,   62,   -1,  256,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  271,  272,   -1,   -1,   -1,   -1,
  278,  278,  273,  274,  275,   -1,   -1,  278,  279,   -1,
   -1,   45,   -1,   -1,   -1,   49,   -1,   -1,   -1,  170,
   54,  172,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   69,  256,   -1,   -1,   -1,
   -1,   75,   76,   77,   78,   79,   80,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,  208,  278,  279,
   -1,  212,  213,   -1,  256,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  273,  274,  275,   -1,   -1,  278,  279,  256,  257,
   -1,  259,  260,  244,  262,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,  256,   -1,
  278,   -1,  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  273,  274,  275,  162,   -1,
   -1,  256,   -1,  258,   -1,   -1,  256,   -1,  258,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,
  275,   -1,   -1,  273,  274,  275,  256,  257,   -1,  259,
  260,   -1,  262,  263,  264,   -1,  266,  267,   -1,   -1,
  270,  271,  272,   -1,   -1,   -1,   -1,  256,  278,  258,
  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  273,  274,  275,  256,  257,  258,
   -1,  260,   -1,   -1,  263,  264,   -1,  266,  267,   -1,
   -1,  270,  271,  272,   -1,  256,  257,   -1,   -1,  278,
  261,  280,  263,  264,   -1,  266,  267,   -1,   -1,  270,
  271,  272,   -1,  256,  257,   -1,   -1,  278,  261,  280,
  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,  256,  257,   -1,  278,  260,  280,   -1,  263,
  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,
   -1,   -1,   -1,   -1,  278,   -1,  280,
};
}
final static short YYFINAL=4;
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
"sentencia : RET '(' expresion ')'",
"declaracion : tipo lista_var ';'",
"declaracion : tipo lista_var error",
"lista_var : lista_var ',' T_ID",
"lista_var : T_ID",
"lista_var : lista_var T_ID",
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
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : error",
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : acceso_par",
"acceso_par : T_ID '{' T_CTE '}'",
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
"unaria : '-' T_CTE",
};

//#line 452 "gramatica.y"

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
    Parser parser = new Parser("C:\\Users\\usuario\\Desktop\\prueba.txt");
    parser.run();
    parser.imprimirSymbolTable();
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
    public void imprimirSymbolTable() {
	System.out.println(this.st);
    }

//#line 735 "Parser.java"
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
case 4:
//#line 67 "gramatica.y"
{System.out.println("Llegue a BEGIN sentencia END");}
break;
case 5:
//#line 68 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 6:
//#line 69 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan Delimitador o Bloque de Sentencia");}
break;
case 19:
//#line 85 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 20:
//#line 89 "gramatica.y"
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
                System.err.println("Error al actualizar el tipo de la variable: " + variable);
            }
        }
    }
}
break;
case 21:
//#line 109 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 24:
//#line 115 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 26:
//#line 121 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }
break;
case 27:
//#line 126 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }
break;
case 28:
//#line 130 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 29:
//#line 134 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
    }
break;
case 31:
//#line 142 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 32:
//#line 145 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 33:
//#line 148 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
    }
break;
case 36:
//#line 158 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 37:
//#line 159 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 38:
//#line 161 "gramatica.y"
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
case 41:
//#line 176 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 42:
//#line 177 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 43:
//#line 180 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 44:
//#line 184 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 45:
//#line 187 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 46:
//#line 191 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 47:
//#line 194 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 48:
//#line 198 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 49:
//#line 201 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 50:
//#line 204 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 51:
//#line 205 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 52:
//#line 206 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 53:
//#line 207 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 55:
//#line 214 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 56:
//#line 217 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 57:
//#line 220 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 58:
//#line 223 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 59:
//#line 224 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 62:
//#line 232 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 63:
//#line 235 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 64:
//#line 238 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 65:
//#line 239 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");}
break;
case 66:
//#line 242 "gramatica.y"
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
//#line 262 "gramatica.y"
{
             String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (LONGINT)*/
            String tipoBase = val_peek(3).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -2147483647, 2147483647));
        }
break;
case 68:
//#line 270 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (DOUBLE)*/
            String tipoBase = val_peek(3).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
break;
case 69:
//#line 278 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaración de tipo.");
        }
break;
case 70:
//#line 281 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 71:
//#line 284 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 72:
//#line 287 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
break;
case 73:
//#line 290 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 74:
//#line 293 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 75:
//#line 294 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 76:
//#line 295 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 77:
//#line 296 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 78:
//#line 297 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 79:
//#line 298 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 80:
//#line 299 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 81:
//#line 300 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 82:
//#line 301 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 83:
//#line 303 "gramatica.y"
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
case 87:
//#line 328 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 88:
//#line 329 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 90:
//#line 335 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 91:
//#line 336 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 92:
//#line 337 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 99:
//#line 349 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 105:
//#line 360 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 108:
//#line 367 "gramatica.y"
{
            
        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
        }
        
    }
break;
case 109:
//#line 376 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Solo se puede acceder a un par con 1 o 2");}
break;
case 111:
//#line 380 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 112:
//#line 381 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 113:
//#line 382 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 115:
//#line 387 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida"); /*CAMBIAR*/
        }
break;
case 134:
//#line 413 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 135:
//#line 416 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
    double valor = val_peek(0).dval;  /* Obtenemos el valor de la constante.*/
      /* Aplicamos el signo negativo.*/

    String nombreConstante = val_peek(0).sval;  /* Obtenemos el nombre de la constante propagado desde la regla `expresion`.*/
    String nombreConMenos = "-" + nombreConstante;
    /* Ahora puedes realizar la verificación en la tabla de símbolos.*/
    if (st.hasKey(nombreConstante)) {
        String tipo = st.getType(nombreConstante);  /* Obtenemos el tipo de la constante.*/
        if (tipo != null) {
            /* Verificamos si el valor original (sin negativo) está en el rango adecuado según el tipo.*/
            if (tipo.equals("longint")) {
                if (!lexer.isLongintRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " está fuera del rango permitido para longint.");
                } else {
                	
                    /* Si el valor está dentro del rango de longint, actualizamos el valor negativo en la tabla.*/
                    st.addValue(nombreConMenos, tipo,SymbolTable.constantValue);
                }
            } else if (tipo.equals("double")) {
                if (!lexer.isDoubleRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " está fuera del rango permitido para double.");
                } else {
                    /* Si el valor está dentro del rango de double, actualizamos el valor negativo en la tabla.*/
                    st.addValue(nombreConMenos, tipo, SymbolTable.constantValue);
                }
            }
        } else {
            System.err.println("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else {
        System.err.println("Error: La constante " + nombreConstante + " no existe en la tabla de símbolos.");
    }
}
break;
//#line 1365 "Parser.java"
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
