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
    0,    0,    0,    1,    1,    2,    2,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    4,    4,    4,   14,   14,   14,    9,    9,    9,    9,
    9,    9,   15,   16,   16,   16,   17,   17,   13,   13,
   13,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    6,    6,    6,    6,    6,    6,    7,    7,    7,    7,
    7,    7,    8,    8,    8,    8,    8,   11,   11,   11,
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
    1,    1,    1,    1,    1,    1,    5,    4,    3,    2,
    3,    3,    2,    3,    1,    2,    7,    7,    7,    7,
    7,    8,    2,    3,    3,    0,    1,    1,    1,    1,
    1,    8,   10,    9,    7,    9,    7,    9,    7,    9,
    7,    8,    6,    8,    7,    9,    7,    6,    5,    6,
    5,    7,    5,    5,    4,    4,    5,    6,    7,    7,
    6,    5,    5,    5,    7,    6,    6,    6,    6,    6,
    6,    5,    5,    5,    5,    5,    6,    6,    7,    2,
    1,    3,    3,    2,    2,    1,    1,    1,    1,    1,
    1,    4,    4,    3,    1,    3,    3,    3,    1,    1,
    3,    3,    3,    4,    4,    2,    3,    2,    2,    2,
    4,    4,    1,    3,    3,    3,    3,    1,    1,    1,
    1,    3,    3,    3,    3,    1,    1,    1,    1,    1,
    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    3,    0,    0,    5,    0,    0,    0,
    0,    0,   40,   39,    0,   16,    0,    6,    8,    9,
   10,   11,   12,   13,   14,   15,    0,    0,    0,    1,
   20,  141,   96,   97,   98,    0,  136,    0,    0,  100,
  101,   99,    0,    0,    0,  138,  139,  140,    0,    0,
    0,    0,    0,    0,    0,   37,   38,    0,  120,    0,
  118,    0,  116,    0,    4,    7,    0,    0,   23,    0,
    0,    0,    0,    0,  142,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   41,    0,    0,    0,    0,    0,
   19,    0,    0,    0,    0,  117,    0,  112,    0,    0,
    0,    0,    0,   22,   26,   21,    0,  104,    0,    0,
    0,  108,    0,  111,    0,    0,  128,  130,    0,    0,
  131,    0,    0,    0,    0,    0,  134,  135,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   91,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  115,  114,    0,    0,    0,   24,  102,  103,
    0,  122,  121,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   63,   67,   64,   73,   72,    0,    0,    0,
    0,    0,    0,   90,   82,   84,    0,   83,   85,    0,
    0,   17,   59,    0,    0,    0,   61,    0,    0,    0,
    0,    0,    0,    0,    0,  126,  127,    0,    0,    0,
    0,    0,    0,    0,    0,   53,    0,   80,    0,   81,
    0,   71,    0,    0,   68,   78,   79,    0,   60,    0,
   33,    0,    0,    0,    0,    0,    0,    0,    0,   47,
    0,   51,    0,    0,    0,    0,    0,   49,    0,   69,
   70,   75,    0,    0,    0,   62,   57,   29,    0,    0,
   34,   28,   35,   31,   30,    0,   52,    0,   42,    0,
    0,   54,   86,    0,    0,    0,   32,   48,   56,    0,
   44,   50,   88,   87,    0,   43,   89,
};
final static short yydgoto[] = {                          3,
   56,   17,   57,   19,   20,   21,   22,   23,   24,   25,
   26,   43,   27,   70,  209,  210,   58,   44,  152,   45,
   28,  120,   46,   47,  129,  130,   48,
};
final static short yysindex[] = {                      -228,
  632, -240,    0,    0,  -29,  412,    0,    6,  351,   16,
  595,  262,    0,    0, -105,    0,  650,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   83,    3, -174,    0,
    0,    0,    0,    0,    0,  -17,    0, -178,  284,    0,
    0,    0,  148, -153,  116,    0,    0,    0,  334,  210,
 -218, -247, -107,   18,   70,    0,    0, -235,    0,   55,
    0, -160,    0, -230,    0,    0,  -36,   86,    0,  -42,
   44, -150, -145,  141,    0,  -91,  106,  116,  116,  116,
  116,  116,  116,  669,   66,  -29,  128,   -6,  129,   52,
 -174, -101,  -99,  353,    0, -111, -112, -111,  119,  120,
    0,   93,  384,  131,  440,    0, -114,    0,   58,   60,
  144, -218, -218,    0,    0,    0,  -90,    0,   66,   48,
 -114,    0, -114,    0,  151, -114,    0,    0,  153,  107,
    0,  669,  614,   66,   33,   33,    0,    0,   66, -195,
  130,  137,  145,  147,  152,  138,  143,  154,  -71,    0,
  -10,  159,  -54,  161,  314,  -65,  -63,  327,  355,  384,
  309,  339,    0,    0, -218,  122,  158,    0,    0,    0,
  116,    0,    0,   41,   41,   41,   41, -194,  466, -185,
  669,  366,    0,    0,    0,    0,    0,  -53,  -46,  162,
  388,  404,  174,    0,    0,    0,  391,    0,    0,  395,
  405,    0,    0,  427,  416,  435,    0,  -14,   40,   47,
  437,  451,   66,   43,   43,    0,    0,  669,  444,  234,
  445,  553,  669,  447,  246,    0,  452,    0,  463,    0,
  475,    0,   -3,  499,    0,    0,    0,  479,    0,  486,
    0, -240, -240, -218, -240, -218, -240, -240,  285,    0,
  487,    0,  -29,  669,  489,  294,  298,    0,  502,    0,
    0,    0,  439,  287,    7,    0,    0,    0,  512,  295,
    0,    0,    0,    0,    0,  513,    0, -211,    0,  516,
  520,    0,    0,  460,  464,  320,    0,    0,    0,  549,
    0,    0,    0,    0,  491,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,  610,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  331,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   14,    0,
    0,    0,    0,    0,    0,  508,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -33,
    0,    0,    0,    0,    0,    0,    0,  -37,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   37,    0,   57,  156,    0,  485,    0,    0,
   -4,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   80,    0,
   22,    0,   35,    0,    0,  494,    0,    0,    0,  580,
    0,    0,    0,   82,  533,  574,    0,    0,   89,    0,
   -5,    0,   21,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   46,    0,    0,
    0,    0,    0,    0,  102,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   71,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  109,   59,   84,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   96,    0,  121,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  146,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  171,    0,  199,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  227,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  253,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   27,    0,    0,    0,    0,    0,    0,    0,
    0,  594,  529,    0, -189,    0,  470,  547,  -83,  579,
    0,    0,   -1,    0,    0,  -19,  -55,
};
final static int YYTABLESIZE=949;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         29,
    4,  117,   30,  112,  196,  228,   25,  119,   64,   29,
  151,  151,  230,  154,  155,   29,  116,   64,  131,    1,
  104,   25,   74,   13,   14,  109,  242,   18,   97,   31,
   95,    1,  105,   74,  193,   66,  138,  138,  138,  110,
  138,  264,  138,   66,  289,   49,   72,   91,  110,    2,
  290,  286,   13,   14,  271,   54,  273,  110,  101,   95,
  108,   65,   38,  181,  218,  107,  182,  219,  151,  197,
  122,  124,  128,  223,   81,   89,  224,   94,  113,   82,
  243,   73,   29,  244,  176,   38,   18,  245,   38,  177,
  246,  171,  143,   81,   79,   94,   80,   95,   82,  124,
   75,  124,  118,  124,   84,   64,  170,   81,   79,  103,
   80,   74,   82,  106,  194,   95,   64,  107,  131,  131,
  131,  131,   93,  105,  125,  113,  125,  121,  125,   92,
   29,   29,  123,  158,   81,   79,   76,   80,  105,   82,
   93,   69,   36,  150,  150,   36,  133,   92,  176,  174,
   62,  175,  106,  177,  214,  215,  216,  217,   13,   14,
   38,   77,   63,   99,  100,   95,  132,  106,  141,  142,
  160,   63,  128,  128,  128,  128,  144,   29,  145,   29,
  156,  157,  163,  165,  164,   38,   58,  168,  183,   81,
   79,  172,   80,  173,   82,  184,  141,  141,  141,  188,
  141,  150,  141,  185,  189,  186,  191,   40,   42,   41,
  187,   55,  200,  114,  201,  190,   29,  195,   25,  198,
   29,   29,  119,  119,  227,  119,  119,  119,  119,  119,
  119,  229,  119,  119,  119,  115,  119,  119,  119,   45,
   25,  111,  268,  269,  119,  272,  119,  274,  275,   62,
   66,   66,   29,   66,   66,   66,   66,   66,   66,   63,
   66,   66,   66,  241,   66,   66,   66,   27,  192,   94,
   63,  110,   66,   32,   66,  263,   65,   65,   71,   65,
   65,   65,   65,   65,   65,  285,   65,   65,   65,  110,
   65,   65,   65,   46,   94,   36,   37,  107,   65,   32,
   65,   18,   18,  169,   18,   18,   18,   18,   18,   18,
  113,   18,   18,   18,   95,   18,   18,   18,  126,  127,
   61,   36,   37,   18,   76,   18,   74,   74,   38,   74,
   74,   74,   74,   74,   74,  105,   74,   74,   74,   93,
   74,   74,   74,   40,   42,   41,   92,   67,   74,  205,
   74,   76,   76,   38,   76,   76,   76,   76,   76,   76,
   68,   76,   76,   76,  106,   76,   76,   76,   40,   42,
   41,   32,  199,   76,  109,   76,   77,   77,   38,   77,
   77,   77,   77,   77,   77,  202,   77,   77,   77,   41,
   77,   77,   77,   36,   37,  203,  125,  207,   77,  211,
   77,   58,   58,   78,   58,   58,   58,   58,   58,   58,
   53,   58,   58,   58,  149,   58,   58,   58,  126,  127,
   33,   34,   35,   58,  226,   58,   55,   55,   38,   55,
   55,   55,   55,   55,   55,  212,   55,   55,   55,  231,
   55,   55,   55,   40,   42,   41,  232,  233,   55,  235,
   55,   39,  234,  236,   45,   45,   38,   45,   45,   45,
   45,   45,   45,  237,   45,   45,   45,  238,   45,   45,
   45,   40,   42,   41,  239,  240,   45,  247,   45,  161,
   92,   93,   27,   27,   38,   27,   27,   27,   27,   27,
   27,  248,   27,   27,   27,  251,   27,   27,   27,   40,
   42,   41,  250,  252,   27,  258,   27,  259,   46,   46,
  260,   46,   46,   46,   46,   46,   46,   59,   46,   46,
   46,  261,   46,   46,   46,  137,  137,  137,  109,  137,
   46,  137,   46,  262,  129,  129,  129,  266,  129,   32,
  129,   60,  265,   41,  267,  277,  276,  279,  137,  137,
  137,  137,  137,  140,  137,  280,   33,   34,   35,  281,
  282,   36,   37,  283,   32,  284,  137,  137,  137,  137,
  287,  288,  241,  132,  291,  132,  132,  132,  292,   96,
   98,   33,   34,   35,  293,   77,   36,   37,  294,   86,
    6,  132,  132,  132,  132,   41,    8,    9,  295,   10,
   11,  178,  180,   12,   13,   14,  109,  296,   41,    2,
   87,   88,   37,   16,  133,  297,  133,  133,  133,   50,
  123,   83,    0,  146,  147,  153,   51,    0,   52,    0,
  148,    0,  133,  133,  133,  133,    0,    0,   85,   32,
  166,  167,   90,    0,    0,    0,    0,  102,  222,  159,
  225,  162,    0,    0,    0,    0,   33,   34,   35,    0,
    0,   36,   37,    0,  119,    0,    0,   32,    0,    0,
    0,  134,  135,  136,  137,  138,  139,    0,    0,    0,
    0,    0,    0,    0,   33,   34,   35,  249,    0,   36,
   37,  256,  257,  208,    0,   32,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  204,  206,    0,    0,
    0,    0,   33,   34,   35,    0,    0,   36,   37,    0,
    0,    5,    6,  278,  220,    1,    0,  221,    8,    9,
    0,   10,   11,    0,    0,   12,   13,   14,    0,    0,
    0,    0,    0,   15,    0,   16,    0,    0,    0,   41,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  109,    0,   41,  137,  213,  137,    0,    0,    0,    0,
    0,    0,  270,    0,  270,    0,    0,    0,    0,    0,
  137,  137,  137,    0,    0,    0,    0,    0,  132,    0,
  132,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  132,  132,  132,  253,    6,
    0,  254,    1,    0,  255,    8,    9,    0,   10,   11,
    0,    0,   12,   13,   14,    0,    0,    0,    0,  133,
   15,  133,   16,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  133,  133,  133,    0,
    5,    6,    0,    0,    1,    0,    0,    8,    9,    0,
   10,   11,   55,    0,   12,   13,   14,    0,    0,    5,
    6,  179,   15,    1,   16,    0,    8,    9,    0,   10,
   11,    0,    0,   12,   13,   14,    0,    5,    6,    0,
    0,   15,    7,   16,    8,    9,    0,   10,   11,    0,
    0,   12,   13,   14,    0,    5,    6,    0,    0,   15,
   65,   16,    8,    9,    0,   10,   11,    0,    0,   12,
   13,   14,    0,    0,    5,    6,    0,   15,    1,   16,
    0,    8,    9,    0,   10,   11,    0,    0,   12,   13,
   14,    0,    0,    0,    0,    0,   15,    0,   16,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          1,
    0,   44,    2,   40,   59,   59,   44,   41,  123,   11,
  123,  123,   59,   97,   98,   17,   59,  123,   74,  260,
  256,   59,   40,  271,  272,  256,   41,    1,  276,   59,
  278,  260,  268,   40,   45,   41,   41,   42,   43,   44,
   45,   45,   47,   17,  256,   40,   44,   49,  279,  278,
  262,   45,  271,  272,  244,   40,  246,   44,   41,  278,
   62,   41,   45,  259,  259,   44,  262,  262,  123,  153,
   72,   73,   74,  259,   42,   49,  262,   41,   44,   47,
   41,  256,   84,   44,   42,   45,   41,   41,   45,   47,
   44,   44,   41,   42,   43,   59,   45,   41,   47,   41,
  279,   43,   59,   45,  258,  123,   59,   42,   43,   40,
   45,   41,   47,   59,  125,   59,  123,  278,  174,  175,
  176,  177,   41,   44,   41,   40,   43,  278,   45,   41,
  132,  133,  278,   41,   42,   43,   41,   45,   59,   47,
   59,   59,   41,  256,  256,   44,   41,   59,   42,   43,
  256,   45,   44,   47,  174,  175,  176,  177,  271,  272,
   45,   41,  277,  271,  272,  278,  258,   59,   41,   41,
   40,  277,  174,  175,  176,  177,  278,  179,  278,  181,
   62,   62,  125,   40,  125,   45,   41,  278,   59,   42,
   43,   41,   45,   41,   47,   59,   41,   42,   43,   62,
   45,  256,   47,   59,   62,   59,  278,   60,   61,   62,
   59,   41,  278,  256,  278,   62,  218,   59,  256,   59,
  222,  223,  256,  257,  278,  259,  260,  261,  262,  263,
  264,  278,  266,  267,  268,  278,  270,  271,  272,   41,
  278,  278,  242,  243,  278,  245,  280,  247,  248,  256,
  256,  257,  254,  259,  260,  261,  262,  263,  264,  277,
  266,  267,  268,  278,  270,  271,  272,   41,  279,   60,
  277,  276,  278,  256,  280,  279,  256,  257,  276,  259,
  260,  261,  262,  263,  264,  279,  266,  267,  268,  276,
  270,  271,  272,   41,  258,  278,  279,  276,  278,  256,
  280,  256,  257,  256,  259,  260,  261,  262,  263,  264,
  276,  266,  267,  268,  258,  270,  271,  272,  278,  279,
   59,  278,  279,  278,   41,  280,  256,  257,   45,  259,
  260,  261,  262,  263,  264,  256,  266,  267,  268,  258,
  270,  271,  272,   60,   61,   62,  258,  265,  278,   41,
  280,  256,  257,   45,  259,  260,  261,  262,  263,  264,
  278,  266,  267,  268,  256,  270,  271,  272,   60,   61,
   62,  256,   59,  278,   44,  280,  256,  257,   45,  259,
  260,  261,  262,  263,  264,   59,  266,  267,  268,   59,
  270,  271,  272,  278,  279,   41,  256,   59,  278,  278,
  280,  256,  257,  256,  259,  260,  261,  262,  263,  264,
   60,  266,  267,  268,   62,  270,  271,  272,  278,  279,
  273,  274,  275,  278,   59,  280,  256,  257,   45,  259,
  260,  261,  262,  263,  264,  278,  266,  267,  268,  278,
  270,  271,  272,   60,   61,   62,   59,   44,  278,   59,
  280,   40,  279,   59,  256,  257,   45,  259,  260,  261,
  262,  263,  264,   59,  266,  267,  268,   41,  270,  271,
  272,   60,   61,   62,   59,   41,  278,   41,  280,   40,
  271,  272,  256,  257,   45,  259,  260,  261,  262,  263,
  264,   41,  266,  267,  268,  262,  270,  271,  272,   60,
   61,   62,   59,   59,  278,   59,  280,  262,  256,  257,
   59,  259,  260,  261,  262,  263,  264,  256,  266,  267,
  268,   59,  270,  271,  272,   41,   42,   43,   44,   45,
  278,   47,  280,   59,   41,   42,   43,   59,   45,  256,
   47,  280,   44,   59,   59,   59,  262,   59,   41,   42,
   43,   44,   45,   84,   47,  262,  273,  274,  275,  262,
   59,  278,  279,  125,  256,  279,   59,   60,   61,   62,
   59,   59,  278,   41,   59,   43,   44,   45,   59,   51,
   52,  273,  274,  275,  125,   39,  278,  279,  125,  256,
  257,   59,   60,   61,   62,  265,  263,  264,  279,  266,
  267,  132,  133,  270,  271,  272,  276,   59,  278,    0,
  277,  278,  279,  280,   41,  125,   43,   44,   45,  269,
   41,   43,   -1,  271,  272,   97,  276,   -1,  278,   -1,
  278,   -1,   59,   60,   61,   62,   -1,   -1,   45,  256,
  112,  113,   49,   -1,   -1,   -1,   -1,   54,  179,  103,
  181,  105,   -1,   -1,   -1,   -1,  273,  274,  275,   -1,
   -1,  278,  279,   -1,   71,   -1,   -1,  256,   -1,   -1,
   -1,   78,   79,   80,   81,   82,   83,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  273,  274,  275,  218,   -1,  278,
  279,  222,  223,  165,   -1,  256,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  160,  161,   -1,   -1,
   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,   -1,
   -1,  256,  257,  254,  259,  260,   -1,  262,  263,  264,
   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,
   -1,   -1,   -1,  278,   -1,  280,   -1,   -1,   -1,  265,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  276,   -1,  278,  256,  171,  258,   -1,   -1,   -1,   -1,
   -1,   -1,  244,   -1,  246,   -1,   -1,   -1,   -1,   -1,
  273,  274,  275,   -1,   -1,   -1,   -1,   -1,  256,   -1,
  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  273,  274,  275,  256,  257,
   -1,  259,  260,   -1,  262,  263,  264,   -1,  266,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,  256,
  278,  258,  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,   -1,
  256,  257,   -1,   -1,  260,   -1,   -1,  263,  264,   -1,
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
"sentencia : RET '(' ')'",
"sentencia : error ';'",
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
"if_statement : IF '(' condicion ')' THEN repeat_sentencia error",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia error",
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

