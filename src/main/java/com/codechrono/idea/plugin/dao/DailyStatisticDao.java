package com.codechrono.idea.plugin.dao;

import com.codechrono.idea.plugin.entity.DailyStatistic;

import java.sql.Connection;
import java.util.List;

public interface DailyStatisticDao extends CommonDao<DailyStatistic>  {
    List<DailyStatistic> queryOneDayEditRecord(Connection conn, Long beginTime);
    List<DailyStatistic> getDailyStatistic(Connection conn, Long beginTime, Long endTime);
}
