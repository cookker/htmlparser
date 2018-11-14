package ms.html.parser.logic;

import java.util.Comparator;

public enum SortingAlgorithm {
    NUMBER(String::compareTo),
    ALPHABET((o1, o2) -> {
        int ignoreCase = o1.compareToIgnoreCase(o2);
        if(ignoreCase == 0){
            return o1.compareTo(o2);
        }
        return ignoreCase;
    }),
    ;
    private Comparator<String> comparator;

    SortingAlgorithm(Comparator<String> comparator) {
        this.comparator = comparator;
    }

    public Comparator<String> getComparator() {
        return comparator;
    }
}
