import java.nio.file.Path;
import java.util.ArrayList;

public class Compiler {
    public static void main(String[] args) throws Exception{
//        String code = "id + id * id";
        String code = "id + ( id )";
       System.out.println(code);
        // Generate tokens
       ArrayList<Token> tokens = Tokenizer.tokenize(code);
       System.out.println("\n> Tokens: ");
       for (Token token : tokens) {
           System.out.print(token.text);
           System.out.print(" ");
       }
       System.out.println("\n--------------------\n");
        // Generate symbol table
//        SymbolTable symbolTable = SymbolTable.generateFromTokens(tokens);
//        symbolTable.display();
//        System.out.println("--------------------\n");

        // Create instance of SLR
        SLR slr = new SLR();
        // Read production rules
        Path fileName = Path.of("./CFG.txt");
        slr.readProductions(fileName);
        System.out.println("> Production Rules : \n");
        // Display production rules
        slr.displayProductionRules();
        System.out.println("--------------------\n");
        // Set start symbol
        SLR.START_SYMBOL = "S";
        // Generate first set
        slr.computeFirstPos();
        System.out.println("> First Set : \n");
        slr.displayFirstPosTable();
        System.out.println("--------------------\n");
        // Display follow set
        System.out.println("\n> Follow Set : \n");
        slr.displayFollowPosTable();
        System.out.println("--------------------\n");
        // Generate LR(0) Item Set
        slr.generateItemSets();
        // Display LR(0) Item Set
        System.out.println("> LR(0) Item Sets : \n");
        slr.displayItemSets();
        // Display goto table
        System.out.println("\n> Goto Table : \n");
        slr.displayGotoTable();
        System.out.println("--------------------\n");
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
