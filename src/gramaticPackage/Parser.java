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

/*#line 66 "Parser.java"*/


   


    
//#line 59 "Parser.java"




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
    0,    1,    2,    2,    3,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    4,    9,   15,   15,   14,   14,
   13,   13,   13,    6,    6,    7,    8,    8,   11,   11,
   11,   17,   17,   17,   17,   16,   16,   16,   18,   18,
   18,   18,   18,   18,    5,   19,   19,   19,   19,   20,
   20,   21,   10,   10,   22,   23,   24,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   26,   26,   26,
   26,   26,   26,   12,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   25,
};
final static short yylen[] = {                            2,
    2,    3,    2,    1,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    5,    3,    8,    1,    1,    3,    1,
    1,    1,    1,    8,   10,    7,    5,    5,    6,    7,
    7,    5,    6,    6,    7,    3,    2,    2,    1,    1,
    1,    1,    1,    1,    4,    1,    3,    3,    1,    1,
    3,    4,    3,    2,    4,    1,    3,    3,    3,    3,
    1,    1,    1,    1,    4,    4,    4,    1,    1,    1,
    2,    2,    2,    3,    3,    3,    3,    1,    1,    1,
    1,    1,    4,    4,    4,    2,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    1,    0,    0,    0,    0,    0,    0,
   22,   21,    0,   13,    0,    4,    5,    6,    7,    8,
    9,   10,   11,   12,    0,    0,   49,    0,    0,    0,
    0,    0,   17,   18,    0,    0,   54,    0,    2,    3,
    0,   20,    0,    0,    0,   39,   40,   41,    0,   78,
    0,   43,   44,   42,    0,    0,    0,   80,   81,   82,
    0,    0,    0,    0,    0,    0,   53,    0,    0,   15,
    0,    0,    0,    0,   48,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   23,    0,
    0,    0,   52,    0,   19,   45,    0,    0,   61,   63,
    0,    0,   64,   68,   69,   70,    0,    0,    0,   76,
    0,   77,    0,    0,    0,   27,   28,    0,    0,    0,
    0,   14,    0,    0,    0,   55,    0,    0,    0,    0,
   71,   73,   72,    0,   84,   85,    0,    0,    0,    0,
    0,   29,    0,    0,    0,    0,    0,   59,    0,   60,
    0,    0,    0,   30,   31,    0,    0,   26,    0,    0,
   66,   67,    0,   24,    0,    0,    0,   16,    0,   32,
    0,    0,    0,   25,   34,   33,    0,   35,
};
final static short yydgoto[] = {                          2,
   33,   15,   34,   17,   18,   19,   20,   21,   22,   23,
   24,   55,   25,   43,   35,   56,  121,   57,   26,   73,
   58,   59,  101,  102,   60,  108,
};
final static short yysindex[] = {                      -264,
 -225,    0,  -64,    0,   31,   33, -150,   86,  -82,  -57,
    0,    0,  -83,    0,   56,    0,    0,    0,    0,    0,
    0,    0,    0,    0, -178,    2,    0,    6,   -6,   36,
 -145,   -4,    0,    0, -125,   85,    0, -133,    0,    0,
 -129,    0,   50,   -4, -124,    0,    0,    0,  -18,    0,
   -4,    0,    0,    0,   41,  114,   -4,    0,    0,    0,
  115,  118, -138, -142,  177,  122,    0,   39,  127,    0,
 -108,  265,   51,  -83,    0,   27,   76,  -42,   -4,  -42,
  -42,   -4,  -81,  265,  117,  120,  121,  124,    0,   57,
  128,    6,    0, -142,    0,    0,   -4,  -83,    0,    0,
  150,  295,    0,    0,    0,    0,   76,  -36,   76,    0,
  -36,    0,  -36,  265,  -82,    0,    0,  -86,  -84,  -41,
  136,    0,  160,  -74,  265,    0,  -30,   27,  -30,  -30,
    0,    0,    0,   76,    0,    0, -169,  146,  162,  182,
  -49,    0,  173,  194,   78,  -24,   78,    0,  -24,    0,
  -24,  -82,  180,    0,    0,  -35,  196,    0, -225,   78,
    0,    0,  -21,    0,  126,  -34,  -29,    0,  187,    0,
  134,  135,  -32,    0,    0,    0,  139,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   26,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -17,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   62,    0,   25,    0,    0,  -12,    0,    0,    0,
    0,  211,    0,  212,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  288,    0,    0,
    0,  224,    0,    0,    0,    0,   -7,    0,   15,    0,
    0,    0,    0,  228,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   63,    0,    0,    0,    0,    0,
    0,    0,    0,   20,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  172,    0,  184,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  255,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    7,    0,  109,    0,    0,    0,    0,    0,    0,    0,
    0,   60,   14,    0,  -95,  178,    0,  221,    0,    0,
   82,    0,    0,   23,  -14,   18,
};
final static int YYTABLESIZE=342;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        105,
  104,   37,   51,  141,  106,  132,  131,    4,   51,  166,
  133,  105,  104,    1,   51,  173,  106,  132,  131,  137,
   51,   76,  133,   79,   79,   79,   79,   79,   86,   79,
   86,   86,   86,   74,    3,   74,   74,   74,   51,   38,
   51,   79,   79,   79,   79,   45,   86,   86,   86,   86,
   51,   74,   74,   74,   74,   75,  163,   75,   75,   75,
   83,  103,   83,   83,   83,   52,   54,   53,   47,   46,
   28,   51,   29,   75,   75,   75,   75,   90,   83,   83,
   83,   83,   80,   78,   27,   79,   41,   81,   62,  152,
   27,   65,  153,   71,   97,   63,   27,  111,  113,   42,
   52,   54,   53,   72,   38,   50,   51,  124,   70,   96,
   77,   16,  103,  103,  103,  103,   84,   80,   30,  129,
   50,   51,   81,   40,  130,   32,   75,   31,   11,   12,
   64,  103,   87,   88,  103,   89,  103,  107,  109,  110,
  112,  114,   66,   67,  146,   68,  149,  151,   69,  145,
  147,  148,  150,   74,   83,   85,  125,  100,   86,   80,
   78,   92,   79,   93,   81,  168,   94,  134,  160,   95,
  135,  161,  136,  162,    5,  116,  115,    3,  117,  120,
    6,    7,  118,    8,    9,  119,  122,   10,   11,   12,
  126,  138,    5,  139,  142,   13,   27,   14,    6,    7,
  143,    8,    9,  144,  154,   10,   11,   12,  100,  100,
  100,  100,   57,   13,   57,   14,   57,   91,   80,   78,
  155,   79,   36,   81,   58,  156,   58,  100,   58,  157,
  100,  158,  100,   27,  159,   49,   50,  140,  164,  167,
  169,   49,   50,  165,  171,  174,  177,   98,   99,  172,
  170,   37,   38,   98,   99,   79,   79,   79,  175,  176,
   86,   86,   86,  178,   56,   74,   74,   74,   36,  123,
   61,   49,   50,   49,   50,   82,    0,   44,   46,   47,
   48,    0,    0,   49,   50,    0,    0,   75,   75,   75,
   23,    0,   83,   83,   83,   65,    0,   65,    0,   65,
   47,   46,    0,   23,   98,   99,   80,   78,    0,   79,
    0,   81,    5,   46,   47,   48,   39,    0,    6,    7,
    0,    8,    9,    0,    0,   10,   11,   12,   62,   62,
   62,    0,   62,   13,   62,   14,  129,  127,    0,  128,
    0,  130,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         42,
   43,   59,   45,   45,   47,   42,   43,    1,   45,   45,
   47,   42,   43,  278,   45,   45,   47,   42,   43,  115,
   45,   40,   47,   41,   42,   43,   44,   45,   41,   47,
   43,   44,   45,   41,  260,   43,   44,   45,   45,  123,
   45,   59,   60,   61,   62,   44,   59,   60,   61,   62,
   45,   59,   60,   61,   62,   41,  152,   43,   44,   45,
   41,   76,   43,   44,   45,   60,   61,   62,   44,   44,
   40,   45,   40,   59,   60,   61,   62,   64,   59,   60,
   61,   62,   42,   43,    3,   45,  265,   47,   29,  259,
    9,   32,  262,   44,   44,   60,   15,   80,   81,  278,
   60,   61,   62,   44,  123,   44,   44,   94,   59,   59,
   51,    3,  127,  128,  129,  130,   57,   42,  269,   42,
   59,   59,   47,   15,   47,   40,   45,  278,  271,  272,
  276,  146,  271,  272,  149,  278,  151,   78,   79,   80,
   81,   82,  268,   59,  127,  279,  129,  130,  278,  127,
  128,  129,  130,  278,   41,   41,   97,   76,   41,   42,
   43,   40,   45,  125,   47,  159,   40,  108,  146,  278,
  111,  149,  113,  151,  257,   59,  258,  260,   59,  123,
  263,  264,   62,  266,  267,   62,   59,  270,  271,  272,
   41,  278,  257,  278,   59,  278,  115,  280,  263,  264,
   41,  266,  267,  278,   59,  270,  271,  272,  127,  128,
  129,  130,   41,  278,   43,  280,   45,   41,   42,   43,
   59,   45,  280,   47,   41,   44,   43,  146,   45,  279,
  149,   59,  151,  152,   41,  278,  279,  279,   59,   44,
  262,  278,  279,  279,  279,   59,  279,  278,  279,  279,
  125,   41,   41,  278,  279,  273,  274,  275,  125,  125,
  273,  274,  275,  125,   41,  273,  274,  275,   41,   92,
  277,  278,  279,  278,  279,   55,   -1,  276,  273,  274,
  275,   -1,   -1,  278,  279,   -1,   -1,  273,  274,  275,
  265,   -1,  273,  274,  275,   41,   -1,   43,   -1,   45,
  276,  276,   -1,  278,  278,  279,   42,   43,   -1,   45,
   -1,   47,  257,  273,  274,  275,  261,   -1,  263,  264,
   -1,  266,  267,   -1,   -1,  270,  271,  272,   41,   42,
   43,   -1,   45,  278,   47,  280,   42,   43,   -1,   45,
   -1,   47,
};
}
final static short YYFINAL=2;
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
"bloque_sentencias : BEGIN sentencias END",
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
"declaracion : tipo lista_var ';'",
"declaracion_funcion : tipo FUN T_ID '(' tipo T_ID ')' bloque_sentencias",
"repeat_sentencia : bloque_sentencias",
"repeat_sentencia : sentencia",
"lista_var : lista_var ',' T_ID",
"lista_var : T_ID",
"tipo : DOUBLE",
"tipo : LONGINT",
"tipo : T_ID",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia END_IF ';'",
"if_statement : IF '(' condicion ')' THEN repeat_sentencia ELSE repeat_sentencia END_IF ';'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')' ';'",
"salida : OUTF '(' T_CADENA ')' ';'",
"salida : OUTF '(' expresion ')' ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'",
"subrango : '{' T_CTE ',' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' T_CTE '}'",
"subrango : '{' T_CTE ',' '-' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' '-' T_CTE '}'",
"condicion : expresion comparador expresion",
"condicion : expresion comparador",
"condicion : comparador expresion",
"comparador : MENOR_IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : DISTINTO",
"comparador : '='",
"comparador : '<'",
"comparador : '>'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list ';'",
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : acceso_par",
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"acceso_par : T_ID '{' T_CTE '}'",
"goto_statement : GOTO T_ETIQUETA ';'",
"goto_statement : GOTO ';'",
"invocacion_funcion : T_ID '(' parametro_real ')'",
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

