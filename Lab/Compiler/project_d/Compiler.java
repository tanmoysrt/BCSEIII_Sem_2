import java.util.ArrayList;

public class Compiler {
    public static void main(String[] args) throws Exception{
        String code = "int main() {\n" +
        "    int a = 5;\n" +
        "    char c = 'x';\n" +
        "    string s = \"hello\";\n" +
        "    put a;\n" +
        "    a = (a == 2) ? 56 : 96" +
        "    { "+
        "        char a = 6;\n" +
        "    }" +
        "    put a;\n" +
        "    return 0;\n" +
        "}";
        System.out.println(code);
        // Generate tokens
        ArrayList<Token> tokens = Tokenizer.tokenize(code);
        System.out.println("Tokens: ");
        for (Token token : tokens) {
            System.out.println(token);
        }
        // Generate symbol table
        SymbolTable symbolTable = SymbolTable.generateFromTokens(tokens);
        symbolTable.display();

    }
}
