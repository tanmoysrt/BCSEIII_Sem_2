%{
    #include <stdio.h>
    #include <stdlib.h>
    void yyerror(char* s);
    int yylex();
%}

%union {
    int number;
    int identifier;
    char* string;
}

%token <number> IDENTIFIER
%token <number> NUMBER
%token <string> RELOP
%token SEMICOLON COLON QUESTION SPACE PLUS NL COMMA
%type <number> ternary
%type <number> start
%type <number> expr
%type <number> comparator
%start start


%%
    start : ternary SEMICOLON NL {$$ = $1;printf("Result %d\n" , $1);return 0;}

    ternary: comparator SPACE QUESTION SPACE expr SPACE COLON SPACE expr {$$ = $1?$5:$9;}

    expr: NUMBER PLUS expr {$$=$1+$3;}
        | NUMBER{$$ = $1;}

    comparator: IDENTIFIER SPACE RELOP SPACE NUMBER {
        switch($3){
            case "<": $$ = $1 < $5; break;
            case ">": $$ = $1 > $5; break;
            case "<=": $$ = $1 <= $5; break;
            case ">=": $$ = $1 >= $5; break;
            case "==": $$ = $1 == $5; break;
            case "!=": $$ = $1 != $5; break;
        }
    }
%%

void yyerror(char* s) {
    printf("Error: %s", s);
}

int main(int argc, char** argv) {
    yyparse();
    return 0;
}