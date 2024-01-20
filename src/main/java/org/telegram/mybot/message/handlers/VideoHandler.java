package org.telegram.mybot.message.handlers;

import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.recognizer.Recognizer;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;


public class VideoHandler extends Handler<Message> {
    public VideoHandler(Sender sender) {
        super(sender);
    }

    @Override
    public void resolve(Message msg) {
        String text = new Recognizer().analyzeVideo(msg.getVideoNote());
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .text(text)
                .build());
    }
}