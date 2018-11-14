package ms.html.parser.logic;


import ms.html.parser.logic.purifier.Purifier;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class HtmlWebPagePurifierTest {

    @Autowired
    Purifier htmlWebPagePurifier;

    private String googleHtml = "Google";

    @Test
    public void html정제_테스트(){
        String html = "http://www.google.com";
        String content = htmlWebPagePurifier.purifyContent(html);
        assertThat(content).startsWith(googleHtml);
    }

}