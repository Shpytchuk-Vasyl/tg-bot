package org.telegram.mybot.messageproccesing;

import org.springframework.stereotype.Service;
import org.telegram.mybot.messageproccesing.handler.MessageHandler;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class UpdateResolver implements Runnable {

    private Update update;

    public UpdateResolver() {}
    public UpdateResolver(Update update) {
        this.update = update;
    }

    @Override
    public void run() {
        if(update.hasMessage()) {
            MessageHandler messageHandler = new MessageHandler();
        };
    }
}
