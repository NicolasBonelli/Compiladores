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



//#line 56 "Parser.java"




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
    0,    0,    1,    2,    2,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    4,    9,    9,    9,    9,
    9,   15,   15,   14,   14,   13,   13,   13,   13,    6,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    7,
    7,    7,    7,    8,    8,    8,   11,   11,   11,   11,
   11,   17,   17,   17,   17,   17,   16,   16,   16,   18,
   18,   18,   18,   18,   18,    5,   19,   19,   19,   19,
   20,   20,   20,   20,   21,   21,   21,   21,   10,   10,
   22,   22,   23,   24,   24,   24,   24,   24,   24,   24,
   24,   24,   24,   24,   26,   26,   26,   26,   26,   26,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   25,
};
final static short yylen[] = {                            2,
    2,    1,    3,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    5,    3,    8,    7,    7,    7,
    7,    1,    1,    3,    1,    1,    1,    1,    1,    8,
   10,    7,    9,    7,    9,    7,    9,    7,    8,    7,
    6,    5,    6,    5,    5,    4,    6,    7,    7,    5,
    7,    5,    6,    6,    7,    1,    3,    2,    2,    1,
    1,    1,    1,    1,    1,    4,    1,    3,    3,    1,
    1,    3,    2,    2,    4,    3,    3,    3,    3,    2,
    4,    1,    1,    3,    3,    3,    3,    1,    1,    1,
    1,    4,    4,    4,    1,    1,    1,    2,    2,    2,
    3,    3,    3,    3,    1,    1,    1,    1,    1,    4,
    4,    4,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    1,   29,    0,    0,    0,    0,    0,
    0,   27,   26,    0,   14,    0,    5,    6,    7,    8,
    9,   10,   11,   12,   13,    0,    0,   70,    0,    0,
    0,    0,    0,    0,   22,   23,    0,    0,   80,    0,
    0,    3,    4,    0,    0,    0,    0,    0,   82,   60,
   61,   62,    0,  105,    0,    0,   64,   65,   63,    0,
    0,    0,  107,  108,  109,    0,    0,    0,    0,    0,
    0,    0,   79,   78,    0,   76,    0,    0,    0,   16,
    0,    0,    0,    0,    0,   69,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   28,    0,    0,    0,    0,   75,    0,    0,    0,
   24,    0,   66,    0,    0,   88,   90,    0,    0,   91,
    0,   95,   96,   97,    0,    0,    0,  103,    0,  104,
    0,    0,    0,    0,   44,   45,    0,    0,    0,   56,
    0,    0,   15,   42,    0,    0,    0,    0,    0,    0,
   81,    0,    0,    0,    0,    0,    0,   98,  100,   99,
    0,  111,  112,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   47,   43,    0,    0,    0,    0,    0,
    0,    0,    0,   86,    0,   87,    0,    0,   34,    0,
   38,    0,    0,    0,   36,   48,   49,   51,    0,    0,
   40,    0,   19,   21,   20,    0,   93,   94,    0,   39,
    0,   30,    0,    0,    0,    0,   17,   35,    0,   37,
   52,    0,    0,    0,   31,   54,   53,    0,   55,
};
final static short yydgoto[] = {                          2,
   35,   16,   36,   18,   19,   20,   21,   22,   23,   24,
   25,   60,   26,   46,   37,   61,  142,   62,   27,   84,
   63,   64,  118,  119,   65,  126,
};
final static short yysindex[] = {                      -267,
 -248,    0,  348,    0,    0,  -21,   -3, -202,   11,  166,
  -54,    0,    0, -117,    0,  311,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -220,  -43,    0,  -38,  -24,
   15, -243,  -11,   29,    0,    0, -225,  -10,    0,  -48,
 -116,    0,    0,  -32,   57,   65,  -18, -179,    0,    0,
    0,    0,  -40,    0,  -11, -143,    0,    0,    0,   83,
   78,  -11,    0,    0,    0,   79,  265,  -74, -125,  319,
   76,   89,    0,    0,   49,    0,  140, -125, -125,    0,
  -84,  -11,  291,  107, -117,    0,  -20,   23,  330,  -27,
  -11,  -27,  -27,  -11,  293,  291,  136,  149,  153,  157,
  161,    0, -106,  152,  183,  -31,    0, -125,  -51,  -47,
    0,  291,    0,  -11, -117,    0,    0,  215,  405,    0,
  -91,    0,    0,    0,   23,   -7,   23,    0,   -7,    0,
   -7,  291,  -50,  -90,    0,    0,  -15,  -14,   -9,    0,
  -17,  198,    0,    0,  206,  229,  -28,  233,  235,  291,
    0,   -1,  -20,   -1,   -1,  330,  216,    0,    0,    0,
   23,    0,    0,   18,  222,  -63,  330,  232,  236,  239,
  240,  257,   26,    0,    0,  250,  272, -248, -248, -248,
   80,    5,   80,    0,    5,    0,    5,   55,    0,  259,
    0,  330,  263,   61,    0,    0,    0,    0,  -13,  283,
    0, -248,    0,    0,    0,   80,    0,    0,  269,    0,
   67,    0,  271,  212,   66,   -6,    0,    0,  285,    0,
    0,  227,  228,   86,    0,    0,    0,  245,    0,
};
final static short yyrindex[] = {                         0,
  359,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   24,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  108,    0,    0,    0,    0,    0,
    0,    0,   19,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   12,    0,    0,    0,    0,    0,
    0,    0,  120,    0,  -42,    0,    0,   41,    0,    0,
    0,    0,    0,  331,    0,  344,    0,  127,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  126,    0,  131,  326,    0,    0,    0,  346,    0,
    0,    0,    0,    0,   46,    0,   51,    0,    0,    0,
    0,  351,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  147,    0,    0,    0,    0,    0,    0,    0,  133,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   73,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  214,    0,    0,    0,    0,
  336,    0,  408,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  234,    0,    0,    0,    0,    0,    0,    0,
    0,  254,    0,    0,    0,  414,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  274,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    3,    0,  170,    0,    0,    0,    0,    0,    0,    0,
    0,  249,   70,    0,    9,   17,    0,  340,    0,    0,
  287,    0,    0,    2,    6,   48,
};
final static int YYTABLESIZE=628;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         87,
   48,   68,   56,    4,   39,   41,   55,   78,   76,  145,
    1,    3,  178,   55,  123,  122,  141,   55,   29,  124,
   55,   57,   59,   58,   55,   82,   55,  173,   57,   59,
   58,  215,   69,   55,  159,  158,   30,   55,  224,  160,
  123,  122,   72,   55,   44,  124,  159,  158,   73,   55,
   33,  160,   77,   77,   77,   77,   77,   45,   77,  106,
  106,  106,  106,  106,   92,  106,   31,   67,   71,   93,
   77,   77,   77,   77,   68,   32,   74,  106,  106,  106,
  106,  113,   41,  113,  113,  113,  101,  105,  101,  101,
  101,  102,  120,  102,  102,  102,   79,  121,   85,  113,
  113,  113,  113,  134,  101,  101,  101,  101,   81,  102,
  102,  102,  102,  110,   89,  110,  110,  110,   95,   97,
   55,  154,  146,   80,   92,   90,  155,   91,  106,   93,
    5,  110,  110,  110,  110,   57,   59,   58,  103,  129,
  131,  166,   57,   59,   58,   12,   13,  109,  110,  140,
  114,   25,  102,  181,  183,  184,  186,  120,  120,  120,
  120,   40,   75,   71,  188,  113,   25,  156,  167,   74,
  157,  168,   17,  107,   73,  194,   72,  147,   71,  108,
  203,  204,  205,  206,   74,   43,  207,  120,  208,   73,
  120,   72,  120,  111,  135,  192,   99,  100,  193,  182,
  211,  185,  187,  101,  217,    5,    6,  136,  164,    3,
  143,  165,    7,    8,  137,    9,   10,   49,  138,   11,
   12,   13,  139,  144,   49,   38,  148,   14,   49,   15,
  149,   49,   47,   68,   50,   51,   52,   49,   40,   53,
   54,   50,   51,   52,   49,   77,   53,   54,   49,  177,
   53,   54,   66,   53,   54,  151,  174,  115,  116,   53,
   54,  172,  169,  170,  175,  214,   53,   54,  171,  176,
   53,   54,  223,  179,  189,  180,  115,  116,   67,  190,
  191,   70,  115,  116,   77,   77,   77,   77,   28,   28,
  195,  106,  106,  106,  196,   83,   28,  197,  198,   67,
  199,   28,   28,   88,  200,   98,   92,   90,  201,   91,
   96,   93,  202,  113,  113,  113,  209,  210,  101,  101,
  101,  212,  213,  102,  102,  102,  216,  218,  219,  220,
  112,   49,   92,   90,   86,   91,  221,   93,  125,  127,
  128,  130,  132,  225,  222,  110,  110,  110,   50,   51,
   52,  226,  227,   53,   54,   50,   51,   52,    2,  104,
   92,   90,  150,   91,  228,   93,   89,   89,   89,  229,
   89,   58,   89,  117,  161,   28,   84,  162,   84,  163,
   84,   28,   46,   46,   59,   46,   83,   46,   46,   46,
   46,   57,   46,   46,   46,    0,   46,   46,   46,   94,
    0,    0,   50,   50,   46,   50,   46,   50,   50,   50,
   50,    0,   50,   50,   50,    0,   50,   50,   50,   28,
    0,    5,    6,    0,   50,    3,   50,    0,    7,    8,
    0,    9,   10,   34,    0,   11,   12,   13,  117,  117,
  117,  117,   28,   14,    0,   15,  154,  152,   85,  153,
   85,  155,   85,   28,   92,    0,   92,    0,   92,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  117,   41,
   41,  117,   41,  117,   41,   41,   41,   41,   28,   41,
   41,   41,    0,   41,   41,   41,    0,    0,    0,   32,
   32,   41,   32,   41,   32,   32,   32,   32,    0,   32,
   32,   32,    0,   32,   32,   32,    0,    0,    0,   18,
   18,   32,   18,   32,   18,   18,   18,   18,    0,   18,
   18,   18,    0,   18,   18,   18,    0,    0,    0,   33,
   33,   18,   33,   18,   33,   33,   33,   33,    0,   33,
   33,   33,    0,   33,   33,   33,    0,    0,    5,    6,
  133,   33,    3,   33,    0,    7,    8,    0,    9,   10,
    0,    0,   11,   12,   13,    0,    5,    6,    0,    0,
   14,   42,   15,    7,    8,    0,    9,   10,    0,    0,
   11,   12,   13,    0,    0,    5,    6,    0,   14,    3,
   15,    0,    7,    8,    0,    9,   10,    0,    0,   11,
   12,   13,    0,    5,    6,    0,    0,   14,    0,   15,
    7,    8,    0,    9,   10,    0,    0,   11,   12,   13,
    0,    0,    0,    0,    0,   14,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
   44,   44,   41,    1,   59,  123,   45,   40,  125,   41,
  278,  260,   41,   45,   42,   43,  123,   45,   40,   47,
   45,   60,   61,   62,   45,   44,   45,   45,   60,   61,
   62,   45,  276,   45,   42,   43,   40,   45,   45,   47,
   42,   43,  268,   45,  265,   47,   42,   43,   59,   45,
   40,   47,   41,   42,   43,   44,   45,  278,   47,   41,
   42,   43,   44,   45,   42,   47,  269,   44,   40,   47,
   59,   60,   61,   62,   60,  278,  125,   59,   60,   61,
   62,   41,  123,   43,   44,   45,   41,   71,   43,   44,
   45,   41,   87,   43,   44,   45,   40,   89,  278,   59,
   60,   61,   62,   95,   59,   60,   61,   62,   44,   59,
   60,   61,   62,   41,  258,   43,   44,   45,   41,   41,
   45,   42,  106,   59,   42,   43,   47,   45,   40,   47,
  256,   59,   60,   61,   62,   60,   61,   62,   69,   92,
   93,  133,   60,   61,   62,  271,  272,   78,   79,  256,
   44,   44,  278,  152,  153,  154,  155,  152,  153,  154,
  155,  279,  279,   44,  156,   59,   59,  259,  259,   44,
  262,  262,    3,  125,   44,  167,   44,  108,   59,   40,
  178,  179,  180,  182,   59,   16,  185,  182,  187,   59,
  185,   59,  187,  278,   59,  259,  271,  272,  262,  152,
  192,  154,  155,  278,  202,  256,  257,   59,  259,  260,
   59,  262,  263,  264,   62,  266,  267,  256,   62,  270,
  271,  272,   62,   41,  256,  280,  278,  278,  256,  280,
  278,  256,  276,  276,  273,  274,  275,  256,  279,  278,
  279,  273,  274,  275,  256,  278,  278,  279,  256,  278,
  278,  279,  277,  278,  279,   41,   59,  278,  279,  278,
  279,  279,  278,  278,   59,  279,  278,  279,  278,   41,
  278,  279,  279,   41,   59,   41,  278,  279,   30,  262,
   59,   33,  278,  279,  273,  274,  275,  276,  265,    3,
   59,  273,  274,  275,   59,   47,   10,   59,   59,  276,
   44,  278,   16,   55,  279,   41,   42,   43,   59,   45,
   62,   47,   41,  273,  274,  275,  262,   59,  273,  274,
  275,   59,  262,  273,  274,  275,   44,   59,  262,   59,
   82,  256,   42,   43,   48,   45,  125,   47,   90,   91,
   92,   93,   94,   59,  279,  273,  274,  275,  273,  274,
  275,  125,  125,  278,  279,  273,  274,  275,    0,   41,
   42,   43,  114,   45,  279,   47,   41,   42,   43,  125,
   45,   41,   47,   87,  126,   89,   41,  129,   43,  131,
   45,   95,  256,  257,   41,  259,   41,  261,  262,  263,
  264,   41,  266,  267,  268,   -1,  270,  271,  272,   60,
   -1,   -1,  256,  257,  278,  259,  280,  261,  262,  263,
  264,   -1,  266,  267,  268,   -1,  270,  271,  272,  133,
   -1,  256,  257,   -1,  278,  260,  280,   -1,  263,  264,
   -1,  266,  267,  268,   -1,  270,  271,  272,  152,  153,
  154,  155,  156,  278,   -1,  280,   42,   43,   41,   45,
   43,   47,   45,  167,   41,   -1,   43,   -1,   45,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  182,  256,
  257,  185,  259,  187,  261,  262,  263,  264,  192,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,  256,
  257,  278,  259,  280,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,  256,
  257,  278,  259,  280,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,  256,
  257,  278,  259,  280,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,  256,  257,
  258,  278,  260,  280,   -1,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,  256,  257,   -1,   -1,
  278,  261,  280,  263,  264,   -1,  266,  267,   -1,   -1,
  270,  271,  272,   -1,   -1,  256,  257,   -1,  278,  260,
  280,   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,
  271,  272,   -1,  256,  257,   -1,   -1,  278,   -1,  280,
  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,   -1,   -1,   -1,  278,   -1,  280,
};
}
final static short YYFINAL=2;
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
"bloque_sentencias : BEGIN sentencias END",
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
"declaracion : tipo lista_var ';'",
"declaracion_funcion : tipo FUN T_ID '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN T_ID '(' tipo T_ID ')'",
"declaracion_funcion : tipo FUN T_ID '(' tipo ')' bloque_sentencias",
"declaracion_funcion : tipo T_ID '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN '(' tipo T_ID ')' bloque_sentencias",
"repeat_sentencia : bloque_sentencias",
"repeat_sentencia : sentencia",
"lista_var : lista_var ',' T_ID",
"lista_var : T_ID",
"tipo : DOUBLE",
"tipo : LONGINT",
"tipo : T_ID",
"tipo : error",
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
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')' ';'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')'",
"repeat_while_statement : REPEAT WHILE '(' condicion ')'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' ')' ';'",
"salida : OUTF '(' T_CADENA ')' ';'",
"salida : OUTF '(' expresion ')' ';'",
"salida : OUTF '(' expresion ')'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' T_ID '>' T_ID ';'",
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
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : acceso_par",
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"expresion_list : expresion_list ','",
"expresion_list : ',' expresion",
"acceso_par : T_ID '{' T_CTE '}'",
"acceso_par : T_ID '{' '}'",
"acceso_par : T_ID '{' T_CTE",
"acceso_par : T_ID T_CTE '}'",
"goto_statement : GOTO T_ETIQUETA ';'",
"goto_statement : GOTO ';'",
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

