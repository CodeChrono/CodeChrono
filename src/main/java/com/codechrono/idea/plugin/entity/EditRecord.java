package com.codechrono.idea.plugin.entity;

import java.util.Date;

public class EditRecord {

    private Date createTime;
    private EditType editType;
    private Number editNum;
    private String content;
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public EditType getEditType() {
        return editType;
    }

    public void setEditType(EditType editType) {
        this.editType = editType;
    }

    public Number getEditNum() {
        return editNum;
    }

    public void setEditNum(Number editNum) {
        this.editNum = editNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


}
