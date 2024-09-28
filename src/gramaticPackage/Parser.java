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
    6,    7,    7,    7,    7,    7,    7,    8,    8,    8,
    8,    8,    8,   11,   11,   11,   11,   11,   11,   11,
   11,   11,   11,   11,   11,   11,   11,   11,   11,   11,
   20,   20,   20,   20,   20,   20,   19,   19,   21,   21,
   21,   21,   21,   21,    5,    5,   22,   22,   22,   22,
   23,   23,   24,   24,   24,   10,   10,   10,   10,   25,
   25,   26,   27,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   27,   29,   29,   29,   29,   29,   29,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   28,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    5,    0,    5,
    3,    2,    3,    1,    1,    7,    7,    7,    7,    7,
    2,    3,    3,    0,    1,    1,    1,    1,    1,    8,
   10,    7,    9,    7,    9,    7,    9,    7,    8,    6,
    8,    7,    6,    5,    6,    5,    7,    5,    5,    4,
    4,    4,    5,    6,    7,    7,    6,    5,    5,    5,
    7,    6,    6,    6,    6,    6,    6,    5,    5,    5,
    5,    6,    6,    7,    2,    1,    3,    3,    1,    1,
    1,    1,    1,    1,    4,    3,    3,    3,    1,    1,
    1,    3,    4,    4,    4,    3,    2,    2,    2,    4,
    1,    1,    3,    3,    3,    3,    1,    1,    1,    1,
    4,    4,    4,    1,    1,    1,    2,    2,    2,    3,
    3,    3,    3,    1,    1,    1,    1,    1,    4,    4,
    4,    2,
};
final static short yydefred[] = {                         0,
    6,    0,    0,    0,    3,    0,    5,    0,    0,    0,
    0,    0,   38,   37,    0,   17,    0,    8,    9,   10,
   11,   12,   13,   14,   15,   16,    0,    0,  100,    1,
  111,    0,  134,    0,    0,    0,    0,  136,  137,  138,
    0,    0,    0,    0,    0,    0,    0,   35,   36,    0,
  109,    0,  107,    0,    4,    7,   25,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   89,   90,   91,
    0,    0,    0,    0,   93,   94,   92,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   39,    0,
    0,    0,    0,    0,    0,    0,    0,  106,    0,    0,
    0,    0,    0,    0,   21,    0,    0,    0,    0,   98,
    0,  117,  119,    0,    0,  120,    0,    0,    0,  124,
  125,  126,    0,    0,    0,  132,    0,  133,    0,    0,
    0,    0,   62,    0,    0,    0,    0,    0,    0,    0,
    0,   86,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  105,  103,  104,    0,    0,    0,   23,
   95,    0,  110,    0,    0,    0,    0,    0,    0,    0,
  127,  129,  128,    0,  140,  141,    0,    0,   58,   63,
   59,   69,   68,    0,    0,    0,    0,    0,    0,   85,
   78,   80,    0,   79,    0,    0,   18,   20,   54,    0,
    0,    0,   56,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  115,    0,  116,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   50,    0,   76,    0,   77,    0,
   67,    0,    0,   64,   74,   75,    0,   55,    0,   31,
    0,    0,    0,    0,    0,    0,    0,    0,  122,  123,
    0,   44,    0,   48,    0,    0,    0,   46,    0,   65,
   66,   71,    0,    0,    0,   57,   52,   28,   26,    0,
   32,   27,   33,   30,   29,    0,   49,    0,   40,    0,
   51,   81,    0,    0,    0,   45,    0,   47,   83,   82,
    0,   41,   84,
};
final static short yydgoto[] = {                          4,
   48,   17,   49,   19,   20,   21,   22,   23,   24,   25,
   26,   36,  198,   27,   60,  205,  206,   50,   37,  144,
   78,   28,  108,   38,   39,  114,  115,   40,  124,
};
final static short yysindex[] = {                      -233,
    0,  703, -238,    0,    0,   85,    0,  -14,  238,   11,
  647,   75,    0,    0,  -91,    0,  725,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -235,  -41,    0,    0,
    0,  -27,    0,  285,  -39,  737, -191,    0,    0,    0,
  484,   86, -222, -217, -198,  285,   39,    0,    0, -199,
    0,   26,    0,  106,    0,    0,    0,  -20,   70,  -13,
  285, -164,   -4,  -23, -140,   88,  285,    0,    0,    0,
   50,  285,   50,   50,    0,    0,    0,  285,  685,   90,
  -27,  114,  108,  139,    0, -151, -121,  210,    0, -109,
 -112,  112,  121,  432,  285,  153,  132,    0,   74,   91,
   93,  173, -222, -222,    0,  -83,  569,    4,  -91,    0,
  -91,    0,    0,  185,  595,    0,  685,  666,  569,    0,
    0,    0,  -23,  488,  -23,    0,  488,    0,  488,  569,
 -111,  227,    0,  241,  264,  266,  268,  253,  274,  283,
   83,    0,   -3,  294,  -51,  310,  107,  115,  327,  349,
  285,  460,  341,    0,    0,    0, -222,  130,  131,    0,
    0,  285,    0,   81,   -4,   81,   81, -101,  604,  -72,
    0,    0,    0,  -23,    0,    0,  685,  353,    0,    0,
    0,    0,    0,  -54,  -47,  136,  359,  376,  146,    0,
    0,    0,  386,    0,  394,  395,    0,    0,    0,  414,
  406,  435,    0,   -8,  144,  162,  442,  445,  569,   28,
  155,   28,    0,  155,    0,  155,  685,  434,  240,  444,
  -52,  685,  450,  242,    0,  451,    0,  452,    0,  462,
    0,  -17,  391,    0,    0,    0,  473,    0,  480,    0,
 -238, -238, -222, -238, -222, -238, -238,   28,    0,    0,
  284,    0,  491,    0,  685,  506,  304,    0,  510,    0,
    0,    0,  446,  297,    2,    0,    0,    0,    0,  302,
    0,    0,    0,    0,    0,  528,    0,  330,    0,  536,
    0,    0,  472,  477,  331,    0,  547,    0,    0,    0,
  493,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  613,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  518,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -7,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  180,    0,    0,    0,    0,    0,    0,  -34,  200,
    0,    0,    0,   21,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  513,    0,    0,    0,   98,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  135,  228,   14,    0,
  543,    0,    0,    0,  580,    0,    0,    0,   53,    0,
    0,    0,   46,    0,   76,    0,    0,    0,    0,  137,
    0,  256,    0,    0,  281,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  311,    0,
    0,    0,    0,    0,    0,    0,  167,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  109,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  337,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  160,   43,
    0,   59,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  363,    0,  388,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  408,    0,
    0,    0,    0,    0,    0,    0,    0,   68,    0,    0,
    0,    0,    0,    0,    0,  428,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  456,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   27,    0,    0,    0,    0,    0,    0,    0,
    0,  754,    0,  654,    0, -142,    0,  635,  -19,  -74,
    0,    0,    0,   -2,    0,    0,  606,  642,   25,
};
final static int YYTABLESIZE=1012;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
    5,   65,   62,   30,  227,   34,   24,  192,   29,   24,
  143,  229,   63,  143,   29,   66,  146,    1,   73,  103,
   57,    2,    1,   74,   24,   41,    2,  264,   18,   58,
  106,   54,  241,  135,  135,  135,  135,  135,   85,  135,
   34,  189,   59,   56,    3,  105,  285,  162,   13,   14,
   46,  135,  135,  135,  135,   89,   96,   97,   91,  110,
  113,  142,  161,  142,  142,  142,   79,   83,   97,  166,
  193,  143,   92,   93,  167,  150,   29,  153,   95,  142,
  142,  142,  142,  113,   98,  113,  130,  113,  130,  130,
  130,  121,  120,   88,   34,   54,  122,  127,  129,  114,
  271,  114,  273,  114,  130,  130,  130,  130,  121,  104,
  121,   88,  121,  109,   29,   29,  131,  117,  131,  131,
  131,  190,  121,  120,   35,   34,  136,  122,  118,   34,
  132,  200,  202,   53,  131,  131,  131,  131,  136,  136,
  136,  100,  136,  142,  136,   88,  142,  177,  134,  139,
  178,  139,  139,  139,  100,  101,  137,  217,   13,   14,
  218,  113,  113,  113,  113,   89,   29,  139,  139,  139,
  139,  152,  133,  147,   29,  101,   34,   87,  101,  135,
   73,   71,  148,   72,  242,   74,  222,  243,  211,  223,
  214,  216,  151,  101,  160,   87,  172,  171,  154,   34,
  102,  173,  244,  102,  142,  245,  255,   34,  113,  256,
   34,  113,  157,  113,   29,  155,   31,  156,  102,   29,
  108,   24,   24,  226,   24,  163,   24,   24,   24,   24,
  228,   24,   24,   24,   61,   24,   24,   24,   32,   33,
   22,  268,  269,   24,  272,   24,  274,  275,  135,  135,
  135,  135,   29,  135,  135,  135,  135,  102,  135,  135,
  135,  263,  135,  135,  135,  135,  135,  135,   96,  240,
  135,  141,  135,  111,  112,  188,  142,  142,  142,  142,
  284,  142,  142,  142,  142,  179,  142,  142,  142,   97,
  142,  142,  142,  142,  142,  142,   61,   45,  142,  180,
  142,  130,  130,  130,  130,   31,  130,  130,  130,  130,
   88,  130,  130,  130,  184,  130,  130,  130,  130,  130,
  130,   60,  181,  130,  182,  130,  183,   32,   33,   34,
   51,  131,  131,  131,  131,  185,  131,  131,  131,  131,
   31,  131,  131,  131,  186,  131,  131,  131,  131,  131,
  131,   19,  191,  131,   52,  131,   86,   87,  111,  112,
  187,   99,   32,   33,  139,  139,  139,  139,  194,  139,
  139,  139,  139,  100,  139,  139,  139,   70,  139,  139,
  139,  139,  139,  139,  195,  197,  139,   31,  139,  199,
  101,  101,  196,  101,   87,  101,  101,  101,  101,  203,
  101,  101,  101,   72,  101,  101,  101,  207,  208,   32,
   33,  225,  101,  230,  101,  102,  102,  231,  102,  232,
  102,  102,  102,  102,  233,  102,  102,  102,   73,  102,
  102,  102,  111,  112,  265,  108,  108,  102,  108,  102,
  108,  108,  108,  108,  234,  108,  108,  108,   53,  108,
  108,  108,  235,  236,  237,   22,   22,  108,   22,  108,
   22,   22,   22,   22,  238,   22,   22,   22,   42,   22,
   22,   22,  149,   73,   71,  239,   72,   22,   74,   22,
  138,  139,  246,   96,   96,  247,   96,  140,   96,   96,
   96,   96,  252,   96,   96,   96,   43,   96,   96,   96,
  201,  253,  254,  259,   34,   96,   42,   96,  258,  260,
  261,   61,   61,   43,   61,   44,   61,   61,   61,   61,
  262,   61,   61,   61,   82,   61,   61,   61,   34,  172,
  171,  266,   34,   61,  173,   61,   60,   60,  267,   60,
   31,   60,   60,   60,   60,  276,   60,   60,   60,  277,
   60,   60,   60,  135,  135,  135,   99,  135,   60,  135,
   60,   99,   32,   33,  279,  280,   19,   19,  281,   19,
  282,   19,   19,   19,   19,  283,   19,   19,   19,  240,
   19,   19,   19,  118,  118,  118,  286,  118,   19,  118,
   19,  287,   70,   70,  288,   70,  289,   70,   70,   70,
   70,  290,   70,   70,   70,  292,   70,   70,   70,  291,
   73,   71,    2,   72,   70,   74,   70,  293,   72,   72,
  112,   72,    0,   72,   72,   72,   72,    0,   72,   72,
   72,    0,   72,   72,   72,    0,  166,  164,    0,  165,
   72,  167,   72,   73,   73,    0,   73,    0,   73,   73,
   73,   73,    0,   73,   73,   73,    0,   73,   73,   73,
    0,    0,    0,   53,   53,   73,   53,   73,   53,   53,
   53,   53,    0,   53,   53,   53,    0,   53,   53,   53,
    0,    0,    0,   42,   42,   53,   42,   53,   42,   42,
   42,   42,    0,   42,   42,   42,   90,   42,   42,   42,
    0,    0,    0,    0,  116,   42,    0,   42,    0,    0,
    0,   43,   43,  131,   43,   31,   43,   43,   43,   43,
    0,   43,   43,   43,    0,   43,   43,   43,    0,    0,
    0,    0,    0,   43,    0,   43,    0,   32,   33,   31,
    6,    0,    0,   31,  145,    0,    8,    9,    0,   10,
   11,  168,  170,   12,   13,   14,  158,  159,    0,    0,
   80,   81,   33,   16,    0,   32,   33,    0,   39,  210,
  212,  213,  215,   39,    0,    0,    0,   39,   73,   71,
    0,   72,   39,   74,    0,    0,    0,   64,   99,    0,
   39,    0,    0,   99,   84,   39,   75,   77,   76,   94,
    0,    0,    0,  221,    0,  116,  116,  116,  116,    0,
  204,  224,    0,    0,  107,    0,  248,    0,    0,  249,
  119,  250,    0,    0,  123,  125,  126,  128,    0,    0,
    0,  130,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  251,  116,    0,    0,  116,  257,  116,    0,    1,
    6,    0,  219,    2,    0,  220,    8,    9,    0,   10,
   11,    0,    0,   12,   13,   14,    0,  174,    0,    0,
  175,   15,  176,   16,    0,    0,    0,    0,    0,  278,
    0,    0,    0,    0,    0,    0,  270,    0,  270,    0,
    0,    0,    1,    6,    0,    0,    2,    0,    0,    8,
    9,    0,   10,   11,   47,  209,   12,   13,   14,    0,
    0,    1,    6,  169,   15,    2,   16,    0,    8,    9,
    0,   10,   11,    0,    0,   12,   13,   14,    0,    0,
    1,    6,    0,   15,    2,   16,    0,    8,    9,    0,
   10,   11,    0,    0,   12,   13,   14,    0,    0,    6,
    0,    0,   15,    7,   16,    8,    9,    0,   10,   11,
    0,    0,   12,   13,   14,    0,    0,    0,    0,    0,
   15,    6,   16,    0,    0,   55,    0,    8,    9,    0,
   10,   11,   67,    0,   12,   13,   14,    0,    0,    0,
    0,    0,   15,    0,   16,    0,    0,    0,    0,   68,
   69,   70,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          2,
    0,   41,   44,    3,   59,   45,   41,   59,   11,   44,
  123,   59,   40,  123,   17,   35,   91,  256,   42,   40,
  256,  260,  256,   47,   59,   40,  260,   45,    2,  265,
   44,  123,   41,   41,   42,   43,   44,   45,   41,   47,
   45,   45,  278,   17,  278,   59,   45,   44,  271,  272,
   40,   59,   60,   61,   62,  278,  256,   44,  276,   62,
   63,   41,   59,   43,   44,   45,  258,   41,  268,   42,
  145,  123,  271,  272,   47,   95,   79,   97,   40,   59,
   60,   61,   62,   41,   59,   43,   41,   45,   43,   44,
   45,   42,   43,   41,   45,  123,   47,   73,   74,   41,
  243,   43,  245,   45,   59,   60,   61,   62,   41,   40,
   43,   59,   45,  278,  117,  118,   41,  258,   43,   44,
   45,  125,   42,   43,   40,   45,  278,   47,   41,   45,
   41,  151,  152,   59,   59,   60,   61,   62,   41,   42,
   43,   44,   45,  256,   47,   60,  256,  259,   41,   41,
  262,   43,   44,   45,   49,   50,  278,  259,  271,  272,
  262,  164,  165,  166,  167,  278,  169,   59,   60,   61,
   62,   40,   59,   62,  177,   41,   45,   41,   44,   41,
   42,   43,   62,   45,   41,   47,  259,   44,  164,  262,
  166,  167,   40,   59,  278,   59,   42,   43,  125,   45,
   41,   47,   41,   44,  256,   44,  259,   41,  211,  262,
   44,  214,   40,  216,  217,  125,  256,  125,   59,  222,
   41,  256,  257,  278,  259,   41,  261,  262,  263,  264,
  278,  266,  267,  268,  276,  270,  271,  272,  278,  279,
   41,  241,  242,  278,  244,  280,  246,  247,  256,  257,
  258,  259,  255,  261,  262,  263,  264,  278,  266,  267,
  268,  279,  270,  271,  272,  273,  274,  275,   41,  278,
  278,   62,  280,  278,  279,  279,  256,  257,  258,  259,
  279,  261,  262,  263,  264,   59,  266,  267,  268,  276,
  270,  271,  272,  273,  274,  275,   41,   60,  278,   59,
  280,  256,  257,  258,  259,  256,  261,  262,  263,  264,
  258,  266,  267,  268,   62,  270,  271,  272,  273,  274,
  275,   41,   59,  278,   59,  280,   59,  278,  279,   45,
  256,  256,  257,  258,  259,   62,  261,  262,  263,  264,
  256,  266,  267,  268,   62,  270,  271,  272,  273,  274,
  275,   41,   59,  278,  280,  280,  271,  272,  278,  279,
  278,  256,  278,  279,  256,  257,  258,  259,   59,  261,
  262,  263,  264,  276,  266,  267,  268,   41,  270,  271,
  272,  273,  274,  275,  278,   59,  278,  256,  280,   41,
  256,  257,  278,  259,  258,  261,  262,  263,  264,   59,
  266,  267,  268,   41,  270,  271,  272,  278,  278,  278,
  279,   59,  278,  278,  280,  256,  257,   59,  259,   44,
  261,  262,  263,  264,  279,  266,  267,  268,   41,  270,
  271,  272,  278,  279,   44,  256,  257,  278,  259,  280,
  261,  262,  263,  264,   59,  266,  267,  268,   41,  270,
  271,  272,   59,   59,   41,  256,  257,  278,  259,  280,
  261,  262,  263,  264,   59,  266,  267,  268,   41,  270,
  271,  272,   41,   42,   43,   41,   45,  278,   47,  280,
  271,  272,   41,  256,  257,   41,  259,  278,  261,  262,
  263,  264,   59,  266,  267,  268,   41,  270,  271,  272,
   41,  262,   59,  262,   45,  278,  269,  280,   59,   59,
   59,  256,  257,  276,  259,  278,  261,  262,  263,  264,
   59,  266,  267,  268,   41,  270,  271,  272,   45,   42,
   43,   59,   45,  278,   47,  280,  256,  257,   59,  259,
  256,  261,  262,  263,  264,  262,  266,  267,  268,   59,
  270,  271,  272,   41,   42,   43,   44,   45,  278,   47,
  280,   44,  278,  279,   59,  262,  256,  257,   59,  259,
  125,  261,  262,  263,  264,  279,  266,  267,  268,  278,
  270,  271,  272,   41,   42,   43,   59,   45,  278,   47,
  280,  262,  256,  257,   59,  259,  125,  261,  262,  263,
  264,  125,  266,  267,  268,   59,  270,  271,  272,  279,
   42,   43,    0,   45,  278,   47,  280,  125,  256,  257,
   41,  259,   -1,  261,  262,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   -1,   42,   43,   -1,   45,
  278,   47,  280,  256,  257,   -1,  259,   -1,  261,  262,
  263,  264,   -1,  266,  267,  268,   -1,  270,  271,  272,
   -1,   -1,   -1,  256,  257,  278,  259,  280,  261,  262,
  263,  264,   -1,  266,  267,  268,   -1,  270,  271,  272,
   -1,   -1,   -1,  256,  257,  278,  259,  280,  261,  262,
  263,  264,   -1,  266,  267,  268,   43,  270,  271,  272,
   -1,   -1,   -1,   -1,   63,  278,   -1,  280,   -1,   -1,
   -1,  256,  257,   79,  259,  256,  261,  262,  263,  264,
   -1,  266,  267,  268,   -1,  270,  271,  272,   -1,   -1,
   -1,   -1,   -1,  278,   -1,  280,   -1,  278,  279,  256,
  257,   -1,   -1,  256,   91,   -1,  263,  264,   -1,  266,
  267,  117,  118,  270,  271,  272,  103,  104,   -1,   -1,
  277,  278,  279,  280,   -1,  278,  279,   -1,  256,  164,
  165,  166,  167,  256,   -1,   -1,   -1,  265,   42,   43,
   -1,   45,  265,   47,   -1,   -1,   -1,   34,  276,   -1,
  278,   -1,   -1,  276,   41,  278,   60,   61,   62,   46,
   -1,   -1,   -1,  169,   -1,  164,  165,  166,  167,   -1,
  157,  177,   -1,   -1,   61,   -1,  211,   -1,   -1,  214,
   67,  216,   -1,   -1,   71,   72,   73,   74,   -1,   -1,
   -1,   78,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  217,  211,   -1,   -1,  214,  222,  216,   -1,  256,
  257,   -1,  259,  260,   -1,  262,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,  124,   -1,   -1,
  127,  278,  129,  280,   -1,   -1,   -1,   -1,   -1,  255,
   -1,   -1,   -1,   -1,   -1,   -1,  243,   -1,  245,   -1,
   -1,   -1,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,  266,  267,  268,  162,  270,  271,  272,   -1,
   -1,  256,  257,  258,  278,  260,  280,   -1,  263,  264,
   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,
  256,  257,   -1,  278,  260,  280,   -1,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,  257,
   -1,   -1,  278,  261,  280,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,
  278,  257,  280,   -1,   -1,  261,   -1,  263,  264,   -1,
  266,  267,  256,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,   -1,  278,   -1,  280,   -1,   -1,   -1,   -1,  273,
  274,  275,
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
"comparador : MENOR_IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : DISTINTO",
"comparador : '='",
"comparador : '<'",
"comparador : '>'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list ';'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : acceso_par",
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"acceso_par : T_ID '{' '1' '}'",
"acceso_par : T_ID '{' '2' '}'",
"acceso_par : T_ID '{' error '}'",
"goto_statement : GOTO T_ETIQUETA ';'",
"goto_statement : GOTO ';'",
"goto_statement : GOTO T_ETIQUETA",
"goto_statement : GOTO error",
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

