package com.passwordmanager.views;

import com.passwordmanager.models.FolderModel;
import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;
import com.passwordmanager.viewmodels.FolderViewModel;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PasswordFormView {
    private final FolderViewModel folderViewModel;
    private final UserModel user;

    public PasswordFormView() {
        this.folderViewModel = new FolderViewModel();
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
    }

    public VBox getView() {
        VBox form = new VBox(20);
        form.getStyleClass().add("form-container");

        // Header
        Label headerLabel = new Label("Manajemen Password");
        headerLabel.getStyleClass().add("form-header");

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("form-grid");

        // Account Name Section
        Label accountNameLabel = new Label("Nama Akun:");
        accountNameLabel.getStyleClass().add("form-label");
        TextField accountNameField = new TextField();
        accountNameField.setPromptText("Masukkan nama akun");
        accountNameField.getStyleClass().add("styled-text-field");
        Button editButton = new Button("Edit");
        editButton.getStyleClass().addAll("button", "button-warning");
        
        grid.add(accountNameLabel, 0, 0);
        grid.add(accountNameField, 1, 0);
        grid.add(editButton, 2, 0);

        // Username Section
        Label usernameLabel = new Label("Username:");
        usernameLabel.getStyleClass().add("form-label");
        TextField usernameField = new TextField();
        usernameField.setPromptText("Masukkan username");
        usernameField.getStyleClass().add("styled-text-field");
        Button saveButton = new Button("Simpan");
        saveButton.getStyleClass().addAll("button", "button-primary");
        
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameField, 1, 1);
        grid.add(saveButton, 2, 1);

        // Password Section
        Label passwordLabel = new Label("Password:");
        passwordLabel.getStyleClass().add("form-label");
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("styled-password-field");
        Button copyPasswordButton = new Button("Salin Password");
        copyPasswordButton.getStyleClass().addAll("button", "button-info");
        
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(copyPasswordButton, 2, 2);

        // Confirm Password Section
        Label confirmPasswordLabel = new Label("Konfirmasi:");
        confirmPasswordLabel.getStyleClass().add("form-label");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.getStyleClass().add("styled-password-field");
        
        grid.add(confirmPasswordLabel, 0, 3);
        grid.add(confirmPasswordField, 1, 3);

        // Category Section
        Label categoryLabel = new Label("Kategori:");
        categoryLabel.getStyleClass().add("form-label");
        ComboBox<String> categoryComboBox = new ComboBox<>();
        categoryComboBox.getStyleClass().add("styled-combo-box");
        categoryComboBox.getItems().addAll("Kantor", "Pribadi", "Sosial");
        
        grid.add(categoryLabel, 0, 4);
        grid.add(categoryComboBox, 1, 4);

        // Folder Section
        Label folderLabel = new Label("Folder:");
        folderLabel.getStyleClass().add("form-label");
        ComboBox<FolderModel> folderComboBox = new ComboBox<>();
        folderComboBox.getStyleClass().add("styled-combo-box");
        
        // Mengisi ComboBox dengan data dari basis data menggunakan user dari session
        folderComboBox.getItems().addAll(folderViewModel.getFolders(user.id));
        
        // Mengatur cara menampilkan folder di ComboBox
        folderComboBox.setCellFactory(param -> new ListCell<FolderModel>() {
            @Override
            protected void updateItem(FolderModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.name);
                }
            }
        });
        
        // Mengatur tampilan item yang terpilih
        folderComboBox.setButtonCell(new ListCell<FolderModel>() {
            @Override
            protected void updateItem(FolderModel item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.name);
                }
            }
        });
        
        grid.add(folderLabel, 0, 5);
        grid.add(folderComboBox, 1, 5);

        // Table Section
        TableView<String> dataTable = new TableView<>();
        dataTable.getStyleClass().add("data-table");
        dataTable.setPlaceholder(new Label("Data akan ditampilkan di sini"));
        GridPane.setColumnSpan(dataTable, 3);
        grid.add(dataTable, 0, 6, 3, 1);

        form.getChildren().addAll(headerLabel, grid);
        return form;
    }
}