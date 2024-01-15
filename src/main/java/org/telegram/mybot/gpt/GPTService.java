package org.telegram.mybot.gpt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GPTService {

    @Value("${gpt.key}")
    private String apiKey;
    private static final String URL = "https://api.openai.com/v1/chat/completions";
    private static final  String MODEL = "gpt-3.5-turbo-1106";

    public String askGPT(String text) {
        GPTResponse content = getChatCPTResponse(text);
        System.out.println(content.toString());
        return content.getChoices().get(0).getMessage().getContent();
    }
    private GPTResponse getChatCPTResponse(String prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        GPTRequest chatGPTRequest = GPTRequest
                .builder()
                .model(MODEL)
                .messages(List.of(new Message("user", prompt)))
                .build();

        HttpEntity<GPTRequest> request = new HttpEntity<>(chatGPTRequest, headers);

        return new RestTemplate().postForObject(URL, request, GPTResponse.class);
    }
}