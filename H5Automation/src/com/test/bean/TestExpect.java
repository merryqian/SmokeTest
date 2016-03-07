package com.test.bean;

public class TestExpect {
	private String className;
	private String methodName;
	private String expect;
	private String actual;
	private String bussinessDesp;
	
	public String getResult(){
		return (expect.equals(actual)?"PASS":"FAIL");
	}
	public String getPackageName(){
		return className+"."+methodName;
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	public String getExpect() {
		return expect;
	}
	public void setExpect(String expect) {
		this.expect = expect;
	}
	public String getActual() {
		return actual;
	}
	public void setActual(String actual) {
		this.actual = actual;
	}
	public String getBussinessDesp() {
		return bussinessDesp;
	}

	public void setBussinessDesp(String bussinessDesp) {
		this.bussinessDesp = bussinessDesp;
	}

	@Override
	public String toString() {
		return "TestExpect [className=" + className + ", methodName="
				+ methodName + ", expect=" + expect + ", actual=" + actual
				+ "]";
	}
	
}
