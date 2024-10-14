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
    4,    4,   14,   14,   14,   15,    9,    9,    9,    9,
    9,    9,   16,   17,   17,   17,   18,   18,   13,   13,
   13,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,    6,    6,    6,    7,    7,    7,    7,
    7,    7,    8,    8,    8,    8,    8,    8,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   20,   20,   20,   20,
   20,   20,   19,   19,   19,   19,   21,   21,   21,   21,
   21,   21,    5,    5,    5,   23,   23,   22,   22,   22,
   22,   22,   22,   22,   24,   24,   24,   10,   10,   10,
   10,   25,   25,   26,   27,   27,   27,   27,   27,   27,
   27,   27,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   28,
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
    1,    3,    3,    3,    4,    3,    2,    3,    2,    2,
    2,    4,    4,    1,    3,    3,    3,    3,    1,    1,
    1,    1,    3,    3,    3,    3,    1,    1,    1,    1,
    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    5,    0,    0,    0,    0,
    0,   40,   39,    0,   16,    0,    6,    8,    9,   10,
   11,   12,   13,   14,   15,    0,    0,    0,    1,  142,
   97,   98,   99,    0,  137,    0,    0,  101,  102,  100,
    0,    0,    0,  139,  140,  141,    0,    0,    0,    0,
    0,    0,    0,   37,   38,    0,  121,    0,  119,    0,
  117,    0,    4,    7,    0,    0,   22,    0,    0,    0,
    0,    0,    0,  143,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  118,    0,  113,    0,  116,   26,
    0,    0,   21,   25,   20,    0,    0,  105,    0,    0,
    0,  109,    0,  112,    0,    0,  129,  131,    0,    0,
  132,    0,    0,    0,    0,    0,  135,  136,    0,    0,
    0,   68,    0,    0,    0,    0,    0,    0,    0,    0,
   92,    0,    0,    0,    0,    0,    0,    0,   19,    0,
    0,    0,    0,    0,  115,    0,    0,   23,    0,  103,
  104,    0,  123,  122,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   63,   67,   64,   74,   73,    0,    0,
    0,    0,    0,    0,   91,   83,   85,    0,   84,   86,
    0,    0,   17,   59,    0,    0,    0,   61,    0,    0,
    0,    0,    0,    0,    0,    0,  127,  128,    0,    0,
    0,    0,    0,    0,    0,    0,   53,    0,   81,    0,
   82,    0,   72,    0,    0,   69,   79,   80,    0,   60,
    0,    0,   33,    0,    0,    0,    0,    0,    0,    0,
   47,    0,   51,    0,    0,    0,    0,    0,   49,    0,
   70,   71,   76,    0,    0,    0,   62,   57,   31,   29,
    0,    0,   34,   28,   35,   30,    0,   52,   55,    0,
   42,    0,    0,   54,   87,    0,    0,    0,   32,   48,
    0,    0,   44,   50,   89,   88,    0,   56,   43,   90,
};
final static short yydgoto[] = {                          3,
   54,   16,   55,   18,   19,   20,   21,   22,   23,   24,
   25,   41,   26,   68,   69,  211,  212,   56,   42,  153,
   43,   27,  120,   44,   45,  129,  130,   46,
};
final static short yysindex[] = {                      -235,
  454, -231,    0,    0,  410,    0,   17,  336,   36,  578,
   92,    0,    0, -109,    0,  615,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  -54,  -42, -220,    0,    0,
    0,    0,    0,  -16,    0, -188,  284,    0,    0,    0,
  556, -163,  119,    0,    0,    0,  249,  369, -198, -222,
 -251,   19,   82,    0,    0, -223,    0,   68,    0, -142,
    0,  -99,    0,    0,  -34,    0,    0,  144,  109,   44,
 -128, -123,  169,    0, -102,  116,  119,  119,  119,  119,
  119,  119,  634,   63,  124,   -5,  110,  129,   78, -220,
 -107, -106,  349,    0, -110, -111, -110,  127,  130,  139,
   97,  359,  160,  463,    0,  -95,    0,   91,    0,    0,
 -198,  170,    0,    0,    0,  -61, -198,    0,   63,  -37,
  -95,    0,  -95,    0,  180,  -95,    0,    0,  186,  159,
    0,  634,  597,   63,    6,    6,    0,    0,   63, -204,
  212,    0,  302,  306,  314,  327,  353,  361,  374,  162,
    0,   16,  385,  -48,  386,  387,  174,  184,    0,  407,
  432,  359,  309,  420,    0,  204, -198,    0,  211,    0,
    0,  119,    0,    0,   41,   41,   41,   41, -193,  554,
 -148,  634,  438,    0,    0,    0,    0,    0,   -8,   26,
  215,  439,  455,  221,    0,    0,    0,  442,    0,    0,
  448,  450,    0,    0,  469,  452,  473,    0,  476,   -9,
   72,   85,  477,   63,   51,   51,    0,    0,  634,  478,
  260,  479,  434,  634,  480,  273,    0,  482,    0,  483,
    0,  484,    0,    7,  500,    0,    0,    0,  486,    0,
  487, -231,    0, -231, -231, -198, -231, -198, -231,  285,
    0,  495,    0,  497,  634,  501,  299,  304,    0,  505,
    0,    0,    0,  447,  298,   32,    0,    0,    0,    0,
  522,  307,    0,    0,    0,    0,  527,    0,    0, -166,
    0,  534,  543,    0,    0,  481,  485,  325,    0,    0,
  548,  549,    0,    0,    0,    0,  499,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  609,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  335,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    3,    0,    0,
    0,    0,    0,  508,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  -31,    0,    0,
    0,    0,    0,    0,    0,  -36,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   38,    0,   89,    0,  489,    0,    0,    0,   -3,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   65,    0,
   39,    0,   60,    0,    0,  152,    0,    0,    0,  581,
    0,    0,    0,  132,  530,  535,    0,    0,  137,    0,
   -4,    0,    0,   21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   46,
    0,    0,    0,    0,    0,    0,  122,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   71,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   84,  164,  433,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   96,    0,  121,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  146,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  171,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  197,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  224,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   18,    0,    0,    0,    0,    0,    0,    0,
    0,  576,  -33,    0,  560, -113,    0,  -65,  -10,  -66,
  585,    0,    0,   -1,    0,    0,  -76,    8,
};
final static int YYTABLESIZE=914;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         28,
    4,   71,   29,   26,   67,  111,  172,   24,   28,  120,
  197,  152,  152,   62,   28,   95,   97,  140,   17,   98,
   99,  171,   24,   73,    1,  109,   76,   62,    1,  155,
  156,  244,  103,   64,   73,   72,   66,  139,  139,  139,
  111,  139,    2,  139,  104,   90,  111,   80,   12,   13,
  229,  265,   81,   96,  182,   94,   47,  183,  107,  100,
  194,   65,  154,   36,   88,  219,  179,  181,  220,  122,
  124,  128,   12,   13,  152,   52,  288,  166,   95,   94,
  131,   28,  108,  169,  231,   36,   18,  198,   36,  291,
   74,  161,  177,  164,   83,  292,   95,  178,  215,  216,
  217,  218,  118,  114,   80,   78,   62,   79,  106,   81,
  224,   75,  245,  225,  223,  246,  226,   62,  144,   80,
   78,  102,   79,  106,   81,  247,  105,  107,  248,   96,
   28,   28,  273,  210,  275,  106,   77,  160,   80,   78,
  195,   79,  107,   81,  151,  151,   60,   96,  117,  121,
   59,  205,  207,  250,  123,  132,  133,  257,  258,   12,
   13,   78,   36,   36,  141,   36,   94,   61,  142,  143,
  145,  146,   94,  128,  128,  128,  128,   93,   28,  108,
   28,   61,  131,  131,  131,  131,   58,  116,  157,  280,
   94,  158,  130,  130,  130,   93,  130,  159,  130,  162,
  177,  175,  115,  176,  125,  178,  125,  151,  125,  167,
   65,   45,  272,   36,  272,  165,  168,   28,  170,   24,
  173,   28,   28,   66,  120,  120,  174,  120,  120,  120,
  120,  120,  120,   70,  120,  120,  120,   27,  120,  120,
  120,   24,  269,  110,  270,  271,  120,  274,  120,  276,
   60,   66,   66,   28,   66,   66,   66,   66,   66,   66,
   61,   66,   66,   66,   46,   66,   66,   66,  243,  228,
  184,   61,  111,   66,   30,   66,   65,   65,  111,   65,
   65,   65,   65,   65,   65,  264,   65,   65,   65,   87,
   65,   65,   65,   36,  193,   95,   34,   35,   65,   30,
   65,   18,   18,  230,   18,   18,   18,   18,   18,   18,
  287,   18,   18,   18,  108,   18,   18,   18,  126,  127,
  106,   34,   35,   18,   75,   18,   75,   75,   36,   75,
   75,   75,   75,   75,   75,  114,   75,   75,   75,  107,
   75,   75,   75,   38,   40,   39,   96,   57,   75,  206,
   75,   77,   77,   36,   77,   77,   77,   77,   77,   77,
  185,   77,   77,   77,  186,   77,   77,   77,   38,   40,
   39,   58,  187,   77,   30,   77,   78,   78,  110,   78,
   78,   78,   78,   78,   78,  188,   78,   78,   78,   94,
   78,   78,   78,   41,   93,   51,   34,   35,   78,  113,
   78,   58,   58,   36,   58,   58,   58,   58,   58,   58,
  150,   58,   58,   58,  189,   58,   58,   58,   38,   40,
   39,  114,  190,   58,  125,   58,   45,   45,   93,   45,
   45,   45,   45,   45,   45,  191,   45,   45,   45,  192,
   45,   45,   45,  196,  199,  200,  126,  127,   45,   37,
   45,  201,   27,   27,   36,   27,   27,   27,   27,   27,
   27,  202,   27,   27,   27,  203,   27,   27,   27,   38,
   40,   39,  204,  126,   27,  126,   27,  126,  208,   46,
   46,  209,   46,   46,   46,   46,   46,   46,  213,   46,
   46,   46,  232,   46,   46,   46,  227,  233,  234,  235,
  236,   46,  163,   46,   30,    5,  237,   36,  238,  239,
  240,    7,    8,  241,    9,   10,  242,  249,   11,   12,
   13,  252,   38,   40,   39,   85,   86,   35,   15,  138,
  138,  138,  110,  138,  260,  138,  251,  253,  259,   30,
  261,  262,  263,  266,  267,  268,  277,   41,  138,  138,
  138,  138,  138,  278,  138,  279,   31,   32,   33,  281,
  282,   34,   35,  284,   30,  283,  138,  138,  138,  138,
  133,  285,  133,  133,  133,  134,  286,  134,  134,  134,
  289,   31,   32,   33,  243,  290,   34,   35,  133,  133,
  133,  133,  293,  134,  134,  134,  134,   80,   78,   41,
   79,  294,   81,  297,   48,  295,  298,  299,    2,  296,
  110,   49,   41,   50,   30,   38,   40,   39,   84,  147,
  148,  124,   89,  300,  112,   82,  149,  101,    0,    0,
    0,   31,   32,   33,    0,    0,   34,   35,    0,   91,
   92,    0,    0,    0,    0,  119,    0,    0,    0,    0,
    0,    0,  134,  135,  136,  137,  138,  139,    0,    0,
    0,    0,    0,    0,    0,   30,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   31,   32,   33,    0,    0,   34,   35,  254,
    5,    0,  255,    1,    0,  256,    7,    8,    0,    9,
   10,    0,    0,   11,   12,   13,    0,    0,    0,    0,
    5,   14,    0,   15,    6,    0,    7,    8,   30,    9,
   10,    0,    0,   11,   12,   13,    0,    0,    0,    0,
    0,   14,    0,   15,    0,   31,   32,   33,    0,    0,
   34,   35,    0,    0,    0,    0,    0,  214,    0,    0,
    0,    0,    0,   41,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  138,  110,  138,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  138,  138,  138,    0,    0,  133,    0,  133,    0,    0,
  134,    0,  134,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  133,  133,  133,    0,    0,  134,  134,  134,
    5,   77,  221,    1,    0,  222,    7,    8,    0,    9,
   10,    0,    0,   11,   12,   13,    0,    0,   31,   32,
   33,   14,    0,   15,    5,    0,    0,    1,    0,    0,
    7,    8,    0,    9,   10,   53,    0,   11,   12,   13,
    0,    0,    0,    5,  180,   14,    1,   15,    0,    7,
    8,    0,    9,   10,    0,    0,   11,   12,   13,    0,
    0,    5,    0,    0,   14,   63,   15,    7,    8,    0,
    9,   10,    0,    0,   11,   12,   13,    0,    0,    0,
    5,    0,   14,    1,   15,    0,    7,    8,    0,    9,
   10,    0,    0,   11,   12,   13,    0,    0,    0,    0,
    0,   14,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    0,   44,    2,   40,   59,   40,   44,   44,   10,   41,
   59,  123,  123,  123,   16,   49,   50,   83,    1,  271,
  272,   59,   59,   40,  260,  125,   37,  123,  260,   96,
   97,   41,  256,   16,   40,  256,   41,   41,   42,   43,
   44,   45,  278,   47,  268,   47,   44,   42,  271,  272,
   59,   45,   47,  276,  259,  278,   40,  262,   60,   41,
   45,   41,   96,   45,   47,  259,  132,  133,  262,   71,
   72,   73,  271,  272,  123,   40,   45,  111,   41,  278,
   73,   83,   44,  117,   59,   45,   41,  154,   45,  256,
  279,  102,   42,  104,  258,  262,   59,   47,  175,  176,
  177,  178,   59,   44,   42,   43,  123,   45,   44,   47,
  259,   41,   41,  262,  180,   44,  182,  123,   41,   42,
   43,   40,   45,   59,   47,   41,   59,   44,   44,   41,
  132,  133,  246,  167,  248,  278,   41,   41,   42,   43,
  125,   45,   59,   47,  256,  256,  256,   59,   40,  278,
   59,  162,  163,  219,  278,  258,   41,  223,  224,  271,
  272,   41,   41,   45,   41,   44,  278,  277,   59,   41,
  278,  278,   41,  175,  176,  177,  178,   41,  180,  279,
  182,  277,  175,  176,  177,  178,   41,   44,   62,  255,
   59,   62,   41,   42,   43,   59,   45,   59,   47,   40,
   42,   43,   59,   45,   41,   47,   43,  256,   45,   40,
  265,   41,  246,   45,  248,  125,  278,  219,  256,  256,
   41,  223,  224,  278,  256,  257,   41,  259,  260,  261,
  262,  263,  264,  276,  266,  267,  268,   41,  270,  271,
  272,  278,  242,  278,  244,  245,  278,  247,  280,  249,
  256,  256,  257,  255,  259,  260,  261,  262,  263,  264,
  277,  266,  267,  268,   41,  270,  271,  272,  278,  278,
   59,  277,  276,  278,  256,  280,  256,  257,  276,  259,
  260,  261,  262,  263,  264,  279,  266,  267,  268,   41,
  270,  271,  272,   45,  279,  258,  278,  279,  278,  256,
  280,  256,  257,  278,  259,  260,  261,  262,  263,  264,
  279,  266,  267,  268,  276,  270,  271,  272,  278,  279,
  256,  278,  279,  278,   41,  280,  256,  257,   45,  259,
  260,  261,  262,  263,  264,  276,  266,  267,  268,  256,
  270,  271,  272,   60,   61,   62,  258,  256,  278,   41,
  280,  256,  257,   45,  259,  260,  261,  262,  263,  264,
   59,  266,  267,  268,   59,  270,  271,  272,   60,   61,
   62,  280,   59,  278,  256,  280,  256,  257,   44,  259,
  260,  261,  262,  263,  264,   59,  266,  267,  268,  258,
  270,  271,  272,   59,  258,   60,  278,  279,  278,  256,
  280,  256,  257,   45,  259,  260,  261,  262,  263,  264,
   62,  266,  267,  268,   62,  270,  271,  272,   60,   61,
   62,  278,   62,  278,  256,  280,  256,  257,   60,  259,
  260,  261,  262,  263,  264,   62,  266,  267,  268,  278,
  270,  271,  272,   59,   59,   59,  278,  279,  278,   40,
  280,  278,  256,  257,   45,  259,  260,  261,  262,  263,
  264,  278,  266,  267,  268,   59,  270,  271,  272,   60,
   61,   62,   41,   41,  278,   43,  280,   45,   59,  256,
  257,  278,  259,  260,  261,  262,  263,  264,  278,  266,
  267,  268,  278,  270,  271,  272,   59,   59,   44,  279,
   59,  278,   40,  280,  256,  257,   59,   45,   59,   41,
   59,  263,  264,   41,  266,  267,   41,   41,  270,  271,
  272,  262,   60,   61,   62,  277,  278,  279,  280,   41,
   42,   43,   44,   45,  262,   47,   59,   59,   59,  256,
   59,   59,   59,   44,   59,   59,  262,   59,   41,   42,
   43,   44,   45,   59,   47,   59,  273,  274,  275,   59,
  262,  278,  279,   59,  256,  262,   59,   60,   61,   62,
   41,  125,   43,   44,   45,   41,  279,   43,   44,   45,
   59,  273,  274,  275,  278,   59,  278,  279,   59,   60,
   61,   62,   59,   59,   60,   61,   62,   42,   43,  265,
   45,   59,   47,  279,  269,  125,   59,   59,    0,  125,
  276,  276,  278,  278,  256,   60,   61,   62,   43,  271,
  272,   41,   47,  125,   65,   41,  278,   52,   -1,   -1,
   -1,  273,  274,  275,   -1,   -1,  278,  279,   -1,  271,
  272,   -1,   -1,   -1,   -1,   70,   -1,   -1,   -1,   -1,
   -1,   -1,   77,   78,   79,   80,   81,   82,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,  256,
  257,   -1,  259,  260,   -1,  262,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
  257,  278,   -1,  280,  261,   -1,  263,  264,  256,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
   -1,  278,   -1,  280,   -1,  273,  274,  275,   -1,   -1,
  278,  279,   -1,   -1,   -1,   -1,   -1,  172,   -1,   -1,
   -1,   -1,   -1,  265,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  256,  276,  258,  278,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,   -1,   -1,  256,   -1,  258,   -1,   -1,
  256,   -1,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  273,  274,  275,   -1,   -1,  273,  274,  275,
  257,  256,  259,  260,   -1,  262,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,  273,  274,
  275,  278,   -1,  280,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,   -1,  266,  267,  268,   -1,  270,  271,  272,
   -1,   -1,   -1,  257,  258,  278,  260,  280,   -1,  263,
  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,
   -1,  257,   -1,   -1,  278,  261,  280,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
  257,   -1,  278,  260,  280,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
   -1,  278,   -1,  280,
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
"nombre_funcion : T_ID",
"declaracion_funcion : tipo FUN nombre_funcion '(' parametro ')' bloque_sentencias",
"declaracion_funcion : tipo FUN nombre_funcion '(' parametros_error ')' bloque_sentencias",
"declaracion_funcion : tipo FUN nombre_funcion '(' tipo ')' bloque_sentencias",
"declaracion_funcion : tipo nombre_funcion '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN nombre_funcion '(' parametro ')' bloque_sentencias ';'",
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

