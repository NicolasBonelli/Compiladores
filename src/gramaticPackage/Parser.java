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
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    4,    4,    4,   14,   14,   14,    9,    9,    9,    9,
    9,   15,   16,   16,   16,   17,   17,   13,   13,   13,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,    6,    6,    7,    7,    7,    7,    7,
    7,    8,    8,    8,    8,    8,    8,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   19,   19,   19,   19,   19,   19,
   18,   18,   18,   18,   20,   20,   20,   20,   20,   20,
    5,    5,    5,   22,   22,   21,   21,   21,   21,   21,
   21,   21,   21,   23,   23,   23,   10,   10,   10,   10,
   24,   24,   25,   26,   26,   26,   26,   26,   26,   26,
   26,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   27,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    1,    2,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    5,    4,    3,
    3,    3,    2,    3,    1,    2,    7,    7,    7,    7,
    7,    2,    3,    3,    0,    1,    1,    1,    1,    1,
    8,   10,    9,    7,    9,    7,    9,    7,    9,    7,
    8,    6,    8,    8,   10,    7,    6,    5,    6,    5,
    7,    5,    5,    4,    4,    4,    5,    6,    7,    7,
    6,    5,    5,    5,    7,    6,    6,    6,    6,    6,
    6,    5,    5,    5,    5,    6,    6,    7,    2,    1,
    3,    3,    2,    2,    1,    1,    1,    1,    1,    1,
    4,    4,    3,    1,    3,    3,    3,    1,    1,    3,
    3,    3,    1,    4,    4,    2,    3,    2,    2,    2,
    4,    4,    1,    3,    3,    3,    3,    1,    1,    1,
    1,    3,    3,    3,    3,    1,    1,    1,    1,    1,
    1,    2,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    3,  113,    0,    5,    0,    0,
    0,    0,    0,   39,   38,    0,   17,    0,    7,    9,
   10,   11,   12,   13,   14,   15,   16,    0,    0,    0,
    1,  141,   95,   96,   97,    0,  136,    0,    0,   99,
  100,   98,    0,    0,    0,  138,  139,  140,    0,    0,
    0,    0,    0,    0,    0,    0,   36,   37,    0,  120,
    0,  118,    0,  116,    0,    4,    8,    0,    0,   23,
    0,    0,    0,    0,    0,  142,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   40,    0,    0,    0,
    0,   20,    0,    0,    0,    0,  117,    0,  111,    0,
    0,    0,    0,    0,   22,   26,   21,    0,  103,    0,
    0,    0,  107,    0,  110,    0,    0,  128,  130,    0,
    0,  131,    0,    0,    0,    0,    0,  134,  135,    0,
    0,    0,   66,    0,    0,    0,    0,    0,    0,    0,
    0,   90,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  115,  114,    0,    0,    0,   24,  101,
  102,    0,  122,  121,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   62,   67,   63,   73,   72,    0,    0,
    0,    0,    0,    0,   89,   82,   84,    0,   83,    0,
    0,   18,   58,    0,    0,    0,   60,    0,    0,    0,
    0,    0,    0,    0,    0,  126,  127,    0,    0,    0,
    0,    0,    0,    0,    0,   52,    0,   80,    0,   81,
    0,   71,    0,    0,   68,   78,   79,    0,   59,    0,
   32,    0,    0,    0,    0,    0,    0,    0,    0,   46,
    0,   50,    0,    0,    0,    0,    0,   48,    0,   69,
   70,   75,    0,    0,    0,   61,   56,   29,   27,    0,
   33,   28,   34,   31,   30,    0,   51,   54,    0,   41,
    0,    0,   53,   85,    0,    0,    0,   47,    0,    0,
   43,   49,   87,   86,    0,   55,   42,   88,
};
final static short yydgoto[] = {                          4,
   57,   18,   58,   20,   21,   22,   23,   24,   25,   26,
   27,   43,   28,   71,  209,  210,   59,   44,  154,   45,
   29,  121,   46,   47,  130,  131,   48,
};
final static short yysindex[] = {                      -227,
    0,  632, -207,    0,    0,    0,  408,    0,  -25,  324,
    3,  595,  -48,    0,    0, -114,    0,  650,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -53,   -7, -186,
    0,    0,    0,    0,    0,  -35,    0, -188,  257,    0,
    0,    0,  556, -155,   92,    0,    0,    0,  222,   22,
 -248, -172, -244,  -38,    0,   78,    0,    0, -242,    0,
   77,    0, -145,    0, -234,    0,    0,  -19,  104,    0,
  139,   89, -129, -125,  142,    0, -100,  118,   92,   92,
   92,   92,   92,   92,  669,  164,    0,  120,   15,  106,
  126,   51, -186, -110, -109,  351,    0, -105,  -77,  110,
  115,    0,   98,  382,  148,  436,    0, -111,    0,   57,
   66,  152, -248, -248,    0,    0,    0,  -85,    0,  164,
  103, -111,    0, -111,    0,  156, -111,    0,    0,  159,
  507,    0,  669,  614,  164,   10,   10,    0,    0,  164,
 -203,  143,    0,  145,  155,  158,  163,  141,  153,  311,
   99,    0,   -9,  333,  364,  360,  160,  168,  375,  409,
  382,  282,  401,    0,    0, -248,  186,  193,    0,    0,
    0,   92,    0,    0,   17,   17,   17,   17, -195,  513,
 -175,  669,  413,    0,    0,    0,    0,    0,   -1,   68,
  196,  418,  438,  201,    0,    0,    0,  424,    0,  425,
  431,    0,    0,  450,  449,  454,    0,  -10,   56,   67,
  474,  486,  164,   21,   21,    0,    0,  669,  470,  271,
  475,  576,  669,  478,  277,    0,  485,    0,  492,    0,
  494,    0,    5,  514,    0,    0,    0,  500,    0,  503,
    0, -207, -207, -248, -207, -248, -207, -207,  301,    0,
  510,    0,  518,  669,  523,  304,  319,    0,  524,    0,
    0,    0,  460,  318,    9,    0,    0,    0,    0,  326,
    0,    0,    0,    0,    0,  550,    0,    0, -237,    0,
  555,  560,    0,    0,  499,  502,  346,    0,  571,  572,
    0,    0,    0,    0,  508,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  634,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  350,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   37,
    0,    0,    0,    0,    0,  481,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  308,    0,    0,    0,    0,    0,
  -33,    0,    0,    0,    0,    0,    0,    0,  140,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  -39,    0,  105,   -3,    0,  462,    0,
    0,    0,   33,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  137,
    0,   45,    0,   62,    0,    0,  469,    0,    0,    0,
  591,    0,    0,    0,  111,  546,  551,    0,    0,  130,
    0,   -6,    0,    0,   19,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   44,    0,
    0,    0,    0,    0,    0,   85,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   69,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  146,   64,   74,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   94,    0,  119,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  144,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   58,    0,  169,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  195,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   30,    0,    0,    0,    0,    0,    0,    0,
    0,   41,  522,    0, -116,    0,  -68,   -5,  -86,  594,
    0,    0,   -2,    0,    0,  342,  370,
};
final static int YYTABLESIZE=949;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
    5,   93,  102,   31,   75,   70,   38,  119,   65,   30,
   62,   65,  156,  105,   49,   30,  141,  153,  289,   93,
  113,  110,   14,   15,  290,  106,  100,  101,    1,   97,
  242,   19,    2,   78,   65,  194,   73,  141,  141,  141,
  113,  141,   54,  141,  111,  153,   93,   67,    1,  264,
    3,   82,    2,  287,   75,  182,   83,  228,  183,   64,
  109,   38,  177,  218,  179,  181,  219,  178,  198,   74,
  123,  125,  129,  138,  138,  138,  109,  138,   91,  138,
  109,   96,   30,  223,   19,   86,  224,   65,  106,   92,
   76,  145,   82,   80,  103,   81,  243,   83,  160,  244,
  163,  113,   85,   99,  124,  112,  124,  245,  124,   74,
  246,  222,  120,  225,  125,  195,  125,  104,  125,  135,
  136,  137,  138,  139,  140,   35,  230,  271,   35,  273,
   30,   30,  108,   38,   76,  107,   38,   65,  159,   82,
   80,   63,   81,  114,   83,   94,  172,  119,  122,  249,
  152,   92,  124,  256,  257,  204,  206,  133,  134,   77,
  142,  171,   64,   94,  143,   64,  144,  146,  147,   92,
   91,  157,  129,  129,  129,  129,  158,   30,  152,   30,
  104,  164,  118,   25,   57,  279,   38,  161,   91,  105,
  165,  166,  169,   14,   15,  104,  173,  117,   25,  174,
   97,  184,  189,  185,  105,   82,   80,   60,   81,   44,
   83,   68,  213,  186,  190,   30,  187,   32,   93,   30,
   30,  188,  119,  119,   69,  119,  119,  119,  119,  119,
  119,   61,  119,  119,  119,   45,  119,  119,  119,   36,
   37,   64,  268,  269,  119,  272,  119,  274,  275,   65,
   65,   30,   65,   65,   65,   65,   65,   65,  112,   65,
   65,   65,   90,   65,   65,   65,   38,  241,   72,  193,
   63,   65,  113,   65,   64,   64,  227,   64,   64,   64,
   64,   64,   64,  263,   64,   64,   64,  286,   64,   64,
   64,   64,   94,   95,  127,  128,   64,   77,   64,   19,
   19,   38,   19,   19,   19,   19,   19,   19,  109,   19,
   19,   19,  109,   19,   19,   19,   40,   42,   41,    6,
  106,   19,  205,   19,   74,   74,   38,   74,   74,   74,
   74,   74,   74,  113,   74,   74,   74,  112,   74,   74,
   74,   40,   42,   41,   32,  229,   74,   32,   74,   76,
   76,  113,   76,   76,   76,   76,   76,   76,  170,   76,
   76,   76,   94,   76,   76,   76,   36,   37,   92,   36,
   37,   76,  191,   76,   77,   77,  192,   77,   77,   77,
   77,   77,   77,   53,   77,   77,   77,   91,   77,   77,
   77,  196,  104,  108,  115,   25,   77,  126,   77,   57,
   57,  105,   57,   57,   57,   57,   57,   57,   40,   57,
   57,   57,  151,   57,   57,   57,  116,   25,  199,  127,
  128,   57,  197,   57,   44,   44,   38,   44,   44,   44,
   44,   44,   44,  202,   44,   44,   44,  200,   44,   44,
   44,   40,   42,   41,  132,  201,   44,   39,   44,  203,
   45,   45,   38,   45,   45,   45,   45,   45,   45,  207,
   45,   45,   45,  211,   45,   45,   45,   40,   42,   41,
  212,  226,   45,  231,   45,  162,  232,   87,    7,  234,
   38,  233,  235,  236,    9,   10,  153,   11,   12,  237,
  238,   13,   14,   15,  240,   40,   42,   41,   88,   89,
   37,   17,  137,  137,  137,  108,  137,  239,  137,  129,
  129,  129,   32,  129,  247,  129,  214,  215,  216,  217,
   40,  137,  137,  137,  137,  137,  248,  137,  250,   33,
   34,   35,  251,  252,   36,   37,  258,   32,  259,  137,
  137,  137,  137,  260,  132,  132,  132,  132,  177,  175,
  261,  176,  262,  178,   33,   34,   35,  265,  266,   36,
   37,  267,  276,    6,    6,  281,    6,    6,  277,    6,
    6,    6,   98,    6,    6,    6,  278,    6,    6,    6,
  282,  280,  283,  113,  284,    6,  132,    6,  132,  132,
  132,  133,   50,  133,  133,  133,  285,   82,   80,   51,
   81,   52,   83,  241,  132,  132,  132,  132,  288,  133,
  133,  133,  133,  291,   40,   40,   42,   41,  292,  152,
  155,  148,  149,  293,  295,  108,  294,   40,  150,  296,
  297,  123,  298,    2,  167,  168,   84,   32,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   33,   34,   35,    0,    0,   36,
   37,    0,    0,   32,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   33,   34,   35,    0,    0,   36,   37,  208,    0,    0,
    0,   32,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   33,   34,
   35,    0,    0,   36,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   40,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  137,  108,  137,   40,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  137,  137,  137,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  270,    0,  270,   55,    7,
    0,  220,    2,    0,  221,    9,   10,    0,   11,   12,
    0,    0,   13,   14,   15,    0,    0,    0,    0,    0,
   16,    0,   17,    0,    0,    0,    0,    0,    0,    0,
    0,  132,    0,  132,    0,    0,  133,    0,  133,    0,
    0,   79,    0,    0,    0,    0,    0,    0,  132,  132,
  132,    0,    0,  133,  133,  133,    0,    0,   33,   34,
   35,  253,    7,    0,  254,    2,    0,  255,    9,   10,
    0,   11,   12,    0,    0,   13,   14,   15,    0,    0,
   55,    7,    0,   16,    2,   17,    0,    9,   10,    0,
   11,   12,   56,    0,   13,   14,   15,    0,    0,   55,
    7,  180,   16,    2,   17,    0,    9,   10,    0,   11,
   12,    0,    0,   13,   14,   15,    0,    6,    7,    0,
    0,   16,    8,   17,    9,   10,    0,   11,   12,    0,
    0,   13,   14,   15,    0,    6,    7,    0,    0,   16,
   66,   17,    9,   10,    0,   11,   12,    0,    0,   13,
   14,   15,    0,    0,   55,    7,    0,   16,    2,   17,
    0,    9,   10,    0,   11,   12,    0,    0,   13,   14,
   15,    0,    0,    0,    0,    0,   16,    0,   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    0,   41,   41,    3,   40,   59,   45,   41,  123,   12,
   59,  123,   99,  256,   40,   18,   85,  123,  256,   59,
   40,  256,  271,  272,  262,  268,  271,  272,  256,  278,
   41,    2,  260,   39,   41,   45,   44,   41,   42,   43,
   44,   45,   40,   47,  279,  123,   49,   18,  256,   45,
  278,   42,  260,   45,   40,  259,   47,   59,  262,   41,
   63,   45,   42,  259,  133,  134,  262,   47,  155,  256,
   73,   74,   75,   41,   42,   43,   44,   45,   49,   47,
   44,   60,   85,  259,   41,   45,  262,  123,   44,   49,
  279,   41,   42,   43,   54,   45,   41,   47,  104,   44,
  106,   44,  258,  276,   41,   44,   43,   41,   45,   41,
   44,  180,   72,  182,   41,  125,   43,   40,   45,   79,
   80,   81,   82,   83,   84,   41,   59,  244,   44,  246,
  133,  134,  278,   45,   41,   59,   45,  123,   41,   42,
   43,  256,   45,   40,   47,   41,   44,   59,  278,  218,
  256,   41,  278,  222,  223,  161,  162,  258,   41,   41,
   41,   59,  277,   59,   59,  277,   41,  278,  278,   59,
   41,   62,  175,  176,  177,  178,   62,  180,  256,  182,
   44,  125,   44,   44,   41,  254,   45,   40,   59,   44,
  125,   40,  278,  271,  272,   59,   41,   59,   59,   41,
  278,   59,   62,   59,   59,   42,   43,  256,   45,   41,
   47,  265,  172,   59,   62,  218,   59,  256,  258,  222,
  223,   59,  256,  257,  278,  259,  260,  261,  262,  263,
  264,  280,  266,  267,  268,   41,  270,  271,  272,  278,
  279,  277,  242,  243,  278,  245,  280,  247,  248,  256,
  257,  254,  259,  260,  261,  262,  263,  264,  278,  266,
  267,  268,   41,  270,  271,  272,   45,  278,  276,  279,
  256,  278,  276,  280,  256,  257,  278,  259,  260,  261,
  262,  263,  264,  279,  266,  267,  268,  279,  270,  271,
  272,  277,  271,  272,  278,  279,  278,   41,  280,  256,
  257,   45,  259,  260,  261,  262,  263,  264,  276,  266,
  267,  268,  276,  270,  271,  272,   60,   61,   62,  262,
  276,  278,   41,  280,  256,  257,   45,  259,  260,  261,
  262,  263,  264,  276,  266,  267,  268,  276,  270,  271,
  272,   60,   61,   62,  256,  278,  278,  256,  280,  256,
  257,   44,  259,  260,  261,  262,  263,  264,  256,  266,
  267,  268,  258,  270,  271,  272,  278,  279,  258,  278,
  279,  278,   62,  280,  256,  257,  278,  259,  260,  261,
  262,  263,  264,   60,  266,  267,  268,  258,  270,  271,
  272,   59,  256,   44,  256,  256,  278,  256,  280,  256,
  257,  256,  259,  260,  261,  262,  263,  264,   59,  266,
  267,  268,   62,  270,  271,  272,  278,  278,   59,  278,
  279,  278,   59,  280,  256,  257,   45,  259,  260,  261,
  262,  263,  264,   59,  266,  267,  268,  278,  270,  271,
  272,   60,   61,   62,   75,  278,  278,   40,  280,   41,
  256,  257,   45,  259,  260,  261,  262,  263,  264,   59,
  266,  267,  268,  278,  270,  271,  272,   60,   61,   62,
  278,   59,  278,  278,  280,   40,   59,  256,  257,  279,
   45,   44,   59,   59,  263,  264,  123,  266,  267,   59,
   41,  270,  271,  272,   41,   60,   61,   62,  277,  278,
  279,  280,   41,   42,   43,   44,   45,   59,   47,   41,
   42,   43,  256,   45,   41,   47,  175,  176,  177,  178,
   59,   41,   42,   43,   44,   45,   41,   47,   59,  273,
  274,  275,  262,   59,  278,  279,   59,  256,  262,   59,
   60,   61,   62,   59,  175,  176,  177,  178,   42,   43,
   59,   45,   59,   47,  273,  274,  275,   44,   59,  278,
  279,   59,  262,  256,  257,  262,  259,  260,   59,  262,
  263,  264,   51,  266,  267,  268,   59,  270,  271,  272,
  262,   59,   59,  276,  125,  278,   41,  280,   43,   44,
   45,   41,  269,   43,   44,   45,  279,   42,   43,  276,
   45,  278,   47,  278,   59,   60,   61,   62,   59,   59,
   60,   61,   62,   59,  265,   60,   61,   62,   59,  256,
   99,  271,  272,  125,  279,  276,  125,  278,  278,   59,
   59,   41,  125,    0,  113,  114,   43,  256,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  273,  274,  275,   -1,   -1,  278,
  279,   -1,   -1,  256,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,   -1,   -1,  278,  279,  166,   -1,   -1,
   -1,  256,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,
  275,   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  265,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  256,  276,  258,  278,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  244,   -1,  246,  256,  257,
   -1,  259,  260,   -1,  262,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,
  278,   -1,  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  256,   -1,  258,   -1,   -1,  256,   -1,  258,   -1,
   -1,  256,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,
  275,   -1,   -1,  273,  274,  275,   -1,   -1,  273,  274,
  275,  256,  257,   -1,  259,  260,   -1,  262,  263,  264,
   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,
  256,  257,   -1,  278,  260,  280,   -1,  263,  264,   -1,
  266,  267,  268,   -1,  270,  271,  272,   -1,   -1,  256,
  257,  258,  278,  260,  280,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,  256,  257,   -1,
   -1,  278,  261,  280,  263,  264,   -1,  266,  267,   -1,
   -1,  270,  271,  272,   -1,  256,  257,   -1,   -1,  278,
  261,  280,  263,  264,   -1,  266,  267,   -1,   -1,  270,
  271,  272,   -1,   -1,  256,  257,   -1,  278,  260,  280,
   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,   -1,   -1,  278,   -1,  280,
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

