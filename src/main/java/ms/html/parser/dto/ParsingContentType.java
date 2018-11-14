package ms.html.parser.dto;

import java.util.Arrays;

public enum ParsingContentType {
    TXT,
    HTML,
    ;
    public static ParsingContentType of(String typeName){
        return Arrays.stream(ParsingContentType.values())
                .filter(e -> e.name().equalsIgnoreCase(typeName))
                .findAny()
                .orElse(TXT);
    }
}
