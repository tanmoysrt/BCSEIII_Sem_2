import java.nio.file.Path;
import java.util.ArrayList;

public class Compiler {
    public static void main(String[] args) throws Exception{
//        String code = "id + id * id";
        // String code = "id + ( id )";
        String code = "int main(){int a = 0 ; (a == 2) ? a*5 : a*6 ; a = (a < 2) ? 5 : 2 ; a=6; get a; put a; return a; } ";
       System.out.println(code); 
        // Generate tokens
       ArrayList<Token> tokens = Tokenizer.tokenize(code);
       System.out.println("\n> Tokens: ");
       for (Token token : tokens) {
           System.out.print(token.text);
           System.out.print("|");
       }
       System.out.println("\n--------------------\n");
        // Generate symbol table
       SymbolTable symbolTable = SymbolTable.generateFromTokens(tokens);
       symbolTable.display();
       System.out.println("--------------------\n");

        // Create instance of SLR
        SLR slr = new SLR();
        // Read production rules
        Path fileName = Path.of("./CFG_latest.txt");
        slr.readProductions(fileName);
        System.out.println("> Production Rules : \n");
        // Display production rules
        slr.displayProductionRules();
        System.out.println();
        // Set start symbol
        SLR.START_SYMBOL = "prog";
        // Generate first set
        slr.computeFirstPos();
        System.out.println("> First / Follow Set \n");
        slr.displayFirstAndFollowPosTable();
        // Generate LR(0) Item Set
        slr.generateItemSets();
        // Display LR(0) Item Set
        System.out.println("> LR(0) Item Sets : \n");
        slr.displayItemSets();
        // Display goto table
        System.out.println("\n> Goto Table : \n");
        slr.displayGotoTable();
        // Display production numbers
        System.out.println("\n> Production Numbers : \n");
        slr.displayNumberedProductionRules();
        // Generate SLR Parsing Table
        slr.generateParsingTable();
        // Display SLR Parsing Table
        System.out.println("\n> SLR Parsing Table : \n");
        slr.displayParsingTable();
        // Parse tokens
       StringBuilder output = new StringBuilder();
       for (Token token : tokens) {
           output.append(token.text);
           output.append(" ");
       }
       System.out.println("\n> Parsing Tokens : \n");
       slr.parseInput(output.toString());



        

    }
}
