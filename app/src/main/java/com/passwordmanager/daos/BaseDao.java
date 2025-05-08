package com.passwordmanager.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.passwordmanager.utils.DBConnection;

public class BaseDao {
    protected Connection conn;
    protected PreparedStatement stmt;
    protected ResultSet result;
    protected String query;

    public BaseDao(){
        this.conn = DBConnection.connect();
    }
}