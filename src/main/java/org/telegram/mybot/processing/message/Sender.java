package org.telegram.mybot.processing.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.mybot.MyBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class Sender {

    private final MyBot bot;

    public Sender(MyBot bot) {
        this.bot = bot;
    }

    public void sendMessage(SendMessage msg) throws TelegramApiException {
        bot.execute(msg);
    }

}
