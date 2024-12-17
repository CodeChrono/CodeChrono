package com.codechrono.idea.plugin.listener;

import com.codechrono.idea.plugin.entity.DailyStatistic;
import com.codechrono.idea.plugin.entity.StatisticType;
import com.codechrono.idea.plugin.service.DailyStatisticInterface;
import com.codechrono.idea.plugin.service.impl.DailyStatisticService;
import com.intellij.ide.IdeEventQueue.EventDispatcher;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * @author codechrono
 * 监听IDEA窗口的焦点变化，根据焦点所在应用情况，计算idea使用时间
 */

public final class FocusDispatcher implements EventDispatcher {
    private static final Logger LOGGER = Logger.getInstance("FClog");
    private final DailyStatisticInterface dailyStatisticService = DailyStatisticService.getInstance();
    // 使用匿名数组列表初始化并添加元素
    public static List<DailyStatistic> cacheDailyList = new ArrayList<>();


    @Override
    public boolean dispatch(@NotNull AWTEvent e) {
        if (e instanceof WindowEvent) {
            WindowEvent we = (WindowEvent) e;

            LOGGER.info("*********dispatch.getId: " + we.getID());
            if (we.getID() == WindowEvent.WINDOW_ACTIVATED || we.getID() == WindowEvent.WINDOW_OPENED) {
                //LOGGER.info("*********getState-ACTIVATED: " + Thread.currentThread().getState());
                onIdeaGainFocus();

            } else if (we.getID() == WindowEvent.WINDOW_DEACTIVATED) {
                //LOGGER.info("*********getState-ACTIVATED: " + Thread.currentThread().getState());
                onIdeaLostFocus();
            }

            // 判断窗口事件的ID是否是窗口激活、窗口失去焦点、窗口获得焦点、窗口失去焦点、窗口关闭、窗口关闭完成、窗口打开
            //WINDOW_ACTIVATED、WINDOW_DEACTIVATED、WINDOW_GAINED_FOCUS、WINDOW_LOST_FOCUS、WINDOW_CLOSING、WINDOW_CLOSED、WINDOW_OPENED

        }
        return false;
    }

    private void onIdeaGainFocus() {

        System.out.println("*******IDEA get Focus*******");
        long currentThreadId = Thread.currentThread().getId();
        for (DailyStatistic daily : cacheDailyList) {
            if (currentThreadId == (daily.getThreadId())) {
                System.out.println("*******Dispatcher get Focus：return");
                return;
            }
        }

        DailyStatistic data = new DailyStatistic();
        data.setBeginTime(System.currentTimeMillis());
        data.setStatisticType(StatisticType.TMP);
        data.setThreadId(currentThreadId);
        System.out.println("*******Dispatcher onIdeaGainFocus, id:" + data.getThreadId());
        cacheDailyList.add(data);
    }

    public void onIdeaLostFocus() {
        onIdeaLostFocus(false);
    }

    /**
     * @param createEvent 是否创建新的焦点事件
     */
    public void onIdeaLostFocus(boolean createEvent) {

        System.out.println("********IDEA app begin change ,lost focus******");

        List <DailyStatistic> deleList = new ArrayList<>();
        for (DailyStatistic daily : cacheDailyList) {
            long currentThreadId = Thread.currentThread().getId();
            if (currentThreadId == (daily.getThreadId())) {
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
                System.out.println("*******IDEA LostFocus,id:" + daily.getThreadId());
                deleList.add(daily);
            }
        }

        if(deleList!=null && deleList.size() > 0) {
            cacheDailyList.removeAll(deleList);
        }
        if(createEvent){
            onIdeaGainFocus();
        }


    }
}
