package com.github.ivaninkv.jtgbot.command;

import com.github.ivaninkv.jtgbot.service.SendBotMessageService;
import com.github.ivaninkv.jtgbot.service.TelegramUserService;
import com.google.common.collect.ImmutableMap;

import static com.github.ivaninkv.jtgbot.command.CommandName.*;

public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    public final Command unknownCommand;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService) {
        commandMap = ImmutableMap.<String, Command> builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier) {
        return commandMap.getOrDefault(commandIdentifier, unknownCommand);
    }
}
