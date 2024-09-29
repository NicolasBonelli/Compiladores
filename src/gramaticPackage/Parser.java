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
   23,   23,   22,   22,   22,   22,   22,   24,   24,   24,
   10,   10,   10,   10,   25,   25,   26,   27,   27,   27,
   27,   27,   27,   27,   27,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   28,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    5,    0,    5,
    3,    3,    3,    1,    2,    7,    7,    7,    7,    7,
    2,    3,    3,    0,    1,    1,    1,    1,    1,    8,
   10,    7,    9,    7,    9,    7,    9,    7,    8,    6,
    8,    8,   10,    7,    6,    5,    6,    5,    7,    5,
    5,    4,    4,    4,    5,    6,    7,    7,    6,    5,
    5,    5,    7,    6,    6,    6,    6,    6,    6,    5,
    5,    5,    5,    6,    6,    7,    2,    1,    3,    3,
    2,    2,    1,    1,    1,    1,    1,    1,    4,    4,
    1,    3,    3,    3,    1,    1,    1,    4,    4,    4,
    3,    2,    2,    2,    4,    4,    1,    3,    3,    3,
    3,    1,    1,    1,    1,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    3,  105,    0,    5,    0,    0,
    0,    0,    0,   38,   37,    0,   17,    0,    7,    9,
   10,   11,   12,   13,   14,   15,   16,    0,    0,  107,
    1,  135,   93,   94,   95,    0,  130,    0,    0,   97,
   98,   96,    0,    0,    0,  132,  133,  134,    0,    0,
    0,    0,    0,    0,    0,    0,   35,   36,    0,  114,
    0,  112,    0,    4,    8,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   39,    0,    0,    0,    0,    0,    0,    0,
    0,  111,    0,    0,    0,    0,    0,    0,   22,   25,
   21,    0,    0,    0,    0,  104,    0,    0,  122,  124,
    0,    0,  125,    0,    0,    0,    0,    0,  128,  129,
    0,    0,    0,   64,    0,    0,    0,    0,    0,    0,
    0,    0,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  110,  108,  109,    0,    0,    0,
   23,   99,  100,    0,  116,  115,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   60,   65,   61,   71,   70,
    0,    0,    0,    0,    0,    0,   87,   80,   82,    0,
   81,    0,    0,   18,   20,   56,    0,    0,    0,   58,
    0,    0,    0,    0,    0,    0,    0,    0,  120,  121,
    0,    0,    0,    0,    0,    0,    0,    0,   50,    0,
   78,    0,   79,    0,   69,    0,    0,   66,   76,   77,
    0,   57,    0,   31,    0,    0,    0,    0,    0,    0,
    0,    0,   44,    0,   48,    0,    0,    0,    0,   46,
    0,   67,   68,   73,    0,    0,    0,   59,   54,   28,
   26,    0,   32,   27,   33,   30,   29,    0,   49,   52,
    0,   40,    0,   51,   83,    0,    0,    0,   45,    0,
    0,   47,   85,   84,    0,   53,   41,   86,
};
final static short yydgoto[] = {                          4,
   57,   18,   58,   20,   21,   22,   23,   24,   25,   26,
   27,   43,  195,   28,   68,  202,  203,   59,   44,  145,
   45,   29,  114,   46,   47,  121,  122,   48,
};
final static short yysindex[] = {                      -200,
    0,  597, -201,    0,    0,    0,  418,    0,  -14,  -49,
    5,  560,  -56,    0,    0,  -62,    0,  615,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -211,  -15,    0,
    0,    0,    0,    0,    0,   -7,    0,    4,  245,    0,
    0,    0,  540, -171,    4,    0,    0,    0,  190,  -55,
 -188, -206,  -70,    4,    0,   74,    0,    0, -180,    0,
   35,    0,  -18,    0,    0,  -38,   79,    6,    4, -158,
  170,   44, -131,   88,    4,    4,    4,    4,    4,    4,
  634,   89,    0,   94,   -7,   78,  108,   54,    0, -127,
 -114,  -50,    0, -104, -109,  109,  115,  113,  519,  144,
  426,    0,   61,   73,   82,  163, -188, -188,    0,    0,
    0,  -68,   89,   45,  -62,    0,  167,  -62,    0,    0,
  171,  103,    0,  634,  579,   89,   44,   44,    0,    0,
   89, -156,  159,    0,  160,  164,  173,  174,  149,  168,
  279,   67,    0,   13,  287,  -51,  288,   83,   87,  298,
  325,  519,  450,  308,    0,    0,    0, -188,   99,  107,
    0,    0,    0,    4,    0,    0,   47,   47,   47,   47,
 -152,  395, -141,  634,  328,    0,    0,    0,    0,    0,
  -53,  -52,  119,  346,  362,  128,    0,    0,    0,  358,
    0,  366,  368,    0,    0,    0,  380,  378,  400,    0,
  -26,  151,  153,  404,  410,   89,   70,   70,    0,    0,
  634,  396,  197,  405, -151,  634,  406,  210,    0,  414,
    0,  415,    0,  416,    0,  -32,  432,    0,    0,    0,
  422,    0,  423,    0, -201, -201, -188, -201, -188, -201,
 -201,  215,    0,  424,    0,  425,  634,  430,  223,    0,
  431,    0,    0,    0,  367,  214,   -3,    0,    0,    0,
    0,  216,    0,    0,    0,    0,    0,  444,    0,    0,
 -205,    0,  445,    0,    0,  381,  382,  226,    0,  449,
  454,    0,    0,    0,  384,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  514,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  293,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  484,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  337,    0,    0,    0,    0,    0,
  -13,    0,    0,    0,    0,    0,  342,    0,    0,    0,
    0,  489,    0,    0,    0,    0,    0,    0,    0,  -24,
    0,   22,  -20,    0,  455,    0,    0,    0,   -4,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   65,    0,   41,    0,    0,  140,    0,    0,
    0,  474,    0,    0,    0,   23,  494,  516,    0,    0,
   39,    0,    7,    0,    0,   32,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   52,
    0,    0,    0,    0,    0,    0,    0,  155,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   72,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   71,  116,  133,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   92,
    0,  112,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  132,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  152,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  172,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   28,    0,    0,    0,    0,    0,    0,    0,
    0,  570,    0,  -33,    0, -203,    0,  -72,   27,  -75,
  473,    0,    0,   -2,    0,    0,   21,  -27,
};
final static int YYTABLESIZE=914;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
    5,  107,   62,   31,   92,  221,  223,  189,  132,   30,
   53,  142,  256,  144,  235,   30,   91,   94,  144,  147,
  135,  135,  135,  105,  135,   49,  135,  113,   70,   19,
  104,  105,   71,  263,   91,  265,  132,  132,  132,  107,
  132,  278,  132,  123,   54,   65,   89,   63,   38,  112,
  280,  171,  173,   66,    1,    1,  281,  186,    2,    2,
   63,  146,   92,   90,  111,   74,   67,  116,  120,   95,
  190,  144,   62,  159,  160,  100,   87,    3,   30,   89,
   92,   90,   14,   15,  103,   78,   81,  101,  164,   93,
   79,   38,   19,  102,  136,   78,   76,   89,   77,  215,
   79,  218,  174,  163,  246,  175,  211,  247,  101,  212,
  248,  169,   72,   99,  102,   63,  170,  216,  108,  115,
  217,   30,   30,  101,  201,  151,  124,  154,  125,  102,
   78,   76,   74,   77,  133,   79,  134,  187,  242,  123,
  123,  123,  123,  249,  169,  167,  143,  168,  135,  170,
  137,  143,   75,  150,   78,   76,  118,   77,  118,   79,
  118,   14,   15,  138,  120,  120,  120,  120,   93,   30,
  148,   30,   55,  119,  271,  119,  149,  119,  197,  199,
  123,  123,  123,  152,  123,  155,  123,  207,  208,  209,
  210,  236,   42,  238,  237,   34,  239,  156,   34,   60,
   96,   97,  158,  262,  143,  262,  157,  165,   30,  161,
  181,  166,   43,   30,   38,   90,   91,  176,  177,   50,
  139,  140,  178,   61,  220,  222,   51,  141,   52,  182,
   86,  179,  180,   91,   38,  260,  261,  103,  264,  106,
  266,  267,  113,  113,   30,  113,  255,  113,  113,  113,
  113,  234,  113,  113,  113,  105,  113,  113,  113,   32,
   69,  109,   63,   63,  113,   63,  113,   63,   63,   63,
   63,  107,   63,   63,   63,  277,   63,   63,   63,   92,
   90,   36,   37,  110,   63,   73,   63,   62,   62,   38,
   62,  185,   62,   62,   62,   62,   89,   62,   62,   62,
  162,   62,   62,   62,   40,   42,   41,   19,   19,   62,
   19,   62,   19,   19,   19,   19,  103,   19,   19,   19,
  101,   19,   19,   19,  118,  119,  102,   72,   72,   19,
   72,   19,   72,   72,   72,   72,  106,   72,   72,   72,
  183,   72,   72,   72,  184,  188,  191,   74,   74,   72,
   74,   72,   74,   74,   74,   74,  194,   74,   74,   74,
  192,   74,   74,   74,  193,  196,  200,   75,   75,   74,
   75,   74,   75,   75,   75,   75,  204,   75,   75,   75,
  105,   75,   75,   75,  205,   24,  219,   55,   55,   75,
   55,   75,   55,   55,   55,   55,  224,   55,   55,   55,
   24,   55,   55,   55,  225,  226,  227,   42,   42,   55,
   42,   55,   42,   42,   42,   42,  228,   42,   42,   42,
  231,   42,   42,   42,  229,  117,  230,   43,   43,   42,
   43,   42,   43,   43,   43,   43,  232,   43,   43,   43,
  233,   43,   43,   43,  240,   83,    7,  118,  119,   43,
  241,   43,    9,   10,  243,   11,   12,   39,  244,   13,
   14,   15,   38,  245,  250,  153,   84,   85,   37,   17,
   38,  251,  252,  253,  254,  257,  268,   40,   42,   41,
  258,  259,  269,  270,  273,   40,   42,   41,  272,  274,
  198,  275,  276,  234,   38,  131,  131,  131,  106,  131,
   32,  131,  279,  282,  285,  283,  284,  286,  288,   40,
   42,   41,  287,    2,  117,   80,    0,   33,   34,   35,
    0,    0,   36,   37,  131,  131,  131,  131,  131,  136,
  131,  136,  136,  136,  126,    0,  126,  126,  126,    0,
    0,    0,  131,  131,  131,  131,    0,  136,  136,  136,
  136,    0,  126,  126,  126,  126,  127,   39,  127,  127,
  127,    0,    0,   38,    0,    0,    0,    0,  106,    0,
   39,    0,    0,    0,  127,  127,  127,  127,   40,   42,
   41,   78,   76,    0,   77,    0,   79,    0,    0,    0,
    0,    0,    6,    0,    0,    6,    0,   24,    6,   40,
   42,   41,    0,    0,    6,    0,    0,   72,    0,    0,
    0,    0,  105,    0,   82,    0,    0,    0,   88,   24,
    0,    0,    0,   98,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  113,    0,
    0,    0,    0,    0,  126,  127,  128,  129,  130,  131,
   55,    7,    0,  213,    2,    0,  214,    9,   10,    0,
   11,   12,    0,    0,   13,   14,   15,    0,    0,    0,
    0,    0,   16,   32,   17,    0,    0,    0,    0,    0,
    0,   32,    0,    0,    0,    0,    0,    0,    0,    0,
   33,   34,   35,    0,    0,   36,   37,    0,   33,   34,
   35,    0,    0,   36,   37,   32,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   39,
    0,    0,   33,   34,   35,    0,    0,   36,   37,    0,
  106,    0,   39,  206,    0,    0,    0,    0,    0,  131,
    0,  131,    0,    0,  136,    0,  136,    0,    0,  126,
    0,  126,    0,    0,    0,    0,  131,  131,  131,    0,
    0,  136,  136,  136,    0,    0,  126,  126,  126,    0,
    0,  127,    0,  127,   32,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  127,  127,
  127,   33,   34,   35,    0,   75,   36,   37,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   33,   34,   35,   55,    7,    0,    0,    2,
    0,    0,    9,   10,    0,   11,   12,   56,    0,   13,
   14,   15,    0,    0,   55,    7,  172,   16,    2,   17,
    0,    9,   10,    0,   11,   12,    0,    0,   13,   14,
   15,    0,    6,    7,    0,    0,   16,    8,   17,    9,
   10,    0,   11,   12,    0,    0,   13,   14,   15,    0,
    6,    7,    0,    0,   16,   64,   17,    9,   10,    0,
   11,   12,    0,    0,   13,   14,   15,    0,    0,   55,
    7,    0,   16,    2,   17,    0,    9,   10,    0,   11,
   12,    0,    0,   13,   14,   15,    0,    0,    0,    0,
    0,   16,    0,   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    0,   40,   59,    3,   60,   59,   59,   59,   81,   12,
   60,   62,   45,  123,   41,   18,   41,   51,  123,   95,
   41,   42,   43,   44,   45,   40,   47,   41,   44,    2,
   49,   50,   40,  237,   59,  239,   41,   42,   43,   44,
   45,   45,   47,   71,   40,   18,   49,   41,   45,   44,
  256,  124,  125,  265,  256,  256,  262,   45,  260,  260,
  123,   95,   41,   41,   59,   39,  278,   70,   71,  276,
  146,  123,   41,  107,  108,  256,   49,  278,   81,   41,
   59,   59,  271,  272,   44,   42,  258,  268,   44,  278,
   47,   45,   41,   59,   41,   42,   43,   59,   45,  172,
   47,  174,  259,   59,  256,  262,  259,  259,   44,  262,
  262,   42,   41,   40,   44,  123,   47,  259,   40,  278,
  262,  124,  125,   59,  158,   99,  258,  101,   41,   59,
   42,   43,   41,   45,   41,   47,   59,  125,  211,  167,
  168,  169,  170,  216,   42,   43,  256,   45,   41,   47,
  278,  256,   41,   41,   42,   43,   41,   45,   43,   47,
   45,  271,  272,  278,  167,  168,  169,  170,  278,  172,
   62,  174,   41,   41,  247,   43,   62,   45,  152,  153,
   41,   42,   43,   40,   45,  125,   47,  167,  168,  169,
  170,   41,   41,   41,   44,   41,   44,  125,   44,  256,
  271,  272,   40,  237,  256,  239,  125,   41,  211,  278,
   62,   41,   41,  216,   45,  271,  272,   59,   59,  269,
  271,  272,   59,  280,  278,  278,  276,  278,  278,   62,
   41,   59,   59,  258,   45,  235,  236,  256,  238,  278,
  240,  241,  256,  257,  247,  259,  279,  261,  262,  263,
  264,  278,  266,  267,  268,  276,  270,  271,  272,  256,
  276,  256,  256,  257,  278,  259,  280,  261,  262,  263,
  264,  276,  266,  267,  268,  279,  270,  271,  272,  258,
  258,  278,  279,  278,  278,   41,  280,  256,  257,   45,
  259,  279,  261,  262,  263,  264,  258,  266,  267,  268,
  256,  270,  271,  272,   60,   61,   62,  256,  257,  278,
  259,  280,  261,  262,  263,  264,  276,  266,  267,  268,
  256,  270,  271,  272,  278,  279,  256,  256,  257,  278,
  259,  280,  261,  262,  263,  264,   44,  266,  267,  268,
   62,  270,  271,  272,  278,   59,   59,  256,  257,  278,
  259,  280,  261,  262,  263,  264,   59,  266,  267,  268,
  278,  270,  271,  272,  278,   41,   59,  256,  257,  278,
  259,  280,  261,  262,  263,  264,  278,  266,  267,  268,
   44,  270,  271,  272,  278,   44,   59,  256,  257,  278,
  259,  280,  261,  262,  263,  264,  278,  266,  267,  268,
   59,  270,  271,  272,   59,   44,  279,  256,  257,  278,
  259,  280,  261,  262,  263,  264,   59,  266,  267,  268,
   41,  270,  271,  272,   59,  256,   59,  256,  257,  278,
  259,  280,  261,  262,  263,  264,   59,  266,  267,  268,
   41,  270,  271,  272,   41,  256,  257,  278,  279,  278,
   41,  280,  263,  264,   59,  266,  267,   40,  262,  270,
  271,  272,   45,   59,   59,   40,  277,  278,  279,  280,
   45,  262,   59,   59,   59,   44,  262,   60,   61,   62,
   59,   59,   59,   59,  262,   60,   61,   62,   59,   59,
   41,  125,  279,  278,   45,   41,   42,   43,   44,   45,
  256,   47,   59,   59,  279,  125,  125,   59,  125,   60,
   61,   62,   59,    0,   41,   43,   -1,  273,  274,  275,
   -1,   -1,  278,  279,   41,   42,   43,   44,   45,   41,
   47,   43,   44,   45,   41,   -1,   43,   44,   45,   -1,
   -1,   -1,   59,   60,   61,   62,   -1,   59,   60,   61,
   62,   -1,   59,   60,   61,   62,   41,  265,   43,   44,
   45,   -1,   -1,   45,   -1,   -1,   -1,   -1,  276,   -1,
  278,   -1,   -1,   -1,   59,   60,   61,   62,   60,   61,
   62,   42,   43,   -1,   45,   -1,   47,   -1,   -1,   -1,
   -1,   -1,  256,   -1,   -1,  259,   -1,  256,  262,   60,
   61,   62,   -1,   -1,  268,   -1,   -1,   38,   -1,   -1,
   -1,   -1,  276,   -1,   45,   -1,   -1,   -1,   49,  278,
   -1,   -1,   -1,   54,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   69,   -1,
   -1,   -1,   -1,   -1,   75,   76,   77,   78,   79,   80,
  256,  257,   -1,  259,  260,   -1,  262,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,   -1,  278,  256,  280,   -1,   -1,   -1,   -1,   -1,
   -1,  256,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,   -1,   -1,  278,  279,   -1,  273,  274,
  275,   -1,   -1,  278,  279,  256,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,
   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,   -1,
  276,   -1,  278,  164,   -1,   -1,   -1,   -1,   -1,  256,
   -1,  258,   -1,   -1,  256,   -1,  258,   -1,   -1,  256,
   -1,  258,   -1,   -1,   -1,   -1,  273,  274,  275,   -1,
   -1,  273,  274,  275,   -1,   -1,  273,  274,  275,   -1,
   -1,  256,   -1,  258,  256,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,
  275,  273,  274,  275,   -1,  256,  278,  279,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  273,  274,  275,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,   -1,  266,  267,  268,   -1,  270,
  271,  272,   -1,   -1,  256,  257,  258,  278,  260,  280,
   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,  256,  257,   -1,   -1,  278,  261,  280,  263,
  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,
  256,  257,   -1,   -1,  278,  261,  280,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,  256,
  257,   -1,  278,  260,  280,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
   -1,  278,   -1,  280,
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
public void imprimirSymbolTable() {
	System.out.println(this.st);
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
case 8:
//#line 72 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 19:
//#line 84 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 20:
//#line 85 "gramatica.y"
{System.out.println("Llegue a sentencia");}
break;
case 21:
//#line 88 "gramatica.y"
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
//#line 108 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 23:
//#line 112 "gramatica.y"
{ 
   
}
break;
case 24:
//#line 115 "gramatica.y"
{ 
    
}
break;
case 25:
//#line 117 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 26:
//#line 123 "gramatica.y"
{
        System.out.println("Declaración de función correcta");
    }
break;
case 27:
//#line 127 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }
break;
case 28:
//#line 132 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }
break;
case 29:
//#line 136 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 30:
//#line 140 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
    }
