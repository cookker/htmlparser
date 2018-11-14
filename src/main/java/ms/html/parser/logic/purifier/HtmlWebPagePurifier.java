package ms.html.parser.logic.purifier;

import lombok.RequiredArgsConstructor;
import ms.html.parser.dto.ParsingContentType;
import ms.html.parser.logic.WebPageRequester;
import org.springframework.stereotype.Component;

/**
 * 파싱타입이 HTML이면 HTML 태그를 제외한 나머지 컨텐츠에 대한 내용을 정제하는 클래스입니다.
 */
@Component
@RequiredArgsConstructor
public class HtmlWebPagePurifier implements Purifier {
    private static final String REGEX_TAG = "\\<.*?>";
    private final WebPageRequester webPageRequester;

    @Override
    public ParsingContentType getContentType() {
        return ParsingContentType.HTML;
    }

    @Override
    public String purifyContent(String url) {
        String rawContent = webPageRequester.readWebPage(url);
        return rawContent.replaceAll(REGEX_TAG,"");
    }
}
