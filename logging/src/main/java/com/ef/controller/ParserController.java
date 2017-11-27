package com.ef.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.joda.time.DateTime;

import com.ef.model.Log;
import com.ef.observer.LogObserver;

import rx.Observable;
import rx.observables.ConnectableObservable;

public class ParserController {

	public List<Log> processFile() {
		String fileName = "./access.log";
		List<Log> logs = new ArrayList<Log>();

		try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
			
			stream.forEach(log -> logs.add(Log.fromString(log)));

		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return logs;

	}
	
	public Map<String, Integer> getBlockedIps(List<Log> logs, DateTime startDate, DateTime endDate, int threshold) {
		
		ConnectableObservable<List<Log>> logsObservable = Observable.from(logs)
				.filter(log -> 	log.getDate().isAfter(startDate) && 
								log.getDate().isBefore(endDate))
				.groupBy(log -> log.getIp())
				.flatMap(list -> list.toList())
				.publish();

		LogObserver logObserver = new LogObserver(threshold);
		logsObservable.subscribe(logObserver);
		logsObservable.connect();
		
		return logObserver.getBlockedIps();
	}

}
