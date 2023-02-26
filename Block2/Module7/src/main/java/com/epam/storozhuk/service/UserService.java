package com.epam.storozhuk.service;

import com.epam.storozhuk.entity.User;
import java.time.LocalDateTime;

public interface UserService {

    User getUserById(Long id);

    User createUser(User user);

    User updateDate(Long id, LocalDateTime updateDate);
}
