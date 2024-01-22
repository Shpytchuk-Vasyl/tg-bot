package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.message.handlers.NoneHandler;
import org.telegram.mybot.tracker.entity.TrackerStatus;
import org.telegram.mybot.tracker.entity.UserStatus;
import org.telegram.mybot.user.entity.Status;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class ExitHandler extends Handler<CallbackQuery> {
    private final User user;
    private final ServiceManager serviceManager;

    public ExitHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(CallbackQuery callbackQuery) {
        user.setStatus(Status.START);
        serviceManager.getUserService().updateUserStatus(user);
        sender.sendEditMessage(EditMessageText
                .builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .text("The tracker has closed")
                .replyMarkup(InlineKeyboardMarkup.builder().clearKeyboard().build())
                .build());

        new NoneHandler(sender).resolve(callbackQuery.getMessage());
    }
}
