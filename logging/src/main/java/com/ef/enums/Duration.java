package com.ef.enums;

public enum Duration {
	
	HOURLY, DAILY;
	
	public static Duration fromString(String duration) {
		switch(duration) {
			case "daily": return DAILY;
			case "hourly": return HOURLY;
		}
		throw new RuntimeException("could not find a proper duration");
	}

}
