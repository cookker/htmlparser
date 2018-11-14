package ms.html.parser.logic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.html.parser.common.ParseException;
import ms.html.parser.dto.AlphabetAndNumberPair;
import ms.html.parser.dto.BundleResult;
import ms.html.parser.dto.UrlParsingRequest;
import ms.html.parser.logic.purifier.Purifier;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class Parser {
    private static final String REGEX_NOT_ALPHABET_AND_NUMBER = "[^0-9a-zA-Z]";
    private final List<Purifier> purifierList;

    AlphabetAndNumberPair removeSpecialCharacter(String contentWithSpecialCharacters){
        AlphabetAndNumberPair alphabetAndNumberPair = new AlphabetAndNumberPair();
        final String numberAndAlphabetString = contentWithSpecialCharacters.replaceAll(REGEX_NOT_ALPHABET_AND_NUMBER, "");

        numberAndAlphabetString.chars().forEach( e -> {
            if(isNumber(e)){
                alphabetAndNumberPair.addNumber(String.valueOf((char)e));
            }else{
                alphabetAndNumberPair.addAlphabet(String.valueOf((char)e));
            }
        });
        return alphabetAndNumberPair;
    }

    private boolean isNumber(int e) {
        return e >= '0' && e <= '9';
    }

    AlphabetAndNumberPair sort(final AlphabetAndNumberPair alphabetAndNumberPair){
        List<String> sortedAlphabets = alphabetAndNumberPair.getAlphabetList()
                .stream()
                .sorted(SortingAlgorithm.ALPHABET.getComparator())
                .collect(Collectors.toList());

        List<String> sortedNumbers = alphabetAndNumberPair.getNumberList()
                .stream()
                .sorted(SortingAlgorithm.NUMBER.getComparator())
                .collect(Collectors.toList());

        return AlphabetAndNumberPair.of(sortedAlphabets, sortedNumbers);
    }

    String mix(final AlphabetAndNumberPair alphabetAndNumberPair){
        List<String> alphabetList = alphabetAndNumberPair.getAlphabetList();
        List<String> numberList = alphabetAndNumberPair.getNumberList();

        final int totalSize = alphabetAndNumberPair.size();
        StringBuilder stringBuilder = new StringBuilder(totalSize);

        int alphabetIndex = 0, numberIndex = 0;
        boolean enableAppendAlphabet = alphabetList.size() > 0;
        boolean enableAppendNumber = numberList.size() > 0;

        for(;enableAppendAlphabet || enableAppendNumber; alphabetIndex++, numberIndex++){
            if(enableAppendAlphabet){
                stringBuilder.append(alphabetList.get(alphabetIndex));
            }
            if(enableAppendNumber){
                stringBuilder.append(numberList.get(numberIndex));
            }

            enableAppendAlphabet = alphabetIndex < alphabetList.size() - 1;
            enableAppendNumber = numberIndex < numberList.size() - 1;
        }

        return stringBuilder.toString();
    }

    BundleResult bundling(final int bundleCount, final String content){
        BundleResult bundleResult = new BundleResult();
        List<String> bundleList = new ArrayList<>();

        final int length = content.length();
        int quotientOffset = length / bundleCount;
        int remainderCount = length - bundleCount * quotientOffset;
        char[] contentCharArray = content.toCharArray();

        int startIndex = 0;
        for(int i = 0 ; i < quotientOffset ; i++, startIndex += bundleCount){
            char[] chars = Arrays.copyOfRange(contentCharArray, startIndex, startIndex + bundleCount);
            bundleList.add(new String(chars));
        }

        bundleResult.setQuotientList(bundleList);

        if(remainderCount > 0){
            char[] remainders = Arrays.copyOfRange(contentCharArray, startIndex, length);
            bundleResult.setRemainder(new String(remainders));
        }

        return bundleResult;
    }

    public BundleResult parseAndBundle(final UrlParsingRequest urlParsingRequest){
        Purifier purifier = purifierList.stream().filter(p -> p.getContentType() == urlParsingRequest.getType())
                .findAny()
                .orElseThrow(() -> new ParseException("정의하지 않은 타입입니다."));

        String purifiedContent = purifier.purifyContent(urlParsingRequest.getUrl());
        log.info("purifiedContent:{}", purifiedContent);
        AlphabetAndNumberPair removedSpecialCharacterContent = this.removeSpecialCharacter(purifiedContent);
        log.info("removedSpecialCharacterContent, alphabet:{}", removedSpecialCharacterContent.getAlphabetText());
        log.info("removedSpecialCharacterContent,   number:{}", removedSpecialCharacterContent.getNumberText());
        AlphabetAndNumberPair sortedContent = this.sort(removedSpecialCharacterContent);
        log.info("sortedContent, alphabet:{}", sortedContent.getAlphabetText());
        log.info("sortedContent,   number:{}", sortedContent.getNumberText());
        String mixedContent = this.mix(sortedContent);
        log.info("mixedContent:{}", mixedContent);
        BundleResult bundleResult = this.bundling(urlParsingRequest.getBundleCount(), mixedContent);
        bundleResult.setTextOnlyContent(removedSpecialCharacterContent.getAlphabetText() + removedSpecialCharacterContent.getNumberText());
        return bundleResult;
    }
}
