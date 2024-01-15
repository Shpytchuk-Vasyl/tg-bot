package org.telegram.mybot.gpt;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GPTResponse {
    private String id;
    private String object;
    private int created;
    private List<Choice> choices;
    private Usage usage;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Choice {
    private int index;
    private Message message;
    private String finish_reason;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
class Usage {
    private int prompt_tokens;
    private int completion_tokens;
    private int total_tokens;


}
