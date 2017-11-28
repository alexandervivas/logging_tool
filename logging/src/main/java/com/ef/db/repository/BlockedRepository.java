package com.ef.db.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.ef.db.dao.BlockedDAO;
import com.ef.db.manager.DBManager;
import com.ef.db.manager.DBManagerEnum;
import com.ef.db.manager.DBManagerFactory;
import com.ef.model.Blocked;
import com.ef.util.DBUtils;

public class BlockedRepository {
	
	private String engine = null;
	
	public BlockedRepository(String engine) {
		this.engine = engine;
	}

	public void insertAll(List<Blocked> blockedList) {
		
		DBManagerEnum managerEnum = DBManagerEnum.newDBManager(engine);
		DBManager manager = DBManagerFactory.getInstance().newDBManager(managerEnum);
		Connection connection = null;
		
		try {
			
			connection = manager.getConnection();
			
			BlockedDAO dao = new BlockedDAO(manager, connection);
			dao.addAll(blockedList);
			
			connection.commit();
		} catch (SQLException e) {
			
			DBUtils.rollback(e, connection);
			
		} finally {
			
			DBUtils.freeConnection(manager, connection);
			
		}
		
		
	}

}
