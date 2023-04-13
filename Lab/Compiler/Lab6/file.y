
%{
    #include <stdio.h>
    #include <stdlib.h>
%}

%token INTEGER
%token NL
%token SPACE
%token ID
%start stmt

%%

stmt: stmt NL {printf("Success\n"); return 0;}

stmt: INTEGER SPACE stmt
    | ID SPACE stmt
    | ID
    | INTEGER

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
