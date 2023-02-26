package com.epam.storozhuk;

import com.epam.storozhuk.dao.EventDao;
import com.epam.storozhuk.dao.TicketDao;
import com.epam.storozhuk.dao.UserDao;
import com.epam.storozhuk.facade.BookingFacade;
import com.epam.storozhuk.facade.impl.BookingFacadeImpl;
import com.epam.storozhuk.memory.EntityStorage;
import com.epam.storozhuk.pdf.PdfGenerator;
import com.epam.storozhuk.service.EventService;
import com.epam.storozhuk.service.TicketService;
import com.epam.storozhuk.service.UserService;
import com.epam.storozhuk.service.impl.EventServiceImpl;
import com.epam.storozhuk.service.impl.TicketServiceImpl;
import com.epam.storozhuk.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@ComponentScan(basePackages = "com.epam.storozhuk", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION,
                classes = {EnableWebMvc.class})})
@PropertySource("classpath:data_files.properties")
public class TestConfig {

    @Autowired
    private EntityStorage entityStorage;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserService userService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private PdfGenerator pdfGenerator;

    @Bean
    public EntityStorage entityStorage() {
        return new EntityStorage();
    }

    @Bean
    public EventDao eventDao() {
        EventDao eventDao = new EventDao();
        eventDao.setStorage(entityStorage);
        return eventDao;
    }

    @Bean
    public UserDao userDao() {
        UserDao userDao = new UserDao();
        userDao.setStorage(entityStorage);
        return userDao;
    }

    @Bean
    public TicketDao ticketDao() {
        TicketDao ticketDao = new TicketDao();
        ticketDao.setStorage(entityStorage);
        return ticketDao;
    }

    @Bean
    public EventService eventService() {
        return new EventServiceImpl();
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl();
    }

    @Bean
    public TicketService ticketService() {
        return new TicketServiceImpl();
    }

    @Bean
    public BookingFacade bookingFacade() {
        return new BookingFacadeImpl(userService, eventService, ticketService, pdfGenerator);
    }

    @Bean
    public PdfGenerator pdfGenerator() {
        return new PdfGenerator();
    }
}
