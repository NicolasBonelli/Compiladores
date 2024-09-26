#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#line 2 "gramatica.y"
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



#line 45 "y.tab.c"
#define IF 257
#define THEN 258
#define ELSE 259
#define BEGIN 260
#define END 261
#define END_IF 262
#define OUTF 263
#define TYPEDEF 264
#define FUN 265
#define RET 266
#define REPEAT 267
#define WHILE 268
#define PAIR 269
#define GOTO 270
#define LONGINT 271
#define DOUBLE 272
#define MENOR_IGUAL 273
#define MAYOR_IGUAL 274
#define DISTINTO 275
#define T_ASIGNACION 276
#define T_CADENA 277
#define T_ID 278
#define T_CTE 279
#define T_ETIQUETA 280
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    0,    1,    2,    2,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    4,    9,    9,    9,    9,
    9,   15,   15,   14,   14,   13,   13,   13,   13,    6,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    7,
    7,    7,    7,    8,    8,    8,   11,   11,   11,   11,
   11,   17,   17,   17,   17,   17,   16,   16,   16,   18,
   18,   18,   18,   18,   18,    5,   19,   19,   19,   19,
   20,   20,   21,   10,   10,   22,   23,   24,   24,   24,
   24,   24,   24,   24,   24,   24,   24,   24,   26,   26,
   26,   26,   26,   26,   12,   12,   12,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   25,
};
short yylen[] = {                                         2,
    2,    1,    3,    2,    1,    1,    1,    1,    1,    1,
    1,    1,    1,    1,    5,    3,    8,    7,    7,    7,
    7,    1,    1,    3,    1,    1,    1,    1,    1,    8,
   10,    7,    9,    7,    9,    7,    9,    7,    8,    7,
    6,    5,    6,    5,    5,    4,    6,    7,    7,    5,
    7,    5,    6,    6,    7,    1,    3,    2,    2,    1,
    1,    1,    1,    1,    1,    4,    1,    3,    3,    1,
    1,    3,    4,    3,    2,    4,    1,    3,    3,    3,
    3,    1,    1,    1,    1,    4,    4,    4,    1,    1,
    1,    2,    2,    2,    3,    3,    3,    3,    1,    1,
    1,    1,    1,    4,    4,    4,    2,
};
short yydefred[] = {                                      0,
    0,    0,    0,    1,   29,    0,    0,    0,    0,    0,
    0,   27,   26,    0,   14,    0,    5,    6,    7,    8,
    9,   10,   11,   12,   13,    0,    0,   70,    0,    0,
    0,    0,    0,    0,   22,   23,    0,    0,   75,    0,
    3,    4,    0,    0,    0,    0,    0,   60,   61,   62,
    0,   99,    0,    0,   64,   65,   63,    0,    0,    0,
  101,  102,  103,    0,    0,    0,    0,    0,    0,    0,
   74,    0,    0,    0,    0,   16,    0,    0,    0,    0,
   69,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   28,    0,    0,    0,
    0,   73,    0,    0,    0,   24,   66,    0,    0,   82,
   84,    0,    0,   85,    0,   89,   90,   91,    0,    0,
    0,   97,    0,   98,    0,    0,    0,    0,   44,   45,
    0,    0,    0,   56,    0,    0,   15,   42,    0,    0,
    0,    0,    0,    0,   76,    0,    0,    0,    0,    0,
    0,   92,   94,   93,    0,  105,  106,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   47,   43,    0,
    0,    0,    0,    0,    0,    0,    0,   80,    0,   81,
    0,    0,   34,    0,   38,    0,    0,    0,   36,   48,
   49,   51,    0,    0,   40,    0,   19,   21,   20,    0,
   87,   88,    0,   39,    0,   30,    0,    0,    0,    0,
   17,   35,    0,   37,   52,    0,    0,    0,   31,   54,
   53,    0,   55,
};
short yydgoto[] = {                                       2,
   35,   16,   36,   18,   19,   20,   21,   22,   23,   24,
   25,   58,   26,   45,   37,   59,  136,   60,   27,   79,
   61,   62,  112,  113,   63,  120,
};
short yysindex[] = {                                   -268,
 -246,    0,  332,    0,    0,   -3,    3, -184,   40,  258,
  -58,    0,    0,  -90,    0,  295,    0,    0,    0,    0,
    0,    0,    0,    0,    0, -220,   19,    0,  -41,  -27,
   52, -237,  -43,  102,    0,    0, -104,   93,    0, -108,
    0,    0,  -35,  133,  -28,  -43,  -91,    0,    0,    0,
  -15,    0,  -43,  -66,    0,    0,    0,   64,  152,  -43,
    0,    0,    0,  153,  125, -116, -135,  136,   29,  160,
    0,   80,  169, -135, -135,    0,  -65,  172,   75,  -90,
    0,  -23,   54,  314,  -30,  -43,  -30,  -30,  -43,  277,
  172,  162,  164,  166,  168,  185,    0, -117,  195,  190,
  -34,    0, -135,  -25,  -19,    0,    0,  -43,  -90,    0,
    0,  227,  182,    0, -149,    0,    0,    0,   54,  -13,
   54,    0,  -13,    0,  -13,  172,  239, -113,    0,    0,
   -5,   -4,   -2,    0,  -37,  216,    0,    0,  223,  246,
  -32,  247,  251,  172,    0,   -7,  -23,   -7,   -7,  314,
  234,    0,    0,    0,   54,    0,    0,   35,  240,    8,
  314,  241,  250,  255,  256,  254,   31,    0,    0,  261,
  264, -246, -246, -246,  118,   -1,  118,    0,   -1,    0,
   -1,   59,    0,  265,    0,  314,  271,   61,    0,    0,
    0,    0,  -22,  287,    0, -246,    0,    0,    0,  118,
    0,    0,  274,    0,   82,    0,  288,  221,   69,  -21,
    0,    0,  292,    0,    0,  225,  228,   73,    0,    0,
    0,  229,    0,
};
short yyrindex[] = {                                      0,
  356,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   18,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   79,    0,    0,    0,    0,    0,    0,
    6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   88,    0,   25,
    0,    0,   11,    0,    0,    0,    0,    0,  316,    0,
  318,    0,  -60,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  143,    0,
    0,    0,  320,    0,    0,    0,    0,    0,   16,    0,
   38,    0,    0,    0,    0,  321,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  123,    0,    0,    0,    0,
    0,    0,    0,  100,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   43,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  154,
    0,    0,    0,    0,  219,    0,  291,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  174,    0,    0,    0,
    0,    0,    0,    0,    0,  194,    0,    0,    0,  300,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  214,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
short yygindex[] = {                                      0,
    2,    0,  119,    0,    0,    0,    0,    0,    0,    0,
    0,  289,   66,    0,   30,   26,    0,  305,    0,    0,
  179,    0,    0,  -31,  -18,    5,
};
#define YYTABLESIZE 612
short yytable[] = {                                      54,
   39,   53,    4,   53,   74,  135,  139,  167,  172,    1,
   53,  117,  116,    3,   53,   77,  118,   53,   55,   57,
   56,   53,  209,  218,   82,   55,   57,   56,  153,  152,
   76,   53,   40,  154,  117,  116,   29,   53,   67,  118,
  153,  152,   30,   53,   43,  154,  100,  100,  100,  100,
  100,  107,  100,  107,  107,  107,   95,   44,   95,   95,
   95,   67,   47,  114,  100,  100,  100,  100,   68,  107,
  107,  107,  107,   53,   95,   95,   95,   95,   96,   33,
   96,   96,   96,  104,   31,  104,  104,  104,   55,   57,
   56,  123,  125,   32,  100,   87,   96,   96,   96,   96,
   88,  104,  104,  104,  104,   87,   85,   40,   86,  150,
   88,   66,  151,  115,  175,  177,  178,  180,  108,  128,
    5,   17,   25,   55,   57,   56,  140,  114,  114,  114,
  114,   71,   98,  107,   42,   12,   13,   25,  134,  104,
  105,   69,   97,   72,  200,  161,   71,  201,  162,  202,
  176,   71,  179,  181,   94,   95,  160,  114,   72,  148,
  114,   96,  114,   70,  149,   93,   87,   85,  141,   86,
   72,   88,   75,  197,  198,  199,   99,   87,   85,  182,
   86,   28,   88,   83,   83,   83,   80,   83,   28,   83,
  188,   84,   90,   92,   28,   46,   46,  211,   46,  101,
   46,   46,   46,   46,  102,   46,   46,   46,  103,   46,
   46,   46,  106,   87,   85,  205,   86,   46,   88,   46,
  129,   38,  130,  148,  146,   81,  147,  131,  149,  132,
  138,   48,   49,   50,   51,   52,   51,   52,   48,   49,
   50,  166,   73,   51,   52,  171,  133,   51,   52,   64,
   51,   52,  142,  137,  109,  110,  208,  217,  143,   78,
  111,   78,   28,   78,   51,   52,  186,  145,   28,  187,
  109,  110,  163,  164,  168,  165,  109,  110,  100,  100,
  100,  169,   28,  107,  107,  107,  170,  173,   95,   95,
   95,  174,  183,   67,   46,   28,  184,  193,  185,  189,
   68,   48,   49,   50,  196,   28,   51,   52,  190,  194,
   96,   96,   96,  191,  192,  104,  104,  104,   65,  195,
  203,   68,  207,  204,  111,  111,  111,  111,   28,  206,
  210,   79,  212,   79,   78,   79,   48,   49,   50,   28,
   86,   83,   86,  213,   86,  215,  214,  216,   91,  220,
  219,  222,  221,  223,  111,    2,   58,  111,   59,  111,
   77,   57,   89,    0,   28,    0,    0,    0,    0,    0,
    0,    0,    0,  119,  121,  122,  124,  126,   50,   50,
    0,   50,    0,   50,   50,   50,   50,    0,   50,   50,
   50,    0,   50,   50,   50,    0,  144,    0,    0,    0,
   50,    0,   50,    0,    0,    0,    0,    0,  155,   41,
   41,  156,   41,  157,   41,   41,   41,   41,    0,   41,
   41,   41,    0,   41,   41,   41,    0,    0,    0,   32,
   32,   41,   32,   41,   32,   32,   32,   32,    0,   32,
   32,   32,    0,   32,   32,   32,    0,    0,    0,   18,
   18,   32,   18,   32,   18,   18,   18,   18,    0,   18,
   18,   18,    0,   18,   18,   18,    0,    0,    0,   33,
   33,   18,   33,   18,   33,   33,   33,   33,    0,   33,
   33,   33,    0,   33,   33,   33,    0,    0,    0,    0,
    0,   33,    0,   33,    5,    6,    0,  158,    3,    0,
  159,    7,    8,    0,    9,   10,    0,    0,   11,   12,
   13,    0,    0,    5,    6,    0,   14,    3,   15,    0,
    7,    8,    0,    9,   10,   34,    0,   11,   12,   13,
    0,    0,    5,    6,  127,   14,    3,   15,    0,    7,
    8,    0,    9,   10,    0,    0,   11,   12,   13,    0,
    5,    6,    0,    0,   14,   41,   15,    7,    8,    0,
    9,   10,    0,    0,   11,   12,   13,    0,    0,    5,
    6,    0,   14,    3,   15,    0,    7,    8,    0,    9,
   10,    0,    0,   11,   12,   13,    0,    5,    6,    0,
    0,   14,    0,   15,    7,    8,    0,    9,   10,    0,
    0,   11,   12,   13,    0,    0,    0,    0,    0,   14,
    0,   15,
};
short yycheck[] = {                                      41,
   59,   45,    1,   45,   40,  123,   41,   45,   41,  278,
   45,   42,   43,  260,   45,   44,   47,   45,   60,   61,
   62,   45,   45,   45,   40,   60,   61,   62,   42,   43,
   59,   45,  123,   47,   42,   43,   40,   45,  276,   47,
   42,   43,   40,   45,  265,   47,   41,   42,   43,   44,
   45,   41,   47,   43,   44,   45,   41,  278,   43,   44,
   45,   44,   44,   82,   59,   60,   61,   62,   44,   59,
   60,   61,   62,   45,   59,   60,   61,   62,   41,   40,
   43,   44,   45,   41,  269,   43,   44,   45,   60,   61,
   62,   87,   88,  278,   69,   42,   59,   60,   61,   62,
   47,   59,   60,   61,   62,   42,   43,  123,   45,  259,
   47,   60,  262,   84,  146,  147,  148,  149,   44,   90,
  256,    3,   44,   60,   61,   62,  101,  146,  147,  148,
  149,   44,   67,   59,   16,  271,  272,   59,  256,   74,
   75,   40,  278,   44,  176,  259,   59,  179,  262,  181,
  146,   59,  148,  149,  271,  272,  127,  176,   59,   42,
  179,  278,  181,  268,   47,   41,   42,   43,  103,   45,
  279,   47,   40,  172,  173,  174,   41,   42,   43,  150,
   45,    3,   47,   41,   42,   43,  278,   45,   10,   47,
  161,  258,   41,   41,   16,  256,  257,  196,  259,   40,
  261,  262,  263,  264,  125,  266,  267,  268,   40,  270,
  271,  272,  278,   42,   43,  186,   45,  278,   47,  280,
   59,  280,   59,   42,   43,   47,   45,   62,   47,   62,
   41,  273,  274,  275,  278,  279,  278,  279,  273,  274,
  275,  279,  278,  278,  279,  278,   62,  278,  279,  277,
  278,  279,  278,   59,  278,  279,  279,  279,  278,   41,
   82,   43,   84,   45,  278,  279,  259,   41,   90,  262,
  278,  279,  278,  278,   59,  278,  278,  279,  273,  274,
  275,   59,  265,  273,  274,  275,   41,   41,  273,  274,
  275,   41,   59,  276,  276,  278,  262,   44,   59,   59,
  276,  273,  274,  275,   41,  127,  278,  279,   59,  279,
  273,  274,  275,   59,   59,  273,  274,  275,   30,   59,
  262,   33,  262,   59,  146,  147,  148,  149,  150,   59,
   44,   41,   59,   43,   46,   45,  273,  274,  275,  161,
   41,   53,   43,  262,   45,  125,   59,  279,   60,  125,
   59,  279,  125,  125,  176,    0,   41,  179,   41,  181,
   41,   41,   58,   -1,  186,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   85,   86,   87,   88,   89,  256,  257,
   -1,  259,   -1,  261,  262,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   -1,  108,   -1,   -1,   -1,
  278,   -1,  280,   -1,   -1,   -1,   -1,   -1,  120,  256,
  257,  123,  259,  125,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,  256,
  257,  278,  259,  280,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,  256,
  257,  278,  259,  280,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,  256,
  257,  278,  259,  280,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,
   -1,  278,   -1,  280,  256,  257,   -1,  259,  260,   -1,
  262,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,  256,  257,   -1,  278,  260,  280,   -1,
  263,  264,   -1,  266,  267,  268,   -1,  270,  271,  272,
   -1,   -1,  256,  257,  258,  278,  260,  280,   -1,  263,
  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,
  256,  257,   -1,   -1,  278,  261,  280,  263,  264,   -1,
  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,  256,
  257,   -1,  278,  260,  280,   -1,  263,  264,   -1,  266,
  267,   -1,   -1,  270,  271,  272,   -1,  256,  257,   -1,
   -1,  278,   -1,  280,  263,  264,   -1,  266,  267,   -1,
   -1,  270,  271,  272,   -1,   -1,   -1,   -1,   -1,  278,
   -1,  280,
};
#define YYFINAL 2
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 280
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"'('","')'","'*'","'+'","','","'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,
"';'","'<'","'='","'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,"'{'",0,"'}'",0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,"IF","THEN","ELSE","BEGIN","END","END_IF","OUTF","TYPEDEF",
"FUN","RET","REPEAT","WHILE","PAIR","GOTO","LONGINT","DOUBLE","MENOR_IGUAL",
"MAYOR_IGUAL","DISTINTO","T_ASIGNACION","T_CADENA","T_ID","T_CTE","T_ETIQUETA",
};
char *yyrule[] = {
"$accept : programa",
"programa : T_ID bloque_sentencias",
"programa : T_ID",
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
"declaracion_funcion : tipo FUN T_ID '(' tipo T_ID ')'",
"declaracion_funcion : tipo FUN T_ID '(' tipo ')' bloque_sentencias",
"declaracion_funcion : tipo T_ID '(' tipo T_ID ')' bloque_sentencias",
"declaracion_funcion : tipo FUN '(' tipo T_ID ')' bloque_sentencias",
"repeat_sentencia : bloque_sentencias",
"repeat_sentencia : sentencia",
"lista_var : lista_var ',' T_ID",
"lista_var : T_ID",
"tipo : DOUBLE",
"tipo : LONGINT",
"tipo : T_ID",
"tipo : error",
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
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')' ';'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' condicion ')'",
"repeat_while_statement : REPEAT WHILE '(' condicion ')'",
"repeat_while_statement : REPEAT repeat_sentencia WHILE '(' ')' ';'",
"salida : OUTF '(' T_CADENA ')' ';'",
"salida : OUTF '(' expresion ')' ';'",
"salida : OUTF '(' expresion ')'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' T_ID '>' T_ID ';'",
"subrango : '{' T_CTE ',' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' T_CTE '}'",
"subrango : '{' T_CTE ',' '-' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' '-' T_CTE '}'",
"subrango : error",
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
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#line 331 "gramatica.y"

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
#line 551 "y.tab.c"
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
case 1:
#line 51 "gramatica.y"
{
    System.out.println("Programa compilado correctamente");
}
break;
case 2:
#line 54 "gramatica.y"
{ 
    System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
}
break;
case 3:
#line 60 "gramatica.y"
{System.out.println("Llegue a BEGIN sentencia END");}
break;
case 5:
#line 63 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 15:
#line 75 "gramatica.y"
{System.out.println("Llegue a sentencia");}
break;
case 16:
#line 78 "gramatica.y"
{ 
    System.out.println("Llegue a declaracion");
    List<String> variables = (List<String>)yyvsp[-1]; /* Asume que lista_var devuelve una lista de variables*/
    
    for (String variable : variables) {
        /* Verificar si la variable ya existe en la tabla de símbolos*/
        if (!st.hasKey(variable)) {
            System.out.println("Error en linea: " + Lexer.nmrLinea + " ERROR, la tabla de símbolos no contenía la variable: " + variable);
        } else {
            /* Actualiza el tipo de la variable si ya está en la tabla de símbolos*/
            boolean actualizado = st.updateType(variable, yyvsp[-2]);
            if (actualizado) {
                System.out.println("Tipo de la variable '" + variable + "' actualizado a: " + yyvsp[-2]);
            } else {
                System.out.println("Error en linea: " + Lexer.nmrLinea + " Error al actualizar el tipo de la variable: " + variable);
            }
        }
    }
}
break;
case 17:
#line 99 "gramatica.y"
{
        System.out.println("Declaración de función correcta");
      }
