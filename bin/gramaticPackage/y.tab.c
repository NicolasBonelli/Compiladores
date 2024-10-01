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



#line 51 "y.tab.c"
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
    0,    0,    0,    1,    1,    1,    2,    2,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    3,   13,    3,
    4,    4,   15,   15,   15,    9,    9,    9,    9,    9,
   16,   17,   17,   17,   18,   18,   14,   14,   14,    6,
    6,    6,    6,    6,    6,    6,    6,    6,    6,    6,
    6,    7,    7,    7,    7,    7,    8,    8,    8,    8,
    8,    8,   11,   11,   11,   11,   11,   11,   11,   20,
   20,   20,   20,   20,   19,   19,   21,   21,   21,   21,
   21,   21,    5,    5,   22,   22,   22,   22,   23,   23,
   24,   24,   24,   24,   10,   10,   10,   25,   25,   26,
   27,   27,   27,   27,   27,   27,   27,   27,   27,   27,
   27,   29,   29,   29,   29,   29,   29,   12,   12,   12,
   12,   12,   12,   12,   12,   12,   12,   12,   12,   28,
};
short yylen[] = {                                         2,
    2,    1,    1,    3,    2,    1,    2,    1,    1,    1,
    1,    1,    1,    1,    1,    1,    1,    5,    0,    5,
    3,    2,    3,    1,    1,    7,    7,    7,    7,    7,
    2,    3,    3,    0,    1,    1,    1,    1,    1,    8,
   10,    7,    9,    7,    9,    7,    9,    7,    8,    6,
    8,    7,    6,    5,    6,    5,    5,    5,    4,    4,
    4,    5,    6,    7,    7,    5,    7,    6,    6,    5,
    6,    6,    7,    1,    3,    2,    1,    1,    1,    1,
    1,    1,    4,    3,    1,    3,    3,    1,    1,    3,
    4,    3,    3,    3,    3,    2,    2,    4,    1,    1,
    3,    3,    3,    3,    1,    1,    1,    1,    4,    4,
    4,    1,    1,    1,    2,    2,    2,    3,    3,    3,
    3,    1,    1,    1,    1,    1,    4,    4,    4,    2,
};
short yydefred[] = {                                      0,
    6,    0,    0,    0,    3,    0,    5,    0,    0,    0,
    0,    0,   38,   37,    0,   17,    0,    8,    9,   10,
   11,   12,   13,   14,   15,   16,    0,    0,   88,    1,
   99,    0,  122,    0,    0,    0,    0,  124,  125,  126,
    0,    0,    0,    0,    0,   35,   36,    0,    0,   96,
    0,    0,    4,    7,   25,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   77,   78,   79,    0,    0,    0,
    0,   81,   82,   80,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   95,   94,
    0,   92,    0,    0,    0,   21,    0,    0,    0,    0,
   87,    0,  105,  107,    0,    0,  108,    0,    0,    0,
  112,  113,  114,    0,    0,    0,  120,    0,  121,    0,
    0,    0,    0,   61,    0,    0,    0,    0,    0,   39,
    0,    0,    0,    0,    0,   91,    0,    0,    0,   23,
   83,    0,   98,    0,    0,    0,    0,    0,    0,    0,
    0,  115,  117,  116,    0,  128,  129,    0,    0,   57,
   62,   58,    0,    0,    0,   74,    0,    0,   18,   20,
   54,    0,    0,   56,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  103,    0,  104,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   50,    0,    0,    0,    0,
    0,   63,   55,    0,   31,    0,    0,    0,    0,    0,
    0,    0,    0,  110,  111,    0,   44,    0,   48,    0,
    0,    0,   46,    0,   64,   65,   67,    0,    0,   52,
   28,   26,    0,   32,   27,   33,   30,   29,    0,   49,
    0,   40,    0,   51,    0,    0,    0,   45,    0,   47,
   70,    0,    0,    0,   41,   72,   71,    0,   73,
};
short yydgoto[] = {                                       4,
   46,   17,   47,   19,   20,   21,   22,   23,   24,   25,
   26,   36,  170,   27,   58,  176,  177,   48,   37,  168,
   76,   28,   99,   38,   39,  105,  106,   40,  115,
};
short yysindex[] = {                                   -180,
    0,  604, -201,    0,    0,  125,    0,    1, -256,   27,
  633,  -52,    0,    0, -115,    0,  776,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -217,   -2,    0,    0,
    0,  -35,    0,  105,  468,  675, -184,    0,    0,    0,
  167,   22, -193,  105,   47,    0,    0, -177,   53,    0,
  -12, -116,    0,    0,    0,  -29,   74,    5,  105, -144,
  216,  -32, -110,  121,    0,    0,    0,  683,  105,  683,
  683,    0,    0,    0,  136,  105,  758,  130,  -35,  107,
  134,   90,    0, -248, -155,  408,  105,  157,    0,    0,
   51,    0,  144, -155, -155,    0,  -93,  136,    7, -115,
    0, -115,    0,    0,  163,  729,    0,  105,  758,  739,
    0,    0,    0,  -32,  696,  -32,    0,  696,    0,  696,
  136, -178,  146,    0,  150,  160,  201,  224,  241,    0,
 -109,  165,  237,  476,  252,    0, -155,   50,   58,    0,
    0,  105,    0,  706,  216,  706,  706,  -32,  -69,  586,
  -68,    0,    0,    0,  -32,    0,    0,  758,  285,    0,
    0,    0,   71,   88,   99,    0,   15,  309,    0,    0,
    0,  334,  356,    0,  -25,  154,  162,  360,  376,  136,
  -30,  722,  -30,    0,  722,    0,  722,  758,  362,  178,
  366,  -49,  758,  368,  179,    0,  370,  373,  384,  404,
  181,    0,    0,  412,    0, -201, -201, -155, -201, -155,
 -201, -201,  -30,    0,    0,  215,    0,  420,    0,  758,
  429,  230,    0,  434,    0,    0,    0,   20,  452,    0,
    0,    0,  220,    0,    0,    0,    0,    0,  441,    0,
  240,    0,  445,    0,  393,  255,   40,    0,  460,    0,
    0,  395,  398,  259,    0,    0,    0,  417,    0,
};
short yyrindex[] = {                                      0,
    0,    0,  548,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  510,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -16,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  195,    0,
    0,    0,    0,    0,    0,    0,  108,  219,    0,    0,
    0,    9,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   95,    0,    0,    0,  526,    0,
    0,    0,   -7,    0,    0,    0,    0,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,    0,  128,  244,   48,
    0,  427,    0,    0,    0,  509,    0,    0,    0,    0,
    0,    0,    0,   34,    0,  484,    0,    0,    0,    0,
  115,    0,  269,    0,    0,  294,    0,    0,    0,    0,
    0,  319,    0,    0,    0,    0,  170,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   59,    0,    0,
    0,    0,    0,    0,   84,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  341,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  148,
   56,    0,   81,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  361,  381,    0,    0,
    0,    0,    0,  401,    0,    0,    0,    0,    0,    0,
    0,    0,  110,    0,    0,    0,    0,    0,    0,    0,
  423,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  443,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
short yygindex[] = {                                      0,
  614,    0,   45,    0,    0,    0,    0,    0,    0,    0,
    0,  765,    0,  -22,    0, -103,    0,  -20,   23,    0,
    0,    0,    0,  778,    0,    0,   14,   -5,  -38,
};
#define YYTABLESIZE 1056
short yytable[] = {                                      93,
   93,   93,   93,   93,   61,   93,   50,   52,   92,   70,
   94,  146,   42,  167,   71,  206,  147,   93,   93,   93,
   93,   43,  127,  128,  123,  123,  123,  123,  123,  129,
  123,  118,  120,  124,  124,  124,   88,  124,   55,  124,
   41,   60,  123,  123,  123,  123,   18,   56,   97,  130,
  142,  130,  130,  130,    1,  107,  122,   64,    2,  201,
   57,   54,  131,   96,  246,  141,   44,  130,  130,  130,
  130,  138,  139,   77,  118,    1,  118,  118,  118,    2,
  158,   84,   85,  159,  254,   81,   87,   52,  149,  151,
   88,   86,  118,  118,  118,  118,  101,    3,  101,  119,
  101,  119,  119,  119,  234,  182,  236,  185,  187,  133,
  135,   89,   90,   95,  175,   13,   14,  119,  119,  119,
  119,  102,  130,  102,  127,  102,  127,  127,  127,  192,
  126,   70,   68,  100,  108,   76,   71,  195,  107,  107,
  107,  107,  127,  127,  127,  127,  166,  109,   24,   34,
  109,   24,  109,   76,  109,   75,  173,  181,  183,  184,
  186,  110,   91,   51,   35,  124,   24,  216,   89,   34,
  123,   89,  222,   75,  125,  136,  107,   70,   68,  107,
  108,  107,   71,  137,  140,  233,   89,  233,   90,  188,
  193,   90,  189,  194,  207,  213,  134,  208,  214,  241,
  215,   34,  209,  143,  160,  210,   90,   80,  161,  220,
   34,   34,  221,   34,   93,   93,   93,   93,  162,   93,
   93,   93,   93,  169,   93,   93,   93,   49,   93,   93,
   93,   93,   93,   93,   93,   97,   93,   93,   93,  123,
  123,  123,  123,   51,  123,  123,  123,  123,   93,  123,
  123,  123,  205,  123,  123,  123,  123,  123,  123,   22,
   34,  123,  163,  123,  130,  130,  130,  130,   88,  130,
  130,  130,  130,   59,  130,  130,  130,  171,  130,  130,
  130,  130,  130,  130,   84,  164,  130,  130,  130,  118,
  118,  118,  118,  200,  118,  118,  118,  118,  245,  118,
  118,  118,  165,  118,  118,  118,  118,  118,  118,   60,
  174,  118,  118,  118,  119,  119,  119,  119,  253,  119,
  119,  119,  119,   86,  119,  119,  119,  178,  119,  119,
  119,  119,  119,  119,   59,  179,  119,  119,  119,  127,
  127,  127,  127,  196,  127,  127,  127,  127,  197,  127,
  127,  127,   76,  127,  127,  127,  127,  127,  127,   19,
   31,  127,  127,  127,   24,  198,   24,  202,   24,   24,
   24,   24,   75,   24,   24,   24,  199,   24,   24,   24,
   31,   66,   32,   33,   89,   24,   89,   24,   89,   89,
   89,   89,  203,   89,   89,   89,  204,   89,   89,   89,
  211,   68,   32,   33,   90,   89,   90,   89,   90,   90,
   90,   90,   31,   90,   90,   90,  212,   90,   90,   90,
  217,   69,   31,    6,  219,   90,  223,   90,  225,    8,
    9,  226,   10,   11,   32,   33,   12,   13,   14,  218,
  224,   53,  227,   78,   79,   33,   16,  228,  132,   70,
   68,   97,  108,   97,   71,   97,   97,   97,   97,  229,
   97,   97,   97,   42,   97,   97,   97,  106,  106,  106,
  230,  106,   97,  106,   97,   22,  239,   22,  240,   22,
   22,   22,   22,   43,   22,   22,   22,  242,   22,   22,
   22,  243,  244,  102,  103,  247,   22,  205,   22,  248,
   84,  249,   84,  250,   84,   84,   84,   84,   63,   84,
   84,   84,   34,   84,   84,   84,  172,  251,  255,  256,
   34,   84,  257,   84,  130,   60,  119,   60,  119,   60,
   60,   60,   60,  252,   60,   60,   60,  258,   60,   60,
   60,  259,  130,  119,  119,  119,   60,    2,   60,  100,
   59,    0,   59,   85,   59,   59,   59,   59,    0,   59,
   59,   59,    0,   59,   59,   59,  123,  123,  123,   85,
  123,   59,  123,   59,    0,   19,    0,   19,    0,   19,
   19,   19,   19,    0,   19,   19,   19,    0,   19,   19,
   19,    0,    0,    0,    0,    0,   19,   66,   19,   66,
    0,   66,   66,   66,   66,    0,   66,   66,   66,    0,
   66,   66,   66,    5,    0,    0,   30,   68,   66,   68,
   66,   68,   68,   68,   68,    0,   68,   68,   68,    0,
   68,   68,   68,    0,    0,    0,    0,   69,   68,   69,
   68,   69,   69,   69,   69,    0,   69,   69,   69,    0,
   69,   69,   69,    0,    0,    0,    0,   53,   69,   53,
   69,   53,   53,   53,   53,    0,   53,   53,   53,    0,
   53,   53,   53,    0,    0,    0,    0,    0,   53,   42,
   53,   42,    0,   42,   42,   42,   42,    0,   42,   42,
   42,    0,   42,   42,   42,    0,    0,    0,    0,   43,
   42,   43,   42,   43,   43,   43,   43,    0,   43,   43,
   43,    0,   43,   43,   43,    0,   70,   68,    0,   69,
   43,   71,   43,   31,  112,  111,    0,   34,    0,  113,
    0,   31,    0,    0,   72,   74,   73,  153,  152,  119,
   34,  130,  154,    0,    0,   32,   33,  112,  111,    0,
   34,    0,  113,   32,   33,    0,  119,  119,  119,    0,
    0,  119,  119,  153,  152,   39,   34,    0,  154,    0,
  146,  144,    0,  145,   39,  147,    0,    0,    0,   29,
    0,   39,    0,    0,    0,   85,    0,   39,   29,    0,
   39,    0,    0,    0,   29,    0,    0,    0,   62,    0,
   75,   85,    0,   39,    0,   82,    0,    0,   86,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   83,  231,
  232,    0,  235,   98,  237,  238,    0,    0,    0,    0,
    0,    0,  114,  116,  117,  119,    0,  101,  104,    0,
  121,    1,    6,    0,  190,    2,    0,  191,    8,    9,
    0,   10,   11,    0,   29,   12,   13,   14,    0,    0,
    6,    0,    0,   15,    7,   16,    8,    9,    0,   10,
   11,    0,  148,   12,   13,   14,    0,    0,    0,  155,
    0,   15,  156,   16,  157,    0,   29,   29,    1,    6,
    0,    0,    2,    0,    0,    8,    9,    0,   10,   11,
   45,    0,   12,   13,   14,    0,  180,    0,    0,    0,
   15,    0,   16,    0,    0,    0,    0,    0,    0,    0,
    0,  104,  104,  104,  104,    0,    0,   29,    0,    0,
   31,    0,    0,    0,    0,   29,    0,    0,   31,    0,
    0,    0,    0,    0,    0,    0,    0,   65,   66,   67,
    0,   31,   32,   33,    0,    0,    0,    0,    0,  104,
   32,   33,  104,    0,  104,   29,    0,    0,    0,    0,
   29,    0,    0,   32,   33,    0,    0,    0,    0,    0,
    0,    0,    0,  102,  103,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    1,    6,  150,   29,    2,  102,
  103,    8,    9,    0,   10,   11,    0,    0,   12,   13,
   14,    0,    0,    1,    6,    0,   15,    2,   16,    0,
    8,    9,    0,   10,   11,    0,    0,   12,   13,   14,
    0,    0,    6,    0,    0,   15,   53,   16,    8,    9,
    0,   10,   11,    0,    0,   12,   13,   14,    0,    0,
    0,    0,    0,   15,    0,   16,
};
short yycheck[] = {                                      41,
   42,   43,   44,   45,   40,   47,   59,  123,  125,   42,
   40,   42,  269,  123,   47,   41,   47,   59,   60,   61,
   62,  278,  271,  272,   41,   42,   43,   44,   45,  278,
   47,   70,   71,   41,   42,   43,   44,   45,  256,   47,
   40,   44,   59,   60,   61,   62,    2,  265,   44,   41,
   44,   43,   44,   45,  256,   61,   77,   35,  260,   45,
  278,   17,   85,   59,   45,   59,   40,   59,   60,   61,
   62,   94,   95,  258,   41,  256,   43,   44,   45,  260,
  259,   60,  276,  262,   45,   41,   40,  123,  109,  110,
  268,   44,   59,   60,   61,   62,   41,  278,   43,   41,
   45,   43,   44,   45,  208,  144,  210,  146,  147,   87,
   88,   59,  125,   40,  137,  271,  272,   59,   60,   61,
   62,   41,  278,   43,   41,   45,   43,   44,   45,  150,
   41,   42,   43,  278,   45,   41,   47,  158,  144,  145,
  146,  147,   59,   60,   61,   62,  256,  258,   41,   45,
   41,   44,   43,   59,   45,   41,  134,  144,  145,  146,
  147,   41,  279,  279,   40,   59,   59,  188,   41,   45,
   41,   44,  193,   59,   41,  125,  182,   42,   43,  185,
   45,  187,   47,   40,  278,  208,   59,  210,   41,  259,
  259,   44,  262,  262,   41,  182,   40,   44,  185,  220,
  187,   45,   41,   41,   59,   44,   59,   41,   59,  259,
   41,   45,  262,   44,  256,  257,  258,  259,   59,  261,
  262,  263,  264,   59,  266,  267,  268,  280,  270,  271,
  272,  273,  274,  275,  276,   41,  278,  279,  280,  256,
  257,  258,  259,  279,  261,  262,  263,  264,  278,  266,
  267,  268,  278,  270,  271,  272,  273,  274,  275,   41,
   45,  278,   62,  280,  256,  257,  258,  259,  276,  261,
  262,  263,  264,  276,  266,  267,  268,   41,  270,  271,
  272,  273,  274,  275,   41,   62,  278,  279,  280,  256,
  257,  258,  259,  279,  261,  262,  263,  264,  279,  266,
  267,  268,   62,  270,  271,  272,  273,  274,  275,   41,
   59,  278,  279,  280,  256,  257,  258,  259,  279,  261,
  262,  263,  264,  276,  266,  267,  268,  278,  270,  271,
  272,  273,  274,  275,   41,  278,  278,  279,  280,  256,
  257,  258,  259,   59,  261,  262,  263,  264,  278,  266,
  267,  268,  258,  270,  271,  272,  273,  274,  275,   41,
  256,  278,  279,  280,  257,  278,  259,   59,  261,  262,
  263,  264,  258,  266,  267,  268,  278,  270,  271,  272,
  256,   41,  278,  279,  257,  278,  259,  280,  261,  262,
  263,  264,   59,  266,  267,  268,   41,  270,  271,  272,
   41,   41,  278,  279,  257,  278,  259,  280,  261,  262,
  263,  264,  256,  266,  267,  268,   41,  270,  271,  272,
   59,   41,  256,  257,   59,  278,   59,  280,   59,  263,
  264,   59,  266,  267,  278,  279,  270,  271,  272,  262,
  262,   41,   59,  277,  278,  279,  280,   44,   41,   42,
   43,  257,   45,  259,   47,  261,  262,  263,  264,  279,
  266,  267,  268,   41,  270,  271,  272,   41,   42,   43,
   59,   45,  278,   47,  280,  257,  262,  259,   59,  261,
  262,  263,  264,   41,  266,  267,  268,   59,  270,  271,
  272,  262,   59,  278,  279,   44,  278,  278,  280,   59,
  257,  262,  259,   59,  261,  262,  263,  264,   41,  266,
  267,  268,   45,  270,  271,  272,   41,  125,   59,  125,
   45,  278,  125,  280,   41,  257,   43,  259,   45,  261,
  262,  263,  264,  279,  266,  267,  268,  279,  270,  271,
  272,  125,   59,   60,   61,   62,  278,    0,  280,   41,
  257,   -1,  259,   44,  261,  262,  263,  264,   -1,  266,
  267,  268,   -1,  270,  271,  272,   41,   42,   43,   44,
   45,  278,   47,  280,   -1,  257,   -1,  259,   -1,  261,
  262,  263,  264,   -1,  266,  267,  268,   -1,  270,  271,
  272,   -1,   -1,   -1,   -1,   -1,  278,  257,  280,  259,
   -1,  261,  262,  263,  264,   -1,  266,  267,  268,   -1,
  270,  271,  272,    0,   -1,   -1,    3,  257,  278,  259,
  280,  261,  262,  263,  264,   -1,  266,  267,  268,   -1,
  270,  271,  272,   -1,   -1,   -1,   -1,  257,  278,  259,
  280,  261,  262,  263,  264,   -1,  266,  267,  268,   -1,
  270,  271,  272,   -1,   -1,   -1,   -1,  257,  278,  259,
  280,  261,  262,  263,  264,   -1,  266,  267,  268,   -1,
  270,  271,  272,   -1,   -1,   -1,   -1,   -1,  278,  257,
  280,  259,   -1,  261,  262,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   -1,   -1,   -1,   -1,  257,
  278,  259,  280,  261,  262,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   -1,   42,   43,   -1,   45,
  278,   47,  280,  256,   42,   43,   -1,   45,   -1,   47,
   -1,  256,   -1,   -1,   60,   61,   62,   42,   43,  256,
   45,  258,   47,   -1,   -1,  278,  279,   42,   43,   -1,
   45,   -1,   47,  278,  279,   -1,  273,  274,  275,   -1,
   -1,  278,  279,   42,   43,  256,   45,   -1,   47,   -1,
   42,   43,   -1,   45,  265,   47,   -1,   -1,   -1,    2,
   -1,  256,   -1,   -1,   -1,  276,   -1,  278,   11,   -1,
  265,   -1,   -1,   -1,   17,   -1,   -1,   -1,   34,   -1,
   36,  276,   -1,  278,   -1,   41,   -1,   -1,   44,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   41,  206,
  207,   -1,  209,   59,  211,  212,   -1,   -1,   -1,   -1,
   -1,   -1,   68,   69,   70,   71,   -1,   60,   61,   -1,
   76,  256,  257,   -1,  259,  260,   -1,  262,  263,  264,
   -1,  266,  267,   -1,   77,  270,  271,  272,   -1,   -1,
  257,   -1,   -1,  278,  261,  280,  263,  264,   -1,  266,
  267,   -1,  108,  270,  271,  272,   -1,   -1,   -1,  115,
   -1,  278,  118,  280,  120,   -1,  109,  110,  256,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,  267,
  268,   -1,  270,  271,  272,   -1,  142,   -1,   -1,   -1,
  278,   -1,  280,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  144,  145,  146,  147,   -1,   -1,  150,   -1,   -1,
  256,   -1,   -1,   -1,   -1,  158,   -1,   -1,  256,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  273,  274,  275,
   -1,  256,  278,  279,   -1,   -1,   -1,   -1,   -1,  182,
  278,  279,  185,   -1,  187,  188,   -1,   -1,   -1,   -1,
  193,   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  278,  279,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,  256,  257,  258,  220,  260,  278,
  279,  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,
  272,   -1,   -1,  256,  257,   -1,  278,  260,  280,   -1,
  263,  264,   -1,  266,  267,   -1,   -1,  270,  271,  272,
   -1,   -1,  257,   -1,   -1,  278,  261,  280,  263,  264,
   -1,  266,  267,   -1,   -1,  270,  271,  272,   -1,   -1,
   -1,   -1,   -1,  278,   -1,  280,
};
#define YYFINAL 4
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
"salida : OUTF '(' T_CADENA ')' ';'",
"salida : OUTF '(' expresion ')' ';'",
"salida : OUTF '(' expresion ')'",
"salida : OUTF '(' T_CADENA ')'",
"salida : OUTF '(' ')' ';'",
"salida : OUTF '(' sentencia ')' ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF T_ID T_ASIGNACION tipo subrango",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' T_ID '>' T_ID ';'",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' LONGINT '>' T_ID",
"sentencia_declarativa_tipos : TYPEDEF PAIR '<' DOUBLE '>' T_ID",
"subrango : '{' T_CTE ',' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' T_CTE '}'",
"subrango : '{' T_CTE ',' '-' T_CTE '}'",
"subrango : '{' '-' T_CTE ',' '-' T_CTE '}'",
"subrango : error",
"condicion : expresion comparador expresion",
"condicion : expresion expresion",
"comparador : MENOR_IGUAL",
"comparador : MAYOR_IGUAL",
"comparador : DISTINTO",
"comparador : '='",
"comparador : '<'",
"comparador : '>'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list ';'",
"asignacion : IDENTIFIER_LIST T_ASIGNACION expresion_list",
"IDENTIFIER_LIST : T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' T_ID",
"IDENTIFIER_LIST : IDENTIFIER_LIST ',' acceso_par",
"IDENTIFIER_LIST : acceso_par",
"expresion_list : expresion",
"expresion_list : expresion_list ',' expresion",
"acceso_par : T_ID '{' T_CTE '}'",
"acceso_par : T_ID '{' '}'",
"acceso_par : T_ID '{' T_CTE",
"acceso_par : T_ID T_CTE '}'",
"goto_statement : GOTO T_ETIQUETA ';'",
"goto_statement : GOTO ';'",
"goto_statement : GOTO T_ETIQUETA",
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
#line 425 "gramatica.y"

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
#line 681 "y.tab.c"
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
#line 56 "gramatica.y"
{
    System.out.println("Programa compilado correctamente");
}
break;
case 2:
#line 59 "gramatica.y"
{ 
    System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias."); 
}
break;
case 3:
#line 62 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre del programa");}
break;
case 4:
#line 66 "gramatica.y"
{System.out.println("Llegue a BEGIN sentencia END");}
break;
case 5:
#line 67 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan bloques de sentencias dentro del codigo");}
break;
case 6:
#line 68 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan Delimitador o Bloque de Sentencia");}
break;
case 8:
#line 71 "gramatica.y"
{System.out.println("Llegue a sentencias");}
break;
case 19:
#line 83 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan ; al final del ret ");}
break;
case 20:
#line 84 "gramatica.y"
{System.out.println("Llegue a sentencia");}
break;
case 21:
#line 87 "gramatica.y"
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
#line 107 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta ; al final de sentencia declarativa");}
break;
case 23:
#line 111 "gramatica.y"
{ 
   
}
break;
case 24:
#line 114 "gramatica.y"
{ 
    
}
break;
case 25:
#line 116 "gramatica.y"
{ System.err.println("Error en línea: " + Lexer.nmrLinea + " - Forma incorrecta de declarar variables");}
break;
case 26:
#line 120 "gramatica.y"
{
        System.out.println("Declaración de función correcta");
    }
break;
case 27:
#line 124 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Error en la cantidad de parámetros de la función.");
    }
