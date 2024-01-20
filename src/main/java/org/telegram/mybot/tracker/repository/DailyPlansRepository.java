package org.telegram.mybot.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.telegram.mybot.tracker.entity.DailyPlan;
import org.telegram.mybot.tracker.entity.PlanRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Repository
public interface DailyPlansRepository extends JpaRepository<DailyPlan,Long> {

    List<DailyPlan> findAllByUserIdAndDate(Long userId, LocalDate date);

    @Modifying
    @Query("update DailyPlan plan set plan.record.isComplete = true where plan.id = ?1")
    void completePlan(Long dailyPlanId);

    List<DailyPlan> findAllByUserId(Long id);
}
