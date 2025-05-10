package com.passwordmanager.daos;

import java.sql.SQLException;
import java.util.ArrayList;

import com.passwordmanager.models.FolderModel;
import com.passwordmanager.models.PasswordStoreModel;
import com.passwordmanager.models.UserModel;

public class PasswordDao extends BaseDao {

    public int addPassword(PasswordStoreModel newPassword, UserModel user){
        int id = 0;

        query = "insert into passwordstore values (null, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            stmt = this.conn.prepareStatement(query);
            stmt.setString(1, newPassword.name);
            stmt.setString(2, newPassword.username);
            stmt.setString(3, newPassword.getEncPassword());
            stmt.setString(4, newPassword.hashkey);
            stmt.setDouble(5, newPassword.getScore());
            stmt.setInt(6, newPassword.getCategoryCode());
            stmt.setInt(7, user.id);
            stmt.setInt(8, newPassword.folder.id);
            
            id = stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return id;
    }

    public ArrayList<PasswordStoreModel> listPassword(UserModel user){
        ArrayList<PasswordStoreModel> dataPassword = new ArrayList<>();
        query = "select passwordstore.*, folder.id as f_id, folder.name as f_name "+
                "from passwordstore "+
                "join folder on (folder.id = passwordstore.folder_id) "+
                "where user_id = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, user.id);
            result = stmt.executeQuery();
            while (result.next()) {
                PasswordStoreModel newPass = new PasswordStoreModel(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getString("username"),
                                result.getString("password"),
                                result.getString("hashkey"),
                                result.getDouble("score"),
                                result.getInt("category"));
                newPass.folder = new FolderModel(result.getInt("f_id"), 
                                            result.getString("f_name"));
                dataPassword.add(newPass);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataPassword;
    }

    public ArrayList<PasswordStoreModel> findPassword(String name, UserModel user){
        ArrayList<PasswordStoreModel> dataPassword = new ArrayList<>();
        query = "select passwordstore.*, folder.id as f_id, folder.name as f_name "+
                "from passwordstore "+
                "join folder on (folder.id = passwordstore.folder_id) "+
                "where (passwordstore.name like ? or username like ?) and user_id = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, "%"+name+"%");
            stmt.setString(2, "%"+name+"%");
            stmt.setInt(3, user.id);
            result = stmt.executeQuery();
            while (result.next()) {
                PasswordStoreModel newPass = new PasswordStoreModel(
                                result.getInt("id"),
                                result.getString("name"),
                                result.getString("username"),
                                result.getString("password"),
                                result.getString("hashkey"),
                                result.getDouble("score"),
                                result.getInt("category"));
                newPass.folder = new FolderModel(result.getInt("f_id"), 
                                            result.getString("f_name"));
                dataPassword.add(newPass);
            }
            return dataPassword;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int updatePass(PasswordStoreModel changedPass){
        query = "update passwordstore set name = ?, username = ?, password = ?, "+
                "category = ?, folder_id = ?, score = ? "+
                "where id = ?";
        try {
            stmt = conn.prepareStatement(query);
            stmt.setString(1, changedPass.name);
            stmt.setString(2, changedPass.username);
            stmt.setString(3, changedPass.getEncPassword());
            stmt.setInt(4, changedPass.getCategoryCode());            
            stmt.setInt(5, changedPass.folder.id);
            stmt.setDouble(6, changedPass.getScore());
            stmt.setInt(7, changedPass.id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int deletePass(PasswordStoreModel deletedPass){
        query = "delete from passwordstore where id = ?";

        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, deletedPass.id);
            return stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public ArrayList<PasswordStoreModel> getPasswordsByFolder(int folderId) {
        ArrayList<PasswordStoreModel> passwords = new ArrayList<>();
        query = "SELECT * FROM passwordstore WHERE folder_id = ?";
        
        try {
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, folderId);
            result = stmt.executeQuery();
            
            while (result.next()) {
                passwords.add(new PasswordStoreModel(
                    result.getInt("id"),
                    result.getString("name"),
                    result.getString("username"),
                    result.getString("password"),
                    result.getString("hashkey"),
                    result.getDouble("score"),
                    result.getInt("category")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return passwords;
    }
}
