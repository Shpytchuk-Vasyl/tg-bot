package org.telegram.mybot.message.handlers.tracker;

import org.telegram.mybot.ServiceManager;
import org.telegram.mybot.message.Handler;
import org.telegram.mybot.message.Sender;
import org.telegram.mybot.tracker.entity.TrackerStatus;
import org.telegram.mybot.user.entity.User;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public class TrackerCallBackHandler extends Handler<CallbackQuery> {
    private final User user;
    private final ServiceManager serviceManager;

    public TrackerCallBackHandler(Sender sender, User user, ServiceManager serviceManager) {
        super(sender);
        this.user = user;
        this.serviceManager = serviceManager;
    }

    @Override
    public void resolve(CallbackQuery callbackQuery) {
        TrackerStatus status = serviceManager.getTrackerService().getUserStatus(user).getTrackerStatus();
        if(status == TrackerStatus.VIEW) {
            switch (callbackQuery.getData()) {
                case TrackerHandler.ADD -> new EditHandler(sender,user,serviceManager).resolve(callbackQuery);
                case TrackerHandler.BACK -> new ExitHandler(sender,user,serviceManager).resolve(callbackQuery);
                case TrackerHandler.FORWARD, TrackerHandler.BACKWARD, TrackerHandler.TODAY
                        -> new NavigationHandler(sender,user,serviceManager).resolve(callbackQuery);
                default -> new CompleteHandler(sender,user,serviceManager).resolve(callbackQuery);
            }
        } else if (status == TrackerStatus.EDIT) {
            new EditHandler(sender,user,serviceManager).resolve(callbackQuery);
        }
    }
}
