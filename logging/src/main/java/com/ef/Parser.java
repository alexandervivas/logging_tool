package com.ef;

import java.util.List;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import com.ef.controller.ParserController;
import com.ef.enums.Duration;
import com.ef.model.Blocked;
import com.ef.util.DateTimeUtils;

public class Parser 
{
    public static void main( String[] args )
    {
    	
        ParserController control = new ParserController();
        
        String filename = "./access.log";
        DateTime startDate = null;
        Duration duration = null;
        int threshold = 0;
    	
    	for(String arg : args) {
    		
    		if(arg.startsWith("--accesslog=")) {
    			filename = arg.substring(12);
    		}
    		
    		if(arg.startsWith("--startDate=")) {
    			String date = arg.substring(12);
    			
    			if(date.charAt(10) == '.') {
    				date = date.substring(0, 10) + " " + date.substring(11);
    			}
    			
    			if(date.length() == 19) {
    				date += ".000";
    			}
    			
    			startDate = DateTimeUtils.getDate(date);
    		}
    		
    		if(arg.startsWith("--duration=")) {
    			duration = Duration.fromString(arg.substring(11));
    		}
    		
    		if(arg.startsWith("--threshold=")) {
    			threshold = Integer.parseInt(arg.substring(12));
    		}
    		
    	}
    	
    	DateTime endDate = DateTimeUtils.getEndDate(startDate, duration);
        
        control.loadFile(filename);
        List<Blocked> blockedIpsList = 
        		control
        		.getBlockedIps(startDate, endDate, threshold)
        		.entrySet()
        		.stream()
        		.map(entry -> new Blocked(entry.getKey(), entry.getValue() + " requests were made from " + entry.getKey() + ", blocking ip", DateTime.now()))
        		.collect(Collectors.toList());
        
        control.reportIps(blockedIpsList);
        
        blockedIpsList.forEach(System.out::println);
    }
}
