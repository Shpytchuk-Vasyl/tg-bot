package org.telegram.mybot.processing.message.handlers;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.processing.message.Handler;
import org.telegram.mybot.processing.message.KeyBoardButtons;
import org.telegram.mybot.processing.message.Sender;
import org.telegram.mybot.processing.user.entity.Status;
import org.telegram.mybot.processing.user.entity.User;
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
            Status status = KeyBoardButtons.getStatus(msg.getText());

            if(!status.equals(Status.NONE)) {
                user.setStatus(status);
                try {
                    serviceManager.getUserService().updateUserStatus(user);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                switch (status) {
                    case JPT -> {
                        sendSpeakMsg(msg);
                    }
                    case SPEECH -> {
                        sendRecognizeMsg(msg);
                    }
                    case VACANCIES -> {
                        sendVacancyMsg(msg);
                    }
                    case TRACKER -> {
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
        notAvailable(msg);
    }

    private void sendVacancyMsg(Message msg) {
        notAvailable(msg);
    }

    private void sendRecognizeMsg(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .replyMarkup(
                        ReplyKeyboardMarkup.builder()
                                .keyboardRow(new KeyboardRow(
                                        List.of(
                                                new KeyboardButton(KeyBoardButtons.MENU)
                                        )))
                                .resizeKeyboard(true)
                                .oneTimeKeyboard(true)
                                .build()
                )
                .text("Send your audio file or voice message: ")
                .build()
        );
    }

    private void sendSpeakMsg(Message msg) {
        notAvailable(msg);
    }

    private void notAvailable(Message msg) {
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .replyMarkup(
                        ReplyKeyboardMarkup.builder()
                                .keyboardRow(new KeyboardRow(
                                        List.of(
                                                new KeyboardButton(KeyBoardButtons.MENU)
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
