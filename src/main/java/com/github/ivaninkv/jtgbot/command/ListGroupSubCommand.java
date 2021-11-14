package com.github.ivaninkv.jtgbot.command;

import com.github.ivaninkv.jtgbot.repository.entity.GroupSub;
import com.github.ivaninkv.jtgbot.repository.entity.TelegramUser;
import com.github.ivaninkv.jtgbot.service.SendBotMessageService;
import com.github.ivaninkv.jtgbot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.stream.Collectors;

/**
 * {@link Command} for getting list of {@link GroupSub}.
 */
public class ListGroupSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;

    public ListGroupSubCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
    }


    @Override
    public void execute(Update update) {
        TelegramUser telegramUser = telegramUserService.findByChatId(update.getMessage().getChatId())
                .orElseThrow(NotFoundException::new);
        String message = "Я нашел все подписки на группы: \n\n";
        String collectedGroup = telegramUser.getGroupSubs().stream()
                .map(it -> "Группа: " + it.getTitle() + ", ID = " + it.getId() + ";\n")
                .collect(Collectors.joining());
        sendBotMessageService.sendMessage(telegramUser.getChatId(), message + collectedGroup);
    }
}
