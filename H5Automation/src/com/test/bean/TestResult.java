package com.test.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by snail on 11/21/15.
 */
public class TestResult {
    private String date;
    private String startTime;
    private String endTime;
    private String duration;
    private String totalCount;
    private String passCount;
    private String failCount;
    private List<TestCase> item = new ArrayList<TestCase>();

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public String getPassCount() {
        return passCount;
    }

    public void setPassCount(String passCount) {
        this.passCount = passCount;
    }

    public String getFailCount() {
        return failCount;
    }

    public void setFailCount(String failCount) {
        this.failCount = failCount;
    }

    public List<TestCase> getItem() {
        return item;
    }

    public void setItem(List<TestCase> item) {
        this.item = item;
    }

    public void addTestItem(String bussinessDesp,String result,String remark){
        TestCase item = new TestCase();
        item.setBussinessDesp(bussinessDesp);
        item.setResult(result);
        item.setRemark(remark);
        getItem().add(item);
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "date='" + date + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", duration='" + duration + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", passCount='" + passCount + '\'' +
                ", failCount='" + failCount + '\'' +
                ", item=" + item +
                '}';
    }
}
