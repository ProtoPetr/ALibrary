package com.epam.servise;

import com.epam.entity.User;

import java.util.List;

/**
 * Interface declares methods for work with current entity
 * and contains specific logic
 */
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
