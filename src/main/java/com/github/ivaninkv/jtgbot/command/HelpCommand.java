package com.github.ivaninkv.jtgbot.command;

import com.github.ivaninkv.jtgbot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.ivaninkv.jtgbot.command.CommandName.*;

public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    public static final String HELP_MESSAGE = String.format("✨<b>Доступные команды</b>✨\n\n"

                    + "<b>Начать/закончить работу с ботом</b>\n"
                    + "%s - начать работу со мной\n"
                    + "%s - подписаться на новую группу\n"
                    + "%s - приостановить работу со мной\n\n"
                    + "%s - получить помощь в работе со мной\n"
                    + "%s - показать статистику пользователей\n",
            START.getCommandName(), ADD_GROUP_SUB.getCommandName(), STOP.getCommandName(),
            HELP.getCommandName(),
            STAT.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId(), HELP_MESSAGE);
    }
}
