package com.passwordmanager.views;

import org.kordamp.ikonli.javafx.FontIcon;

import com.passwordmanager.models.FolderModel;
import com.passwordmanager.models.PasswordStoreModel;
import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;
import com.passwordmanager.viewmodels.FolderViewModel;
import com.passwordmanager.viewmodels.PasswordViewModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class PasswordFormView {
    private final FormComponents components;
    private final FolderViewModel folderViewModel;
    private final PasswordViewModel passwordViewModel;
    private final UserModel user;
    private final LeftPanelView leftPanelView;

    public PasswordFormView(LeftPanelView leftPanelView) {
        this.components = new FormComponents();
        this.folderViewModel = new FolderViewModel();
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
        this.passwordViewModel = new PasswordViewModel(this.user);
        this.leftPanelView = leftPanelView;
        
        initializeView();
    }

    public VBox getView() {
        VBox form = new VBox(20);
        form.getStyleClass().add("form-container");

        GridPane grid = createFormGrid();
        layoutComponents(grid);

        form.getChildren().addAll(createHeader(), grid);
        return form;
    }

    private void initializeView() {
        setupStyles();
        setupData();
        setupBindings();
        setupEventHandlers();
    }

    private Label createHeader() {
        Label headerLabel = new Label("Manajemen Password");
        headerLabel.getStyleClass().add("form-header");
        return headerLabel;
    }

    private GridPane createFormGrid() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.getStyleClass().add("form-grid");
        return grid;
    }

    private void layoutComponents(GridPane grid) {
        // Row 0: Account Name
        grid.add(new Label("Nama Akun:"), 0, 0);
        grid.add(components.accountNameField, 1, 0);
        grid.add(components.editButton, 2, 0);

        // Row 1: Username
        grid.add(new Label("Username:"), 0, 1);
        grid.add(components.usernameField, 1, 1);
        grid.add(components.saveButton, 2, 1);

        // Row 2: Password
        grid.add(new Label("Password:"), 0, 2);
        grid.add(components.passwordField, 1, 2);
        grid.add(components.copyPasswordButton, 2, 2);

        // Row 3: Confirm Password
        grid.add(new Label("Konfirmasi:"), 0, 3);
        grid.add(components.confirmPasswordField, 1, 3);

        // Row 4: Category
        grid.add(new Label("Kategori:"), 0, 4);
        grid.add(components.categoryComboBox, 1, 4);

        // Row 5: Folder
        grid.add(new Label("Folder:"), 0, 5);
        grid.add(components.folderComboBox, 1, 5);

        // Row 6: Table (spans 3 columns)
        grid.add(components.dataTable, 0, 6, 3, 1);
    }

    private void setupStyles() {
        // Text fields
        components.accountNameField.getStyleClass().add("styled-text-field");
        components.usernameField.getStyleClass().add("styled-text-field");
        components.passwordField.getStyleClass().add("styled-password-field");
        components.confirmPasswordField.getStyleClass().add("styled-password-field");
        
        // ComboBoxes
        components.categoryComboBox.getStyleClass().add("styled-combo-box");
        components.folderComboBox.getStyleClass().add("styled-combo-box");
        
        // Buttons
        components.saveButton.getStyleClass().addAll("button", "button-primary");
        components.saveButton.setPrefWidth(100);
        
        components.editButton.getStyleClass().addAll("button", "button-warning");
        components.editButton.setPrefWidth(100);
        components.editButton.setDisable(true);
        
        components.copyPasswordButton.getStyleClass().addAll("button", "button-icon", "button-info");
        components.copyPasswordButton.setDisable(true);
        
        // Table
        components.dataTable.getStyleClass().add("data-table");
        components.dataTable.setPlaceholder(new Label("Data akan ditampilkan di sini"));
    }

    private void setupData() {
        // Setup categories
        components.categoryComboBox.getItems().addAll(PasswordStoreModel.CATEGORIES);
        
        // Setup folder
        ObservableList<FolderModel> folder = FXCollections.observableArrayList();
        folder.addAll(folderViewModel.getFolder(user.id));
        components.folderComboBox.setItems(folder);
    }

    private void setupBindings() {
        components.accountNameField.textProperty()
            .bindBidirectional(passwordViewModel.accountNameProperty);
        components.usernameField.textProperty()
            .bindBidirectional(passwordViewModel.usernameProperty);
        components.passwordField.textProperty()
            .bindBidirectional(passwordViewModel.passwordProperty);
        components.confirmPasswordField.textProperty()
            .bindBidirectional(passwordViewModel.confirmPasswordProperty);
        components.categoryComboBox.valueProperty()
            .bindBidirectional(passwordViewModel.categoryProperty);
        components.folderComboBox.valueProperty()
            .bindBidirectional(passwordViewModel.folderProperty);
    }

    private void setupEventHandlers() {
        components.saveButton.setOnAction(event -> handleSave());
    }

    private void handleSave() {
        FolderModel selectedFolder = components.folderComboBox.getValue();
        if (selectedFolder == null) {
            showError("Folder harus dipilih!");
            return;
        }

        String result = passwordViewModel.savePassword(selectedFolder.id);
        if (result == null) {
            showSuccess("Password berhasil disimpan!");
            leftPanelView.refreshFolderTree();
        } else {
            showError(result);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sukses");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Inner class untuk mengelompokkan semua komponen form
    private static class FormComponents {
        // Text fields
        final TextField accountNameField = createTextField("Masukkan nama akun");
        final TextField usernameField = createTextField("Masukkan username");
        final PasswordField passwordField = new PasswordField();
        final PasswordField confirmPasswordField = new PasswordField();
        
        // ComboBoxes
        final ComboBox<String> categoryComboBox = new ComboBox<>();
        final ComboBox<FolderModel> folderComboBox = new ComboBox<>();
        
        // Buttons
        final Button saveButton = new Button("Simpan");
        final Button editButton = new Button("Edit");
        final Button copyPasswordButton = createIconButton("fas-copy");
        
        // Table
        final TableView<String> dataTable = new TableView<>();
        
        // Helper methods
        private static TextField createTextField(String prompt) {
            TextField field = new TextField();
            field.setPromptText(prompt);
            return field;
        }
        
        private static Button createIconButton(String iconCode) {
            Button button = new Button();
            button.setGraphic(new FontIcon(iconCode));
            return button;
        }
    }
}