package com.example.ProjectClub.service;
import com.example.ProjectClub.model.User;
import com.example.ProjectClub.store.UserStore;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Optional;

@Component
@RequestScope
public class LoginProcessor {

    private final UserStore userStore;
    private final UserSession userSession;

    private String username;
    private String password;

    public LoginProcessor(UserStore userStore, UserSession userSession) {
        this.userStore = userStore;
        this.userSession = userSession;
    }

    public boolean login() {
        // Find the user by the username provided from the form.
        Optional<User> optionalUser = userStore.findByUsername(this.username);

        // Check if the user exists.
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            // Check if the submitted password matches the stored password.
            // NOTE: This is a plain text comparison, which is NOT secure for production.
            // This is the step that Spring Security's PasswordEncoder would handle.
            if (this.password.equals(user.getPassword())) {
                // If they match, the login is successful!
                // We "log the user in" by storing their name in the session bean.
                userSession.setUsername(user.getUsername());
                return true;
            }
        }

        // If the user doesn't exist or the password doesn't match, the login fails.
        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
