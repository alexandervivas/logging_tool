package com.ef.observer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ef.model.Log;

import rx.Observer;

public class LogObserver implements Observer<List<Log>> {
	
	private Map<String, Integer> blockedIps = new HashMap<String, Integer>();
	private int threshold;
	
	public LogObserver(int threshold) {
		this.threshold = threshold;
	}

	@Override
	public void onCompleted() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onError(Throwable arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNext(List<Log> arg0) {
		
		if(arg0.size() > threshold) {
			blockedIps.put(arg0.get(0).getIp(), arg0.size());
		}
		
	}
	
	public Map<String, Integer> getBlockedIps() {
		
		return blockedIps;
		
	}

}
