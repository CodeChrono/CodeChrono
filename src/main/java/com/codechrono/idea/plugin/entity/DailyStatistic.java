package com.codechrono.idea.plugin.entity;


public class DailyStatistic {
    private Long beginTime;
    private Long endTime;
    private String statisticType;
    private Number insertNum;
    private Number deleteNum;
    private Number codeRatio;
    private Integer useTime;
    private Number idleTime;
    private Number timeRatio;
    private long threadId;

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    private String projectName;

    public long getThreadId() {
        return threadId;
    }

    public void setThreadId(long threadId) {
        this.threadId = threadId;
    }

    public String getStatisticType() {
        return statisticType;
    }

    public void setStatisticType(String statisticType) {
        this.statisticType = statisticType;
    }

    public Number getInsertNum() {
        return insertNum;
    }

    public void setInsertNum(Number insertNum) {
        this.insertNum = insertNum;
    }

    public Number getDeleteNum() {
        return deleteNum;
    }

    public void setDeleteNum(Number deleteNum) {
        this.deleteNum = deleteNum;
    }

    public Number getCodeRatio() {
        return codeRatio;
    }

    public void setCodeRatio(Number codeRatio) {
        this.codeRatio = codeRatio;
    }

    public Number getIdleTime() {
        return idleTime;
    }

    public void setIdleTime(Number idleTime) {
        this.idleTime = idleTime;
    }

    public Number getTimeRatio() {
        return timeRatio;
    }

    public void setTimeRatio(Number timeRatio) {
        this.timeRatio = timeRatio;
    }
    public String tostring(){
        return "DailyStatistic{" +
                "beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", statisticType=" + statisticType +
                ", insertNum=" + insertNum +
                ", deleteNum=" + deleteNum +
                ", codeRatio=" + codeRatio +
                ", useTime=" + useTime +
                ", idleTime=" + idleTime +
                ", timeRatio=" + timeRatio +
                '}';
    }

    public Integer getUseTime() {
        return useTime;
    }

    public void setUseTime(Integer useTime) {
        this.useTime = useTime;
    }
}
