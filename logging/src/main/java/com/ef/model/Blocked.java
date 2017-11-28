package com.ef.model;

import org.joda.time.DateTime;

public class Blocked {

	private String ip;
	private String comments;
	private DateTime date;
	
	public Blocked(String ip, String comments, DateTime date) {
		this.ip = ip;
		this.comments = comments;
		this.date = date;
	}

	public String getIp() {
		return ip;
	}

	public String getComments() {
		return comments;
	}

	public DateTime getDate() {
		return date;
	}
	
	@Override
	public String toString() {
		return "Blocked [ip=" + ip + ", comments=" + comments + ", date=" + date + "]";
	}

}