//#line 438 "gramatica.y"

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
//#line 766 "Parser.java"
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
case 8:
//#line 71 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 19:
//#line 83 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
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
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
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
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables");}
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
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }
break;
case 28:
//#line 129 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }
break;
case 29:
//#line 133 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 30:
//#line 137 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
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
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 33:
//#line 150 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 34:
//#line 153 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
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
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 43:
//#line 182 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 44:
//#line 186 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 45:
//#line 189 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 46:
//#line 193 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 47:
//#line 196 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 48:
//#line 200 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 49:
//#line 203 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 50:
//#line 206 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 51:
//#line 207 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 53:
//#line 213 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 54:
//#line 216 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 55:
//#line 219 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 56:
//#line 222 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 57:
//#line 223 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 59:
//#line 230 "gramatica.y"
{     
        System.out.println("Llegue a salida");   
        }
break;
case 60:
//#line 233 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 61:
//#line 236 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 62:
//#line 239 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 63:
//#line 240 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");}
break;
case 64:
//#line 243 "gramatica.y"
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
case 65:
//#line 263 "gramatica.y"
{
            /* Obtener el nombre del tipo desde T_ID*/
            String nombreTipo = val_peek(4).sval; /* T_ID*/

            /* Obtener el tipo base (INTEGER o SINGLE)*/
            String tipoBase = val_peek(2).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            //tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        }
break;
case 66:
//#line 272 "gramatica.y"
{

        }
break;
case 67:
//#line 275 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaración de tipo.");
        }
