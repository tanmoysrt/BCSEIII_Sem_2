import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tokenizer {

    // Define regular expression patterns for the tokens
    private static final String[][] TOKEN_REGEX = {
        {"operator", "(=|<|>|==|\\+|-|\\*|\\?|\\:)"},
        {"punctuator", "(\\(|\\)|\\{|\\}|\\,)"},
        {"delimiter", "(;)"},
        {"constant", "(\\d+|\'.\'|\".*\")"},
        {"keyword", "(get|put|return|int|char|string)"},
        {"identifier", "[a-zA-Z_]\\w*"}
    };

    // Regex for whitespace
    public static Pattern whitespacePattern = Pattern.compile("\\s+");

    // Define a function to tokenize the input string
    public static ArrayList<Token> tokenize(String inputString) throws Exception {
        ArrayList<Token> tokens = new ArrayList<Token>();
        int position = 0;

        while (position < inputString.length()) {
            Matcher matcher = null;
            // update position if the current character is a whitespace
            matcher = whitespacePattern.matcher(inputString.substring(position));
            if (matcher.lookingAt()) {
                position += matcher.end();
                continue;
            }
            // Check for each token type
            for (String[] tokenRegex : TOKEN_REGEX) {
                // compile the regex pattern for the token
                Pattern pattern = Pattern.compile(tokenRegex[1]);
                matcher = pattern.matcher(inputString.substring(position));
                if (matcher.lookingAt()) {
                    String text = matcher.group(0);
                    // For string and char tokens, remove the quotes
                    if (tokenRegex[0].equals("constant")) {
                        // check if constant is a string or char
                        Pattern charOrStringPattern = Pattern.compile("(\'.\'|\".*\")");
                        Matcher charOrStringMatcher = charOrStringPattern.matcher(text);
                        if (charOrStringMatcher.matches()) {
                            text = text.substring(1, text.length() - 1);
                        }
                    }
                    // Get the line and column number of the token
                    int[] lineColumn = getLineAndColumn(inputString, position);
                    // Add the token to the list
                    tokens.add(new Token(tokenRegex[0], text, lineColumn[0], lineColumn[1]));
                    // Update the position
                    position += matcher.end();
                    break;
                }
            }
            // If no token matched, throw an exception
            if (matcher == null || !matcher.lookingAt()) {
                int[] lineColumn = getLineAndColumn(inputString, position);
                throw new Exception("Invalid token at line " + lineColumn[0] + " and column " + lineColumn[1]);
            }
        }
        return tokens;
    }

    public static int[] getLineAndColumn(String inputString, int position) {
        // 1-indexed
        // line no -> no of new lines before the position + 1
        // column no -> position - index of last new line
        int[] lineColumn = new int[2];
        int line = inputString.substring(0, position).split("\r?\n").length;
        int lastNewlinePos = inputString.substring(0, position).lastIndexOf('\n');
        int column = position - lastNewlinePos;
        lineColumn[0] = line;
        lineColumn[1] = column;
        return lineColumn;
    }
}