break;
case 18:
#line 102 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración de función.");
      }
break;
case 19:
#line 105 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el identificador del argumento de la función.");
      }
break;
case 20:
#line 108 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN");
        
      }
break;
case 21:
#line 112 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el identificador de la funcion");
      }
break;
case 22:
#line 118 "gramatica.y"
{}
break;
case 23:
#line 119 "gramatica.y"
{}
break;
case 24:
#line 122 "gramatica.y"
{ 
   
}
break;
case 25:
#line 125 "gramatica.y"
{ 
    
}
break;
case 26:
#line 129 "gramatica.y"
{ yyval = "double"; }
break;
case 27:
#line 130 "gramatica.y"
{ yyval = "longint"; }
break;
case 28:
#line 132 "gramatica.y"
{
        System.out.println("Llegue a tipo");
        /* Verificar si el tipo está en la tabla de tipos definidos*/
        if (tablaTipos.containsKey(yyvsp[0])) {
            yyval = yyvsp[0]; /* Si el tipo está definido, se usa el nombre del tipo*/
        } else {
            yyerror("Error en linea: " + Lexer.nmrLinea + " Tipo no definido: " + yyvsp[0]);
        }
    }
break;
case 29:
#line 141 "gramatica.y"
{
    System.err.println("Error en línea: " + Lexer.nmrLinea + " - Tipo no válido o no declarado.");
    }