//#line 504 "gramatica.y"

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
    // Crear un JFileChooser para seleccionar el archivo
    JFileChooser fileChooser = new JFileChooser();
    int result = fileChooser.showOpenDialog(null);  // Muestra el cuadro de diálogo

    if (result == JFileChooser.APPROVE_OPTION) {  // Si el usuario selecciona un archivo
        File selectedFile = fileChooser.getSelectedFile();
        String filePath = selectedFile.getAbsolutePath();  // Obtener la ruta del archivo seleccionado

        // Instanciar el Parser con la ruta seleccionada
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

//#line 770 "Parser.java"
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
case 5:
//#line 68 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 18:
//#line 85 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 19:
//#line 86 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
break;
case 20:
//#line 87 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta sentencia antes del ; ");}
break;
case 21:
//#line 91 "gramatica.y"
{ 
    List<ParserVal> variables = new ArrayList<ParserVal>();
    variables.add(val_peek(1)); 
    
    for (ParserVal variable : variables) {
        /* Verificar si la variable ya existe en la tabla de simbolos*/
        if (!st.hasKey(variable.toString())) {
            System.out.println("Aclaracion, la tabla de simbolos no contenia la variable: " + variable.toString());
        } else {
            /* Actualiza el tipo de la variable si ya esta en la tabla de simbolos*/
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
//#line 109 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 23:
//#line 110 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}
break;
case 26:
//#line 116 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 28:
//#line 122 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }
break;
case 29:
//#line 127 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }
break;
case 30:
//#line 131 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 31:
//#line 135 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    }
break;
case 32:
//#line 138 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se puede poner ; al final de la declaracion de una fucnion");
    }
break;
case 34:
//#line 146 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 35:
//#line 149 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 36:
//#line 152 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion debe tener un parametro.");
    }
break;
case 39:
//#line 162 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 40:
//#line 163 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 41:
//#line 165 "gramatica.y"
{
        
        /* Verificando si el tipo esta en la tabla de tipos definidos*/
        
        if (tablaTipos.containsKey(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0));
        } 
    }
break;
case 44:
//#line 180 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 45:
//#line 181 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 46:
//#line 184 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 47:
//#line 188 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 48:
//#line 191 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 49:
//#line 195 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 50:
//#line 198 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 51:
//#line 202 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 52:
//#line 205 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 53:
//#line 208 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 54:
//#line 209 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 55:
//#line 210 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 56:
//#line 211 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 58:
//#line 219 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 59:
//#line 222 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
break;
case 60:
//#line 225 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 61:
//#line 228 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 62:
//#line 229 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 65:
//#line 237 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 66:
//#line 240 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 67:
//#line 243 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
break;
case 68:
//#line 246 "gramatica.y"
{
        
        
        /* Obtener el nombre del tipo desde T_ID*/
        String nombreTipo = val_peek(4).sval; /* T_ID*/

        /* Obtener el tipo base (INTEGER o SINGLE)*/
        String tipoBase = val_peek(2).sval;
        
        /* tipo base (INTEGER o SINGLE)*/
        
        double limiteInferior = val_peek(4).dval; /* Limite inferior */
        
        
        double limiteSuperior =  val_peek(5).dval; /* Limite superior */
        
        /* Almacenar en la tabla de tipos*/
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        
        }
break;
case 69:
//#line 266 "gramatica.y"
{
             String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (LONGINT)*/
            String tipoBase = val_peek(3).sval;
            
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -2147483647, 2147483647));
        }
