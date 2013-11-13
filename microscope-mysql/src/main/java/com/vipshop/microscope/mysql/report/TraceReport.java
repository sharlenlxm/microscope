package com.vipshop.microscope.mysql.report;

import java.math.BigDecimal;
import java.math.RoundingMode;

import com.vipshop.microscope.common.util.CalendarUtil;

public class TraceReport {
	
	private String id;
	private int year;
	private int month;
	private int week;
	private int day;
	private int hour;
	private String type;
	private String name;
	private long totalCount;
	private long failureCount;
	private float failurePrecent;
	private float min;
	private float max;
	private float avg;
	private float tps;
	
	private long sum;
	private long startTime;
	private long endTime;
	
	private long duration;
	
	
	public static String makeId(CalendarUtil calendarUtil, String traceName) {
		return calendarUtil.uniqueTimeStamp() + "-" + traceName;
	}
	
	public static String makePreId(CalendarUtil calendarUtil, String traceName) {
		return calendarUtil.uniquePreTimeStamp() + "-" + traceName;
	}
	
	public static float makeTPS(TraceReport report) {
		BigDecimal count = new BigDecimal(report.getTotalCount() * 1000);
		BigDecimal time = new BigDecimal(report.getDuration());
		return count.divide(time, 3, RoundingMode.HALF_DOWN).floatValue();
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public String getType() {
		return type;
	}

	public void setType(String name) {
		this.type = name;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	public long getFailureCount() {
		return failureCount;
	}

	public void setFailureCount(long failureCount) {
		this.failureCount = failureCount;
	}

	public float getFailurePrecent() {
		return failurePrecent;
	}

	public void setFailurePrecent(float failurePrecent) {
		this.failurePrecent = failurePrecent;
	}

	public float getMin() {
		return min;
	}

	public void setMin(float min) {
		this.min = min;
	}

	public float getMax() {
		return max;
	}

	public void setMax(float max) {
		this.max = max;
	}

	public float getAvg() {
		return avg;
	}

	public void setAvg(float avg) {
		this.avg = avg;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWeek() {
		return week;
	}

	public void setWeek(int week) {
		this.week = week;
	}

	public float getTps() {
		return tps;
	}

	public void setTps(float tps) {
		this.tps = tps;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getStartTime() {
		return startTime;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public long getEndTime() {
		return endTime;
	}

	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}

	public long getSum() {
		return sum;
	}

	public void setSum(long sum) {
		this.sum = sum;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	@Override
	public String toString() {
		return "TraceReport [id=" + id + ", year=" + year + ", month=" + month + ", week=" + week + ", day=" + day + ", hour=" + hour + ", type=" + type + ", name=" + name + ", totalCount="
				+ totalCount + ", failureCount=" + failureCount + ", failurePrecent=" + failurePrecent + ", min=" + min + ", max=" + max + ", avg=" + avg + ", tps=" + tps + ", sum=" + sum
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", duration=" + duration + "]";
	}
	
	public String toReportUseName() {
		return "{'name':'" + name + "', 'totalCount':'" + totalCount + "', 'failureCount':'" + failureCount + "', 'failurePrecent':'" + failurePrecent + "', 'min':'" + min + "', 'max':'" + max + "', 'avg':'" + avg + "', 'tps':'" + tps + "'}";
	}
	
	public String toReportUseType() {
		return "{'type':" + type + ", 'totalCount':" + totalCount + ", failureCount:" + failureCount + ", failurePrecent:" + failurePrecent + ", min:" + min + ", max:" + max + ", avg:" + avg + ", tps:" + tps + "}";
	}

}