package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.tracker.entity.TrackerStatus;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

public class CompleteHandler extends Handler<CallbackQuery> {
    private final User user;
    private final ServiceManager serviceManager;

    public CompleteHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(CallbackQuery callbackQuery) {
        serviceManager.getTrackerService().completeDailyPlan(Long.valueOf(callbackQuery.getData()));
        SendMessage msg = TrackerHandler.getDailyPlans(user,
                NavigationHandler.getDateFromText(callbackQuery.getMessage().getText()), serviceManager);
        sender.sendEditMessage(EditMessageText.builder()
                .text(msg.getText())
                .chatId(msg.getChatId())
                .messageId(callbackQuery.getMessage().getMessageId())
                .replyMarkup((InlineKeyboardMarkup) msg.getReplyMarkup())
                .build());
    }
}
