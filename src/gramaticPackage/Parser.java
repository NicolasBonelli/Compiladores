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
   14,   19,   20,   21,    7,    7,    7,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    7,    7,   23,
    8,    8,    8,    8,    8,    8,    9,    9,    9,    9,
    9,    9,   12,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   12,
   24,   24,   24,   24,   24,   24,   22,   22,   22,   22,
   25,   25,   25,   25,   25,   25,    6,    6,    6,   27,
   27,   26,   26,   26,   26,   26,   26,   26,   26,   26,
   28,   28,   28,   11,   11,   11,   11,   29,   29,   30,
   31,   31,   31,   31,   31,   31,   31,   31,   13,   13,
   13,   13,   13,   13,   13,   13,   13,   13,   32,
};
final static short yylen[] = {                            2,
    2,    1,    1,    3,    2,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    5,    4,    4,    3,
    3,    2,    3,    1,    2,    1,    7,    7,    7,    7,
    7,    8,    2,    3,    3,    0,    1,    1,    1,    1,
    1,    2,    2,    2,    7,    8,    8,    6,    7,    6,
    7,    7,    8,    7,    8,    5,    6,    7,    8,    1,
    7,    6,    5,    6,    5,    7,    5,    5,    4,    4,
    5,    4,    6,    7,    7,    6,    5,    5,    5,    7,
    6,    6,    6,    6,    6,    6,    5,    5,    5,    5,
    5,    6,    6,    7,    2,    1,    3,    3,    2,    2,
    1,    1,    1,    1,    1,    1,    4,    4,    3,    1,
    3,    3,    3,    1,    1,    3,    3,    3,    1,    3,
    4,    3,    2,    3,    2,    2,    2,    4,    4,    1,
    3,    3,    3,    3,    1,    1,    1,    1,    3,    3,
    3,    3,    1,    1,    1,    1,    1,    1,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    3,    0,    5,    0,    0,    0,
   60,    0,   40,   39,    0,  119,   16,    0,    6,    8,
    9,   10,   11,   12,   13,   14,   15,    0,    0,    0,
    0,    1,  148,  101,  102,  103,    0,  143,    0,    0,
  105,  106,  104,    0,    0,    0,  145,  146,  147,    0,
    0,    0,    0,    0,    0,  127,    0,  125,    0,  123,
    0,    4,    7,    0,    0,   22,    0,    0,    0,   37,
   38,    0,    0,    0,    0,    0,  149,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   41,
    0,    0,    0,    0,    0,    0,    0,  124,    0,  117,
    0,  122,   26,    0,    0,    0,   21,   25,   20,    0,
    0,    0,    0,  109,    0,    0,    0,  120,  113,    0,
  116,    0,    0,  135,  137,    0,    0,  138,    0,    0,
    0,    0,    0,    0,  141,  142,    0,    0,    0,    0,
    0,    0,   72,    0,    0,    0,    0,    0,    0,    0,
    0,   96,    0,    0,    0,    0,    0,    0,    0,   19,
    0,  121,    0,    0,    0,   23,    0,    0,    0,    0,
  107,  108,    0,  129,  128,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   56,   44,    0,   67,   71,
   68,   78,   77,    0,    0,    0,    0,    0,    0,   95,
   87,   89,    0,   88,   90,    0,    0,   17,    0,    0,
    0,    0,    0,   63,    0,    0,    0,   65,    0,    0,
    0,  133,  134,   50,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   57,    0,   85,    0,   86,    0,   76,
    0,    0,   73,   83,   84,    0,   33,    0,    0,    0,
    0,    0,    0,    0,   64,    0,   51,    0,   54,   52,
    0,   58,   45,    0,    0,    0,   74,   75,   80,    0,
    0,    0,   31,   29,    0,    0,   34,   28,   35,   30,
   66,   61,   55,   53,   47,   59,   46,   91,    0,    0,
    0,   32,   93,   92,    0,   94,
};
final static short yydgoto[] = {                          3,
    4,   70,   18,   71,   20,   21,   22,   23,   24,   25,
   26,   27,   44,   28,   68,  221,  222,  148,   87,   88,
  151,   45,   29,  164,   46,   30,  126,   47,   48,  136,
  137,   49,
};
final static short yysindex[] = {                      -246,
  605,    0,    0, -218,    0,  -32,    0,   31,  339,   43,
    0,  -47,    0,    0, -114,    0,    0,  625,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   71,  -76,   32,
 -165,    0,    0,    0,    0,    0,  -33,    0, -166,  400,
    0,    0,    0,   86, -157,  154,    0,    0,    0,  262,
  314, -209, -168, -106,   54,    0,   61,    0, -146,    0,
 -108,    0,    0,  -38,    0,    0,   94,  -11,  104,    0,
    0, -212,   79,  156, -129,  207,    0, -157,  126,  154,
  154,  154,  154,  154,  154,  649,  -89,  -74,   51,  142,
  -21,    0,  138,  152,   98, -165,  -73,  -61,  347,    0,
 -105, -119, -105,  266,  341,  349,  564,    0, -101,    0,
  286,    0,    0, -209,  377, -209,    0,    0,    0,  146,
  322,  388,  -24,    0,   51,   28, -101,    0,    0, -101,
    0,  395, -101,    0,    0,  399,  135,    0,  180,  -74,
  545,   51,   64,   64,    0,    0,   51,    0,  393,  649,
  194,  405,    0,  411,  418,  422,  428,  426,  427,  433,
  232,    0,  -18,  452,  -49,  453,  454,  236,  242,    0,
  465,    0,  249, -209,  252,    0,  490,  322,  461,  476,
    0,    0,  154,    0,    0,    4,    4,    4,    4,  477,
  275,  484, -178, -215,  585,    0,    0,  479,    0,    0,
    0,    0,    0,  -44,   46,  270,  491,  519,  288,    0,
    0,    0,  509,    0,    0,  514,  520,    0,  543,  -30,
   78,   82,  546,    0,  547,  532,  551,    0,   51,   74,
   74,    0,    0,    0,  534,  337,  553,  555,  340,  557,
  561,  342, -147,    0,  563,    0,  568,    0,  578,    0,
   -5,  566,    0,    0,    0, -218,    0, -218, -218, -209,
 -218, -209, -218,  582,    0,  583,    0,  584,    0,    0,
  586,    0,    0,  587,  588,  589,    0,    0,    0,  498,
  365,   13,    0,    0,  590,  372,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  529,  530,
  378,    0,    0,    0,  533,    0,
};
final static short yyrindex[] = {                         0,
    0,    3,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  348,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   33,    0,    0,    0,    0,    0,  510,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -41,    0,    0,    0,
    0,    0,    0,    0,  -20,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   76,    0,    0,    0,   95,    0,
  502,   10,    0,    0,    0,   23,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   29,    0,   35,    0,    0,   41,
    0,    0,  593,    0,    0,    0,  618,    0,    0,    0,
    0,  120,  515,  521,    0,    0,  127,  425,    0,    0,
    0,    9,    0,    0,   34,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   59,    0,    0,  157,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   84,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  130,  119,
  165,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  109,    0,    0,    0,  134,    0,  159,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  187,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  212,    0,    0,    0,    0,
    0,    0,    0,    0,  237,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
   18,    1,    0,   42,    0,    0,    0,    0,    0,    0,
    0,    0,  548,  537,    0, -201,    0,  -23,  -55,  -52,
 -115,   -9,    0,  -68,  616,    0,    0,  651,    0,    0,
   25,  -31,
};
final static int YYTABLESIZE=929;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        126,
    5,  114,    2,  163,   32,   72,   76,   40,   61,  212,
  258,   58,   39,    1,  246,  179,  112,  163,   76,   26,
   39,   61,  139,   24,  191,  140,  209,   41,   43,   42,
   79,    2,  120,  166,  167,   41,   43,   42,   24,  281,
  240,    1,   19,  122,  138,   67,  241,  119,   39,   70,
  143,  143,  143,  119,  143,  123,  143,  301,  287,   63,
  289,   13,   14,  145,  145,  145,  115,  145,  100,  145,
   50,  183,  110,  163,   69,   74,  115,  239,  112,  243,
  150,  115,   55,  238,  118,  194,  182,  110,  195,   61,
   75,   94,   83,   81,  106,   82,  213,   84,   39,   18,
   86,   61,   13,   14,  248,   83,  210,  102,  275,  100,
   84,  177,   77,  180,  276,  188,   99,  193,  259,  108,
  189,  260,  261,   39,   79,  262,  197,   83,   81,   66,
   82,  109,   84,  116,   99,  100,  162,  124,  155,   83,
   81,   59,   82,  121,   84,   41,   43,   42,  130,   48,
  162,   13,   14,  100,  138,  138,  138,  138,  100,  131,
   98,  131,   60,  131,  104,  105,  141,   97,  225,  227,
  111,  242,  149,  111,   81,   60,  188,  186,   98,  187,
    6,  189,  152,    1,  150,   97,    8,    9,  111,   10,
   11,   69,  154,   12,   13,   14,  153,   36,   39,   82,
   36,   15,   16,   17,  156,  132,  162,  132,   56,  132,
  230,  231,  232,  233,  126,  126,  157,  126,  126,  126,
  126,  126,  126,   33,  126,  126,  126,   62,  126,  126,
  126,   33,   57,  245,   59,   24,  126,  126,  126,  113,
   34,   35,   36,   60,  117,   37,   38,  257,   34,   35,
   36,   39,   49,   37,   38,   60,  283,   24,  284,  285,
  208,  288,   26,  290,   70,   70,  118,   70,   70,   70,
   70,   70,   70,  280,   70,   70,   70,   27,   70,   70,
   70,  133,  134,  181,  110,  119,   70,   70,   70,   69,
   69,  300,   69,   69,   69,   69,   69,   69,  115,   69,
   69,   69,   93,   69,   69,   69,   39,   73,  115,   33,
  112,   69,   69,   69,   18,   18,  118,   18,   18,   18,
   18,   18,   18,  247,   18,   18,   18,  168,   18,   18,
   18,   37,   38,   99,   33,   64,   18,   18,   18,   79,
   79,   80,   79,   79,   79,   79,   79,   79,   65,   79,
   79,   79,  100,   79,   79,   79,   37,   38,   34,   35,
   36,   79,   79,   79,   48,   48,   39,   48,   48,   48,
   48,   48,   48,   99,   48,   48,   48,   98,   48,   48,
   48,   41,   43,   42,   97,  111,   48,   48,   48,   81,
   81,  114,   81,   81,   81,   81,   81,   81,   54,   81,
   81,   81,  169,   81,   81,   81,   41,  170,  161,   33,
  172,   81,   81,   81,   82,   82,  174,   82,   82,   82,
   82,   82,   82,  176,   82,   82,   82,  178,   82,   82,
   82,   37,   38,  127,  128,  184,   82,   82,   82,  185,
   78,  190,   62,   62,   39,   62,   62,   62,   62,   62,
   62,  196,   62,   62,   62,  198,   62,   62,   62,   41,
   43,   42,  132,  199,   62,   62,   62,   49,   49,  200,
   49,   49,   49,   49,   49,   49,  201,   49,   49,   49,
  202,   49,   49,   49,  133,  134,  203,  204,  205,   49,
   49,   49,   27,   27,  206,   27,   27,   27,   27,   27,
   27,  226,   27,   27,   27,   39,   27,   27,   27,  207,
  211,  214,  215,  216,   27,   27,   27,   33,    6,  217,
   41,   43,   42,  218,    8,    9,  219,   10,   11,  223,
  224,   12,   13,   14,  228,  234,  235,  244,   90,   91,
   92,   17,  144,  144,  144,  114,  144,  249,  144,  250,
  144,  144,  144,  144,  144,  139,  144,  139,  139,  139,
   41,  140,  251,  140,  140,  140,  252,  253,  144,  144,
  144,  144,  254,  139,  139,  139,  139,   33,  255,  140,
  140,  140,  140,  256,   97,   98,  263,  264,  101,  103,
  265,  266,  267,   89,   34,   35,   36,   95,  268,   37,
   38,  271,  107,  274,  171,   83,   81,   51,   82,  282,
   84,  269,   41,  270,   52,  272,   53,  158,  159,  273,
  125,  277,  298,  114,  160,   41,  278,  142,  143,  144,
  145,  146,  147,  136,  136,  136,  279,  136,  165,  136,
  291,  292,  293,  299,  294,  295,  296,  297,  302,  257,
  173,   31,  175,  303,  304,   33,  305,  306,  130,   85,
    0,    0,    0,    0,    0,    0,    0,    0,   31,    0,
    0,    0,   34,   35,   36,    0,    0,   37,   38,   31,
   42,   43,    0,   43,   43,    0,   42,   43,   43,    0,
   43,   43,    0,    0,   43,   43,   43,    0,    0,    0,
   96,    0,   43,   43,   43,    0,    0,    0,    0,  110,
  220,    0,    0,    0,    0,    0,   33,    0,    0,    0,
    0,    0,    0,    0,  129,  131,  135,    0,    0,    0,
  229,    0,    0,   34,   35,   36,   31,    0,   37,   38,
    6,    0,  236,    1,    0,  237,    8,    9,    0,   10,
   11,    0,    0,   12,   13,   14,    0,    0,    0,    0,
    0,   15,   16,   17,    0,  144,   41,  144,    0,    0,
  139,    0,  139,    0,    0,    0,  140,  114,  140,   41,
    0,    0,  144,  144,  144,    0,    0,  139,  139,  139,
    0,   31,    0,  140,  140,  140,  286,    0,  286,    0,
   31,    6,  192,    0,    1,    0,    0,    8,    9,    0,
   10,   11,    0,    0,   12,   13,   14,    0,    0,    0,
    0,    0,   15,   16,   17,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  135,  135,  135,  135,
    0,    6,   31,  150,    1,   31,    0,    8,    9,    0,
   10,   11,    0,    0,   12,   13,   14,    0,    0,    0,
    0,    6,   15,   16,   17,    7,    0,    8,    9,    0,
   10,   11,    0,    0,   12,   13,   14,    0,    0,    0,
    0,    6,   15,   16,   17,   62,    0,    8,    9,    0,
   10,   11,    0,    0,   12,   13,   14,    0,    0,    0,
    0,    0,   15,   16,   17,    6,    0,    0,    1,    0,
    0,    8,    9,    0,   10,   11,    0,    0,   12,   13,
   14,    0,    0,    0,    0,    0,   15,   16,   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
    0,   40,    0,  123,    4,   29,   40,   40,  123,   59,
   41,   59,   45,  260,   59,   40,  125,  123,   40,   40,
   45,  123,   78,   44,  140,   78,   45,   60,   61,   62,
   40,  278,   44,  102,  103,   60,   61,   62,   59,   45,
  256,  260,    1,  256,   76,   28,  262,   59,   45,   41,
   41,   42,   43,   44,   45,  268,   47,   45,  260,   18,
  262,  271,  272,   41,   42,   43,   44,   45,  278,   47,
   40,   44,   44,  123,   41,   44,   44,  193,   44,  195,
  259,   64,   40,  262,   44,  141,   59,   59,  141,  123,
  256,   50,   42,   43,   41,   45,  165,   47,   45,   41,
  258,  123,  271,  272,   59,   42,  125,  276,  256,  278,
   47,  121,  279,  123,  262,   42,   41,  141,   41,   59,
   47,   44,   41,   45,   41,   44,  150,   42,   43,   59,
   45,  278,   47,   40,   59,   41,  256,   59,   41,   42,
   43,  256,   45,   40,   47,   60,   61,   62,  278,   41,
  256,  271,  272,   59,  186,  187,  188,  189,  278,   41,
   41,   43,  277,   45,  271,  272,   41,   41,  178,  179,
  279,  195,  262,   44,   41,  277,   42,   43,   59,   45,
  257,   47,   41,  260,  259,   59,  263,  264,   59,  266,
  267,  268,   41,  270,  271,  272,   59,   41,   45,   41,
   44,  278,  279,  280,  278,   41,  256,   43,  256,   45,
  186,  187,  188,  189,  256,  257,  278,  259,  260,  261,
  262,  263,  264,  256,  266,  267,  268,   41,  270,  271,
  272,  256,  280,  278,  256,  256,  278,  279,  280,  278,
  273,  274,  275,  277,  256,  278,  279,  278,  273,  274,
  275,   45,   41,  278,  279,  277,  256,  278,  258,  259,
  279,  261,  260,  263,  256,  257,  278,  259,  260,  261,
  262,  263,  264,  279,  266,  267,  268,   41,  270,  271,
  272,  278,  279,  256,  256,  276,  278,  279,  280,  256,
  257,  279,  259,  260,  261,  262,  263,  264,  276,  266,
  267,  268,   41,  270,  271,  272,   45,  276,  276,  256,
  276,  278,  279,  280,  256,  257,  276,  259,  260,  261,
  262,  263,  264,  278,  266,  267,  268,   62,  270,  271,
  272,  278,  279,  258,  256,  265,  278,  279,  280,  256,
  257,  256,  259,  260,  261,  262,  263,  264,  278,  266,
  267,  268,  258,  270,  271,  272,  278,  279,  273,  274,
  275,  278,  279,  280,  256,  257,   45,  259,  260,  261,
  262,  263,  264,   60,  266,  267,  268,  258,  270,  271,
  272,   60,   61,   62,  258,  256,  278,  279,  280,  256,
  257,   44,  259,  260,  261,  262,  263,  264,   60,  266,
  267,  268,   62,  270,  271,  272,   59,   59,   62,  256,
  125,  278,  279,  280,  256,  257,   40,  259,  260,  261,
  262,  263,  264,  278,  266,  267,  268,   40,  270,  271,
  272,  278,  279,  278,  279,   41,  278,  279,  280,   41,
   41,  262,  256,  257,   45,  259,  260,  261,  262,  263,
  264,   59,  266,  267,  268,  262,  270,  271,  272,   60,
   61,   62,  256,   59,  278,  279,  280,  256,  257,   59,
  259,  260,  261,  262,  263,  264,   59,  266,  267,  268,
   59,  270,  271,  272,  278,  279,   59,   62,   62,  278,
  279,  280,  256,  257,   62,  259,  260,  261,  262,  263,
  264,   41,  266,  267,  268,   45,  270,  271,  272,  278,
   59,   59,   59,  278,  278,  279,  280,  256,  257,  278,
   60,   61,   62,   59,  263,  264,  278,  266,  267,  278,
   41,  270,  271,  272,   59,   59,  262,   59,  277,  278,
  279,  280,   41,   42,   43,   44,   45,  278,   47,   59,
   41,   42,   43,   44,   45,   41,   47,   43,   44,   45,
   59,   41,   44,   43,   44,   45,  279,   59,   59,   60,
   61,   62,   59,   59,   60,   61,   62,  256,   59,   59,
   60,   61,   62,   41,  271,  272,   41,   41,   52,   53,
   59,   41,   59,   46,  273,  274,  275,   50,  262,  278,
  279,  262,   55,  262,   41,   42,   43,  269,   45,   44,
   47,   59,  265,   59,  276,   59,  278,  271,  272,   59,
   73,   59,  125,  276,  278,  278,   59,   80,   81,   82,
   83,   84,   85,   41,   42,   43,   59,   45,  102,   47,
   59,   59,   59,  279,   59,   59,   59,   59,   59,  278,
  114,    1,  116,  125,  125,  256,  279,  125,   41,   44,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   18,   -1,
   -1,   -1,  273,  274,  275,   -1,   -1,  278,  279,   29,
  256,  257,   -1,  259,  260,   -1,  262,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
   50,   -1,  278,  279,  280,   -1,   -1,   -1,   -1,   59,
  174,   -1,   -1,   -1,   -1,   -1,  256,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   74,   75,   76,   -1,   -1,   -1,
  183,   -1,   -1,  273,  274,  275,   86,   -1,  278,  279,
  257,   -1,  259,  260,   -1,  262,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
   -1,  278,  279,  280,   -1,  256,  265,  258,   -1,   -1,
  256,   -1,  258,   -1,   -1,   -1,  256,  276,  258,  278,
   -1,   -1,  273,  274,  275,   -1,   -1,  273,  274,  275,
   -1,  141,   -1,  273,  274,  275,  260,   -1,  262,   -1,
  150,  257,  258,   -1,  260,   -1,   -1,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,   -1,  278,  279,  280,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  186,  187,  188,  189,
   -1,  257,  192,  259,  260,  195,   -1,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,  257,  278,  279,  280,  261,   -1,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,  257,  278,  279,  280,  261,   -1,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,   -1,
   -1,   -1,  278,  279,  280,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,   -1,   -1,   -1,  278,  279,  280,
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
"bloque_THEN : THEN repeat_sentencia",
"bloque_THEN_CON_ELSE : THEN repeat_sentencia",
"bloque_ELSE : ELSE repeat_sentencia",
"if_statement : IF '(' condicion ')' bloque_THEN END_IF ';'",
"if_statement : IF '(' condicion ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF ';'",
"if_statement : IF '(' condicion ')' bloque_THEN_CON_ELSE repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' bloque_THEN END_IF",
"if_statement : IF '(' condicion ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF",
"if_statement : IF '(' ')' bloque_THEN END_IF ';'",
"if_statement : IF '(' ')' bloque_THEN_CON_ELSE bloque_ELSE END_IF ';'",
"if_statement : IF '(' condicion ')' repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' repeat_sentencia bloque_ELSE END_IF ';'",
"if_statement : IF '(' condicion ')' THEN END_IF ';'",
"if_statement : IF '(' condicion ')' THEN ELSE END_IF ';'",
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

