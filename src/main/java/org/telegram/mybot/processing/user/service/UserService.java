package org.telegram.mybot.processing.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.mybot.processing.user.entity.User;
import org.telegram.mybot.processing.user.repository.UserRepository;

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
