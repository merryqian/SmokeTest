package com.test.bean;

/**
 * Created by snail on 11/21/15.
 */
public class TestCase {
    private String index;
    private String bussinessDesp;
    private String result;
    private String remark;

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getBussinessDesp() {
        return bussinessDesp;
    }

    public void setBussinessDesp(String bussinessDesp) {
        this.bussinessDesp = bussinessDesp;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "TestCase{" +
                "index='" + index + '\'' +
                ", bussinessDesp='" + bussinessDesp + '\'' +
                ", result='" + result + '\'' +
                ", remark='" + remark + '\'' +
                '}';
    }
}
