package com.passwordmanager.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.passwordmanager.models.FolderModel;
import com.passwordmanager.utils.DBConnection;

public class FolderDao {
    public int createFolder(String name, int userId) {
        String sql = "INSERT INTO folders (name, user_id) VALUES (?, ?)";
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            
            pstmt.setString(1, name);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<FolderModel> getFoldersByUserId(int userId) {
        List<FolderModel> folders = new ArrayList<>();
        String sql = "SELECT * FROM folders WHERE user_id = ?";
        
        try (Connection conn = DBConnection.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                folders.add(new FolderModel(
                    rs.getInt("id"),
                    rs.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return folders;
    }
}