//#line 739 "gramatica.y"

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
    if((st.getUse(variable).equals("Nombre de variable par") &&!st.getUse(expresion).equals("Nombre de variable par"))
    ||(!st.getUse(variable).equals("Nombre de variable par") &&st.getUse(expresion).equals("Nombre de variable par"))){
        System.out.println("Warning: No se pueden utilizar los tipos pares en operaciones que conlleven tipos distintos ");        
        System.out.println("Error en asignacion:"+variable + " := " + expresion + ";");
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

//#line 771 "Parser.java"
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
            if(st.isTypePair(val_peek(2).sval)){/*si el tipo*/
                st.updateUse(variable, "Nombre de variable par");
            }else{
	            st.updateUse(variable, "Nombre de variable");
            }


	        /*updatear ambito*/

	    } else {
	        System.err.println("Error, la variable no está en la tabla de símbolos: " + variable);
	    }
	}
}
break;
case 21:
//#line 109 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 22:
//#line 110 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}
break;
case 23:
//#line 114 "gramatica.y"
{
    
    @SuppressWarnings("unchecked")
    List<String> variables = (List<String>) val_peek(2).obj;
    variables.add(val_peek(0).sval);  /* Agregar nueva variable*/
    yyval.obj = variables;  /* Pasar la lista actualizada hacia arriba */
}
break;
case 24:
//#line 121 "gramatica.y"
{
    List<String> variables = new ArrayList<String>();
    variables.add(val_peek(0).sval);  /* Agregar la primera variable*/
    yyval.obj = variables; 
}
break;
case 25:
//#line 126 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 26:
//#line 130 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
    System.out.println("Entre a Funcion antes (o despues?) de la derecha");}
