package com.github.ivaninkv.jtgbot.javarushclient;

import com.github.ivaninkv.jtgbot.javarushclient.dto.GroupDiscussionInfo;
import com.github.ivaninkv.jtgbot.javarushclient.dto.GroupInfo;
import com.github.ivaninkv.jtgbot.javarushclient.dto.GroupRequestArgs;
import com.github.ivaninkv.jtgbot.javarushclient.dto.GroupsCountRequestArgs;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.ivaninkv.jtgbot.javarushclient.dto.GroupInfoType.TECH;

@DisplayName("Integration-level testing for JavaRushGroupClientImplTest")
class JavaRushGroupClientTest {

    private final JavaRushGroupClient groupClient = new JavaRushGroupClientImpl("https://javarush.ru/api/1.0/rest");

    @Test
    public void shouldProperlyGetGroupsWithEmptyArgs() {
        // given
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        // when
        List<GroupInfo> groupInfos = groupClient.getGroupList(args);

        // then
        Assertions.assertNotNull(groupInfos);
        Assertions.assertFalse(groupInfos.isEmpty());
    }

    @Test
    public void shouldProperlyGetWithOffSetAndLimit() {
        // given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        // when
        List<GroupInfo> groupInfos = groupClient.getGroupList(args);

        // then
        Assertions.assertNotNull(groupInfos);
        Assertions.assertEquals(3, groupInfos.size());
    }

    @Test
    public void shouldProperlyGetGroupsDiscWithEmptyArgs() {
        // given
        GroupRequestArgs args = GroupRequestArgs.builder().build();

        // when
        List<GroupDiscussionInfo> groupInfos = groupClient.getGroupDiscussionList(args);

        // then
        Assertions.assertNotNull(groupInfos);
        Assertions.assertFalse(groupInfos.isEmpty());
    }

    @Test
    public void shouldProperlyGetGroupDiscWithOffSetAndLimit() {
        // given
        GroupRequestArgs args = GroupRequestArgs.builder()
                .offset(1)
                .limit(3)
                .build();

        // when
        List<GroupDiscussionInfo> groupList = groupClient.getGroupDiscussionList(args);

        // then
        Assertions.assertNotNull(groupList);
        Assertions.assertEquals(3, groupList.size());
    }

    @Test
    public void shouldProperlyGetGroupCount() {
        // given
        GroupsCountRequestArgs args = GroupsCountRequestArgs.builder().build();

        // when
        Integer groupCount = groupClient.getGroupCount(args);

        // then
        Assertions.assertEquals(31, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupTECHCount() {
        // given
        GroupsCountRequestArgs args = GroupsCountRequestArgs.builder()
                .type(TECH)
                .build();

        // when
        Integer groupCount = groupClient.getGroupCount(args);

        // then
        Assertions.assertEquals(7, groupCount);
    }

    @Test
    public void shouldProperlyGetGroupById() {
        // given
        Integer androidGroupId = 16;

        // when
        GroupDiscussionInfo groupById = groupClient.getGroupById(androidGroupId);

        // then
        Assertions.assertNotNull(groupById);
        Assertions.assertEquals(16, groupById.getId());
        Assertions.assertEquals(TECH, groupById.getType());
        Assertions.assertEquals("android", groupById.getKey());
    }
}
