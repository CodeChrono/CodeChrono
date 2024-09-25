package com.codechrono.idea.plugin.service.impl;

import com.codechrono.idea.plugin.dao.EditRecordDao;
import com.codechrono.idea.plugin.dao.impl.EditRecordDaoImpl;
import com.codechrono.idea.plugin.entity.EditRecord;
import com.codechrono.idea.plugin.service.DatabaseBasicService;
import com.codechrono.idea.plugin.service.EditRecordInterface;
import com.intellij.openapi.application.ApplicationManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Codechrono
 */
public class EditRecordService implements EditRecordInterface {
    private final DatabaseBasicService databaseBasicService = ApplicationManager.getApplication().getService(DatabaseBasicService.class);
    private final EditRecordDao editRecordDao = EditRecordDaoImpl.getInstance();

    public static EditRecordService getInstance() {
        return ApplicationManager.getApplication().getService(EditRecordService.class);
    }

    private EditRecordService() {
    }

    @Override
    public void insert(EditRecord[] ary) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            editRecordDao.insert(conn, ary);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
    }

    @Override
    public EditRecord insert(EditRecord editRecord) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return editRecordDao.insert(conn, editRecord);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            editRecordDao.delete(conn, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
    }

    @Override
    public void update(EditRecord[] ary) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            editRecordDao.update(conn, ary);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
    }

    @Override
    public void update(EditRecord editRecord) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            editRecordDao.update(conn, editRecord);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
    }

    @Override
    public EditRecord findById(Integer id) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return editRecordDao.findById(conn, id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;
    }

    @Override
    public void exchangeShowOrder(Integer showOrder1, Integer showOrder2) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            editRecordDao.exchangeShowOrder(conn, showOrder1, showOrder2);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
    }


    public EditRecord findByTitle(String title) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return editRecordDao.findByTitle(conn, title);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;
    }


    public List<EditRecord> findAll() {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return editRecordDao.findAll(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;
    }


    public List<String> getTitles() {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return editRecordDao.getTitles(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;
    }


    @Override
    public void deleteAllById(Integer id) {

    }

    @Override
    public EditRecord findFirst() {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return editRecordDao.findFirst(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;
    }

}
