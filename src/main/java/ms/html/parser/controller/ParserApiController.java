package ms.html.parser.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.html.parser.common.Response;
import ms.html.parser.dto.BundleResult;
import ms.html.parser.dto.ParsingContentType;
import ms.html.parser.dto.UrlParsingRequest;
import ms.html.parser.logic.Parser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class ParserApiController {
    private final Parser parser;

    @GetMapping("/parse")
    public Response<BundleResult> parseUrlContent(@RequestParam("url") String url,
                                @RequestParam("type") String parsingType,
                                @RequestParam("bundleCount") int bundleCount){
        return Response.success(parser.parseAndBundle(UrlParsingRequest.of(url, ParsingContentType.of(parsingType), bundleCount)));
    }
}
