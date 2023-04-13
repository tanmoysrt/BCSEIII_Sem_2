%option noinput nounput noyywrap

%{
#include "file.tab.h"
%}

%%
[" "] {return SPACE;}
[0-9]+ {return INTEGER;} 
[a-zA-Z]+ {return ID;}
[\n] {return NL;}
%%

