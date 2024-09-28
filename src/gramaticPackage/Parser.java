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
    6,    7,    7,    7,    7,    7,    8,    8,    8,    8,
    8,    8,   11,   11,   11,   11,   11,   11,   11,   20,
   20,   20,   20,   20,   19,   19,   19,   21,   21,   21,
   21,   21,   21,    5,    5,   22,   22,   22,   22,   23,
   23,   24,   24,   24,   24,   10,   10,   10,   25,   25,
   26,   27,   27,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   29,   29,   29,   29,   29,   29,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   28,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    5,    0,    5,
    3,    2,    3,    1,    1,    7,    7,    7,    7,    7,
    2,    3,    3,    0,    1,    1,    1,    1,    1,    8,
   10,    7,    9,    7,    9,    7,    9,    7,    8,    6,
    8,    7,    6,    5,    6,    5,    5,    5,    4,    4,
    4,    5,    6,    7,    7,    5,    7,    6,    6,    5,
    6,    6,    7,    1,    3,    2,    2,    1,    1,    1,
    1,    1,    1,    4,    3,    1,    3,    3,    1,    1,
    3,    4,    3,    3,    3,    3,    2,    2,    4,    1,
    1,    3,    3,    3,    3,    1,    1,    1,    1,    4,
    4,    4,    1,    1,    1,    2,    2,    2,    3,    3,
    3,    3,    1,    1,    1,    1,    1,    4,    4,    4,
    2,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    3,    0,    5,    0,    0,    0,
    0,    0,   38,   37,    0,   17,    0,    8,    9,   10,
   11,   12,   13,   14,   15,   16,    0,    0,   89,    1,
  100,   78,   79,   80,    0,  123,    0,    0,   82,   83,
   81,    0,    0,    0,  125,  126,  127,    0,    0,    0,
    0,    0,   35,   36,    0,    0,   97,    0,    0,    4,
    7,   25,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   96,
   95,    0,   93,    0,    0,    0,   21,    0,    0,    0,
    0,   88,    0,  106,  108,    0,    0,  109,    0,    0,
  113,  114,  115,    0,    0,    0,  121,    0,  122,    0,
    0,    0,    0,   61,    0,    0,    0,    0,    0,   39,
    0,    0,    0,    0,    0,   92,    0,    0,    0,   23,
   84,    0,   99,    0,    0,    0,    0,    0,    0,    0,
  116,  118,  117,    0,  129,  130,    0,    0,   57,   62,
   58,    0,    0,    0,   74,    0,    0,   18,   20,   54,
    0,    0,   56,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  104,    0,  105,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   50,    0,    0,    0,    0,    0,
   63,   55,    0,   31,    0,    0,    0,    0,    0,    0,
    0,    0,  111,  112,    0,   44,    0,   48,    0,    0,
    0,   46,    0,   64,   65,   67,    0,    0,   52,   28,
   26,    0,   32,   27,   33,   30,   29,    0,   49,    0,
   40,    0,   51,    0,    0,    0,   45,    0,   47,   70,
    0,    0,    0,   41,   72,   71,    0,   73,
};
final static short yydgoto[] = {                          4,
   53,   17,   54,   19,   20,   21,   22,   23,   24,   25,
   26,   42,  169,   27,   65,  175,  176,   55,   43,  167,
   44,   28,  100,   45,   46,  106,  107,   47,  115,
};
final static short yysindex[] = {                      -198,
    0,  765, -175,    0,    0,  444,    0,  -29, -232,  -11,
  593,  -42,    0,    0, -115,    0,  783,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -177,   10,    0,    0,
    0,    0,    0,    0,   21,    0,  451,  665,    0,    0,
    0,  708, -235,  451,    0,    0,    0,  160,  -21, -229,
  451,   72,    0,    0, -181,   45,    0,   -6, -113,    0,
    0,    0,  -35,   73,   11,  451, -151,   98,   47, -129,
   95,  164,  451,  164,  164,  451,  747,  128,   96,   21,
  108,  127,  716,    0, -262, -169,  785,  518,  468,    0,
    0,   32,    0,  137, -169, -169,    0, -102,  128,   27,
 -115,    0, -115,    0,    0,  145,  768,    0,  747,  728,
    0,    0,    0,   47,  619,   47,    0,  619,    0,  619,
  128, -199,  138,    0,  155,  156,  146,  157,  162,    0,
 -110,  177,  218,  675,  201,    0, -169,  -16,    6,    0,
    0,  451,    0,  116,   98,  116,  116, -154,  -68, -141,
    0,    0,    0,   47,    0,    0,  747,  249,    0,    0,
    0,   37,   53,   54,    0,    4,  275,    0,    0,    0,
  280,  314,    0,  -26,  106,  107,  315,  320,  128,   64,
  136,   64,    0,  136,    0,  136,  747,  311,  112,  316,
  -75,  747,  331,  134,    0,  338,  351,  359,  378,  150,
    0,    0,  374,    0, -175, -175, -169, -175, -169, -175,
 -175,   64,    0,    0,  173,    0,  377,    0,  747,  382,
  194,    0,  401,    0,    0,    0,   12,  417,    0,    0,
    0,  184,    0,    0,    0,    0,    0,  404,    0,  214,
    0,  421,    0,  356,  203,   28,    0,  424,    0,    0,
  361,  366,  221,    0,    0,    0,  385,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  507,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  169,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  187,    0,    0,    0,    0,
    0,    0,    0,  101,  207,    0,    0,    0,    7,    0,
    0,    0,    0,    0,    0,   18,    0,   66,    0,  511,
    0,    0,    0,   -9,    0,    0,    0,    0,    0,    0,
    0,  -41,    0,    0,    0,    0,    0,    0,  121,  231,
   34,    0,  825,    0,    0,    0,  474,    0,    0,    0,
    0,    0,    0,   31,    0,   55,    0,    0,    0,    0,
   90,    0,  255,    0,    0,  279,    0,    0,    0,    0,
    0,  303,    0,    0,    0,    0,  149,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   79,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  325,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  141,   85,
    0,  597,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  345,  365,    0,    0,    0,
    0,    0,  387,    0,    0,    0,    0,    0,    0,    0,
    0,  721,    0,    0,    0,    0,    0,    0,    0,  411,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  431,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  598,    0,    5,    0,    0,    0,    0,    0,    0,    0,
    0,  770,    0,  -55,    0, -112,    0,  603,  -24,    0,
  478,    0,    0,  771,    0,    0,  639,  -12,    9,
};
final static int YYTABLESIZE=1063;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         94,
   94,   94,   94,   94,   95,   94,   18,   59,  127,  128,
   48,   93,  166,   71,  205,  129,   57,   94,   94,   94,
   94,   61,   77,  124,  124,  124,  124,  124,   51,  124,
  131,  125,  125,  125,   89,  125,   49,  125,   85,  138,
  139,  124,  124,  124,  124,   50,   86,  131,  200,  131,
  131,  131,   82,   67,   98,  108,  245,    1,   76,  157,
   68,    2,  158,  133,  135,  131,  131,  131,  131,   97,
  142,  119,  253,  119,  119,  119,   76,   87,   62,    3,
    1,  174,  118,  120,    2,  141,   89,   63,   74,  119,
  119,  119,  119,   75,  233,  120,  235,  120,  120,  120,
   64,   13,   14,   90,  187,  146,   77,  188,  130,  172,
  147,   88,   96,  120,  120,  120,  120,  192,   91,  128,
  193,  128,  128,  128,   77,  102,  101,  102,  109,  102,
   75,  108,  108,  108,  108,  110,  123,  128,  128,  128,
  128,   24,   37,   59,   24,  165,  206,  208,   75,  207,
  209,  232,  181,  232,  184,  186,  136,  112,  111,   24,
   37,   90,  113,   58,   90,   92,  124,  125,  108,   74,
   72,  108,   73,  108,   75,  140,  137,  152,  151,   90,
   37,   91,  153,  219,   91,  143,  220,    1,    6,   34,
  189,    2,   34,  190,    8,    9,  159,   10,   11,   91,
   81,   12,   13,   14,   37,  112,  111,  162,   37,   15,
  113,   16,   86,  160,  161,   94,   94,   94,  163,   94,
   94,   94,   94,  164,   94,   94,   94,   98,   94,   94,
   94,   94,   94,   94,   94,  168,   94,   56,   94,  124,
  124,  124,   94,  124,  124,  124,  124,   22,  124,  124,
  124,  204,  124,  124,  124,  124,  124,  124,  170,  173,
  124,  177,  124,  131,  131,  131,   89,  131,  131,  131,
  131,   85,  131,  131,  131,   76,  131,  131,  131,  131,
  131,  131,  199,  178,  131,   66,  131,  119,  119,  119,
  244,  119,  119,  119,  119,   60,  119,  119,  119,   58,
  119,  119,  119,  119,  119,  119,  252,  195,  119,   87,
  119,  120,  120,  120,  196,  120,  120,  120,  120,   59,
  120,  120,  120,   77,  120,  120,  120,  120,  120,  120,
  197,  198,  120,  201,  120,  128,  128,  128,  202,  128,
  128,  128,  128,   19,  128,  128,  128,   75,  128,  128,
  128,  128,  128,  128,  203,  210,  128,   24,  128,   24,
  211,   24,   24,   24,   24,   66,   24,   24,   24,  216,
   24,   24,   24,  217,  218,  103,  104,   90,   24,   90,
   24,   90,   90,   90,   90,   68,   90,   90,   90,  222,
   90,   90,   90,  103,  104,  223,  224,   91,   90,   91,
   90,   91,   91,   91,   91,   69,   91,   91,   91,  225,
   91,   91,   91,  103,  104,   31,    6,  226,   91,   31,
   91,  227,    8,    9,   39,   10,   11,   53,  228,   12,
   13,   14,  229,   39,  238,  239,   79,   80,   36,   16,
  241,   35,   36,   98,   86,   98,   39,   98,   98,   98,
   98,   42,   98,   98,   98,  242,   98,   98,   98,  243,
  246,  204,  247,   22,   98,   22,   98,   22,   22,   22,
   22,   43,   22,   22,   22,  248,   22,   22,   22,  249,
  250,  251,  254,   38,   22,  255,   22,   85,   37,   85,
  256,   85,   85,   85,   85,   37,   85,   85,   85,  257,
   85,   85,   85,   39,   41,   40,    2,  134,   85,  258,
   85,   60,   37,   60,  101,   60,   60,   60,   60,   76,
   60,   60,   60,    0,   60,   60,   60,   39,   41,   40,
    0,    0,   60,    0,   60,   59,    0,   59,    0,   59,
   59,   59,   59,    0,   59,   59,   59,    0,   59,   59,
   59,  124,  124,  124,   86,  124,   59,  124,   59,   19,
    0,   19,   37,   19,   19,   19,   19,    0,   19,   19,
   19,    0,   19,   19,   19,    0,    0,   39,   41,   40,
   19,   66,   19,   66,    0,   66,   66,   66,   66,    0,
   66,   66,   66,    0,   66,   66,   66,    5,    0,    0,
   30,   68,   66,   68,   66,   68,   68,   68,   68,    0,
   68,   68,   68,    0,   68,   68,   68,    0,    0,    0,
    0,   69,   68,   69,   68,   69,   69,   69,   69,    0,
   69,   69,   69,    0,   69,   69,   69,  103,    0,  103,
    0,  103,   69,   53,   69,   53,    0,   53,   53,   53,
   53,    0,   53,   53,   53,    0,   53,   53,   53,    0,
  152,  151,    0,   37,   53,  153,   53,   42,    0,   42,
    0,   42,   42,   42,   42,    0,   42,   42,   42,  122,
   42,   42,   42,    0,    0,    0,    0,   43,   42,   43,
   42,   43,   43,   43,   43,    0,   43,   43,   43,   31,
   43,   43,   43,    0,    0,   70,   31,    0,   43,   37,
   43,  148,  150,    0,    0,  171,   32,   33,   34,   37,
    0,   35,   36,   31,   39,   41,   40,    0,   35,   36,
    0,    0,    0,    0,   39,   41,   40,    0,    0,    0,
   32,   33,   34,    0,    0,   35,   36,    0,    0,   74,
   72,  191,   73,    0,   75,    0,  126,   74,   72,  194,
   73,  110,   75,  110,    0,  110,   39,   39,   41,   40,
    0,    0,   29,   31,    0,   39,    0,    0,    0,    0,
    0,   29,  180,  182,  183,  185,   86,   29,   39,  215,
   32,   33,   34,    0,  221,   35,   36,    0,    0,    0,
    0,    0,  230,  231,    0,  234,   69,  236,  237,  146,
  144,    0,  145,   78,  147,    0,    0,   83,   84,  212,
   87,  240,  213,    0,  214,  132,   74,   72,    0,   73,
    0,   75,    0,    0,    0,   99,    0,  102,  105,    0,
    0,  114,  116,  117,  119,  121,    0,   29,    1,    6,
    0,    0,    2,    0,    0,    8,    9,    0,   10,   11,
   52,    0,   12,   13,   14,  107,  107,  107,    0,  107,
   15,  107,   16,    0,   31,    0,    0,    0,    0,   29,
   29,    0,    0,    0,  154,    0,    0,  155,    0,  156,
    0,    0,    0,    0,    0,    0,   35,   36,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  179,    0,    0,  105,  105,  105,  105,    0,   29,
   31,    0,    0,    0,    0,    0,    0,   29,    0,    0,
   31,    0,    0,    0,    0,    0,    0,   32,   33,   34,
    0,    0,   35,   36,    0,    0,    0,   32,   33,   34,
    0,  105,   35,   36,  105,    0,  105,   29,    0,    0,
    0,    0,   29,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   32,   33,   34,    1,    6,  149,    0,    2,    0,   29,
    8,    9,    0,   10,   11,    0,    0,   12,   13,   14,
    0,    0,    1,    6,    0,   15,    2,   16,    0,    8,
    9,    0,   10,   11,    0,    0,   12,   13,   14,    0,
    0,    6,    0,    0,   15,    7,   16,    8,    9,    0,
   10,   11,    0,    0,   12,   13,   14,    0,    0,    6,
    0,    0,   15,   60,   16,    8,    9,    0,   10,   11,
    0,    0,   12,   13,   14,    0,    0,    0,    0,    0,
   15,    0,   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   40,   47,    2,  123,  271,  272,
   40,  125,  123,   38,   41,  278,   59,   59,   60,   61,
   62,   17,  258,   41,   42,   43,   44,   45,   40,   47,
   86,   41,   42,   43,   44,   45,  269,   47,   60,   95,
   96,   59,   60,   61,   62,  278,  276,   41,   45,   43,
   44,   45,   48,   44,   44,   68,   45,  256,   41,  259,
   40,  260,  262,   88,   89,   59,   60,   61,   62,   59,
   44,   41,   45,   43,   44,   45,   59,   44,  256,  278,
  256,  137,   74,   75,  260,   59,  268,  265,   42,   59,
   60,   61,   62,   47,  207,   41,  209,   43,   44,   45,
  278,  271,  272,   59,  259,   42,   41,  262,  278,  134,
   47,   40,   40,   59,   60,   61,   62,  259,  125,   41,
  262,   43,   44,   45,   59,   41,  278,   43,  258,   45,
   41,  144,  145,  146,  147,   41,   41,   59,   60,   61,
   62,   41,   45,  123,   44,  256,   41,   41,   59,   44,
   44,  207,  144,  209,  146,  147,  125,   42,   43,   59,
   45,   41,   47,  279,   44,  279,   59,   41,  181,   42,
   43,  184,   45,  186,   47,  278,   40,   42,   43,   59,
   45,   41,   47,  259,   44,   41,  262,  256,  257,   41,
  259,  260,   44,  262,  263,  264,   59,  266,  267,   59,
   41,  270,  271,  272,   45,   42,   43,   62,   45,  278,
   47,  280,   44,   59,   59,  257,  258,  259,   62,  261,
  262,  263,  264,   62,  266,  267,  268,   41,  270,  271,
  272,  273,  274,  275,  276,   59,  278,  280,  280,  257,
  258,  259,  278,  261,  262,  263,  264,   41,  266,  267,
  268,  278,  270,  271,  272,  273,  274,  275,   41,   59,
  278,  278,  280,  257,  258,  259,  276,  261,  262,  263,
  264,   41,  266,  267,  268,  258,  270,  271,  272,  273,
  274,  275,  279,  278,  278,  276,  280,  257,  258,  259,
  279,  261,  262,  263,  264,   41,  266,  267,  268,  279,
  270,  271,  272,  273,  274,  275,  279,   59,  278,  276,
  280,  257,  258,  259,  278,  261,  262,  263,  264,   41,
  266,  267,  268,  258,  270,  271,  272,  273,  274,  275,
  278,  278,  278,   59,  280,  257,  258,  259,   59,  261,
  262,  263,  264,   41,  266,  267,  268,  258,  270,  271,
  272,  273,  274,  275,   41,   41,  278,  257,  280,  259,
   41,  261,  262,  263,  264,   41,  266,  267,  268,   59,
  270,  271,  272,  262,   59,  278,  279,  257,  278,  259,
  280,  261,  262,  263,  264,   41,  266,  267,  268,   59,
  270,  271,  272,  278,  279,  262,   59,  257,  278,  259,
  280,  261,  262,  263,  264,   41,  266,  267,  268,   59,
  270,  271,  272,  278,  279,  256,  257,   59,  278,  256,
  280,   44,  263,  264,  256,  266,  267,   41,  279,  270,
  271,  272,   59,  265,  262,   59,  277,  278,  279,  280,
   59,  278,  279,  257,  276,  259,  278,  261,  262,  263,
  264,   41,  266,  267,  268,  262,  270,  271,  272,   59,
   44,  278,   59,  257,  278,  259,  280,  261,  262,  263,
  264,   41,  266,  267,  268,  262,  270,  271,  272,   59,
  125,  279,   59,   40,  278,  125,  280,  257,   45,  259,
  125,  261,  262,  263,  264,   45,  266,  267,  268,  279,
  270,  271,  272,   60,   61,   62,    0,   40,  278,  125,
  280,  257,   45,  259,   41,  261,  262,  263,  264,   42,
  266,  267,  268,   -1,  270,  271,  272,   60,   61,   62,
   -1,   -1,  278,   -1,  280,  257,   -1,  259,   -1,  261,
  262,  263,  264,   -1,  266,  267,  268,   -1,  270,  271,
  272,   41,   42,   43,   44,   45,  278,   47,  280,  257,
   -1,  259,   45,  261,  262,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   -1,   -1,   60,   61,   62,
  278,  257,  280,  259,   -1,  261,  262,  263,  264,   -1,
  266,  267,  268,   -1,  270,  271,  272,    0,   -1,   -1,
    3,  257,  278,  259,  280,  261,  262,  263,  264,   -1,
  266,  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,  257,  278,  259,  280,  261,  262,  263,  264,   -1,
  266,  267,  268,   -1,  270,  271,  272,   41,   -1,   43,
   -1,   45,  278,  257,  280,  259,   -1,  261,  262,  263,
  264,   -1,  266,  267,  268,   -1,  270,  271,  272,   -1,
   42,   43,   -1,   45,  278,   47,  280,  257,   -1,  259,
   -1,  261,  262,  263,  264,   -1,  266,  267,  268,   77,
  270,  271,  272,   -1,   -1,   -1,   -1,  257,  278,  259,
  280,  261,  262,  263,  264,   -1,  266,  267,  268,  256,
  270,  271,  272,   -1,   -1,   41,  256,   -1,  278,   45,
  280,  109,  110,   -1,   -1,   41,  273,  274,  275,   45,
   -1,  278,  279,  256,   60,   61,   62,   -1,  278,  279,
   -1,   -1,   -1,   -1,   60,   61,   62,   -1,   -1,   -1,
  273,  274,  275,   -1,   -1,  278,  279,   -1,   -1,   42,
   43,  149,   45,   -1,   47,   -1,   41,   42,   43,  157,
   45,   41,   47,   43,   -1,   45,  256,   60,   61,   62,
   -1,   -1,    2,  256,   -1,  265,   -1,   -1,   -1,   -1,
   -1,   11,  144,  145,  146,  147,  276,   17,  278,  187,
  273,  274,  275,   -1,  192,  278,  279,   -1,   -1,   -1,
   -1,   -1,  205,  206,   -1,  208,   37,  210,  211,   42,
   43,   -1,   45,   44,   47,   -1,   -1,   48,   48,  181,
   51,  219,  184,   -1,  186,   41,   42,   43,   -1,   45,
   -1,   47,   -1,   -1,   -1,   66,   -1,   67,   68,   -1,
   -1,   72,   73,   74,   75,   76,   -1,   77,  256,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   41,   42,   43,   -1,   45,
  278,   47,  280,   -1,  256,   -1,   -1,   -1,   -1,  109,
  110,   -1,   -1,   -1,  115,   -1,   -1,  118,   -1,  120,
   -1,   -1,   -1,   -1,   -1,   -1,  278,  279,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  142,   -1,   -1,  144,  145,  146,  147,   -1,  149,
  256,   -1,   -1,   -1,   -1,   -1,   -1,  157,   -1,   -1,
  256,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,
   -1,   -1,  278,  279,   -1,   -1,   -1,  273,  274,  275,
   -1,  181,  278,  279,  184,   -1,  186,  187,   -1,   -1,
   -1,   -1,  192,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,  256,  257,  258,   -1,  260,   -1,  219,
  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,  256,  257,   -1,  278,  260,  280,   -1,  263,
  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,
   -1,  257,   -1,   -1,  278,  261,  280,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,  257,
   -1,   -1,  278,  261,  280,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,
  278,   -1,  280,
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
"sentencias : sentencias sentencia",
"sentencias : sentencia",
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
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')' ';'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')'",
"repeat_while_statement : REPEAT WHILE '(' condicion ')'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' ')' ';'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE condicion ';'",
"salida : OUTF '(' T_CADENA ')' ';'",
"salida : OUTF '(' expresion ')' ';'",
"salida : OUTF '(' expresion ')'",
"salida : OUTF '(' T_CADENA ')'",
"salida : OUTF '(' ')' ';'",
"salida : OUTF '(' sentencia ')' ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' T_ID '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID",
"subrango : '{' T_CTE ',' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' T_CTE '}'",
"subrango : '{' T_CTE ',' '-' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' '-' T_CTE '}'",
"subrango : error",
"condicion : expresion comparador expresion",
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
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : acceso_par",
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"acceso_par : T_ID '{' T_CTE '}'",
"acceso_par : T_ID '{' '}'",
"acceso_par : T_ID '{' T_CTE",
"acceso_par : T_ID T_CTE '}'",
"goto_statement : GOTO T_ETIQUETA ';'",
"goto_statement : GOTO ';'",
"goto_statement : GOTO T_ETIQUETA",
"invocacion_funcion : T_ID '(' parametro_real ')'",
"invocacion_funcion : error",
"parametro_real : expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '+' expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '-' expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '*' expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '/' expresion_aritmetica",
"expresion_aritmetica : T_CTE",
"expresion_aritmetica : T_ID",
"expresion_aritmetica : acceso_par",
"expresion_aritmetica : unaria",
"expresion_aritmetica : expresion_aritmetica '+' operador expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '*' operador expresion_aritmetica",
"expresion_aritmetica : expresion_aritmetica '/' operador expresion_aritmetica",
"operador : '+'",
"operador : '*'",
"operador : '/'",
"operador : operador '+'",
"operador : operador '/'",
"operador : operador '*'",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '/' expresion",
"expresion : T_CTE",
"expresion : T_ID",
"expresion : acceso_par",
"expresion : invocacion_funcion",
"expresion : unaria",
"expresion : expresion '+' operador expresion",
"expresion : expresion '*' operador expresion",
"expresion : expresion '/' operador expresion",
"unaria : '-' expresion",
};

