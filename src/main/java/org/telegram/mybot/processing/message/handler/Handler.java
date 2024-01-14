package org.telegram.mybot.processing.message.handler;

import org.telegram.mybot.processing.message.Sender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


public abstract class Handler<T> {

    Sender sender;

    public Handler(Sender sender) {
        this.sender = sender;
    }

    public abstract void resolve(T msg) throws TelegramApiException;

}
