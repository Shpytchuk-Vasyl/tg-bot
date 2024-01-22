package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.tracker.entity.TrackerStatus;
import org.telegram.mybot.tracker.entity.UserStatus;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.format.DateTimeFormatter;

public class EditHandler extends Handler<CallbackQuery> {
    private final User user;
    private final ServiceManager serviceManager;

    public EditHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(CallbackQuery callbackQuery) {
        UserStatus userStatus = serviceManager.getTrackerService().getUserStatus(user);
        userStatus.setTrackerStatus(TrackerStatus.EDIT);
        serviceManager.getTrackerService().setUserStatus(userStatus);

        sender.sendEditMessage(EditMessageText
                .builder()
                .chatId(callbackQuery.getMessage().getChatId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .text("Date: " + NavigationHandler.getDateFromText(callbackQuery.getMessage().getText())
                                .format(DateTimeFormatter.ISO_DATE) + "\n" +
                        "Example 1: \n" +
                        "do something 9:00 - 12:00\n" +
                        "do homework.\n" +
                        "...\n" +
                        "Example 2: \n" +
                        "draw a picture.\n\n" +
                        "Write each new subsection on a new line"
                )
                .replyMarkup(InlineKeyboardMarkup.builder().clearKeyboard().build())
                .build());
    }
}
