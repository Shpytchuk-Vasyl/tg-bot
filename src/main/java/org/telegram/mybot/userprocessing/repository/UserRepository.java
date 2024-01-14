package org.telegram.mybot.userprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.mybot.userprocessing.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
