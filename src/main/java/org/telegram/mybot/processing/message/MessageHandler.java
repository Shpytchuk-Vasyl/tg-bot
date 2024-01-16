package org.telegram.mybot.processing.message;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.processing.message.handlers.*;
import org.telegram.mybot.processing.user.entity.Status;
import org.telegram.mybot.processing.user.entity.User;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageHandler extends Handler<Message> {
    private final User user;
    private final ServiceManager serviceManager;

    public MessageHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(Message msg) {

        if(msg.hasText() && msg.getText().equalsIgnoreCase(KeyBoardButtons.MENU))
            user.setStatus(Status.NONE);

        switch (user.getStatus()) {
            case START -> new StartHandler(sender, user, serviceManager).resolve(msg);
            case VACANCIES -> {
                break;
            }
            case TRACKER -> {

            }
            case GPT -> new GPTHandler(sender, serviceManager, user).resolve(msg);
            case SPEECH -> {
                if(msg.hasVoice())
                    new VoiceHandler(sender).resolve(msg);
                else if(msg.hasVideoNote())
                    new VideoHandler(sender).resolve(msg);
                else {
                    user.setStatus(Status.NONE);
                    this.resolve(msg);
                }

            } case NONE -> {
                new NoneHandler(sender).resolve(msg);
                user.setStatus(Status.START);
                serviceManager.getUserService().updateUserStatus(user);
            }
        }
    }

}
