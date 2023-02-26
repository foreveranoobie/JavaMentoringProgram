package com.epam.storozhuk.dao;

import com.epam.storozhuk.entity.User;
import java.time.LocalDateTime;

public class UserDao extends AbstractDao<User> {

    public UserDao() {
        super("user");
    }

    @Override
    public User get(Long id) {
        return (User) storage.get(key.concat(":" + id));
    }

    @Override
    public User save(User entity) {
        return (User) storage.save(key, entity);
    }

    @Override
    public User delete(Long id) {
        return (User) storage.delete(key, id);
    }

    public User updateDate(Long id, LocalDateTime updateDate) {
        User user = (User) storage.get("user:" + id);
        user.setUpdateDate(updateDate);
        return user;
    }
}
