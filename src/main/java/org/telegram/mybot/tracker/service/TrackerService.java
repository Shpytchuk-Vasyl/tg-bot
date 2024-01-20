package org.telegram.mybot.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.mybot.tracker.entity.*;
import org.telegram.mybot.tracker.repository.*;
import org.telegram.mybot.user.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
public class TrackerService {

    @Autowired
    private DailyPlansRepository dailyPlansRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;

    public List<DailyPlan> getDailyPlan(User user) {
        return null;
    }

    public UserStatus getUserStatus(User user) {
        return null;
    }

    public void addNewPlan(DailyPlan dailyPlan, User user) {

    }

    public void completeDailyPlan(DailyPlan dailyPlan) {

    }

    public void deleteDailyPlan(DailyPlan dailyPlan) {

    }


    public Map<LocalDate, PlanRecord> getAllPlans(User user) {
        return null;
    }


}

