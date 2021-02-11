package com.David.JiuJitsuJournal.data.services;

import com.David.JiuJitsuJournal.data.mappers.UserMapper;
import com.David.JiuJitsuJournal.data.repository.UserRepository;
import com.David.JiuJitsuJournal.domain.models.User;
import org.springframework.stereotype.Service;

@Service
public class UserDataService implements com.David.JiuJitsuJournal.domain.dataServices.UserDataService {
    UserRepository userRepository;

    public UserDataService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public User getUser(String username) {
        com.David.JiuJitsuJournal.data.entities.User entityUser = userRepository.findByUsername(username).get();
        if(entityUser != null){
            return UserMapper.mapEntityToDomain(entityUser);
        }

        return null;
    }
}
