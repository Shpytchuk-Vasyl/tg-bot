package org.telegram.mybot.gpt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.mybot.gpt.memory.service.GPTMemoryService;
import org.telegram.mybot.processing.message.ResourceForCommands;
import org.telegram.mybot.processing.user.entity.User;

import java.util.List;

@Service
public class GPTService {

    @Value("${gpt.key}")
    private String apiKey;
    private static final String URL = "https://api.openai.com/v1/chat/completions";
    private static final  String MODEL = "gpt-3.5-turbo-1106";

    @Autowired
    private GPTMemoryService memoryService;

    public String askGPT(User user, String text) {
        if(text.equals(ResourceForCommands.CLEAR_GPT_CHAT))
            return clearContext(user);
        String question = memoryService.getContext(user) + text;
        GPTResponse content = getChatCPTResponse(question);
        String answer = content.getChoices().get(0).getMessage().getContent();
        memoryService.saveContext(user,text, answer);
        return answer + "\ntokens:" + content.getUsage().getTotal_tokens() ;
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

    private String clearContext(User user) {
        memoryService.clearContext(user);
        return "Context cleared successfully";
    }
}