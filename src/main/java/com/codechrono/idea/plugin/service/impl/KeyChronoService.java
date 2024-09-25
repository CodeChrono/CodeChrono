package com.codechrono.idea.plugin.service.impl;

import com.codechrono.idea.plugin.dao.KeyChronoDao;

import com.codechrono.idea.plugin.dao.impl.KeyChronoDaoImpl;

import com.codechrono.idea.plugin.entity.KeyChrono;
import com.codechrono.idea.plugin.service.DatabaseBasicService;
import com.codechrono.idea.plugin.service.KeyChronoInterface;
import com.intellij.openapi.application.ApplicationManager;

import java.sql.Connection;
import java.sql.SQLException;

public class KeyChronoService implements KeyChronoInterface {
    private final DatabaseBasicService databaseBasicService = ApplicationManager.getApplication().getService(DatabaseBasicService.class);

    private final KeyChronoDao keychronoDao = KeyChronoDaoImpl.getInstance();
    public static KeyChronoService getInstance() {
        return ApplicationManager.getApplication().getService(KeyChronoService.class);
    }
    @Override
    public void insert(KeyChrono[] ary) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            keychronoDao.insert(conn, ary);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
    }

    @Override
    public KeyChrono insert(KeyChrono keyChrono) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return keychronoDao.insert(conn, keyChrono);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(KeyChrono[] ary) {

    }

    @Override
    public void update(KeyChrono keyChrono) {

    }

    @Override
    public KeyChrono findById(Integer id) {
        return null;
    }

    @Override
    public void exchangeShowOrder(Integer showOrder1, Integer showOrder2) {

    }

    @Override
    public KeyChrono findByKeyCode(String keyCode) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return keychronoDao.findByKeyCode(conn, keyCode);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;
    }
}
