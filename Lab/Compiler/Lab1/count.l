%{
#include <stdio.h>
int floatcount = 0;
int inetegercount = 0;
%}

%%
[0-9]+\.[0-9]+     floatcount++;
[0-9]+     inetegercount++;
.       ;
%%

int main(int argc, char *argv[]) {
    yylex();
    printf("floatcount: %d\n", floatcount);
    printf("inetegercount: %d\n", inetegercount);
    printf("total: %d\n", floatcount + inetegercount);
    return 0;
}
