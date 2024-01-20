package org.telegram.mybot.message.handlers;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.message.ResourceForCommands;
import org.telegram.mybot.user.entity.Status;
import org.telegram.mybot.user.entity.User;
import org.telegram.mybot.vacancy.VacancyParser;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class StartHandler extends Handler<Message> {
    private final User user;
    private final ServiceManager serviceManager;

    public StartHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(Message msg) {
        if (msg.hasText()) {
            Status status = ResourceForCommands.getStatus(msg.getText());

            if(!status.equals(Status.NONE)) {
                user.setStatus(status);
                serviceManager.getUserService().updateUserStatus(user);
                switch (status) {
                    case GPT -> sendGPTMsg(msg);
                    case SPEECH -> sendRecognizeMsg(msg);
                    case VACANCIES -> sendVacancyMsg(msg);
                    case TRACKER -> {
                        if(serviceManager.getTrackerService().getUserStatus(user) == null) {
                            serviceManager.getTrackerService().registerUser(user);
                        }
                        sendTrackerMsg(msg);
                    }
                }

            } else {
                incorrectInput(msg);
            }

        } else {
            incorrectInput(msg);
        }

    }

    private void incorrectInput(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .text("Please choose available function :")
                .build());
    }

    private void sendTrackerMsg(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .text("Please choose category")
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

    private void sendVacancyMsg(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .text("Please choose category")
                .replyMarkup(ReplyKeyboardMarkup.builder()
                        .keyboardRow(new KeyboardRow(
                                new VacancyParser().getCategories()
                                        .stream()
                                        .map(KeyboardButton::new)
                                        .toList()
                        ))
                        .keyboardRow(new KeyboardRow(
                                List.of(
                                        new KeyboardButton(ResourceForCommands.MENU)
                                )))
                        .resizeKeyboard(true)
                        .build()
                )
                .build());
    }

    private void sendRecognizeMsg(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .replyMarkup(
                        ReplyKeyboardMarkup.builder()
                                .keyboardRow(new KeyboardRow(
                                        List.of(
                                                new KeyboardButton(ResourceForCommands.MENU)
                                        )))
                                .resizeKeyboard(true)
                                .oneTimeKeyboard(true)
                                .build()
                )
                .text("Send your audio file or voice message: ")
                .build()
        );
    }

    private void sendGPTMsg(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .replyMarkup(
                        ReplyKeyboardMarkup.builder()
                                .keyboardRow(new KeyboardRow(
                                        List.of(
                                                new KeyboardButton(ResourceForCommands.MENU)
                                        )))
                                .keyboardRow(new KeyboardRow(
                                        List.of(
                                                new KeyboardButton(ResourceForCommands.CLEAR_GPT_CHAT)
                                        )))
                                .resizeKeyboard(true)
                                .build()
                )
                .text("Send messages: ")
                .build()
        );
    }

    private void notAvailable(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .replyMarkup(
                        ReplyKeyboardMarkup.builder()
                                .keyboardRow(new KeyboardRow(
                                        List.of(
                                                new KeyboardButton(ResourceForCommands.MENU)
                                        )))
                                .resizeKeyboard(true)
                                .oneTimeKeyboard(true)
                                .build()
                )
                .text("This function not available yet")
                .build()
        );
    }

}
