package com.example.schedulerexample.model;

import java.io.Serializable;

public class AlarmSchedulerModel implements Serializable {

    private String mDay;
    private String mMonth;
    private String mYear;

    private String mHour;
    private String mMinute;

    private boolean isRungToday;
    private boolean isRecurring;

    public AlarmSchedulerModel(String mDay, String mMonth, String mYear, String mHour, String mMinute, boolean isRungToday, boolean isRecurring) {
        this.mDay = mDay;
        this.mMonth = mMonth;
        this.mYear = mYear;
        this.mHour = mHour;
        this.mMinute = mMinute;
        this.isRungToday = isRungToday;
        this.isRecurring = isRecurring;
    }

    public String getmDay() {
        return mDay;
    }

    public void setmDay(String mDay) {
        this.mDay = mDay;
    }

    public String getmMonth() {
        return mMonth;
    }

    public void setmMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public String getmYear() {
        return mYear;
    }

    public void setmYear(String mYear) {
        this.mYear = mYear;
    }

    public String getmHour() {
        return mHour;
    }

    public void setmHour(String mHour) {
        this.mHour = mHour;
    }

    public String getmMinute() {
        return mMinute;
    }

    public void setmMinute(String mMinute) {
        this.mMinute = mMinute;
    }

    public boolean isRungToday() {
        return isRungToday;
    }

    public void setRungToday(boolean rungToday) {
        isRungToday = rungToday;
    }

    public boolean isRecurring() {
        return isRecurring;
    }

    public void setRecurring(boolean recurring) {
        isRecurring = recurring;
    }
}
