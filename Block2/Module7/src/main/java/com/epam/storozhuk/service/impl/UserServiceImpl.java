package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.UserDao;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Long id) {
        return userDao.get(id);
    }

    @Override
    public User createUser(User user) {
        return userDao.save(user);
    }

    @Override
    public User updateDate(Long id, LocalDateTime updateDate) {
        return userDao.updateDate(id, updateDate);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
