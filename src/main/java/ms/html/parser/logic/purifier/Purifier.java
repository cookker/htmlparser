package ms.html.parser.logic.purifier;

import ms.html.parser.dto.ParsingContentType;

public interface Purifier {
    ParsingContentType getContentType();
    String purifyContent(String url);
}
