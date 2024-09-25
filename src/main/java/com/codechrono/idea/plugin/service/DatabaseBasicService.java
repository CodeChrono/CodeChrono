package com.codechrono.idea.plugin.service;


import com.codechrono.idea.plugin.constant.Constant;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ColumnListHandler;

import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * 需要两个依赖jar
 * commons-dbcp.jar
 * commons-pool.jar
 */
public class DatabaseBasicService {
    private static final String DATABASE_DRIVER = "org.sqlite.JDBC";
    private static final String DATABASE_URL = "jdbc:sqlite:" + Constant.DB_FILE_PATH;
    private static final String EDITOR_OFFSET_START = "offset_start";
    private static final String EDITOR_OFFSET_END = "offset_end";
    private static final String IMAGE_RECORDS = "image_records";
    private BasicDataSource source;

    public DatabaseBasicService() {
        try {
            //创建了DBCP的数据库连接池
            source = new BasicDataSource();
            //设置基本信息
            source.setMaxActive(1);
            source.setDriverClassName(DATABASE_DRIVER);
            source.setUrl(DATABASE_URL);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 如果不存在,创建DB文件
        createFileAndDir();
        // 如果表不存在,创建表
        initTable();
    }


    public BasicDataSource getSource() {
        return source;
    }

    public Connection getConnection() throws SQLException {
        return source.getConnection();
    }

    /**
     * 释放资源
     */
    public void closeResource(Connection conn, Statement statement, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果不存在目录和文件就创建
     */
    private void createFileAndDir() {
        //"C:\Users\Administrator\.ideaNotebooksFile"
        if (!Files.exists(Constant.PROJECT_DB_DIRECTORY_PATH)) {
            try {
                Files.createDirectories(Constant.PROJECT_DB_DIRECTORY_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //"C:\Users\Administrator\.ideaNotebooksFile\notebooks.db"
        if (!Files.exists(Constant.DB_FILE_PATH)) {
            try {
                Files.createFile(Constant.DB_FILE_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //创建图片文件夹.
        if (!Files.exists(Constant.IMAGE_DIRECTORY_PATH)) {
            try {
                Files.createDirectories(Constant.IMAGE_DIRECTORY_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //创建图片临时文件夹.
        if (!Files.exists(Constant.TEMP_IMAGE_DIRECTORY_PATH)) {
            try {
                Files.createDirectories(Constant.TEMP_IMAGE_DIRECTORY_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 如果不存在就初始化表
     */
    private void initTable() {
        String createEditRecordSQL = "CREATE TABLE IF NOT EXISTS edit_record (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "edit_type TEXT," +
                "edit_num INTEGER," +
                "content TEXT," +
                "project TEXT," +
                "project_name TEXT," +
                "create_time INTEGER" +
                ")";


        String createDailyStatisticQL = "CREATE TABLE IF NOT EXISTS daily_statistic (" +
                "id INTEGER  PRIMARY KEY AUTOINCREMENT," +
                "begin_time INTEGER," +
                "end_time INTEGER," +
                "statistic_type TEXT," +
                "insert_num INTEGER," +
                "delete_num INTEGER," +
                "code_ratio INTEGER," +
                "use_time INTEGER," +
                "idle_time INTEGER," +
                "time_ratio INTEGER," +
                "project_name TEXT" +
                ")";
        String createKeyChronoSQL = "CREATE TABLE IF NOT EXISTS key_chrono (" +

                "key_code   varchar(29) not null primary key," +
                "input      varchar(25) not null," +
                "created_at LONG        not null," +
                "updated_at LONG        not null"+
                ")";
        try {
            QueryRunner queryRunner = new QueryRunner(getSource());
            queryRunner.update(createEditRecordSQL);
            queryRunner.update(createDailyStatisticQL);
            queryRunner.update(createKeyChronoSQL);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    /**
     * 判断是否存在某个字段的方法
     *
     * @param table       表
     * @param column      字段/列
     * @param queryRunner 查询
     * @return true 存在该字段 false 不存在该字段
     * @throws SQLException sql异常
     */
    private boolean isColumnExists(String table, String column, QueryRunner queryRunner) throws SQLException {
        boolean isExists = false;
        List<String> name = queryRunner.query("PRAGMA table_info(" + table + ")", new ColumnListHandler<String>("name"));
        for (String s : name) {
            if (column.equalsIgnoreCase(s)) {
                isExists = true;
                break;
            }
        }
        return isExists;
    }
}
