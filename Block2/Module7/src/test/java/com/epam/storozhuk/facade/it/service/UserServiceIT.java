package com.epam.storozhuk.facade.it.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.storozhuk.dao.UserDao;
import com.epam.storozhuk.entity.User;
import com.epam.storozhuk.service.UserService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:/ApplicationContext.xml")
@ExtendWith(SpringExtension.class)
public class UserServiceIT {

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    @Test
    public void shouldCreateUser() {
        User user = User.builder()
                .username("test-user")
                .firstname("Test")
                .lastname("User")
                .createDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .email("test-user@gmail.com")
                .age(30)
                .build();

        User createdUser = userService.createUser(user);

        assertAll(() -> {
            assertEquals(user.getUsername(), createdUser.getUsername());
            assertEquals(user.getFirstname(), createdUser.getFirstname());
            assertEquals(user.getLastname(), createdUser.getLastname());
            assertEquals(user.getEmail(), createdUser.getEmail());
            assertEquals(user.getAge(), createdUser.getAge());
            assertEquals(user.getCreateDate(), createdUser.getCreateDate());
            assertEquals(user.getUpdateDate(), createdUser.getUpdateDate());
        });

        assertEquals(createdUser, userDao.get(createdUser.getId()));
    }
}
