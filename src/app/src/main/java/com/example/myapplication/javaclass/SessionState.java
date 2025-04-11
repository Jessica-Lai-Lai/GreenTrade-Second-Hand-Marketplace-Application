package com.example.myapplication.javaclass;

public class SessionState extends UserState {
    public SessionState(UserSession userSession) {
        super(userSession);
    }
    @Override
    public boolean login(String Email, String password) {
        System.out.println("You cannot log in the session state");
        return false;
    }
    @Override
    public boolean logout() {
        System.out.println("You have Log out");
        userSession.changeState(new NoSessionState(userSession));
        return true;
    }
}
