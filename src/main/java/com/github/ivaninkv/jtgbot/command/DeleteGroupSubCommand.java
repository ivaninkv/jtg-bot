package com.github.ivaninkv.jtgbot.command;

import com.github.ivaninkv.jtgbot.repository.entity.GroupSub;
import com.github.ivaninkv.jtgbot.repository.entity.TelegramUser;
import com.github.ivaninkv.jtgbot.service.GroupSubService;
import com.github.ivaninkv.jtgbot.service.SendBotMessageService;
import com.github.ivaninkv.jtgbot.service.TelegramUserService;
import org.springframework.util.CollectionUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.ivaninkv.jtgbot.command.CommandName.DELETE_GROUP_SUB;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Delete Group subscription {@link Command}.
 */
public class DeleteGroupSubCommand implements Command {
    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final GroupSubService groupSubService;

    public DeleteGroupSubCommand(SendBotMessageService sendBotMessageService,
                                 TelegramUserService telegramUserService,
                                 GroupSubService groupSubService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.groupSubService = groupSubService;
    }

    private void sendGroupIdList(Long chatId) {
        String message;
        List<GroupSub> groupSubs = telegramUserService.findByChatId(chatId)
                .orElseThrow(NotFoundException::new)
                .getGroupSubs();
        if (CollectionUtils.isEmpty(groupSubs)) {
            message = "Пока нет подписок на группы. Чтобы добавить подписку напишите /addgroupsub";
        } else {
            String userGroupSubData = groupSubs.stream()
                    .map(groupSub -> String.format("%s - %s \n", groupSub.getTitle(), groupSub.getId()))
                    .collect(Collectors.joining());
            message = String.format("Чтобы удалить подписку на группу - передайте команду вместе с ID группы. \n" +
                    "Например: /deletegroupsub 16 \n\n" +
                    "я подготовил список всех групп, на которые вы подписаны) \n\n" +
                    "имя группы - ID группы \n\n" +
                    "%s", userGroupSubData);
        }

        sendBotMessageService.sendMessage(chatId, message);
    }

    @Override
    public void execute(Update update) {
        Long chatId = update.getMessage().getChatId();
        if (update.getMessage().getText().equalsIgnoreCase(DELETE_GROUP_SUB.getCommandName())) {
            sendGroupIdList(chatId);
            return;
        }
        String groupId = update.getMessage().getText().split(SPACE)[1];
        if (isNumeric(groupId)) {
            Optional<GroupSub> optionalGroupSub = groupSubService.findById(Integer.valueOf(groupId));
            if (optionalGroupSub.isPresent()) {
                GroupSub groupSub = optionalGroupSub.get();
                TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
                if (groupSub.getUsers().contains(telegramUser)) {
                    groupSub.getUsers().remove(telegramUser);
                    groupSubService.save(groupSub);
                    sendBotMessageService.sendMessage(chatId, String.format("Удалил подписку на группу: %s", groupSub.getTitle()));
                } else {
                    sendBotMessageService.sendMessage(chatId, String.format("Вы не подписаны на группу: %s", groupSub.getTitle()));
                }
            } else {
                sendBotMessageService.sendMessage(chatId, "Не нашел такой группы.");
            }
        } else {
            sendBotMessageService.sendMessage(chatId, "неправильный формат ID группы.\n " +
                    "ID должно быть целым положительным числом");
        }
    }
}
