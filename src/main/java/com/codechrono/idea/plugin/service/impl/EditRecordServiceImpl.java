package com.codechrono.idea.plugin.service.impl;

import com.codechrono.idea.plugin.dao.EditRecordDao;
import com.codechrono.idea.plugin.dao.impl.EditRecordDaoImpl;
import com.codechrono.idea.plugin.entity.EditRecord;
import com.codechrono.idea.plugin.service.DatabaseBasicService;
import com.codechrono.idea.plugin.service.EditRecordService;
import com.intellij.openapi.application.ApplicationManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author LeeWyatt
 */
public class EditRecordServiceImpl implements EditRecordService {
    private final DatabaseBasicService databaseBasicService = ApplicationManager.getApplication().getService(DatabaseBasicService.class);
    private final EditRecordDao notebookDao = EditRecordDaoImpl.getInstance();

    public static EditRecordServiceImpl getInstance() {
        return ApplicationManager.getApplication().getService(EditRecordServiceImpl.class);
    }

    private EditRecordServiceImpl() {
    }

    @Override
    public void insert(EditRecord[] ary) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            notebookDao.insert(conn, ary);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
    }

    @Override
    public EditRecord insert(EditRecord notebook) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            return notebookDao.insert(conn, notebook);
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
            notebookDao.delete(conn, id);
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
            notebookDao.update(conn, ary);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
    }

    @Override
    public void update(EditRecord notebook) {
        Connection conn = null;
        try {
            conn = databaseBasicService.getConnection();
            notebookDao.update(conn, notebook);
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
            return notebookDao.findById(conn, id);
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
            notebookDao.exchangeShowOrder(conn, showOrder1, showOrder2);
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
            return notebookDao.findByTitle(conn, title);
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
            return notebookDao.findAll(conn);
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
            return notebookDao.getTitles(conn);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            databaseBasicService.closeResource(conn, null, null);
        }
        return null;
    }


    @Override
    public List<EditRecord> findAllByChapterId(Integer chapterId) {
        return null;
    }

    @Override
    public void deleteAllByChapterId(Integer chapterId) {

    }

    @Override
    public void deleteAllByNotebookId(Integer notebookId) {

    }

    @Override
    public List<String> getTitles(String notebookTitle, String chapterTitle) {
        return null;
    }

    @Override
    public EditRecord findByTitle(String noteName, Integer chapterId) {
        return null;
    }

    @Override
    public EditRecord findByTitles(String noteTile, String chapterTitle, String notebookTitle) {
        return null;
    }

    @Override
    public List<String> getImageRecordsByNotebookId(int notebookId) {
        return null;
    }

    @Override
    public List<String> getImageRecordsByChapterId(int chapterId) {
        return null;
    }

    @Override
    public List<String> getImageRecordsByNoteId(int noteId) {
        return null;
    }


}
