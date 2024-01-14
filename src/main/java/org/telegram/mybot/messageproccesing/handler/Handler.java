package org.telegram.mybot.messageproccesing.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.mybot.MyBot;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public abstract class Handler<T> {

    @Autowired
    MyBot myBot;

    public abstract void resolve(T msg) throws TelegramApiException;

}
