package com.passwordmanager.viewmodels;

import com.passwordmanager.daos.FolderDao;
import com.passwordmanager.models.FolderModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FolderViewModel {
    private final FolderDao folderDao;
    private final ObservableList<FolderModel> folders;

    public FolderViewModel() {
        this.folderDao = new FolderDao();
        this.folders = FXCollections.observableArrayList();
    }

    public String createFolder(String name, int userId) {
        int folderId = folderDao.createFolder(name, userId);
        if (folderId > 0) {
            folders.add(new FolderModel(folderId, name));
            return null;
        }
        return "Gagal menambahkan folder";
    }

    public ObservableList<FolderModel> getFolders(int userId) {
        folders.clear();
        folders.addAll(folderDao.getFoldersByUserId(userId));
        return folders;
    }
}