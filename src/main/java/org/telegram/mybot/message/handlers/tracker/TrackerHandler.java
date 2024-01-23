package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.*;
import org.telegram.mybot.tracker.entity.*;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class TrackerHandler extends Handler<Message> {
    private final User user;
    private final ServiceManager serviceManager;

    private final LocalDate localDate;

    public static final String ADD = "Add new";
    public static final String BACK = ResourceForCommands.MENU;
    public static final String FORWARD = "Forward";
    public static final String TODAY = "Today";
    public static final String BACKWARD = "Backward";

    private static final List<List<InlineKeyboardButton>> buttons = List.of(
            List.of(
                    InlineKeyboardButton.builder().text(BACKWARD).callbackData(BACKWARD).build(),
                    InlineKeyboardButton.builder().text(TODAY).callbackData(TODAY).build(),
                    InlineKeyboardButton.builder().text(FORWARD).callbackData(FORWARD).build()
            ),
            List.of(InlineKeyboardButton.builder().text(ADD).callbackData(ADD).build()),
            List.of(InlineKeyboardButton.builder().text(BACK).callbackData(BACK).build())
    );


    public TrackerHandler(Sender sender, User user, ServiceManager serviceManager, LocalDate localDate) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
        this.localDate = localDate;
    }

    @Override
    public void resolve(Message msg) {
        UserStatus status = serviceManager.getTrackerService().getUserStatus(user);
        switch (status.getTrackerStatus()) {
            case NONE -> {
                if(ResourceForCommands.getTrackerStartKeyBoardButtons().contains(msg.getText())) {
                    if(msg.getText().contains("excel")) {
                        new ExcelHandler(sender,user, serviceManager).resolve(msg);
                    } else {
                        sender.sendMessage(getDailyPlans(user, localDate, serviceManager));
                        setUserStatusView(status);
                    }
                }
            }
            case EDIT -> {

                Arrays.stream(msg.getText().split("\n"))
                        .filter(plan -> !(plan.isBlank() || NavigationHandler.pattern.matcher(plan).find()))
                        .forEach(plan -> serviceManager.getTrackerService().addNewPlan(plan, user, localDate));
            }
        }


    }


    private void setUserStatusView(UserStatus status) {
        status.setTrackerStatus(TrackerStatus.VIEW);
        serviceManager.getTrackerService().setUserStatus(status);
    }


    public static SendMessage getDailyPlans(User user, LocalDate date, ServiceManager serviceManager) {
        List<DailyPlan> plans = serviceManager.getTrackerService().getDailyPlan(user, date);


        if(plans.isEmpty()) {
            return SendMessage
                    .builder()
                    .chatId(user.getChatId())
                    .text("You haven't the plans for " +
                            date.format(DateTimeFormatter.ISO_DATE) +
                            ".\nPlease, add new plans.")

                    .replyMarkup(new InlineKeyboardMarkup(buttons))
                    .build();
        }

        String completed = getCompleted(plans);

        List<List<InlineKeyboardButton>> planButtons = getPlanButtons(plans);

        planButtons.addAll(new ArrayList<>(buttons));

        return SendMessage
                .builder()
                .chatId(user.getChatId())
                .text("Your plans for "+
                        date.format(DateTimeFormatter.ISO_DATE)  +
                        "\nTo complete a plan, click on it\n\n" +
                        completed)
                .replyMarkup(new InlineKeyboardMarkup(planButtons))
                .build();

    }

    private static List<List<InlineKeyboardButton>> getPlanButtons(List<DailyPlan> plans) {
        return (plans.stream()
               .filter(plan -> !plan.getRecord().getIsComplete())
               .map(plan -> List.of(InlineKeyboardButton.builder()
                       .text(plan.getRecord().getPlan())
                       .callbackData(plan.getId().toString())
                       .build())
               )
               .collect(Collectors.toCollection(ArrayList::new)));
    }

    private static String getCompleted(List<DailyPlan> plans) {
        return plans.stream()
                 .filter(plan -> plan.getRecord().getIsComplete())
                 .map(plan -> plan.getRecord().getPlan())
                 .collect(Collectors.joining("\n"));
    }


}