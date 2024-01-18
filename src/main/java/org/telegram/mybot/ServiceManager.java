package org.telegram.mybot;

import lombok.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.mybot.gpt.GPTService;
import org.telegram.mybot.processing.user.service.UserService;
import org.telegram.mybot.vacancy.VacancyParser;

@Service
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceManager {
    @Autowired
    private UserService userService;
    @Autowired
    private GPTService gptService;
    @Autowired
    private VacancyParser vacancyParser;

}
