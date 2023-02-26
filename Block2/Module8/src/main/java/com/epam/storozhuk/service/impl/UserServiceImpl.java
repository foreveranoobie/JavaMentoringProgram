package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.UserDao;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * {@inheritDoc}
 */
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserById(Long id) {
        return userDao.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(User user) {
        User createdUser = userDao.save(user);
        logger.info("Created user: {}", createdUser);
        return createdUser;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User updateDate(Long id, LocalDateTime updateDate) {
        logger.info("Setting updateDate to {} for user having id {}", updateDate, id);
        return userDao.updateDate(id, updateDate);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
