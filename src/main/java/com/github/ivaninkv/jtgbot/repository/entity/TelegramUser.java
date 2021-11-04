package com.github.ivaninkv.jtgbot.repository.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Telegram User entity.
 */
@Data
@Entity
@Table(name = "tg_user")
public class TelegramUser {

    @Id
    @Column(name = "chat_id")
    private long chatId;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "active")
    private boolean active;
}
