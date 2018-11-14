package ms.html.parser.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class BundleResult {
    private List<String> quotientList;
    private String textOnlyContent = "";
    private String remainder = "";
}
