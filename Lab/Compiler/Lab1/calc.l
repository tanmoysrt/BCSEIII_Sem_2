%option noinput nounput noyywrap

%{
#include <stdio.h>
#include <stdlib.h>
#include <math.h>

double result = 0;
int last_operation = 0;
// Last read number
double number = 0;

%}

%%
[0-9]+((\.)?[0-9]+)? {
    sscanf(yytext, "%lf", &number);
    switch(last_operation) {
        case 1: 
            result += number; // +
            break;
        case 2:
            result -= number; // -
            break;
        case 3:
            result *= number; // *
            break;
        case 4:
            result /= number;// divide
            break;
        case 5:
            result = pow(result, number); // power
            break;
        default:
            result = number; // first number
            break;
    }
    last_operation = 0; // Reset op
}

\+   { last_operation = 1; }
\-   { last_operation = 2; }
\*   { last_operation = 3; }
\/   { last_operation = 4; }
\^   { last_operation = 5; }
\n   { printf("%.2lf\n", result); }
.    ;
%%

// Main function
int main() {
    yylex();
    return 0;
}