break;
case 31:
//#line 145 "gramatica.y"
{
        /* Caso correcto con un solo parámetro*/
    }
break;
case 32:
//#line 150 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 33:
//#line 153 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 34:
//#line 156 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
    }
break;
case 35:
//#line 160 "gramatica.y"
{}
break;
case 36:
//#line 161 "gramatica.y"
{}
break;
case 37:
//#line 165 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 38:
//#line 166 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 39:
//#line 168 "gramatica.y"
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
//#line 183 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 43:
//#line 186 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 44:
//#line 190 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 45:
//#line 193 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 46:
//#line 197 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 47:
//#line 200 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 48:
//#line 204 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 49:
//#line 207 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 50:
//#line 210 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 51:
//#line 211 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 52:
//#line 212 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 53:
//#line 213 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 55:
//#line 220 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 56:
//#line 223 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 57:
//#line 226 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 58:
//#line 229 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 59:
//#line 230 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 61:
//#line 237 "gramatica.y"
{     
        System.out.println("Llegue a salida");   
        }
break;
case 62:
//#line 240 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 63:
//#line 243 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 64:
//#line 246 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 65:
//#line 247 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");}
break;
case 66:
//#line 250 "gramatica.y"
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
//#line 270 "gramatica.y"
{
            /* Obtener el nombre del tipo desde T_ID*/
            String nombreTipo = val_peek(4).sval; /* T_ID*/

            /* Obtener el tipo base (INTEGER o SINGLE)*/
            String tipoBase = val_peek(2).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            //tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        }
break;
case 68:
//#line 279 "gramatica.y"
{

        }
break;
case 69:
//#line 282 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaración de tipo.");
        }
