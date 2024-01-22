package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.tracker.entity.TrackerStatus;
import org.telegram.mybot.tracker.entity.UserStatus;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        LocalDate date = NavigationHandler.getDateFromText(callbackQuery.getMessage().getText());

        if (userStatus.getTrackerStatus() == TrackerStatus.VIEW) {
            userStatus.setTrackerStatus(TrackerStatus.EDIT);
            serviceManager.getTrackerService().setUserStatus(userStatus);

            sender.sendEditMessage(EditMessageText
                    .builder()
                    .chatId(callbackQuery.getMessage().getChatId())
                    .messageId(callbackQuery.getMessage().getMessageId())
                    .text("Example 1: \n" +
                            "Date: " + date.format(DateTimeFormatter.ISO_DATE) + "\n" +
                            "do something 9:00 - 12:00\n" +
                            "do homework.\n" +
                            "...\n" +
                            "Example 2: \n" +
                            "Date: " + LocalDate.now().format(DateTimeFormatter.ISO_DATE) + "\n" +
                            "draw a picture.\n\n" +
                            "Write each new subsection on a new line. " +
                            "If you want to specify a date, then follow the format given in the example. " +
                            "If no date is specified, new items will be added to today's plan. " +
                            "Send a message and click on the button below"
                    )
                    .replyMarkup(InlineKeyboardMarkup.builder()
                            .clearKeyboard()
                            .keyboardRow(List.of(InlineKeyboardButton.builder()
                                    .text(TrackerHandler.ADD)
                                    .callbackData(TrackerHandler.ADD)
                                    .build()))
                            .build())
                    .build());
        } else if (userStatus.getTrackerStatus() == TrackerStatus.EDIT){
            userStatus.setTrackerStatus(TrackerStatus.VIEW);
            serviceManager.getTrackerService().setUserStatus(userStatus);
            SendMessage msg = TrackerHandler.getDailyPlans(user, date, serviceManager);
            sender.sendEditMessage(EditMessageText.builder()
                    .text(msg.getText())
                    .chatId(msg.getChatId())
                    .messageId(callbackQuery.getMessage().getMessageId())
                    .replyMarkup((InlineKeyboardMarkup) msg.getReplyMarkup())
                    .build());
        }
    }
}
