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
    
 
   

class Subrango{
    private double limiteSuperior;
    private double limiteInferior;
    
    public Subrango(double limiteInferior, double limiteSuperior) {
        this.limiteSuperior = limiteSuperior; this.limiteInferior = limiteInferior;
    }
    public double getLimiteInferior() {
        return limiteInferior;
    }

    public double getLimiteSuperior() {
        return limiteSuperior;
    }

    @Override
    public String toString(){
      return "Limite inferior: "+ limiteInferior + " - Limite Superior: "+ limiteSuperior;
    }
}





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
    0,    0,    0,    1,    1,    2,    2,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    4,
    4,    4,   14,   14,   14,    9,    9,    9,    9,    9,
    9,   15,   16,   16,   16,   17,   17,   13,   13,   13,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,    6,    6,    7,    7,    7,    7,    7,
    7,    8,    8,    8,    8,    8,    8,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   19,   19,   19,   19,   19,
   19,   18,   18,   18,   18,   20,   20,   20,   20,   20,
   20,    5,    5,    5,   22,   22,   21,   21,   21,   21,
   21,   21,   21,   23,   23,   23,   10,   10,   10,   10,
   24,   24,   25,   26,   26,   26,   26,   26,   26,   26,
   26,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   27,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    5,    4,    4,    3,
    3,    2,    3,    1,    2,    7,    7,    7,    7,    7,
    8,    2,    3,    3,    0,    1,    1,    1,    1,    1,
    8,   10,    9,    7,    9,    7,    9,    7,    9,    7,
    8,    6,    8,    8,   10,    7,    6,    5,    6,    5,
    7,    5,    5,    4,    4,    5,    4,    6,    7,    7,
    6,    5,    5,    5,    7,    6,    6,    6,    6,    6,
    6,    5,    5,    5,    5,    5,    6,    6,    7,    2,
    1,    3,    3,    2,    2,    1,    1,    1,    1,    1,
    1,    4,    4,    3,    1,    3,    3,    3,    1,    1,
    3,    3,    3,    4,    3,    2,    3,    2,    2,    2,
    4,    4,    1,    3,    3,    3,    3,    1,    1,    1,
    1,    3,    3,    3,    3,    1,    1,    1,    1,    1,
    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    5,    0,    0,    0,    0,
    0,   39,   38,    0,   16,    0,    6,    8,    9,   10,
   11,   12,   13,   14,   15,    0,    0,    0,    1,  141,
   96,   97,   98,    0,  136,    0,    0,  100,  101,   99,
    0,    0,    0,  138,  139,  140,    0,    0,    0,    0,
    0,    0,    0,   36,   37,    0,  120,    0,  118,    0,
  116,    0,    4,    7,    0,    0,   22,    0,    0,    0,
    0,    0,  142,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   40,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  117,    0,  112,    0,  115,    0,    0,
    0,   21,   25,   20,    0,  104,    0,    0,    0,  108,
    0,  111,    0,    0,  128,  130,    0,    0,  131,    0,
    0,    0,    0,    0,  134,  135,    0,    0,    0,   67,
    0,    0,    0,    0,    0,    0,    0,    0,   91,    0,
    0,    0,    0,    0,    0,    0,   19,    0,    0,    0,
    0,    0,  114,    0,    0,    0,   23,  102,  103,    0,
  122,  121,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   62,   66,   63,   73,   72,    0,    0,    0,    0,
    0,    0,   90,   82,   84,    0,   83,   85,    0,    0,
   17,   58,    0,    0,    0,   60,    0,    0,    0,    0,
    0,    0,    0,    0,  126,  127,    0,    0,    0,    0,
    0,    0,    0,    0,   52,    0,   80,    0,   81,    0,
   71,    0,    0,   68,   78,   79,    0,   59,    0,   32,
    0,    0,    0,    0,    0,    0,    0,    0,   46,    0,
   50,    0,    0,    0,    0,    0,   48,    0,   69,   70,
   75,    0,    0,    0,   61,   56,   28,    0,    0,   33,
   27,   34,   30,   29,    0,   51,   54,    0,   41,    0,
    0,   53,   86,    0,    0,    0,   31,   47,    0,    0,
   43,   49,   88,   87,    0,   55,   42,   89,
};
final static short yydgoto[] = {                          3,
   54,   16,   55,   18,   19,   20,   21,   22,   23,   24,
   25,   41,   26,   68,  208,  209,   56,   42,  151,   43,
   27,  118,   44,   45,  127,  128,   46,
};
final static short yysindex[] = {                      -231,
  611, -237,    0,    0,  435,    0,   48,  374,   68,  574,
  -49,    0,    0, -109,    0,  629,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -54,    8, -228,    0,    0,
    0,    0,    0,  -36,    0, -175,  282,    0,    0,    0,
  554, -210,  117,    0,    0,    0,  247,   99, -247, -153,
 -217,  -39,   76,    0,    0, -236,    0,   23,    0, -154,
    0, -108,    0,    0,  -29,  115,    0,   42,   39, -121,
 -115,  142,    0,  -91,  135,  117,  117,  117,  117,  117,
  117,  648,   64,  140,   17,  130,  151,   50, -228,  -79,
  -76,  351,    0, -107,  -78, -107,  147,  150,  145,  111,
  357,  163,  461,    0,  -97,    0,   88,    0,  174, -247,
 -247,    0,    0,    0,  -63,    0,   64,   46,  -97,    0,
  -97,    0,  177,  -97,    0,    0,  178,  401,    0,  648,
  593,   64,    2,    2,    0,    0,   64, -197,  300,    0,
  325,  329,  334,  364,  365,  376,  380,  167,    0,  -12,
  393,  -51,  400,  404,  189,  190,    0,  410,  429,  357,
  307,  412,    0, -247,  195,  198,    0,    0,    0,  117,
    0,    0,   67,   67,   67,   67, -196,  552, -179,  648,
  418,    0,    0,    0,    0,    0,   -9,   -8,  209,  432,
  454,  220,    0,    0,    0,  446,    0,    0,  448,  449,
    0,    0,  468,  453,  474,    0,  -19,   59,   97,  475,
  479,   64,   14,   14,    0,    0,  648,  476,  271,  477,
  416,  648,  478,  277,    0,  481,    0,  482,    0,  483,
    0,  -11,  499,    0,    0,    0,  485,    0,  486,    0,
 -237, -237, -247, -237, -247, -237, -237,  290,    0,  495,
    0,  500,  648,  503,  296,  302,    0,  511,    0,    0,
    0,  450,  304,   -2,    0,    0,    0,  520,  306,    0,
    0,    0,    0,    0,  532,    0,    0, -235,    0,  541,
  543,    0,    0,  480,  492,  324,    0,    0,  545,  548,
    0,    0,    0,    0,  493,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  608,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  333,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   20,    0,    0,
    0,    0,    0,  506,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -34,    0,    0,
    0,    0,    0,    0,    0,  350,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   35,    0,   55,    0,  487,    0,    0,    0,   -5,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   78,    0,   33,    0,
   45,    0,    0,  141,    0,    0,    0,  569,    0,    0,
    0,   61,  528,  533,    0,    0,   80,    0,   -6,    0,
    0,   19,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   44,    0,    0,
    0,    0,    0,  120,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   69,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  107,   91,  105,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   94,    0,  119,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  144,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  169,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  194,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  222,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   52,    0,    0,    0,    0,    0,    0,    0,
    0,  590,  -37,    0, -128,    0,  -52,   30,  -77,  571,
    0,    0,   -1,    0,    0,   22,  -31,
};
final static int YYTABLESIZE=928;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
    4,   99,   29,   72,   67,   36,  119,  195,   28,   59,
  110,   94,   96,   62,   28,  150,  108,  153,  154,  102,
  289,  241,    1,   12,   13,   62,  290,   71,    1,  138,
   93,  103,  192,  263,   65,  138,  138,  138,  110,  138,
  129,  138,  286,   79,  150,   89,    2,   82,   80,  227,
  229,   70,   17,   97,   98,  175,   72,  152,  106,   64,
  176,  180,  217,  110,  181,  218,   75,   64,  120,  122,
  126,  150,  165,  166,  196,   94,  107,  177,  179,  222,
   28,  104,  223,   36,   18,  115,   62,   47,  113,  170,
  142,   79,   77,   94,   78,   95,   80,  116,   87,  242,
  114,   93,  243,   73,  169,   79,   77,   52,   78,   74,
   80,   36,  193,   95,  270,  101,  272,   12,   13,   93,
   92,  105,   95,  105,   93,  221,  207,  224,   28,   28,
  159,  124,  162,  124,   76,  124,  105,  244,   92,   62,
  245,  129,  129,  129,  129,  125,   60,  125,  149,  125,
  106,  158,   79,   77,  111,   78,  119,   80,   92,   77,
   35,   36,  121,   35,  248,  106,  130,   61,  255,  256,
  107,  126,  126,  126,  126,  131,   28,  149,   28,   61,
  139,  129,  129,  129,   57,  129,   36,  129,  140,  203,
  205,  141,   12,   13,  213,  214,  215,  216,  143,   93,
  278,  144,  160,  157,  149,  269,   57,  269,  155,   44,
   65,  156,  163,  164,  167,   28,   30,  171,  172,   28,
   28,  119,  119,   66,  119,  119,  119,  119,  119,  119,
   58,  119,  119,  119,   26,  119,  119,  119,   34,   35,
   61,  267,  268,  119,  271,  119,  273,  274,  109,   65,
   65,   28,   65,   65,   65,   65,   65,   65,  240,   65,
   65,   65,   45,   65,   65,   65,  191,  262,  226,  228,
  110,   65,   60,   65,   64,   64,  285,   64,   64,   64,
   64,   64,   64,   69,   64,   64,   64,   86,   64,   64,
   64,   36,   94,   61,   30,  110,   64,  112,   64,   18,
   18,  168,   18,   18,   18,   18,   18,   18,  107,   18,
   18,   18,   95,   18,   18,   18,   34,   35,   93,  113,
  113,   18,   74,   18,   74,   74,   36,   74,   74,   74,
   74,   74,   74,  105,   74,   74,   74,   92,   74,   74,
   74,   38,   40,   39,  124,  125,   74,  204,   74,   76,
   76,   36,   76,   76,   76,   76,   76,   76,  182,   76,
   76,   76,  106,   76,   76,   76,   38,   40,   39,   90,
   91,   76,   30,   76,   77,   77,  109,   77,   77,   77,
   77,   77,   77,  183,   77,   77,   77,  184,   77,   77,
   77,   40,  185,   24,   34,   35,   77,  123,   77,   57,
   57,   36,   57,   57,   57,   57,   57,   57,   24,   57,
   57,   57,  148,   57,   57,   57,   38,   40,   39,  124,
  125,   57,  186,   57,   44,   44,  187,   44,   44,   44,
   44,   44,   44,   51,   44,   44,   44,  188,   44,   44,
   44,  189,  175,  173,  190,  174,   44,  176,   44,   26,
   26,  194,   26,   26,   26,   26,   26,   26,  197,   26,
   26,   26,  198,   26,   26,   26,  199,  200,  201,  202,
  206,   26,  210,   26,   37,  211,  225,   45,   45,   36,
   45,   45,   45,   45,   45,   45,  230,   45,   45,   45,
  231,   45,   45,   45,   38,   40,   39,  232,  233,   45,
  161,   45,   30,    5,  234,   36,  235,  236,  237,    7,
    8,  238,    9,   10,  239,  246,   11,   12,   13,  247,
   38,   40,   39,   84,   85,   35,   15,  137,  137,  137,
  109,  137,  250,  137,  249,  251,  257,   30,  258,  259,
  260,  261,  264,  265,  266,   40,  137,  137,  137,  137,
  137,  275,  137,  276,   31,   32,   33,  280,  277,   34,
   35,  279,   30,  281,  137,  137,  137,  137,  132,  282,
  132,  132,  132,  133,  283,  133,  133,  133,  287,   31,
   32,   33,  284,  240,   34,   35,  132,  132,  132,  132,
  288,  133,  133,  133,  133,   79,   77,   40,   78,  291,
   80,  292,  295,  296,  293,   24,  297,    2,  109,  123,
   40,   81,   30,   38,   40,   39,  294,  298,    0,    0,
    0,  145,  146,    0,    0,    0,    0,   24,  147,   31,
   32,   33,   83,    0,   34,   35,   88,    0,    0,    0,
    0,  100,   48,    0,    0,    0,    0,    0,    0,   49,
    0,   50,    0,    0,    0,    0,    0,    0,  117,    0,
    0,    0,    0,    0,    0,  132,  133,  134,  135,  136,
  137,  252,    5,    0,  253,    1,    0,  254,    7,    8,
    0,    9,   10,    0,    0,   11,   12,   13,    0,    0,
   30,    0,    0,   14,    0,   15,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   31,   32,   33,
    0,    0,   34,   35,    0,    0,   30,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   31,   32,   33,    0,    0,   34,   35,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   40,    0,    0,    0,    0,    0,    0,    0,  212,
    0,  137,  109,  137,   40,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  137,  137,
  137,    0,    0,  132,    0,  132,    0,    0,  133,    0,
  133,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  132,  132,  132,    0,    0,  133,  133,  133,    5,   76,
  219,    1,    0,  220,    7,    8,    0,    9,   10,    0,
    0,   11,   12,   13,    0,    0,   31,   32,   33,   14,
    5,   15,    0,    1,    0,    0,    7,    8,    0,    9,
   10,   53,    0,   11,   12,   13,    0,    0,    0,    5,
  178,   14,    1,   15,    0,    7,    8,    0,    9,   10,
    0,    0,   11,   12,   13,    0,    0,    5,    0,    0,
   14,    6,   15,    7,    8,    0,    9,   10,    0,    0,
   11,   12,   13,    0,    0,    5,    0,    0,   14,   63,
   15,    7,    8,    0,    9,   10,    0,    0,   11,   12,
   13,    0,    0,    0,    5,    0,   14,    1,   15,    0,
    7,    8,    0,    9,   10,    0,    0,   11,   12,   13,
    0,    0,    0,    0,    0,   14,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    0,   41,    2,   40,   59,   45,   41,   59,   10,   59,
   40,   49,   50,  123,   16,  123,  125,   95,   96,  256,
  256,   41,  260,  271,  272,  123,  262,  256,  260,   82,
  278,  268,   45,   45,   41,   41,   42,   43,   44,   45,
   72,   47,   45,   42,  123,   47,  278,  258,   47,   59,
   59,   44,    1,  271,  272,   42,   40,   95,   60,   41,
   47,  259,  259,   44,  262,  262,   37,   16,   70,   71,
   72,  123,  110,  111,  152,   41,   44,  130,  131,  259,
   82,   59,  262,   45,   41,   44,  123,   40,   44,   44,
   41,   42,   43,   59,   45,   41,   47,   59,   47,   41,
   59,   41,   44,  279,   59,   42,   43,   40,   45,   41,
   47,   45,  125,   59,  243,   40,  245,  271,  272,   59,
   41,   44,  276,  278,  278,  178,  164,  180,  130,  131,
  101,   41,  103,   43,   41,   45,   59,   41,   59,  123,
   44,  173,  174,  175,  176,   41,  256,   43,  256,   45,
   44,   41,   42,   43,   40,   45,  278,   47,   60,   41,
   41,   45,  278,   44,  217,   59,  258,  277,  221,  222,
  279,  173,  174,  175,  176,   41,  178,  256,  180,  277,
   41,   41,   42,   43,   41,   45,   45,   47,   59,  160,
  161,   41,  271,  272,  173,  174,  175,  176,  278,  278,
  253,  278,   40,   59,  256,  243,  256,  245,   62,   41,
  265,   62,  125,   40,  278,  217,  256,   41,   41,  221,
  222,  256,  257,  278,  259,  260,  261,  262,  263,  264,
  280,  266,  267,  268,   41,  270,  271,  272,  278,  279,
  277,  241,  242,  278,  244,  280,  246,  247,  278,  256,
  257,  253,  259,  260,  261,  262,  263,  264,  278,  266,
  267,  268,   41,  270,  271,  272,  279,  279,  278,  278,
  276,  278,  256,  280,  256,  257,  279,  259,  260,  261,
  262,  263,  264,  276,  266,  267,  268,   41,  270,  271,
  272,   45,  258,  277,  256,  276,  278,  256,  280,  256,
  257,  256,  259,  260,  261,  262,  263,  264,  276,  266,
  267,  268,  258,  270,  271,  272,  278,  279,  258,  278,
  276,  278,   41,  280,  256,  257,   45,  259,  260,  261,
  262,  263,  264,  256,  266,  267,  268,  258,  270,  271,
  272,   60,   61,   62,  278,  279,  278,   41,  280,  256,
  257,   45,  259,  260,  261,  262,  263,  264,   59,  266,
  267,  268,  256,  270,  271,  272,   60,   61,   62,  271,
  272,  278,  256,  280,  256,  257,   44,  259,  260,  261,
  262,  263,  264,   59,  266,  267,  268,   59,  270,  271,
  272,   59,   59,   44,  278,  279,  278,  256,  280,  256,
  257,   45,  259,  260,  261,  262,  263,  264,   59,  266,
  267,  268,   62,  270,  271,  272,   60,   61,   62,  278,
  279,  278,   59,  280,  256,  257,   62,  259,  260,  261,
  262,  263,  264,   60,  266,  267,  268,   62,  270,  271,
  272,   62,   42,   43,  278,   45,  278,   47,  280,  256,
  257,   59,  259,  260,  261,  262,  263,  264,   59,  266,
  267,  268,   59,  270,  271,  272,  278,  278,   59,   41,
   59,  278,  278,  280,   40,  278,   59,  256,  257,   45,
  259,  260,  261,  262,  263,  264,  278,  266,  267,  268,
   59,  270,  271,  272,   60,   61,   62,   44,  279,  278,
   40,  280,  256,  257,   59,   45,   59,   59,   41,  263,
  264,   59,  266,  267,   41,   41,  270,  271,  272,   41,
   60,   61,   62,  277,  278,  279,  280,   41,   42,   43,
   44,   45,  262,   47,   59,   59,   59,  256,  262,   59,
   59,   59,   44,   59,   59,   59,   41,   42,   43,   44,
   45,  262,   47,   59,  273,  274,  275,  262,   59,  278,
  279,   59,  256,  262,   59,   60,   61,   62,   41,   59,
   43,   44,   45,   41,  125,   43,   44,   45,   59,  273,
  274,  275,  279,  278,  278,  279,   59,   60,   61,   62,
   59,   59,   60,   61,   62,   42,   43,  265,   45,   59,
   47,   59,  279,   59,  125,  256,   59,    0,  276,   41,
  278,   41,  256,   60,   61,   62,  125,  125,   -1,   -1,
   -1,  271,  272,   -1,   -1,   -1,   -1,  278,  278,  273,
  274,  275,   43,   -1,  278,  279,   47,   -1,   -1,   -1,
   -1,   52,  269,   -1,   -1,   -1,   -1,   -1,   -1,  276,
   -1,  278,   -1,   -1,   -1,   -1,   -1,   -1,   69,   -1,
   -1,   -1,   -1,   -1,   -1,   76,   77,   78,   79,   80,
   81,  256,  257,   -1,  259,  260,   -1,  262,  263,  264,
   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,
  256,   -1,   -1,  278,   -1,  280,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,
   -1,   -1,  278,  279,   -1,   -1,  256,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  265,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  170,
   -1,  256,  276,  258,  278,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,
  275,   -1,   -1,  256,   -1,  258,   -1,   -1,  256,   -1,
  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,   -1,   -1,  273,  274,  275,  257,  256,
  259,  260,   -1,  262,  263,  264,   -1,  266,  267,   -1,
   -1,  270,  271,  272,   -1,   -1,  273,  274,  275,  278,
  257,  280,   -1,  260,   -1,   -1,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,  257,
  258,  278,  260,  280,   -1,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,  257,   -1,   -1,
  278,  261,  280,  263,  264,   -1,  266,  267,   -1,   -1,
  270,  271,  272,   -1,   -1,  257,   -1,   -1,  278,  261,
  280,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,  257,   -1,  278,  260,  280,   -1,
  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,   -1,   -1,   -1,  278,   -1,  280,
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
"sentencia : RET '(' ')' ';'",
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
"salida : OUTF '(' ')' ';'",
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
"acceso_par : T_ID '{' '}'",
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

