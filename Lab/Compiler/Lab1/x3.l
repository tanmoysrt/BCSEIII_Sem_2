%{
#include <stdio.h>
%}

%%
[0-9]+        printf("INTEGER: %s\n", yytext);
%%

int main(int argc, char *argv[]) {
    yylex();
    return 0;
}
