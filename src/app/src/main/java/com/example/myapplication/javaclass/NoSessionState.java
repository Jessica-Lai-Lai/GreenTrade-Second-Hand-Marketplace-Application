package com.example.myapplication.javaclass;

public class NoSessionState extends UserState {
    public NoSessionState(UserSession userSession) {
        super(userSession);
    }
    @Override //this is a dummy example (no auth method should be hard-coded)
    public boolean login(String Email, String password) {
        if("comp2100@anu.edu.au".equals(Email) && "comp2100".equals(password)) {
            System.out.println("Login done");
            SessionState nextState = new SessionState(userSession);
            userSession.changeState(nextState);
            return true;
        } else if ("comp6442@anu.edu.au".equals(Email) && "comp6442".equals(password)) {
            SessionState nextState = new SessionState(userSession);
            userSession.changeState(nextState);
            return true;
        }
        System.out.println("Login failed");
        return false;
    }
    @Override
    public boolean logout() {
        System.out.println("You cannot logout in no session state");
        return false;
    }
}