//#line 456 "gramatica.y"

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

//#line 755 "Parser.java"
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
//#line 85 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
break;
case 21:
//#line 89 "gramatica.y"
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
case 22:
//#line 108 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 23:
//#line 109 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}
break;
case 26:
//#line 115 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 28:
//#line 121 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }
break;
case 29:
//#line 126 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }
break;
case 30:
//#line 130 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 31:
//#line 134 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
    }
break;
case 33:
//#line 142 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 34:
//#line 145 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 35:
//#line 148 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
    }
break;
case 38:
//#line 158 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 39:
//#line 159 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 40:
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
case 43:
//#line 176 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 44:
//#line 177 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 45:
//#line 180 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 46:
//#line 184 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 47:
//#line 187 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 48:
//#line 191 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 49:
//#line 194 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 50:
//#line 198 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 51:
//#line 201 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 52:
//#line 204 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 53:
//#line 205 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 54:
//#line 206 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 55:
//#line 207 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 57:
//#line 214 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 58:
//#line 217 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 59:
//#line 220 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 60:
//#line 223 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 61:
//#line 224 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 64:
//#line 232 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 65:
//#line 235 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 66:
//#line 238 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 67:
//#line 239 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");}
break;
case 68:
//#line 242 "gramatica.y"
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
case 69:
//#line 262 "gramatica.y"
{
             String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (LONGINT)*/
            String tipoBase = val_peek(3).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -2147483647, 2147483647));
        }