break;
case 28:
#line 129 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre del parámetro de la función.");
    }
break;
case 29:
#line 133 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta palabra reservada FUN.");
    }
break;
case 30:
#line 137 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el nombre de la función.");
    }
break;
case 31:
#line 142 "gramatica.y"
{
        /* Caso correcto con un solo parámetro*/
    }
break;
case 32:
#line 147 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 33:
#line 150 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función no debe tener más de un parámetro.");
    }
break;
case 34:
#line 153 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - La función debe tener un parámetro.");
    }
break;
case 35:
#line 157 "gramatica.y"
{}
break;
case 36:
#line 158 "gramatica.y"
{}
break;
case 37:
#line 162 "gramatica.y"
{ yyval.sval = "double"; }
break;
case 38:
#line 163 "gramatica.y"
{ yyval.sval = "longint"; }
break;
case 39:
#line 165 "gramatica.y"
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
#line 179 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 43:
#line 182 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia IF.");
            }
break;
case 44:
#line 186 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 45:
#line 189 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del IF.");
            }
break;
case 46:
#line 193 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 47:
#line 196 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta THEN en el IF.");
            }
break;
case 48:
#line 200 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 49:
#line 203 "gramatica.y"
{
                System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan sentencias en el IF.");
            }
break;
case 50:
#line 206 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 51:
#line 207 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Faltan parentesis en el IF.");}
break;
case 53:
#line 213 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la sentencia WHILE.");
    }
