package com.ef.db.manager;

import com.ef.db.manager.impl.H2DBManager;
import com.ef.db.manager.impl.MySQLDBManager;

public class DBManagerFactory {
	
	private static DBManagerFactory instance = null;
	
	private DBManagerFactory() {
		 
    }
	
	public static DBManagerFactory getInstance() {
		
		if(instance == null) {
			
			synchronized (DBManagerFactory.class) {
				
				instance = new DBManagerFactory();
				
			}
			
		}
		
		return instance;
		
	}
 
    public DBManager newDBManager(DBManagerEnum dbManagerEnum) {
 
        switch (dbManagerEnum) {
 
        case MYSQL:
 
            return new MySQLDBManager();
 
        case H2:
 
            return new H2DBManager();
 
        default:
 
            return null;
 
        }
 
    }

}
