package com.passwordmanager.viewmodels;

import com.passwordmanager.daos.FolderDao;
import com.passwordmanager.models.FolderModel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class FolderViewModel {
    private final FolderDao folderDao;
    private final ObservableList<FolderModel> folder;

    public FolderViewModel() {
        this.folderDao = new FolderDao();
        this.folder = FXCollections.observableArrayList();
    }

    public String createFolder(String name, int userId) {
        int folderId = folderDao.createFolder(name, userId);
        if (folderId > 0) {
            folder.add(new FolderModel(folderId, name));
            return null;
        }
        return "Gagal menambahkan folder";
    }

    public ObservableList<FolderModel> getFolder(int userId) {
        folder.clear();
        folder.addAll(folderDao.getfolderByUserId(userId));
        return folder;
    }
}