break;
case 54:
#line 216 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el bloque de sentencias en la declaración REPEAT.");
    }
break;
case 55:
#line 219 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta la condicion del WHILE.");
    }
break;
case 56:
#line 222 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta () en la sentencia while");}
break;
case 58:
#line 228 "gramatica.y"
{     
        System.out.println("Llegue a salida");   
        }
break;
case 59:
#line 231 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
        }
break;
case 60:
#line 234 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; en la salida.");
      }
break;
case 61:
#line 237 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - OUTF no puede ser vacio");}
break;
case 62:
#line 238 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Parámetro incorrecto en sentencia OUTF");}
break;
case 63:
#line 241 "gramatica.y"
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
case 64:
#line 261 "gramatica.y"
{
            /* Obtener el nombre del tipo desde T_ID*/
            String nombreTipo = val_peek(4).sval; /* T_ID*/

            /* Obtener el tipo base (INTEGER o SINGLE)*/
            String tipoBase = val_peek(2).sval;
            System.out.println("tipobase"+ " "+tipoBase );
            tablaTipos.put(nombreTipo, new TipoSubrango(tipoBase, limiteInferior, limiteSuperior));
        }
break;
case 65:
#line 270 "gramatica.y"
{

        }
break;
case 66:
#line 273 "gramatica.y"
{
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final de la declaración de tipo.");
        }
