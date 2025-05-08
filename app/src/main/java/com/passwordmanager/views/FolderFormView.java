package com.passwordmanager.views;

import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;
import com.passwordmanager.viewmodels.FolderViewModel;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class FolderFormView {
    private final FolderViewModel folderViewModel;
    private final UserModel user;

    public FolderFormView() {
        this.folderViewModel = new FolderViewModel();
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
    }

    public Dialog<String> getDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Tambah Folder");
        dialog.setHeaderText(null);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label folderNameLabel = new Label("Nama Folder:");
        TextField folderNameField = new TextField();
        grid.add(folderNameLabel, 0, 0);
        grid.add(folderNameField, 1, 0);

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        ButtonType cancelButtonType = new ButtonType("Batal", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType createButtonType = new ButtonType("Buat Folder", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(cancelButtonType, createButtonType);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == createButtonType) {
                String folderName = folderNameField.getText();
                String result = folderViewModel.createFolder(folderName, user.id);
                if (result == null) {
                    return folderName;
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText(result);
                    alert.showAndWait();
                    return null;
                }
            }
            return null;
        });

        return dialog;
    }
}