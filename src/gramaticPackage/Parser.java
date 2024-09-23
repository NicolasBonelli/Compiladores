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
import gramaticPackage.*;
    

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
}



    
    
//#line 87 "Parser.java"




public class Parser
{
	 private Map<String, TipoSubrango> tablaTipos;
	 private SymbolTable st;
	 private Lexer lexer;
	 boolean yydebug;        //do I want debug output?
	 int yynerrs;            //number of errors so far
	 int yyerrflag;          //was there an error?
	 int yychar;             //the current working character
           //the current working character
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
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    4,   11,   16,   16,   17,   17,
   15,   15,   14,   14,   14,    6,    6,    6,    6,    6,
    6,    9,    9,   13,   13,   13,   19,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    7,    7,    7,    7,    7,    7,    7,    7,    7,
    7,    8,    8,    5,   20,   20,   20,   20,   21,   21,
   21,   21,   22,   22,   12,   10,   23,   18,   18,   18,
   18,   18,   18,   18,
};
final static short yylen[] = {                            2,
    2,    3,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    3,   10,    2,    1,    1,    5,
    3,    1,    1,    1,    1,    8,    8,   10,   10,   10,
   10,    5,    5,    6,    7,    7,    5,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,    3,    3,
    3,    7,    7,    4,    1,    3,    3,    1,    1,    3,
    3,    1,    4,    4,    3,    5,    1,    3,    3,    3,
    3,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    1,    0,    0,    0,    0,    0,   24,
   23,    0,   82,    0,    4,    5,    6,    7,    8,    9,
   10,    0,   12,   13,   14,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    2,    3,
    0,    0,    0,    0,    0,    0,    0,   22,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   84,    0,    0,    0,    0,    0,
    0,    0,   75,    0,    0,    0,    0,   56,    0,   57,
    0,   58,    0,   59,    0,   60,    0,   61,    0,    0,
   15,    0,   50,    0,   51,    0,   52,    0,   53,    0,
    0,    0,   80,   81,   54,    0,   55,    0,   72,    0,
    0,    0,   67,    0,    0,    0,    0,    0,   25,    0,
    0,    0,    0,   73,   74,    0,   21,   64,    0,    0,
   32,   33,    0,    0,    0,    0,    0,    0,   76,    0,
   71,    0,    0,    0,    0,    0,    0,   34,    0,    0,
    0,    0,    0,    0,    0,   35,   36,    0,   62,   63,
    0,    0,    0,   26,    0,    0,   27,    0,    0,    0,
    0,    0,    0,   37,    0,   19,    0,   18,   29,   28,
   30,   31,    0,   16,   17,    0,    0,   20,
};
final static short yydgoto[] = {                          2,
    4,   14,  176,   16,   17,   18,   19,   20,   21,   22,
   23,   24,   25,   26,   49,  177,  178,   27,  136,   28,
  111,   65,   75,
};
final static short yysindex[] = {                      -225,
 -205,    0,  423,    0,   19,   23, -224,  367, -212,    0,
    0,  -26,    0,  386,    0,    0,    0,    0,    0,    0,
    0,  121,    0,    0,    0, -226,   98,  -36,    0, -241,
 -266,   17, -176, -162, -152,   54, -188,   60,    0,    0,
 -241, -241, -241, -241, -241, -241, -164,    0,  -29, -241,
 -241, -241, -241, -188, -188, -188, -188, -241, -241, -241,
 -157,  -26,   82,  121,    0,   83,    2,   15, -160, -238,
   86,   87,    0,   36,   88,    3,    7,    0,   36,    0,
   36,    0,   36,    0,   36,    0,   36,    0,   36,   90,
    0, -145,    0,   36,    0,   36,    0,   36,    0,   36,
   33,   33,    0,    0,    0,   36,    0,   36,    0,   36,
  -28,    2,    0, -124,   80,   89,   84,   85,    0,   26,
 -241, -241,   91,    0,    0, -238,    0,    0, -241,  367,
    0,    0, -136, -126, -125,  103,  115,  116,    0, -115,
    0,   36, -155, -154,  108,  110,  126,    0,  112,  117,
  133,  367,  118,  367,  119,    0,    0, -100,    0,    0,
  -80,  -77,  -76,    0,  -74,  -73,    0,   67,  406,  136,
  140,  144,  145,    0,  165,    0,  -70,    0,    0,    0,
    0,    0, -188,    0,    0,   29,  147,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  524,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  350,    0,    0,    0,    0,    0,    0,   75,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -41,    0,    0,    0,    0,  -41,    0,    0,    0,
    0,    0,    0,  169,    0,    0,    0,    0,   28,    0,
   51,    0,   74,    0,   97,    0,  120,    0,  143,    0,
    0,    0,    0,  166,    0,  194,    0,  217,    0,  240,
  -18,    5,    0,    0,    0,  263,    0,  286,    0,  -12,
    0,  -34,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -8,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    1,    0,   14,    0,    0,    0,  -23,    0,    0,   43,
    0,    0,    0,  -65,    0,    0,   34,  546,    0,    0,
    0,   21,    0,
};
final static int YYTABLESIZE=802;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         83,
   83,   83,   83,   83,  120,   83,   63,   61,   34,   66,
   66,   67,   13,   37,   92,  129,   15,   83,   83,   83,
   83,   35,   78,   29,   78,   78,   78,   40,   29,   91,
  128,   69,   10,   11,   29,   70,   62,   13,   47,  119,
   78,   78,   78,   78,   32,   79,   69,   79,   79,   79,
   70,   48,    1,   33,    3,  116,   56,   54,   30,   55,
  140,   57,   31,   79,   79,   79,   79,   36,   44,  187,
   56,   54,   64,   55,   56,   57,   69,   56,   54,   57,
   55,  113,   57,   78,   80,   82,   84,   86,   88,   67,
   13,   45,   93,   95,   97,   99,   38,  137,  138,   70,
  105,  107,  109,  152,  154,   71,  153,  155,   76,   77,
  117,  118,   73,   90,   46,   72,   84,   84,   68,   84,
  112,   84,  114,  115,   38,  121,  122,  124,  123,  126,
  143,  125,  127,  130,   84,   84,   84,   47,  131,   56,
   54,  145,   55,  144,   57,  133,  134,  132,  135,  139,
   29,  146,  162,  147,  165,  149,  150,   58,   53,   59,
   48,  148,  151,   64,   64,  163,  156,  166,  157,  158,
  159,  141,   29,  161,   29,  160,  164,  167,  168,  169,
   45,   44,   46,   49,  170,  171,    5,  172,  173,   29,
  184,  174,    6,    7,  179,  175,    8,   29,  180,    9,
   10,   11,  181,  182,  183,  188,   38,   12,   13,   77,
  185,    0,    0,    0,    0,   83,    0,   83,    0,   83,
   83,   83,   83,    0,   83,   83,   83,    0,   83,   83,
   83,   83,   83,   83,   39,    0,   83,   83,   78,   60,
   78,   66,   78,   78,   78,   78,    0,   78,   78,   78,
    0,   78,   78,   78,   78,   78,   78,   40,    0,   78,
   78,   79,    0,   79,    0,   79,   79,   79,   79,    0,
   79,   79,   79,    0,   79,   79,   79,   79,   79,   79,
   41,    0,   79,   79,   44,    0,   44,    0,   44,   44,
   44,   44,    0,   44,   44,   44,    0,   44,   44,   44,
    0,    0,    0,   42,    0,   44,   44,   45,    0,   45,
    0,   45,   45,   45,   45,    0,   45,   45,   45,    0,
   45,   45,   45,    0,    0,    0,   43,    0,   45,   45,
   46,    0,   46,    0,   46,   46,   46,   46,    0,   46,
   46,   46,    0,   46,   46,   46,    0,   84,   84,   84,
   68,   46,   46,   47,    0,   47,    0,   47,   47,   47,
   47,    0,   47,   47,   47,    0,   47,   47,   47,    0,
   50,   51,   52,    0,   47,   47,   48,    0,   48,    0,
   48,   48,   48,   48,    0,   48,   48,   48,    0,   48,
   48,   48,    0,   41,   42,   43,    0,   48,   48,   49,
    0,   49,    0,   49,   49,   49,   49,    0,   49,   49,
   49,    0,   49,   49,   49,    0,    0,    0,    0,    0,
   49,   49,   38,    0,   38,    0,   38,   38,   38,   38,
    0,   38,   38,   38,    0,   38,   38,   38,    0,    0,
    0,    0,    0,   38,   38,    0,    0,    0,    0,    0,
   39,    0,   39,    0,   39,   39,   39,   39,    0,   39,
   39,   39,    0,   39,   39,   39,    0,    0,    0,    0,
    0,   39,   39,   40,    0,   40,    0,   40,   40,   40,
   40,    0,   40,   40,   40,    0,   40,   40,   40,    0,
    0,    0,    0,    0,   40,   40,   41,    0,   41,    0,
   41,   41,   41,   41,    0,   41,   41,   41,    0,   41,
   41,   41,    0,    0,    0,    0,    0,   41,   41,   42,
    0,   42,    0,   42,   42,   42,   42,    0,   42,   42,
   42,    0,   42,   42,   42,    0,    0,    0,    0,    0,
   42,   42,   43,    0,   43,    0,   43,   43,   43,   43,
    0,   43,   43,   43,    0,   43,   43,   43,    0,    0,
    0,    0,    0,   43,   43,   83,   83,   65,   83,    0,
   83,    0,    0,    0,    0,    0,   68,    0,    0,    0,
    0,    0,   74,   83,   83,   83,   79,   81,   83,   85,
   87,   89,    0,    0,    0,   94,   96,   98,  100,  101,
  102,  103,  104,  106,  108,  110,   11,    0,   11,    0,
   11,   11,   11,   11,    0,   11,   11,   11,    0,   11,
   11,   11,    0,    5,    0,    0,    3,   11,   11,    6,
    7,    0,    0,    8,    0,    0,    9,   10,   11,    0,
    0,    0,    5,    0,   12,   13,   39,    0,    6,    7,
    0,    0,    8,    0,    0,    9,   10,   11,    0,    0,
    0,    0,    5,   12,   13,    0,    0,    0,    6,    7,
    0,  175,    8,    0,  142,    9,   10,   11,    0,    5,
    0,    0,    0,   12,   13,    6,    7,    0,    0,    8,
    0,    0,    9,   10,   11,    0,    0,    0,    0,    0,
   12,   13,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  186,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   25,    0,
    0,    0,    0,    0,    0,    0,   83,   83,   83,   65,
    0,   25,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   70,   47,   30,   44,    8,   44,
  277,  278,  279,   40,   44,   44,    3,   59,   60,   61,
   62,    8,   41,    3,   43,   44,   45,   14,    8,   59,
   59,   44,  271,  272,   14,   44,  278,  279,  265,  278,
   59,   60,   61,   62,  269,   41,   59,   43,   44,   45,
   59,  278,  278,  278,  260,   41,   42,   43,   40,   45,
  126,   47,   40,   59,   60,   61,   62,  280,   41,   41,
   42,   43,   30,   45,   42,   47,   60,   42,   43,   47,
   45,   61,   47,   41,   42,   43,   44,   45,   46,  278,
  279,   41,   50,   51,   52,   53,  123,  121,  122,  276,
   58,   59,   60,  259,  259,  268,  262,  262,   49,   50,
  271,  272,   59,  278,   41,  268,   42,   43,   44,   45,
  278,   47,   41,   41,  123,   40,   40,  125,   41,   40,
  130,  125,  278,  258,   60,   61,   62,   41,   59,   42,
   43,  278,   45,  130,   47,   62,   62,   59,  123,   59,
  130,  278,  152,  279,  154,   41,   41,   60,   61,   62,
   41,   59,  278,  121,  122,  152,   59,  154,   59,   44,
   59,  129,  152,   41,  154,   59,   59,   59,  279,  260,
   60,   61,   62,   41,  262,  262,  257,  262,  262,  169,
  261,  125,  263,  264,   59,  266,  267,  177,   59,  270,
  271,  272,   59,   59,   40,   59,   41,  278,  279,   41,
  177,   -1,   -1,   -1,   -1,  257,   -1,  259,   -1,  261,
  262,  263,  264,   -1,  266,  267,  268,   -1,  270,  271,
  272,  273,  274,  275,   41,   -1,  278,  279,  257,  276,
  259,  276,  261,  262,  263,  264,   -1,  266,  267,  268,
   -1,  270,  271,  272,  273,  274,  275,   41,   -1,  278,
  279,  257,   -1,  259,   -1,  261,  262,  263,  264,   -1,
  266,  267,  268,   -1,  270,  271,  272,  273,  274,  275,
   41,   -1,  278,  279,  257,   -1,  259,   -1,  261,  262,
  263,  264,   -1,  266,  267,  268,   -1,  270,  271,  272,
   -1,   -1,   -1,   41,   -1,  278,  279,  257,   -1,  259,
   -1,  261,  262,  263,  264,   -1,  266,  267,  268,   -1,
  270,  271,  272,   -1,   -1,   -1,   41,   -1,  278,  279,
  257,   -1,  259,   -1,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,  273,  274,  275,
  276,  278,  279,  257,   -1,  259,   -1,  261,  262,  263,
  264,   -1,  266,  267,  268,   -1,  270,  271,  272,   -1,
  273,  274,  275,   -1,  278,  279,  257,   -1,  259,   -1,
  261,  262,  263,  264,   -1,  266,  267,  268,   -1,  270,
  271,  272,   -1,  273,  274,  275,   -1,  278,  279,  257,
   -1,  259,   -1,  261,  262,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,
  278,  279,  257,   -1,  259,   -1,  261,  262,  263,  264,
   -1,  266,  267,  268,   -1,  270,  271,  272,   -1,   -1,
   -1,   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,   -1,
  257,   -1,  259,   -1,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
   -1,  278,  279,  257,   -1,  259,   -1,  261,  262,  263,
  264,   -1,  266,  267,  268,   -1,  270,  271,  272,   -1,
   -1,   -1,   -1,   -1,  278,  279,  257,   -1,  259,   -1,
  261,  262,  263,  264,   -1,  266,  267,  268,   -1,  270,
  271,  272,   -1,   -1,   -1,   -1,   -1,  278,  279,  257,
   -1,  259,   -1,  261,  262,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,
  278,  279,  257,   -1,  259,   -1,  261,  262,  263,  264,
   -1,  266,  267,  268,   -1,  270,  271,  272,   -1,   -1,
   -1,   -1,   -1,  278,  279,   42,   43,   44,   45,   -1,
   47,   -1,   -1,   -1,   -1,   -1,   31,   -1,   -1,   -1,
   -1,   -1,   37,   60,   61,   62,   41,   42,   43,   44,
   45,   46,   -1,   -1,   -1,   50,   51,   52,   53,   54,
   55,   56,   57,   58,   59,   60,  257,   -1,  259,   -1,
  261,  262,  263,  264,   -1,  266,  267,  268,   -1,  270,
  271,  272,   -1,  257,   -1,   -1,  260,  278,  279,  263,
  264,   -1,   -1,  267,   -1,   -1,  270,  271,  272,   -1,
   -1,   -1,  257,   -1,  278,  279,  261,   -1,  263,  264,
   -1,   -1,  267,   -1,   -1,  270,  271,  272,   -1,   -1,
   -1,   -1,  257,  278,  279,   -1,   -1,   -1,  263,  264,
   -1,  266,  267,   -1,  129,  270,  271,  272,   -1,  257,
   -1,   -1,   -1,  278,  279,  263,  264,   -1,   -1,  267,
   -1,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,
  278,  279,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  183,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  265,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,  276,
   -1,  278,
};
}
final static short YYFINAL=2;
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
"bloque_sentencias : BEGIN sentencias END",
"sentencias : sentencias sentencia",
"sentencias : sentencia",
"sentencia : declaracion",
"sentencia : asignacion",
"sentencia : if_statement",
"sentencia : condicion",
"sentencia : repeat_while_statement",
"sentencia : salida",
"sentencia : invocacion_funcion",
"sentencia : declaracion_funcion",
"sentencia : goto_statement",
"sentencia : sentencia_declarativa_tipos",
"declaracion : tipo lista_var ';'",
"declaracion_funcion : tipo FUN T_ID '(' tipo T_ID ')' BEGIN cuerpo_funcion END",
"cuerpo_funcion : cuerpo_funcion sentencias_funcion",
"cuerpo_funcion : sentencias_funcion",
"sentencias_funcion : sentencia",
"sentencias_funcion : RET '(' expresion ')' ';'",
"lista_var : lista_var ',' T_ID",
"lista_var : T_ID",
"tipo : DOUBLE",
"tipo : LONGINT",
"tipo : T_ID",
"if_statement : IF '(' condicion ')' THEN bloque_sentencias END_IF ';'",
"if_statement : IF '(' condicion ')' THEN sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' THEN bloque_sentencias ELSE sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' THEN bloque_sentencias ELSE bloque_sentencias END_IF ';'",
"if_statement : IF '(' condicion ')' THEN sentencia ELSE bloque_sentencias END_IF ';'",
"if_statement : IF '(' condicion ')' THEN sentencia ELSE sentencia END_IF ';'",
"salida : OUTF '(' T_CADENA ')' ';'",
"salida : OUTF '(' expresion ')' ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'",
"subrango : '{' T_CTE ',' T_CTE '}'",
"condicion : expresion MENOR_IGUAL expresion",
"condicion : expresion MAYOR_IGUAL expresion",
"condicion : expresion DISTINTO expresion",
"condicion : expresion '=' expresion",
"condicion : expresion '<' expresion",
"condicion : expresion '>' expresion",
"condicion : invocacion_funcion MENOR_IGUAL expresion",
"condicion : invocacion_funcion MAYOR_IGUAL expresion",
"condicion : invocacion_funcion DISTINTO expresion",
"condicion : invocacion_funcion '=' expresion",
"condicion : invocacion_funcion '<' expresion",
"condicion : invocacion_funcion '>' expresion",
"condicion : expresion MENOR_IGUAL invocacion_funcion",
"condicion : expresion MAYOR_IGUAL invocacion_funcion",
"condicion : expresion DISTINTO invocacion_funcion",
"condicion : expresion '=' invocacion_funcion",
"condicion : expresion '<' invocacion_funcion",
"condicion : expresion '>' invocacion_funcion",
"condicion : invocacion_funcion MENOR_IGUAL invocacion_funcion",
"condicion : invocacion_funcion MAYOR_IGUAL invocacion_funcion",
"condicion : invocacion_funcion DISTINTO invocacion_funcion",
"condicion : invocacion_funcion '=' invocacion_funcion",
"condicion : invocacion_funcion '<' invocacion_funcion",
"condicion : invocacion_funcion '>' invocacion_funcion",
"repeat_while_statement : REPEAT bloque_sentencias WHILE '(' condicion ')' ';'",
"repeat_while_statement : REPEAT sentencia WHILE '(' condicion ')' ';'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list ';'",
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : acceso_par",
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"expresion_list : expresion_list ',' invocacion_funcion",
"expresion_list : invocacion_funcion",
"acceso_par : T_ID '{' '1' '}'",
"acceso_par : T_ID '{' '2' '}'",
"goto_statement : GOTO T_ETIQUETA ';'",
"invocacion_funcion : T_ID '(' parametro_real ')' ';'",
"parametro_real : expresion",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '/' expresion",
"expresion : T_CTE",
"expresion : T_ID",
"expresion : acceso_par",
};

