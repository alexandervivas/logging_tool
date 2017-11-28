package com.ef.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import com.ef.db.manager.DBManager;
import com.ef.model.Blocked;
import com.ef.util.DBUtils;

public class BlockedDAO {
	
	private DBManager manager;
    private Connection connection;
    
    private static final String INSERT_STATEMENT = "insert into blocked(ip, comments, date) VALUES(?,?,?)";

	public BlockedDAO(DBManager manager, Connection connection) {
		this.manager = manager;
		this.connection = connection;
	}

	public void addAll(List<Blocked> blockedList) {

		PreparedStatement preparedStatement = null;
		
		try {
			
			preparedStatement = connection.prepareStatement(INSERT_STATEMENT);
			
			for(Blocked blocked : blockedList) {
				preparedStatement.setString(1, blocked.getIp());
				preparedStatement.setString(2, blocked.getComments());
				preparedStatement.setTimestamp(3, new Timestamp(blocked.getDate().getMillis()));
				
				preparedStatement.addBatch();
			}
			
			preparedStatement.executeBatch();
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			
		} finally {
			
			DBUtils.freeResources(manager, null, preparedStatement);
			
		}
		
	}

}
