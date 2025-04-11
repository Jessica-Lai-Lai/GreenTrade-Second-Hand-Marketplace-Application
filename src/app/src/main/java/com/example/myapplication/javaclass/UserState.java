package com.example.myapplication.javaclass;

public abstract class UserState {
    protected UserSession userSession;
    public UserState(UserSession userSession) {
        this.userSession = userSession;
    }
    public abstract boolean login(String Email, String password);
    public abstract boolean logout();
}