break;
case 68:
//#line 278 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 69:
//#line 281 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaración de tipo.");
        }
break;
case 70:
//#line 284 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
break;
case 71:
//#line 287 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 72:
//#line 290 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 73:
//#line 291 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 74:
//#line 292 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 75:
//#line 293 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 76:
//#line 294 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 77:
//#line 295 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 78:
//#line 296 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 79:
//#line 297 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 80:
//#line 298 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 81:
//#line 300 "gramatica.y"
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
case 82:
//#line 322 "gramatica.y"
{ System.out.println("Llegue a subrango con - en el primero");}
break;
case 83:
//#line 323 "gramatica.y"
{System.out.println("Llegue a subrango con - en el segundo");}
break;
case 84:
//#line 324 "gramatica.y"
{System.out.println("Llegue a subrango con - en los dos");}
break;
case 85:
//#line 325 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 86:
//#line 327 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 88:
//#line 333 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la expresion");}
break;
case 95:
//#line 345 "gramatica.y"
{}
break;
case 96:
//#line 346 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion");}
break;
case 97:
//#line 348 "gramatica.y"
{
               
                }
break;
case 98:
//#line 351 "gramatica.y"
{
                }
break;
case 99:
//#line 354 "gramatica.y"
{ 
                }
break;
case 100:
//#line 358 "gramatica.y"
{
                }
