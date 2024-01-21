package org.telegram.mybot.message;


import lombok.Setter;
import org.springframework.stereotype.Service;
import org.telegram.mybot.MyBot;
import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.handlers.NoneHandler;
import org.telegram.mybot.user.entity.Status;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Setter
public class UpdateResolver implements Runnable {

    private ServiceManager serviceManager;
    private Update update;
    private MyBot bot;

    public UpdateResolver(ServiceManager serviceManager, Update update, MyBot bot) {
        this.serviceManager = serviceManager;
        this.update = update;
        this.bot = bot;
    }

    public UpdateResolver() {}


    @Override
    public void run() {
        if(update.hasMessage()) {
            User user = serviceManager.getUserService().findByChatId(update.getMessage().getChatId());

            if(user == null) {
               new NoneHandler(new Sender(bot)).resolve(update.getMessage());
               registerUser(update);

            } else {
                new MessageHandler(new Sender(bot), user, serviceManager).resolve(update.getMessage());

            }
        } else if(update.hasCallbackQuery()) {
            User user = serviceManager.getUserService().findByChatId(update.getCallbackQuery().getMessage().getChatId());
            new CallBackHandler(new Sender(bot), user, serviceManager).resolve(update.getCallbackQuery());

        }
    }


    private void registerUser(Update update) {
        serviceManager.getUserService().registerUser(User
                .builder()
                .chatId(update.getMessage().getChatId())
                .status(Status.START)
                .userName(update.getMessage().getChat().getUserName())
                .build());
    }
}