break;
case 27:
//#line 134 "gramatica.y"
{
        
        System.out.println("Entre a la 2da llave");
        /*updatear uso nombre funcion*/
        st.updateUse(val_peek(4).sval, "Nombre de funcion");
        

        /* Separar el tipo y el nombre del parámetro*/
        String[] tipoYNombre = val_peek(2).sval.split(":");
        String tipoParametro = tipoYNombre[0];
        String nombreParametro = tipoYNombre[1];


        /* Insertar en la tabla de funciones*/
        st.insertTF(val_peek(4).sval, new CaracteristicaFuncion(val_peek(6).sval, tipoParametro, nombreParametro));    
    }
break;
case 28:
//#line 150 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }
break;
case 29:
//#line 155 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }
break;
case 30:
//#line 159 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 31:
//#line 163 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    }
break;
case 32:
//#line 166 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se puede poner ; al final de la declaracion de una fucnion");
    }
break;
case 33:
//#line 171 "gramatica.y"
{
        st.updateType(val_peek(0).sval, val_peek(1).sval);
            /*updatear uso de variable a variable*/
            if(st.isTypePair(val_peek(1).sval)){/*si el tipo*/
                st.updateUse(val_peek(0).sval, "Nombre de variable par");
            }else{
	            st.updateUse(val_peek(0).sval, "Nombre de parametro");
            }
        yyval.sval = val_peek(1).sval + ":" + val_peek(0).sval;
        
    }
