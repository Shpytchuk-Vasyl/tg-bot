package org.telegram.mybot.message.handlers;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public class GPTHandler extends Handler<Message> {
    private final ServiceManager serviceManager;
    private final User user;
    public GPTHandler(Sender sender, ServiceManager serviceManager, User user) {
        super(sender);
        this.serviceManager = serviceManager;
        this.user = user;
    }

    @Override
    public void resolve(Message msg) {
        String text = serviceManager.getGptService().askGPT(user, msg.getText());
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .text(text)
                .build());
    }
}
