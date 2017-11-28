package com.ef.db.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public interface DBManager {
	
	public Connection getConnection();
	 
    public void returnConnection(Connection connection);
 
    public void closeResources(ResultSet resultSet, PreparedStatement preparedStatement);

}
