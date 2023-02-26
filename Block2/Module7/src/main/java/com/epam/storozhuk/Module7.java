package com.epam.storozhuk;

import com.epam.storozhuk.dao.UserDao;
import com.epam.storozhuk.facade.BookingFacade;
import com.epam.storozhuk.memory.EntityStorage;
import com.epam.storozhuk.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Module7 {

    public static void main(String... args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("ApplicationContext.xml");

        EntityStorage storage = context.getBean("entityStorage", EntityStorage.class);

        UserDao userDao = context.getBean("userDao", UserDao.class);

        UserService userService = (UserService) context.getBean("userService", UserService.class);

        BookingFacade bookingFacade = context.getBean("bookingFacade", BookingFacade.class);
        System.out.println(storage);
    }
}