break;
case 70:
//#line 270 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (DOUBLE)*/
            String tipoBase = val_peek(3).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
break;
case 71:
//#line 278 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaración de tipo.");
        }
break;
case 72:
//#line 281 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 73:
//#line 284 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 74:
//#line 287 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
break;
case 75:
//#line 290 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 76:
//#line 293 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 77:
//#line 294 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 78:
//#line 295 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 79:
//#line 296 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 80:
//#line 297 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 81:
//#line 298 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 82:
//#line 299 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 83:
//#line 300 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 84:
//#line 301 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 85:
//#line 303 "gramatica.y"
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
case 89:
//#line 328 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 90:
//#line 329 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 92:
//#line 335 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 93:
//#line 336 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 94:
//#line 337 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 101:
//#line 349 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 103:
//#line 351 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
break;
case 110:
//#line 363 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 111:
//#line 364 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 112:
//#line 365 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 113:
//#line 366 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 114:
//#line 371 "gramatica.y"
{
            
        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
        }
        
    }
break;
case 115:
//#line 380 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Solo se puede acceder a un par con 1 o 2");}
break;
case 116:
//#line 381 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 118:
//#line 385 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 119:
//#line 386 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 120:
//#line 387 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 122:
//#line 391 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida"); 
        }
break;
case 141:
//#line 417 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 142:
//#line 420 "gramatica.y"
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
//#line 1413 "Parser.java"
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
