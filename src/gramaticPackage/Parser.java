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
import Paquetecompi.TipoEtiqueta;    
import java.math.BigDecimal;
   

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

//#line 52 "Parser.java"




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
    0,    0,    0,    2,    2,    3,    3,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    5,
    5,    5,   15,   15,   15,    1,   16,   10,   10,   10,
   10,   10,   10,   17,   18,   18,   18,   19,   19,   14,
   14,   14,   20,   21,   22,   23,   24,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    7,   26,    8,    8,    8,    8,    8,    8,    9,
    9,    9,    9,    9,    9,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   27,   27,   27,   27,   27,   27,   25,
   25,   25,   25,   28,   28,   28,   28,   28,   28,    6,
    6,    6,   30,   30,   29,   29,   29,   29,   29,   29,
   29,   29,   29,   31,   31,   31,   11,   11,   11,   11,
   32,   32,   33,   34,   34,   34,   34,   34,   34,   34,
   34,   13,   13,   13,   13,   13,   13,   13,   13,   13,
   13,   35,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    5,    4,    4,    3,
    3,    2,    3,    1,    2,    1,    2,    6,    6,    6,
    7,    6,    7,    2,    3,    3,    0,    1,    1,    1,
    1,    1,    1,    1,    2,    2,    2,    7,    8,    8,
    6,    7,    6,    7,    7,    8,    7,    8,    5,    6,
    7,    8,    1,    7,    6,    5,    6,    5,    7,    5,
    5,    4,    4,    5,    4,    6,    7,    7,    6,    5,
    5,    5,    7,    6,    6,    6,    6,    6,    6,    5,
    5,    5,    5,    5,    6,    6,    7,    2,    1,    3,
    3,    2,    2,    1,    1,    1,    1,    1,    1,    4,
    4,    3,    1,    3,    3,    3,    1,    1,    3,    3,
    3,    1,    3,    4,    3,    2,    3,    2,    2,    2,
    4,    4,    1,    3,    3,    3,    3,    1,    1,    1,
    1,    3,    3,    3,    3,    1,    1,    1,    1,    1,
    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    5,    0,    0,    0,
   63,    0,   41,   40,    0,  122,   16,    0,    6,    8,
    9,   10,   11,   12,   13,   14,   15,    0,    0,    0,
    0,    0,    1,  151,  104,  105,  106,    0,  146,    0,
    0,  108,  109,  107,    0,    0,    0,  148,  149,  150,
    0,    0,    0,    0,    0,    0,  130,    0,  128,    0,
  126,    0,    4,    7,   27,    0,   22,    0,    0,   26,
    0,    0,    0,   38,   39,    0,    0,    0,    0,    0,
  152,    0,    0,    0,    0,    0,    0,    0,    0,   43,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   42,    0,    0,    0,    0,    0,
    0,    0,  127,    0,  120,    0,  125,    0,   21,   25,
   20,    0,    0,    0,    0,    0,    0,  112,    0,    0,
    0,  123,  116,    0,  119,    0,    0,  138,  140,    0,
    0,  141,    0,    0,    0,    0,    0,    0,  144,  145,
    0,    0,    0,   44,    0,    0,    0,   75,    0,    0,
    0,    0,    0,    0,    0,    0,   99,    0,    0,    0,
    0,    0,    0,    0,   19,    0,  124,    0,   23,    0,
    0,    0,    0,    0,    0,    0,    0,  110,  111,    0,
  132,  131,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   59,   47,    0,   70,   74,   71,   81,   80,
    0,    0,    0,    0,    0,    0,   98,   90,   92,    0,
   91,   93,    0,    0,   17,    0,    0,   34,    0,    0,
    0,    0,    0,   66,    0,    0,    0,   68,    0,    0,
    0,  136,  137,   53,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   60,    0,   88,    0,   89,    0,   79,
    0,    0,   76,   86,   87,    0,   32,   30,    0,    0,
   35,   29,   36,    0,   67,    0,   54,   55,    0,   57,
    0,   61,   48,    0,    0,    0,   77,   78,   83,    0,
    0,    0,   31,   33,   69,   64,   56,   58,   50,   62,
   49,   94,    0,    0,    0,   96,   95,    0,   97,
};
final static short yydgoto[] = {                          3,
    4,   74,   18,   75,   20,   21,   22,   23,   24,   25,
   26,   27,   45,   28,   69,   29,  182,  183,  152,   91,
  155,   92,   93,  156,   46,   30,  169,   47,   31,  130,
   48,   49,  140,  141,   50,
};
final static short yysindex[] = {                      -237,
  644,    0,    0, -214,    0,  490,    0,   30,  268,   77,
    0,  -59,    0,    0, -110,    0,    0,  664,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -52,  -31,  576,
  -40, -203,    0,    0,    0,    0,    0,  -18,    0, -207,
  450,    0,    0,    0,  111, -145,   54,    0,    0,    0,
  262,  -57, -191, -233, -150,  -24,    0,   25,    0, -155,
    0, -113,    0,    0,    0,    0,    0,   95,    5,    0,
 -191,  101,  107,    0,    0, -221,    7, -119, -123,   79,
    0, -145,  122,   54,   54,   54,   54,   54,   54,    0,
  688,  -98,  -85,   89,  127,  -34,    0,  117,  136,   61,
 -203, -100,  -89,  139,    0,  -99,  -91,  -99,  131,  133,
  143,   73,    0, -108,    0,   87,    0, -191,    0,    0,
    0,  -61,  -60, -191,  347,  176,  498,    0,   89,   68,
 -108,    0,    0, -108,    0,  178, -108,    0,    0,  179,
  149,    0,  -39,  -85,  600,   89,   44,   44,    0,    0,
   89,    0,  165,    0,  688,   55,  294,    0,  315,  324,
  340,  344,  362,  366,  370,  155,    0,  -15,  375,  -45,
  376,  377,  164,  171,    0,  394,    0,  180,    0,  416,
  -25,   53,   98,  418,  347,  461,  401,    0,    0,   54,
    0,    0,   81,   81,   81,   81,  402,  203, -111,  430,
 -200,  624,    0,    0,  409,    0,    0,    0,    0,    0,
  -51,  -49,  197,  420,  439,  205,    0,    0,    0,  426,
    0,    0,  427,  428,    0,  451, -214,    0, -214, -214,
 -191, -214, -191,    0,  472,  455,  479,    0,   89,   51,
   51,    0,    0,    0,  465,  468,  269,  477,  283,  488,
  489,  287, -174,    0,  494,    0,  495,    0,  497,    0,
    3,  513,    0,    0,    0, -214,    0,    0,  522,  307,
    0,    0,    0,  527,    0,  532,    0,    0,  537,    0,
  538,    0,    0,  543,  546,  547,    0,    0,    0,  482,
  330,   20,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  485,  486,  333,    0,    0,  491,    0,
};
final static short yyrindex[] = {                         0,
    0,    2,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  290,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   32,    0,    0,    0,    0,    0,  528,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -22,    0,    0,
    0,    0,    0,    0,    0,   -4,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   26,    0,
    0,    0,    0,   78,    0,  520,  -16,    0,    0,    0,
   16,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  108,    0,    0,    0,    0,   86,    0,
   33,    0,    0,   35,    0,    0,  141,    0,    0,    0,
  577,    0,    0,    0,    0,  103,  533,  539,    0,    0,
  120,  392,    0,    0,    0,    0,    9,    0,    0,   34,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   59,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   84,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  126,   28,
   66,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  109,    0,    0,    0,  134,    0,  159,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  184,    0,
    0,    0,    0,    0,    0,  210,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  237,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  100,    1,    0,   50,    0,    0,    0,    0,    0,    0,
    0,    0,  557,  561,    0,    0, -189,    0,  -12,  474,
  417,  -71,  -62, -107,   13,    0,  -74,  578,    0,    0,
  635,    0,    0,   14,   10,
};
final static int YYTABLESIZE=968;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         59,
    5,    2,  104,   78,   33,   80,   67,  256,   71,  258,
  143,  117,   62,  219,   62,  229,  111,   76,  129,  144,
   40,   80,    1,  168,  146,  146,  146,  122,  146,  216,
  146,  168,  171,  172,  126,   26,  198,   13,   14,   24,
    2,  271,  107,  273,  105,    1,  127,  291,  122,   73,
   19,   40,   79,   83,   24,  250,  148,  148,  148,  118,
  148,  251,  148,  121,  305,  128,  102,   64,  134,   51,
  134,   81,  134,  201,   72,  118,  115,  168,  121,   13,
   14,  285,  202,  113,  102,   87,  105,  286,   62,  142,
   88,  247,  195,  230,  253,  220,  231,  196,   40,   18,
   99,  160,   87,   85,   62,   86,  135,   88,  135,  217,
  135,  190,   90,  176,   87,   85,   56,   86,  103,   88,
  109,  110,  114,   40,   82,   40,  189,   68,   72,  113,
   87,   85,  199,   86,  118,   88,  103,  184,  232,  187,
  124,  233,  204,  101,  113,   60,  125,  154,   37,   51,
  246,   37,   87,   85,  134,   86,  167,   88,  131,  132,
  100,  101,  145,  153,  167,  116,   61,  157,   61,  114,
   42,   44,   43,  154,   84,  158,  159,  161,  100,   13,
   14,  139,  139,  139,  114,  139,  105,  139,  162,  252,
  195,  193,  173,  194,  174,  196,   57,  235,  237,   85,
  166,  175,  142,  142,  142,  142,  240,  241,  242,  243,
  167,  177,   65,  102,  103,  185,  179,  180,  191,  192,
   58,   60,  197,  203,   28,   66,  255,  267,  257,  268,
  269,   34,  272,  129,  129,   77,  129,  129,  129,  129,
  129,  129,   61,  129,  129,  129,   70,  129,  129,  129,
   65,   24,  228,   38,   39,  129,  129,  129,   61,  122,
  119,   26,   34,  215,   73,   73,  293,   73,   73,   73,
   73,   73,   73,   24,   73,   73,   73,   52,   73,   73,
   73,  290,  120,  102,   38,   39,   73,   73,   73,   72,
   72,  118,   72,   72,   72,   72,   72,   72,  304,   72,
   72,   72,   98,   72,   72,   72,   40,  118,  115,   34,
  121,   72,   72,   72,   18,   18,  205,   18,   18,   18,
   18,   18,   18,  188,   18,   18,   18,   55,   18,   18,
   18,   38,   39,  117,  136,  103,   18,   18,   18,   82,
   82,  113,   82,   82,   82,   82,   82,   82,   42,   82,
   82,   82,  206,   82,   82,   82,  137,  138,  137,  138,
  101,   82,   82,   82,   51,   51,   84,   51,   51,   51,
   51,   51,   51,  207,   51,   51,   51,  100,   51,   51,
   51,  114,  208,   35,   36,   37,   51,   51,   51,   84,
   84,   40,   84,   84,   84,   84,   84,   84,  209,   84,
   84,   84,  210,   84,   84,   84,   42,   44,   43,  163,
  164,   84,   84,   84,   85,   85,  165,   85,   85,   85,
   85,   85,   85,  211,   85,   85,   85,  212,   85,   85,
   85,  213,  214,  218,  221,  222,   85,   85,   85,   28,
   28,  223,   28,   28,   28,   28,   28,   28,  224,   28,
   28,   28,  225,   28,   28,   28,  227,  226,  234,  238,
  244,   28,   28,   28,  245,   65,   65,  254,   65,   65,
   65,   65,   65,   65,  259,   65,   65,   65,  260,   65,
   65,   65,  261,  262,  263,  264,  265,   65,   65,   65,
   82,  266,   52,   52,   40,   52,   52,   52,   52,   52,
   52,  236,   52,   52,   52,   40,   52,   52,   52,   42,
   44,   43,  274,  275,   52,   52,   52,   34,    6,  276,
   42,   44,   43,  277,    8,    9,  278,   10,   11,   41,
  279,   12,   13,   14,   40,  280,   52,  186,   95,   96,
   97,   17,   40,   53,  281,   54,  282,  283,  284,   42,
   44,   43,  287,  288,   42,  289,  292,   42,   44,   43,
  147,  147,  147,  117,  147,  117,  147,   42,  147,  147,
  147,  147,  147,  142,  147,  142,  142,  142,   42,  143,
  294,  143,  143,  143,  228,  295,  147,  147,  147,  147,
  296,  142,  142,  142,  142,  297,  298,  143,  143,  143,
  143,  299,   34,   94,  300,  301,  302,  100,  303,  306,
  307,  308,  112,  106,  108,  309,  249,  133,  200,   35,
   36,   37,   89,    0,   38,   39,    0,    0,    0,    0,
    0,  123,    0,  129,    0,   32,    0,    0,    0,    0,
  146,  147,  148,  149,  150,  151,    0,   45,   46,    0,
   46,   46,   32,   45,   46,   46,    0,   46,   46,    0,
    0,   46,   46,   46,   32,    0,    0,  170,    0,   46,
   46,   46,    0,    0,    0,    0,    0,    0,  178,    0,
    0,    0,    0,    0,  181,  101,    6,    0,  154,    1,
    0,  248,    8,    9,  115,   10,   11,    0,    0,   12,
   13,   14,    0,    0,    0,   34,    0,   15,   16,   17,
    0,    0,  133,  135,  139,    0,   34,    0,    0,    0,
    0,    0,   35,   36,   37,   32,    0,   38,   39,    0,
    0,    0,    0,   35,   36,   37,    0,    0,   38,   39,
    0,    0,    0,    0,    0,   34,  239,    0,    0,    0,
    0,    0,    0,   34,    0,    0,    0,    0,    0,    0,
    0,    0,   35,   36,   37,    0,    0,   38,   39,    0,
   35,   36,   37,    0,    0,   38,   39,    0,    0,   32,
    0,    0,    0,  147,   42,  147,    0,    0,  142,   32,
  142,  270,    0,  270,  143,  117,  143,   42,    0,    0,
  147,  147,  147,    0,    0,  142,  142,  142,    0,    0,
    0,  143,  143,  143,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  139,  139,  139,
  139,    0,    6,    0,   32,    1,   32,    0,    8,    9,
    0,   10,   11,   73,    0,   12,   13,   14,    0,    0,
    0,    0,    0,   15,   16,   17,    6,   90,    0,    1,
    0,    0,    8,    9,    0,   10,   11,    0,    0,   12,
   13,   14,    0,    0,    0,    0,    0,   15,   16,   17,
    6,    0,  154,    1,    0,    0,    8,    9,    0,   10,
   11,    0,    0,   12,   13,   14,    0,    0,    0,    0,
    6,   15,   16,   17,    7,    0,    8,    9,    0,   10,
   11,    0,    0,   12,   13,   14,    0,    0,    0,    0,
    6,   15,   16,   17,   63,    0,    8,    9,    0,   10,
   11,    0,    0,   12,   13,   14,    0,    0,    0,    0,
    0,   15,   16,   17,    6,    0,    0,    1,    0,    0,
    8,    9,    0,   10,   11,    0,    0,   12,   13,   14,
    0,    0,    0,    0,    0,   15,   16,   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         59,
    0,    0,   60,   44,    4,   40,   59,   59,   40,   59,
   82,  125,  123,   59,  123,   41,   41,   30,   41,   82,
   45,   40,  260,  123,   41,   42,   43,   44,   45,   45,
   47,  123,  107,  108,  256,   40,  144,  271,  272,   44,
  278,  231,  276,  233,  278,  260,  268,   45,   44,   41,
    1,   45,  256,   41,   59,  256,   41,   42,   43,   44,
   45,  262,   47,   59,   45,   59,   41,   18,   41,   40,
   43,  279,   45,  145,   41,   44,   44,  123,   44,  271,
  272,  256,  145,   59,   59,   42,  278,  262,  123,   80,
   47,  199,   42,   41,  202,  170,   44,   47,   45,   41,
   51,   41,   42,   43,  123,   45,   41,   47,   43,  125,
   45,   44,  258,   41,   42,   43,   40,   45,   41,   47,
  271,  272,  278,   45,   41,   45,   59,   28,   29,   44,
   42,   43,  145,   45,   40,   47,   59,  125,   41,  127,
   40,   44,  155,   41,   59,  256,   40,  259,   41,   41,
  262,   44,   42,   43,  278,   45,  256,   47,  278,  279,
   41,   59,   41,  262,  256,  279,  277,   41,  277,   44,
   60,   61,   62,  259,   41,   59,   41,  278,   59,  271,
  272,   41,   42,   43,   59,   45,  278,   47,  278,  202,
   42,   43,   62,   45,   62,   47,  256,  185,  186,   41,
   62,   59,  193,  194,  195,  196,  193,  194,  195,  196,
  256,  125,  265,  271,  272,   40,  278,  278,   41,   41,
  280,  256,  262,   59,   41,  278,  278,  227,  278,  229,
  230,  256,  232,  256,  257,  276,  259,  260,  261,  262,
  263,  264,  277,  266,  267,  268,  278,  270,  271,  272,
   41,  256,  278,  278,  279,  278,  279,  280,  277,  276,
  256,  260,  256,  279,  256,  257,  266,  259,  260,  261,
  262,  263,  264,  278,  266,  267,  268,   41,  270,  271,
  272,  279,  278,  258,  278,  279,  278,  279,  280,  256,
  257,  276,  259,  260,  261,  262,  263,  264,  279,  266,
  267,  268,   41,  270,  271,  272,   45,  276,  276,  256,
  276,  278,  279,  280,  256,  257,  262,  259,  260,  261,
  262,  263,  264,  256,  266,  267,  268,   60,  270,  271,
  272,  278,  279,   44,  256,  258,  278,  279,  280,  256,
  257,  256,  259,  260,  261,  262,  263,  264,   59,  266,
  267,  268,   59,  270,  271,  272,  278,  279,  278,  279,
  258,  278,  279,  280,  256,  257,  256,  259,  260,  261,
  262,  263,  264,   59,  266,  267,  268,  258,  270,  271,
  272,  256,   59,  273,  274,  275,  278,  279,  280,  256,
  257,   45,  259,  260,  261,  262,  263,  264,   59,  266,
  267,  268,   59,  270,  271,  272,   60,   61,   62,  271,
  272,  278,  279,  280,  256,  257,  278,  259,  260,  261,
  262,  263,  264,   62,  266,  267,  268,   62,  270,  271,
  272,   62,  278,   59,   59,   59,  278,  279,  280,  256,
  257,  278,  259,  260,  261,  262,  263,  264,  278,  266,
  267,  268,   59,  270,  271,  272,   41,  278,   41,   59,
   59,  278,  279,  280,  262,  256,  257,   59,  259,  260,
  261,  262,  263,  264,  278,  266,  267,  268,   59,  270,
  271,  272,   44,  279,   59,   59,   59,  278,  279,  280,
   41,   41,  256,  257,   45,  259,  260,  261,  262,  263,
  264,   41,  266,  267,  268,   45,  270,  271,  272,   60,
   61,   62,   41,   59,  278,  279,  280,  256,  257,   41,
   60,   61,   62,   59,  263,  264,   59,  266,  267,   40,
  262,  270,  271,  272,   45,   59,  269,   40,  277,  278,
  279,  280,   45,  276,  262,  278,   59,   59,  262,   60,
   61,   62,   59,   59,  265,   59,   44,   60,   61,   62,
   41,   42,   43,   44,   45,  276,   47,  278,   41,   42,
   43,   44,   45,   41,   47,   43,   44,   45,   59,   41,
   59,   43,   44,   45,  278,   59,   59,   60,   61,   62,
   59,   59,   60,   61,   62,   59,   59,   59,   60,   61,
   62,   59,  256,   47,   59,   59,  125,   51,  279,  125,
  125,  279,   56,   53,   54,  125,  200,   41,  145,  273,
  274,  275,   45,   -1,  278,  279,   -1,   -1,   -1,   -1,
   -1,   71,   -1,   77,   -1,    1,   -1,   -1,   -1,   -1,
   84,   85,   86,   87,   88,   89,   -1,  256,  257,   -1,
  259,  260,   18,  262,  263,  264,   -1,  266,  267,   -1,
   -1,  270,  271,  272,   30,   -1,   -1,  107,   -1,  278,
  279,  280,   -1,   -1,   -1,   -1,   -1,   -1,  118,   -1,
   -1,   -1,   -1,   -1,  124,   51,  257,   -1,  259,  260,
   -1,  262,  263,  264,   60,  266,  267,   -1,   -1,  270,
  271,  272,   -1,   -1,   -1,  256,   -1,  278,  279,  280,
   -1,   -1,   78,   79,   80,   -1,  256,   -1,   -1,   -1,
   -1,   -1,  273,  274,  275,   91,   -1,  278,  279,   -1,
   -1,   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,
   -1,   -1,   -1,   -1,   -1,  256,  190,   -1,   -1,   -1,
   -1,   -1,   -1,  256,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,   -1,
  273,  274,  275,   -1,   -1,  278,  279,   -1,   -1,  145,
   -1,   -1,   -1,  256,  265,  258,   -1,   -1,  256,  155,
  258,  231,   -1,  233,  256,  276,  258,  278,   -1,   -1,
  273,  274,  275,   -1,   -1,  273,  274,  275,   -1,   -1,
   -1,  273,  274,  275,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  193,  194,  195,
  196,   -1,  257,   -1,  200,  260,  202,   -1,  263,  264,
   -1,  266,  267,  268,   -1,  270,  271,  272,   -1,   -1,
   -1,   -1,   -1,  278,  279,  280,  257,  258,   -1,  260,
   -1,   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,
  271,  272,   -1,   -1,   -1,   -1,   -1,  278,  279,  280,
  257,   -1,  259,  260,   -1,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
  257,  278,  279,  280,  261,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
  257,  278,  279,  280,  261,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
   -1,  278,  279,  280,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,   -1,   -1,   -1,  278,  279,  280,
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
"programa : nombre bloque_sentencias",
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
"nombre : T_ID",
"encabezado_funcion : tipo FUN",
"declaracion_funcion : encabezado_funcion nombre '(' parametro ')' bloque_sentencias",
"declaracion_funcion : encabezado_funcion nombre '(' parametros_error ')' bloque_sentencias",
"declaracion_funcion : encabezado_funcion nombre '(' tipo ')' bloque_sentencias",
"declaracion_funcion : tipo nombre '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : encabezado_funcion '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : encabezado_funcion nombre '(' parametro ')' bloque_sentencias ';'",
"parametro : tipo T_ID",
"parametros_error : parametro ',' parametro",
"parametros_error : parametros_error ',' parametro",
"parametros_error :",
"repeat_sentencia : bloque_sentencias",
"repeat_sentencia : sentencia",
"tipo : DOUBLE",
"tipo : LONGINT",
"tipo : T_ID",
"signo_THEN : THEN",
"signo_ELSE : ELSE",
"bloque_THEN : signo_THEN repeat_sentencia",
"bloque_THEN_CON_ELSE : signo_THEN repeat_sentencia",
"bloque_ELSE : signo_ELSE repeat_sentencia",
"if_statement : IF '(' condicion ')' bloque_THEN END_IF ';'",
"if_statement : IF '(' condicion ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF ';'",
"if_statement : IF '(' condicion ')' bloque_THEN_CON_ELSE repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' bloque_THEN END_IF",
"if_statement : IF '(' condicion ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF",
"if_statement : IF '(' ')' bloque_THEN END_IF ';'",
"if_statement : IF '(' ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF ';'",
"if_statement : IF '(' condicion ')' repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' repeat_sentencia bloque_ELSE END_IF ';'",
"if_statement : IF '(' condicion ')' signo_THEN END_IF ';'",
"if_statement : IF '(' condicion ')' signo_THEN signo_ELSE END_IF ';'",
"if_statement : IF condicion bloque_THEN END_IF ';'",
"if_statement : IF condicion bloque_THEN_CON_ELSE bloque_ELSE END_IF ';'",
"if_statement : IF '(' condicion ')' bloque_THEN error ';'",
"if_statement : IF '(' condicion ')' bloque_THEN_CON_ELSE bloque_ELSE error ';'",
"inicio_while : REPEAT",
"repeat_while_statement : inicio_while repeat_sentencia WHILE '(' condicion ')' ';'",
"repeat_while_statement : inicio_while repeat_sentencia WHILE '(' condicion ')'",
"repeat_while_statement : inicio_while WHILE '(' condicion ')'",
"repeat_while_statement : inicio_while repeat_sentencia WHILE '(' ')' ';'",
"repeat_while_statement : inicio_while repeat_sentencia WHILE condicion ';'",
"repeat_while_statement : inicio_while repeat_sentencia error '(' condicion ')' ';'",
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
"IDENTIFIER_LIST : T_CTE",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_CTE",
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

