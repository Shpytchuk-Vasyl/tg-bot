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
        if(serviceManager.getTrackerService().getUserStatus(user).getTrackerStatus() == TrackerStatus.VIEW) {
            if(callbackQuery.getData().equalsIgnoreCase(TrackerHandler.ADD)) {
                new EditHandler(sender,user,serviceManager).resolve(callbackQuery);
            } else if (callbackQuery.getData().equalsIgnoreCase(TrackerHandler.BACK)) {


                ///some cod here

            } else if (callbackQuery.getData().equalsIgnoreCase(TrackerHandler.FORWARD)
                    || callbackQuery.getData().equalsIgnoreCase(TrackerHandler.BACKWARD)
                    || callbackQuery.getData().equalsIgnoreCase(TrackerHandler.TODAY)) {
                new NavigationHandler(sender,user,serviceManager).resolve(callbackQuery);
            } else {
                new CompleteHandler(sender,user,serviceManager).resolve(callbackQuery);
            }
        }
    }
}
