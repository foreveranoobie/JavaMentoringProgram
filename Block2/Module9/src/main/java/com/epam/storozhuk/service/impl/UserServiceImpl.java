package com.epam.storozhuk.service.impl;

import com.epam.storozhuk.dao.UserDao;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {

    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private UserDao userDao;

    /**
     * {@inheritDoc}
     */
    @Override
    public User getUserById(Long id) {
        return userDao.findById(id).get();
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public User createUser(User user) {
        User createdUser = userDao.save(user);
        logger.info("Created user: {}", createdUser);
        return createdUser;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public User updateDate(Long id, LocalDateTime updateDate) {
        logger.info("Setting updateDate to {} for user having id {}", updateDate, id);
        User user = userDao.findById(id).get();
        user.setUpdateDate(updateDate);
        userDao.save(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void refillBalance(Long id, float amount) {
        if (amount < 0L) {
            throw new IllegalArgumentException("Amount can't be less than 0");
        }
        logger.info("Received {} of currency to refill User (id = {})", amount, id);
        User user = userDao.findById(id).get();
        user.setBalance(user.getBalance() + amount);
        userDao.save(user);
    }

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