//#line 1044 "gramatica.y"

public static boolean crearEjecutable=true;
private ReturnChecker returnChecker = new ReturnChecker();
private int nivel = 0;
private boolean dentroFuncion = false;

public void yyerror(String s) {
    SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " String: " +s);
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
public void chequeoPares(String variable, String expresion){
    if (st.getUse(variable) == null && st.getUse(expresion) == null) {
        SymbolTable.aggListaErrores("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("Las variables: "+ variable + " " + expresion + " nunca fueron declaradas");

    }
    else
    if (st.getUse(variable) == null) {
        SymbolTable.aggListaErrores("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("La variable: "+ variable + " nunca fue declarada");

    } else if (st.getUse(expresion) == null) {
        SymbolTable.aggListaErrores("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("La variable: "+ expresion + " nunca fue declarada");

    }

    else 
    if((st.getUse(variable).equals("Nombre de variable par") &&!st.getUse(expresion).equals("Nombre de variable par"))
    ||(!st.getUse(variable).equals("Nombre de variable par") &&st.getUse(expresion).equals("Nombre de variable par"))){
        System.out.println("Warning: No se pueden utilizar los tipos pares en operaciones que conlleven tipos distintos ");        
        SymbolTable.aggListaErrores("Error en asignacion: "+variable + " := " + expresion + ";");
    }else{
        System.out.println(variable + " := " + expresion + ";");
    }
}
public boolean isPair(String variable){
    if (st.getUse(variable)!=null)
        if(st.getUse(variable).equals("Nombre de variable par")){
            return true;
        }
        return false;
}
public String borrarUltimoAmbito(){
    String originalString = SymbolTable.ambitoGlobal.toString();

    // Separar por ":"
    String[] partes = originalString.split(":");

    // Crear un nuevo StringBuilder con todas las partes excepto la última
    StringBuilder nuevoStringBuilder = new StringBuilder();
    for (int i = 0; i < partes.length - 1; i++) {
        nuevoStringBuilder.append(partes[i]);
        if (i < partes.length - 2) {
            nuevoStringBuilder.append(":"); // Volver a agregar los separadores ":"
        }
    }
    return nuevoStringBuilder.toString();
}
public int getTypeOfConst(String constValue){
    // Verificar si es Octal: empieza con 0 y no contiene 8 ni 9
    if (constValue.startsWith("0") && constValue.matches("[0-7]+")) {
        return 0; // Es Octal
    }
    
    // Verificar si es Double: contiene una 'd' o un signo '+' o '-'
    if (constValue.toLowerCase().contains("d") || constValue.contains("+") || constValue.contains("-")) {
        return 1; // Es Double
    }
    
    // Si no es Octal ni Double, asumimos que es Longint
    return 2; // Es Longint
}

String getStringByType(String constante){
    int tipo = getTypeOfConst(constante);
    String valorString="";
    switch (tipo) {
        case 0:{ /* Octal*/

        
            Double valor = SymbolTable.conversionesRangos.get(constante);
            if (valor != null) {
                    long valorOctal = valor.longValue();
                    valorString = "0" + Long.toOctalString(valorOctal); // Convertimos a octal y lo representamos como cadena.

            }else{

                valorString = constante;
            }
            break;
        }    

        case 1:{ /* Double*/

            // Guardamos el valor original de `constante` sin reemplazar 'd' o 'D'
            String valorOriginal = constante;

            // Reemplazamos 'd' o 'D' con 'E' para convertirlo al formato científico para `BigDecimal`
            
            // Buscamos el valor en `SymbolTable.conversionesRangos` usando el valor en formato `Double`
            Double valor = SymbolTable.conversionesRangos.get(valorOriginal);
            
            if (valor != null) {
                // Si encontramos el valor en el rango de conversiones, lo usamos
                String valorModificado=valor.toString().replace("E","d");
                valorString = valorModificado;
            } else {
                // Si no lo encontramos, usamos la constante original
                valorString = valorOriginal;
            }
            break;
        }
        case 2:{ /* Longint*/

            try {
                
                
                Double valor = SymbolTable.conversionesRangos.get(constante);
                
                if (valor != null) {
                    long valorLong = valor.longValue();
                
                    String valorCambiado = Long.toString(valorLong);
                    
                    valorString = valorCambiado;
                } else {
                    valorString = constante;
                }
            } catch (NumberFormatException e) {
                SymbolTable.aggListaErrores("Error: El valor no es un número Longint válido - " + constante);
                valorString = constante; // En caso de error, usamos la constante original
            }
            break;
        }    
        default:
            SymbolTable.aggListaErrores("Tipo de constante desconocido.");
    }
    return valorString;
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
    st.imprimirTablaFunciones();
    }

//#line 917 "Parser.java"
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
//#line 48 "gramatica.y"
{
    SymbolTable.aggPolaca(val_peek(1).sval+"%");
    if (SymbolTable.errores.isEmpty())
        System.out.println("Programa compilado correctamente");
    else {System.err.println("No se puede crear el ejecutable");
            SymbolTable.imprimirErrores();}
    /*updatear uso nombre funcion*/
    st.updateUse(val_peek(1).sval, "Nombre de programa");
    if(st.containsUnsignedGoto()){/*INCORPORAR LISTA DE ERRORES*/
        Parser.crearEjecutable=false;
        if (SymbolTable.errores.isEmpty())
            System.err.println("No se puede crear el ejecutable"); 

    }
    
}
break;
case 2:
//#line 64 "gramatica.y"
{ 
    SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
}
break;
case 3:
//#line 67 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}
break;
case 5:
//#line 72 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 16:
//#line 87 "gramatica.y"
{
            
            if(st.containsTypeGotos(new TipoEtiqueta(val_peek(0).sval,null,null))){
                int posicion = st.popFirstOccurrenceByNameGotos(val_peek(0).sval);
                if(posicion != -1){
                    SymbolTable.polaca.set(posicion,String.valueOf(SymbolTable.polaca.size()+1));
                }
            }else{
                st.aggPilaEtiquetas(new TipoEtiqueta(val_peek(0).sval,SymbolTable.polaca.size(),SymbolTable.ambitoGlobal.toString()));
            }
            SymbolTable.aggPolaca(val_peek(0).sval);

            if(st.contieneSymbolAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal)){
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
            }else{
                if(st.getAmbitoByKey(val_peek(0).sval).equals(" ")){
                    st.updateAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(val_peek(0).sval,"String","Nombre de variable",SymbolTable.ambitoGlobal.toString(), 280);
                }
            }
         }
break;
case 17:
//#line 109 "gramatica.y"
{
            if (!dentroFuncion) SymbolTable.aggListaErrores("Error en linea: "+ Lexer.nmrLinea + " - No se puede usar ret fuera de función");
            else {SymbolTable.aggPolaca("RET"); returnChecker.registerReturn();}
            }
break;
case 18:
//#line 113 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 19:
//#line 114 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
break;
case 20:
//#line 118 "gramatica.y"
{ 
    List<String> variables = (List<String>) val_peek(1).obj;  /* Obtener la lista de variables de lista_var*/
    System.out.println("vars:"+variables);
	for (String variable : variables) {
	    /* Verificar si la variable ya existe en la tabla de símbolos */
	    if (st.hasKey(variable)) {
	        System.out.println("Aclaracion, se declaro la variable: " + variable);
            
            /*updatear uso de variable a variable*/
            if(st.isTypePair(val_peek(2).sval)){/*si el tipo*/
                st.updateUse(variable, "Nombre de variable par");
            }else{
	            st.updateUse(variable, "Nombre de variable");
            }


            if(st.contieneSymbolAmbito(variable,SymbolTable.ambitoGlobal)){
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
            }else{
                if(st.getAmbitoByKey(variable).equals(" ")){
                    st.updateAmbito(variable,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(variable,val_peek(2).sval,"Nombre de variable",SymbolTable.ambitoGlobal.toString(), 278);
                }
            }
            /*updatear tipo de variable*/
            st.updateType(variable,SymbolTable.ambitoGlobal.toString(), val_peek(2).sval);
            
	    } else {
	        SymbolTable.aggListaErrores("Error, la variable no está en la tabla de símbolos: " + variable);
	    }
	}
}
break;
case 21:
//#line 150 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 22:
//#line 151 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}
break;
case 23:
//#line 155 "gramatica.y"
{
    
    @SuppressWarnings("unchecked")
    List<String> variables = (List<String>) val_peek(2).obj;
    variables.add(val_peek(0).sval);  /* Agregar nueva variable*/
    yyval.obj = variables;  /* Pasar la lista actualizada hacia arriba */
}
break;
case 24:
//#line 162 "gramatica.y"
{
    List<String> variables = new ArrayList<String>();
    variables.add(val_peek(0).sval);  /* Agregar la primera variable*/
    yyval.obj = variables; 
}
break;
case 25:
//#line 167 "gramatica.y"
{ SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 26:
//#line 171 "gramatica.y"
{ yyval.sval = val_peek(0).sval;

    System.out.println("Entre a Funcion antes (o despues?) de la derecha");
    SymbolTable.aggPolaca(val_peek(0).sval+"$");
    if (SymbolTable.ambitoGlobal.length() == 0) {
        SymbolTable.ambitoGlobal = new StringBuilder(val_peek(0).sval);
    } else SymbolTable.ambitoGlobal.append(":" + val_peek(0).sval);
        }
break;
case 27:
//#line 181 "gramatica.y"
{ dentroFuncion = true; returnChecker.enterFunction();}
break;
case 28:
//#line 182 "gramatica.y"
{
        
        System.out.println("Entre a la 2da llave");
        /*updatear uso nombre funcion*/
        st.updateUse(val_peek(4).sval, "Nombre de funcion");
        

        /* Separar el tipo y el nombre del parámetro*/
        String[] tipoYNombre = val_peek(2).sval.split(":");
        String tipoParametro = tipoYNombre[0];
        String nombreParametro = tipoYNombre[1];

        if(st.contieneSymbolAmbito(val_peek(4).sval,SymbolTable.ambitoGlobal)){
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar funciones en el mismo ambito. Error con el nombre de la funcion:"+val_peek(4).sval);
        }else{
            if(st.getAmbitoByKey(val_peek(4).sval).equals(" ")){
                StringBuilder ambitoOrig= new StringBuilder(borrarUltimoAmbito());
                st.updateAmbito(val_peek(4).sval,ambitoOrig);
            }else{
                st.addValue(val_peek(4).sval,"String","Nombre de funcion",SymbolTable.ambitoGlobal.toString(), 278);
            }
            /* Insertar en la tabla de funciones*/
            st.insertTF(val_peek(4).sval+":"+this.borrarUltimoAmbito(), new CaracteristicaFuncion(val_peek(6).sval, tipoParametro, nombreParametro)); 
        }
        
        /* Encuentra el índice donde empieza "Gato"*/
        int inicio = st.ambitoGlobal.indexOf(":" + val_peek(4).sval);

        /* Si la palabra a borrar existe en el StringBuilder, elimínala*/
        if (inicio != -1) {
            st.ambitoGlobal.delete(inicio, inicio + val_peek(4).sval.length()+1);
        }
        SymbolTable.aggPolaca(val_peek(4).sval+"%");
        returnChecker.exitFunction();
        dentroFuncion = false;

    }
break;
case 29:
//#line 219 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }
break;
case 30:
//#line 224 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }
break;
case 31:
//#line 228 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 32:
//#line 232 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    }
break;
case 33:
//#line 235 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - No se puede poner ; al final de la declaracion de una fucnion");
    }
break;
case 34:
//#line 240 "gramatica.y"
{
        
        /*updatear uso de variable a variable*/
        if(st.isTypePair(val_peek(1).sval)){/*si el tipo*/
            st.updateUse(val_peek(0).sval, "Nombre de variable par");
        }else{
            st.updateUse(val_peek(0).sval, "Nombre de parametro");
        }
        if(st.contieneSymbolAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal)){
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
        }else{
            if(st.getAmbitoByKey(val_peek(0).sval).equals(" ")){
                st.updateAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal);
            }else{
                st.addValue(val_peek(0).sval,val_peek(1).sval,"Nombre de parametro",SymbolTable.ambitoGlobal.toString(), 278);
            }
        }
        yyval.sval = val_peek(1).sval + ":" + val_peek(0).sval;
        
    }
break;
case 35:
//#line 262 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 36:
//#line 265 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 37:
//#line 268 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - La funcion debe tener un parametro.");
    }
