public class Token {
    public String token_type;
    public String text;
    public int line;
    public int column;

    public Token(String type, String text, int line, int column) {
        this.token_type = type;
        this.text = text;
        this.line = line;
        this.column = column;
    }

    @Override
    public String toString() {
        return String.format("(%s, '%s', %d, %d)", token_type, text, line, column);
    }
}