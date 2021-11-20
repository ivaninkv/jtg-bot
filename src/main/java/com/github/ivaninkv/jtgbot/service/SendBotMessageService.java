package com.github.ivaninkv.jtgbot.service;

import java.util.List;

public interface SendBotMessageService {
    void sendMessage(long chatId, String message);

    void sendMessage(long chatId, List<String> message);
}