break;
case 32:
#line 149 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma al final de la sentencia IF.");
            }
break;
case 33:
#line 152 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma al final de la sentencia IF.");
            }
break;
case 34:
#line 156 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 35:
#line 159 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 36:
#line 163 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 37:
#line 166 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 38:
#line 170 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 39:
#line 173 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 41:
#line 180 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma al final de la sentencia WHILE.");
    }
break;
case 42:
#line 183 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 43:
#line 186 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 45:
#line 195 "gramatica.y"
{     
        System.out.println("Llegue a salida");   
        }
break;
case 46:
#line 198 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma en la salida.");
        }
break;
case 47:
#line 202 "gramatica.y"
{
            System.out.println("Llegue a sentencia_declarativa_tipos");
        /* Guardar el nuevo tipo en la tabla de símbolos*/
        String nombreTipo = yyvsp[-4]; /* T_ID*/
        String tipoBase = yyvsp[-2]; /* tipo base (INTEGER o SINGLE)*/
        double limiteInferior = Double.parseDouble(yyvsp[-1]); /* Limite inferior del subrango*/
        double limiteSuperior = Double.parseDouble(yyvsp[0]); /* Limite superior del subrango*/

        /* Almacenar en la tabla de símbolos*/
        tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        }
break;
case 50:
#line 215 "gramatica.y"
{
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el punto y coma al final de la declaración de tipo.");
        }
