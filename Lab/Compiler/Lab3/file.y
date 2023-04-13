%{
   /* Definition section */
   #include<stdio.h>
   #include<stdlib.h>
%}
  
%token A B NL
  
/* Rule Section */
%%
stmt: S NL  { printf("valid string\n");
              exit(0); }
;
S: A S B | A S | S B |
S: S A S B | S B S A |
;
%%


  
int yyerror(char *msg)
 {
  printf("invalid string\n");
  exit(0);
 }

#include "lex.yy.c"  
//driver code 
main()
 {
  printf("enter the string\n");
  yyparse();
 }
