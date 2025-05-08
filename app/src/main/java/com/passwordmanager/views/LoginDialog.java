package com.passwordmanager.views;

import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;
import com.passwordmanager.viewmodels.LoginViewModel;

import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

/**
 *
 * @author apple
 */
public class LoginDialog {

    Dialog<UserModel> dialog ;
    private final LoginViewModel loginViewModel;
    private SplitPane mainPane;
    private ImageView imageView;

    private VBox vbox;
    private Label userNameLabel, passwordLabel, fullNameLabel;
    private TextField userNameTextField, fullNameTextField;
    private PasswordField passwordField;
    private Button loginButton, toRegButton, registerButton, exitButton, toLoginButton;

    public LoginDialog() {
        this.dialog = new Dialog<>();
        this.loginViewModel = new LoginViewModel();
    }

    public Dialog<UserModel> getDialog() {
        initComponents();
        setActions();

        loginButton.setOnAction(event -> {
            System.out.println("Login button clicked");
            String errMessage = loginViewModel.login();
            if (errMessage == null) {
                dialog.setResult((UserModel) SessionManager.getInstance().getSesData("user"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login Gagal");
                alert.setHeaderText(null);
                alert.setContentText(errMessage);
                alert.showAndWait();
                passwordField.clear();
            }
        });

        registerButton.setOnAction(event -> {
            System.out.println("Register button clicked");
            String errMessage = loginViewModel.register();
            if (errMessage == null) {
                dialog.setResult((UserModel) SessionManager.getInstance().getSesData("user"));
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Registrasi Gagal");
                alert.setHeaderText(null);
                alert.setContentText(errMessage);
                alert.showAndWait();
            }
        });

        mainPane.getItems().add(imageView);
        mainPane.getItems().add(getLoginForm());

        dialog.setTitle("Halaman Login");
        dialog.setResizable(false);
        dialog.getDialogPane().setContent(mainPane);
        dialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        // dialog.show();
        return dialog;
    }

    private void initComponents() {
        userNameLabel = new Label("Username");
        userNameTextField = new TextField();
        passwordLabel = new Label("Password");
        passwordField = new PasswordField();
        fullNameLabel = new Label("Nama Lengkap");
        fullNameTextField = new TextField();
        loginButton = new Button("Login");
        registerButton = new Button("Register");
        exitButton = new Button("Keluar");
        toLoginButton = new Button("back to Login");
        toRegButton = new Button("Daftar Baru");

        userNameTextField.textProperty().bindBidirectional(loginViewModel.usernameProperty);
        passwordField.textProperty().bindBidirectional(loginViewModel.passwordProperty);
        fullNameTextField.textProperty().bindBidirectional(loginViewModel.fullnameProperty);
        
        loginButton.getStyleClass().addAll("btn", "btn-success", "btn-full");
        toRegButton.getStyleClass().addAll("btn", "btn-info", "btn-full");
        exitButton.getStyleClass().addAll("btn", "btn-danger", "btn-full");
        registerButton.getStyleClass().addAll("btn", "btn-success", "btn-full");
        toLoginButton.getStyleClass().addAll("btn", "btn-info", "btn-full");

        vbox = new VBox(5);
        vbox.setPadding(new Insets(20));
        mainPane = new SplitPane();
        imageView = new ImageView("file:src/main/resources/lock-image.png");
        imageView.setFitWidth(300);
        imageView.preserveRatioProperty().set(true);
    }

    private void setActions(){
        toRegButton.setOnAction(event -> {
            getRegisterForm();
        });

        toLoginButton.setOnAction(event -> {
            getLoginForm();
        });

        exitButton.setOnAction(event -> {
            System.exit(0);
        });
    }

    // public void show() {
    //     mainPane.getItems().add(imageView);
    //     mainPane.getItems().add(getLoginForm());

    //     dialog.setTitle("Halaman Login");
    //     dialog.setResizable(false);
    //     dialog.getDialogPane().setContent(mainPane);
    //     dialog.getDialogPane().getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    //     dialog.show();
    // }

    public VBox getLoginForm(){
        clearText();
        vbox.getChildren().clear();
        vbox.getChildren().addAll(userNameLabel, userNameTextField, 
                    passwordLabel, passwordField, 
                    loginButton, toRegButton, exitButton);
        return vbox; 
    }

    public VBox getRegisterForm(){
        clearText();
        vbox.getChildren().clear();
        vbox.getChildren().addAll(userNameLabel, userNameTextField, 
                        passwordLabel, passwordField, 
                        fullNameLabel, fullNameTextField, 
                        registerButton, toLoginButton);
        return vbox;
    }

    public void clearText(){
        userNameTextField.clear();
        passwordField.clear();
        fullNameTextField.clear();
    }
}
