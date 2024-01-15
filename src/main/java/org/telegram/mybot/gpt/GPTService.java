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
    private static final String apiUrl = "https://api.openai.com/v1/chat/completions";
    private static final  String model = "gpt-3.5-turbo-1106";

    public GPTService() {}


    public String askGPT(String text) {
        return getChatCPTResponse(text).getChoices().get(0).getMessage().getContent();
    }
    private GPTResponse getChatCPTResponse(String prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        GPTRequest chatGPTRequest = new GPTRequest();

        chatGPTRequest.setModel(model);
        chatGPTRequest.setMessages(List.of(new Message("user", prompt)));

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<GPTRequest> request = new HttpEntity<>(chatGPTRequest, headers);

        return restTemplate.postForObject(apiUrl, request, GPTResponse.class);
    }
}