//#line 582 "gramatica.y"

public void yyerror(String s) {
    System.err.println("Error en linea: " + Lexer.nmrLinea + " String: " +s);
  }

int yylex() {
    try {
        Pair token = lexer.analyze(reader);  
        if (token != null) {
            if (token.getToken() == 277 || token.getToken() == 278 || token.getToken() == 279 || token.getToken() == 280) { //SI SE TRATA DE UN TOKEN QUE TIENE MUCHAS REFERENCIAS EN TABLA DE SIMBOLOS
                yylval = new ParserVal(token.getLexema());
            }
            if(token.getToken()<31) { //SI SE TRATA DE UN TOKEN DE UN SIMBOLO SINGULAR ESPECIFICO EN LA TABLA DE SIMBOLOS
            	
            	char character = token.getLexema().charAt(0);  
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
   
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);  

    if (result == JFileChooser.APPROVE_OPTION) {  
        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();  

        
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
    if (st.containsKeyTT(tipo)) {
        TipoSubrango subrango = st.getTipoSubrango(tipo);
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


	private SymbolTable st;
	private Lexer lexer;
	private BufferedReader reader;

	public Parser(String filePath) {
	    this.st = new SymbolTable();
	    try {
	        this.reader = new BufferedReader(new FileReader(filePath));
	        this.lexer = new Lexer(st);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
    }
    public void imprimirSymbolTable() {
	System.out.println(this.st);
    st.imprimirTablaTipos();

    }

//#line 758 "Parser.java"
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
//#line 51 "gramatica.y"
{
    System.out.println("Programa compilado correctamente");
    /*updatear uso nombre funcion*/
    st.updateUse(val_peek(1).sval, "Nombre de programa");
}
break;
case 2:
//#line 56 "gramatica.y"
{ 
    System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
}
break;
case 3:
//#line 59 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}
break;
case 5:
//#line 64 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 18:
//#line 81 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 19:
//#line 82 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
break;
case 20:
//#line 86 "gramatica.y"
{ 
    List<String> variables = (List<String>) val_peek(1).obj;  /* Obtener la lista de variables de lista_var*/

	for (String variable : variables) {
	    /* Verificar si la variable ya existe en la tabla de símbolos */
	    if (st.hasKey(variable)) {
	        System.out.println("Aclaracion, se declaro la variable: " + variable);
            /*updatear tipo de variable*/
            st.updateType(variable, val_peek(2).sval);
            /*updatear uso de variable a variable*/
	        st.updateUse(variable, "Nombre de variable");
	        /*updatear ambito*/

	    } else {
	        System.err.println("Error, la variable no está en la tabla de símbolos: " + variable);
	    }
	}
}
break;
case 21:
//#line 103 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 22:
//#line 104 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}
break;
case 23:
//#line 108 "gramatica.y"
{
    
    @SuppressWarnings("unchecked")
    List<String> variables = (List<String>) val_peek(2).obj;
    variables.add(val_peek(0).sval);  /* Agregar nueva variable*/
    yyval.obj = variables;  /* Pasar la lista actualizada hacia arriba */
}
break;
case 24:
//#line 115 "gramatica.y"
{
    List<String> variables = new ArrayList<String>();
    variables.add(val_peek(0).sval);  /* Agregar la primera variable*/
    yyval.obj = variables; 
}
break;
case 25:
//#line 120 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 26:
//#line 125 "gramatica.y"
{
        /*updatear uso nombre funcion*/
        st.updateUse(val_peek(4).sval, "Nombre de funcion");
        /*updatear uso nombre parametro*/
        st.updateUse(val_peek(2).sval, "Nombre de parametro");
    }
break;
case 27:
//#line 131 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }
break;
case 28:
//#line 136 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }
break;
case 29:
//#line 140 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 30:
//#line 144 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    }
break;
case 31:
//#line 147 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se puede poner ; al final de la declaracion de una fucnion");
    }
break;
case 32:
//#line 152 "gramatica.y"
{
        yyval.sval=val_peek(0).sval;
    }
break;
case 33:
//#line 157 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 34:
//#line 160 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 35:
//#line 163 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion debe tener un parametro.");
    }
break;
case 38:
//#line 173 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 39:
//#line 174 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 40:
//#line 176 "gramatica.y"
{
        
        /* Verificando si el tipo esta en la tabla de tipos definidos*/
        
        if (st.containsKeyTT(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0).sval);
        } 
    }
break;
case 43:
//#line 191 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 44:
//#line 192 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 45:
//#line 195 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 46:
//#line 199 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 47:
//#line 202 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 48:
//#line 206 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 49:
//#line 209 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 50:
//#line 213 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 51:
//#line 216 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 52:
//#line 219 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 53:
//#line 220 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 54:
//#line 221 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 55:
//#line 222 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 57:
//#line 230 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 58:
//#line 233 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
break;
case 59:
//#line 236 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 60:
//#line 239 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 61:
//#line 240 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 64:
//#line 248 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 65:
//#line 251 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 66:
//#line 254 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
break;
case 67:
//#line 255 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta contenido en el OUTF");}
break;
case 68:
//#line 258 "gramatica.y"
{ 

        System.out.println("2do");
        /* Obtener el nombre del tipo desde T_ID*/
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        String tipoBase = val_peek(2).sval;
        
        Subrango subrango = (Subrango) val_peek(1).obj;
    
        double limiteInferior = subrango.getLimiteInferior(); /* Limite inferior */
        double limiteSuperior = subrango.getLimiteSuperior(); /* Limite superior */
        /* Almacenar en la tabla de tipos*/
        /*FALTA CHEQUEAR MISMO TIPO*/
        if (tipoBase.toLowerCase().equals("longint")){
            long limiteInferiorLong = (long) limiteInferior; /* Convertir a longint*/
            long limiteSuperiorLong = (long) limiteSuperior; /* Convertir a longint*/
            this.st.insertTT(nombreTipo, new TipoSubrango(tipoBase, limiteInferiorLong, limiteSuperiorLong));

        } else this.st.insertTT(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        /*updatear uso*/
        st.updateUse(nombreTipo, "Nombre de tipo");


        }
break;
case 69:
//#line 283 "gramatica.y"
{
             String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (LONGINT)*/
            String tipoBase = val_peek(3).sval;
            /*updatear uso*/
            st.updateUse(nombreTipo, "Nombre de tipo");
            st.insertTT(nombreTipo, new TipoSubrango(tipoBase, -2147483647, 2147483647));
        }
break;
case 70:
//#line 292 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (DOUBLE)*/
            String tipoBase = val_peek(3).sval;
            /*updatear uso*/
            st.updateUse(nombreTipo, "Nombre de tipo");
            st.insertTT(nombreTipo, new TipoSubrango(tipoBase, -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
break;
case 71:
//#line 301 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
break;
case 72:
//#line 304 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 73:
//#line 307 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 74:
//#line 310 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
break;
case 75:
//#line 313 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 76:
//#line 316 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 77:
//#line 317 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 78:
//#line 318 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 79:
//#line 319 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 80:
//#line 320 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 81:
//#line 321 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 82:
//#line 322 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 83:
//#line 323 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 84:
//#line 324 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 85:
//#line 325 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la asignacion en la definicion de nuevos tipos");}
break;
case 86:
//#line 327 "gramatica.y"
{
        
        /*CODIGO PARA PARTE SEMANTICA*/

       String limiteInferiorStr = val_peek(3).sval; /* T_CTE (limites inferiores)*/
       String limiteSuperiorStr = val_peek(1).sval; /* T_CTE (limites superiores)*/
        try {
           
            double limiteInferior = Double.parseDouble(limiteInferiorStr);
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);
            System.out.println("limiteInferior: " + limiteInferior);
            System.out.println("limiteSuperior: " + limiteSuperior);

            if (limiteInferior <= limiteSuperior)
                yyval.obj = new Subrango(limiteInferior, limiteSuperior);
            else {
                System.out.println("Aclaración: el limite inferior era mas grande que el superior, fueron invertidos");
                yyval.obj = new Subrango(limiteSuperior, limiteInferior);

            }
            
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
        }
    }
break;
case 87:
//#line 352 "gramatica.y"
{

       /*CODIGO PARA PARTE SEMANTICA*/

       String limiteInferiorStr = val_peek(3).sval; /* T_CTE (limites inferiores)*/
       String limiteSuperiorStr = val_peek(1).sval; /* T_CTE (limites superiores)*/
        try {
           
            double limiteInferior = Double.parseDouble(limiteInferiorStr)*-1;
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);
            System.out.println("limiteInferior: " + limiteInferior);
            System.out.println("limiteSuperior: " + limiteSuperior);

            if (limiteInferior <= limiteSuperior)
                yyval.obj = new Subrango(limiteInferior, limiteSuperior);
            else {
                System.out.println("Aclaración: el limite inferior era mas grande que el superior, fueron invertidos");
                yyval.obj = new Subrango(limiteSuperior, limiteInferior);

            }
            
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
        }

    }
break;
case 88:
//#line 378 "gramatica.y"
{/*CODIGO PARA PARTE SEMANTICA*/
        yyerror("Error: el subrango esta mal declarado, fueron invertidos los rangos");
        String limiteInferiorStr = val_peek(1).sval; /* T_CTE (limites inferiores)*/
        String limiteSuperiorStr = val_peek(4).sval; /* T_CTE (limites superiores)*/
         try {
            
             double limiteInferior = Double.parseDouble(limiteInferiorStr)*-1;
             double limiteSuperior = Double.parseDouble(limiteSuperiorStr);
             System.out.println("limiteInferior: " + limiteInferior);
             System.out.println("limiteSuperior: " + limiteSuperior);
 
             
             yyval.obj = new Subrango(limiteInferior, limiteSuperior);
             
             
         } catch (NumberFormatException e) {
             System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
         }}
break;
case 89:
//#line 396 "gramatica.y"
{/*CODIGO PARA PARTE SEMANTICA*/
        String limiteInferiorStr = val_peek(4).sval; /* T_CTE (limites inferiores)*/
        String limiteSuperiorStr = val_peek(1).sval; /* T_CTE (limites superiores)*/
         try {
            
             double limiteInferior = Double.parseDouble(limiteInferiorStr)*-1;
             double limiteSuperior = Double.parseDouble(limiteSuperiorStr)*-1;
             System.out.println("limiteInferior: " + limiteInferior);
             System.out.println("limiteSuperior: " + limiteSuperior);
 
             
             if (limiteInferior <= limiteSuperior)
                yyval.obj = new Subrango(limiteInferior, limiteSuperior);
            else {
                System.out.println("Aclaración: el limite inferior era mas grande que el superior, fueron invertidos");
                yyval.obj = new Subrango(limiteSuperior, limiteInferior);

            }
             
             
         } catch (NumberFormatException e) {
             System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
         }}
break;
case 90:
//#line 419 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 91:
//#line 420 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 93:
//#line 426 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 94:
//#line 427 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 95:
//#line 428 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 102:
//#line 440 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 104:
//#line 442 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
break;
case 111:
//#line 454 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 112:
//#line 455 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 113:
//#line 456 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 114:
//#line 461 "gramatica.y"
{
            
        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
        }
        
    }
break;
case 115:
//#line 470 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se debe utilizar el indice 1 o 2 para acceder a los pares");}
break;
case 116:
//#line 471 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 118:
//#line 475 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 119:
//#line 476 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 120:
//#line 477 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 122:
//#line 481 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
break;
case 141:
//#line 507 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 142:
//#line 510 "gramatica.y"
{ 
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
                    st.addValue(nombreConMenos, tipo,"Constante",null,SymbolTable.constantValue);
                }
            } else if (tipo.equals("double")) {
                if (!lexer.isDoubleRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para double.");
                } else {
                    
                    st.addValue(nombreConMenos, tipo,"Constante",null, SymbolTable.constantValue);
                }
            }else if (tipo.equals("Octal")) {
                if (!lexer.isOctalRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para octales.");
                    
                } else {
                    st.addValue(nombreConMenos, tipo,"Constante",null, SymbolTable.constantValue);
                }
            }
        } else {
            System.err.println("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else { /*se trata de numero negativo menor al menor negativo.*/
    	
        if (nombreConstante.startsWith("0") && !nombreConstante.matches(".*[89].*")) {
        	System.err.println("El valor octal " + "-"+nombreConstante+ " se ajusto al valor minimo.");
            st.addValue("-020000000000", "Octal","Constante",null, SymbolTable.constantValue);
        } else if (nombreConstante.contains(".")) {
        	System.err.println("El valor double -" + nombreConstante + " se ajusta al valor mínimo.");

            /* Parseamos el valor como double para comparaciones*/
            double valorDouble = Double.parseDouble("-" + nombreConstante.replace("d", "e"));
            /* Rango mínimo y máximo de los números double*/
            double maxNegativeDouble = -1.7976931348623157e+308;
            double minNegativeDouble = -2.2250738585072014e-308;

            /* Si está por debajo del máximo permitido, lo mantenemos*/
            if (valorDouble < maxNegativeDouble) {
                st.addValue("-1.7976931348623156d+308", "double","Constante",null, SymbolTable.constantValue);
            } 
            /* Si está por debajo del mínimo permitido pero mayor al mínimo ajustado*/
            else if (valorDouble > minNegativeDouble) {
                st.addValue("-2.2250738585072015d-308", "double","Constante",null, SymbolTable.constantValue);
            } 
            /* Si está en el rango permitido*/
            else {
                st.addValue("-" + nombreConstante, "double","Constante",null, SymbolTable.constantValue);
            }
            
        } else{ /*ya se sabe que es entero*/
            /* Lógica para longint*/
        	System.err.println("El valor longint -" + nombreConstante + " se ajusta al valor mínimo.");
            nombreConMenos = "-2147483648"; /* Asignar valor mínimo si está fuera de rango*/
            st.addValue(nombreConMenos, "longint","Constante",null, SymbolTable.constantValue);
        }
        
    }
}
break;
//#line 1569 "Parser.java"
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
