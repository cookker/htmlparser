package ms.html.parser.logic;

import ms.html.parser.dto.AlphabetAndNumberPair;
import ms.html.parser.dto.BundleResult;
import ms.html.parser.logic.purifier.Purifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ParserTest {
    @MockBean
    WebPageRequester webPageRequester;
    @MockBean
    List<Purifier> purifiers;
    @Autowired
    Parser parser;

    @Test
    public void 숫자와영문자만추출() {
        assertThat(parser.removeSpecialCharacter("김#09aaAbc78!?").getAlphabetText()).isEqualTo("aaAbc");
        assertThat(parser.removeSpecialCharacter("김#09aaAbc78!?").getNumberText()).isEqualTo("0978");
    }

    @Test
    public void 숫자문자정렬() {
        AlphabetAndNumberPair pair = parser.removeSpecialCharacter("김#09aaAbc78!?");
        AlphabetAndNumberPair sortedPair = parser.sort(pair);

        assertThat(sortedPair.getNumberText()).isEqualTo("0789");
        assertThat(sortedPair.getAlphabetText()).isEqualTo("Aaabc");
    }

    @Test
    public void 숫자문자정렬_숫자만있는경우() {
        AlphabetAndNumberPair pair = parser.removeSpecialCharacter("김#0978!?");
        AlphabetAndNumberPair sortedPair = parser.sort(pair);

        assertThat(sortedPair.getNumberText()).isEqualTo("0789");
        assertThat(sortedPair.getAlphabetText()).isEqualTo("");
    }

    @Test
    public void 숫자문자정렬_문자만있는경우() {
        AlphabetAndNumberPair pair = parser.removeSpecialCharacter("김#aaAbc!?");
        AlphabetAndNumberPair sortedPair = parser.sort(pair);

        assertThat(sortedPair.getNumberText()).isEqualTo("");
        assertThat(sortedPair.getAlphabetText()).isEqualTo("Aaabc");
    }

    @Test
    public void 특수문자제거_정렬_섞기(){
        AlphabetAndNumberPair pair = parser.removeSpecialCharacter("http://localhost:8080/dummy");
        AlphabetAndNumberPair sortedPair = parser.sort(pair);
        List<String> alphabetList = sortedPair.getAlphabetList();
        List<String> numberList = sortedPair.getNumberList();
        String mix = parser.mix(AlphabetAndNumberPair.of(alphabetList, numberList));
        System.out.println(mix);

    }
    @Test
    public void 영문숫자순으로섞기() {
        List<String> alphabetList = new ArrayList<>();
        List<String> numberList = new ArrayList<>();
        alphabetList.add("A");
        alphabetList.add("a");
        alphabetList.add("a");
        alphabetList.add("a");
        alphabetList.add("b");
        alphabetList.add("c");
        numberList.add("0");
        numberList.add("7");
        numberList.add("8");
        numberList.add("9");

        String mix = parser.mix(AlphabetAndNumberPair.of(alphabetList, numberList));
        assertThat(mix).isEqualTo("A0a7a8a9bc");
    }

    @Test
    public void 영문숫자순으로섞기_문자만있는경우() {
        List<String> alphabetList = new ArrayList<>();
        List<String> numberList = new ArrayList<>();
        alphabetList.add("A");
        alphabetList.add("a");
        alphabetList.add("a");
        alphabetList.add("a");
        alphabetList.add("b");
        alphabetList.add("c");

        String mix = parser.mix(AlphabetAndNumberPair.of(alphabetList, numberList));
        assertThat(mix).isEqualTo("Aaaabc");
    }

    @Test
    public void 영문숫자순으로섞기_숫자만있는경우() {
        List<String> alphabetList = new ArrayList<>();
        List<String> numberList = new ArrayList<>();
        numberList.add("0");
        numberList.add("7");
        numberList.add("8");
        numberList.add("9");

        String mix = parser.mix(AlphabetAndNumberPair.of(alphabetList, numberList));
        assertThat(mix).isEqualTo("0789");
    }

    @Test
    public void 묶음처리_컨텐츠개수보다작은경우() {
        BundleResult bundleResult = parser.bundling(3, "A0a7a9b9c");
        assertThat(bundleResult.getQuotientList().size()).isEqualTo(3);
        assertThat(bundleResult.getRemainder()).isBlank();
    }

    @Test
    public void 묶음처리_컨텐츠개수보다작은경우_나머지가있는경우() {
        BundleResult bundleResult = parser.bundling(4, "A0a7a9b9c");
        assertThat(bundleResult.getQuotientList().size()).isEqualTo(2);
        assertThat(bundleResult.getRemainder()).isEqualTo("c");
    }

    @Test
    public void 묶음처리_컨텐츠개수와같은경우() {
        BundleResult bundleResult = parser.bundling(9, "A0a7a9b9c");
        assertThat(bundleResult.getQuotientList().size()).isEqualTo(1);
        assertThat(bundleResult.getRemainder()).isBlank();
    }

    @Test
    public void 묶음처리_컨텐츠개수보다큰경우() {
        BundleResult bundleResult = parser.bundling(1000, "A0a7a9b9c");
        assertThat(bundleResult.getQuotientList().size()).isEqualTo(0);
        assertThat(bundleResult.getRemainder()).isEqualTo("A0a7a9b9c");
    }
}