package org.telegram.mybot.message.handlers;


import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.*;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class VacancyHandler extends Handler<Message> {

    private final ServiceManager serviceManager;

    public VacancyHandler(Sender sender, ServiceManager serviceManager) {
        super(sender);
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(Message msg) {
        if(serviceManager.getVacancyParser().getCategories().contains(msg.getText())) {
            serviceManager.getVacancyParser()
                    .getVacancyHTML(msg.getText())
                    .forEach(vacancy -> sender.sendMessage(SendMessage
                            .builder()
                            .chatId(msg.getChatId())
                            .text(vacancy.toString())
                            .parseMode("HTML")
                            .build()));
        } else {
            sender.sendMessage(SendMessage
                    .builder()
                    .chatId(msg.getChatId())
                    .text("Incorrect category!!!")
                    .build());
        }

    }
}
