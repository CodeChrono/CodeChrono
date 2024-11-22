package com.codechrono.idea.plugin.utils;

import com.codechrono.idea.plugin.entity.DailyStatistic;
import com.codechrono.idea.plugin.listener.FocusDispatcher;
import com.codechrono.idea.plugin.service.DailyStatisticInterface;
import com.codechrono.idea.plugin.service.impl.DailyStatisticService;

import java.util.*;

import static com.codechrono.idea.plugin.utils.CodeChronoBundle.message;

/**
 * @author CodeChrono
 * 主界面数据中心
 */
public class DataCenter {
    public static String fileName = "CodeChrono";
    static StringBuffer teContent;


    private final DailyStatisticInterface dailyStatisticService = DailyStatisticService.getInstance();


/*    public static StringBuffer getTeContentTmp() {
        teContent = new StringBuffer("您今天使用idea【5小时30分钟20秒】，闲置时间【1小时2分钟20秒】，时间利用率33%");
        teContent.append("\n  总字符量【285】，回退数【30】");
        teContent.append("\n以下是按项目展示内容:");
        teContent.append("\n  【mall】总字符量【200】，回退数【25】，占用时间【3小时2分钟20秒】");
        teContent.append("\n  【tyAi】总字符量【80】，回退数【3】，占用时间【3小时2分钟20秒】");
        teContent.append("\n  【mall】总字符量【5】，回退数【2】，占用时间【3小时2分钟20秒】");
        return teContent;


    }*/

    public StringBuffer getDailyStatisticTeContent(Long beginTime, Long endTime) {
        if ("zh".equals(Locale.getDefault().getLanguage())) {
            return getDailyStatisticTeContentZh(beginTime, endTime);
        } else {
            return getDailyStatisticTeContentEu(beginTime, endTime);
        }
    }

    public StringBuffer getDailyStatisticTeContentEu(Long beginTime, Long endTime) {
        List<DailyStatistic> dailyStatistic = dailyStatisticService.getOneDayDailyStatistic(beginTime, endTime);
        if (dailyStatistic == null) {
            dailyStatistic = new ArrayList<>();

        }


        teContent = new StringBuffer();
        teContent.append("\nProject Statistic Overview");
        int allNum = 0;
        int deleteNum = 0;
        long useTime = 0;
        for (int i = 0; i < dailyStatistic.size(); i++) {
            teContent.append("\n  " + (i + 1) + "." + dailyStatistic.get(i).getProjectName()
                    + "Total Chars【" + dailyStatistic.get(i).getInsertNum()
                    + "】，Revs【" + dailyStatistic.get(i).getDeleteNum() +
                    "】，WorkTime " + longToTimeString(dailyStatistic.get(i).getUseTime()) + "");

            allNum = allNum + dailyStatistic.get(i).getInsertNum().intValue() + dailyStatistic.get(i).getDeleteNum().intValue();
            deleteNum = deleteNum + dailyStatistic.get(i).getDeleteNum().intValue();
            useTime = useTime + dailyStatistic.get(i).getUseTime();
        }
        long minTime = 0;
        long maxTime = 0;
        long workTime = 0;
        if (dailyStatistic != null && dailyStatistic.size() > 0) {
            minTime = dailyStatistic.stream().findFirst().get().getBeginTime();
            maxTime = dailyStatistic.stream().max(Comparator.comparingLong(DailyStatistic::getEndTime)).get().getEndTime();
            workTime = maxTime - minTime;
        }
        int timeRatio = workTime > 0 ? Math.round((float) useTime / workTime * 100) : 0;

        teContent.append("\nToday's Activity");
        teContent.append(String.format("\n  • IDEA Active Time：%s \n  • Idle Time：%s\n  • Time Efficiency：%d%%",
                longToTimeString(useTime), longToTimeString(workTime - useTime), timeRatio));

        teContent.insert(1, String.format("\n  • Total Characters：%d \n  • Total Revisions：%d\n", allNum, deleteNum));
        teContent.insert(1, "Project Metrics");

        //删除当天记录，待修改
        return teContent;
    }

