package ms.html.parser.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor
public class AlphabetAndNumberPair {
    private List<String> alphabetList = new ArrayList<>();
    private List<String> numberList = new ArrayList<>();

    public static AlphabetAndNumberPair of(List<String> alphabetList, List<String> numberList) {
        AlphabetAndNumberPair pair = new AlphabetAndNumberPair();
        pair.setAlphabetList(alphabetList);
        pair.setNumberList(numberList);
        return pair;
    }

    public void addAlphabet(String alphabet){
        alphabetList.add(alphabet);
    }

    public void addNumber(String number){
        numberList.add(number);
    }

    public int size(){
        return alphabetList.size() + numberList.size();
    }

    public String getAlphabetText() {
        return String.join("", alphabetList);
    }

    public String getNumberText(){
        return String.join("", numberList);
    }
}
