package com.github.ivaninkv.jtgbot.service;

import com.github.ivaninkv.jtgbot.bot.JavaTelegramBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final JavaTelegramBot javaBot;

    @Autowired
    public SendBotMessageServiceImpl(JavaTelegramBot javaBot) {
        this.javaBot = javaBot;
    }

    @Override
    public void sendMessage(long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.enableHtml(true);
        sendMessage.setText(message);
        try {
            javaBot.execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void sendMessage(long chatId, List<String> messages) {
        if (isEmpty(messages)) return;

        messages.forEach(m -> sendMessage(chatId, m));
    }
}