//#line 968 "gramatica.y"

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

//#line 822 "Parser.java"
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
case 16:
//#line 82 "gramatica.y"
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
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se pueden redeclarar variables. Error con la variable:"+val_peek(0).sval);
            }else{
                if(st.getAmbitoByKey(val_peek(0).sval).equals(" ")){
                    st.updateAmbito(val_peek(0).sval,SymbolTable.ambitoGlobal);
                }else{
                    st.addValue(val_peek(0).sval,"String","Nombre de variable",SymbolTable.ambitoGlobal.toString(), 280);
                }
            }
         }
break;
case 18:
//#line 105 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 19:
//#line 106 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta retornar algo en el RET ");}
break;
case 20:
//#line 110 "gramatica.y"
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
//#line 142 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 22:
//#line 143 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta variable en la sentencia declarativa");}
break;
case 23:
//#line 147 "gramatica.y"
{
    
    @SuppressWarnings("unchecked")
    List<String> variables = (List<String>) val_peek(2).obj;
    variables.add(val_peek(0).sval);  /* Agregar nueva variable*/
    yyval.obj = variables;  /* Pasar la lista actualizada hacia arriba */
}
break;
case 24:
//#line 154 "gramatica.y"
{
    List<String> variables = new ArrayList<String>();
    variables.add(val_peek(0).sval);  /* Agregar la primera variable*/
    yyval.obj = variables; 
}
break;
case 25:
//#line 159 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables. Faltan las comas ','");}
break;
case 26:
//#line 163 "gramatica.y"
{ yyval.sval = val_peek(0).sval;
    System.out.println("Entre a Funcion antes (o despues?) de la derecha");

    if (SymbolTable.ambitoGlobal.length() == 0) {
        SymbolTable.ambitoGlobal = new StringBuilder(val_peek(0).sval);
    } else SymbolTable.ambitoGlobal.append(":" + val_peek(0).sval);
        }
