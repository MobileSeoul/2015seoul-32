package com.menew.seoul_women_lecture;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;

/**
 * Created by Administrator on 2015-10-26.
 */
public class Lecture implements Serializable {
    private String title;
    private String organ;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String level;
    private String receiveStartDate;
    private String receiveEndDate;
    private String receiveStartTime;
    private String receiveEndTime;
    private String fee;
    private String collectNum;
    private String spareNum;
    private String visitReceiveFlag;
    private String onlineREceiveFlag;
    private String url;
    private String monday;
    private String tuesday;
    private String wednesday;
    private String thursday;
    private String friday;
    private String saturday;
    private String sunday;

    public Lecture(String title, String organ, String startDate, String endDate,
                   String startTime, String endTime, String level, String receiveStartDate,
                   String receiveEndDate, String receiveStartTime, String receiveEndTime,
                   String fee, String collectNum, String spareNum, String visitReceiveFlag,
                   String onlineReceiveFlag, String url, String monday, String tuesday,
                   String wednesday, String thursday, String friday, String saturday, String sunday) {
        this.title = title;
        this.organ = organ;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.level = level;
        this.receiveStartDate = receiveStartDate;
        this.receiveEndDate = receiveEndDate;
        this.receiveStartTime = receiveStartTime;
        this.receiveEndTime = receiveEndTime;
        this.fee = fee;
        this.collectNum = collectNum;
        this.spareNum = spareNum;
        this.visitReceiveFlag = visitReceiveFlag;
        this.onlineREceiveFlag = onlineReceiveFlag;
        this.url = url;
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getReceiveStartDate() {
        return receiveStartDate;
    }

    public void setReceiveStartDate(String receiveStartDate) {
        this.receiveStartDate = receiveStartDate;
    }

    public String getReceiveEndDate() {
        return receiveEndDate;
    }

    public void setReceiveEndDate(String receiveEndDate) {
        this.receiveEndDate = receiveEndDate;
    }

    public String getReceiveStartTime() {
        return receiveStartTime;
    }

    public void setReceiveStartTime(String receiveStartTime) {
        this.receiveStartTime = receiveStartTime;
    }

    public String getReceiveEndTime() {
        return receiveEndTime;
    }

    public void setReceiveEndTime(String receiveEndTime) {
        this.receiveEndTime = receiveEndTime;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(String collectNum) {
        this.collectNum = collectNum;
    }

    public String getSpareNum() {
        return spareNum;
    }

    public void setSpareNum(String spareNum) {
        this.spareNum = spareNum;
    }

    public String getVisitReceiveFlag() {
        return visitReceiveFlag;
    }

    public void setVisitReceiveFlag(String visitReceiveFlag) {
        this.visitReceiveFlag = visitReceiveFlag;
    }

    public String getOnlineREceiveFlag() {
        return onlineREceiveFlag;
    }

    public void setOnlineREceiveFlag(String onlineREceiveFlag) {
        this.onlineREceiveFlag = onlineREceiveFlag;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMonday() {
        return monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getTuesday() {
        return tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getWednesday() {
        return wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getThursday() {
        return thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getFriday() {
        return friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }

    public String getSunday() {
        return sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public int getStatus() {
        long receiveStart = getConvertTime(getReceiveStartDate(), getReceiveStartTime());
        long receiveEnd = getConvertTime(getReceiveEndDate(), getReceiveEndTime());
        long educateStart = getConvertTime(getStartDate(), getStartTime());
        long educateEnd = getConvertTime(getEndDate(), getEndTime());
        long current = System.currentTimeMillis();

        if(current > educateEnd) {
            return Const.STATUS_CLOSED;
        }
        else if(current >= educateStart && current <= educateEnd) {
            return Const.STATUS_EDUCATION;
        }
        else if(current >= receiveStart && current <= receiveEnd){
            return Const.STATUS_RESERVATION;
        }
        else {
            return Const.STATUS_UNKNOWN;
        }

    }

    private long getConvertTime(String date, String time) {
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4, 6));
        int day = Integer.parseInt(date.substring(6, 8));

        String[] arrTime = time.split(":");
        int hour = Integer.parseInt(arrTime[0]);
        int min = Integer.parseInt(arrTime[1]);

        Calendar cal = Calendar.getInstance();
        cal.set(year, month-1, day, hour, min);

        return cal.getTimeInMillis();
    }

    public long getReceiveStartDateTime() {
        return getConvertTime(getReceiveStartDate(), getReceiveStartTime());
    }
    public long getReceiveEndDateTime() {
        return getConvertTime(getReceiveEndDate(), getReceiveEndTime());
    }
    public long getEducateStartDateTime() {
        return getConvertTime(getStartDate(), getStartTime());
    }
    public long getEducateEndDateTime() {
        return getConvertTime(getEndDate(), getEndTime());
    }
}
