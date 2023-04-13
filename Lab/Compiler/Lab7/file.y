

%{
    #include <stdio.h>
    #include <stdlib.h>

    int x = 0;
    int ans = -1;
%}

%union {
    int number;
    int identifier;
}

%token SPACE
%token INTEGER
%token ID
%token RELOP
%token QUESTIONMARK
%token SEMICOLON
%token COLON
%token NL
%type comparator
%type expr
%type lambda
%start stmt
%%

stmt: lambda SEMICOLON NL {$$ = $1; printf("Answer %d", $1); return 0;}
lambda: comparator SPACE QUESTIONMARK SPACE expr SPACE COLON SPACE expr {$$ = $1 ?$5:$9;}
comparator: ID SPACE RELOP SPACE INTEGER {printf("comparator %s %s %d", $1, $3, $5);};
expr: ID {$$ = $1; printf("expr %s", $1);}

%%

int yyerror(char *msg)
 {
  printf("invalid string\n");
  exit(0);
 }
//driver code 
main()
 {
  printf("enter the string\n");
  yyparse();
 }
