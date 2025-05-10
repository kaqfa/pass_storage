package com.passwordmanager.utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InitDB {
    static String[] sql = {
                """
                CREATE TABLE IF NOT EXISTS folder (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    name TEXT NOT NULL,
                    user_id INTEGER NOT NULL,
                    FOREIGN KEY (user_id) REFERENCES users(id)
                );
                """,
                
                "CREATE TABLE if not exists userdata (id INTEGER PRIMARY KEY AUTOINCREMENT," + //
                "    username TEXT, password TEXT, fullname TEXT);",

                "CREATE UNIQUE INDEX if not exists user_username_IDX ON userdata (username);",

                "CREATE TABLE if not exists passwordstore  (" + //
                "    id INTEGER PRIMARY KEY AUTOINCREMENT," + //
                "    name TEXT, username TEXT, password TEXT, hashkey TEXT," + //
                "    score REAL, category INTEGER, user_id INTEGER, folder_id INTEGER," + //
                "    CONSTRAINT passwordstore_user_FK FOREIGN KEY (user_id)" +
                "       REFERENCES userdata(id) ON DELETE RESTRICT ON UPDATE RESTRICT," + //
                "    CONSTRAINT passwordstore_folder_FK FOREIGN KEY (folder_id)" +
                "       REFERENCES folder(id) ON DELETE SET NULL ON UPDATE SET NULL);",
                
                "CREATE TABLE if not exists additional (" + //
                "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT," + //
                "    entry_key TEXT, entry_value TEXT, is_password INTEGER, password_id INTEGER," + //
                "    CONSTRAINT additional_passwordstore_FK FOREIGN KEY (password_id)" +
                "       REFERENCES passwordstore(id) ON DELETE CASCADE ON UPDATE CASCADE);"
            };

    private InitDB() {}

    public static void initialize(){
        Connection conn;
        Statement stmt;
        try {
            conn = DBConnection.connect();
            stmt = conn.createStatement();
            for(String query: sql){
                stmt.executeUpdate(query);
            }
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
}