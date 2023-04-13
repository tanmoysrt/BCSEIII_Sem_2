%option noinput nounput noyywrap

%{
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

// define variables here

%}

%%

// write logics here

%%

// Main function
int main() {
    yylex();
    // can write some
    return 0;
}