break;
case 70:
//#line 285 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 71:
//#line 288 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 72:
//#line 291 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
break;
case 73:
//#line 294 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 74:
//#line 297 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 75:
//#line 298 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 76:
//#line 299 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 77:
//#line 300 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 78:
//#line 301 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 79:
//#line 302 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 80:
//#line 303 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 81:
//#line 304 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 82:
//#line 305 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 83:
//#line 307 "gramatica.y"
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
//#line 329 "gramatica.y"
{ System.out.println("Llegue a subrango con - en el primero");}
break;
case 85:
//#line 330 "gramatica.y"
{System.out.println("Llegue a subrango con - en el segundo");}
break;
case 86:
//#line 331 "gramatica.y"
{System.out.println("Llegue a subrango con - en los dos");}
break;
case 87:
//#line 332 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 88:
//#line 334 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 90:
//#line 340 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 91:
//#line 341 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 92:
//#line 342 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1da expresion en la condicion");}
break;
case 99:
//#line 354 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 100:
//#line 355 "gramatica.y"
{ /* Acción correcta */ }
break;
case 101:
//#line 358 "gramatica.y"
{}
break;
case 102:
//#line 359 "gramatica.y"
{}
break;
case 103:
//#line 363 "gramatica.y"
{
               
                }
break;
case 104:
//#line 366 "gramatica.y"
{
                }
