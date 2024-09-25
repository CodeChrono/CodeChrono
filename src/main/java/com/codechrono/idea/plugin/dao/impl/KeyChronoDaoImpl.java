package com.codechrono.idea.plugin.dao.impl;

import com.codechrono.idea.plugin.dao.BaseDAO;

import com.codechrono.idea.plugin.dao.KeyChronoDao;
import com.codechrono.idea.plugin.entity.KeyChrono;

import java.sql.Connection;

public class KeyChronoDaoImpl extends BaseDAO<KeyChrono> implements KeyChronoDao {
    private static KeyChronoDaoImpl instance;
    String SELECT_SQL = "select * from key_chrono ";

    private KeyChronoDaoImpl() {
    }

    public static synchronized KeyChronoDaoImpl getInstance() {
        if (instance == null) {
            instance = new KeyChronoDaoImpl();
        }
        return instance;
    }

    @Override
    public void insert(Connection conn, KeyChrono[] keyChronos) {
        if (keyChronos == null || keyChronos.length == 0) {
            return;
        }
        String sql = "insert into key_chrono(key_code,input,created_at,updated_at) values(?,?,?,?)";
        int size = keyChronos.length;
        if (size == 1) {
            KeyChrono keyChrono = keyChronos[0];
            update(conn, sql, keyChrono.getKeyCode(), keyChrono.getInput(), keyChrono.getCreatedAt(), keyChrono.getUpdatedAt());
        } else {
            Object[][] args = new Object[size][12];
            for (int i = 0; i < size; i++) {
                KeyChrono note = keyChronos[i];
                args[i][0] = note.getKeyCode();
                args[i][1] = note.getInput();
                args[i][2] = note.getCreatedAt();
                args[i][3] = note.getUpdatedAt();
            }
            updateBatch(conn, sql, args);
        }
    }

    @Override
    public KeyChrono insert(Connection conn, KeyChrono note) {
        String sql = "insert into key_chrono(key_code,input,created_at,updated_at) values(?,?,?,?)";
        update(conn, sql, note.getKeyCode(), note.getInput(), note.getCreatedAt(), note.getUpdatedAt());
        String sqlSelect = SELECT_SQL + "  limit 1;";
        return getBean(conn, sqlSelect);
    }

    @Override
    public void delete(Connection conn, Integer id) {

    }

    @Override
    public void update(Connection conn, KeyChrono[] ary) {

    }

    @Override
    public void update(Connection conn, KeyChrono keyChrono) {

    }

    @Override
    public KeyChrono findById(Connection conn, Integer id) {
        return null;
    }

    @Override
    public void exchangeShowOrder(Connection conn, Integer showOrder1, Integer showOrder2) {

    }

    @Override
    public KeyChrono findByKeyCode(Connection conn, String keyCode) {

        String sqlSelect = SELECT_SQL + "where key_code=?" + " limit 1;";
        if ("CodeChrono".equals(keyCode)) {
            sqlSelect = SELECT_SQL + " order by created_at desc limit 1;";
            return getBean(conn, sqlSelect);
        }
        return getBean(conn, sqlSelect, keyCode);
    }
}
