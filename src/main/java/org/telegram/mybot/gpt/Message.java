package org.telegram.mybot.gpt;

import lombok.*;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Message{
    private String role;
    private String content;

}