//#line 424 "gramatica.y"

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
//#line 56 "gramatica.y"
{
    System.out.println("Programa compilado correctamente");
}
break;
case 2:
//#line 59 "gramatica.y"
{ 
    System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
}
break;
case 3:
//#line 62 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}
break;
case 4:
//#line 66 "gramatica.y"
{System.out.println("Llegue a BEGIN sentencia END");}
break;
case 5:
//#line 67 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 6:
//#line 68 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan Delimitador o Bloque de Sentencia");}
break;
case 8:
//#line 71 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 19:
//#line 83 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
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
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
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
{ System.err.println("Error en línea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables");}
break;
case 26:
//#line 120 "gramatica.y"
{
        System.out.println("Declaración de función correcta");
    }
break;
case 27:
//#line 124 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }
break;
case 28:
//#line 129 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }
break;
case 29:
//#line 133 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 30:
//#line 137 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
    }
break;
case 31:
//#line 142 "gramatica.y"
{
        /* Caso correcto con un solo parámetro*/
    }
break;
case 32:
//#line 147 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 33:
//#line 150 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 34:
//#line 153 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
    }
break;
case 35:
//#line 157 "gramatica.y"
{}
break;
case 36:
//#line 158 "gramatica.y"
{}
break;
case 37:
//#line 162 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 38:
//#line 163 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 39:
//#line 165 "gramatica.y"
{
        System.out.println("Llegue a tipo");
        /* Verificar si el tipo está en la tabla de tipos definidos*/
        if (tablaTipos.containsKey(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo está definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0));
        } 
    }
