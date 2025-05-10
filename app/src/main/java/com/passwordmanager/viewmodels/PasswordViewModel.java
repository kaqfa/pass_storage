package com.passwordmanager.viewmodels;

import com.passwordmanager.daos.PasswordDao;
import com.passwordmanager.models.FolderModel;
import com.passwordmanager.models.PasswordStoreModel;
import com.passwordmanager.models.UserModel;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class PasswordViewModel {
    private final PasswordDao passwordDao;
    private final UserModel user;
    
    // Properties untuk binding dengan view
    public StringProperty accountNameProperty = new SimpleStringProperty();
    public StringProperty usernameProperty = new SimpleStringProperty();
    public StringProperty passwordProperty = new SimpleStringProperty();
    public StringProperty confirmPasswordProperty = new SimpleStringProperty();
    public StringProperty categoryProperty = new SimpleStringProperty();
    public ObjectProperty<FolderModel> folderProperty = new SimpleObjectProperty<>();
    
    public PasswordViewModel(UserModel user) {
        this.passwordDao = new PasswordDao();
        this.user = user;
    }
    
    public String savePassword(int folderId) {
        // Validasi input
        if (isAnyFieldEmpty()) {
            return "Semua field harus diisi!";
        }
        
        if (!isPasswordValid()) {
            return "Password minimal 8 karakter!";
        }
        
        if (!isPasswordMatch()) {
            return "Password dan konfirmasi password tidak sama!";
        }
        
        if (!isUsernameValid()) {
            return "Username minimal 4 karakter!";
        }
        
        // Buat objek password baru
        PasswordStoreModel newPassword = new PasswordStoreModel(
            accountNameProperty.get(),
            usernameProperty.get(),
            passwordProperty.get(),
            getCategoryIndex()
        );
        newPassword.folder = folderProperty.get();
        int idPass = passwordDao.addPassword(newPassword, user);
        System.out.println(idPass);
        System.out.println(passwordDao);
        if (idPass > 0) {
            clearForm();
            return null; // null berarti sukses
        }
        
        return "Gagal menyimpan password!";
    }

    public String updatePassword(PasswordStoreModel password) {
        // Validasi input
        if (isAnyFieldEmpty()) {
            return "Semua field harus diisi!";
        }
        
        if (!isPasswordValid()) {
            return "Password minimal 8 karakter!";
        }
        
        if (!isPasswordMatch()) {
            return "Password dan konfirmasi password tidak sama!";
        }
        
        if (!isUsernameValid()) {
            return "Username minimal 4 karakter!";
        }
        
        // Update data password
        password.name = accountNameProperty.get();
        password.username = usernameProperty.get();
        password.setPassword(passwordProperty.get());
        password.setCategory(getCategoryIndex());
        password.folder = folderProperty.get();
        
        if (passwordDao.updatePass(password) > 0) {
            clearForm();
            return null; // null berarti sukses
        }
        
        return "Gagal mengupdate password!";
    }

    public void loadPasswordData(PasswordStoreModel password) {
        accountNameProperty.set(password.name);
        usernameProperty.set(password.username);
        passwordProperty.set(password.getPassword());
        confirmPasswordProperty.set(password.getPassword());
        categoryProperty.set(password.getCategory());
        folderProperty.set(password.folder);
    }
    
    private boolean isAnyFieldEmpty() {
        return accountNameProperty.get() == null || accountNameProperty.get().isEmpty() ||
               usernameProperty.get() == null || usernameProperty.get().isEmpty() ||
               passwordProperty.get() == null || passwordProperty.get().isEmpty() ||
               confirmPasswordProperty.get() == null || confirmPasswordProperty.get().isEmpty() ||
               categoryProperty.get() == null || categoryProperty.get().isEmpty();
    }
    
    private boolean isPasswordValid() {
        return passwordProperty.get().length() >= 8;
    }
    
    private boolean isPasswordMatch() {
        return passwordProperty.get().equals(confirmPasswordProperty.get());
    }
    
    private boolean isUsernameValid() {
        return usernameProperty.get().length() >= 4;
    }

    private int getCategoryIndex() {
        String category = categoryProperty.get();
        if (category == null || category.isEmpty()) {
            return -1;
        }
        
        return java.util.Arrays.asList(PasswordStoreModel.CATEGORIES).indexOf(category);
    }
    
    public void clearForm() {
        accountNameProperty.set("");
        usernameProperty.set("");
        passwordProperty.set("");
        confirmPasswordProperty.set("");
        categoryProperty.set(null);
    }
}