break;
case 40:
//#line 278 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 41:
//#line 279 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 42:
//#line 281 "gramatica.y"
{  
        /* Verificando si el tipo esta en la tabla de tipos definidos*/
        
        if (st.containsKeyTT(val_peek(0).sval+":"+SymbolTable.ambitoGlobal.toString())) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0).sval);
        } 
    }
break;
case 43:
//#line 291 "gramatica.y"
{
    if (dentroFuncion)
            returnChecker.enterBlock();
}
break;
case 44:
//#line 296 "gramatica.y"
{
    if (dentroFuncion)
            returnChecker.enterBlock();
}
break;
case 45:
//#line 301 "gramatica.y"
{
    
        int posicion = SymbolTable.pila.pop();
        SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));
        SymbolTable.pila.push(SymbolTable.polaca.size());
        if (dentroFuncion)
            returnChecker.exitBlock();

}
break;
case 46:
//#line 312 "gramatica.y"
{
    
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()+2));
    SymbolTable.pila.push(SymbolTable.polaca.size());
    SymbolTable.aggPolaca(""); SymbolTable.aggPolaca("BI");
    if (dentroFuncion)
        returnChecker.exitBlock();

}
break;
case 47:
//#line 322 "gramatica.y"
{
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));
    if (dentroFuncion) {
        returnChecker.exitBlock();
        
    }
}
break;
case 50:
//#line 335 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 51:
//#line 336 "gramatica.y"
{
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 52:
//#line 339 "gramatica.y"
{
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 53:
//#line 343 "gramatica.y"
{
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 54:
//#line 346 "gramatica.y"
{
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 55:
//#line 350 "gramatica.y"
{
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 56:
//#line 353 "gramatica.y"
{
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 57:
//#line 357 "gramatica.y"
{
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 58:
//#line 360 "gramatica.y"
{
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 59:
//#line 363 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 60:
//#line 364 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 61:
//#line 365 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 62:
//#line 366 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 63:
//#line 371 "gramatica.y"
{SymbolTable.pila.push(SymbolTable.polaca.size());
                      if (dentroFuncion)
                        returnChecker.enterBlock();
                    }
break;
case 64:
//#line 376 "gramatica.y"
{
    SymbolTable.aggPolaca(""); SymbolTable.aggPolaca("BI");
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));
    SymbolTable.polaca.set(SymbolTable.polaca.size()-2, String.valueOf(SymbolTable.pila.pop()));
    if (dentroFuncion) {
        returnChecker.exitBlock();
    }
    }
break;
case 65:
//#line 385 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 66:
//#line 388 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
break;
case 67:
//#line 391 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 68:
//#line 394 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 69:
//#line 395 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 70:
//#line 401 "gramatica.y"
{         SymbolTable.aggPolaca(val_peek(2).sval);
                                            SymbolTable.aggPolaca("OUTF");}
break;
case 71:
//#line 403 "gramatica.y"
{ SymbolTable.aggPolaca("OUTF");}
break;
case 72:
//#line 404 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 73:
//#line 407 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 74:
//#line 410 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
break;
case 75:
//#line 411 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta contenido en el OUTF");}
break;
case 76:
//#line 416 "gramatica.y"
{ 

        System.out.println("2do");
        /* Obtener el nombre del tipo desde T_ID*/
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        String tipoBase = val_peek(2).sval;
        
        Subrango subrango = (Subrango) val_peek(1).obj;
        double limiteInferior, limiteSuperior;
        if (subrango != null){ 
            limiteInferior = subrango.getLimiteInferior(); /* Limite inferior */
            limiteSuperior = subrango.getLimiteSuperior(); /* Limite superior */
        } else {limiteInferior = 0; /* Limite inferior */
            limiteSuperior = 0;}
        /* Almacenar en la tabla de tipos*/

        if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(4).sval);
        }else{

                /*FALTA CHEQUEAR MISMO TIPO*/
            if (tipoBase.toLowerCase().equals("longint")){
                long limiteInferiorLong = (long) limiteInferior; /* Convertir a longint*/
                long limiteSuperiorLong = (long) limiteSuperior; /* Convertir a longint*/
                this.st.insertTT(nombreTipo+":"+SymbolTable.ambitoGlobal.toString(), new TipoSubrango(tipoBase, limiteInferiorLong, limiteSuperiorLong));

            } else this.st.insertTT(nombreTipo+":"+SymbolTable.ambitoGlobal.toString(), new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
            /*updatear uso*/
            st.updateUse(nombreTipo, "Nombre de tipo");

            if(st.getAmbitoByKey(nombreTipo).equals(" ")){
                st.updateAmbito(nombreTipo,SymbolTable.ambitoGlobal);
            }else{
                st.addValue(nombreTipo,"String","Nombre de tipo",SymbolTable.ambitoGlobal.toString(), 278);
            }
        }

        }
break;
case 77:
//#line 455 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/



            if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(5).sval);
            }else{
    
                /*FALTA CHEQUEAR MISMO TIPO*/
                st.insertTT(nombreTipo+":"+SymbolTable.ambitoGlobal.toString(), new TipoSubrango("longint", -2147483647, 2147483647));

            
                /*updatear uso*/
                st.updateUse(nombreTipo, "Nombre de tipo de par");
    
                if(st.getAmbitoByKey(nombreTipo).equals(" ")){
                    st.updateAmbito(nombreTipo,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(nombreTipo,"String","Nombre de tipo de par",SymbolTable.ambitoGlobal.toString(), 278);
                }
            }

        }
break;
case 78:
//#line 479 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/
            
            

            if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(5).sval);
            }else{
    
                /*FALTA CHEQUEAR MISMO TIPO*/
                st.insertTT(nombreTipo+":"+SymbolTable.ambitoGlobal.toString(), new TipoSubrango("double", -1.7976931348623157E+308, 1.7976931348623157E+308));	
                
            
                /*updatear uso*/
                st.updateUse(nombreTipo, "Nombre de tipo de par");
    
                if(st.getAmbitoByKey(nombreTipo).equals(" ")){
                    st.updateAmbito(nombreTipo,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(nombreTipo,"String","Nombre de tipo de par",SymbolTable.ambitoGlobal.toString(), 278);
                }
            }
        }
break;
case 79:
//#line 502 "gramatica.y"
{
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
break;
case 80:
//#line 505 "gramatica.y"
{
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 81:
//#line 508 "gramatica.y"
{
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 82:
//#line 511 "gramatica.y"
{
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
break;
case 83:
//#line 514 "gramatica.y"
{
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 84:
//#line 517 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 85:
//#line 518 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 86:
//#line 519 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 87:
//#line 520 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 88:
//#line 521 "gramatica.y"
{ SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 89:
//#line 522 "gramatica.y"
{ SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 90:
//#line 523 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 91:
//#line 524 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 92:
//#line 525 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 93:
//#line 526 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Falta la asignacion en la definicion de nuevos tipos");}
break;
case 94:
//#line 528 "gramatica.y"
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
            SymbolTable.aggListaErrores("Error al convertir los limites del subrango a double: " + e.getMessage());
        }
    }
break;
case 95:
//#line 553 "gramatica.y"
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
            SymbolTable.aggListaErrores("Error al convertir los limites del subrango a double: " + e.getMessage());
        }

    }
break;
case 96:
//#line 577 "gramatica.y"
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
             SymbolTable.aggListaErrores("Error al convertir los limites del subrango a double: " + e.getMessage());
         }}
break;
case 97:
//#line 595 "gramatica.y"
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
             SymbolTable.aggListaErrores("Error al convertir los limites del subrango a double: " + e.getMessage());
         }}
