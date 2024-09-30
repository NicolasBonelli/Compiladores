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





//#line 61 "Parser.java"




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
   22,   22,   21,   21,   21,   21,   21,   23,   23,   23,
   10,   10,   10,   10,   24,   24,   25,   26,   26,   26,
   26,   26,   26,   26,   26,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   27,
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
    1,    3,    3,    3,    1,    1,    1,    4,    4,    2,
    3,    2,    2,    2,    4,    4,    1,    3,    3,    3,
    3,    1,    1,    1,    1,    3,    3,    3,    3,    1,
    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    3,  107,    0,    5,    0,    0,
    0,    0,    0,   37,   36,    0,   17,    0,    7,    9,
   10,   11,   12,   13,   14,   15,   16,    0,    0,  106,
    1,  135,   93,   94,   95,    0,  130,    0,    0,   97,
   98,   96,    0,    0,    0,  132,  133,  134,    0,    0,
    0,    0,    0,    0,    0,    0,   34,   35,    0,  114,
    0,  112,  110,    0,    4,    8,    0,    0,    0,    0,
    0,    0,  136,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   38,    0,    0,    0,    0,    0,    0,
    0,    0,  111,    0,    0,    0,    0,    0,   21,   24,
   20,    0,    0,    0,    0,  104,    0,    0,  122,  124,
    0,    0,  125,    0,    0,    0,    0,    0,  128,  129,
    0,    0,    0,   64,    0,    0,    0,    0,    0,    0,
    0,    0,   88,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  109,  108,    0,    0,    0,   22,
   99,  100,    0,  116,  115,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   60,   65,   61,   71,   70,    0,
    0,    0,    0,    0,    0,   87,   80,   82,    0,   81,
    0,    0,   18,   56,    0,    0,    0,   58,    0,    0,
    0,    0,    0,    0,    0,    0,  120,  121,    0,    0,
    0,    0,    0,    0,    0,    0,   50,    0,   78,    0,
   79,    0,   69,    0,    0,   66,   76,   77,    0,   57,
    0,   30,    0,    0,    0,    0,    0,    0,    0,    0,
   44,    0,   48,    0,    0,    0,    0,    0,   46,    0,
   67,   68,   73,    0,    0,    0,   59,   54,   27,   25,
    0,   31,   26,   32,   29,   28,    0,   49,   52,    0,
   39,    0,    0,   51,   83,    0,    0,    0,   45,    0,
    0,   41,   47,   85,   84,    0,   53,   40,   86,
};
final static short yydgoto[] = {                          4,
   57,   18,   58,   20,   21,   22,   23,   24,   25,   26,
   27,   43,   28,   69,  200,  201,   59,   44,  145,   45,
   29,  114,   46,   47,  121,  122,   48,
};
final static short yysindex[] = {                      -182,
    0,  587, -179,    0,    0,    0,  401,    0,    9,  344,
   14,  -72,  -46,    0,    0, -119,    0,  605,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -241,   24,    0,
    0,    0,    0,    0,    0,  -38,    0, -214,  248,    0,
    0,    0,  549, -183,  -42,    0,    0,    0,  213,  112,
 -216, -193, -233,  -42,    0,   69,    0,    0, -211,    0,
   30,    0,    0, -247,    0,    0,  -28,   70,  130,  -42,
 -181,  133,    0, -147,   79,  -42,  -42,  -42,  -42,  -42,
  -42,  624,  100,    0,   98,  -38,   87,  109,   45,    0,
 -116, -110,  352,    0, -115, -118,  101,  120,   57,  373,
  143,  427,    0,   68,   72,  163, -216, -216,    0,    0,
    0,  -69,  100,    4, -119,    0,  172, -119,    0,    0,
  174,  114,    0,  624,  569,  100,  -27,  -27,    0,    0,
  100, -168,  159,    0,  280,  291,  295,  309,  313,  323,
  331,  122,    0,  -11,  320,  -52,  351,  147,  151,  377,
  396,  373,  273,  380,    0,    0, -216,  164,  165,    0,
    0,    0,  -42,    0,    0,    5,    5,    5,    5, -138,
  452, -127,  624,  394,    0,    0,    0,    0,    0,  -53,
  -45,  179,  405,  421,  192,    0,    0,    0,  414,    0,
  415,  416,    0,    0,  437,  422,  441,    0,  -19,  111,
  129,  445,  455,  100,   37,   37,    0,    0,  624,  439,
  239,  443,  507,  624,  444,  243,    0,  461,    0,  465,
    0,  466,    0,    7,  467,    0,    0,    0,  469,    0,
  475,    0, -179, -179, -216, -179, -216, -179, -179,  274,
    0,  476,    0,  478,  624,  479,  277,  278,    0,  482,
    0,    0,    0,  417,  264,    8,    0,    0,    0,    0,
  266,    0,    0,    0,    0,    0,  486,    0,    0, -198,
    0,  490,  491,    0,    0,  428,  429,  281,    0,  498,
  505,    0,    0,    0,    0,  447,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  568,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  241,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  471,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  299,    0,    0,    0,    0,    0,
  -40,    0,    0,    0,    0,    0,    0,  131,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -23,    0,   54,  -14,    0,  317,    0,    0,    0,   -1,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   23,    0,   28,    0,    0,   86,    0,    0,
    0,  532,    0,    0,    0,   71,  540,  545,    0,    0,
   78,    0,  -15,    0,    0,   10,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   35,
    0,    0,    0,    0,    0,    0,  161,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   60,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   81,   62,  136,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   85,    0,  110,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  135,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   49,    0,  160,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  188,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  261,    0,   17,    0,    0,    0,    0,    0,    0,    0,
    0,  586,  525,    0, -212,    0,  -65,   34,  -85,  531,
    0,    0,   -2,    0,    0,  341,  -51,
};
final static int YYTABLESIZE=904;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
  113,   72,   38,   64,  144,  219,  188,  144,  104,   30,
  147,  107,   62,  221,   79,   30,  132,   91,   19,   80,
  123,  233,  262,   67,  264,   63,  135,  135,  135,  107,
  135,  105,  135,  185,   66,   91,   68,   97,   98,  132,
  132,  132,  106,  132,  101,  132,   90,  163,   49,   38,
   62,  255,  278,   54,   14,   15,  102,  280,  170,  172,
  189,   94,  162,  281,   73,   88,  101,   71,  116,  120,
  144,  103,   75,    1,   82,   19,    1,    2,  168,   30,
    2,  101,   96,  169,   64,  136,   79,   77,  103,   78,
  173,   80,  107,  174,   92,    3,  115,  150,   79,   77,
   72,   78,  118,   80,  118,  213,  118,  216,  100,  108,
  124,   90,   92,  186,  123,  123,  123,  123,   89,  125,
  209,   30,   30,  210,  102,   74,  123,  123,  123,   90,
  123,  214,  123,  151,  215,  154,   89,  143,  133,  102,
  143,   79,   77,  240,   78,  134,   80,  247,  248,  135,
   75,  234,   14,   15,  235,  168,  166,   63,  167,   94,
  169,  137,  148,  120,  120,  120,  120,  138,   30,  236,
   30,   93,  237,  112,   23,   55,  119,   38,  119,  270,
  119,  149,  152,   55,    7,  195,  197,    2,  111,   23,
    9,   10,  155,   11,   12,   56,  156,   13,   14,   15,
   42,   33,  157,  143,   33,   16,   30,   17,  160,   60,
   30,   30,  164,   32,  165,  113,  113,  175,  113,  113,
  113,  113,  113,  113,  218,  113,  113,  113,   43,  113,
  113,  113,  220,   61,   91,   36,   37,  113,   63,  113,
   63,   63,   30,   63,   63,   63,   63,   63,   63,  106,
   63,   63,   63,   87,   63,   63,   63,   38,  232,  161,
    5,  107,   63,   31,   63,   62,   62,  184,   62,   62,
   62,   62,   62,   62,  106,   62,   62,   62,  101,   62,
   62,   62,  118,  119,  105,  254,  277,   62,   74,   62,
   19,   19,   38,   19,   19,   19,   19,   19,   19,   70,
   19,   19,   19,  103,   19,   19,   19,   40,   42,   41,
    6,   92,   19,  196,   19,   72,   72,   38,   72,   72,
   72,   72,   72,   72,  107,   72,   72,   72,   90,   72,
   72,   72,   40,   42,   41,   89,  102,   72,  176,   72,
   74,   74,  107,   74,   74,   74,   74,   74,   74,  177,
   74,   74,   74,  178,   74,   74,   74,  131,  131,  131,
  105,  131,   74,  131,   74,   75,   75,  179,   75,   75,
   75,   75,   75,   75,  180,   75,   75,   75,  187,   75,
   75,   75,   91,   92,  181,  109,   23,   75,  117,   75,
   55,   55,  182,   55,   55,   55,   55,   55,   55,  183,
   55,   55,   55,   53,   55,   55,   55,  110,   23,  190,
  118,  119,   55,  142,   55,   42,   42,   38,   42,   42,
   42,   42,   42,   42,  191,   42,   42,   42,  192,   42,
   42,   42,   40,   42,   41,  193,  194,   42,  198,   42,
   39,  202,  203,   43,   43,   38,   43,   43,   43,   43,
   43,   43,  217,   43,   43,   43,  222,   43,   43,   43,
   40,   42,   41,  223,  224,   43,  153,   43,   84,    7,
  225,   38,  226,  227,  228,    9,   10,  229,   11,   12,
  230,  231,   13,   14,   15,  238,   40,   42,   41,   85,
   86,   37,   17,  259,  260,  239,  263,  241,  265,  266,
  242,  243,  249,   32,  250,   38,  205,  206,  207,  208,
  256,  131,  131,  131,  131,  131,  105,  131,   38,  251,
   33,   34,   35,  252,  253,   36,   37,  257,   32,  131,
  131,  131,  131,  258,  268,  267,  269,  271,  272,  273,
  274,  275,  276,  232,  279,   33,   34,   35,  282,  283,
   36,   37,  284,  285,    6,    6,  287,    6,    6,  286,
    6,    6,    6,  288,    6,    6,    6,    2,    6,    6,
    6,  289,  117,   81,  107,   95,    6,    0,    6,    0,
  126,   38,  126,  126,  126,  127,    0,  127,  127,  127,
   79,   77,  105,   78,   38,   80,    0,    0,  126,  126,
  126,  126,    0,  127,  127,  127,  127,    0,   40,   42,
   41,    0,   50,    0,    0,    0,    0,    0,    0,   51,
  146,   52,  139,  140,    0,    0,    0,    0,   32,  141,
   83,  158,  159,    0,   89,    0,    0,    0,    0,   99,
    0,    0,    0,    0,    0,   33,   34,   35,    0,    0,
   36,   37,    0,    0,    0,  113,   32,    0,    0,    0,
    0,  126,  127,  128,  129,  130,  131,    0,    0,    0,
    0,    0,    0,   33,   34,   35,    0,    0,   36,   37,
    0,  199,   32,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   33,
   34,   35,    0,    0,   36,   37,    0,   55,    7,    0,
  211,    2,    0,  212,    9,   10,    0,   11,   12,    0,
    0,   13,   14,   15,    0,    0,  131,    0,  131,   16,
    0,   17,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  131,  131,  131,    0,    0,  204,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  261,
    0,  261,  244,    7,    0,  245,    2,    0,  246,    9,
   10,    0,   11,   12,    0,    0,   13,   14,   15,    0,
    0,    0,    0,    0,   16,    0,   17,    0,    0,    0,
    0,    0,    0,    0,    0,  126,    0,  126,    0,    0,
  127,    0,  127,    0,   76,    0,    0,    0,    0,    0,
    0,    0,  126,  126,  126,    0,    0,  127,  127,  127,
    0,   33,   34,   35,   55,    7,  171,    0,    2,    0,
    0,    9,   10,    0,   11,   12,    0,    0,   13,   14,
   15,    0,    6,    7,    0,    0,   16,    8,   17,    9,
   10,    0,   11,   12,    0,    0,   13,   14,   15,    0,
    6,    7,    0,    0,   16,   65,   17,    9,   10,    0,
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
   41,   40,   45,  123,  123,   59,   59,  123,  256,   12,
   96,   40,   59,   59,   42,   18,   82,   41,    2,   47,
   72,   41,  235,  265,  237,   41,   41,   42,   43,   44,
   45,  279,   47,   45,   18,   59,  278,  271,  272,   41,
   42,   43,   44,   45,  256,   47,   49,   44,   40,   45,
   41,   45,   45,   40,  271,  272,  268,  256,  124,  125,
  146,  278,   59,  262,  279,   49,   44,   44,   71,   72,
  123,   44,   39,  256,  258,   41,  256,  260,   42,   82,
  260,   59,  276,   47,  123,   41,   42,   43,   59,   45,
  259,   47,   44,  262,   41,  278,  278,   41,   42,   43,
   41,   45,   41,   47,   43,  171,   45,  173,   40,   40,
  258,   41,   59,  125,  166,  167,  168,  169,   41,   41,
  259,  124,  125,  262,   44,   41,   41,   42,   43,   59,
   45,  259,   47,  100,  262,  102,   59,  256,   41,   59,
  256,   42,   43,  209,   45,   59,   47,  213,  214,   41,
   41,   41,  271,  272,   44,   42,   43,  277,   45,  278,
   47,  278,   62,  166,  167,  168,  169,  278,  171,   41,
  173,   60,   44,   44,   44,   41,   41,   45,   43,  245,
   45,   62,   40,  256,  257,  152,  153,  260,   59,   59,
  263,  264,  125,  266,  267,  268,  125,  270,  271,  272,
   41,   41,   40,  256,   44,  278,  209,  280,  278,  256,
  213,  214,   41,  256,   41,  256,  257,   59,  259,  260,
  261,  262,  263,  264,  278,  266,  267,  268,   41,  270,
  271,  272,  278,  280,  258,  278,  279,  278,  277,  280,
  256,  257,  245,  259,  260,  261,  262,  263,  264,  278,
  266,  267,  268,   41,  270,  271,  272,   45,  278,  256,
    0,  276,  278,    3,  280,  256,  257,  279,  259,  260,
  261,  262,  263,  264,  276,  266,  267,  268,  256,  270,
  271,  272,  278,  279,   44,  279,  279,  278,   41,  280,
  256,  257,   45,  259,  260,  261,  262,  263,  264,  276,
  266,  267,  268,  276,  270,  271,  272,   60,   61,   62,
  262,  258,  278,   41,  280,  256,  257,   45,  259,  260,
  261,  262,  263,  264,  276,  266,  267,  268,  258,  270,
  271,  272,   60,   61,   62,  258,  256,  278,   59,  280,
  256,  257,   44,  259,  260,  261,  262,  263,  264,   59,
  266,  267,  268,   59,  270,  271,  272,   41,   42,   43,
   44,   45,  278,   47,  280,  256,  257,   59,  259,  260,
  261,  262,  263,  264,   62,  266,  267,  268,   59,  270,
  271,  272,  271,  272,   62,  256,  256,  278,  256,  280,
  256,  257,   62,  259,  260,  261,  262,  263,  264,  278,
  266,  267,  268,   60,  270,  271,  272,  278,  278,   59,
  278,  279,  278,   62,  280,  256,  257,   45,  259,  260,
  261,  262,  263,  264,  278,  266,  267,  268,  278,  270,
  271,  272,   60,   61,   62,   59,   41,  278,   59,  280,
   40,  278,  278,  256,  257,   45,  259,  260,  261,  262,
  263,  264,   59,  266,  267,  268,  278,  270,  271,  272,
   60,   61,   62,   59,   44,  278,   40,  280,  256,  257,
  279,   45,   59,   59,   59,  263,  264,   41,  266,  267,
   59,   41,  270,  271,  272,   41,   60,   61,   62,  277,
  278,  279,  280,  233,  234,   41,  236,   59,  238,  239,
  262,   59,   59,  256,  262,  265,  166,  167,  168,  169,
   44,   41,   42,   43,   44,   45,  276,   47,  278,   59,
  273,  274,  275,   59,   59,  278,  279,   59,  256,   59,
   60,   61,   62,   59,   59,  262,   59,   59,  262,  262,
   59,  125,  279,  278,   59,  273,  274,  275,   59,   59,
  278,  279,  125,  125,  256,  257,   59,  259,  260,  279,
  262,  263,  264,   59,  266,  267,  268,    0,  270,  271,
  272,  125,   41,   43,  276,   51,  278,   -1,  280,   -1,
   41,  265,   43,   44,   45,   41,   -1,   43,   44,   45,
   42,   43,  276,   45,  278,   47,   -1,   -1,   59,   60,
   61,   62,   -1,   59,   60,   61,   62,   -1,   60,   61,
   62,   -1,  269,   -1,   -1,   -1,   -1,   -1,   -1,  276,
   96,  278,  271,  272,   -1,   -1,   -1,   -1,  256,  278,
   45,  107,  108,   -1,   49,   -1,   -1,   -1,   -1,   54,
   -1,   -1,   -1,   -1,   -1,  273,  274,  275,   -1,   -1,
  278,  279,   -1,   -1,   -1,   70,  256,   -1,   -1,   -1,
   -1,   76,   77,   78,   79,   80,   81,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,
   -1,  157,  256,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,
  274,  275,   -1,   -1,  278,  279,   -1,  256,  257,   -1,
  259,  260,   -1,  262,  263,  264,   -1,  266,  267,   -1,
   -1,  270,  271,  272,   -1,   -1,  256,   -1,  258,  278,
   -1,  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,   -1,  163,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  235,
   -1,  237,  256,  257,   -1,  259,  260,   -1,  262,  263,
  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,
   -1,   -1,   -1,   -1,  278,   -1,  280,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,   -1,  258,   -1,   -1,
  256,   -1,  258,   -1,  256,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  273,  274,  275,   -1,   -1,  273,  274,  275,
   -1,  273,  274,  275,  256,  257,  258,   -1,  260,   -1,
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
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : acceso_par",
"IDENTIFIER_LIST : error",
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

