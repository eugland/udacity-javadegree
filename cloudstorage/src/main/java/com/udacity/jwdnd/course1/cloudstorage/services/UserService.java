package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class UserService {

    private final UserMapper userMapper;
    private final HashService hashService;

    public UserService(UserMapper userMapper, HashService hashService) {
        this.userMapper = userMapper;
        this.hashService = hashService;
    }

    public boolean isUsernameAvaliable(String username) {
        return userMapper.getUserByUsername(username) == null;
    }

    public int createUser(User user) {
        SecureRandom r = new SecureRandom();
        byte[] salt = new byte[16];
        r.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashPassword = hashService.getHashedValue(user.getPassword(), encodedSalt);
        // we do not know if the local temp user will be used later in the thread or is being used somewhere else,
        // so we create a value copy just for uploading.
        User uploadingUser = new User(user.getUserid(),
                user.getUsername(),
                encodedSalt,
                hashPassword,
                user.getFirstname(),
                user.getLastname());
        System.out.println(uploadingUser);
        return userMapper.insertUsr(uploadingUser);
    }
    // String hashedPassword = hashService.getHashedValue(password, encodedSalt);

    public User getUser(String username) {
        return userMapper.getUserByUsername(username);
    }
}
