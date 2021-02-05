package com.David.JiuJitsuJournal.domain;

import com.David.JiuJitsuJournal.domain.models.User;

public interface UserDataService {
    User getUser(String username);
}
