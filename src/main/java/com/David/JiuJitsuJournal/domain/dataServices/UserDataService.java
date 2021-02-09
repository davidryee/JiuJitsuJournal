package com.David.JiuJitsuJournal.domain.dataServices;

import com.David.JiuJitsuJournal.domain.models.User;

public interface UserDataService {
    User getUser(String username);
}
