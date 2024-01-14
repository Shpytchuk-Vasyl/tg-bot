package org.telegram.mybot.processing.message.handler;

import org.telegram.mybot.processing.message.KeyBoardButtons;
import org.telegram.mybot.processing.message.Sender;
import org.telegram.mybot.processing.user.entity.Status;
import org.telegram.mybot.processing.user.entity.User;
import org.telegram.mybot.processing.user.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Message;

public class MessageHandler extends Handler<Message> {
    User user;
    UserService userService;

    public MessageHandler(Sender sender, User user, UserService userService) {
        super(sender);
        this.user = user;
        this.userService = userService;
    }

    @Override
    public void resolve(Message msg) {

        if(msg.hasText() && msg.getText().equalsIgnoreCase(KeyBoardButtons.MENU))
            user.setStatus(Status.NONE);

        switch (user.getStatus()) {
            case START -> {
                new StartHandler(sender, user, userService).resolve(msg);
            }
            case VACANCIES -> {
                break;
            }
            case TRACKER -> {

            }
            case JPT -> {


            } case SPEECH -> {
                if(msg.hasVoice())
                    new VoiceHandler(sender).resolve(msg);
                else {
                    user.setStatus(Status.NONE);
                    this.resolve(msg);
                }

            } case NONE -> {
                new NoneHandler(sender).resolve(msg);
                user.setStatus(Status.START);
                userService.updateUserStatus(user);
            }
        }
    }

}