break;
case 34:
//#line 184 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 35:
//#line 187 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 36:
//#line 190 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion debe tener un parametro.");
    }
break;
case 39:
//#line 200 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 40:
//#line 201 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 41:
//#line 203 "gramatica.y"
{
        
        /* Verificando si el tipo esta en la tabla de tipos definidos*/
        
        if (st.containsKeyTT(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0).sval);
        } 
    }
break;
case 44:
//#line 218 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 45:
//#line 219 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 46:
//#line 222 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 47:
//#line 226 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 48:
//#line 229 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 49:
//#line 233 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 50:
//#line 236 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 51:
//#line 240 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 52:
//#line 243 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 53:
//#line 246 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 54:
//#line 247 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 55:
//#line 248 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 56:
//#line 249 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 58:
//#line 257 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 59:
//#line 260 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
break;
case 60:
//#line 263 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 61:
//#line 266 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 62:
//#line 267 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 65:
//#line 275 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 66:
//#line 278 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 67:
//#line 281 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
break;
case 68:
//#line 282 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta contenido en el OUTF");}
break;
case 69:
//#line 285 "gramatica.y"
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
case 70:
//#line 310 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/
            /*updatear uso*/
            st.updateUse(nombreTipo, "Nombre de tipo de par");
            st.insertTT(nombreTipo, new TipoSubrango("longint", -2147483647, 2147483647));
        }
