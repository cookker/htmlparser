package ms.html.parser.logic;


import org.junit.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class AlphabetComparatorTest {


    @Test
    public void 알파벳정렬(){
        String collect = Arrays.stream("aAbbbbCB".split(""))
                .sorted(SortingAlgorithm.ALPHABET.getComparator())
                .collect(Collectors.joining());

        assertThat(collect).isEqualTo("AaBbbbbC");
    }

    @Test
    public void 숫자정렬(){
        String collect = Arrays.stream("112003".split(""))
                .sorted(SortingAlgorithm.NUMBER.getComparator())
                .collect(Collectors.joining());
        assertThat(collect).isEqualTo("001123");
    }

}