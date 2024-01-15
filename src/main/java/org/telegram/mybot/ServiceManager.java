package org.telegram.mybot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.mybot.gpt.GPTService;
import org.telegram.mybot.processing.user.service.UserService;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceManager {
    @Autowired
    private UserService userService;
    @Autowired
    private GPTService gptService;
}
