package org.telegram.mybot.processing.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.mybot.processing.user.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findUserByChatId(Long chatId);
}
