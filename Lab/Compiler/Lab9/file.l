%option noinput nounput noyywrap

%{
    #include "file.tab.h"
%}

%%
[" "]+ {return SPACE;}
[0-9]+ {yylval.number = atoi(yytext);return NUMBER;}
[a-zA-Z]+ {yylval.number = 5;return IDENTIFIER;}
[\>=\>\<\<=\==\!=] {return RELOP;}
";" {return SEMICOLON;}
":" {return COLON;}
"?" {return QUESTION;}
"+" {return PLUS;}
"," {return COMMA;}
\n {return NL;}
%%