//#line 349 "gramatica.y"

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
//#line 626 "Parser.java"
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
//#line 50 "gramatica.y"
{
    System.out.println("Programa compilado correctamente");
}
break;
case 2:
//#line 53 "gramatica.y"
{ 
    System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
}
break;
case 3:
//#line 59 "gramatica.y"
{System.out.println("Llegue a BEGIN sentencia END");}
break;
case 5:
//#line 62 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 15:
//#line 74 "gramatica.y"
{System.out.println("Llegue a sentencia");}
break;
case 16:
//#line 77 "gramatica.y"
{ 
	System.out.println("Llegue a declaracion");
    List<ParserVal> variables = new ArrayList<ParserVal>();
    variables.add(val_peek(1)); //Asume que lista_var devuelve una lista de variables

    for (ParserVal variable : variables) {
        
        if (!st.hasKey(variable.toString())) {
            System.out.println("Aclaracion, la tabla de símbolos no contenía la variable: " + variable.toString());
        } else {
            
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
case 17:
//#line 98 "gramatica.y"
{
        System.out.println("Declaración de función correcta");
      }
break;
case 18:
//#line 101 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración de función.");
      }
break;
case 19:
//#line 104 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el identificador del argumento de la función.");
      }
break;
case 20:
//#line 107 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN");
        
      }
