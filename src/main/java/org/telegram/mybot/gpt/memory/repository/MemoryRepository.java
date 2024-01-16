package org.telegram.mybot.gpt.memory.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.telegram.mybot.gpt.memory.entity.MemoryEntity;

import java.util.List;

@Repository
public interface MemoryRepository extends JpaRepository<MemoryEntity, Long> {
    List<MemoryEntity> findAllByUserId(Long user_id, Sort sort);
    void deleteAllByUserId(Long user_id);
}
