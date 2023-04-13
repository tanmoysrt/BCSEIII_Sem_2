%{
    #include <stdio.h>
    #include <stdlib.h>
    void yyerror(char* s);
    int yylex();
%}

%union {
    int number;
    int identifier;
}

%token <number> IDENTIFIER
%token <number> NUMBER
%token GE LE EQ GT LT SEMICOLON COLON QUESTION SPACE PLUS NE NL COMMA
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

    comparator: IDENTIFIER SPACE GE SPACE NUMBER {$$ = $1 >= $5;}
              | IDENTIFIER SPACE LE SPACE NUMBER {$$ = $1 <= $5;}
              | IDENTIFIER SPACE EQ SPACE NUMBER {$$ = $1 == $5;}
              | IDENTIFIER SPACE GT SPACE NUMBER {$$ = $1 > $5;}
              | IDENTIFIER SPACE LT SPACE NUMBER {$$ = $1 < $5;}
              | IDENTIFIER SPACE EQ SPACE IDENTIFIER {$$ = $1 == $5;}
              | IDENTIFIER SPACE GT SPACE IDENTIFIER {$$ = $1 > $5;}
              | IDENTIFIER SPACE LT SPACE IDENTIFIER {$$ = $1 < $5;}
              | IDENTIFIER SPACE GE SPACE IDENTIFIER {$$ = $1 >= $5;}
              | IDENTIFIER SPACE LE SPACE IDENTIFIER {$$ = $1 <= $5;}
%%

void yyerror(char* s) {
    printf("Error: %s", s);
}

int main(int argc, char** argv) {
    yyparse();
    return 0;
}