break;
case 21:
//#line 111 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el identificador de la funcion");
      }
break;
case 22:
//#line 117 "gramatica.y"
{}
break;
case 23:
//#line 118 "gramatica.y"
{}
break;
case 24:
//#line 121 "gramatica.y"
{ 
   
}
break;
case 25:
//#line 124 "gramatica.y"
{ 
    
}
break;
case 26:
//#line 128 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 27:
//#line 129 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 28:
//#line 131 "gramatica.y"
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
case 29:
//#line 140 "gramatica.y"
{
    System.err.println("Error en línea: " + Lexer.nmrLinea + " - Tipo no válido o no declarado.");
    }
break;
case 32:
//#line 148 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma al final de la sentencia IF.");
            }
break;
case 33:
//#line 151 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma al final de la sentencia IF.");
            }
break;
case 34:
//#line 155 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 35:
//#line 158 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 36:
//#line 162 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 37:
//#line 165 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 38:
//#line 169 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 39:
//#line 172 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 41:
//#line 179 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma al final de la sentencia WHILE.");
    }
break;
case 42:
//#line 182 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 43:
//#line 185 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 45:
//#line 194 "gramatica.y"
{     
        System.out.println("Llegue a salida");   
        }
break;
case 46:
//#line 197 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma en la salida.");
        }
