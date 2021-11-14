package com.github.ivaninkv.jtgbot.command;

import com.github.ivaninkv.jtgbot.javarushclient.JavaRushGroupClient;
import com.github.ivaninkv.jtgbot.javarushclient.dto.GroupDiscussionInfo;
import com.github.ivaninkv.jtgbot.javarushclient.dto.GroupRequestArgs;
import com.github.ivaninkv.jtgbot.repository.entity.GroupSub;
import com.github.ivaninkv.jtgbot.service.GroupSubService;
import com.github.ivaninkv.jtgbot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

import static com.github.ivaninkv.jtgbot.command.CommandName.ADD_GROUP_SUB;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Add Group subscription {@link Command}.
 */
public class AddGroupSubCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final JavaRushGroupClient javaRushGroupClient;
    private final GroupSubService groupSubService;

    public AddGroupSubCommand(SendBotMessageService sendBotMessageService,
                              JavaRushGroupClient javaRushGroupClient,
                              GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.javaRushGroupClient = javaRushGroupClient;
        this.groupSubService = groupSubService;
    }

    private void sendGroupIdList(Long chatId) {
        String groupIds = javaRushGroupClient.getGroupList(GroupRequestArgs.builder().build()).stream()
                .map(group -> String.format("%s - %s \n", group.getTitle(), group.getId()))
                .collect(Collectors.joining());

        String message = "Чтобы подписаться на группу - передай команду вместе с ID группы. \n" +
                "Например: /addGroupSub 16. \n\n" +
                "я подготовил список всех групп - выбирай какую хочешь :) \n\n" +
                "имя группы - ID группы \n\n" +
                "%s";

        sendBotMessageService.sendMessage(chatId, String.format(message, groupIds));
    }

    private void sendGroupNotFound(Long chatId, String groupId) {
        String groupNotFoundMessage = "Нет группы с ID = \"%s\"";
        sendBotMessageService.sendMessage(chatId, String.format(groupNotFoundMessage, groupId));
    }

    @Override
    public void execute(Update update) {
        if (update.getMessage().getText().equalsIgnoreCase(ADD_GROUP_SUB.getCommandName())) {
            sendGroupIdList(update.getMessage().getChatId());
            return;
        }
        String groupId = update.getMessage().getText().split(SPACE)[1];
        Long chatId = update.getMessage().getChatId();
        if (isNumeric(groupId)) {
            GroupDiscussionInfo groupById = javaRushGroupClient.getGroupById(Integer.parseInt(groupId));
            if (isNull(groupById.getId())) {
                sendGroupNotFound(chatId, groupId);
            }
            GroupSub savedGroupSub = groupSubService.save(chatId, groupById);
            sendBotMessageService.sendMessage(chatId, "Подписал на группу " + savedGroupSub.getTitle());
        } else {
            sendGroupNotFound(chatId, groupId);
        }
    }
}