break;
case 42:
//#line 179 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 43:
//#line 182 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 44:
//#line 186 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 45:
//#line 189 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 46:
//#line 193 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 47:
//#line 196 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 48:
//#line 200 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 49:
//#line 203 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 50:
//#line 206 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 51:
//#line 207 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 53:
//#line 213 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 54:
//#line 216 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 55:
//#line 219 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 56:
//#line 222 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 58:
//#line 228 "gramatica.y"
{     
        System.out.println("Llegue a salida");   
        }
break;
case 59:
//#line 231 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 60:
//#line 234 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 61:
//#line 237 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 62:
//#line 238 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");}
break;
case 63:
//#line 241 "gramatica.y"
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
case 64:
//#line 261 "gramatica.y"
{
            /* Obtener el nombre del tipo desde T_ID*/
            String nombreTipo = val_peek(4).sval; /* T_ID*/

            /* Obtener el tipo base (INTEGER o SINGLE)*/
            String tipoBase = val_peek(2).sval;
            System.out.println("tipobase"+ " "+tipoBase );
          //  tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        }
break;
case 65:
//#line 270 "gramatica.y"
{

        }
break;
case 66:
//#line 273 "gramatica.y"
{
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
break;
case 67:
//#line 276 "gramatica.y"
{
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 68:
//#line 279 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 69:
//#line 280 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 70:
//#line 282 "gramatica.y"
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
case 71:
//#line 304 "gramatica.y"
{ System.out.println("Llegue a subrango con - en el primero");}
break;
case 72:
//#line 305 "gramatica.y"
{System.out.println("Llegue a subrango con - en el segundo");}
break;
case 73:
//#line 306 "gramatica.y"
{System.out.println("Llegue a subrango con - en los dos");}
break;
case 74:
//#line 307 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 76:
//#line 312 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado derecho de la comparacion");}
break;
case 77:
//#line 313 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado izquierdo de la comparacion");}
break;
case 84:
//#line 324 "gramatica.y"
{}
break;
case 85:
//#line 325 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion");}
break;
case 86:
//#line 327 "gramatica.y"
{ 
                }
break;
case 87:
//#line 329 "gramatica.y"
{
               
                }
break;
case 88:
//#line 332 "gramatica.y"
{
                }
break;
case 89:
//#line 334 "gramatica.y"
{
                }
break;
case 90:
//#line 338 "gramatica.y"
{
      }
break;
case 91:
//#line 340 "gramatica.y"
{
      }
break;
case 92:
//#line 346 "gramatica.y"
{
                    /* Verificar si el T_CTE es '1' o '2'*/
          if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
              yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Solo se permite 1 o 2 dentro de las llaves.");
          } else {
              yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
          
      }
    }
break;
case 93:
//#line 355 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta constante dentro de las llaves."); 
      }
break;
case 94:
//#line 358 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta cierre de llave en acceso a parámetros."); 
      }
