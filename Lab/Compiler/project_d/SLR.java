import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class SLR {
    private Map<String, List<List<String>>> production_rules;
    private Map<String, Set<String>> firstPos;
    private Map<String, Set<String>> followPos;
    public static final String EPSILON = "EPSILON";
    public static final String DOLLAR = "$";
    public static String START_SYMBOL = null;

    public SLR() {
        production_rules = new HashMap<>();
        firstPos = new HashMap<>();
        followPos = new HashMap<>();
    }

    public void readProductions(Path path) throws Exception {
        List<String> raw_lines = Files.readAllLines(path);
        for(String line : raw_lines) {
            String[] parts = line.split("->");
            String nonTerminal = parts[0].trim();
            List<List<String>> productions = new ArrayList<>();
            String[] production_parts = parts[1].trim().split("\\|");
            for (String production_part : production_parts) {
                String[] symbols = production_part.trim().split(" ");
                List<String> tmp = Arrays.asList(symbols).stream()
                                    .map(String::trim)
                                    .filter(x -> !x.isEmpty())
                                    .collect(Collectors.toList());
                productions.add(tmp);
            }
            production_rules.put(nonTerminal, productions);
        }
    }

    public boolean isNonTerminal(String symbol) {
        return production_rules.containsKey(symbol);
    }

    public Set<String> findFirst(String symbol) {
        /**
         * Algorithm:
         * 1. If symbol is a terminal, return symbol
         * 2. If symbol is epsilon, return epsilon
         * 3. If already computed, return firstPos
         * 4. Fetch productions of symbol
         * 5. For each production, find first of first symbol
         * 6. Store result in firstPos
         */
        Set<String> first = new HashSet<>();
        // 1
        if(!isNonTerminal(symbol)) {
            first.add(symbol);
            return first;
        }
        // 2
        if(isNullable(symbol)) {
            first.add(EPSILON);
            return first;
        }
        // 3
        if(firstPos.containsKey(symbol)) {
            return firstPos.get(symbol);
        }
        // 4
        List<List<String>> productions = production_rules.get(symbol);
        // 5
        for (List<String> production : productions) {            
            for (int i = 0; i < production.size(); i++) {
                Set<String> firstSymbolFirst = findFirst(production.get(i));
                if(isNullable(production.get(i)) || !firstSymbolFirst.contains(EPSILON)) {
                    first.addAll(firstSymbolFirst);
                    break;
                }else{
                    // remove only if it is not the last symbol
                    if(i + 1 < production.size()) {
                        firstSymbolFirst.remove(EPSILON);
                    }
                    first.addAll(firstSymbolFirst);
                }
            }
        }
        // 6
        firstPos.put(symbol, new HashSet<>(first));
        return first;
    }

    public Set<String> findFollow(String symbol) {
        /*
         * Algorithm:
         * 1. Fetch all productions
         * 2. If symbol is start symbol, add $ to follow list
         * 3. For each production parts, find follow of symbol
         * 4. If symbol is last character, add follow(start_symbol) to follow
         * 5. If character next to symbol is terminal, add it to follow
         * 6. If character next to symbol is non-terminal, add first(character) to follow
         */
        if(followPos.containsKey(symbol)) {
            return followPos.get(symbol);
        }
        HashSet<String> follow = new HashSet<>();
        if(isNullable(symbol)) {
            return follow;
        }

        if(START_SYMBOL.equals(symbol)) {
            follow.add(DOLLAR);
        }

        // 1 
        for (String nonTerminal : production_rules.keySet()) {
            // 3
            List<List<String>> productions = production_rules.get(nonTerminal);
            for (List<String> production : productions) {
                for (int i = 0; i < production.size(); i++) {
                    String currentSymbol = production.get(i);
                    String nextSymbol = i + 1 < production.size() ? production.get(i + 1) : null;
                    if(currentSymbol.equals(symbol)) {
                        // 4
                        if(nextSymbol == null) {
                            // last symbol
                            if(!nonTerminal.equals(symbol)){
                                follow.addAll(findFollow(nonTerminal));
                            }
                        }else{
                            // 5
                            if(!isNonTerminal(nextSymbol)) {
                                // terminal
                                follow.add(nextSymbol);
                            }else{
                                // 6
                                Set<String> first = findFirst(nextSymbol);
                                // if first has epsilon then go for next symbol in production
                                if(first.contains(EPSILON)){
                                    first.remove(EPSILON);
                                    follow.addAll(first);
                                    if(i + 2 < production.size()) {
                                        follow.addAll(findFirst(production.get(i + 2)));
                                    }else{
                                        follow.addAll(findFollow(nonTerminal));
                                    }
                                }else{
                                    follow.addAll(first);
                                }
                            }
                        }
                    }
                }
            }
        }

        followPos.put(symbol, follow);
        return follow;

    }

    public boolean isNullable(String symbol) {
        return symbol.equals(EPSILON);
    }


    public void computerFirstPos() {
        for (String nonTerminal : production_rules.keySet()) {
            findFirst(nonTerminal);
        }
    }

    public static void main(String[] args) throws Exception {
        // Read the grammar from CFG.txt
        Path fileName = Path.of("./CFG_test.txt");
        SLR slr = new SLR();
        slr.readProductions(fileName);
        // slr.computerFirstPos();
        // System.out.println(slr.findFirst("S"));
        SLR.START_SYMBOL = "S";
        System.out.println(slr.findFollow("S"));
        System.out.println(slr.findFollow("B"));
        System.out.println(slr.findFollow("C"));
        System.out.println(slr.findFollow("D"));
        System.out.println(slr.findFollow("E"));
        System.out.println(slr.findFollow("F"));
        // System.out.println(slr.findFollow("C"));
        // System.out.println(slr.findFollow("D"));
        // System.out.println(slr.findFollow("E"));
        // System.out.println(slr.findFollow("F"));
        // slr.readProductions(fileName);
        // slr.findFirstPos("prog", new String[] {"type", "main", "(", ")", "{", "stmts", "}"});

        // for (String production : slr.productions) {
        //     String[] parts = production.split("->");
        //     String nonTerminal = parts[0].trim();
        //     String[] symbols = parts[1].trim().split(" ");
        //     slr.findFirstPos(nonTerminal, symbols);
        //     slr.findFollowPos(nonTerminal, symbols);
        // }
        // Prepare followpos and firstpos
        // Prepare the parsing table
    }
}