break;
case 70:
//#line 274 "gramatica.y"
{
            String nombreTipo = val_peek(1).sval; /* T_ID*/

            /*tipo base (DOUBLE)*/
            String tipoBase = val_peek(3).sval;
            
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, -1.7976931348623157E+308, 1.7976931348623157E+308));		
        }
break;
case 71:
//#line 282 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
break;
case 72:
//#line 285 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 73:
//#line 288 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 74:
//#line 291 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
break;
case 75:
//#line 294 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 76:
//#line 297 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 77:
//#line 298 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 78:
//#line 299 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 79:
//#line 300 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 80:
//#line 301 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 81:
//#line 302 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 82:
//#line 303 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 83:
//#line 304 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 84:
//#line 305 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 85:
//#line 306 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la asignacion en la definicion de nuevos tipos");}
break;
case 86:
//#line 308 "gramatica.y"
{
        
        /*CODIGO PARA PARTE SEMANTICA*/
        
        String limiteInferiorStr = val_peek(3).sval; /* T_CTE (limites inferiores)*/
        String limiteSuperiorStr = val_peek(1).sval; /* T_CTE (limites superiores)*/

        
        

        try {
           
            double limiteInferior = Double.parseDouble(limiteInferiorStr);
            double limiteSuperior = Double.parseDouble(limiteSuperiorStr);

            
            yylval.obj = new Subrango(limiteInferior, limiteSuperior);
            
        } catch (NumberFormatException e) {
            System.err.println("Error al convertir los limites del subrango a double: " + e.getMessage());
        }
    }
