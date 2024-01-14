package org.telegram.mybot.processing.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicUpdate
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private Long chatId;


    @Enumerated(EnumType.STRING)
    private  Status status = Status.NONE;


}
