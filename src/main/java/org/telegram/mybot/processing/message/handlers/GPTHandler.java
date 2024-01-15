package org.telegram.mybot.processing.message.handlers;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.processing.message.Handler;
import org.telegram.mybot.processing.message.Sender;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class GPTHandler extends Handler<Message> {
    private final ServiceManager serviceManager;
    public GPTHandler(Sender sender, ServiceManager serviceManager) {
        super(sender);
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(Message msg) {
        String text = serviceManager.getGptService().askGPT(msg.getText());
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .text(text)
                .build());
    }
}
