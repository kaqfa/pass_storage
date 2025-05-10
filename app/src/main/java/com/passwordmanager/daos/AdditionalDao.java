package com.passwordmanager.daos;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.passwordmanager.models.AdditionalDataModel;

public class AdditionalDao extends BaseDao {
    
    public int addAdditionalData(AdditionalDataModel data, int passwordId) {
        int id = 0;
        query = "INSERT INTO additional VALUES (null, ?, ?, ?, ?)";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, data.entryKey);
            stmt.setString(2, data.entryValue);
            stmt.setInt(3, data.isPassword ? 1 : 0);
            stmt.setInt(4, passwordId);
            stmt.executeUpdate();
            id = stmt.getGeneratedKeys().getInt(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public List<AdditionalDataModel> getAdditionalDataByPasswordId(int passwordId) {
        List<AdditionalDataModel> additionalData = new ArrayList<>();
        query = "SELECT * FROM additional WHERE password_id = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, passwordId);
            result = stmt.executeQuery();
            
            while (result.next()) {
                additionalData.add(new AdditionalDataModel(
                    result.getInt("id"),
                    result.getString("entry_key"),
                    result.getString("entry_value"),
                    result.getInt("is_password") == 1
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return additionalData;
    }

    public int updateAdditionalData(AdditionalDataModel data) {
        query = "UPDATE additional SET entry_key = ?, entry_value = ?, is_password = ? WHERE id = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, data.entryKey);
            stmt.setString(2, data.entryValue);
            stmt.setInt(3, data.isPassword ? 1 : 0);
            stmt.setInt(4, data.id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAdditionalData(int id) {
        query = "DELETE FROM additional WHERE id = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deleteAllAdditionalDataByPasswordId(int passwordId) {
        query = "DELETE FROM additional WHERE password_id = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, passwordId);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}