break;
case 47:
//#line 201 "gramatica.y"
{
	System.out.println("Llegue a sentencia_declarativa_tipos");
    
    // Obtener el nombre del tipo desde T_ID
    String nombreTipo = val_peek(4).sval; /* T_ID*/

    // Obtener el tipo base (INTEGER o SINGLE)
    String tipoBase = val_peek(2).sval;
    System.out.println("tipobase"+ " "+tipoBase );
    /* tipo base (INTEGER o SINGLE)*/
    // Limite inferior del subrango (asegúrate de que sea numérico)
    double limiteInferior = val_peek(4).dval; /* Limite inferior */
    System.out.println("liminf"+ " "+limiteInferior );
    // Limite superior del subrango (asegúrate de que sea numérico)
    double limiteSuperior =  val_peek(5).dval; /* Limite superior */
    System.out.println("limsup"+ " "+limiteSuperior );
    // Almacenar en la tabla de tipos
    tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
    System.out.println("ENTRE A DEFINIR NUEVO TIPO");
}
break;
case 50:
//#line 214 "gramatica.y"
{
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma al final de la declaración de tipo.");
        }
break;
case 51:
//#line 217 "gramatica.y"
{
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 52:
//#line 222 "gramatica.y"
{
	System.out.println("Llegue a subrango");

	// Verificar que los valores en la pila son correctos antes de convertirlos
	String limiteInferiorStr = val_peek(3).sval; // T_CTE (límites inferiores)
	String limiteSuperiorStr = val_peek(1).sval; // T_CTE (límites superiores)

	System.out.println("VAL3 (Limite Inferior): " + limiteInferiorStr);
	System.out.println("VAL1 (Limite Superior): " + limiteSuperiorStr);

	try {
	    // Convertir los valores de límites de cadena a double
	    double limiteInferior = Double.parseDouble(limiteInferiorStr);
	    double limiteSuperior = Double.parseDouble(limiteSuperiorStr);

	    // Crear el objeto Subrango y asignarlo
	    yylval.obj = new Subrango(limiteInferior, limiteSuperior);
	    System.out.println("Subrango creado correctamente con límites: " + limiteInferior + " - " + limiteSuperior);
	} catch (NumberFormatException e) {
	    System.err.println("Error al convertir los límites del subrango a double: " + e.getMessage());
	}
}
break;
case 56:
//#line 230 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 58:
//#line 235 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado derecho de la comparacion");}
break;
case 59:
//#line 236 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado izquierdo de la comparacion");}
break;
case 66:
//#line 247 "gramatica.y"
{
          
}
break;
case 67:
//#line 251 "gramatica.y"
{ 
                }
break;
case 68:
//#line 253 "gramatica.y"
{
               
                }
break;
case 69:
//#line 256 "gramatica.y"
{
                }
break;
case 70:
//#line 258 "gramatica.y"
{
                }
break;
case 71:
//#line 262 "gramatica.y"
{
      }
break;
case 72:
//#line 264 "gramatica.y"
{
      }
break;
case 73:
//#line 266 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta expresión después de la coma."); 
      }
