package org.telegram.mybot.message;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.handlers.tracker.TrackerCallBackHandler;
import org.telegram.mybot.user.entity.Status;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class CallBackHandler extends Handler<CallbackQuery> {

    private final User user;
    private final ServiceManager serviceManager;

    public CallBackHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(CallbackQuery msg) {
        if (user.getStatus() == Status.TRACKER) {
            new TrackerCallBackHandler(sender, user,serviceManager).resolve(msg);
        }

    }
}
