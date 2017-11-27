package com.ef;

import java.util.List;

import org.joda.time.DateTime;

import com.ef.controller.ParserController;
import com.ef.enums.Duration;
import com.ef.model.Log;
import com.ef.util.DateTimeUtils;

public class Parser 
{
    public static void main( String[] args )
    {
        ParserController control = new ParserController();
        
        List<Log> logs = control.processFile();
        
        DateTime startDate = DateTimeUtils.getDate("2017-01-01 00:00:00.000");
        DateTime endDate = DateTimeUtils.getEndDate(startDate, Duration.fromString("daily"));
        
        System.out.println("Starts search between " + startDate + " and " + endDate);
        
        control.getBlockedIps(logs, startDate, endDate, 500)
        	.forEach((key, value) -> System.out.println(key + ": made " + value + " requests"));
    }
}
