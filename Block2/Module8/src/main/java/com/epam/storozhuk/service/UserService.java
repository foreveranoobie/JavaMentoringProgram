package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.User;
import java.time.LocalDateTime;

/**
 * Works with {@link User}
 */
public interface UserService {

    /**
     * @param id of user
     * @return {@link User} found in storage
     */
    User getUserById(Long id);

    /**
     * Creates new user and puts it to storage
     *
     * @param user to be saved
     * @return saved instance of {@link User}
     */
    User createUser(User user);

    /**
     * Renews updateDate User's attribute
     *
     * @param id of user
     * @param updateDate to set
     * @return update {@link User}
     */
    User updateDate(Long id, LocalDateTime updateDate);
}
