package com.ef.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ef.db.manager.DBManager;

public class DBUtils {
	
	public static void rollback(SQLException exception, Connection connection) {
		
		exception.printStackTrace();
		
		try {
			 
			connection.rollback();
 
        } catch (SQLException e) {
 
            e.printStackTrace();
 
        }
	}
	
	public static void freeResources(DBManager manager, ResultSet resultSet, PreparedStatement preparedStatement) {
		
		if(manager != null) {
			manager.closeResources(resultSet, preparedStatement);
		}
		
	}
	
	public static void freeConnection(DBManager manager, Connection connection) {
		
		if(manager != null) {
			manager.returnConnection(connection);
		}
		
	}

}
