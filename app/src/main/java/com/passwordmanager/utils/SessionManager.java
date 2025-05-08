package com.passwordmanager.utils;

import java.util.HashMap;

public class SessionManager{

    private static SessionManager instance;
    private HashMap<String, Object> sessionData;

    private SessionManager() {
        // Private constructor agar tidak bisa diinstatiasi secara langsung
    }

    public static synchronized SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
            instance.sessionData = new HashMap<>();
        }
        return instance;
    }

    public Object getSesData(String key) {
        return sessionData.get(key);
    }

    public void setSesData(String key, Object value) {
        this.sessionData.put(key, value);
    }

    public void clearSession() {
        this.sessionData.clear();
    }
}