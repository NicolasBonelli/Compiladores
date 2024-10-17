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
    0,    0,    0,    2,    2,    3,    3,    4,    4,    4,
    4,    4,    4,    4,    4,    4,    4,    4,    4,    5,
    5,    5,   15,   15,   15,    1,   10,   10,   10,   10,
   10,   10,   16,   17,   17,   17,   18,   18,   14,   14,
   14,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    8,    8,    8,    8,
    8,    8,    9,    9,    9,    9,    9,    9,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   20,   20,   20,   20,
   20,   20,   19,   19,   19,   19,   21,   21,   21,   21,
   21,   21,    6,    6,    6,   23,   23,   22,   22,   22,
   22,   22,   22,   22,   22,   22,   24,   24,   24,   11,
   11,   11,   11,   25,   25,   26,   27,   27,   27,   27,
   27,   27,   27,   27,   13,   13,   13,   13,   13,   13,
   13,   13,   13,   13,   28,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    5,    4,    4,    3,
    3,    2,    3,    1,    2,    1,    7,    7,    7,    7,
    7,    8,    2,    3,    3,    0,    1,    1,    1,    1,
    1,    8,   10,    9,    7,    9,    7,    9,    7,    9,
    7,    8,    6,    8,    8,   10,    7,    6,    5,    6,
    5,    7,    5,    5,    4,    4,    5,    4,    6,    7,
    7,    6,    5,    5,    5,    7,    6,    6,    6,    6,
    6,    6,    5,    5,    5,    5,    5,    6,    6,    7,
    2,    1,    3,    3,    2,    2,    1,    1,    1,    1,
    1,    1,    4,    4,    3,    1,    3,    3,    3,    1,
    1,    3,    3,    3,    1,    3,    4,    3,    2,    3,
    2,    2,    2,    4,    4,    1,    3,    3,    3,    3,
    1,    1,    1,    1,    3,    3,    3,    3,    1,    1,
    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    5,    0,    0,    0,
    0,    0,   40,   39,    0,  115,   16,    0,    6,    8,
    9,   10,   11,   12,   13,   14,   15,    0,    0,    0,
    1,  144,   97,   98,   99,    0,  139,    0,    0,  101,
  102,  100,    0,    0,    0,  141,  142,  143,    0,    0,
    0,    0,    0,    0,    0,   37,   38,    0,  123,    0,
  121,    0,  119,    0,    4,    7,    0,    0,   22,    0,
    0,    0,    0,    0,    0,  145,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  120,    0,  113,
    0,  118,   26,    0,    0,    0,   21,   25,   20,    0,
  105,    0,    0,    0,  116,  109,    0,  112,    0,    0,
  131,  133,    0,    0,  134,    0,    0,    0,    0,    0,
  137,  138,    0,    0,    0,   68,    0,    0,    0,    0,
    0,    0,    0,    0,   92,    0,    0,    0,    0,    0,
    0,    0,   19,    0,    0,    0,    0,    0,  117,    0,
    0,    0,   23,  103,  104,    0,  125,  124,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   63,   67,   64,
   74,   73,    0,    0,    0,    0,    0,    0,   91,   83,
   85,    0,   84,   86,    0,    0,   17,   59,    0,    0,
    0,   61,    0,    0,    0,    0,    0,    0,    0,    0,
  129,  130,    0,    0,    0,    0,    0,    0,    0,    0,
   53,    0,   81,    0,   82,    0,   72,    0,    0,   69,
   79,   80,    0,   60,    0,    0,   33,    0,    0,    0,
    0,    0,    0,    0,   47,    0,   51,    0,    0,    0,
    0,    0,   49,    0,   70,   71,   76,    0,    0,    0,
   62,   57,   31,   29,    0,    0,   34,   28,   35,   30,
    0,   52,   55,    0,   42,    0,    0,   54,   87,    0,
    0,    0,   32,   48,    0,    0,   44,   50,   89,   88,
    0,   56,   43,   90,
};
final static short yydgoto[] = {                          3,
    4,   56,   18,   57,   20,   21,   22,   23,   24,   25,
   26,   27,   43,   28,   71,  215,  216,   58,   44,  157,
   45,   29,  123,   46,   47,  133,  134,   48,
};
final static short yysindex[] = {                      -220,
  609,    0,    0, -223,    0,  -36,    0,    5,  -55,    9,
  565,  -45,    0,    0, -107,    0,    0,  629,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   65,   42, -193,
    0,    0,    0,    0,    0,  -21,    0, -204,  480,    0,
    0,    0,  105, -177,   -2,    0,    0,    0,  281,  -39,
 -181, -164, -126,  -38,   45,    0,    0, -222,    0,   34,
    0, -173,    0, -103,    0,    0,  -29,    0,    0,   76,
   24,   73, -115, -149,   98,    0, -121,  112,   -2,   -2,
   -2,   -2,   -2,   -2,  653,  165,  117,  -25,    0,  109,
  134,   68, -193, -101,  -96,  -42,    0,  -94,  -85,  -94,
  126,  136,  133,   80,  366,  164,  444,    0, -105,    0,
   91,    0,    0, -181,  169, -181,    0,    0,    0,  -53,
    0,  165,  130, -105,    0,    0, -105,    0,  187, -105,
    0,    0,  237,  567,    0,  653,  589,  165,   18,   18,
    0,    0,  165, -212,  220,    0,  309,  334,  338,  345,
  343,  356,  367,  152,    0,  -22,  377,  363,  384,  388,
  177,  183,    0,  409,  431,  366,  505,  417,    0,  199,
 -181,  200,    0,    0,    0,   -2,    0,    0,   25,   25,
   25,   25, -170,  471, -142,  653,  420,    0,    0,    0,
    0,    0,   69,   75,  202,  426,  452,  221,    0,    0,
    0,  448,    0,    0,  449,  455,    0,    0,  489,  470,
  490,    0,  491,  -10,   89,  110,  492,  165,   84,   84,
    0,    0,  653,  484,  277,  495,  425,  653,  496,  287,
    0,  497,    0,  498,    0,  503,    0,   14,  531,    0,
    0,    0,  529,    0,  533, -223,    0, -223, -223, -181,
 -223, -181, -223,  331,    0,  539,    0,  544,  653,  545,
  351,  353,    0,  557,    0,    0,    0,  493,  341,   22,
    0,    0,    0,    0,  558,  349,    0,    0,    0,    0,
  562,    0,    0, -226,    0,  564,  571,    0,    0,  506,
  507,  355,    0,    0,  576,  577,    0,    0,    0,    0,
  517,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    1,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -31,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   51,
    0,    0,    0,    0,    0,  535,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    3,
    0,    0,    0,    0,    0,    0,    0,  -32,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   47,    0,   97,    0,  527,   10,    0,
    0,    0,   35,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  146,    0,   52,    0,    0,   60,    0,    0,  154,
    0,    0,    0,  596,    0,    0,    0,  114,  540,  546,
    0,    0,  143,    0,   28,    0,    0,   53,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   78,    0,    0,    0,    0,    0,    0,
  159,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  103,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  147,   21,  172,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  128,    0,  153,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  178,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  203,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  231,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  256,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    4,    2,    0,   38,    0,    0,    0,    0,    0,    0,
    0,    0,  584,  512,    0,  -93,    0,  488,   -6,  -58,
  600,    0,    0,   -1,    0,    0,  272,  -40,
};
final static int YYTABLESIZE=933;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         30,
    2,    5,  103,   39,   53,   31,   38,   26,   38,   30,
  114,   24,  110,   61,   75,   64,   30,   64,   75,  154,
   96,  112,  198,   40,   42,   41,   24,   41,  156,  295,
  248,   70,   78,  106,  135,  296,    1,  156,   19,    1,
  159,  160,   38,  122,   49,  107,  186,   93,   54,  187,
  139,  139,  139,  115,  139,   66,  139,    2,  269,   82,
  110,  127,   74,  127,   83,  127,  292,  120,   66,   38,
  115,  126,  128,  132,   76,  141,  141,  141,  111,  141,
   85,  141,  119,   30,  105,   73,   91,   95,  223,   13,
   14,  224,  108,   65,  111,  108,   97,   64,  165,  202,
  168,   64,  199,  114,  109,   95,   13,   14,  148,   82,
   80,   99,   81,   97,   83,  116,  228,   38,   18,  229,
  164,   82,   80,   69,   81,  181,   83,  233,  127,  249,
  182,  121,  250,  235,   30,   30,  136,   96,  135,  135,
  135,  135,   38,   75,  101,  102,   82,   80,   62,   81,
  251,   83,  137,  252,   94,   96,  277,  145,  279,  209,
  211,  155,  124,  125,   40,   42,   41,  146,   77,   63,
  155,   63,   94,  176,  147,  111,  149,  132,  132,  132,
  132,  150,   30,   93,   30,   13,   14,  161,  175,  106,
  107,  163,   97,   78,  132,  132,  132,  162,  132,   36,
  132,   93,   36,  166,  106,  107,   82,   80,  171,   81,
   59,   83,  128,   50,  128,  169,  128,   32,   58,   32,
   51,   30,   52,   24,  173,   30,   30,  177,  151,  152,
   62,   94,   95,   41,   60,  153,   33,   34,   35,   36,
   37,   36,   37,   45,  110,   24,   41,  273,  113,  274,
  275,   63,  278,   32,  280,   63,  197,   30,  122,  122,
   26,  122,  122,  122,  122,  122,  122,  247,  122,  122,
  122,   27,  122,  122,  122,   36,   37,  178,  188,  117,
  122,  122,  122,   66,   66,  115,   66,   66,   66,   66,
   66,   66,  268,   66,   66,   66,   46,   66,   66,   66,
  291,  118,  130,  131,   95,   66,   66,   66,   65,   65,
  111,   65,   65,   65,   65,   65,   65,   72,   65,   65,
   65,   90,   65,   65,   65,   38,  111,  108,   32,   67,
   65,   65,   65,   18,   18,  114,   18,   18,   18,   18,
   18,   18,   68,   18,   18,   18,  232,   18,   18,   18,
   36,   37,  234,  129,   96,   18,   18,   18,   75,   75,
   79,   75,   75,   75,   75,   75,   75,  189,   75,   75,
   75,   94,   75,   75,   75,  130,  131,   33,   34,   35,
   75,   75,   75,   77,   77,  174,   77,   77,   77,   77,
   77,   77,  190,   77,   77,   77,  191,   77,   77,   77,
   93,  106,  107,  192,  193,   77,   77,   77,   78,   78,
   38,   78,   78,   78,   78,   78,   78,  194,   78,   78,
   78,  201,   78,   78,   78,   40,   42,   41,  195,  196,
   78,   78,   78,   58,   58,  200,   58,   58,   58,   58,
   58,   58,  203,   58,   58,   58,  204,   58,   58,   58,
  219,  220,  221,  222,  205,   58,   58,   58,   45,   45,
  206,   45,   45,   45,   45,   45,   45,  207,   45,   45,
   45,  208,   45,   45,   45,  212,  213,  217,  231,  236,
   45,   45,   45,  167,  237,  156,   27,   27,   38,   27,
   27,   27,   27,   27,   27,  238,   27,   27,   27,  239,
   27,   27,   27,   40,   42,   41,  240,  241,   27,   27,
   27,   46,   46,  242,   46,   46,   46,   46,   46,   46,
   77,   46,   46,   46,   38,   46,   46,   46,  244,  243,
  245,  246,  253,   46,   46,   46,   32,    6,  256,   40,
   42,   41,  255,    8,    9,  210,   10,   11,  264,   38,
   12,   13,   14,  257,  263,  265,  266,   87,   88,   89,
   17,  267,   98,  100,   40,   42,   41,  140,  140,  140,
  110,  140,  144,  140,  270,  140,  140,  140,  140,  140,
  135,  140,  135,  135,  135,   41,  136,  271,  136,  136,
  136,  272,  281,  140,  140,  140,  140,  282,  135,  135,
  135,  135,  283,  285,  136,  136,  136,  136,  181,  179,
  158,  180,  286,  182,  287,  288,  293,  289,  155,  290,
  294,   32,  297,  183,  185,  170,  247,  172,   86,  298,
  299,  300,   92,  301,  302,  303,  126,  104,   33,   34,
   35,  304,   84,   36,   37,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  122,    0,    0,    0,    0,
    0,    0,  138,  139,  140,  141,  142,  143,    0,    0,
    0,  227,    0,  230,    0,    0,    0,    0,    0,    0,
  258,    6,  214,  259,    1,    0,  260,    8,    9,    0,
   10,   11,    0,    0,   12,   13,   14,    0,    0,   32,
    0,    0,   15,   16,   17,    0,    0,    0,    0,    0,
  254,    0,    0,    0,  261,  262,   33,   34,   35,    0,
    0,   36,   37,    0,    0,    0,    0,    6,    0,  225,
    1,    0,  226,    8,    9,   32,   10,   11,    0,    0,
   12,   13,   14,    0,    0,    0,  284,    0,   15,   16,
   17,    0,   33,   34,   35,    0,    0,   36,   37,  218,
   32,  276,    0,  276,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   33,   34,   35,
    0,    0,   36,   37,    0,    0,    0,    0,    0,    0,
  140,   41,  140,    0,    0,  135,    0,  135,    0,    0,
    0,  136,  110,  136,   41,    0,    0,  140,  140,  140,
    0,    0,  135,  135,  135,    0,    0,    0,  136,  136,
  136,    6,    0,    0,    1,    0,    0,    8,    9,    0,
   10,   11,   55,    0,   12,   13,   14,    0,    0,    0,
    0,    0,   15,   16,   17,    6,  184,    0,    1,    0,
    0,    8,    9,    0,   10,   11,    0,    0,   12,   13,
   14,    0,    0,    0,    0,    6,   15,   16,   17,    7,
    0,    8,    9,    0,   10,   11,    0,    0,   12,   13,
   14,    0,    0,    0,    0,    6,   15,   16,   17,   65,
    0,    8,    9,    0,   10,   11,    0,    0,   12,   13,
   14,    0,    0,    0,    0,    0,   15,   16,   17,    6,
    0,    0,    1,    0,    0,    8,    9,    0,   10,   11,
    0,    0,   12,   13,   14,    0,    0,    0,    0,    0,
   15,   16,   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    0,    0,   41,   40,   60,    4,   45,   40,   45,   11,
   40,   44,   44,   59,   40,  123,   18,  123,   40,   62,
   60,  125,   45,   60,   61,   62,   59,   59,  123,  256,
   41,   28,   39,  256,   75,  262,  260,  123,    1,  260,
   99,  100,   45,   41,   40,  268,  259,   49,   40,  262,
   41,   42,   43,   44,   45,   18,   47,  278,   45,   42,
   62,   41,  256,   43,   47,   45,   45,   44,   41,   45,
   67,   73,   74,   75,  279,   41,   42,   43,   44,   45,
  258,   47,   59,   85,   40,   44,   49,   41,  259,  271,
  272,  262,   59,   41,   44,   44,  278,  123,  105,  158,
  107,  123,  125,   44,  278,   59,  271,  272,   41,   42,
   43,  276,   45,  278,   47,   40,  259,   45,   41,  262,
   41,   42,   43,   59,   45,   42,   47,   59,  278,   41,
   47,   59,   44,   59,  136,  137,  258,   41,  179,  180,
  181,  182,   45,   41,  271,  272,   42,   43,  256,   45,
   41,   47,   41,   44,   41,   59,  250,   41,  252,  166,
  167,  256,  278,  279,   60,   61,   62,   59,   41,  277,
  256,  277,   59,   44,   41,  279,  278,  179,  180,  181,
  182,  278,  184,   41,  186,  271,  272,   62,   59,   44,
   44,   59,  278,   41,   41,   42,   43,   62,   45,   41,
   47,   59,   44,   40,   59,   59,   42,   43,   40,   45,
  256,   47,   41,  269,   43,  125,   45,  256,   41,  256,
  276,  223,  278,  256,  278,  227,  228,   41,  271,  272,
  256,  271,  272,  265,  280,  278,  273,  274,  275,  278,
  279,  278,  279,   41,  276,  278,  278,  246,  278,  248,
  249,  277,  251,  256,  253,  277,  279,  259,  256,  257,
  260,  259,  260,  261,  262,  263,  264,  278,  266,  267,
  268,   41,  270,  271,  272,  278,  279,   41,   59,  256,
  278,  279,  280,  256,  257,  276,  259,  260,  261,  262,
  263,  264,  279,  266,  267,  268,   41,  270,  271,  272,
  279,  278,  278,  279,  258,  278,  279,  280,  256,  257,
  276,  259,  260,  261,  262,  263,  264,  276,  266,  267,
  268,   41,  270,  271,  272,   45,  276,  276,  256,  265,
  278,  279,  280,  256,  257,  276,  259,  260,  261,  262,
  263,  264,  278,  266,  267,  268,  278,  270,  271,  272,
  278,  279,  278,  256,  258,  278,  279,  280,  256,  257,
  256,  259,  260,  261,  262,  263,  264,   59,  266,  267,
  268,  258,  270,  271,  272,  278,  279,  273,  274,  275,
  278,  279,  280,  256,  257,  256,  259,  260,  261,  262,
  263,  264,   59,  266,  267,  268,   59,  270,  271,  272,
  258,  256,  256,   59,   62,  278,  279,  280,  256,  257,
   45,  259,  260,  261,  262,  263,  264,   62,  266,  267,
  268,   59,  270,  271,  272,   60,   61,   62,   62,  278,
  278,  279,  280,  256,  257,   59,  259,  260,  261,  262,
  263,  264,   59,  266,  267,  268,   59,  270,  271,  272,
  179,  180,  181,  182,  278,  278,  279,  280,  256,  257,
  278,  259,  260,  261,  262,  263,  264,   59,  266,  267,
  268,   41,  270,  271,  272,   59,  278,  278,   59,  278,
  278,  279,  280,   40,   59,  123,  256,  257,   45,  259,
  260,  261,  262,  263,  264,   44,  266,  267,  268,  279,
  270,  271,  272,   60,   61,   62,   59,   59,  278,  279,
  280,  256,  257,   59,  259,  260,  261,  262,  263,  264,
   41,  266,  267,  268,   45,  270,  271,  272,   59,   41,
   41,   41,   41,  278,  279,  280,  256,  257,  262,   60,
   61,   62,   59,  263,  264,   41,  266,  267,  262,   45,
  270,  271,  272,   59,   59,   59,   59,  277,  278,  279,
  280,   59,   51,   52,   60,   61,   62,   41,   42,   43,
   44,   45,   85,   47,   44,   41,   42,   43,   44,   45,
   41,   47,   43,   44,   45,   59,   41,   59,   43,   44,
   45,   59,  262,   59,   60,   61,   62,   59,   59,   60,
   61,   62,   59,   59,   59,   60,   61,   62,   42,   43,
   99,   45,  262,   47,  262,   59,   59,  125,  256,  279,
   59,  256,   59,  136,  137,  114,  278,  116,   45,   59,
  125,  125,   49,  279,   59,   59,   41,   54,  273,  274,
  275,  125,   43,  278,  279,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   72,   -1,   -1,   -1,   -1,
   -1,   -1,   79,   80,   81,   82,   83,   84,   -1,   -1,
   -1,  184,   -1,  186,   -1,   -1,   -1,   -1,   -1,   -1,
  256,  257,  171,  259,  260,   -1,  262,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,  256,
   -1,   -1,  278,  279,  280,   -1,   -1,   -1,   -1,   -1,
  223,   -1,   -1,   -1,  227,  228,  273,  274,  275,   -1,
   -1,  278,  279,   -1,   -1,   -1,   -1,  257,   -1,  259,
  260,   -1,  262,  263,  264,  256,  266,  267,   -1,   -1,
  270,  271,  272,   -1,   -1,   -1,  259,   -1,  278,  279,
  280,   -1,  273,  274,  275,   -1,   -1,  278,  279,  176,
  256,  250,   -1,  252,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,
   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,
  256,  265,  258,   -1,   -1,  256,   -1,  258,   -1,   -1,
   -1,  256,  276,  258,  278,   -1,   -1,  273,  274,  275,
   -1,   -1,  273,  274,  275,   -1,   -1,   -1,  273,  274,
  275,  257,   -1,   -1,  260,   -1,   -1,  263,  264,   -1,
  266,  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,   -1,  278,  279,  280,  257,  258,   -1,  260,   -1,
   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,   -1,  257,  278,  279,  280,  261,
   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,   -1,  257,  278,  279,  280,  261,
   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,   -1,   -1,  278,  279,  280,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,
  278,  279,  280,
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
"declaracion_funcion : tipo FUN nombre '(' parametro ')' bloque_sentencias",
"declaracion_funcion : tipo FUN nombre '(' parametros_error ')' bloque_sentencias",
"declaracion_funcion : tipo FUN nombre '(' tipo ')' bloque_sentencias",
"declaracion_funcion : tipo nombre '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN nombre '(' parametro ')' bloque_sentencias ';'",
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