//#line 259 "gramatica.y"

public void yyerror(String s) {
    System.err.println("Error: " + s);
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
    Parser parser = new Parser("C:\\Users\\hecto\\OneDrive\\Escritorio\\prueba.txt");
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
//#line 525 "Parser.java"
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
//#line 54 "gramatica.y"
{  
    System.out.println("Programa compilado correctamente");
}
break;
case 2:
//#line 58 "gramatica.y"
{System.out.println("Llegue a BEGIN sentencia END");}
break;
case 4:
//#line 61 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 14:
//#line 73 "gramatica.y"
{System.out.println("Llegue a sentencia");}
break;
case 15:
//#line 76 "gramatica.y"
{ 
    System.out.println("Llegue a declaracion");
    List<String> variables = (List<String>)val_peek(1); /* Asume que lista_var devuelve una lista de variables*/
    
    for (String variable : variables) {
        /* Verificar si la variable ya existe en la tabla de símbolos*/
        if (!st.hasKey(variable)) {
            System.out.println("ERROR, la tabla de símbolos no contenía la variable: " + variable);
        } else {
            /* Actualiza el tipo de la variable si ya está en la tabla de símbolos*/
            boolean actualizado = st.updateType(variable, val_peek(2).sval);
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
//#line 97 "gramatica.y"
{System.out.println("declaracion_funcion");}
break;
case 17:
//#line 101 "gramatica.y"
{}
break;
case 18:
//#line 102 "gramatica.y"
{}
break;
case 19:
//#line 105 "gramatica.y"
{ 
   
}
break;
case 20:
//#line 108 "gramatica.y"
{ 
    
}
break;
case 21:
//#line 112 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 22:
//#line 113 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 23:
//#line 115 "gramatica.y"
{
        System.out.println("Llegue a tipo");
        /* Verificar si el tipo está en la tabla de tipos definidos*/
        if (tablaTipos.containsKey(val_peek(0).sval)) {
            yyval = val_peek(0); /* Si el tipo está definido, se usa el nombre del tipo*/
        } else {
            yyerror("Tipo no definido: " + val_peek(0));
        }
    }
break;
case 28:
//#line 137 "gramatica.y"
{     System.out.println("Llegue a salida");   }
break;
case 31:
//#line 143 "gramatica.y"
{
            System.out.println("Llegue a sentencia_declarativa_tipos");
        /* Guardar el nuevo tipo en la tabla de símbolos*/
        String nombreTipo = val_peek(5).sval; /* T_ID*/
        String tipoBase = val_peek(3).sval; /* tipo base (INTEGER o SINGLE)*/
        double limiteInferior = val_peek(2).dval; /* Limite inferior del subrango*/
        double limiteSuperior = val_peek(1).dval; /* Limite superior del subrango*/

        /* Almacenar en la tabla de símbolos*/
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
    }
break;
case 32:
//#line 155 "gramatica.y"
{
        System.out.println("Llegue a subrango");

        /*$$ = new Subrango(Double.parseDouble($2), Double.parseDouble($4));*/
    }
break;
case 37:
//#line 166 "gramatica.y"
{System.err.println("Falta expresion del lado derecho de la comparacion");}
break;
case 38:
//#line 167 "gramatica.y"
{System.err.println("Falta expresion del lado izquierdo de la comparacion");}
break;
case 45:
//#line 178 "gramatica.y"
{
    
       
}
break;
case 46:
//#line 183 "gramatica.y"
{ 
                }
break;
case 47:
//#line 185 "gramatica.y"
{
               
                }
break;
case 48:
//#line 188 "gramatica.y"
{
                }
break;
case 49:
//#line 190 "gramatica.y"
{
                }
break;
case 50:
//#line 193 "gramatica.y"
{
                }
break;
case 51:
//#line 195 "gramatica.y"
{
                }
break;
case 52:
//#line 198 "gramatica.y"
{ 
    /* Verificar si el T_CTE es '1' o '2'*/
    /* */
    if (!(val_peek(1).equals("1") || val_peek(1).equals("2"))) {
        yyerror("Error: Solo se permite 1 o 2 dentro de las llaves.");
    } else {
        yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
    }
}
break;
case 54:
//#line 209 "gramatica.y"
{System.err.println("Error: hay goto sin etiqueta"); }
break;
case 65:
//#line 223 "gramatica.y"
{System.err.println("Error: Dos o mas operadores juntos");}
break;
case 66:
//#line 224 "gramatica.y"
{System.err.println("Error: Dos o mas operadores juntos");}
break;
case 67:
//#line 225 "gramatica.y"
{System.err.println("Error: Dos o mas operadores juntos");}
break;
case 74:
//#line 229 "gramatica.y"
{
        }
break;
case 75:
//#line 231 "gramatica.y"
{
        }
break;
case 76:
//#line 233 "gramatica.y"
{
        }
break;
case 77:
//#line 235 "gramatica.y"
{
        }
break;
case 78:
//#line 237 "gramatica.y"
{
        }
break;
case 79:
//#line 239 "gramatica.y"
{
        }
break;
case 80:
//#line 241 "gramatica.y"
{
        }
break;
case 81:
//#line 243 "gramatica.y"
{
        }
break;
case 82:
//#line 245 "gramatica.y"
{ /* Se añade la regla para operadores unarios*/
        }
break;
case 83:
//#line 247 "gramatica.y"
{System.err.println("Error: Dos o mas operadores juntos");}
break;
case 84:
//#line 248 "gramatica.y"
{System.err.println("Error: Dos o mas operadores juntos");}
break;
case 85:
//#line 249 "gramatica.y"
{System.err.println("Error: Dos o mas operadores juntos");}
break;
case 86:
//#line 253 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
           yyval.dval = -val_peek(0).dval;
       }
break;
//#line 921 "Parser.java"
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
      if (yychar == 0)  {        //Good exit (if lex returns 0 ;-)
    	  System.out.println(st);
    	  break;           //quit the loop--all DONE
         }      
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
