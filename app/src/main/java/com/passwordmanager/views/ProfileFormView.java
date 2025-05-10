package com.passwordmanager.views;

import com.passwordmanager.daos.UserDao;
import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileFormView {
    private final UserModel user;
    private final UserDao userDao;

    public ProfileFormView() {
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
        this.userDao = new UserDao();
    }

    public VBox getView() {
        VBox form = new VBox(20);
        form.getStyleClass().add("form-container");

        // Header
        Label headerLabel = new Label("Edit Profil Pengguna");
        headerLabel.getStyleClass().add("form-header");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.getStyleClass().add("form-grid");

        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("form-label");
        TextField usernameField = new TextField(user.username);
        usernameField.getStyleClass().add("styled-text-field");
        usernameField.setEditable(false);
        grid.add(usernameLabel, 0, 0);
        grid.add(usernameField, 1, 0);

        Label passwordLabel = new Label("Password Baru:");
        passwordLabel.getStyleClass().add("form-label");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("styled-password-field");
        grid.add(passwordLabel, 0, 1);
        grid.add(passwordField, 1, 1);

        Label confirmPasswordLabel = new Label("Konfirmasi Password:");
        confirmPasswordLabel.getStyleClass().add("form-label");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("styled-password-field");
        grid.add(confirmPasswordLabel, 0, 2);
        grid.add(confirmPasswordField, 1, 2);

        Label fullNameLabel = new Label("Nama Lengkap:");
        fullNameLabel.getStyleClass().add("form-label");
        TextField fullNameField = new TextField(user.fullname);
        fullNameField.getStyleClass().add("styled-text-field");
        grid.add(fullNameLabel, 0, 3);
        grid.add(fullNameField, 1, 3);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.setPadding(new Insets(20, 0, 0, 0));
        
        Button closeButton = new Button("Tutup");
        closeButton.getStyleClass().addAll("btn", "btn-danger");
        
        Button saveButton = new Button("Simpan Perubahan");
        saveButton.getStyleClass().addAll("btn", "btn-success");
        
        buttonBox.getChildren().addAll(closeButton, saveButton);

        form.getChildren().addAll(headerLabel, grid, buttonBox);
        saveButton.setOnAction(event -> {
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String fullname = fullNameField.getText();
            
            // Validasi jika ada password yang diinputkan
            if (password != null && !password.isEmpty()) {
                if (!password.equals(confirmPassword)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Password dan konfirmasi password tidak sama");
                    alert.showAndWait();
                    return;
                }
                
                // Update dengan password baru
                if (userDao.update(user.id, password, fullname) > 0) {
                    showSuccess();
                    user.fullname = fullname;
                } else {
                    showError("Gagal mengupdate profil");
                }
            } else {
                // Update tanpa password
                if (userDao.update(user.id, fullname) > 0) {
                    showSuccess();
                    user.fullname = fullname;
                } else {
                    showError("Gagal mengupdate profil");
                }
            }
        });

        return form;
    }

    private void showSuccess() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText("Profil berhasil diupdate");
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}