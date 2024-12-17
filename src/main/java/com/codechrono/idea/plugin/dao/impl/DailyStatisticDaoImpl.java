package com.codechrono.idea.plugin.dao.impl;

import com.codechrono.idea.plugin.dao.BaseDAO;
import com.codechrono.idea.plugin.dao.DailyStatisticDao;
import com.codechrono.idea.plugin.entity.DailyStatistic;

import java.sql.Connection;
import java.util.List;

public class DailyStatisticDaoImpl extends BaseDAO<DailyStatistic> implements DailyStatisticDao {
    private static DailyStatisticDaoImpl instance;
    private static final String SELECT_SQL = "select begin_time,code_ratio from daily_statistic ";

    private DailyStatisticDaoImpl() {
    }

    public static synchronized DailyStatisticDaoImpl getInstance() {
        if (instance == null) {
            instance = new DailyStatisticDaoImpl();
        }
        return instance;
    }


    @Override
    public List<DailyStatistic> queryOneDayEditRecord(Connection conn, Long beginTime) {
        String sql = "SELECT SUM(insert_num) insert_num,\n" +
                "       SUM(delete_num) delete_num,\n" +
                "       round(SUM(insert_num) * 1.0 / (SUM(insert_num) + SUM(delete_num)), 2) code_ratio,\n" +
                "       project_name\n" +
                "FROM (select sum(edit_num) insert_num, 0 delete_num, project_name\n" +
                "      from edit_record\n" +
                "      where create_time > ?\n" +
                "        and edit_type = \"INSERT\"\n" +
                "      group by project_name\n" +
                "      UNION\n" +
                "      select 0 insert_num, sum(edit_num) delete_num, project_name\n" +
                "      from edit_record\n" +
                "      where create_time > ?\n" +
                "        and edit_type = \"DELETE\"\n" +
                "      group by project_name)\n" +
                "group by project_name";
        return queryList(conn, sql, beginTime, beginTime);

    }

    @Override
    public List<DailyStatistic> getDailyStatistic(Connection conn, Long beginTime,Long endTime) {
        String sql="select min(begin_time) begin_time,max(end_time) end_time,sum(insert_num) insert_num,sum(delete_num) delete_num,sum(use_time) use_time,project_name,statistic_type\n" +
                "from daily_statistic\n" +
                "where begin_time >= ? \n" +
                "    and begin_time < ? \n"+
                "group by project_name,statistic_type \n" +
                "order by begin_time asc;";
        return queryList(conn, sql, beginTime, endTime);
    }

    @Override
    public void insert(Connection conn, DailyStatistic[] ary) {
        if (ary == null || ary.length == 0) {
            return;
        }
        String sql = "insert into daily_statistic(begin_time,end_time,statistic_type,insert_num,delete_num,code_ratio,use_time,idle_time,time_ratio,project_name) values(?,?,?,?,?,?,?,?,?,?)";
        int size = ary.length;
        if (size == 1) {
            DailyStatistic dailyStatistic = ary[0];
            update(conn, sql, dailyStatistic.getBeginTime(), dailyStatistic.getEndTime(), dailyStatistic.getStatisticType(), dailyStatistic.getInsertNum(), dailyStatistic.getDeleteNum(), dailyStatistic.getCodeRatio(), dailyStatistic.getUseTime(), dailyStatistic.getIdleTime(), dailyStatistic.getTimeRatio(),dailyStatistic.getProjectName());
        } else {
            Object[][] args = new Object[size][12];
            for (int i = 0; i < size; i++) {
                DailyStatistic dailyStatistic = ary[i];
                args[i][0] = dailyStatistic.getBeginTime();
                args[i][1] = dailyStatistic.getEndTime();
                args[i][2] = dailyStatistic.getStatisticType();
                args[i][3] = dailyStatistic.getInsertNum();
                args[i][4] = dailyStatistic.getDeleteNum();
                args[i][5] = dailyStatistic.getCodeRatio();
                args[i][6] = dailyStatistic.getUseTime();
                args[i][7] = dailyStatistic.getIdleTime();
                args[i][8] = dailyStatistic.getTimeRatio();
                args[i][9] = dailyStatistic.getProjectName();

            }
            updateBatch(conn, sql, args);
        }
    }

    @Override
    public DailyStatistic insert(Connection conn, DailyStatistic dailyStatistic) {
        if (dailyStatistic == null) {
            return null;
        }
        String sql = "insert into daily_statistic(begin_time,end_time,statistic_type,insert_num,delete_num,code_ratio,use_time,idle_time,time_ratio,project_name) values(?,?,?,?,?,?,?,?,?,?)";

        update(conn, sql, dailyStatistic.getBeginTime(), dailyStatistic.getEndTime(), dailyStatistic.getStatisticType(), dailyStatistic.getInsertNum(), dailyStatistic.getDeleteNum(), dailyStatistic.getCodeRatio(), dailyStatistic.getUseTime(), dailyStatistic.getIdleTime(), dailyStatistic.getTimeRatio(),dailyStatistic.getProjectName());
        return dailyStatistic;
    }
        @Override
        public void delete (Connection conn, Integer id){

        }

        @Override
        public void update (Connection conn, DailyStatistic[]ary){

        }

        @Override
        public void update (Connection conn, DailyStatistic dailyStatistic){

        }

        @Override
        public DailyStatistic findById (Connection conn, Integer id){
            return null;
        }

        @Override
        public void exchangeShowOrder (Connection conn, Integer showOrder1, Integer showOrder2){

        }
    }
