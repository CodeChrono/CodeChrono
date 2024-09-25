package com.codechrono.idea.plugin.listener;

import com.codechrono.idea.plugin.entity.DailyStatistic;
import com.codechrono.idea.plugin.entity.StatisticType;
import com.codechrono.idea.plugin.service.DailyStatisticInterface;
import com.codechrono.idea.plugin.service.impl.DailyStatisticService;
import com.intellij.ide.IdeEventQueue.EventDispatcher;
import org.jetbrains.annotations.NotNull;


import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * 监听IDEA窗口的焦点变化，根据焦点所在应用情况，计算idea使用时间
 */

public final class FocusDispatcher implements EventDispatcher {

    private DailyStatisticInterface dailyStatisticService = DailyStatisticService.getInstance();
    // 使用匿名数组列表初始化并添加元素
    public List<DailyStatistic> cacheDailyList = new ArrayList<DailyStatistic>();

    @Override
    public boolean dispatch(@NotNull AWTEvent e) {
        if (e instanceof WindowEvent) {
            WindowEvent we = (WindowEvent) e;
            System.out.println("*********WindowEvent: " + we.getID());
            if (we.getID() == WindowEvent.WINDOW_GAINED_FOCUS) {
                onIdeaGainFocus();
            } else if (we.getID() == WindowEvent.WINDOW_DEACTIVATED) {
                onIdeaLostFocus();
            } else if (we.getID() == WindowEvent.WINDOW_ACTIVATED) {
                onIdeaGainFocus();
            }

        }
        return false;
    }

    private void onIdeaGainFocus() {

        System.out.println("*******IDEA 获得焦点*******");

        for (DailyStatistic daily : cacheDailyList) {
            if (Thread.currentThread().getId() == (daily.getThreadId())) {
                return;
            }
        }

        DailyStatistic data = new DailyStatistic();
        data.setBeginTime(System.currentTimeMillis());
        data.setStatisticType(StatisticType.TMP);
        data.setThreadId(Thread.currentThread().getId());
        cacheDailyList.add(data);
    }

    private void onIdeaLostFocus() {

        System.out.println("********IDEA 应用开始切换，失去焦点******");
        for (DailyStatistic daily : cacheDailyList) {
            if (Thread.currentThread().getId() == (daily.getThreadId())) {
                List<DailyStatistic> dailyStatisticList = dailyStatisticService.queryOneDayEditRecord(daily.getBeginTime());

                for (DailyStatistic data : dailyStatisticList) {

                    daily.setInsertNum(data.getInsertNum());
                    daily.setDeleteNum(data.getDeleteNum());
                    daily.setCodeRatio(data.getCodeRatio());
                    daily.setProjectName(data.getProjectName());
                    daily.setEndTime(System.currentTimeMillis());
                    //计算使用时间
                    daily.setUseTime((int) (daily.getEndTime() - daily.getBeginTime()));
                    dailyStatisticService.insert(daily);
                }

                System.out.println("lost丢失" + daily.tostring());
                cacheDailyList.remove(daily);
                return;
            }
        }

    }
}
