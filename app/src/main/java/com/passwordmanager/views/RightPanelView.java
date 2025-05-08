package com.passwordmanager.views;

import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class RightPanelView {
    private final VBox rightPanel;
    private final Pane mainPanel;
    private final UserModel user;
    private final MainAppView mainAppView;

    public RightPanelView(MainAppView mainAppView) {
        this.rightPanel = new VBox();
        this.mainPanel = new VBox();
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
        this.mainAppView = mainAppView;
        initialize();
    }

    private void initialize() {
        HBox menuPanel = createMenuPanel();
        menuPanel.setAlignment(Pos.CENTER_RIGHT);
        
        mainPanel.setPadding(new Insets(10));
        createMainPanel();
        rightPanel.getChildren().addAll(menuPanel, mainPanel);
    }

    private HBox createMenuPanel() {
        HBox toolbar = new HBox(10);
        toolbar.setPadding(new Insets(10));
        toolbar.setStyle("-fx-background-color: #f0f0f0;");

        Button addFolderButton = new Button("Tambah Folder");
        Button addPasswordButton = new Button("Tambah Password");
        Button editProfileButton = new Button("Edit Profil");
        Button logoutButton = new Button("Logout");

        addFolderButton.getStyleClass().addAll("btn", "btn-info");
        addPasswordButton.getStyleClass().addAll("btn", "btn-info");
        editProfileButton.getStyleClass().addAll("btn", "btn-warning");
        logoutButton.getStyleClass().addAll("btn", "btn-danger");

        addFolderButton.setOnAction(event -> mainAppView.showFolderDialog());
        addPasswordButton.setOnAction(event -> showPasswordForm());
        editProfileButton.setOnAction(event -> showProfileForm());
        logoutButton.setOnAction(event -> mainAppView.logoutAction());

        toolbar.getChildren().addAll(addFolderButton, addPasswordButton, editProfileButton, logoutButton);
        return toolbar;
    }

    private void createMainPanel() {
        mainPanel.setStyle("-fx-border-color: #ccc; -fx-border-width: 1px;");
        mainPanel.setPadding(new Insets(20));
        mainPanel.getChildren().setAll(showGreeting());
    }

    private VBox showGreeting() {
        VBox greetingContainer = new VBox();
        greetingContainer.setPadding(new Insets(10));
        greetingContainer.setAlignment(Pos.TOP_LEFT);
        Label greetingLabel = new Label("Selamat Datang di Aplikasi Penyimpanan Password: ");
        Label userFullName = new Label(user.fullname);
        greetingLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #333;");
        userFullName.setStyle("-fx-font-size: 16px; -fx-text-fill: #333; -fx-font-weight: bold; -fx-font-family: 'Arial';");
        greetingContainer.getChildren().addAll(greetingLabel, userFullName);
        return greetingContainer;
    }

    private void showPasswordForm() {
        PasswordFormView passwordFormView = new PasswordFormView();
        mainPanel.getChildren().setAll(passwordFormView.getView());
    }

    private void showProfileForm() {
        ProfileFormView profileFormView = new ProfileFormView();
        mainPanel.getChildren().setAll(profileFormView.getView());
    }

    public VBox getView() {
        return rightPanel;
    }

    public void setMainContent(Pane content) {
        mainPanel.getChildren().setAll(content);
    }
}