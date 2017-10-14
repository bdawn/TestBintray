package com.xtech.gobike.commons.dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;

/**
 * 跑步记录
 */
@DatabaseTable(tableName = "running_record")
public class RunningRecord extends BaseData {
    //主键Id
    @DatabaseField(generatedId = true)
    protected int id;

    //平均速度 厘米/秒
    @DatabaseField(columnName = "avg_speed")
    private int avgSpeed;

    //最大速度 厘米/秒
    @DatabaseField(columnName = "max_speed")
    private int maxSpeed;

    //最小速度 厘米/秒
    @DatabaseField(columnName = "min_speed")
    private int minSpeed;

    //平均坡度 无单位
    @DatabaseField(columnName = "avg_slope")
    private int avgSlope;

    //最大坡度 无单位
    @DatabaseField(columnName = "max_slope")
    private int maxSlope;

    //最小坡度 无单位
    @DatabaseField(columnName = "min_slope")
    private int minSlope;

    //跑步距离 米
    @DatabaseField(columnName = "distance")
    private int distance;

    //卡路里 卡
    @DatabaseField(columnName = "calorie")
    private int calorie;

    //心率 次/分
    @DatabaseField(columnName = "heart_rate")
    private int heartRate;

    //步数
    @DatabaseField(columnName = "num_step")
    private int stepNum;

    //跑步持续时间(不包括暂停时间) 秒
    @DatabaseField(columnName = "duration")
    private long duration;

    //开始跑步时间
    @DatabaseField(columnName = "start_time")
    private Date startTime;

    //结束跑步时间
    @DatabaseField(columnName = "end_time")
    private Date endTime;

    //记录写入表的时间
    @DatabaseField(columnName = "record_time")
    private Date recordTime;

    //用户Id
    @DatabaseField(columnName = "user_id")
    private String userId;

    //记录的上传标志位 0-未上传 1-上传中 2-已上传
    @DatabaseField(columnName = "upload_status")
    private int uploadStatus;

    //记录的存在标志位 0-存在状态 1-删除状态
    @DatabaseField(columnName = "delete_status")
    private int deleteStatus;

    public RunningRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAvgSpeed() {
        return avgSpeed;
    }

    public void setAvgSpeed(int avgSpeed) {
        this.avgSpeed = avgSpeed;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public int getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(int minSpeed) {
        this.minSpeed = minSpeed;
    }

    public int getAvgSlope() {
        return avgSlope;
    }

    public void setAvgSlope(int avgSlope) {
        this.avgSlope = avgSlope;
    }

    public int getMaxSlope() {
        return maxSlope;
    }

    public void setMaxSlope(int maxSlope) {
        this.maxSlope = maxSlope;
    }

    public int getMinSlope() {
        return minSlope;
    }

    public void setMinSlope(int minSlope) {
        this.minSlope = minSlope;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getHeartRate() {
        return heartRate;
    }

    public void setHeartRate(int heartRate) {
        this.heartRate = heartRate;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(Date recordTime) {
        this.recordTime = recordTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getUploadStatus() {
        return uploadStatus;
    }

    public void setUploadStatus(int uploadStatus) {
        this.uploadStatus = uploadStatus;
    }

    public int getDeleteStatus() {
        return deleteStatus;
    }

    public void setDeleteStatus(int deleteStatus) {
        this.deleteStatus = deleteStatus;
    }

    public int getStepNum() {
        return stepNum;
    }

    public void setStepNum(int stepNum) {
        this.stepNum = stepNum;
    }

    @Override
    public String toString() {
        return "RunningRecord{" +
                "id=" + id +
                ", avgSpeed=" + avgSpeed +
                ", maxSpeed=" + maxSpeed +
                ", minSpeed=" + minSpeed +
                ", avgSlope=" + avgSlope +
                ", maxSlope=" + maxSlope +
                ", minSlope=" + minSlope +
                ", distance=" + distance +
                ", calorie=" + calorie +
                ", heartRate=" + heartRate +
                ", duration=" + duration +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", recordTime=" + recordTime +
                ", userId='" + userId + '\'' +
                ", uploadStatus=" + uploadStatus +
                ", deleteStatus=" + deleteStatus +
                ", stepNum=" + stepNum +
                '}';
    }
}
