package com.passwordmanager.daos;

import java.sql.SQLException;

import com.passwordmanager.models.UserModel;

public class UserDao extends BaseDao {
    
    public int register(String username, String password, String fullname){
        int id = 0;
        query = "insert into userdata values (null, ?, ?, ?)";
        try {
            stmt = conn.prepareStatement(query);
            UserModel newUser = new UserModel(username, password, fullname);
            stmt.setString(1, newUser.username);
            stmt.setString(2, newUser.getPassword());
            stmt.setString(3, newUser.fullname);
            stmt.executeUpdate();
            id = stmt.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            // kalau ada yang bisa mendeteksi exception username sudah ada, tak tambahin nilainya
            e.printStackTrace();
        }

        return id;
    }

    public UserModel login(String username, String password){
        UserModel user = null;
        query = "select * from userdata where username = ?";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, username);
            result = stmt.executeQuery();
            if(!result.isAfterLast()){
                result.next();
                if(UserModel.checkPassword(password, result.getString("password"))){
                    user = new UserModel(result.getInt("id"), 
                                result.getString("username"), 
                                result.getString("password"), 
                                result.getString("fullname"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return user;
    }
    
    public int update(int id, String fullname){
        query = "update userdata set fullname = ? where id = ?";
        
        try{
            stmt = conn.prepareStatement(query);
            stmt.setString(1, fullname);
            stmt.setInt(2, id);
            return stmt.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        
        return 0;
    }
    
    public int update(int id, String password, String fullname){
        query = "update userdata set fullname = ?, password = ? where id = ?";
        
        try {
            stmt = conn.prepareStatement(query);
            UserModel newUser = new UserModel("", password, fullname);
            stmt.setString(1, newUser.fullname);
            stmt.setString(2, newUser.getPassword());
            stmt.setInt(3, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }

    public int delete(int id){
        query = "delete from userdata where id = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return 0;
    }
}