    public StringBuffer getDailyStatisticTeContentZh(Long beginTime, Long endTime) {
        FocusDispatcher focusDispatcher = new FocusDispatcher();
        focusDispatcher.onIdeaLostFocus(false);

        List<DailyStatistic> dailyStatistic = dailyStatisticService.getOneDayDailyStatistic(beginTime, endTime);
        if (dailyStatistic == null) {
            dailyStatistic = new ArrayList<>();
        }

        teContent = new StringBuffer();
        teContent.append("\n项目详情");
        int allNum = 0;
        int deleteNum = 0;
        long useTime = 0;
        for (int i = 0; i < dailyStatistic.size(); i++) {
            teContent.append("\n  " + (i + 1) + "." + dailyStatistic.get(i).getProjectName()
                    + "总字符量【" + dailyStatistic.get(i).getInsertNum()
                    + "】，回退数【" + dailyStatistic.get(i).getDeleteNum() +
                    "】，占用时间" + longToTimeString(dailyStatistic.get(i).getUseTime()) + "");

            allNum = allNum + dailyStatistic.get(i).getInsertNum().intValue() + dailyStatistic.get(i).getDeleteNum().intValue();
            deleteNum = deleteNum + dailyStatistic.get(i).getDeleteNum().intValue();
            useTime = useTime + dailyStatistic.get(i).getUseTime();
        }
        long minTime = 0;
        long maxTime = 0;
        long workTime = 0;
        if (dailyStatistic != null && dailyStatistic.size() > 0) {
            minTime = dailyStatistic.stream().findFirst().get().getBeginTime();
            maxTime = dailyStatistic.stream().max(Comparator.comparingLong(DailyStatistic::getEndTime)).get().getEndTime();
            workTime = maxTime - minTime;
        }
        int timeRatio = workTime > 0 ? Math.round((float) useTime / workTime * 100) : 0;

        teContent.append("\n今日使用详情");
        teContent.append(String.format("\n  • IDEA使用时间：%s \n  • 闲置时间：%s\n  • 时间利用率%d%%",
                longToTimeString(useTime), longToTimeString(workTime - useTime), timeRatio));

        teContent.insert(1, String.format("\n  • 总字符量：%d \n  • 回退数：%d\n", allNum, deleteNum));
        teContent.insert(1, "项目统计概览");

        //删除当天记录，待修改
        return teContent;

    }

    /*
    public StringBuffer getDailyStatisticTeContent(String typeSelected) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);


        Long endTime = calendar.getTime().getTime()+86399000; //60s*60*24*1000-1000
        if(StatisticType.WEEK.equals(typeSelected)) {
            calendar.add(Calendar.DATE, -7);
            endTime = calendar.getTime().getTime()+604799000;//7*86400000-1000
        }

        Long beginTime = calendar.getTime().getTime();

        //日志代码，发布前删除
        Date date1 = new Date(beginTime);
        Date date2 = new Date(endTime);
        // 格式化日期
        java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        System.out.println("Formatted Dategin: " + formatter.format(date1));
        System.out.println("Formatted DateEnd:  " + formatter.format(date2));
        //正式删除

        List<DailyStatistic> dailyStatistic = dailyStatisticService.getOneDayDailyStatistic(beginTime, endTime);
        if(dailyStatistic == null) dailyStatistic=new ArrayList<>() ;

        teContent = new StringBuffer();
        teContent.append("\n以下是按项目展示内容:");
        int allNum = 0;
        int deleteNum = 0;
        long useTime = 0;
        for (DailyStatistic data : dailyStatistic) {
            teContent.append("\n  【" + data.getProjectName() + "】总字符量【" + data.getInsertNum() + "】，回退数【" + data.getDeleteNum() + "】，占用时间【" + longToTimeString(data.getUseTime()) + "】");
            allNum = allNum + data.getInsertNum().intValue() + data.getDeleteNum().intValue();
            ;
            deleteNum = deleteNum + data.getDeleteNum().intValue();
            ;
            useTime = useTime + data.getUseTime();
        }
        long minTime = 0;
        long maxTime = 0;
        long workTime = 0;
        if (dailyStatistic != null && dailyStatistic.size() > 0) {
            minTime = dailyStatistic.stream().findFirst().get().getBeginTime();
            maxTime = dailyStatistic.get(dailyStatistic.size() - 1).getEndTime();
            workTime = maxTime - minTime;
        }
        int timeRatio = workTime > 0 ? Math.round((float) useTime / workTime * 100)  : 0;

        teContent.append(String.format("\n您今天使用idea【%s】，闲置时间【%s】，时间利用率【%d%%】",
                longToTimeString(useTime), longToTimeString(workTime - useTime),timeRatio));
        teContent.append(String.format("\n总字符量【%d】，回退数【%d】", allNum, deleteNum, timeRatio));

        //删除当天记录，待修改
        return teContent;

    }
    */
    public String longToTimeString(long timestamp) {
        timestamp = timestamp / 1000;
        // 除以3600秒(1小时)得到小时数
        long hours = timestamp / 3600;
        // 计算分钟数
        long minutes = (timestamp % 3600) / 60;
        // 剩余的秒数
        long secs = timestamp % 60;
        return String.format("%d" + message("common.hour")
                + "%d" + message("common.minutes")
                + "%d" + message("common.seconds"), hours, minutes, secs);
        //return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }


}

