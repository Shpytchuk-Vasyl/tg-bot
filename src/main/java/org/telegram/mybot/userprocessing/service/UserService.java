package org.telegram.mybot.userprocessing.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.mybot.userprocessing.entity.User;
import org.telegram.mybot.userprocessing.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public void saveUser(String name, Long chatId) {
        userRepository.save(User.builder()
                .userName(name)
                .chatId(chatId)
                .build());
    }

}
