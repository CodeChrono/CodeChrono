package com.codechrono.idea.plugin.dao.impl;

import com.codechrono.idea.plugin.dao.BaseDAO;
import com.codechrono.idea.plugin.dao.EditRecordDao;
import com.codechrono.idea.plugin.entity.EditRecord;
import java.sql.Connection;
import java.util.List;

/**
 * @author Codechrono
 */
public class EditRecordDaoImpl extends BaseDAO<EditRecord> implements EditRecordDao {
    private static EditRecordDaoImpl instance;

    private EditRecordDaoImpl() {
    }

    public static synchronized EditRecordDaoImpl getInstance() {
        if (instance == null) {
            instance = new EditRecordDaoImpl();
        }
        return instance;
    }

    private static final String SELECT_SQL = "select * from edit_record ";

    @Override
    public void insert(Connection conn, EditRecord[] notes) {
        if (notes == null || notes.length == 0) {
            return;
        }
        String sql = "insert into edit_record(edit_type,edit_num,content,project_name,create_time) values(ifnull(?,3),?,?,?,?)";
        int size = notes.length;
        if (size == 1) {
            EditRecord note = notes[0];
            update(conn, sql, note.getEditType(), note.getEditNum(), note.getContent(),  note.getProjectName(), note.getCreateTime());
        } else {
            Object[][] args = new Object[size][12];
            for (int i = 0; i < size; i++) {
                EditRecord note = notes[i];
                args[i][0] = note.getEditType();
                args[i][1] = note.getEditNum();
                args[i][2] = note.getContent();
                args[i][3] = note.getProjectName();
                args[i][4] = note.getCreateTime();

            }
            updateBatch(conn, sql, args);
        }
    }

    @Override
    public EditRecord insert(Connection conn, EditRecord note) {
        String sql = "insert into edit_record(edit_type,edit_num,content,project_name,create_time) values(ifnull(?,3),?,?,?,?) ";
        update(conn, sql, note.getEditType(), note.getEditNum(), note.getContent(),  note.getProjectName(), note.getCreateTime());
        String sqlSelect = SELECT_SQL + "  limit 1;";
        return getBean(conn, sqlSelect);
    }

    @Override
    public void delete(Connection conn, Integer id) {
        String sql = "delete from edit_record where id =?";
        update(conn, sql, id);
    }

    @Override
    public void update(Connection conn, EditRecord[] editRecords) {
        if (editRecords == null || editRecords.length == 0) {
            return;
        }
        String sql = "update edit_record set edit_type=?,edit_num=?,content=?,project_name=?,create_time=? where id =?";
        int size = editRecords.length;
        if (size == 1) {
            EditRecord editRecord = editRecords[0];
            update(conn, sql, editRecord.getEditType(), editRecord.getEditNum(), editRecord.getContent(),  editRecord.getProjectName(), editRecord.getCreateTime());
        } else {
            Object[][] args = new Object[size][14];
            for (int i = 0; i < size; i++) {
                EditRecord note = editRecords[i];
                args[i][0] = note.getEditType();
                args[i][1] = note.getEditNum();
                args[i][2] = note.getContent();
                args[i][3] = note.getProjectName();
                args[i][4] = note.getCreateTime();
            }
            updateBatch(conn, sql, args);
        }
    }

    @Override
    public void update(Connection conn, EditRecord editRecord) {
        String sql = "update edit_record set edit_type=?,edit_num=?,content=?,project_name=?,create_time=? where id =?";
        update(conn, sql, editRecord.getEditType(), editRecord.getEditNum(), editRecord.getContent(),  editRecord.getProjectName(), editRecord.getCreateTime());
    }

    @Override
    public EditRecord findById(Connection conn, Integer id) {
        String sql = SELECT_SQL + "where id =? limit 1";
        return getBean(conn, sql, id);
    }

    @Override
    public void exchangeShowOrder(Connection conn, Integer showOrder1, Integer showOrder2) {

        String sql = "update edit_record set show_order = (case when show_order = ? then ? when show_order = ? then ? end) where show_order = ? or show_order = ?";
        update(conn, sql, showOrder1, showOrder2, showOrder2, showOrder1, showOrder1, showOrder2);

    }


    @Override
    public EditRecord findByTitle(Connection conn, String name) {
        return null;
    }

    @Override
    public List<EditRecord> findAll(Connection connection) {

        return null;
    }

    @Override
    public List<String> getTitles(Connection conn) {
        return null;
    }

    @Override
    public EditRecord findFirst(Connection conn) {
        String sql = "select create_time from edit_record order by id limit 1";
        return getBean(conn, sql);
    }


}
