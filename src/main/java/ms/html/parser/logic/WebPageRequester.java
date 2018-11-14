package ms.html.parser.logic;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ms.html.parser.common.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class WebPageRequester {
    private final RestTemplate restTemplate;

    public String readWebPage(String url){
        try{
            ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
            if(responseEntity.getStatusCode() != HttpStatus.OK){
                throw new ParseException("해당 URL의 내용을 파싱할 수 없습니다.");
            }
            return responseEntity.getBody();
        }catch (Exception e){
            throw new ParseException("URL에 오류가 있습니다.");
        }
    }
}
