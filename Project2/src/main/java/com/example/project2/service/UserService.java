package com.example.project2.service;

import com.example.project2.dao.UserDao;
import com.example.project2.domain.entity.User;
import com.example.project2.exception.UserSaveFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    private final UserDao userDao;
    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    @Transactional
    public List<User> getAllUserSuccess(){return userDao.getAllUsers();}
    @Transactional
    public User getUserById(int id){return userDao.getUserById(id);}
    @Transactional
    public void saveUserSuccess(User user){userDao.addUser(user);}
    @Transactional(rollbackOn = UserSaveFailedException.class)
    public void saveUserFailed(User user) throws UserSaveFailedException{
        userDao.addUser(user);
        userDao.somethingWentWrong();
    }
    @Transactional
    public User getUserByUsernameAndPassword(String username, String password){return userDao.getUserByUsernameAndPassword(username, password);}
}
