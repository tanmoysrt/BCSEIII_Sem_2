%option noinput nounput noyywrap

%{
#include "file.tab.h"
%}

%%
[ \t]+ {return SPACE;}
[0-9]+ { yylval.number = atoi(yytext); return INTEGER;} 
[a-zA-Z]+ { yylval.number = 5; return ID;}
[\<\<=\>\>=\==\!=\;\:] {return RELOP;}
[\?] {return QUESTIONMARK;}
[\;] {return SEMICOLON;}
[\:] {return COLON;}
[\n] {return NL;}
%%