//#line 452 "gramatica.y"

public void yyerror(String s) {
    System.err.println("Error en linea: " + Lexer.nmrLinea + " String: " +s);
  }

int yylex() {
    try {
        Pair token = lexer.analyze(reader);  
        System.out.println("Pair: "+ token);
        if (token != null) {
            System.out.println("Token: " + token.getLexema() + " :: " + token.getToken());

            
            if (token.getToken() == 277 || token.getToken() == 278 || token.getToken() == 279 || token.getToken() == 280) { //SI SE TRATA DE UN TOKEN QUE TIENE MUCHAS REFERENCIAS EN TABLA DE SIMBOLOS
                yylval = new ParserVal(token.getLexema());
            }
            if(token.getToken()<31) { //SI SE TRATA DE UN TOKEN DE UN SIMBOLO SINGULAR ESPECIFICO EN LA TABLA DE SIMBOLOS
            	
            	char character = token.getLexema().charAt(0);  
                System.out.println("Character:" + character);
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

//#line 736 "Parser.java"
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
case 19:
//#line 84 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 20:
//#line 88 "gramatica.y"
{ 
    System.out.println("Llegue a declaracion");
    List<ParserVal> variables = new ArrayList<ParserVal>();
    variables.add(val_peek(1)); 
    
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
//#line 108 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 24:
//#line 114 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 26:
//#line 120 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }
break;
case 27:
//#line 125 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }
break;
case 28:
//#line 129 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 29:
//#line 133 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
    }
break;
case 31:
//#line 141 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 32:
//#line 144 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 33:
//#line 147 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
    }
break;
case 36:
//#line 157 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 37:
//#line 158 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 38:
//#line 160 "gramatica.y"
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
//#line 175 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 42:
//#line 176 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 43:
//#line 179 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 44:
//#line 183 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 45:
//#line 186 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 46:
//#line 190 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 47:
//#line 193 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 48:
//#line 197 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 49:
//#line 200 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 50:
//#line 203 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 51:
//#line 204 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 52:
//#line 205 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 53:
//#line 206 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 55:
//#line 213 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 56:
//#line 216 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 57:
//#line 219 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 58:
//#line 222 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 59:
//#line 223 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 62:
//#line 231 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 63:
//#line 234 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 64:
//#line 237 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 65:
//#line 238 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");}
break;
case 66:
//#line 241 "gramatica.y"
{
        System.out.println("Llegue a sentencia_declarativa_tipos");
        
        /* Obtener el nombre del tipo desde T_ID*/
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        /* Obtener el tipo base (INTEGER o SINGLE)*/
        String tipoBase = val_peek(2).sval;
        System.out.println("tipobase"+ " "+tipoBase );
        /* tipo base (INTEGER o SINGLE)*/
        
        double limiteInferior = val_peek(4).dval; /* Limite inferior */
        System.out.println("liminf"+ " "+limiteInferior );
        
        double limiteSuperior =  val_peek(5).dval; /* Limite superior */
        System.out.println("limsup"+ " "+limiteSuperior );
        /* Almacenar en la tabla de tipos*/
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        System.out.println("ENTRE A DEFINIR NUEVO TIPO");
        }
break;
case 67:
//#line 261 "gramatica.y"
{
             String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (LONGINT)*/
            String tipoBase = val_peek(3).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -2147483647, 2147483647));
        }
break;
case 68:
//#line 269 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (DOUBLE)*/
            String tipoBase = val_peek(3).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
break;
case 69:
//#line 277 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaración de tipo.");
        }
