package com.passwordmanager.views;

import com.passwordmanager.models.PasswordStoreModel;
import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.PanelObserver;
import com.passwordmanager.utils.SessionManager;

import javafx.scene.Scene;
import javafx.scene.control.Dialog;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainAppView implements PanelObserver{
    private final Stage parentStage;
    private Scene scene;
    private final BorderPane rootPanel;
    private UserModel user;
    private LeftPanelView leftPanelView;
    private RightPanelView rightPanelView;

    public MainAppView(Stage parentStage) {
        this.parentStage = parentStage;
        this.rootPanel = new BorderPane();
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
    }

    public Scene getScene() {
        showLoginDialog();
        scene = new Scene(rootPanel, 1000, 800);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        parentStage.setTitle("Aplikasi Penyimpanan Password");
        return scene;
    }

    public void redrawAll() {
        leftPanelView = new LeftPanelView();
        rightPanelView = new RightPanelView();
        
        // Set observer
        leftPanelView.setObserver(this);
        rightPanelView.setObserver(this);
        
        rootPanel.setLeft(leftPanelView.getView());
        rootPanel.setCenter(rightPanelView.getView());
    }

    private void showLoginDialog() {
        LoginDialog loginDialog = new LoginDialog();
        Dialog<UserModel> dialog = loginDialog.getDialog();
        dialog.showAndWait().ifPresent(loggedInUser -> {
            if (loggedInUser != null) {
                this.user = loggedInUser;
            }
            redrawAll();
        });
    }

    public void showFolderDialog() {
        FolderFormView folderFormView = new FolderFormView();
        Dialog<String> dialog = folderFormView.getDialog();

        dialog.showAndWait().ifPresent(folderName -> {
            if (folderName != null && !folderName.isEmpty()) {
                leftPanelView.refreshFolderTree();
            }
        });
    }

    public void logoutAction() {
        SessionManager.getInstance().clearSession();
        showLoginDialog();
    }

    public LeftPanelView getLeftPanelView() {
        return leftPanelView;
    }

    public RightPanelView getRightPanelView() {
        return rightPanelView;
    }

    @Override
    public void onPasswordSelected(PasswordStoreModel password) {
        rightPanelView.showPasswordDetail(password);
    }

    @Override
    public void onPasswordUpdated() {
        leftPanelView.refreshFolderTree();
    }

    @Override
    public void onFolderUpdated() {
        leftPanelView.refreshFolderTree();
    }
}