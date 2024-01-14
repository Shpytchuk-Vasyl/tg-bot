package org.telegram.mybot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.mybot.processing.message.UpdateResolver;
import org.telegram.mybot.processing.user.service.UserService;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class MyBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String name;
    @Autowired
    UserService userService;


    public MyBot(@Value("${telegram.bot.token}") String token) {
        super(token);
    }

    @Override
    public void onUpdateReceived(Update update) {
      new Thread(new UpdateResolver(userService, update, this )).start();
    }

    @Override
    public String getBotUsername() {
        return name;
    }



}