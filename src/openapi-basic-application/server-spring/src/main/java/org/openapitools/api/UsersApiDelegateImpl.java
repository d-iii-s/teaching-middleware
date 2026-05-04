package org.openapitools.api;

import org.openapitools.model.User;
import org.openapitools.model.UserBase;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class UsersApiDelegateImpl implements UsersApiDelegate {

    private static final ConcurrentHashMap<Integer, User> userData = new ConcurrentHashMap<>();
    private static final AtomicInteger userLastId = new AtomicInteger(1);

    @Override
    public ResponseEntity<Void> createUser(User user) {
        user.setId(userLastId.getAndIncrement());
        userData.put(user.getId(), user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteUser(Integer userId) {
        User user = userData.remove(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Override
    public ResponseEntity<User> readUser(Integer userId) {
        User user = userData.get(userId);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<UserBase>> readUsers() {
        List<UserBase> users = new ArrayList<>();
        for (User user : userData.values()) {
            UserBase userBase = new UserBase();
            userBase.setId(user.getId());
            userBase.setFirstname(user.getFirstname());
            userBase.setLastname(user.getLastname());
            users.add(userBase);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> updateUser(Integer userId, User user) {
        user.setId(userId);
        userData.put(user.getId(), user);
        return new ResponseEntity<>(HttpStatus.RESET_CONTENT);
    }
}
