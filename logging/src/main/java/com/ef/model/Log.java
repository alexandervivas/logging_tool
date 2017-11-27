package com.ef.model;

import org.joda.time.DateTime;

import com.ef.util.DateTimeUtils;

public class Log {
	
	private DateTime date;
	private String ip;
	private String request;
	private int status;
	private String userAgent;
	
	public Log(DateTime date, String ip, String request, int status, String userAgent) {
		this.date = date;
		this.ip = ip;
		this.request = request;
		this.status = status;
		this.userAgent = userAgent;
	}

	public static Log fromString(String logStr) {
		
		String[] values = logStr.split("\\|");
		
		return new Log(DateTimeUtils.getDate(values[0]), values[1], values[2], Integer.parseInt(values[3]), values[4]);
		
	}
	
	public String getIp() {
		return ip;
	}
	
	public DateTime getDate() {
		return date;
	}

	@Override
	public String toString() {
		return "Log [date=" + date + ", ip=" + ip + ", request=" + request + ", status=" + status + ", userAgent="
				+ userAgent + "]";
	}

}