break;
case 67:
#line 276 "gramatica.y"
{
            System.err.println("Error en línea: " + Lexer.nmrLinea + " - Solo se pueden declarar pares de tipos basicos como LONGINT y DOUBLE");
        }
break;
case 68:
#line 279 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 69:
#line 280 "gramatica.y"
{System.err.println("Error en línea: " + Lexer.nmrLinea + " - Falta el ; al final del PAIR");}
break;
case 70:
#line 282 "gramatica.y"
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
case 71:
#line 304 "gramatica.y"
{ System.out.println("Llegue a subrango con - en el primero");}
break;
case 72:
#line 305 "gramatica.y"
{System.out.println("Llegue a subrango con - en el segundo");}
break;
case 73:
#line 306 "gramatica.y"
{System.out.println("Llegue a subrango con - en los dos");}
break;
case 74:
#line 307 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Subrango mal definido o faltan delimitadores.");
    }
break;
case 76:
#line 313 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta expresion del lado derecho de la comparacion");}
break;
case 83:
#line 325 "gramatica.y"
{}
break;
case 84:
#line 326 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final de la asignacion");}
break;
case 85:
#line 328 "gramatica.y"
{ 
                }
break;
case 86:
#line 330 "gramatica.y"
{
               
                }
break;
case 87:
#line 333 "gramatica.y"
{
                }
