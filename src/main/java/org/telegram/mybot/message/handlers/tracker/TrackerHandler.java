package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.*;
import org.telegram.mybot.tracker.entity.*;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class TrackerHandler extends Handler<Message> {
    private final User user;
    private final ServiceManager serviceManager;

    public static final String ADD = "Add new";
    public static final String BACK = "Back";


    public TrackerHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(Message msg) {
        UserStatus status = serviceManager.getTrackerService().getUserStatus(user);
        switch (status.getTrackerStatus()) {
            case NONE -> {
                if(ResourceForCommands.getTrackerStartKeyBoardButtons().contains(msg.getText())) {
                    if(msg.getText().contains("excel")) {
                        sendExcel(msg);
                    } else {
                        sendDailyPlans(msg);
                        status.setTrackerStatus(TrackerStatus.VIEW);
                        serviceManager.getTrackerService().setUserStatus(status);
                    }
                }
            }
            case VIEW -> {}
            case CREATE -> {}
            case EDIT -> {}
        }


    }


    private void sendExcel(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .text("Please choose available function :")
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboardRow(new KeyboardRow(
                                List.of(
                                        new KeyboardButton(ResourceForCommands.getTrackerStartKeyBoardButtons().get(0)),
                                        new KeyboardButton(ResourceForCommands.getTrackerStartKeyBoardButtons().get(1))
                                )
                        ))
                        .keyboardRow(new KeyboardRow(
                                List.of(
                                        new KeyboardButton(ResourceForCommands.MENU)
                                )))
                        .resizeKeyboard(true)
                        .oneTimeKeyboard(true)
                        .build()
                )
                .build());
    }

    private void sendDailyPlans(Message msg) {
        List<DailyPlan> plans = serviceManager.getTrackerService().getDailyPlan(user, LocalDate.now());
        if(plans.isEmpty()) {
            sender.sendMessage(SendMessage
                    .builder()
                    .chatId(msg.getChatId())
                    .text("You haven't the plans for today. Please, add new plans.")
                    .replyMarkup(InlineKeyboardMarkup.builder()
                            .keyboardRow(List.of(InlineKeyboardButton.builder().text(ADD).callbackData(ADD).build()))
                            .keyboardRow(List.of(InlineKeyboardButton.builder().text(BACK).callbackData(BACK).build()))
                            .build())
                    .build());
        } else {

           String completed =  plans.stream()
                    .filter(plan -> plan.getRecord().getIsComplete())
                    .map(plan -> plan.getRecord().getPlan())
                    .collect(Collectors.joining("\n"));

           List<List<InlineKeyboardButton>> buttons = (plans.stream()
                   .filter(plan -> !plan.getRecord().getIsComplete())
                   .map(plan -> List.of(InlineKeyboardButton.builder()
                           .text(plan.getRecord().getPlan())
                           .callbackData(plan.getId().toString())
                           .build())
                   )
                   .toList());

            buttons.add(List.of(InlineKeyboardButton.builder().text(ADD).callbackData(ADD).build()));
            buttons.add(List.of(InlineKeyboardButton.builder().text(BACK).callbackData(BACK).build()));

            sender.sendMessage(SendMessage
                    .builder()
                    .chatId(msg.getChatId())
                    .text("Your plans for today\n To complete a plan, click on it\n" + completed)
                    .replyMarkup(new InlineKeyboardMarkup(buttons))
                    .build());

        }
    }



}