package com.github.ivaninkv.jtgbot.service;

import com.github.ivaninkv.jtgbot.dto.StatisticDTO;

/**
 * Service for getting bot statistics.
 */
public interface StatisticsService {
    StatisticDTO countBotStatistics();
}
