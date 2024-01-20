package org.telegram.mybot.gpt.memory.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.telegram.mybot.gpt.memory.entity.MemoryEntity;
import org.telegram.mybot.gpt.memory.repository.MemoryRepository;
import org.telegram.mybot.user.entity.User;

import java.util.stream.Collectors;

@Service
public class GPTMemoryService {
    @Autowired
    private MemoryRepository memoryRepository;

    public void saveContext(User user, String question, String answer) {
        memoryRepository.save(MemoryEntity.builder()
                .memoryUnit(question + '\n' + answer + '\n')
                .user(user)
                .build()
        );
    }

    public String getContext(User user) {
        return memoryRepository.findAllByUserId(user.getId(), Sort.by("creationTime"))
                .stream()
                .map(MemoryEntity::getMemoryUnit)
                .collect(Collectors.joining());

    }

    @Transactional
    public void clearContext(User user) {
        memoryRepository.deleteAllByUserId(user.getId());
    }


}
