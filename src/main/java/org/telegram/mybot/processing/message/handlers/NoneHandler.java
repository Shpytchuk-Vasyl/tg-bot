package org.telegram.mybot.processing.message.handlers;

import org.telegram.mybot.processing.message.Handler;
import org.telegram.mybot.processing.message.ResourceForCommands;
import org.telegram.mybot.processing.message.Sender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.List;

public class NoneHandler extends Handler<Message> {
    public NoneHandler(Sender sender) {
        super(sender);
    }

    @Override
    public void resolve(Message msg) {
        List<String> buttons = ResourceForCommands.getStartsKeyBoardButtons();
        sender.sendMessage(SendMessage
                .builder()
                .chatId(msg.getChatId())
                .replyMarkup(
                        ReplyKeyboardMarkup.builder()
                                .keyboardRow(new KeyboardRow(
                                        List.of(
                                                new KeyboardButton(buttons.get(0)),
                                                new KeyboardButton(buttons.get(1))
                                        )))
                                .keyboardRow(new KeyboardRow(
                                        List.of(
                                                new KeyboardButton(buttons.get(2)),
                                                new KeyboardButton(buttons.get(3))
                                        )))
                                .resizeKeyboard(true)
                                .oneTimeKeyboard(true)
                                .build()
                )
                .text("Click on the button: ")
                .build()
        );
    }
}
