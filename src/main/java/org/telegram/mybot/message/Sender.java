package org.telegram.mybot.message;

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

    public void sendMessage(SendMessage msg) {
       try{
            bot.execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
