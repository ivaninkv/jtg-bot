package com.github.ivaninkv.jtgbot.command;

import com.github.ivaninkv.jtgbot.repository.entity.GroupSub;
import com.github.ivaninkv.jtgbot.repository.entity.TelegramUser;
import com.github.ivaninkv.jtgbot.service.SendBotMessageService;
import com.github.ivaninkv.jtgbot.service.TelegramUserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.github.ivaninkv.jtgbot.command.AbstractCommandTest.prepareUpdate;
import static com.github.ivaninkv.jtgbot.command.CommandName.LIST_GROUP_SUB;

@DisplayName("Unit-level testing for ListGroupSubCommand")
class ListGroupSubCommandTest {
    @Test
    public void shouldProperlyShowsListGroupSub() {
        // given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setActive(true);
        telegramUser.setChatId(1L);

        List<GroupSub> groupSubList = new ArrayList<>();
        groupSubList.add(populateGroupSub(1, "gs1"));
        groupSubList.add(populateGroupSub(2, "gs2"));
        groupSubList.add(populateGroupSub(3, "gs3"));
        groupSubList.add(populateGroupSub(4, "gs4"));

        telegramUser.setGroupSubs(groupSubList);

        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        Mockito.when(telegramUserService.findByChatId(telegramUser.getChatId())).thenReturn(Optional.of(telegramUser));

        ListGroupSubCommand command = new ListGroupSubCommand(sendBotMessageService, telegramUserService);

        Update update = prepareUpdate(telegramUser.getChatId(), LIST_GROUP_SUB.getCommandName());

        String collectedGroups = "Я нашел все подписки на группы: \n\n" +
                telegramUser.getGroupSubs().stream()
                        .map(it -> "Группа: " + it.getTitle() + ", ID = " + it.getId() + ";\n")
                        .collect(Collectors.joining());

        // when
        command.execute(update);

        // then
        Mockito.verify(sendBotMessageService).sendMessage(telegramUser.getChatId(), collectedGroups);
    }

    private GroupSub populateGroupSub(Integer id, String title) {
        GroupSub gs = new GroupSub();
        gs.setId(id);
        gs.setTitle(title);
        return gs;
    }
}
