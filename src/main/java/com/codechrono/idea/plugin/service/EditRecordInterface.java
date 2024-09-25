package com.codechrono.idea.plugin.service;

import com.codechrono.idea.plugin.entity.EditRecord;

/**
 * @author Codechrono
 */
public interface EditRecordInterface extends CommonInterface<EditRecord> {


    /**
     * 删除记录
     * @param
     */
    void deleteAllById(Integer id);
    /**
     * 查询首条记录
     * @param
     */
    EditRecord findFirst();
}