package org.telegram.mybot.tracker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.mybot.tracker.entity.UserStatus;


@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus,Long> {
    UserStatus findByUserId(Long userId);
}
