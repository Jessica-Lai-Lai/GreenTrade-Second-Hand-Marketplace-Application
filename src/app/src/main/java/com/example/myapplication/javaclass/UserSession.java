package com.example.myapplication.javaclass;

public class UserSession {
    // current state of the session
    UserState userState;
    // user on session
    String Email;
    public UserSession() {
        UserState defaultState = new NoSessionState(this);
        changeState(defaultState);
    }
    public void changeState(UserState state) {
        this.userState = state;
    }
    public boolean login(String Email, String password) {
        boolean loginOk = userState.login(Email, password);
        if(loginOk) {
            this.Email = Email;
        }
        return loginOk;
    }
    public String getEmail() {
        return Email;
    }
}
