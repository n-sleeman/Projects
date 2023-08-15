%{
//#include "yaccExample.h"
#include "IOMngr.h"
#include "y.tab.h"

#define YY_INPUT(buf,result,max_size) \
	{ int c = getNextSourceChar(); \
          result = (c == EOF) ? YY_NULL : (buf[0] = c, 1); \
        }

%}


letter [A-Za-z]
digit [0-9]
string [A-Za-z0-9 !@:()#$%|^&*?.,<>/=+-]

%%
while					{return While;}
if					{return IF;}
else					{return ELSE;}
int					{return Int;}
print					{return Write;}
printString				{return PrintStr;}
printlines				{return PrintLine;}
printspaces				{return PrintSpace;}
read					{return Read;}
\"({string})+\"				{return Str;}
{letter}({letter}|{digit})*		{return Ident;}
{digit}{digit}*				{return IntLit;}
\=\=					{return EQ;}
\!\=					{return NEQ;}
\<					{return LT;}
\>					{return GT;}
\<\=					{return LTE;}
\>\=					{return GTE;}
\&\&					{return AND;}
\|\|					{return OR;}
\[					{return '[';}
\]					{return ']';}
\=					{return '=';}
\!					{return '!';}
\+					{return '+';}
\^					{return '^';}
\-					{return '-';}
\/					{return '/';}	
\%					{return '%';}
\*					{return '*';}
\;					{return ';';}
\{					{return '{';}
\}					{return '}';}
\(					{return '(';}
\)					{return ')';}


[ ]				{}
\t				{}
\r				{}
\n				{}

.				{writeIndicator(getCurrentColumnNum());
				   writeMessage("Illegal Character in lex");}

%%

	
int yywrap () {
    return 1;
}
			
	
