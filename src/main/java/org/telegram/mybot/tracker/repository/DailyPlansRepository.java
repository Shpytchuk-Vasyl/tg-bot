package org.telegram.mybot.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.mybot.tracker.entity.DailyPlan;


@Repository
public interface DailyPlansRepository extends JpaRepository<DailyPlan,Long> {

}
