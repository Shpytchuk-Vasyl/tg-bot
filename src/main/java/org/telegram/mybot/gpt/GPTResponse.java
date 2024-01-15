package org.telegram.mybot.gpt;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GPTResponse {
    private String id;
    private String object;
    private int created;
    private List<Choice> choices;
    private Usage usage;

}

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Choice {
    private int index;
    private Message message;
    private String finish_reason;

}

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;


}
