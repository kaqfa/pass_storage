package com.passwordmanager.views;

import com.passwordmanager.models.FolderModel;
import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.SessionManager;
import com.passwordmanager.viewmodels.FolderViewModel;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LeftPanelView {
    private final VBox leftPanel;
    private final TreeView<String> passwordTree;
    private final FolderViewModel folderViewModel;
    private final UserModel user;

    public LeftPanelView() {
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
        this.leftPanel = new VBox();
        this.folderViewModel = new FolderViewModel();
        this.passwordTree = new TreeView<>();
        initialize();
    }

    private void initialize() {
        createSearchBox();
        createPasswordList();
    }

    private void createSearchBox() {
        HBox searchBox = new HBox(10);
        searchBox.setPadding(new Insets(10));

        TextField searchField = new TextField();
        Button searchButton = new Button("Cari");

        searchBox.getChildren().addAll(searchField, searchButton);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        leftPanel.getChildren().add(searchBox);
    }

    private void createPasswordList() {
        VBox passwordList = new VBox();
        passwordList.setPadding(new Insets(10));
        passwordList.setStyle("-fx-border-color: #ccc; -fx-border-width: 0px;");

        Label titleLabel = new Label("List Data Password");
        VBox.setVgrow(passwordTree, Priority.ALWAYS);
        refreshFolderTree();

        passwordList.getChildren().addAll(titleLabel, passwordTree);
        VBox.setVgrow(passwordList, Priority.ALWAYS);
        leftPanel.getChildren().add(passwordList);
    }

    public void refreshFolderTree() {
        TreeItem<String> rootItem = new TreeItem<>("Folder Password");
        rootItem.setExpanded(true);
        
        ObservableList<FolderModel> folder_list = folderViewModel.getFolder(user.id);
        for (FolderModel folder : folder_list) {
            rootItem.getChildren().add(new TreeItem<>(folder.name));
        }
        
        passwordTree.setRoot(rootItem);
    }

    public VBox getView() {
        return leftPanel;
    }
}