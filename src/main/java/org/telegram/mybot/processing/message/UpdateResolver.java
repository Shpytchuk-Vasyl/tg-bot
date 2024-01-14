package org.telegram.mybot.processing.message;


import org.springframework.stereotype.Service;
import org.telegram.mybot.MyBot;
import org.telegram.mybot.processing.message.handler.MessageHandler;

import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateResolver implements Runnable {

    private Update update;

    public UpdateResolver(Update update, MyBot bot) {
        this.update = update;
        this.bot = bot;
    }

    private MyBot bot;

    public UpdateResolver() {}


    @Override
    public void run() {
        if(update.hasMessage()) {
            MessageHandler handler = new MessageHandler(new Sender(bot));
            handler.resolve(update.getMessage());
        }
    }
}
