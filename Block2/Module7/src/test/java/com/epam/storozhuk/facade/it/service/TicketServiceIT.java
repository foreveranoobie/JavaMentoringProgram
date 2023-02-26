package com.epam.storozhuk.facade.it.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.epam.storozhuk.dao.TicketDao;
import com.epam.storozhuk.entity.Category;
import com.epam.storozhuk.entity.Ticket;
import com.epam.storozhuk.service.TicketService;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
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
public class TicketServiceIT {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketDao ticketDao;

    @Test
    public void shouldBookTicket() {
        LocalDateTime currentTime = LocalDateTime.now();
        Ticket ticket = Ticket.builder()
                .userId(1L)
                .eventId(1L)
                .purchaseDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .place(1)
                .createDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .updateDate(currentTime.truncatedTo(ChronoUnit.MINUTES))
                .cinemaPhones(Arrays.asList(1234, 9876))
                .cinemaFacilities(Arrays.asList(10, 2))
                .cinemaName("Cinema")
                .cinemaAddress("Address")
                .category(Category.STANDART)
                .build();

        Ticket savedTicket = ticketService.bookTicket(ticket);

        assertEquals(savedTicket, ticketDao.get(savedTicket.getId()));
    }
}
