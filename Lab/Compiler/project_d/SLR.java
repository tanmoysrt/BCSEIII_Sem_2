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
    private Map<Integer, Map<String, List<List<String>>>> itemSets;

    public SLR() {
        production_rules = new HashMap<>();
        firstPos = new HashMap<>();
        followPos = new HashMap<>();
        itemSets = new HashMap<>();
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

    public boolean isTerminal(String symbol) {
        return !isNonTerminal(symbol);
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

    public void computeFirstPos() {
        for (String nonTerminal : production_rules.keySet()) {
            findFirst(nonTerminal);
        }
    }
    
    public Map<String, List<List<String>>> closure(String symbol) {
        Map<String, List<List<String>>> closureSet = new HashMap<>();
        Queue<String> queue = new LinkedList<>();
        List<String> startRule = new ArrayList<>(Arrays.asList(".", symbol));
        List<List<String>> startClosure = new ArrayList<>(Arrays.asList(startRule));
        closureSet.put(symbol, startClosure);
        queue.offer(symbol);
        while (!queue.isEmpty()) {
            String nextSymbol = queue.poll();
            List<List<String>> nextClosureSet = closureSet.get(nextSymbol);
            for (int i = 0; i < nextClosureSet.size(); i++) {
                List<String> nextAugmentedRule = new ArrayList<>(nextClosureSet.get(i));
                if (nextAugmentedRule.indexOf(".") == nextAugmentedRule.size() - 1) {
                    continue;
                }
                String nextNextSymbol = nextAugmentedRule.get(nextAugmentedRule.indexOf(".") + 1);
                if (!production_rules.containsKey(nextNextSymbol)) {
                    continue;
                }
                List<List<String>> nextNextClosureSet = closureSet.getOrDefault(nextNextSymbol, new ArrayList<>());
                for (List<String> nextProductionRule : production_rules.get(nextNextSymbol)) {
                    List<String> nextNextAugmentedRule = new ArrayList<>(nextProductionRule);
                    nextNextAugmentedRule.add(0, ".");
                    boolean added = false;
                    for (Iterator<List<String>> it = nextNextClosureSet.iterator(); it.hasNext();) {
                        List<String> rule = it.next();
                        if (rule.equals(nextNextAugmentedRule)) {
                            added = true;
                            break;
                        }
                    }
                    if (!added) {
                        nextNextClosureSet.add(nextNextAugmentedRule);
                        closureSet.put(nextNextSymbol, nextNextClosureSet);
                        queue.offer(nextNextSymbol);
                    }
                }
            }
        }
        // delete the self E -> . E
        if(startClosure.size() > 0){
            closureSet.get(symbol).remove(startClosure.get(0));
        }
        return closureSet;
    }
    
    public void preapareFirstSet(){
        Map<String, List<List<String>>> closureSet = closure(START_SYMBOL);
        closureSet.put(START_SYMBOL+"'", new ArrayList<>(Arrays.asList(new ArrayList<>(Arrays.asList(".", START_SYMBOL)))));
        itemSets.put(itemSets.size(), closureSet);
    }

    public Map<String, List<List<String>>> gotoSet(Map<String, List<List<String>>> closureSet, String symbol) {
        Map<String, List<List<String>>> closureSetCopy = new HashMap<>();
        for (String nonTerminal : closureSet.keySet()) {
            List<List<String>> rules = new ArrayList<>();
            for (List<String> rule : closureSet.get(nonTerminal)) {
                rules.add(new ArrayList<>(rule));
            }
            closureSetCopy.put(nonTerminal, rules);
        }

        Map<String, List<List<String>>> gotoSets = new HashMap<>();
        // filter the closure which has symbol after dot
        for (String nonTerminal : closureSetCopy.keySet()) {
            List<List<String>> rules = new ArrayList<>();
            for (List<String> rule : closureSetCopy.get(nonTerminal)) {
                if (rule.indexOf(".") + 1 < rule.size() && rule.get(rule.indexOf(".") + 1).equals(symbol)) {
                    rules.add(rule);
                }
            }
            if(rules.size() > 0){
                gotoSets.put(nonTerminal, rules);
            }
        }

        // move the dot
        for (String nonTerminal : gotoSets.keySet()) {
            List<List<String>> rules = gotoSets.get(nonTerminal);
            for (List<String> rule : rules) {
                int dotIndex = rule.indexOf(".");
                rule.set(dotIndex, rule.get(dotIndex + 1));
                rule.set(dotIndex + 1, ".");
            }
        }

        // generate the list of symbols after dot
        Set<String> symbols_after_dot = new HashSet<>();
        for (String nonTerminal : gotoSets.keySet()) {
            List<List<String>> rules = gotoSets.get(nonTerminal);
            for (List<String> rule : rules) {
                int dotIndex = rule.indexOf(".");
                if(dotIndex + 1 < rule.size()){
                    symbols_after_dot.add(rule.get(dotIndex + 1));
                }
            }
        }

        // generate closure set for each symbol after dot
        for (String symbol_after_dot : symbols_after_dot) {
            Map<String, List<List<String>>> closureSetForSymbol = closure(symbol_after_dot);
            for (String nonTerminal : closureSetForSymbol.keySet()) {
                List<List<String>> rules = closureSetForSymbol.get(nonTerminal);
                if(rules.size() > 0){
                    if(gotoSets.containsKey(nonTerminal)){
                        gotoSets.get(nonTerminal).addAll(rules);
                    }else{
                        gotoSets.put(nonTerminal, rules);
                    }
                }
            }
        }

        return gotoSets;
    }

    public void generateItemSets() {
        // Prepare I0
        preapareFirstSet();
        System.out.println("Item Sets > "+itemSets);
        // Create list of symbols
        HashSet<String> symbols = new HashSet<>();
        for (String nonTerminal : production_rules.keySet()) {
            symbols.add(nonTerminal);
            for (List<String> rule : production_rules.get(nonTerminal)) {
                symbols.addAll(rule);
            }
        }
        // Generate item sets
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(0);
        System.out.println(itemSets);

        while (!queue.isEmpty()) {
            int itemSetId = queue.poll();
            // System.out.println("Item Set Id > "+itemSetId);
            Map<String, List<List<String>>> itemSetMap = itemSets.get(itemSetId);
            
            for (String symbol : symbols) {
                Map<String, List<List<String>>> gotoSet = gotoSet(itemSetMap, symbol);
                System.out.println("ID > "+itemSetId+" Symbol > "+symbol+" Goto Set > "+gotoSet);

                if (gotoSet.size() == 0) {
                    continue;
                }
                itemSets.put(itemSets.size(), gotoSet);
                queue.offer(itemSets.size() - 1);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        // ? Read the grammar from CFG.txt
        Path fileName = Path.of("./CFG_test_2.txt");
        SLR slr = new SLR();
        SLR.START_SYMBOL = "E";
        slr.readProductions(fileName);
        // slr.computeFirstPos();
        // System.out.println(slr.findFirst("S"));
        // ? Prepare followpos and firstpos
        // System.out.println(slr.findFollow("S"));
        // System.out.println(slr.findFollow("B"));
        // System.out.println(slr.findFollow("C"));
        // System.out.println(slr.findFollow("D"));
        // System.out.println(slr.findFollow("E"));
        // System.out.println(slr.findFollow("F"));

        // Prepare LR(0) items
        // System.out.println(slr.closure("E"));

        // slr.preapareFirstSet();
        slr.generateItemSets();
        // System.out.println(slr.itemSets);
        // System.out.println(slr.gotoSet(slr.itemSets.get(0), "("));
        // System.out.println(slr.itemSets);
        // slr.generateItemSets(); 
        System.out.println(slr.itemSets);

        // Prepare the parsing table

    }
}