break;
case 98:
//#line 618 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 99:
//#line 619 "gramatica.y"
{
        System.out.println("Error??");
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 100:
//#line 625 "gramatica.y"
{
    SymbolTable.aggPolaca(val_peek(1).sval);
    SymbolTable.pila.push(SymbolTable.polaca.size()); SymbolTable.aggPolaca("");  SymbolTable.aggPolaca("BF"); 
}
break;
case 101:
//#line 629 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 102:
//#line 630 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 103:
//#line 631 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 104:
//#line 634 "gramatica.y"
{yyval.sval = "<=" ;}
break;
case 105:
//#line 635 "gramatica.y"
{yyval.sval = ">=";}
break;
case 106:
//#line 636 "gramatica.y"
{yyval.sval = "!=";}
break;
case 107:
//#line 637 "gramatica.y"
{yyval.sval = "=";}
break;
case 108:
//#line 638 "gramatica.y"
{yyval.sval = "<";}
break;
case 109:
//#line 639 "gramatica.y"
{yyval.sval = ">";}
break;
case 110:
//#line 643 "gramatica.y"
{ SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 111:
//#line 644 "gramatica.y"
{
            
            /* Obtener las listas de variables y expresiones*/
            List<String> listaVariables = (List<String>) val_peek(3).obj;
            List<String> listaExpresiones = (List<String>) val_peek(1).obj;
            st.imprimirTablaTipos();
            if (listaVariables != null){ 
                /* Verificar si hay más variables que expresiones*/
                if (listaVariables.size() > listaExpresiones.size()) {
                    System.out.println("Warning: Hay más variables que expresiones. Se asignará 0 a las variables sobrantes.");
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable = listaVariables.get(i).toString();
                        if (i < listaExpresiones.size()) {
                            String expresion= listaExpresiones.get(i).toString();
                            
                            chequeoPares(variable,expresion);                       
                        } else {
                            SymbolTable.aggPolaca("0");
                            
                            System.out.println(listaVariables.get(i).toString() + " := 0;");
                        }

                        for (int j = 0; j < SymbolTable.polaca.size(); j++) {
                            if (SymbolTable.polaca.get(j).equals(" ")) {
                                SymbolTable.polaca.set(j, variable);
                                SymbolTable.polaca.set(j+1, ":=");

                                break;  /* Salir del loop una vez que reemplace el primer espacio*/
                            }
                        }
                    }
                } else if (listaVariables.size() < listaExpresiones.size()) {
                    System.out.println("Warning: Hay más expresiones que variables. Se descartarán las expresiones sobrantes.");
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable= listaVariables.get(i).toString();
                        String expresion= listaExpresiones.get(i).toString();
                        /* Buscar el primer espacio vacío y reemplazarlo con la variable*/
                        for (int j = 0; j < SymbolTable.polaca.size(); j++) {
                            if (SymbolTable.polaca.get(j).equals(" ")) {
                                SymbolTable.polaca.set(j, variable);
                                SymbolTable.polaca.set(j+1, ":=");

                                break;  /* Salir del loop una vez que reemplace el primer espacio*/
                            }
                        }

                        chequeoPares(variable,expresion);
                        
                    }
                } else {
                    /* Generar el código para cada asignación correspondiente*/
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable= listaVariables.get(i).toString();
                        String expresion= listaExpresiones.get(i).toString();
                        System.out.println("expresion: " + expresion);
                        
                        /* Buscar el primer espacio vacío y reemplazarlo con la variable*/
                        for (int j = 0; j < SymbolTable.polaca.size(); j++) {
                            if (SymbolTable.polaca.get(j).equals(" ")) {
                                SymbolTable.polaca.set(j, variable);
                                SymbolTable.polaca.set(j+1, ":=");
                                break;  /* Salir del loop una vez que reemplace el primer espacio*/
                            }
                        }

                        chequeoPares(variable,expresion);
                    }
                }
        }
    }
