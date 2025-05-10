package com.passwordmanager.views;

import org.kordamp.ikonli.javafx.FontIcon;

import com.passwordmanager.models.FolderModel;
import com.passwordmanager.models.PasswordStoreModel;
import com.passwordmanager.models.UserModel;
import com.passwordmanager.utils.PanelObserver;
import com.passwordmanager.utils.SessionManager;
import com.passwordmanager.viewmodels.FolderViewModel;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class LeftPanelView {
    private final VBox leftPanel;
    private final TreeView<Object> passwordTree;
    private final FolderViewModel folderViewModel;
    private final UserModel user;
    private PasswordFormView passwordFormView;
    private PanelObserver observer;

    public LeftPanelView() {
        this.user = (UserModel) SessionManager.getInstance().getSesData("user");
        this.leftPanel = new VBox();
        this.folderViewModel = new FolderViewModel();
        this.passwordTree = new TreeView<>();
        // this.passwordFormView = null;
        initialize();
    }

    private void initialize() {
        createSearchBox();
        createPasswordList();
    }

    public void setObserver(PanelObserver observer) {
        this.observer = observer;
    }
    
    private void setupTreeEvents() {
        passwordTree.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                TreeItem<Object> item = passwordTree.getSelectionModel().getSelectedItem();
                if (item != null && item.getValue() instanceof PasswordStoreModel) {
                    observer.onPasswordSelected((PasswordStoreModel) item.getValue());
                }
            }
        });
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
        
        passwordTree.setCellFactory(tv -> new TreeCell<Object>() {
            private final FontIcon folderIcon = new FontIcon("fas-folder");
            private final FontIcon keyIcon = new FontIcon("fas-key");
            
            @Override
            protected void updateItem(Object item, boolean empty) {
                super.updateItem(item, empty);
                
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (item instanceof FolderModel folderModel) {
                        setText(folderModel.name);
                        folderIcon.setIconColor(javafx.scene.paint.Color.NAVY);
                        setGraphic(folderIcon);
                    } else if (item instanceof PasswordStoreModel passwordStoreModel) {
                        setText(passwordStoreModel.name);
                        keyIcon.setIconColor(javafx.scene.paint.Color.CRIMSON);
                        setGraphic(keyIcon);
                    } else if (item instanceof String string) {
                        setText(string);
                        folderIcon.setIconColor(javafx.scene.paint.Color.NAVY);
                        setGraphic(folderIcon);
                    }
                }
            }
        });

        setupTreeEvents();

        refreshFolderTree();
        passwordList.getChildren().addAll(titleLabel, passwordTree);
        VBox.setVgrow(passwordList, Priority.ALWAYS);
        leftPanel.getChildren().add(passwordList);
    }

    public void refreshFolderTree() {
        TreeItem<Object> rootItem = new TreeItem<>("Folder Password");
        rootItem.setExpanded(true);
        
        ObservableList<FolderModel> folder_list = folderViewModel.getFolder(user.id);
        for (FolderModel folder : folder_list) {
            TreeItem<Object> folderNode = new TreeItem<>(folder);
            rootItem.getChildren().add(folderNode);
            
            ObservableList<PasswordStoreModel> passwords = folderViewModel.getPasswordsByFolder(folder.id);
            for (PasswordStoreModel password : passwords) {
                folderNode.getChildren().add(new TreeItem<>(password));
            }
        }
        
        passwordTree.setRoot(rootItem);
    }

    public void setPasswordFormView(PasswordFormView passwordFormView) {
        this.passwordFormView = passwordFormView;
    }

    public VBox getView() {
        return leftPanel;
    }
}