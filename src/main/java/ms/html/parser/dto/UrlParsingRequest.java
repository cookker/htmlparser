package ms.html.parser.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@AllArgsConstructor(staticName = "of")
public class UrlParsingRequest {
    private String url;
    private ParsingContentType type;
    private int bundleCount;

}