break;
case 27:
//#line 172 "gramatica.y"
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
//#line 207 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en la cantidad de parametros de la funcion.");
    }
break;
case 29:
//#line 212 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del parametro de la funcion.");
    }
break;
case 30:
//#line 216 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 31:
//#line 220 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre de la funcion.");
    }
break;
case 32:
//#line 223 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - No se puede poner ; al final de la declaracion de una fucnion");
    }
break;
case 33:
//#line 228 "gramatica.y"
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
//#line 250 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 35:
//#line 253 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion no debe tener mas de un parametro.");
    }
break;
case 36:
//#line 256 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - La funcion debe tener un parametro.");
    }
break;
case 39:
//#line 266 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 40:
//#line 267 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 41:
//#line 269 "gramatica.y"
{  
        /* Verificando si el tipo esta en la tabla de tipos definidos*/
        
        if (st.containsKeyTT(val_peek(0).sval+":"+SymbolTable.ambitoGlobal.toString())) {
            yyval = val_peek(0); /* Si el tipo esta definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + val_peek(0).sval);
        } 
    }
break;
case 42:
//#line 280 "gramatica.y"
{
    
        int posicion = SymbolTable.pila.pop();
        SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));
        SymbolTable.pila.push(SymbolTable.polaca.size());


}
break;
case 43:
//#line 290 "gramatica.y"
{
    
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()+2));
    SymbolTable.pila.push(SymbolTable.polaca.size());
    SymbolTable.aggPolaca(""); SymbolTable.aggPolaca("BI");

}
break;
case 44:
//#line 298 "gramatica.y"
{
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));

}
break;
case 47:
//#line 308 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ELSE en el IF");}
break;
case 48:
//#line 309 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 49:
//#line 312 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 50:
//#line 316 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 51:
//#line 319 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 52:
//#line 323 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 53:
//#line 326 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 54:
//#line 330 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 55:
//#line 333 "gramatica.y"
{
                System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 56:
//#line 336 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 57:
//#line 337 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 58:
//#line 338 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 59:
//#line 339 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta END_IF.");}
break;
case 60:
//#line 344 "gramatica.y"
{SymbolTable.pila.push(SymbolTable.polaca.size());}
break;
case 61:
//#line 346 "gramatica.y"
{
    SymbolTable.aggPolaca(""); SymbolTable.aggPolaca("BI");
    int posicion = SymbolTable.pila.pop();
    SymbolTable.polaca.set(posicion, String.valueOf(SymbolTable.polaca.size()));
    SymbolTable.polaca.set(SymbolTable.polaca.size()-2, String.valueOf(SymbolTable.pila.pop()));
    }
break;
case 62:
//#line 352 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 63:
//#line 355 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaracion REPEAT.");
    }
break;
case 64:
//#line 358 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 65:
//#line 361 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 66:
//#line 362 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta while en el bucle repeat");}
break;
case 69:
//#line 370 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 70:
//#line 373 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 71:
//#line 376 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Parametro incorrecto en sentencia OUTF");}
break;
case 72:
//#line 377 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta contenido en el OUTF");}
break;
case 73:
//#line 382 "gramatica.y"
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
case 74:
//#line 418 "gramatica.y"
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
case 75:
//#line 442 "gramatica.y"
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
case 76:
//#line 465 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta tipo base en la declaracion de tipo.");
        }
break;
case 77:
//#line 468 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 78:
//#line 471 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Faltan '<' '>' en la declaracion de tipo.");
        }
break;
case 79:
//#line 474 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaracion de tipo.");
        }
break;
case 80:
//#line 477 "gramatica.y"
{
            System.err.println("Error en linea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 81:
//#line 480 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 82:
//#line 481 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 83:
//#line 482 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 84:
//#line 483 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el PAIR en la sentencia de declaracion de par");}
break;
case 85:
//#line 484 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 86:
//#line 485 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el identificador en la sentencia de declaracion de par");}
break;
case 87:
//#line 486 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el nombre del tipo definido");}
break;
case 88:
//#line 487 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el tipo base del nuevo tipo");}
break;
case 89:
//#line 488 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta el subrango del nuevo tipo");}
break;
case 90:
//#line 489 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Falta la asignacion en la definicion de nuevos tipos");}
break;
case 91:
//#line 491 "gramatica.y"
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
case 92:
//#line 516 "gramatica.y"
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
case 93:
//#line 540 "gramatica.y"
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
case 94:
//#line 558 "gramatica.y"
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
case 95:
//#line 581 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " -Falta el rango en el subrango");}
break;
case 96:
//#line 582 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 97:
//#line 587 "gramatica.y"
{
    SymbolTable.aggPolaca(val_peek(1).sval);
    SymbolTable.pila.push(SymbolTable.polaca.size()); SymbolTable.aggPolaca("");  SymbolTable.aggPolaca("BF"); 
}
break;
case 98:
//#line 591 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta comparador en la condicion");}
break;
case 99:
//#line 592 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 2da expresion en la condicion");}
break;
case 100:
//#line 593 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta 1ra expresion en la condicion");}
break;
case 101:
//#line 596 "gramatica.y"
{yyval.sval = "<=" ;}
break;
case 102:
//#line 597 "gramatica.y"
{yyval.sval = ">=";}
break;
case 103:
//#line 598 "gramatica.y"
{yyval.sval = "!=";}
break;
case 104:
//#line 599 "gramatica.y"
{yyval.sval = "=";}
break;
case 105:
//#line 600 "gramatica.y"
{yyval.sval = "<";}
break;
case 106:
//#line 601 "gramatica.y"
{yyval.sval = ">";}
break;
case 107:
//#line 605 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion"); }
break;
case 108:
//#line 606 "gramatica.y"
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
case 109:
//#line 676 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta lado derecho de la asignacion"); }
break;
case 110:
//#line 680 "gramatica.y"
{
            SymbolTable.aggPolaca(" "); SymbolTable.aggPolaca(" ");

           /* Crear una nueva lista con una sola expresión*/
           List<String> lista = new ArrayList<>();
           lista.add(val_peek(0).sval);  /* Almacenar la expresión como cadena de texto*/
           yyval.obj = lista;
        }
break;
case 111:
//#line 688 "gramatica.y"
{
            SymbolTable.aggPolaca(" "); SymbolTable.aggPolaca(" ");

            /* Agregar la expresión a la lista existente*/
            List<String> lista = (List<String>) val_peek(2).obj;
            lista.add(val_peek(0).sval);  /* Almacenar la nueva expresión*/
            yyval.obj = lista;
        }
break;
case 112:
//#line 699 "gramatica.y"
{
                
                /* Agregar el identificador a la lista*/
                st.esUsoValidoAmbito(val_peek(0).sval);
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yylval.obj = lista;
            }
break;
case 113:
//#line 707 "gramatica.y"
{
                 /* Agregar acceso_par (acceso a atributos o elementos) a la lista*/
                List<String> lista = (List<String>) val_peek(2).obj;
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 114:
//#line 713 "gramatica.y"
{
                
                st.esUsoValidoAmbito(val_peek(0).sval);
                /* Crear lista con el primer identificador*/
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 115:
//#line 721 "gramatica.y"
{
                /* Crear una nueva lista con acceso_par*/
                List<String> lista = new ArrayList<>();
                lista.add(val_peek(0).sval);
                yyval.obj = lista;
            }
break;
case 116:
//#line 727 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 117:
//#line 728 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 118:
//#line 729 "gramatica.y"
{ System.err.println("Error en linea: " + Lexer.nmrLinea + " Faltan ',' en las variables de las asignaciones multiples ");}
break;
case 119:
//#line 730 "gramatica.y"
{System.err.println("No puede haber constantes a la izquierda en la asignacion");}
break;
case 120:
//#line 731 "gramatica.y"
{System.err.println("No puede haber constantes a la izquierda en la asignacion");}
break;
case 121:
//#line 736 "gramatica.y"
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
case 122:
//#line 748 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se debe utilizar el indice 1 o 2 para acceder a los pares");}
break;
case 123:
//#line 749 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Se utilizan las llaves para acceder a los pares");}
break;
case 124:
//#line 753 "gramatica.y"
{
            System.err.println("ENTRE AL GOTO");
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
case 125:
//#line 771 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 126:
//#line 772 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 127:
//#line 773 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta");}
break;
case 128:
//#line 776 "gramatica.y"
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
case 129:
//#line 791 "gramatica.y"
{
        System.err.println("Error en linea: " + Lexer.nmrLinea + " - Invocacion a funcion mal definida"); 
        }
break;
case 130:
//#line 796 "gramatica.y"
{
    /* Asegúrate de que el valor de la expresión aritmética se pase correctamente hacia arriba*/
    yyval.sval = val_peek(0).sval;
}
break;
case 131:
//#line 802 "gramatica.y"
{
        SymbolTable.aggPolaca("+");
        yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
      }
break;
case 132:
//#line 806 "gramatica.y"
{
        SymbolTable.aggPolaca("-");
        yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
      }
break;
case 133:
//#line 810 "gramatica.y"
{
        SymbolTable.aggPolaca("*");
        yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
      }
break;
case 134:
//#line 814 "gramatica.y"
{
        SymbolTable.aggPolaca("/");
        yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
      }
break;
case 135:
//#line 818 "gramatica.y"
{
        SymbolTable.aggPolaca(val_peek(0).sval);
        yyval.sval = val_peek(0).sval;  /* La constante como cadena*/
      }
break;
case 136:
//#line 822 "gramatica.y"
{
        SymbolTable.aggPolaca(val_peek(0).sval);
        yyval.sval = val_peek(0).sval;  /* El identificador como cadena*/
      }
break;
case 137:
//#line 826 "gramatica.y"
{
        yyval.sval = val_peek(0).sval;  /* El resultado del acceso*/
      }
break;
case 138:
//#line 829 "gramatica.y"
{
        yyval.sval = val_peek(0).sval;  /* El valor unario*/
      }
break;
case 139:
//#line 835 "gramatica.y"
{
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("+");
            /* Devuelve la expresión como una cadena que representa la suma*/
            yyval.sval = val_peek(2).sval + " + " + val_peek(0).sval;
        }
break;
case 140:
//#line 843 "gramatica.y"
{
            if( (isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("-");
            /* Devuelve la expresión como una cadena que representa la resta*/
            yyval.sval = val_peek(2).sval + " - " + val_peek(0).sval;
        }
break;
case 141:
//#line 851 "gramatica.y"
{
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("*");
            /* Devuelve la expresión como una cadena que representa la multiplicación*/
            yyval.sval = val_peek(2).sval + " * " + val_peek(0).sval;
        }
break;
case 142:
//#line 859 "gramatica.y"
{
            if((isPair(val_peek(0).sval)|| isPair(val_peek(2).sval))){
                System.out.println("No se puede utilizar un par dentro de una expresion. Se debe usar acceso par.");
            }
            SymbolTable.aggPolaca("/");
            /* Devuelve la expresión como una cadena que representa la división*/
            yyval.sval = val_peek(2).sval + " / " + val_peek(0).sval;
        }
break;
case 143:
//#line 867 "gramatica.y"
{
            SymbolTable.aggPolaca(val_peek(0).sval);
            /* Devuelve el valor de la constante como cadena*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 144:
//#line 872 "gramatica.y"
{
            SymbolTable.aggPolaca(val_peek(0).sval);
            /* Devuelve el identificador como cadena*/
            st.esUsoValidoAmbito(val_peek(0).sval);
            yyval.sval = val_peek(0).sval;
        }
break;
case 145:
//#line 878 "gramatica.y"
{
            /* Devuelve el resultado del acceso a un parámetro*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 146:
//#line 882 "gramatica.y"
{
            /* Devuelve el resultado de la invocación de una función*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 147:
//#line 886 "gramatica.y"
{
            /* Devuelve la expresión unaria*/
            yyval.sval = val_peek(0).sval;
        }
break;
case 148:
//#line 890 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " - Error en Expresion");}
break;
case 149:
//#line 893 "gramatica.y"
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
//#line 2173 "Parser.java"
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
