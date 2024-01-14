package org.telegram.mybot.processing.message.handler;



import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.mybot.MyBot;
import org.telegram.mybot.processing.message.Sender;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageHandler extends Handler<Message> {


    public MessageHandler(Sender sender) {
        super(sender);
    }

    @Override
    public void resolve(Message msg) {
        try {
            sender.sendMessage(SendMessage
                    .builder()
                    .chatId(msg.getChatId())
                    .text(msg.getText())
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