break;
case 74:
//#line 269 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta expresión antes de la coma."); 
      }
break;
case 75:
//#line 275 "gramatica.y"
{
          /* Verificar si el T_CTE es '1' o '2'*/
          if (!(val_peek(1).equals("1") || val_peek(1).equals("2"))) {
              yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Solo se permite 1 o 2 dentro de las llaves.");
          } else {
              yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
          }
      }
break;
case 76:
//#line 283 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta constante dentro de las llaves."); 
      }
break;
case 77:
//#line 286 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta cierre de llave en acceso a parámetros."); 
      }
break;
case 78:
//#line 289 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta apertura de llave en acceso a parámetros."); 
    }
break;
case 80:
//#line 295 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 81:
//#line 298 "gramatica.y"
{
      }
break;
case 82:
//#line 300 "gramatica.y"
{ System.err.println("Error en línea: " + Lexer.nmrLinea + " Invocacion a funcion no valida "); }
break;
case 92:
//#line 313 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 93:
//#line 314 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 94:
//#line 315 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + "Error: Dos o mas operadores juntos");}
break;
case 101:
//#line 319 "gramatica.y"
{
        }
break;
case 102:
//#line 321 "gramatica.y"
{
        }
break;
case 103:
//#line 323 "gramatica.y"
{
        }
