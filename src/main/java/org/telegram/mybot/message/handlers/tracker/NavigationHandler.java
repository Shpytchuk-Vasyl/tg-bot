package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.*;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.time.LocalDate;
import java.util.regex.*;


public class NavigationHandler extends Handler<CallbackQuery> {
    private final User user;
    private final ServiceManager serviceManager;
    public static Pattern pattern = Pattern.compile("\\d{2,4}-\\d{1,2}-\\d{1,2}");

    public NavigationHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(CallbackQuery callbackQuery) {
        LocalDate date = getNextDateFromCallback(callbackQuery);
        SendMessage msg = TrackerHandler.getDailyPlans(user, date, serviceManager);
        sender.sendEditMessage(EditMessageText.builder()
                        .text(msg.getText())
                        .chatId(msg.getChatId())
                        .messageId(callbackQuery.getMessage().getMessageId())
                        .replyMarkup((InlineKeyboardMarkup) msg.getReplyMarkup())
                        .build());
    }

    private LocalDate getNextDateFromCallback(CallbackQuery callbackQuery) {
        LocalDate nowDate = getDateFromText(callbackQuery.getMessage().getText());

        return switch (callbackQuery.getData()) {
            case TrackerHandler.BACKWARD -> nowDate.minusDays(1);
            case TrackerHandler.FORWARD -> nowDate.plusDays(1);
            default -> LocalDate.now();
        };

    }

    public static LocalDate getDateFromText(String text) {
        Matcher matcher = pattern.matcher(text);
        return matcher.find()
                ? LocalDate.parse(text.substring(matcher.start(), matcher.end()))
                : LocalDate.now();
    }


}
