package org.telegram.mybot.processing.message;

import org.telegram.mybot.processing.user.entity.Status;

import java.util.List;

public class KeyBoardButtons {

    public final static String MENU = "/Menu";
    public static List<String> getStartsKeyBoardButtons() {
        return List.of("/JPT", "/Speech","/Tracker", "/Vacancy");
    }

    public static Status getStatus(String text) {
        List<String> buttons = KeyBoardButtons.getStartsKeyBoardButtons();
        if(buttons.get(0).equals(text)) {
            return Status.SPEAK_WITH_JPT;
        } else if(buttons.get(1).equals(text)) {
            return Status.RECOGNIZE_SPEECH;
        } else if(buttons.get(2).equals(text)) {
            return Status.HABITS_TRACKER;
        } else if(buttons.get(3).equals(text)) {
            return Status.FIND_VACANCIES;
        } else {
            return Status.NONE;
        }
    }

}