break;
case 71:
//#line 316 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/
            /*updatear uso*/
            st.updateUse(nombreTipo, "Nombre de tipo de par");
            st.insertTT(nombreTipo, new TipoSubrango("double", -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
break;
case 72:
//#line 322 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
break;
case 73:
//#line 325 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 74:
//#line 328 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 75:
//#line 331 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
break;
case 76:
//#line 334 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 77:
//#line 337 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 78:
//#line 338 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 79:
//#line 339 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 80:
//#line 340 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 81:
//#line 341 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 82:
//#line 342 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 83:
//#line 343 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 84:
//#line 344 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 85:
//#line 345 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 86:
//#line 346 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la asignacion en la definicion de nuevos tipos");}
break;
case 87:
//#line 348 "gramatica.y"
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
//#line 373 "gramatica.y"
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
//#line 399 "gramatica.y"
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
//#line 417 "gramatica.y"
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
//#line 440 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 92:
//#line 441 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 94:
//#line 447 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 95:
//#line 448 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 96:
//#line 449 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 103:
//#line 461 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 104:
//#line 462 "gramatica.y"
{
            /* Obtener las listas de variables y expresiones*/
            List<String> listaVariables = (List<String>) val_peek(3).obj;
            List<String> listaExpresiones = (List<String>) val_peek(1).obj;

            /* Verificar si hay más variables que expresiones*/
            if (listaVariables.size() > listaExpresiones.size()) {
                System.out.println("Warning: Hay más variables que expresiones. Se asignará 0 a las variables sobrantes.");
                for (int i = 0; i < listaVariables.size(); i++) {
                    if (i < listaExpresiones.size()) {
                        String variable= listaVariables.get(i).toString();
                        String expresion= listaExpresiones.get(i).toString();
                        chequeoPares(variable,expresion);
                                               
                    } else {
                        /* Asignar 0 a las variables sobrantes*/
                        System.out.println(listaVariables.get(i).toString() + " := 0;");
                    }
                }
            } else if (listaVariables.size() < listaExpresiones.size()) {
                System.out.println("Warning: Hay más expresiones que variables. Se descartarán las expresiones sobrantes.");
                for (int i = 0; i < listaVariables.size(); i++) {
                    String variable= listaVariables.get(i).toString();
                    String expresion= listaExpresiones.get(i).toString();
                    chequeoPares(variable,expresion);
                       
                }
            } else {
                /* Generar el código para cada asignación correspondiente*/
                for (int i = 0; i < listaVariables.size(); i++) {
                    String variable= listaVariables.get(i).toString();
                    String expresion= listaExpresiones.get(i).toString();
                    chequeoPares(variable,expresion);
                }
            }
        }
break;
case 105:
//#line 499 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
break;
case 106:
//#line 503 "gramatica.y"
{
           /* Crear una nueva lista con una sola expresión*/
           List<String> lista = new ArrayList<>();
           lista.add(val_peek(0).sval);  /* Almacenar la expresión como cadena de texto*/
           yyval.obj = lista;
        }
break;
case 107:
//#line 509 "gramatica.y"
{
            /* Agregar la expresión a la lista existente*/
            List<String> lista = (List<String>) val_peek(2).obj;
            lista.add(val_peek(0).sval);  /* Almacenar la nueva expresión*/
            yyval.obj = lista;
        }
break;
case 108:
//#line 518 "gramatica.y"
{
                /* Agregar el identificador a la lista*/
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yylval.obj = lista;
            }
break;
case 109:
//#line 524 "gramatica.y"
{
                 /* Agregar acceso_par (acceso a atributos o elementos) a la lista*/
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 110:
//#line 530 "gramatica.y"
{
                /* Crear lista con el primer identificador*/
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 111:
//#line 536 "gramatica.y"
{
                /* Crear una nueva lista con acceso_par*/
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 112:
//#line 542 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 113:
//#line 543 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 114:
//#line 544 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 115:
//#line 549 "gramatica.y"
{
            
        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
        }
        
    }
break;
case 116:
//#line 558 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se debe utilizar el indice 1 o 2 para acceder a los pares");}
break;
case 117:
//#line 559 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 119:
//#line 563 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 120:
//#line 564 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 121:
//#line 565 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 122:
//#line 568 "gramatica.y"
{
        /* Verifica que el parámetro no sea nulo antes de intentar convertirlo a cadena*/
        if (val_peek(1).sval != null) {
            yyval.sval = val_peek(3).sval + "(" + val_peek(1).sval + ")";
        } else {
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro de función nulo");
            yyval.sval = val_peek(3).sval + "()";  /* Asume que no hay parámetros si es nulo*/
        }
    }
break;
case 123:
//#line 577 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
break;
case 124:
//#line 582 "gramatica.y"
{
    /* Asegúrate de que el valor de la expresión aritmética se pase correctamente hacia arriba*/
    yyval.sval = val_peek(0).sval;
}
break;
case 125:
//#line 588 "gramatica.y"
{
          yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
      }
break;
case 126:
//#line 591 "gramatica.y"
{
          yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
      }
break;
case 127:
//#line 594 "gramatica.y"
{
          yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
      }
break;
case 128:
//#line 597 "gramatica.y"
{
          yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
      }
break;
case 129:
//#line 600 "gramatica.y"
{
          yyval.sval = val_peek(0).sval;  /* La constante como cadena*/
      }
break;
case 130:
//#line 603 "gramatica.y"
{
          yyval.sval = val_peek(0).sval;  /* El identificador como cadena*/
      }
break;
case 131:
//#line 606 "gramatica.y"
{
          yyval.sval = val_peek(0).sval;  /* El resultado del acceso*/
      }
break;
case 132:
//#line 609 "gramatica.y"
{
          yyval.sval = val_peek(0).sval;  /* El valor unario*/
      }
break;
case 133:
//#line 615 "gramatica.y"
{
            if(isPair(val_peek(0).sval)|| isPair(val_peek(2).sval)){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            /* Devuelve la expresión como una cadena que representa la suma*/
            yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
        }
break;
case 134:
//#line 622 "gramatica.y"
{
            if(isPair(val_peek(0).sval)|| isPair(val_peek(2).sval)){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            /* Devuelve la expresión como una cadena que representa la resta*/
            yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
        }
break;
case 135:
//#line 629 "gramatica.y"
{
            if(isPair(val_peek(0).sval)|| isPair(val_peek(2).sval)){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            /* Devuelve la expresión como una cadena que representa la multiplicación*/
            yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
        }
break;
case 136:
//#line 636 "gramatica.y"
{
            if(isPair(val_peek(0).sval)|| isPair(val_peek(2).sval)){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            /* Devuelve la expresión como una cadena que representa la división*/
            yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
        }
break;
case 137:
//#line 643 "gramatica.y"
{
            /* Devuelve el valor de la constante como cadena*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 138:
//#line 647 "gramatica.y"
{
            /* Devuelve el identificador como cadena*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 139:
//#line 651 "gramatica.y"
{
            /* Devuelve el resultado del acceso a un parámetro*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 140:
//#line 655 "gramatica.y"
{
            /* Devuelve el resultado de la invocación de una función*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 141:
//#line 659 "gramatica.y"
{
            /* Devuelve la expresión unaria*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 142:
//#line 663 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 143:
//#line 666 "gramatica.y"
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
//#line 1841 "Parser.java"
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