break;
case 104:
//#line 325 "gramatica.y"
{
        }
break;
case 105:
//#line 327 "gramatica.y"
{
        }
break;
case 106:
//#line 329 "gramatica.y"
{
        }
break;
case 107:
//#line 331 "gramatica.y"
{
        }
break;
case 108:
//#line 333 "gramatica.y"
{
        }
break;
case 109:
//#line 335 "gramatica.y"
{ /* Se añade la regla para operadores unarios*/
        }
break;
case 110:
//#line 337 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 111:
//#line 338 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 112:
//#line 339 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 113:
//#line 343 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
           yyval.dval = -val_peek(0).dval;
       }
break;
//#line 1190 "Parser.java"
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
      if (yychar == 0) { 
    	 //Good exit (if lex returns 0 ;-)
    	 this.imprimirTablaTipos();
         break;
      }//quit the loop--all DONE
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
public void imprimirTablaTipos() {
    // Iterar sobre cada entrada del mapa
    for (Map.Entry<String, TipoSubrango> entry : tablaTipos.entrySet()) {
        // Obtener la clave (nombre del tipo) y el valor (TipoSubrango)
        String nombreTipo = entry.getKey();
        TipoSubrango tipoSubrango = entry.getValue();

        // Imprimir los detalles del tipo
        System.out.println("Nombre del tipo: " + nombreTipo);
        System.out.println("Tipo base: " + tipoSubrango.tipoBase);
        System.out.println("Limite inferior: " + tipoSubrango.limiteInferior);
        System.out.println("Limite superior: " + tipoSubrango.limiteSuperior);
        System.out.println("-----------------------------------");
    }
}


}
//################### END OF CLASS ##############################