break;
case 101:
//#line 363 "gramatica.y"
{
      }
break;
case 102:
//#line 365 "gramatica.y"
{
      }
break;
case 103:
//#line 373 "gramatica.y"
{
          
    }
break;
case 104:
//#line 376 "gramatica.y"
{}
break;
case 105:
//#line 377 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Solo se puede acceder a un par con 1 o 2");}
break;
case 107:
//#line 381 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 108:
//#line 382 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 109:
//#line 383 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 110:
//#line 385 "gramatica.y"
{
      }
break;
case 111:
//#line 387 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida"); /*CAMBIAR*/
        }
break;
case 121:
//#line 402 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 122:
//#line 403 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 123:
//#line 404 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + "Error: Dos o mas operadores juntos");}
break;
case 130:
//#line 408 "gramatica.y"
{
        }
break;
case 131:
//#line 410 "gramatica.y"
{
        }
break;
case 132:
//#line 412 "gramatica.y"
{
        }
break;
case 133:
//#line 414 "gramatica.y"
{
        }
break;
case 134:
//#line 416 "gramatica.y"
{
        }
break;
case 135:
//#line 418 "gramatica.y"
{
        }
break;
case 136:
//#line 420 "gramatica.y"
{
        }
break;
case 137:
//#line 422 "gramatica.y"
{
        }
break;
case 138:
//#line 424 "gramatica.y"
{ /* Se añade la regla para operadores unarios*/
        }
break;
case 139:
//#line 426 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 140:
//#line 427 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 141:
//#line 428 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 142:
//#line 432 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
    yyval.dval = -val_peek(0).dval;
}
break;
//#line 1497 "Parser.java"
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
