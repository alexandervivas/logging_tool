package com.ef.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import com.ef.db.manager.DBManager;
import com.ef.model.Log;
import com.ef.util.DBUtils;

public class LogDAO {
	
	private DBManager manager;
    private Connection connection;
    
    private static final String INSERT_STATEMENT = "insert ignore into logs(date, ip, request, status, user_agent) VALUES(?,?,?,?,?)";
    private static final String SELECT_STATEMENT = "select ip, count(1) from logs where date between ? and ? group by ip having count(1) > ?";

	public LogDAO(DBManager manager, Connection connection) {
		this.manager = manager;
		this.connection = connection;
	}

	public void addAll(List<Log> logs) {

		PreparedStatement preparedStatement = null;
		
		try {
			
			preparedStatement = connection.prepareStatement(INSERT_STATEMENT);
			
			for(Log log : logs) {
				preparedStatement.setTimestamp(1, new Timestamp(log.getDate().getMillis()));
				preparedStatement.setString(2, log.getIp());
				preparedStatement.setString(3, log.getRequest());
				preparedStatement.setInt(4, log.getStatus());
				preparedStatement.setString(5, log.getUserAgent());
				
				preparedStatement.addBatch();
			}
			
			preparedStatement.executeBatch();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			DBUtils.freeResources(manager, null, preparedStatement);
			
		}
		
	}

	public Map<String, Integer> getIpBlockingCandidates(DateTime startDate, DateTime endDate, int threshold) {
		
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Map<String, Integer> result = new HashMap<String, Integer>();
		
		try {
			
			preparedStatement = connection.prepareStatement(SELECT_STATEMENT);
			preparedStatement.setTimestamp(1, new Timestamp(startDate.getMillis()));
			preparedStatement.setTimestamp(2, new Timestamp(endDate.getMillis()));
			preparedStatement.setInt(3, threshold);
			
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				result.put(resultSet.getString(1), resultSet.getInt(2));
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return result;
	} 

}