break;
case 112:
//#line 714 "gramatica.y"
{ SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
break;
case 113:
//#line 718 "gramatica.y"
{
            SymbolTable.aggPolaca(" "); SymbolTable.aggPolaca(" ");

           /* Crear una nueva lista con una sola expresión*/
           List<String> lista = new ArrayList<>();
           lista.add(val_peek(0).sval);  /* Almacenar la expresión como cadena de texto*/
           yyval.obj = lista;
        }
break;
case 114:
//#line 726 "gramatica.y"
{
            SymbolTable.aggPolaca(" "); SymbolTable.aggPolaca(" ");

            /* Agregar la expresión a la lista existente*/
            List<String> lista = (List<String>) val_peek(2).obj;
            lista.add(val_peek(0).sval);  /* Almacenar la nueva expresión*/
            yyval.obj = lista;
        }
break;
case 115:
//#line 737 "gramatica.y"
{
                
                /* Agregar el identificador a la lista*/
                st.esUsoValidoAmbito(val_peek(0).sval);
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yylval.obj = lista;
            }
break;
case 116:
//#line 745 "gramatica.y"
{
                 /* Agregar acceso_par (acceso a atributos o elementos) a la lista*/
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 117:
//#line 751 "gramatica.y"
{
                
                st.esUsoValidoAmbito(val_peek(0).sval);
                /* Crear lista con el primer identificador*/
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 118:
//#line 759 "gramatica.y"
{
                /* Crear una nueva lista con acceso_par*/
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 119:
//#line 765 "gramatica.y"
{ SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 120:
//#line 766 "gramatica.y"
{ SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 121:
//#line 767 "gramatica.y"
{ SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 122:
//#line 768 "gramatica.y"
{SymbolTable.aggListaErrores("No puede haber constantes a la izquierda en la asignacion");}
break;
case 123:
//#line 769 "gramatica.y"
{SymbolTable.aggListaErrores("No puede haber constantes a la izquierda en la asignacion");}
break;
case 124:
//#line 774 "gramatica.y"
{

        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            st.esUsoValidoAmbito(val_peek(3).sval);
            yyval.sval = val_peek(3).sval + "{" + val_peek(1).sval + "}";
            SymbolTable.aggPolaca(val_peek(3).sval + "{" + val_peek(1).sval + "}"); 

        }
        
    }
break;
case 125:
//#line 786 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Se debe utilizar el indice 1 o 2 para acceder a los pares");}
break;
case 126:
//#line 787 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 127:
//#line 791 "gramatica.y"
{
            if(st.containsTypeEtiquetas(new TipoEtiqueta(val_peek(1).sval,null,null))){/*YA HUBO UNA ETIQUETA DECLARADA ANTES*/
                int posicion = st.popFirstOccurrenceByNameEtiquetas(val_peek(1).sval);
                if(posicion!=-1){

                    SymbolTable.aggPolaca(String.valueOf(posicion+1));
                }else{
                    SymbolTable.aggPolaca("");
                }
            }else{
                st.aggPilaGotos(new TipoEtiqueta(val_peek(1).sval,SymbolTable.polaca.size(),SymbolTable.ambitoGlobal.toString()));
                SymbolTable.aggPolaca("");
            }
            SymbolTable.aggPolaca("BI");

            st.esUsoValidoAmbito(val_peek(1).sval);
            }
break;
case 128:
//#line 808 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 129:
//#line 809 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 130:
//#line 810 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 131:
//#line 813 "gramatica.y"
{
        /* Verifica que el parámetro no sea nulo antes de intentar convertirlo a cadena*/
        if (val_peek(1).sval != null) {
            if (st.getUse(val_peek(3).sval) == null) {
                SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Llamado funcion:"+val_peek(3).sval+"  no declarada");
            }
            st.esUsoValidoAmbito(val_peek(3).sval);
            yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
            SymbolTable.aggPolaca(val_peek(3).sval); 

        } else {
            SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Parámetro de función nulo");
            yyval.sval = val_peek(3).sval + "()";  /* Asume que no hay parámetros si es nulo*/
        }
    }
break;
case 132:
//#line 828 "gramatica.y"
{
        SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
break;
case 133:
//#line 833 "gramatica.y"
{
    /* Asegúrate de que el valor de la expresión aritmética se pase correctamente hacia arriba*/
    yyval.sval = val_peek(0).sval;
}
break;
case 134:
//#line 839 "gramatica.y"
{
                if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                    System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
                }
                SymbolTable.aggPolaca("+");
                /* Devuelve la expresión como una cadena que representa la suma*/
                yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
            }
break;
case 135:
//#line 847 "gramatica.y"
{
                if( (isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                    System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
                }
                SymbolTable.aggPolaca("-");
                /* Devuelve la expresión como una cadena que representa la resta*/
                yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
            }
break;
case 136:
//#line 855 "gramatica.y"
{
                if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                    System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
                }
                SymbolTable.aggPolaca("*");
                /* Devuelve la expresión como una cadena que representa la multiplicación*/
                yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
            }
break;
case 137:
//#line 863 "gramatica.y"
{
                if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                    System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
                }
                SymbolTable.aggPolaca("/");
                /* Devuelve la expresión como una cadena que representa la división*/
                yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
            }
break;
case 138:
//#line 871 "gramatica.y"
{
                String constante = val_peek(0).sval;
                String valorString =getStringByType(constante);
                /* Agregar el valorString a la polaca inversa en la SymbolTable*/
                SymbolTable.aggPolaca(valorString);
                /* Devuelve el valor de la constante como cadena*/
                yyval.sval = constante;
            }
break;
case 139:
//#line 879 "gramatica.y"
{
                SymbolTable.aggPolaca(val_peek(0).sval);
                /* Devuelve el identificador como cadena*/
                st.esUsoValidoAmbito(val_peek(0).sval);
                yyval.sval = val_peek(0).sval;
            }
break;
case 140:
//#line 885 "gramatica.y"
{
                /* Devuelve el resultado del acceso a un parámetro*/
                yyval.sval = val_peek(0).sval;
            }
break;
case 141:
//#line 890 "gramatica.y"
{
                /* Devuelve la expresión unaria*/
                yyval.sval = val_peek(0).sval;
            }
break;
case 142:
//#line 897 "gramatica.y"
{
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("+");
            /* Devuelve la expresión como una cadena que representa la suma*/
            yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
        }
break;
case 143:
//#line 905 "gramatica.y"
{
            if( (isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("-");
            /* Devuelve la expresión como una cadena que representa la resta*/
            yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
        }
break;
case 144:
//#line 913 "gramatica.y"
{
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("*");
            /* Devuelve la expresión como una cadena que representa la multiplicación*/
            yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
        }
break;
case 145:
//#line 921 "gramatica.y"
{
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("/");
            /* Devuelve la expresión como una cadena que representa la división*/
            yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
        }
break;
case 146:
//#line 929 "gramatica.y"
{
            String constante = val_peek(0).sval;
            String valorString =getStringByType(constante);
            /* Agregar el valorString a la polaca inversa en la SymbolTable*/
            SymbolTable.aggPolaca(valorString);
            /* Devuelve el valor de la constante como cadena*/
            yyval.sval = constante;
    }
break;
case 147:
//#line 937 "gramatica.y"
{
            SymbolTable.aggPolaca(val_peek(0).sval);
            /* Devuelve el identificador como cadena*/
            st.esUsoValidoAmbito(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 148:
//#line 943 "gramatica.y"
{
            /* Devuelve el resultado del acceso a un parámetro*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 149:
//#line 947 "gramatica.y"
{
            /* Devuelve el resultado de la invocación de una función*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 150:
//#line 951 "gramatica.y"
{
            /* Devuelve la expresión unaria*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 151:
//#line 955 "gramatica.y"
{SymbolTable.aggListaErrores("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 152:
//#line 958 "gramatica.y"
{ 


    double valor = val_peek(0).dval;  
    /* Devuelve el valor unario con el signo negativo*/
    yyval.sval = "-" + val_peek(0).sval;
    String nombreConstante = val_peek(0).sval;  
    String nombreConMenos = "-" + nombreConstante;
    /* verificacion en la tabla de simbolos.*/
    if (st.hasKey(nombreConstante)) {
        String tipo = st.getType(nombreConstante);  /*  tipo de la constante.*/
        if (tipo != null) {
            /* Verifica si el valor original (sin negativo) esta en el rango adecuado segun el tipo.*/
            if (tipo.equals("longint")) {
                if (!lexer.isLongintRange(valor)) {
                    SymbolTable.aggListaErrores("Error: El valor de la constante " + valor + " esta fuera del rango permitido para longint.");
                } else {
                    SymbolTable.aggPolaca(nombreConstante);
                    st.addValue(nombreConMenos, tipo,"Constante"," ",SymbolTable.constantValue);
                }
            } else if (tipo.equals("double")) {
                if (!lexer.isDoubleRange(valor)) {
                    SymbolTable.aggListaErrores("Error: El valor de la constante " + valor + " esta fuera del rango permitido para double.");
                } else {
                    SymbolTable.aggPolaca(nombreConstante);
                    st.addValue(nombreConMenos, tipo,"Constante"," ", SymbolTable.constantValue);
                }
            }else if (tipo.equals("Octal")) {
                if (!lexer.isOctalRange(valor)) {
                    SymbolTable.aggListaErrores("Error: El valor de la constante " + valor + " esta fuera del rango permitido para octales.");
                    
                } else {
                    SymbolTable.aggPolaca(nombreConstante);
                    st.addValue(nombreConMenos, tipo,"Constante"," ", SymbolTable.constantValue);
                }
            }
        } else {
            SymbolTable.aggListaErrores("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else { /*se trata de numero negativo menor al menor negativo.*/
    	
        if (nombreConstante.startsWith("0") && !nombreConstante.matches(".*[89].*")) {
        	SymbolTable.aggListaErrores("El valor octal " + "-"+nombreConstante+ " se ajusto al valor minimo.");
            SymbolTable.aggPolaca("020000000000");
            st.addValue("-020000000000", "Octal","Constante"," ", SymbolTable.constantValue);
        } else if (nombreConstante.contains(".")) {
        	SymbolTable.aggListaErrores("El valor double -" + nombreConstante + " se ajusta al valor mínimo.");

            /* Parseamos el valor como double para comparaciones*/
            double valorDouble = Double.parseDouble("-" + nombreConstante.replace("d", "e"));
            /* Rango mínimo y máximo de los números double*/
            double maxNegativeDouble = -1.7976931348623157e+308;
            double minNegativeDouble = -2.2250738585072014e-308;

            /* Si está por debajo del máximo permitido, lo mantenemos*/
            if (valorDouble < maxNegativeDouble) {
                SymbolTable.aggPolaca("1.7976931348623156d+308");
                st.addValue("-1.7976931348623156d+308", "double","Constante"," ", SymbolTable.constantValue);
            } 
            /* Si está por debajo del mínimo permitido pero mayor al mínimo ajustado*/
            else if (valorDouble > minNegativeDouble) {
                st.addValue("-2.2250738585072015d-308", "double","Constante"," ", SymbolTable.constantValue);
                SymbolTable.aggPolaca("2.2250738585072015d-308");
            } 
            /* Si está en el rango permitido*/
            else {
                SymbolTable.aggPolaca(nombreConstante);
                st.addValue("-" + nombreConstante, "double","Constante"," ", SymbolTable.constantValue);
            }
            
        } else{ /*ya se sabe que es entero*/
            /* Lógica para longint*/
        	SymbolTable.aggListaErrores("El valor longint -" + nombreConstante + " se ajusta al valor mínimo.");
            nombreConMenos = "-2147483648"; /* Asignar valor mínimo si está fuera de rango*/
            st.addValue(nombreConMenos, "longint","Constante"," ", SymbolTable.constantValue);
            SymbolTable.aggPolaca("2147483648");

        }
        
    }

    SymbolTable.aggPolaca("-");

}
break;
//#line 2367 "Parser.java"
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
