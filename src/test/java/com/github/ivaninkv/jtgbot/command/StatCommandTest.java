package com.github.ivaninkv.jtgbot.command;

import static com.github.ivaninkv.jtgbot.command.CommandName.STAT;
import static com.github.ivaninkv.jtgbot.command.StatCommand.STAT_MESSAGE;

public class StatCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return STAT.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return String.format(STAT_MESSAGE, 0);
    }

    @Override
    Command getCommand() {
        return new StatCommand(sendBotMessageService, telegramUserService);
    }
}