break;
case 95:
//#line 361 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta apertura de llave en acceso a parámetros."); 
    }
break;
case 97:
//#line 367 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 98:
//#line 368 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 99:
//#line 371 "gramatica.y"
{
      }
break;
case 100:
//#line 373 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida");
        }
break;
case 110:
//#line 388 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 111:
//#line 389 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 112:
//#line 390 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + "Error: Dos o mas operadores juntos");}
break;
case 119:
//#line 394 "gramatica.y"
{
        }
break;
case 120:
//#line 396 "gramatica.y"
{
        }
break;
case 121:
//#line 398 "gramatica.y"
{
        }
break;
case 122:
//#line 400 "gramatica.y"
{
        }
break;
case 123:
//#line 402 "gramatica.y"
{
        }
break;
case 124:
//#line 404 "gramatica.y"
{
        }
break;
case 125:
//#line 406 "gramatica.y"
{
        }
break;
case 126:
//#line 408 "gramatica.y"
{
        }
break;
case 127:
//#line 410 "gramatica.y"
{ /* Se añade la regla para operadores unarios*/
        }
break;
case 128:
//#line 412 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 129:
//#line 413 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 130:
//#line 414 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 131:
//#line 418 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
    yyval.dval = -val_peek(0).dval;
}
break;
//#line 1444 "Parser.java"
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