//#line 340 "gramatica.y"


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
    Parser parser = new Parser("C:\\Users\\hecto\\OneDrive\\Escritorio\\prueba.txt");
    parser.run();
}


//#line 600 "Parser.java"
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

public void yyerror(String s) {
	  System.err.println("Error: " + s);
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
//#line 82 "gramatica.y"
{  
    System.out.println("Programa compilado correctamente");
}
break;
case 2:
//#line 86 "gramatica.y"
{System.out.println("Llegue a BEGIN sentencia END");}
break;
case 4:
//#line 89 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 14:
//#line 101 "gramatica.y"
{System.out.println("Llegue a sentencia");}
break;
case 15:
//#line 103 "gramatica.y"
{ 
	System.out.println("Llegue a declaracion");
    List<ParserVal> variables = new ArrayList<ParserVal>();
    variables.add(val_peek(1)); /* Asume que lista_var devuelve una lista de variables*/
    
    for (ParserVal variable : variables) {
        /* Verificar si la variable ya existe en la tabla de símbolos*/
        if (!st.hasKey(variable.toString())) {
            System.out.println("ERROR, la tabla de símbolos no contenía la variable: " + variable);
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
case 16:
//#line 124 "gramatica.y"
{System.out.println("declaracion_funcion");}
break;
case 18:
//#line 128 "gramatica.y"
{System.out.println("Llegue a cuerpo_funcion");}
break;
case 20:
//#line 133 "gramatica.y"
{System.out.println("Llegue a sentencia_funcion");}
break;
case 21:
//#line 138 "gramatica.y"
{ 
	  System.out.println("Llegue a lista_var 1");
	    /* Si ya tenemos una lista de variables, añadimos la nueva variable*/
	    List<String> variables = (List<String>)val_peek(2);
	    variables.add(val_peek(0).toString());
	    yyval = (ParserVal) variables;  /* Devolvemos la lista actualizada*/
	    /* Creamos un ParserVal para almacenar la lista en el campo 'obj' */
	    yyval = new ParserVal();
	    yyval.obj = variables;  /* Almacenamos la lista en el campo 'obj' de ParserVal */
}
break;
case 22:
//#line 145 "gramatica.y"
{ 
	System.out.println("Llegue a lista_var 2");
    /* Creamos una nueva lista con la primera variable */
    List<String> variables = new ArrayList<>();
    variables.add(val_peek(0).toString());
    
    /* Creamos un ParserVal para almacenar la lista en el campo 'obj' */
    yyval = new ParserVal();
    yyval.obj = variables;  /* Almacenamos la lista en el campo 'obj' de ParserVal */
}
break;
case 23:
	//#line 153 "gramatica.y"
	{ yyval = new ParserVal("double"); }
	break;
	case 24:
	//#line 154 "gramatica.y"
	{ yyval = new ParserVal("longint"); }
	break;
case 25:
//#line 156 "gramatica.y"
{
        System.out.println("Llegue a tipo");
        /* Verificar si el tipo está en la tabla de tipos definidos*/
        if (tablaTipos.containsKey(val_peek(0))) {
            yyval = val_peek(0); /* Si el tipo está definido, se usa el nombre del tipo*/
        } else {
            yyerror("Tipo no definido: " + val_peek(0));
        }
    }
break;
case 31:
//#line 175 "gramatica.y"
{        System.out.println("Llegue a if_statement");        }
break;
case 33:
//#line 179 "gramatica.y"
{     System.out.println("Llegue a salida");   }
break;
case 36:
//#line 185 "gramatica.y"
{
	System.out.println("Llegue a sentencia_declarativa_tipos");
    /* Guardar el nuevo tipo en la tabla de símbolos*/
        String nombreTipo = val_peek(5).toString(); /* T_ID*/
        String tipoBase = val_peek(3).toString(); /* tipo base (INTEGER o SINGLE)*/
        double limiteInferior = Double.parseDouble(val_peek(2).toString()); /* Limite inferior del subrango*/
        double limiteSuperior = Double.parseDouble(val_peek(1).toString()); /* Limite superior del subrango*/

    /* Almacenar en la tabla de símbolos*/
    tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
    }
break;
case 37:
//#line 197 "gramatica.y"
{
	System.out.println("Llegue a subrango");

	yyval = new ParserVal(new Subrango(Double.parseDouble(val_peek(3).toString()), Double.parseDouble(val_peek(1).toString())));

    }
break;
case 38:
//#line 205 "gramatica.y"
{ System.out.println("Llegue a MENOR_IGUAL");
}
break;
case 62:
//#line 233 "gramatica.y"
{System.out.println("Llegue a repeat_while");}
break;
case 64:
//#line 237 "gramatica.y"
{ 
	
	System.out.println("Aca estaba el control de tipos");
       
}
break;
case 65:
//#line 274 "gramatica.y"
{ /*yyval = new ParserVal(new ArrayList<ParserVal>());  // Inicializas correctamente con una nueva lista
	
//Verifica que val_peek(0) no sea nulo antes de añadirlo
if (val_peek(0) != null) {
 ((ArrayList<ParserVal>)yyval.obj).add(val_peek(0));  // Casteas a ArrayList y añades val_peek(0)
}  // Casteamos obj a ArrayList
      */
	System.out.println("IDENTIFIER_LIST: T_ID ");
}
break;
case 66:
//#line 277 "gramatica.y"
{/*
	yyval = new ParserVal(new ArrayList<ParserVal>());  // Inicializas con una lista vacía
	yyval.list.add(val_peek(0));  // Añades el elemento a la lista correctamente
	val_peek(2).list.add(val_peek(0));  // Aquí val_peek(2) ya es un ParserVal que contiene una lista*/
	System.out.println("IDENTIFIER_LIST: IDENTIFIER_LIST ',' T_ID");


                }
break;
case 67:
//#line 281 "gramatica.y"
{/*
	yyval = new ParserVal(new ArrayList<ParserVal>());  // Inicializas con una lista vacía
	yyval.list.add(val_peek(0));  // Añades el elemento a la lista correctamente
	val_peek(2).list.add(val_peek(0));  // Aquí val_peek(2) ya es un ParserVal que contiene una lista */
	System.out.println("IDENTIFIER_LIST: IDENTIFIER_LIST ',' acceso_par");

                }
break;
case 68:
//#line 285 "gramatica.y"
{/*
	 yyval = new ParserVal(new ArrayList<ParserVal>());  // Crear un ParserVal con un ArrayList
	 ((ArrayList<ParserVal>)yyval.obj).add(val_peek(0)) */
	System.out.println("IDENTIFIER_LIST: acceso_par");
                }
break;
case 69:
//#line 290 "gramatica.y"
{/*
	 yyval = new ParserVal(new ArrayList<ParserVal>());  // Crear un ParserVal con un ArrayList
	 ((ArrayList<ParserVal>)yyval.obj).add(val_peek(0));
	 */
	 System.out.println("expresion_list: expresion");
              }
break;
case 70:
//#line 294 "gramatica.y"
{
	/* yyval = new ParserVal(new ArrayList<ParserVal>());  // Inicializas con una lista vacía
	yyval.list.add(val_peek(0));  // Añades el elemento a la lista correctamente
	val_peek(2).list.add(val_peek(0));  // Aquí val_peek(2) ya es un ParserVal que contiene una lista */
	
	System.out.println("expresion_list: expresion_list ',' expresion");
              }
break;
case 71:
//#line 298 "gramatica.y"
{
	/*
	yyval = new ParserVal(new ArrayList<ParserVal>());  // Inicializas con una lista vacía
	yyval.list.add(val_peek(0));  // Añades el elemento a la lista correctamente
	val_peek(2).list.add(val_peek(0));  // Aquí val_peek(2) ya es un ParserVal que contiene una 

	*/
	
	System.out.println("expresion_list: expresion_list ',' invocacion_funcion");

              }
break;
case 72:
//#line 302 "gramatica.y"
{/*
	yyval = new ParserVal(new ArrayList<ParserVal>());  // Crear un ParserVal con un ArrayList
	 ((ArrayList<ParserVal>)yyval.obj).add(val_peek(0)); */
	System.out.println("expresion_list: invocacion_funcion");

              }
break;
case 73:
	//#line 251 "gramatica.y"
		{ yyval = new ParserVal(val_peek(3).toString() + "{1}"); }
		break;
		case 74:
		//#line 252 "gramatica.y"
		{ yyval = new ParserVal(val_peek(3).toString() + "{2}"); }
		break;

case 78:
//#line 317 "gramatica.y"
{
    System.out.println("Llegue a variable := expresion suma");        
	//yyval = (Integer (val_peek(2) + val_peek(0)));  /* Asegúrate de manejar los tipos correctamente*/
        }
break;
case 79:
//#line 320 "gramatica.y"
{
	System.out.println("Llegue a variable := expresion resta");        
	//yyval = (Integer (val_peek(2) - val_peek(0)));  /* Asegúrate de manejar los tipos correctamente*/        }
}break;
case 80:
//#line 323 "gramatica.y"
{
	System.out.println("Llegue a variable := expresion multi");        
	//yyval = (Integer (val_peek(2) * val_peek(0)));  /* Asegúrate de manejar los tipos correctamente*/
}
break;
case 81:
//#line 326 "gramatica.y"
{
	System.out.println("Llegue a variable := expresion division");        
	//yyval = (Integer (val_peek(2) / val_peek(0)));  /* Asegúrate de manejar los tipos correctamente*/
}
break;
case 82:
//#line 329 "gramatica.y"
{
            yyval = val_peek(0);  /* Suponiendo que T_CTE es un número constante*/
        }
break;
case 83:
//#line 332 "gramatica.y"
{
            yyval = new ParserVal(st.hasKey(val_peek(0).toString()));  /* Buscar el valor del identificador en la tabla de símbolos*/
        }
break;
case 84:
//#line 335 "gramatica.y"
{
    yyval = new ParserVal(st.hasKey(val_peek(0).toString()));  /* Buscar el valor del identificador en la tabla de símbolos*/
        }
break;
//#line 1024 "Parser.java"
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

/*#line 66 "Parser.java"*/


/* Función para verificar si el valor está dentro del rango*/
boolean verificarRango(String tipo, double valor) {
    if (tablaTipos.containsKey(tipo)) {
        TipoSubrango subrango = tablaTipos.get(tipo);
        return valor >= subrango.limiteInferior && valor <= subrango.limiteSuperior;
    }
    return true; /* Si no es un tipo definido por el usuario, no se verifica el rango*/
}

/* Definir rangos para tipos estándar*/
boolean verificarRangoLongInt(double valor) {
    return valor >= -Math.pow(2, 31) && valor <= Math.pow(2, 31) - 1;
}

boolean verificarRangoDouble(double valor) {
    return valor >= -1.7976931348623157e308 && valor <= 1.7976931348623157e308;
}

String obtenerTipo(String variable) {
    /* Implementa la lógica para obtener el tipo de la variable a partir de una tabla de símbolos.*/
    /* Debe devolver el tipo como "longint", "double" o un tipo definido por el usuario.*/
    if (!st.hasKey(variable)) return variable;

    return st.getType(variable);  /* Ejemplo*/
}

}
//################### END OF CLASS ##############################
