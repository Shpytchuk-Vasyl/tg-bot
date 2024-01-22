package org.telegram.mybot.message;

import org.telegram.mybot.user.entity.Status;

import java.util.List;

public class ResourceForCommands {

    public final static String MENU = "/Menu";
    public final static String CLEAR_GPT_CHAT = "/Clear chat";
    public static List<String> getStartsKeyBoardButtons() {
        return List.of("/GPT", "/Speech","/Tracker", "/Vacancy");
    }

    public static List<String> getTrackerStartKeyBoardButtons() {
        return List.of("Get plans for the day", "Get all plans in excel");
    }


    public static Status getStatus(String text) {
        List<String> buttons = getStartsKeyBoardButtons();
        if(buttons.get(0).equals(text)) {
            return Status.GPT;
        } else if(buttons.get(1).equals(text)) {
            return Status.SPEECH;
        } else if(buttons.get(2).equals(text)) {
            return Status.TRACKER;
        } else if(buttons.get(3).equals(text)) {
            return Status.VACANCY;
        } else {
            return Status.NONE;
        }
    }

}
