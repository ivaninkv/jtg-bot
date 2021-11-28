package com.github.ivaninkv.jtgbot.service;

import com.github.ivaninkv.jtgbot.javarushclient.JavaRushPostClient;
import com.github.ivaninkv.jtgbot.javarushclient.dto.PostInfo;
import com.github.ivaninkv.jtgbot.repository.entity.GroupSub;
import com.github.ivaninkv.jtgbot.repository.entity.TelegramUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FindNewPostServiceImpl implements FindNewPostService {
    public static final String JAVARUSH_WEB_POST_FORMAT = "https://javarush.ru/groups/posts/%s";

    private final GroupSubService groupSubService;
    private final JavaRushPostClient javaRushPostClient;
    private final SendBotMessageService sendBotMessageService;

    @Autowired
    public FindNewPostServiceImpl(GroupSubService groupSubService,
                                  JavaRushPostClient javaRushPostClient,
                                  SendBotMessageService sendBotMessageService) {
        this.groupSubService = groupSubService;
        this.javaRushPostClient = javaRushPostClient;
        this.sendBotMessageService = sendBotMessageService;
    }

    private void setNewLastPostId(GroupSub gSub, List<PostInfo> newPosts) {
        newPosts.stream().mapToInt(PostInfo::getId).max()
                .ifPresent(id -> {
                    gSub.setLastPostId(id);
                    groupSubService.save(gSub);
                });
    }

    private String getPostUrl(String key) {
        return String.format(JAVARUSH_WEB_POST_FORMAT, key);
    }

    private void notifySubscribersAboutNewPosts(GroupSub gSub, List<PostInfo> newPosts) {
        Collections.reverse(newPosts);
        List<String> messagesWithNewPosts = newPosts.stream()
                .map(post -> String.format("✨Вышла новая статья <b>%s</b> в группе <b>%s</b>.✨\n\n" +
                                "<b>Описание:</b> %s\n\n" +
                                "<b>Ссылка:</b> %s\n",
                        post.getTitle(), gSub.getTitle(), post.getDescription(), getPostUrl(post.getKey())))
                .collect(Collectors.toList());

        gSub.getUsers().stream()
                .filter(TelegramUser::isActive)
                .forEach(it -> sendBotMessageService.sendMessage(it.getChatId(), messagesWithNewPosts));
    }

    @Override
    public void findNewPosts() {
        groupSubService.findAll().forEach(groupSub -> {
            List<PostInfo> newPosts = javaRushPostClient.findNewPosts(groupSub.getId(), groupSub.getLastPostId());
            setNewLastPostId(groupSub, newPosts);
            notifySubscribersAboutNewPosts(groupSub, newPosts);
        });
    }

}
