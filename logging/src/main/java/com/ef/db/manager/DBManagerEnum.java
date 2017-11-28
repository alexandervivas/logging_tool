package com.ef.db.manager;

public enum DBManagerEnum {

    MYSQL,
    H2;
 
    public static DBManagerEnum newDBManager(String dbManagerEnum) {
 
        if ("MYSQL".equalsIgnoreCase(dbManagerEnum)) {
 
            return MYSQL;
 
        }
 
        if ("H2".equalsIgnoreCase(dbManagerEnum)) {
 
            return H2;
 
        }
 
        return null;
        
    }

}
