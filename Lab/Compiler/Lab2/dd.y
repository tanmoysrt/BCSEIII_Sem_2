%{
#include <stdio.h>
#include <string.h>
#include <ctype.h>
%}

%token NUMBER

%%

input: number_list

number_list: number_list number | /* empty */

number: NUMBER {
    char *p = yytext;
    if (*p == '0' && *(p + strlen(p) - 1) == '0') {
        printf("Valid: %s\n", yytext);
    } else {
        printf("Invalid: %s\n", yytext);
    }
}

%%

int main(void) {
    yyparse();
    return 0;
}
