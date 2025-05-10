package com.passwordmanager.utils;

import com.passwordmanager.models.PasswordStoreModel;

public interface PanelObserver {
    void onPasswordSelected(PasswordStoreModel password);
    void onPasswordUpdated();
    void onFolderUpdated();
}
