package utils;

import model.LoginData;
import model.User;

public class UserGenerator {

    public static User getRandomUser() {
        String email = "unique" + System.currentTimeMillis() + "@mail.com";
        return new User(email, "password123", "TestUser");
    }

    public static LoginData getLoginData(User user) {
        return new LoginData(user.getEmail(), user.getPassword());
    }
}