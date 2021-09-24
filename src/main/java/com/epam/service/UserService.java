package com.epam.service;

import com.epam.entity.User;

import java.util.List;

public interface UserService {
    void createUser(User user);
    void updateUserData(User user);
    User findUserById(long id);
    List<User> getUsersBySearch(String searchStr);
    List<User> findAllUsers();
    List<User> findAllLibraryUsers();
    boolean userIsExist(String login, String password);
    User getUserByLoginPassword(String login, String password);
    User.ROLE getRoleByLoginPassword(String login, String password);
}
