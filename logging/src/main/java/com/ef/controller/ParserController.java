package com.ef.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.joda.time.DateTime;

import com.ef.db.repository.BlockedRepository;
import com.ef.db.repository.LogRepository;
import com.ef.model.Blocked;
import com.ef.model.Log;

import io.reactivex.Flowable;
import io.reactivex.flowables.ConnectableFlowable;

public class ParserController {

	private LogRepository logRepository = new LogRepository("MYSQL");
	private BlockedRepository blockedRepository = new BlockedRepository("MYSQL");
	private Connection connection;

	public void loadFile(String filename) {
		/**
		 * Trying to implement some kind of backpressure here for large files
		 */
		connection = logRepository.getConnection();
		Flowable<List<String>> flowable = Flowable.using(
		        () -> new BufferedReader(new FileReader(filename)),
		        reader -> Flowable.fromIterable(() -> reader.lines().iterator()),
		        reader -> reader.close()
		).buffer(1000);
		
		ConnectableFlowable<List<String>> cFlowable = flowable.publish();
		cFlowable.subscribe(list -> saveIntoDatabase(list));
		cFlowable.connect();
		logRepository.freeConnection(connection);

	}
	
	private void saveIntoDatabase(List<String> list) {
		System.out.print(".");
		List<Log> logs = list
				.stream()
				.map(logStr -> Log.fromString(logStr))
				.collect(Collectors.toList());
		
		logRepository.insertAll(connection, logs);
	}

	public Map<String, Integer> getBlockedIps(DateTime startDate, DateTime endDate, int threshold) {
		
		return logRepository.getIpBlockingCandidates(startDate, endDate, threshold);
		
	}

	public void reportIps(List<Blocked> blockedList) {
		
		blockedRepository.insertAll(blockedList);
		
	}

}
