package org.telegram.mybot.processing.message;

import org.telegram.mybot.processing.user.entity.Status;

import java.util.List;

public class KeyBoardButtons {

    public final static String MENU = "/Menu";
    public final static String CLEAR_GPT_CHAT = "/Clear chat";
    public static List<String> getStartsKeyBoardButtons() {
        return List.of("/GPT", "/Speech","/Tracker", "/Vacancy");
    }

    public static Status getStatus(String text) {
        List<String> buttons = KeyBoardButtons.getStartsKeyBoardButtons();
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
