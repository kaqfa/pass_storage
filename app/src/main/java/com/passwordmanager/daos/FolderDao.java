package com.passwordmanager.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.passwordmanager.models.FolderModel;

public class FolderDao extends BaseDao {
    public int createFolder(String name, int userId) {
        int id = 0;
        query = "INSERT INTO folder (name, user_id) VALUES (?, ?)";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name);
            stmt.setInt(2, userId);
            stmt.executeUpdate();

            result = stmt.getGeneratedKeys();
            if (result.next()) {
                id = result.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<FolderModel> getfolderByUserId(int userId) {
        List<FolderModel> folder = new ArrayList<>();
        query = "SELECT * FROM folder WHERE user_id = ?";
        
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, userId);
            result = stmt.executeQuery();

            while (result.next()) {
                folder.add(new FolderModel(
                    result.getInt("id"),
                    result.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return folder;
    }
}
