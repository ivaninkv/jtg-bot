package com.github.ivaninkv.jtgbot.bot;

import com.github.ivaninkv.jtgbot.command.CommandContainer;
import com.github.ivaninkv.jtgbot.javarushclient.JavaRushGroupClient;
import com.github.ivaninkv.jtgbot.service.GroupSubService;
import com.github.ivaninkv.jtgbot.service.SendBotMessageServiceImpl;
import com.github.ivaninkv.jtgbot.service.StatisticsService;
import com.github.ivaninkv.jtgbot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.ivaninkv.jtgbot.command.CommandName.NO;

@Component
public class JavaTelegramBot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private static final String COMMAND_PREFIX = "/";
    private final CommandContainer commandContainer;

    @Autowired
    public JavaTelegramBot(TelegramUserService telegramUserService, JavaRushGroupClient groupClient,
                           GroupSubService groupSubService,
                           @Value("#{'${bot.admins}'.split(',')}") List<String> admins, StatisticsService statisticsService) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),
                telegramUserService, groupClient, groupSubService, admins, statisticsService);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            String username = update.getMessage().getFrom().getUserName();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(" ")[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier, username).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), username).execute(update);
            }
        }
    }
}