break;
case 51:
#line 218 "gramatica.y"
{
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 52:
#line 223 "gramatica.y"
{
        System.out.println("Llegue a subrango");

        /*$$ = new Subrango(Double.parseDouble($2), Double.parseDouble($4));*/
    }
break;
case 56:
#line 231 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 58:
#line 236 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado derecho de la comparacion");}
break;
case 59:
#line 237 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado izquierdo de la comparacion");}
break;
case 66:
#line 248 "gramatica.y"
{
    
       
}
break;
case 67:
#line 253 "gramatica.y"
{ 
                }
break;
case 68:
#line 255 "gramatica.y"
{
               
                }
break;
case 69:
#line 258 "gramatica.y"
{
                }
break;
case 70:
#line 260 "gramatica.y"
{
                }
break;
case 71:
#line 263 "gramatica.y"
{
                }
break;
case 72:
#line 265 "gramatica.y"
{
                }
break;
case 73:
#line 268 "gramatica.y"
{ 
    /* Verificar si el T_CTE es '1' o '2'*/
    /* */
    if (!(yyvsp[-1].equals("1") || yyvsp[-1].equals("2"))) {
        yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Solo se permite 1 o 2 dentro de las llaves.");
    } else {
        yyval = yyvsp[-3] + "{" + yyvsp[-1] + "}";
    }
}
break;
case 75:
#line 279 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 76:
#line 282 "gramatica.y"
{
      }
break;
case 86:
#line 295 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 87:
#line 296 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 88:
#line 297 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + "Error: Dos o mas operadores juntos");}
break;
case 95:
#line 301 "gramatica.y"
{
        }
break;
case 96:
#line 303 "gramatica.y"
{
        }
break;
case 97:
#line 305 "gramatica.y"
{
        }
break;
case 98:
#line 307 "gramatica.y"
{
        }
break;
case 99:
#line 309 "gramatica.y"
{
        }
break;
case 100:
#line 311 "gramatica.y"
{
        }
break;
case 101:
#line 313 "gramatica.y"
{
        }
break;
case 102:
#line 315 "gramatica.y"
{
        }
break;
case 103:
#line 317 "gramatica.y"
{ /* Se añade la regla para operadores unarios*/
        }
break;
case 104:
#line 319 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 105:
#line 320 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 106:
#line 321 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 107:
#line 325 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
           yyval = -yyvsp[0];
       }
break;
#line 1074 "y.tab.c"
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
