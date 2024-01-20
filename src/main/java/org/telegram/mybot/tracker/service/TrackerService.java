package org.telegram.mybot.tracker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.mybot.tracker.entity.*;
import org.telegram.mybot.tracker.repository.*;
import org.telegram.mybot.user.entity.User;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TrackerService {

    @Autowired
    private DailyPlansRepository dailyPlansRepository;
    @Autowired
    private UserStatusRepository userStatusRepository;

    public List<DailyPlan> getDailyPlan(User user, LocalDate date) {
        return dailyPlansRepository.findAllByUserIdAndDate(user.getId(), date);
    }

    public UserStatus getUserStatus(User user) {
        return userStatusRepository.findByUserId(user.getId());
    }

    public void setUserStatus(UserStatus userStatus) {
        userStatusRepository.save(userStatus);
    }

    public void addNewPlan(String plan, User user) {
        dailyPlansRepository.save(
                DailyPlan.builder()
                        .user(user)
                        .record(new PlanRecord(plan,false))
                        .build()
        );
    }

    public void completeDailyPlan(Long dailyPlanId) {
        dailyPlansRepository.completePlan(dailyPlanId);
    }

    public void deleteDailyPlan(Long dailyPlanId) {
        dailyPlansRepository.deleteById(dailyPlanId);
    }

    public Map<LocalDate, List<PlanRecord>> getAllPlans(User user) {
        return dailyPlansRepository.findAllByUserId(user.getId())
                .stream()
                .collect(Collectors.toMap(
                        DailyPlan::getDate,
                        plan -> {
                           List<PlanRecord> list = new ArrayList<PlanRecord>();
                           list.add(plan.getRecord());
                           return list;
                           },
                        (planRecords, planRecords2) -> {
                            planRecords.addAll(planRecords2);
                            return planRecords;
                        })
                );
    }


}

