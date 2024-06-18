package com.codechrono.idea.plugin.service;

import com.codechrono.idea.plugin.entity.DailyStatistic;

import java.util.List;

/**
 * @author LeeWyatt
 */
public interface DailyStatisticService extends CommonService<DailyStatistic> {


    void insert(DailyStatistic[] ary);

    DailyStatistic insert(DailyStatistic notebook);

    /** 获取一段时间内编辑记录
     * @param 开始时间
     * @return 时间段内字符数量
     */
    List<DailyStatistic> queryOneDayEditRecord(Long beginDate);

    /**
     * 获取当天编辑记录
     * @param beginDate
     * @return
     */
    List<DailyStatistic> getOneDayDailyStatistic(Long beginDate,Long endDate);


}