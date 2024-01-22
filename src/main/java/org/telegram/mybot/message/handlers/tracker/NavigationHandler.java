package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.tracker.entity.TrackerStatus;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NavigationHandler extends Handler<CallbackQuery> {
    private final User user;
    private final ServiceManager serviceManager;

    public NavigationHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(CallbackQuery callbackQuery) {
        LocalDate date = getNextDateFromCallback(callbackQuery);
        sender.sendMessage(TrackerHandler.getDailyPlans(user, date, serviceManager));
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
        Matcher matcher = Pattern.compile("\\d{2,4}-\\d{1,2}-\\d{1,2}").matcher(text);
        return matcher.find()
                ? LocalDate.parse(text.substring(matcher.start(), matcher.end()))
                : LocalDate.now();
    }


}
