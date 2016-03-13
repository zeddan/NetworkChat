package gui;

import server.User;

public class UserMock extends User {

    String username;

    public UserMock(String username) {
        super(null, null);
        this.username = username;
    }

    public String getUserName() {
        return username;
    }

}