//#line 873 "gramatica.y"

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
public void chequeoPares(String variable, String expresion){
    if (st.getUse(variable) == null && st.getUse(expresion) == null) {
        System.out.println("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("Las variables: "+ variable + " " + expresion + " nunca fueron declaradas");

    }
    else
    if (st.getUse(variable) == null) {
        System.out.println("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("La variable: "+ variable + " nunca fue declarada");

    } else if (st.getUse(expresion) == null) {
        System.out.println("Error en asignacion: "+variable + " := " + expresion + ";");
        System.out.println("La variable: "+ expresion + " nunca fue declarada");

    }

    else 
    if((st.getUse(variable).equals("Nombre de variable par") &&!st.getUse(expresion).equals("Nombre de variable par"))
    ||(!st.getUse(variable).equals("Nombre de variable par") &&st.getUse(expresion).equals("Nombre de variable par"))){
        System.out.println("Warning: No se pueden utilizar los tipos pares en operaciones que conlleven tipos distintos ");        
        System.out.println("Error en asignacion: "+variable + " := " + expresion + ";");
    }else{
        System.out.println(variable + " := " + expresion + ";");
    }
}
public boolean isPair(String variable){
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

//#line 817 "Parser.java"
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
//#line 52 "gramatica.y"
{
    System.out.println("Programa compilado correctamente");
    /*updatear uso nombre funcion*/
    st.updateUse(val_peek(1).sval, "Nombre de programa");
    
    
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
case 5:
//#line 67 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 18:
//#line 84 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 19:
//#line 85 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
break;
case 20:
//#line 89 "gramatica.y"
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
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
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
	        System.err.println("Error, la variable no está en la tabla de símbolos: " + variable);
	    }
	}
}
break;
case 21:
//#line 121 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 22:
//#line 122 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}
break;
case 23:
//#line 126 "gramatica.y"
{
    
    @SuppressWarnings("unchecked")
    List<String> variables = (List<String>) val_peek(2).obj;
    variables.add(val_peek(0).sval);  /* Agregar nueva variable*/
    yyval.obj = variables;  /* Pasar la lista actualizada hacia arriba */
}
break;
case 24:
//#line 133 "gramatica.y"
{
    List<String> variables = new ArrayList<String>();
    variables.add(val_peek(0).sval);  /* Agregar la primera variable*/
    yyval.obj = variables; 
}
break;
case 25:
//#line 138 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 26:
//#line 142 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
    System.out.println("Entre a Funcion antes (o despues?) de la derecha");

    if (SymbolTable.ambitoGlobal.length() == 0) {
        SymbolTable.ambitoGlobal = new StringBuilder(val_peek(0).sval);
    } else SymbolTable.ambitoGlobal.append(":" + val_peek(0).sval);
        }
