package com.codechrono.idea.plugin.entity;


public class EditRecord {
    private Integer id;

    private Long createTime;
    private String editType;
    private Number editNum;
    private String content;
    private String projectName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }



    public EditType getEditType() {
        return EditType.fromString(this.editType);
    }

    public void setEditType(EditType editType) {
        this.editType = editType.toString();
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
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }




}
