package ms.html.parser.controller;


import ms.html.parser.logic.Parser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest(ParserApiController.class)
public class PurifierApiControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    Parser parser;

    @Test
    public void parserApiTest() throws Exception {
        String url = "/parse?url=http://www.google.com&type=txt&bundleCount=3";
        mockMvc.perform(get(url))
                .andDo(print());
    }
}