break;
case 27:
//#line 151 "gramatica.y"
{
        
        System.out.println("Entre a la 2da llave");
        /*updatear uso nombre funcion*/
        st.updateUse(val_peek(4).sval, "Nombre de funcion");
        

        /* Separar el tipo y el nombre del parámetro*/
        String[] tipoYNombre = val_peek(2).sval.split(":");
        String tipoParametro = tipoYNombre[0];
        String nombreParametro = tipoYNombre[1];

        if(st.contieneSymbolAmbito(val_peek(4).sval,SymbolTable.ambitoGlobal)){
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar funciones en el mismo ambito. Error con el nombre de la funcion:"+val_peek(4).sval);
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


    }
break;
case 28:
//#line 186 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }
break;
case 29:
//#line 191 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }
break;
case 30:
//#line 195 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 31:
//#line 199 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    }
break;
case 32:
//#line 202 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se puede poner ; al final de la declaracion de una fucnion");
    }
break;
case 33:
//#line 207 "gramatica.y"
{
        
        /*updatear uso de variable a variable*/
        if(st.isTypePair(val_peek(1).sval)){/*si el tipo*/
            st.updateUse(val_peek(0).sval, "Nombre de variable par");
        }else{
            st.updateUse(val_peek(0).sval, "Nombre de parametro");
        }
        if(st.contieneSymbolAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal)){
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
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
case 34:
//#line 229 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 35:
//#line 232 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 36:
//#line 235 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion debe tener un parametro.");
    }
