package org.telegram.mybot.processing.message.handler;

import org.telegram.mybot.processing.message.KeyBoardButtons;
import org.telegram.mybot.processing.message.Sender;
import org.telegram.mybot.processing.user.entity.Status;
import org.telegram.mybot.processing.user.entity.User;
import org.telegram.mybot.processing.user.service.UserService;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class StartHandler extends Handler<Message> {
    User user;
    UserService userService;

    public StartHandler(Sender sender, User user, UserService userService) {
        super(sender);
        this.user = user;
        this.userService = userService;
    }

    @Override
    public void resolve(Message msg) {
        if (msg.hasText()) {
            Status status = KeyBoardButtons.getStatus(msg.getText());

            if(!status.equals(Status.NONE)) {
                user.setStatus(status);
                userService.updateUserStatus(user);

                switch (status) {
                    case SPEAK_WITH_JPT -> {
                        sendSpeakMsg(msg);
                    }
                    case RECOGNIZE_SPEECH -> {
                        sendRecognizeMsg(msg);
                    }
                    case FIND_VACANCIES -> {
                        sendVacancyMsg(msg);
                    }
                    case HABITS_TRACKER -> {
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
