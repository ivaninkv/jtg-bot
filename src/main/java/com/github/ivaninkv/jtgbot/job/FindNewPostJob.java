package com.github.ivaninkv.jtgbot.job;

import com.github.ivaninkv.jtgbot.service.FindNewPostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Job for finding new posts.
 */
@Slf4j
@Component
public class FindNewPostJob {
    private final FindNewPostService findNewPostService;

    @Autowired
    public FindNewPostJob(FindNewPostService findNewPostService) {
        this.findNewPostService = findNewPostService;
    }

    @Scheduled(fixedRateString = "${bot.postSchedulerTimeout}")
    public void findNewPosts() {
        LocalDateTime start = LocalDateTime.now();
        log.info("Find new post job started.");
        findNewPostService.findNewPosts();
        LocalDateTime end = LocalDateTime.now();
        log.info("Find new post job finished. Took seconds: {}",
                end.toEpochSecond(ZoneOffset.UTC) - start.toEpochSecond(ZoneOffset.UTC));
    }
}