break;
case 39:
//#line 245 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 40:
//#line 246 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 41:
//#line 248 "gramatica.y"
{
        
        /* Verificando si el tipo esta en la tabla de tipos definidos*/
        
        if (st.containsKeyTT(val_peek(0).sval+":"+SymbolTable.ambitoGlobal.toString())) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0).sval);
        } 
    }
break;
case 44:
//#line 263 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 45:
//#line 264 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 46:
//#line 267 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 47:
//#line 271 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 48:
//#line 274 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 49:
//#line 278 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 50:
//#line 281 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 51:
//#line 285 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 52:
//#line 288 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 53:
//#line 291 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 54:
//#line 292 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 55:
//#line 293 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 56:
//#line 294 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 58:
//#line 302 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 59:
//#line 305 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
break;
case 60:
//#line 308 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 61:
//#line 311 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 62:
//#line 312 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 65:
//#line 320 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 66:
//#line 323 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 67:
//#line 326 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
break;
case 68:
//#line 327 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta contenido en el OUTF");}
break;
case 69:
//#line 332 "gramatica.y"
{ 

        System.out.println("2do");
        /* Obtener el nombre del tipo desde T_ID*/
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        String tipoBase = val_peek(2).sval;
        
        Subrango subrango = (Subrango) val_peek(1).obj;
    
        double limiteInferior = subrango.getLimiteInferior(); /* Limite inferior */
        double limiteSuperior = subrango.getLimiteSuperior(); /* Limite superior */
        /* Almacenar en la tabla de tipos*/

        if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(4).sval);
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
case 70:
//#line 368 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/



            if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(5).sval);
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
case 71:
//#line 392 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/
            
            

            if(st.contieneSymbolAmbito(nombreTipo,SymbolTable.ambitoGlobal)){
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar tipos. Error con el tipo: "+val_peek(5).sval);
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
case 72:
//#line 415 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
break;
case 73:
//#line 418 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 74:
//#line 421 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 75:
//#line 424 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
break;
case 76:
//#line 427 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 77:
//#line 430 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 78:
//#line 431 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 79:
//#line 432 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 80:
//#line 433 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 81:
//#line 434 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 82:
//#line 435 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 83:
//#line 436 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 84:
//#line 437 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 85:
//#line 438 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 86:
//#line 439 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la asignacion en la definicion de nuevos tipos");}
break;
case 87:
//#line 441 "gramatica.y"
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
case 88:
//#line 466 "gramatica.y"
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
case 89:
//#line 492 "gramatica.y"
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
case 90:
//#line 510 "gramatica.y"
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
case 91:
//#line 533 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 92:
//#line 534 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 94:
//#line 540 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 95:
//#line 541 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 96:
//#line 542 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 103:
//#line 554 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 104:
//#line 555 "gramatica.y"
{
            
            /* Obtener las listas de variables y expresiones*/
            List<String> listaVariables = (List<String>) val_peek(3).obj;
            List<String> listaExpresiones = (List<String>) val_peek(1).obj;

            if (listaVariables != null){ 
                /* Verificar si hay más variables que expresiones*/
                if (listaVariables.size() > listaExpresiones.size()) {
                    System.out.println("Warning: Hay más variables que expresiones. Se asignará 0 a las variables sobrantes.");
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable= listaVariables.get(i).toString();
                        if (i < listaExpresiones.size()) {
                            String expresion= listaExpresiones.get(i).toString();
                            chequeoPares(variable,expresion);                       
                        } else {
                            SymbolTable.aggPolaca("0");
                            
                            System.out.println(listaVariables.get(i).toString() + " := 0;");
                        }
                        SymbolTable.aggPolaca(variable);
                        /* Asignar 0 a las variables sobrantes*/
                        SymbolTable.aggPolaca(":=");
                    }
                } else if (listaVariables.size() < listaExpresiones.size()) {
                    System.out.println("Warning: Hay más expresiones que variables. Se descartarán las expresiones sobrantes.");
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable= listaVariables.get(i).toString();
                        String expresion= listaExpresiones.get(i).toString();
                        
                        SymbolTable.aggPolaca(variable);
                        SymbolTable.aggPolaca(":=");
                        chequeoPares(variable,expresion);
                        
                    }
                } else {
                    /* Generar el código para cada asignación correspondiente*/
                    for (int i = 0; i < listaVariables.size(); i++) {
                        String variable= listaVariables.get(i).toString();
                        String expresion= listaExpresiones.get(i).toString();
                        System.out.println("expresion: " + expresion);
                        SymbolTable.aggPolaca(variable);
                        SymbolTable.aggPolaca(":=");
                        chequeoPares(variable,expresion);
                    }
                }
        }
    }
