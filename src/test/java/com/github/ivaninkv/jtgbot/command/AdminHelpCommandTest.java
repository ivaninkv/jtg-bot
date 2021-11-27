package com.github.ivaninkv.jtgbot.command;

import org.junit.jupiter.api.DisplayName;

import static com.github.ivaninkv.jtgbot.command.AdminHelpCommand.ADMIN_HELP_MESSAGE;
import static com.github.ivaninkv.jtgbot.command.CommandName.ADMIN_HELP;

@DisplayName("Unit-level testing for AdminHelpCommand")
class AdminHelpCommandTest extends AbstractCommandTest {

    @Override
    String getCommandName() {
        return ADMIN_HELP.getCommandName();
    }

    @Override
    String getCommandMessage() {
        return ADMIN_HELP_MESSAGE;
    }

    @Override
    Command getCommand() {
        return new AdminHelpCommand(sendBotMessageService);
    }
}
