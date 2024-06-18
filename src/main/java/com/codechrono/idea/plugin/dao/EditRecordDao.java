package com.codechrono.idea.plugin.dao;

import com.codechrono.idea.plugin.entity.EditRecord;

import java.sql.Connection;
import java.util.List;

/**
 * @author LeeWyatt
 */
public interface EditRecordDao extends CommonDao<EditRecord> {

    EditRecord findByTitle(Connection conn, String name);

    List<EditRecord> findAll(Connection connection);

    List<String> getTitles(Connection conn);





}
