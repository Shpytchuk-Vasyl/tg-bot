package org.telegram.mybot.messageproccesing.handler;



import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MessageHandler extends Handler<Message> {
    @Override
    public void resolve(Message msg) {
        try {
            myBot.execute(SendMessage
                    .builder()
                    .chatId(msg.getChatId())
                    .text(msg.getText())
                    .build());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
