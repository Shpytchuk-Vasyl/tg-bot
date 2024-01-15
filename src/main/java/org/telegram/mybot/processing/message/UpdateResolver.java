package org.telegram.mybot.processing.message;


import lombok.Setter;
import org.springframework.stereotype.Service;
import org.telegram.mybot.MyBot;
import org.telegram.mybot.processing.message.handler.MessageHandler;
import org.telegram.mybot.processing.message.handler.NoneHandler;
import org.telegram.mybot.processing.user.entity.Status;
import org.telegram.mybot.processing.user.entity.User;
import org.telegram.mybot.processing.user.service.UserService;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Setter
public class UpdateResolver implements Runnable {

    private UserService userService;
    private Update update;
    private MyBot bot;

    public UpdateResolver(UserService userService, Update update, MyBot bot) {
        this.userService = userService;
        this.update = update;
        this.bot = bot;
    }

    public UpdateResolver() {}


    @Override
    public void run() {
        if(update.hasMessage()) {
            User user = userService.findByChatId(update.getMessage().getChatId());

            if(user == null) {
               new NoneHandler(new Sender(bot)).resolve(update.getMessage());
               registerUser(update);
            } else {
                new MessageHandler(new Sender(bot), user, userService).resolve(update.getMessage());

            }
        }
    }


    private void registerUser(Update update) {
        userService.registerUser(User
                .builder()
                .chatId(update.getMessage().getChatId())
                .status(Status.START)
                .userName(update.getMessage().getChat().getUserName())
                .build());
    }
}
