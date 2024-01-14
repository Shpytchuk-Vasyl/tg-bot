package org.telegram.mybot.processing.message.handler;

import org.telegram.mybot.processing.message.KeyBoardButtons;
import org.telegram.mybot.processing.message.Sender;
import org.telegram.mybot.processing.user.entity.Status;
import org.telegram.mybot.processing.user.entity.User;
import org.telegram.mybot.processing.user.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

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

        if(msg.getText().equalsIgnoreCase(KeyBoardButtons.MENU))
            user.setStatus(Status.NONE);

        switch (user.getStatus()) {
            case START -> {
                new StartHandler(sender, user, userService).resolve(msg);
            }
            case FIND_VACANCIES -> {
                break;
            }
            case HABITS_TRACKER -> {

            }
            case SPEAK_WITH_JPT -> {


            } case RECOGNIZE_SPEECH -> {

            } case NONE -> {
                new NoneHandler(sender).resolve(msg);
                user.setStatus(Status.START);
                userService.updateUserStatus(user);
            }
        }
    }

}
