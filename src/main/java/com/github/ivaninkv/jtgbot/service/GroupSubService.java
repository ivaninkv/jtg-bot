package com.github.ivaninkv.jtgbot.service;

import com.github.ivaninkv.jtgbot.javarushclient.dto.GroupDiscussionInfo;
import com.github.ivaninkv.jtgbot.repository.entity.GroupSub;

import java.util.Optional;

/**
 * Service for manipulating with {@link GroupSub}.
 */
public interface GroupSubService {
    GroupSub save(Long chatId, GroupDiscussionInfo groupDiscussionInfo);
    GroupSub save(GroupSub groupSub);
    Optional<GroupSub> findById(Integer groupId);
}