break;
case 90:
//#line 333 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 91:
//#line 334 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 93:
//#line 340 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 94:
//#line 341 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 95:
//#line 342 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 102:
//#line 354 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 104:
//#line 356 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
break;
case 111:
//#line 368 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 112:
//#line 369 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 113:
//#line 370 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 114:
//#line 375 "gramatica.y"
{
            
        if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
            yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
        } else {
            yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
        }
        
    }
break;
case 115:
//#line 384 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Solo se puede acceder a un par con 1 o 2");}
break;
case 116:
//#line 385 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 118:
//#line 389 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 119:
//#line 390 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 120:
//#line 391 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 122:
//#line 395 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
break;
case 141:
//#line 421 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 142:
//#line 424 "gramatica.y"
{ /* Esta regla maneja especificamente el '-' unario*/
    double valor = val_peek(0).dval;  
    System.out.println("dval: "+valor);

    String nombreConstante = val_peek(0).sval;  
    String nombreConMenos = "-" + nombreConstante;
    System.out.println("sval: "+nombreConstante);
    /* verificacion en la tabla de simbolos.*/
    if (st.hasKey(nombreConstante)) {
        String tipo = st.getType(nombreConstante);  /*  tipo de la constante.*/
        if (tipo != null) {
            /* Verifica si el valor original (sin negativo) esta en el rango adecuado segun el tipo.*/
            if (tipo.equals("longint")) {
                if (!lexer.isLongintRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para longint.");
                } else {
                	
                    
                    st.addValue(nombreConMenos, tipo,SymbolTable.constantValue);
                }
            } else if (tipo.equals("double")) {
                if (!lexer.isDoubleRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para double.");
                } else {
                    
                    st.addValue(nombreConMenos, tipo, SymbolTable.constantValue);
                }
            }else if (tipo.equals("Octal")) {
                if (!lexer.isOctalRange(valor)) {
                    System.err.println("Error: El valor de la constante " + valor + " esta fuera del rango permitido para octales.");
                    
                } else {
                    System.out.println("Entre a el else del octal");
                    st.addValue(nombreConMenos, tipo, SymbolTable.constantValue);
                }
            }
        } else {
            System.err.println("Error: El tipo de la constante no pudo ser determinado.");
        }
    } else { /*se trata de numero negativo menor al menor negativo.*/
    	/*ACA VER QUE TIPO DE NUMERO ES CON IFS*/
    	System.out.println("Entre");
        if (nombreConstante.startsWith("0") && !nombreConstante.matches(".*[89].*")) {
        	System.err.println("El valor octal " + "-"+nombreConstante+ " se ajusto al valor minimo.");
            st.addValue("-020000000000", "Octal", SymbolTable.constantValue);
        } else if (nombreConstante.contains(".")) {
        	System.err.println("El valor double -" + nombreConstante + " se ajusta al valor mínimo.");

            
            /* Parseamos el valor como double para comparaciones*/
            double valorDouble = Double.parseDouble("-" + nombreConstante.replace("d", "e"));
            System.out.println("valorDouble: "+valorDouble);
            /* Rango mínimo y máximo de los números double*/
            double maxNegativeDouble = -1.7976931348623157e+308;
            double minNegativeDouble = -2.2250738585072014e-308;

            /* Si está por debajo del máximo permitido, lo mantenemos*/
            if (valorDouble < maxNegativeDouble) {
                st.addValue("-1.7976931348623156d+308", "double", SymbolTable.constantValue);
            } 
            /* Si está por debajo del mínimo permitido pero mayor al mínimo ajustado*/
            else if (valorDouble > minNegativeDouble) {
                st.addValue("-2.2250738585072015d-308", "double", SymbolTable.constantValue);
            } 
            /* Si está en el rango permitido*/
            else {
                st.addValue("-" + nombreConstante, "double", SymbolTable.constantValue);
            }
            
        } else{ /*ya se sabe que es entero*/
            /* Lógica para longint*/
        	System.err.println("El valor longint -" + nombreConstante + " se ajusta al valor mínimo.");
            nombreConMenos = "-2147483648"; /* Asignar valor mínimo si está fuera de rango*/
            st.addValue(nombreConMenos, "longint", SymbolTable.constantValue);
        }
        
    }
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
