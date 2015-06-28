package com.science.coolclock.bean;

import java.io.Serializable;

/**
 * @description
 * 
 * @author 幸运Science 陈土燊
 * @school University of South China
 * @email chentushen.science@gmail.com,274240671@qq.com
 * @2015-6-21
 * 
 */

public class Clock implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String clockName; // 闹钟名称
	private String time; // 时间
	private String week; // 星期
	private String bells; // 铃声
	private String bellsSize; // 铃声大小
	private String clearClockVoice; // 关闭闹钟声音

	public Clock() {
		super();
	}

	public Clock(String clockName, String time, String week, String bells,
			String bellsSize, String clearClockVoice) {
		super();
		this.clockName = clockName;
		this.time = time;
		this.week = week;
		this.bells = bells;
		this.bellsSize = bellsSize;
		this.clearClockVoice = clearClockVoice;
	}

	public String getClockName() {
		return clockName;
	}

	public void setClockName(String clockName) {
		this.clockName = clockName;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public String getBells() {
		return bells;
	}

	public void setBells(String bells) {
		this.bells = bells;
	}

	public String getBellsSize() {
		return bellsSize;
	}

	public void setBellsSize(String bellsSize) {
		this.bellsSize = bellsSize;
	}

	public String getClearClockVoice() {
		return clearClockVoice;
	}

	public void setClearClockVoice(String clearClockVoice) {
		this.clearClockVoice = clearClockVoice;
	}

}
