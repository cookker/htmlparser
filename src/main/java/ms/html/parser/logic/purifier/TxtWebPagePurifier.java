package ms.html.parser.logic.purifier;

import lombok.RequiredArgsConstructor;
import ms.html.parser.dto.ParsingContentType;
import ms.html.parser.logic.WebPageRequester;
import org.springframework.stereotype.Component;

/**
 * 웹페이지 전체 데이터에 대해 정제하는 클래스입니다.
 */
@Component
@RequiredArgsConstructor
public class TxtWebPagePurifier implements Purifier {
    private final WebPageRequester webPageRequester;

    @Override
    public ParsingContentType getContentType() {
        return ParsingContentType.TXT;
    }

    @Override
    public String purifyContent(String url) {
        return webPageRequester.readWebPage(url);
    }
}
