%{
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "IOMngr.h"
#include "SymTab.h"
#include "Semantics.h"
#include "CodeGen.h"

extern int yylex();	/* The next token function. */
extern char *yytext;   /* The matched token text.  */
extern int yyleng;      /* The token text length.   */
extern int yyparse();
extern int yyerror(char *);
void dumpTable();

extern SymTab *table;

%}


%union {
  long val;
  char * string;
  struct ExprRes * ExprRes;
  struct InstrSeq * InstrSeq;
 // struct BExprRes * BExprRes;
}

%type <string> Id
%type <ExprRes> Unary
%type <ExprRes> Expo
%type <ExprRes> Factor
%type <ExprRes> Term
%type <ExprRes> Expr
%type <InstrSeq> StmtSeq
%type <InstrSeq> Stmt
%type <ExprRes> BExpr
%type <ExprRes> Bool


//%token SetIO
//%token ARR
%token PrintStr
%token While
%token PrintLine
%token PrintSpace
%token Read
%token Ident 		
%token IntLit 	
%token Int
%token Write
%token IF
%token ELSE
%token EQ
%token NEQ
%token GT
%token LT
%token LTE
%token GTE
%token AND
%token OR
%token Str


%%

Prog		:	Declarations StmtSeq				 	        	{Finish($2); } ;
Declarations	:	Dec Declarations							{ };
//Declarations 	: 	ArrDec Declarations							{ };
Declarations	:								         	{ };
Dec		:	Int Ident {enterName(table, yytext);}';'	                        {};
//ArrDec	:	Int Ident {enterName(table, yytext);} '[' Expr ']' ';'			{printf("dec arr...\n");};	
StmtSeq 	:	Stmt StmtSeq								{$$ = AppendSeq($1, $2); }; 
StmtSeq		:										{$$ = NULL;} ;
Stmt 		: 	Read '(' Id ')' ';'							{$$ = doRead($3);}; 
Stmt		:	While '(' BExpr ')' '{' StmtSeq '}'					{$$ = doWhile($3, $6);};
Stmt		:	PrintLine '(' Expr ')' ';'						{$$ = printLines($3);};
Stmt 		: 	PrintSpace '(' Expr ')' ';'						{$$ = printSpaces($3);};
Stmt		:	Write '(' Expr ')' ';'							{$$ = doPrint($3); };
Stmt		:	PrintStr '(' Expr ')' ';'						{$$ = printStr($3);};
Stmt		:	Id '=' Expr ';'								{$$ = doAssign($1, $3);};
Stmt		: 	Id '=' BExpr ';'							{$$ = doAssign($1, $3);};
Stmt		:	IF '(' BExpr ')' '{' StmtSeq '}'					{$$ = doIf($3, $6);};
Stmt 		: 	IF '(' BExpr ')' '{' StmtSeq '}' ELSE '{' StmtSeq '}'			{$$ = doIfElse($3,$6,$10);};
BExpr		:	'!'  '(' BExpr ')'							{$$ = doNOT($3);}; 
BExpr		: 	'!' Expr								{$$ = doNOT($2);};
BExpr		:	'!' Bool								{$$ = doNOT($2);};
BExpr		: 	Expr AND Expr								{$$ = doAND($1, $3);};
BExpr		:	Bool AND Bool								{$$ = doAND($1, $3);};
BExpr		: 	Bool OR Bool								{$$ = doOR($1, $3);}
BExpr		: 	Expr OR Expr								{$$ = doOR($1, $3);};
BExpr		:	Bool									{$$ = $1;};
Bool		:	Expr EQ Expr								{$$ = doBExpr($1, $3);};
Bool		: 	Expr NEQ Expr								{$$ = doNEQ($1, $3);};
Bool		: 	Expr LT Expr								{$$ = doLT($1, $3);};
Bool		: 	Expr GT Expr								{$$ = doGT($1, $3);};
Bool		:	Expr GTE Expr								{$$ = doGTE($1, $3);};
Bool		:	Expr LTE Expr								{$$ = doLTE($1, $3);};
//Bool		: 	Expr									{$$ = $1;};
Expr		:	Expr '+' Term								{$$ = doAdd($1, $3); } ;
Expr 		:	Expr '-' Term								{$$ = doSub($1, $3);};
Expr		:	Term									{$$ = $1; } ;
Term		:	Term '*' Factor								{$$ = doMult($1, $3); } ;
Term		:	Term '%' Factor								{$$ = doMod($1, $3);};
Term		:	Term '/' Factor								{$$ = doDiv($1, $3);};
Term		:	Factor									{$$ = $1;};
Factor		: 	Expo '^' Factor 							{$$ = doPow($1, $3);};
Factor		: 	Expo									{$$ = $1;};
Expo		: 	'-' Unary 								{$$ = doNeg($2);};
Expo		:	Unary									{$$ = $1;};
Expo		:       '(' Expr ')'								{$$ = $2;};		
Unary 		:	Str									{$$ = getStr(yytext);};
Unary		:	IntLit									{ $$ = doIntLit(yytext); };
Unary		:	Ident									{ $$ = doRval(yytext); };
Id		: 	Ident									{ $$ = strdup(yytext);}
 
%%

int yyerror(char *s)  {
  writeIndicator(getCurrentColumnNum());
  writeMessage("Illegal Character in YACC");
  return 1;
}
