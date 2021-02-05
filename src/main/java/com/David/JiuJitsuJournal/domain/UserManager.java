package com.David.JiuJitsuJournal.domain;

import com.David.JiuJitsuJournal.domain.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserManager {
    private UserDataService userDataService;

    public UserManager(UserDataService userDataService) {
        this.userDataService = userDataService;
    }

    public User getUser(String username) {
        return userDataService.getUser(username);
    }
}
