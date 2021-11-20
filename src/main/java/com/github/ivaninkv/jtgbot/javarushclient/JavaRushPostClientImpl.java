package com.github.ivaninkv.jtgbot.javarushclient;

import com.github.ivaninkv.jtgbot.javarushclient.dto.PostInfo;
import kong.unirest.GenericType;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class JavaRushPostClientImpl implements JavaRushPostClient {
    private final String javarushApiPostPath;
    private final int postLimit = 15;

    public JavaRushPostClientImpl(@Value("${javarush.api.path}") String javarushApi) {
        this.javarushApiPostPath = javarushApi + "/posts";
    }

    @Override
    public List<PostInfo> findNewPosts(Integer groupId, Integer lastPostId) {
        log.info(String.format("findNewPosts started, groupId=%d, lastPostId=%d",
                groupId, lastPostId));
        List<PostInfo> lastPostByGroup = Unirest.get(javarushApiPostPath)
                .queryString("order", "NEW")
                .queryString("groupKid", groupId)
                .queryString("limit", postLimit)
                .asObject(new GenericType<List<PostInfo>>() {
                }).getBody();
        List<PostInfo> newPosts = new ArrayList<>();
        for (PostInfo post : lastPostByGroup) {
            if (lastPostId.equals(post.getId())) {
                return newPosts;
            }
            newPosts.add(post);
        }

        log.debug(String.format("New post quantity - %d", newPosts.size()));
        return newPosts;
    }
}