break;
case 88:
#line 335 "gramatica.y"
{
                }
break;
case 89:
#line 339 "gramatica.y"
{
      }
break;
case 90:
#line 341 "gramatica.y"
{
      }
break;
case 91:
#line 347 "gramatica.y"
{
                    /* Verificar si el T_CTE es '1' o '2'*/
          if (!(val_peek(1).sval.equals("1") || val_peek(1).sval.equals("2"))) {
              yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Solo se permite 1 o 2 dentro de las llaves.");
          } else {
              yyval.sval = val_peek(3) + "{" + val_peek(1) + "}";
          
      }
    }
break;
case 92:
#line 356 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta constante dentro de las llaves."); 
      }
break;
case 93:
#line 359 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta cierre de llave en acceso a parámetros."); 
      }
break;
case 94:
#line 362 "gramatica.y"
{ 
          yyerror("Error en linea: " + Lexer.nmrLinea + " Error: Falta apertura de llave en acceso a parámetros."); 
    }
break;
case 96:
#line 368 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: hay goto sin etiqueta"); }
break;
case 97:
#line 369 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Falta ; al final del GOTO");}
break;
case 98:
#line 372 "gramatica.y"
{
      }
break;
case 99:
#line 374 "gramatica.y"
{
        System.err.println("Error en línea: " + Lexer.nmrLinea + " - Invocación a funcion mal definida");
        }