break;
case 105:
//#line 603 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
break;
case 106:
//#line 607 "gramatica.y"
{
           /* Crear una nueva lista con una sola expresión*/
           List<String> lista = new ArrayList<>();
           lista.add(val_peek(0).sval);  /* Almacenar la expresión como cadena de texto*/
           yyval.obj = lista;
        }
break;
case 107:
//#line 613 "gramatica.y"
{
            /* Agregar la expresión a la lista existente*/
            List<String> lista = (List<String>) val_peek(2).obj;
            lista.add(val_peek(0).sval);  /* Almacenar la nueva expresión*/
            yyval.obj = lista;
        }
break;
case 108:
//#line 622 "gramatica.y"
{
                
                /* Agregar el identificador a la lista*/
                st.esUsoValidoAmbito(val_peek(0).sval);
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yylval.obj = lista;
            }
break;
case 109:
//#line 630 "gramatica.y"
{
                 /* Agregar acceso_par (acceso a atributos o elementos) a la lista*/
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 110:
//#line 636 "gramatica.y"
{
                
                st.esUsoValidoAmbito(val_peek(0).sval);
                /* Crear lista con el primer identificador*/
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 111:
//#line 644 "gramatica.y"
{
                /* Crear una nueva lista con acceso_par*/
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 112:
//#line 650 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 113:
//#line 651 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 114:
//#line 652 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 115:
//#line 653 "gramatica.y"
{System.err.println("No puede haber constantes a la izquierda en la asignacion");}
break;
case 116:
//#line 654 "gramatica.y"
{System.err.println("No puede haber constantes a la izquierda en la asignacion");}
break;
case 117:
//#line 659 "gramatica.y"
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
case 118:
//#line 671 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se debe utilizar el indice 1 o 2 para acceder a los pares");}
break;
case 119:
//#line 672 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 121:
//#line 676 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 122:
//#line 677 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 123:
//#line 678 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 124:
//#line 681 "gramatica.y"
{
        /* Verifica que el parámetro no sea nulo antes de intentar convertirlo a cadena*/
        if (val_peek(1).sval != null) {
            if (st.getUse(val_peek(3).sval) == null) {
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Llamado funcion:"+val_peek(3).sval+"  no declarada");
            }
            st.esUsoValidoAmbito(val_peek(3).sval);
            yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
            SymbolTable.aggPolaca(val_peek(3).sval + "(" + val_peek(1).sval + ")"); 

        } else {
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro de función nulo");
            yyval.sval = val_peek(3).sval + "()";  /* Asume que no hay parámetros si es nulo*/
        }
    }
break;
case 125:
//#line 696 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
break;
case 126:
//#line 701 "gramatica.y"
{
    /* Asegúrate de que el valor de la expresión aritmética se pase correctamente hacia arriba*/
    yyval.sval = val_peek(0).sval;
}
break;
case 127:
//#line 707 "gramatica.y"
{
        SymbolTable.aggPolaca("+");
        yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
      }
break;
case 128:
//#line 711 "gramatica.y"
{
        SymbolTable.aggPolaca("-");
        yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
      }
break;
case 129:
//#line 715 "gramatica.y"
{
        SymbolTable.aggPolaca("*");
        yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
      }
break;
case 130:
//#line 719 "gramatica.y"
{
        SymbolTable.aggPolaca("/");
        yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
      }
break;
case 131:
//#line 723 "gramatica.y"
{
        SymbolTable.aggPolaca(val_peek(0).sval);
        yyval.sval = val_peek(0).sval;  /* La constante como cadena*/
      }
break;
case 132:
//#line 727 "gramatica.y"
{
        SymbolTable.aggPolaca(val_peek(0).sval);
        yyval.sval = val_peek(0).sval;  /* El identificador como cadena*/
      }
break;
case 133:
//#line 731 "gramatica.y"
{
        yyval.sval = val_peek(0).sval;  /* El resultado del acceso*/
      }
break;
case 134:
//#line 734 "gramatica.y"
{
        yyval.sval = val_peek(0).sval;  /* El valor unario*/
      }
break;
case 135:
//#line 740 "gramatica.y"
{
            if(isPair(val_peek(0).sval)|| isPair(val_peek(2).sval)){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("+");
            /* Devuelve la expresión como una cadena que representa la suma*/
            yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
        }
break;
case 136:
//#line 748 "gramatica.y"
{
            if(isPair(val_peek(0).sval)|| isPair(val_peek(2).sval)){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("-");
            /* Devuelve la expresión como una cadena que representa la resta*/
            yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
        }
break;
case 137:
//#line 756 "gramatica.y"
{
            if(isPair(val_peek(0).sval)|| isPair(val_peek(2).sval)){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("*");
            /* Devuelve la expresión como una cadena que representa la multiplicación*/
            yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
        }
break;
case 138:
//#line 764 "gramatica.y"
{
            if(isPair(val_peek(0).sval)|| isPair(val_peek(2).sval)){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("/");
            /* Devuelve la expresión como una cadena que representa la división*/
            yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
        }
break;
case 139:
//#line 772 "gramatica.y"
{
            SymbolTable.aggPolaca(val_peek(0).sval);
            /* Devuelve el valor de la constante como cadena*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 140:
//#line 777 "gramatica.y"
{
              SymbolTable.aggPolaca(val_peek(0).sval);
            /* Devuelve el identificador como cadena*/
            st.esUsoValidoAmbito(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 141:
//#line 783 "gramatica.y"
{
            /* Devuelve el resultado del acceso a un parámetro*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 142:
//#line 787 "gramatica.y"
{
            /* Devuelve el resultado de la invocación de una función*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 143:
//#line 791 "gramatica.y"
{
            /* Devuelve la expresión unaria*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 144:
//#line 795 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 145:
//#line 798 "gramatica.y"
{ 
    SymbolTable.aggPolaca(val_peek(0).sval);
    SymbolTable.aggPolaca("-");
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
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para longint.");
                } else {
                    st.addValue(nombreConMenos, tipo,"Constante"," ",SymbolTable.constantValue);
                }
            } else if (tipo.equals("double")) {
                if (!lexer.isDoubleRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para double.");
                } else {
                    
                    st.addValue(nombreConMenos, tipo,"Constante"," ", SymbolTable.constantValue);
                }
            }else if (tipo.equals("Octal")) {
                if (!lexer.isOctalRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para octales.");
                    
                } else {
                    st.addValue(nombreConMenos, tipo,"Constante"," ", SymbolTable.constantValue);
                }
            }
        } else {
            System.err.println("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else { /*se trata de numero negativo menor al menor negativo.*/
    	
        if (nombreConstante.startsWith("0") && !nombreConstante.matches(".*[89].*")) {
        	System.err.println("El valor octal " + "-"+nombreConstante+ " se ajusto al valor minimo.");
            st.addValue("-020000000000", "Octal","Constante"," ", SymbolTable.constantValue);
        } else if (nombreConstante.contains(".")) {
        	System.err.println("El valor double -" + nombreConstante + " se ajusta al valor mínimo.");

            /* Parseamos el valor como double para comparaciones*/
            double valorDouble = Double.parseDouble("-" + nombreConstante.replace("d", "e"));
            /* Rango mínimo y máximo de los números double*/
            double maxNegativeDouble = -1.7976931348623157e+308;
            double minNegativeDouble = -2.2250738585072014e-308;

            /* Si está por debajo del máximo permitido, lo mantenemos*/
            if (valorDouble < maxNegativeDouble) {
                st.addValue("-1.7976931348623156d+308", "double","Constante"," ", SymbolTable.constantValue);
            } 
            /* Si está por debajo del mínimo permitido pero mayor al mínimo ajustado*/
            else if (valorDouble > minNegativeDouble) {
                st.addValue("-2.2250738585072015d-308", "double","Constante"," ", SymbolTable.constantValue);
            } 
            /* Si está en el rango permitido*/
            else {
                st.addValue("-" + nombreConstante, "double","Constante"," ", SymbolTable.constantValue);
            }
            
        } else{ /*ya se sabe que es entero*/
            /* Lógica para longint*/
        	System.err.println("El valor longint -" + nombreConstante + " se ajusta al valor mínimo.");
            nombreConMenos = "-2147483648"; /* Asignar valor mínimo si está fuera de rango*/
            st.addValue(nombreConMenos, "longint","Constante"," ", SymbolTable.constantValue);
        }
        
    }
}
break;
//#line 2025 "Parser.java"
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
