package  com.passwordmanager.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection conn = null;
    public static final String DB_PATH = "password.db";

    public static Connection getConnection() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private DBConnection() { }

    @SuppressWarnings("CallToPrintStackTrace")
    public static Connection connect(){
        try{
            if(conn == null || conn.isClosed()){
                Class.forName("org.sqlite.JDBC");
                conn = DriverManager.getConnection("jdbc:sqlite:"+DB_PATH);
            }
        }catch(SQLException | ClassNotFoundException e){
            e.printStackTrace();
        }
        return conn;
    }
    
}