package com.github.ivaninkv.jtgbot.repository;

import com.github.ivaninkv.jtgbot.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, Long> {
    List<TelegramUser> findAllByActiveTrue();

    List<TelegramUser> findAllByActiveFalse();
}
