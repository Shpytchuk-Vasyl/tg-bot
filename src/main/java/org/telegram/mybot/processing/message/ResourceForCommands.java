package org.telegram.mybot.processing.message;

import org.telegram.mybot.processing.user.entity.Status;
import org.telegram.mybot.vacancy.VacancyParser;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.security.Key;
import java.util.List;

public class ResourceForCommands {

    public final static String MENU = "/Menu";
    public final static String CLEAR_GPT_CHAT = "/Clear chat";
    public static List<String> getStartsKeyBoardButtons() {
        return List.of("/GPT", "/Speech","/Tracker", "/Vacancy");
    }

    public static Status getStatus(String text) {
        List<String> buttons = ResourceForCommands.getStartsKeyBoardButtons();
        if(buttons.get(0).equals(text)) {
            return Status.GPT;
        } else if(buttons.get(1).equals(text)) {
            return Status.SPEECH;
        } else if(buttons.get(2).equals(text)) {
            return Status.TRACKER;
        } else if(buttons.get(3).equals(text)) {
            return Status.VACANCIES;
        } else {
            return Status.NONE;
        }
    }

}
