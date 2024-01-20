package org.telegram.mybot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.mybot.message.UpdateResolver;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Component
public class MyBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.name}")
    private String name;
    @Autowired
    private ServiceManager serviceManager;
    private static final ExecutorService executorService = Executors.newFixedThreadPool(10);;
    private final String token;


    public MyBot(@Value("${telegram.bot.token}") String token) {
        super(token);
        this.token = token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        executorService.execute(new UpdateResolver(serviceManager, update, this));
        System.out.println(this);
    }

    @Override
    public String getBotUsername() {
        return name;
    }



}