break;
case 109:
#line 389 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 110:
#line 390 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Error: Dos o mas operadores juntos");}
break;
case 111:
#line 391 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + "Error: Dos o mas operadores juntos");}
break;
case 118:
#line 395 "gramatica.y"
{
        }
break;
case 119:
#line 397 "gramatica.y"
{
        }
break;
case 120:
#line 399 "gramatica.y"
{
        }
break;
case 121:
#line 401 "gramatica.y"
{
        }
break;
case 122:
#line 403 "gramatica.y"
{
        }
break;
case 123:
#line 405 "gramatica.y"
{
        }
break;
case 124:
#line 407 "gramatica.y"
{
        }
break;
case 125:
#line 409 "gramatica.y"
{
        }
break;
case 126:
#line 411 "gramatica.y"
{ /* Se añade la regla para operadores unarios*/
        }
break;
case 127:
#line 413 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 128:
#line 414 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 129:
#line 415 "gramatica.y"
{System.err.println("Error en linea: " + Lexer.nmrLinea + " Dos o mas operadores juntos");}
break;
case 130:
#line 419 "gramatica.y"
{ /* Esta regla maneja específicamente el '-' unario*/
    yyval.dval = -val_peek(0).dval;
}
break;
#line 1361 "y.tab.c"
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
