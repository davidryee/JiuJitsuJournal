package com.David.JiuJitsuJournal.data.mappers;

import com.David.JiuJitsuJournal.domain.models.User;

public class UserMapper {
    public static User mapEntityToDomain(com.David.JiuJitsuJournal.data.entities.User user){
        return new User(user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword());
    }
}
