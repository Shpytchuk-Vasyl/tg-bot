package org.telegram.mybot.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.mybot.user.entity.User;
import org.telegram.mybot.user.repository.UserRepository;

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

    public User findByChatId(Long chatId) {
        return userRepository.findUserByChatId(chatId);
    }

    public void updateUserStatus(User user) {
        userRepository.saveAndFlush(user);
    }

    public void registerUser(User user) {
        userRepository.save(user);
    }
}
