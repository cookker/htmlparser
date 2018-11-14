package ms.html.parser.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@WebMvcTest
public class DummyControllerWebTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void 더미웹페이지내용확인() throws Exception {
        mockMvc.perform(get("/dummy"))
                .andDo(print());
    }
}