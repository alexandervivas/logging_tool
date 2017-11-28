package com.ef.db.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.ef.db.dao.LogDAO;
import com.ef.db.manager.DBManager;
import com.ef.db.manager.DBManagerEnum;
import com.ef.db.manager.DBManagerFactory;
import com.ef.model.Log;
import com.ef.util.DBUtils;

public class LogRepository {
	
	private String engine = null;
	
	public LogRepository(String engine) {
		this.engine = engine;
	}

	public void insertAll(Connection connection, List<Log> logs) {
		
		DBManagerEnum managerEnum = DBManagerEnum.newDBManager(engine);
		DBManager manager = DBManagerFactory.getInstance().newDBManager(managerEnum);
		
		try {
			
			LogDAO dao = new LogDAO(manager, connection);
			dao.addAll(logs);
			
			connection.commit();
		} catch (SQLException e) {
			
			DBUtils.rollback(e, connection);
			
		}
		
		
	}

	public Connection getConnection() {
		DBManagerEnum managerEnum = DBManagerEnum.newDBManager(engine);
		DBManager manager = DBManagerFactory.getInstance().newDBManager(managerEnum);
		return manager.getConnection();
	}
	
	public void freeConnection(Connection connection) {
		
		DBManagerEnum managerEnum = DBManagerEnum.newDBManager(engine);
		DBManager manager = DBManagerFactory.getInstance().newDBManager(managerEnum);
		DBUtils.freeConnection(manager, connection);
		
	}

	public Map<String, Integer> getIpBlockingCandidates(DateTime startDate, DateTime endDate, int threshold) {
		DBManagerEnum managerEnum = DBManagerEnum.newDBManager(engine);
		DBManager manager = DBManagerFactory.getInstance().newDBManager(managerEnum);
		Connection connection = null;
		Map<String, Integer> result = null;
		
		try {
			connection = manager.getConnection();
			
			LogDAO dao = new LogDAO(manager, connection);
			result = dao.getIpBlockingCandidates(startDate, endDate, threshold);
			
			connection.commit();
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			DBUtils.freeConnection(manager, connection);
			
		}
		
		return result;
	}

}
