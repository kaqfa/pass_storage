package com.passwordmanager.views;

import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ProfileFormView {
    private final UserModel user;

    public ProfileFormView() {
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
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
        return form;
    }
}