break;
case 105:
//#line 368 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 106:
//#line 369 "gramatica.y"
{ 
                }
break;
case 107:
//#line 373 "gramatica.y"
{
                }
break;
case 108:
//#line 381 "gramatica.y"
{
          
    }
break;
case 109:
//#line 384 "gramatica.y"
{}
break;
case 110:
//#line 385 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Solo se puede acceder a un par con 1 o 2");}
break;
case 112:
//#line 389 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 113:
//#line 390 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 114:
//#line 391 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 115:
//#line 395 "gramatica.y"
{
      }
break;
case 116:
//#line 397 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida"); /*CAMBIAR*/
        }
break;
case 126:
//#line 415 "gramatica.y"
{
        }
break;
case 127:
//#line 417 "gramatica.y"
{
        }
break;
case 128:
//#line 419 "gramatica.y"
{
        }
break;
case 129:
//#line 421 "gramatica.y"
{
        }
break;
case 130:
//#line 423 "gramatica.y"
{
                    
        }
break;
case 131:
//#line 426 "gramatica.y"
{
           
        }
break;
case 132:
//#line 429 "gramatica.y"
{
              
        }
break;
case 133:
//#line 432 "gramatica.y"
{
                    
        }
break;
case 134:
//#line 435 "gramatica.y"
{ /* Se añade la regla para operadores unarios*/
        }
break;
case 135:
//#line 437 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 136:
//#line 440 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
    yyval.dval = -val_peek(0).dval;
}
break;
//#line 1469 "Parser.java"
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