break;
case 70:
//#line 280 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 71:
//#line 283 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 72:
//#line 286 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
break;
case 73:
//#line 289 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 74:
//#line 292 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 75:
//#line 293 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 76:
//#line 294 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 77:
//#line 295 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 78:
//#line 296 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 79:
//#line 297 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 80:
//#line 298 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 81:
//#line 299 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 82:
//#line 300 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 83:
//#line 302 "gramatica.y"
{
        System.out.println("Llegue a subrango");
        /*CODIGO PARA PARTE SEMANTICA*/
        
        String limiteInferiorStr = val_peek(3).sval; /* T_CTE (límites inferiores)*/
        String limiteSuperiorStr = val_peek(1).sval; /* T_CTE (límites superiores)*/

        System.out.println("VAL3 (Limite Inferior): " + limiteInferiorStr);
        System.out.println("VAL1 (Limite Superior): " + limiteSuperiorStr);

        try {
           
            double limiteInferior = Double.parseDouble(limiteInferiorStr);
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);

            
            yylval.obj = new Subrango(limiteInferior, limiteSuperior);
            System.out.println("Subrango creado correctamente con límites: " + limiteInferior + " - " + limiteSuperior);
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los límites del subrango a double: " + e.getMessage());
        }
    }
break;
case 87:
//#line 327 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 88:
//#line 328 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 90:
//#line 334 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 91:
//#line 335 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 92:
//#line 336 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 99:
//#line 348 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 107:
//#line 361 "gramatica.y"
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
case 110:
//#line 377 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 112:
//#line 381 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 113:
//#line 382 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 114:
//#line 383 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 116:
//#line 387 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida"); 
        }
break;
case 135:
//#line 413 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 136:
//#line 416 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
    double valor = val_peek(0).dval;  
      

    String nombreConstante = val_peek(0).sval;  
    String nombreConMenos = "-" + nombreConstante;
    /* verificación en la tabla de símbolos.*/
    if (st.hasKey(nombreConstante)) {
        String tipo = st.getType(nombreConstante);  /*  tipo de la constante.*/
        if (tipo != null) {
            /* Verifica si el valor original (sin negativo) está en el rango adecuado según el tipo.*/
            if (tipo.equals("longint")) {
                if (!lexer.isLongintRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " está fuera del rango permitido para longint.");
                } else {
                	
                    
                    st.addValue(nombreConMenos, tipo,SymbolTable.constantValue);
                }
            } else if (tipo.equals("double")) {
                if (!lexer.isDoubleRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " está fuera del rango permitido para double.");
                } else {
                    
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
//#line 1370 "Parser.java"
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
